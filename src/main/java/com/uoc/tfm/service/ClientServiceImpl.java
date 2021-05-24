package com.uoc.tfm.service;

import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.commons.domain.StationsStatus;
import com.uoc.tfm.domain.contracts.Contract;
import com.uoc.tfm.domain.status.StationStatus;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.apache.logging.log4j.LogManager.getLogger;

@Service
public class ClientServiceImpl implements ClientService {

    @Value("${service.name:default}")
    private String serviceName;

    @Autowired
    private JdcadeuxImpl jdcadeuxImpl;

    private Logger logger = getLogger(ClientServiceImpl.class);

    @Override
    public StationsLocation getStationsLocation(String service) {
        StationsLocation stationsLocation = new StationsLocation();
        RestTemplate restTemplate = new RestTemplate();

        StationStatus[] stationStatusList = restTemplate.getForObject(
                jdcadeuxImpl.buildStationsUrl(service),
                StationStatus[].class);

        Arrays.asList(stationStatusList)
                .parallelStream()
                .forEach(stationStatus ->
                        stationsLocation.addStation(
                                stationStatus.getNumber(),
                                stationStatus.getPosition().getLat(),
                                stationStatus.getPosition().getLng(),
                                stationStatus.getAddress()));

        return stationsLocation;
    }

    @Override
    public StationsStatus getStationStatus(String service) {
        StationsStatus stationsStatus = new StationsStatus();
        RestTemplate restTemplate = new RestTemplate();

        StationStatus[] stationStatusList = restTemplate.getForObject(
                jdcadeuxImpl.buildStationsUrl(service),
                StationStatus[].class);

        Arrays.asList(stationStatusList)
                .parallelStream()
                .forEach(stationStatus ->
                        stationsStatus.addStation(
                                stationStatus.getNumber(),
                                stationStatus.getBike_stands(),
                                stationStatus.getAvailable_bikes()));

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

}
