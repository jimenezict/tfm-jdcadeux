package com.uoc.tfm.service;

public interface Jdcadeux {

    String buildLocationUrl(String domain, String service);

    String buildStatusUrl(String domain, String service, String city, int id);

    String buildContractsUrl();

}
