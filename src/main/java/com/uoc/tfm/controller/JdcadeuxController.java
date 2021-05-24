package com.uoc.tfm.controller;

import com.uoc.tfm.domain.contracts.Contract;
import org.springframework.web.bind.annotation.GetMapping;

public interface JdcadeuxController {

    @GetMapping("/getContracts")
    public Contract[] getContracts();
}
