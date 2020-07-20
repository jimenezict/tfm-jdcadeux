package com.uoc.tfm.service;

import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.commons.domain.StationsStatus;

public interface ClientService {

    public StationsLocation getStationsLocation(String domain, String service);

    public StationsStatus getStationStatus(String domain,String city, String service);

    public String getServiceName();

}
