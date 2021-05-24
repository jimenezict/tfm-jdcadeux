package com.uoc.tfm.controller;

import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.domain.contracts.Contract;
import com.uoc.tfm.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JdcadeuxControllerImpl implements JdcadeuxController {

    @Autowired
    private ClientService clientService;

    private static final Logger log = LoggerFactory.getLogger(JdcadeuxControllerImpl.class);

    @Override
    public Contract[] getContracts() {
        log.info("Starting call to get contracts list");
        Contract[] contracts;
        try {
            contracts = clientService.getServiceContracts();
        } catch (Exception e) {
            log.error("Fail on call to get contract list ", e);
            return null;
        }
        log.info("Ending call to get contracts list with size {}", contracts.length);

        return contracts;
    }

}
