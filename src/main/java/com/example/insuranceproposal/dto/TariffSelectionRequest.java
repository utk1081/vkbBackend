package com.example.insuranceproposal.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TariffSelectionRequest {
	//@NotNull(message = "Selected tariffs are required")
	@NotEmpty(message = "At least one tariff must be selected")
	private List<String> selectedTariffIds;

	public List<String> getSelectedTariffIds() {
		return selectedTariffIds;
	}

	public void setSelectedTariffIds(List<String> selectedTariffIds) {
		this.selectedTariffIds = selectedTariffIds;
	}
	
}
