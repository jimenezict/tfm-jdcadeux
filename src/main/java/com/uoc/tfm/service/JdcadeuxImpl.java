package com.uoc.tfm.service;

import org.springframework.stereotype.Service;

@Service
public class JdcadeuxImpl implements Jdcadeux {

    @Override
    public String buildLocationUrl(String service) {
        return "http://www." + service + ".es/service/carto";
    }

    @Override
    public String buildStatusUrl(String service, String city, int id) {
        return "http://www." + service + ".es/service/stationdetails/" + city + "/" + id;
    }
}
