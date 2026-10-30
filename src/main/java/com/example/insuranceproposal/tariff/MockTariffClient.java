package com.example.insuranceproposal.tariff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class MockTariffClient implements  TariffClient{

	private final List<Tariff> tariffs = List.of(
	        createTariff("HEALTH_BASIC", "Health Basic", "80.00"),
	        createTariff("DENTAL_PLUS", "Dental Plus", "15.00")
	    );
	
	@Override
	public Optional<Tariff> findById(String tariffId) {
		// TODO Auto-generated method stub
		return tariffs.stream().filter(t-> t.getTariffId().equals(tariffId)).findFirst();
	}


	@Override
	public List<Tariff> findAll() {
		// TODO Auto-generated method stub
		return tariffs;
	}
	
	private Tariff createTariff(
            String tariffId, String name, String monthlyPrice) {

        Tariff tariff = new Tariff();
        tariff.setTariffId(tariffId);
        tariff.setName(name);
        tariff.setMonthlyPrice(new BigDecimal(monthlyPrice));

        return tariff;
    }


}
