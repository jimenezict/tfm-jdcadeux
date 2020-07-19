package com.uoc.tfm.controller;

import com.uoc.tfm.service.ClientService;
import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.commons.domain.StationsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientControllerImpl implements ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientControllerImpl.class);

    @Autowired
    private ClientService clientService;

    @Override
    public StationsLocation getStationsLocation(String service) {
        log.info("Starting station collections: {} and city {}", clientService.getServiceName(), service);
        StationsLocation stationsLocation = new StationsLocation();

        try {
            stationsLocation = clientService.getStationsLocation(service);
        } catch (Exception e) {
            log.error("Fail on Location status collection {} and city {} due to: ", clientService.getServiceName(), service, e);
            return stationsLocation;
        }
        log.info("Ending station collections: {} and city {} with {} registers",
                clientService.getServiceName(), service,
                stationsLocation.getStationLocationList().size());
        return stationsLocation;

    }

    @Override
    public StationsStatus getStationStatus(String city, String service) {
        log.info("Starting station status collections: {} and city {}", service, city);
        StationsStatus stationStatus = new StationsStatus();

        try {
            stationStatus = clientService.getStationStatus(city, service);
        } catch (Exception e) {
            log.error("Fail on station status collection {} and city {} due to: ", clientService.getServiceName(), city, e);
            return stationStatus;
        }
        log.info("Ending station status collections: {} and city {} with {} registers",
                clientService.getServiceName(), city,
                stationStatus.getStationStatusList().size());
        return stationStatus;

    }
}
