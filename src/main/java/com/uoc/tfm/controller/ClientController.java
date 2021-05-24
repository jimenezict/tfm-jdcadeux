package com.uoc.tfm.controller;


import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.commons.domain.StationsStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface ClientController {

    @GetMapping("/location/{service}")
    public StationsLocation getStationsLocation(@PathVariable("service") String service);

    @GetMapping("/status/{service}")
    public StationsStatus getStationStatus(@PathVariable("service") String service);

}
