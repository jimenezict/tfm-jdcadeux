package com.uoc.tfm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JdcadeuxImpl implements Jdcadeux {

    private static final String URL_ROOT = "https://api.jcdecaux.com/vls/v1/";

    @Value("${jdcadeux.apiKey}")
    private String apiKey;

    @Override
    public String buildStationsUrl(String service) {
        return URL_ROOT + "stations?contract=" + service + "&apiKey=" + apiKey;
    }

    @Override
    public String buildContractsUrl() {
        return URL_ROOT + "contracts?apiKey=" + apiKey;
    }

}
