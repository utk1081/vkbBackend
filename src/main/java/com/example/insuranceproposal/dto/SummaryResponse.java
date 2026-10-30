package com.example.insuranceproposal.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.insuranceproposal.domain.ApplicationStatus;
import com.example.insuranceproposal.domain.BankDetails;
import com.example.insuranceproposal.domain.BasicInformation;
import com.example.insuranceproposal.domain.SelectedTariff;

public class SummaryResponse {

	private UUID applicationId;
    private ApplicationStatus status;
    private BasicInformation basicInformation;
    private List<SelectedTariff> selectedTariffs;
    private BankDetails bankDetails;
    private BigDecimal totalAmount;
	public UUID getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(UUID applicationId) {
		this.applicationId = applicationId;
	}
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	public BasicInformation getBasicInformation() {
		return basicInformation;
	}
	public void setBasicInformation(BasicInformation basicInformation) {
		this.basicInformation = basicInformation;
	}
	public List<SelectedTariff> getSelectedTariffs() {
		return selectedTariffs;
	}
	public void setSelectedTariffs(List<SelectedTariff> selectedTariffs) {
		this.selectedTariffs = selectedTariffs;
	}
	public BankDetails getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
