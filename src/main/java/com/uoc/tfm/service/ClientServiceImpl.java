package com.uoc.tfm.service;

import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.commons.domain.StationsStatus;
import com.uoc.tfm.domain.contracts.Contract;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.LogManager.getLogger;

@Service
public class ClientServiceImpl implements ClientService {

    @Value("${service.name:default}")
    private String serviceName;

    @Autowired
    private JdcadeuxImpl jdcadeuxImpl;

    private Logger logger = getLogger(ClientServiceImpl.class);

    @Override
    public StationsLocation getStationsLocation(String domain, String service) {
        StationsLocation stationsLocation = new StationsLocation();
        BufferedReader callResult = getCall(jdcadeuxImpl.buildLocationUrl(domain, service));

        try {
            if (nonNull(callResult)) {
                callResult
                        .lines()
                        .filter(line -> line.contains("<marker "))
                        .forEach(line -> mapLocationLine(line, stationsLocation));
            }
            callResult.close();
        } catch (Exception ex) {}

        return stationsLocation;
    }

    @Override
    public StationsStatus getStationStatus(String domain, String city, String service) {
        StationsStatus stationsStatus = new StationsStatus();
        getStationsLocation(domain, service).getStationLocationList().parallelStream().forEach(stationLocation -> {
            int i = stationLocation.getId();
            try {
                addStationStatusById(domain, city, service, stationsStatus, i);
            } catch (IOException e) {
                logger.warn("Doesn't found a station with id {} for the city {}", i, city);
            } catch (Exception e) {
                logger.error("Critical error on id {} for the city {}. Error ", i, city, e);
            }
        });

        return stationsStatus;
    }

    @Override
    public Contract[] getServiceContracts() {
        RestTemplate restTemplate = new RestTemplate();
        Contract[] contracts =
                restTemplate.getForObject(
                        jdcadeuxImpl.buildContractsUrl(),
                        Contract[].class);

        return contracts;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    private BufferedReader getCall(String service) {
        HttpEntity entity = null;
        final HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(service);

        try {
            HttpResponse response = httpClient.execute(request);
            entity = response.getEntity();
            if (entity != null) {
                return new BufferedReader(new InputStreamReader(entity.getContent()));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void mapLocationLine(String line, StationsLocation stationsLocation) {
        stationsLocation.addStation(
                Integer.valueOf(getFieldContent(line, "number")),
                Double.valueOf(getFieldContent(line, "lat")),
                Double.valueOf(getFieldContent(line, "lng")),
                getFieldContent(line, "address"));
    }

    private static String getFieldContent(String line, String field) {
        int startIndex = line.indexOf(field + "=\"");
        if (startIndex < 0) return "Field " + field + " not found";
        int endIndex = line.indexOf("\" ", startIndex);
        if (endIndex < 0) return "Field " + field + " not found";
        return line.substring(startIndex + field.length() + 2, endIndex);
    }

    private static String getValueContent(String stationXML, String value) {
        int startIndex = stationXML.indexOf(value);
        if (startIndex < 0) return "Value " + value + " not found";
        int endIndex = stationXML.indexOf("</" + value, startIndex);
        if (endIndex < 0) return "Value " + value + " not found";
        return stationXML.substring(startIndex + value.length() + 1, endIndex);
    }

    private void mapStationsStatus(int i, String stationXML, StationsStatus stationStatus) {
        stationStatus.addStation(i,
                Integer.valueOf(getValueContent(stationXML, "total")),
                Integer.valueOf(getValueContent(stationXML, "available")));
    }

    private int numberOfStations(String domain, String service) {
        BufferedReader callResult = getCall(jdcadeuxImpl.buildLocationUrl(domain, service));
        long numberOfStation = 0;

        try {
            if (nonNull(callResult)) {
                numberOfStation = callResult
                        .lines()
                        .filter(line -> line.contains("<marker "))
                        .count();
            }
            callResult.close();
        } catch (Exception ex) {}

        return (int) numberOfStation;
    }

    private void addStationStatusById(String domain, String city, String service, StationsStatus stationsStatus, int i) throws IOException {
        BufferedReader callResult = getCall(jdcadeuxImpl.buildStatusUrl(domain, service, city, i));
        String stationXML = callResult.lines().collect(Collectors.joining("\n"));
        mapStationsStatus(i, stationXML, stationsStatus);
        callResult.close();
    }
}
