package com.uoc.tfm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JdcadeuxImpl implements Jdcadeux {

    @Value("${jdcadeux.apiKey}")
    private String apiKey;

    @Override
    public String buildLocationUrl(String domain, String service) {
        return "http://www." + service + "." + domain + "/service/carto";
    }

    @Override
    public String buildStatusUrl(String domain, String service, String city, int id) {
        return "http://www." + service + "." + domain + "/service/stationdetails/" + city + "/" + id;
    }

    @Override
    public String buildContractsUrl() {
        return "https://api.jcdecaux.com/vls/v1/contracts?apiKey=" + apiKey;
    }

}
