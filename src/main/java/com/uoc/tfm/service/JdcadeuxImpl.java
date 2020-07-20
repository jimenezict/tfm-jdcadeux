package com.uoc.tfm.service;

import org.springframework.stereotype.Service;

@Service
public class JdcadeuxImpl implements Jdcadeux {

    @Override
    public String buildLocationUrl(String domain, String service) {
        return "http://www." + service + "." + domain + "/service/carto";
    }

    @Override
    public String buildStatusUrl(String domain, String service, String city, int id) {
        return "http://www." + service + "." + domain + "/service/stationdetails/" + city + "/" + id;
    }
}
