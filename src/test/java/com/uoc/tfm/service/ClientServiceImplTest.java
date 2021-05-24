package com.uoc.tfm.service;

import com.uoc.tfm.commons.domain.StationLocation;
import com.uoc.tfm.commons.domain.StationStatus;
import com.uoc.tfm.commons.domain.StationsStatus;
import com.uoc.tfm.domain.contracts.Contract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl underTest;

    @Test
    public void getStationStatus_shouldReturnListOfStations_whenContractSantanderIsCall() {
        List<StationStatus> stationsStatus = underTest.getStationStatus("santander").getStationStatusList();
        assertThat(stationsStatus.size() > 10);
        assertThat(stationsStatus.get(0).getId() > 0);
        assertThat(stationsStatus.get(0).getSize() > 0);
        assertThat(stationsStatus.get(0).getOccupacy() <= stationsStatus.get(0).getSize());
    }

    @Test
    public void getStationStatus_shouldReturnListOfLocation_whenContractSantanderIsCall() {
        List<StationLocation> stationsLocation = underTest.getStationsLocation("santander").getStationLocationList();
        assertThat(stationsLocation.size() > 10);
        assertThat(stationsLocation.get(0).getId() > 0);
        assertThat(stationsLocation.get(0).getAddress().length() > 0);
        assertThat(stationsLocation.get(0).getLatitude() != 0.00);
        assertThat(stationsLocation.get(0).getLongitude() != 0.00);
    }

    @Test
    public void buildContractsUrl_shouldReturnUrlwithApiKey() {
        Contract[] contracts = underTest.getServiceContracts();
        assertThat(contracts.length > 20);
    }

}