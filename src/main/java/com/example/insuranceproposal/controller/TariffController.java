package com.example.insuranceproposal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.insuranceproposal.tariff.Tariff;
import com.example.insuranceproposal.tariff.TariffClient;

@RestController
@RequestMapping("/tariffs")
public class TariffController {

    private final TariffClient tariffClient;

    public TariffController(TariffClient tariffClient) {
        this.tariffClient = tariffClient;
    }

    @GetMapping
    public List<Tariff> getAllTariffs() {
        return tariffClient.findAll();
    }
}