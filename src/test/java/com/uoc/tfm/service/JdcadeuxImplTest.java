package com.uoc.tfm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdcadeuxImplTest {

    @Autowired
    private Jdcadeux jdcadeuxImpl;

    @Value("${jdcadeux.apiKey}")
    private String apiKey;

    @Test
    public void buildContractsUrl_shouldReturnUrlwithApiKey() {
        String contractsUrl = jdcadeuxImpl.buildContractsUrl();
        assertThat(!contractsUrl.isEmpty());
        assertThat(contractsUrl.contains("apiKey="));
        assertThat(contractsUrl.contains(apiKey));
    }

}