package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.criteria.RateCriteria;
import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.services.rates.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rates")
public class RatesController {

    private final RatesService ratesService;

    @Autowired
    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @PostMapping()
    public RateEntity saveRate(
            @RequestBody @Valid RateCriteria criteria
    ) {
        return ratesService
                .saveRate(criteria)
                .orElseThrow();
    }

}