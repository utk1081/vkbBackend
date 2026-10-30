package com.example.insuranceproposal.tariff;

import java.math.BigDecimal;

public class Tariff {

	public Tariff() {
		// TODO Auto-generated constructor stub
	}
	private String tariffId;
    private String name;
    private BigDecimal monthlyPrice;
	public String getTariffId() {
		return tariffId;
	}
	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getMonthlyPrice() {
		return monthlyPrice;
	}
	public void setMonthlyPrice(BigDecimal monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}
}
