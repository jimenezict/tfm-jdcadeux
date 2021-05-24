package com.uoc.tfm.service;

import com.uoc.tfm.domain.contracts.Contract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl underTest;

    @Test
    public void buildContractsUrl_shouldReturnUrlwithApiKey() {
        Contract[] contracts = underTest.getServiceContracts();
        assertThat(contracts.length > 20);
    }

}