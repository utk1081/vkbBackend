package com.example.insuranceproposal.tariff;

import java.util.List;
import java.util.Optional;

public interface TariffClient {

	 Optional<Tariff> findById(String tariffId);

	    List<Tariff> findAll();
	    
}
