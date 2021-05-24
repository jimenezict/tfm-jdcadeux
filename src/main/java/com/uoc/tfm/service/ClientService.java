package com.uoc.tfm.service;

import com.uoc.tfm.commons.domain.StationsLocation;
import com.uoc.tfm.commons.domain.StationsStatus;
import com.uoc.tfm.domain.contracts.Contract;

public interface ClientService {

    public StationsLocation getStationsLocation(String service);

    public StationsStatus getStationStatus(String service);

    public Contract[] getServiceContracts();

    public String getServiceName();

}
