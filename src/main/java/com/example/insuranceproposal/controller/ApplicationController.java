package com.example.insuranceproposal.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.insuranceproposal.domain.InsuranceApplication;
import com.example.insuranceproposal.dto.BankDetailsRequest;
import com.example.insuranceproposal.dto.BasicInformationRequest;
import com.example.insuranceproposal.dto.SummaryResponse;
import com.example.insuranceproposal.dto.TariffSelectionRequest;
import com.example.insuranceproposal.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
public class ApplicationController {

	private ApplicationService applicationService;

	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@PostMapping("/applications")
	public UUID startSpplication() {
		return applicationService.startApplication();
	}
	
	@GetMapping("/applications/{applicationId}")
	public InsuranceApplication getApplication(@PathVariable UUID applicationId) {
		return applicationService.getApplication(applicationId);
	}
	
	@PostMapping("/applications/{applicationId}/basic-information")
	public void saveBasicInformation(@PathVariable UUID applicationId, @Valid @RequestBody BasicInformationRequest request) {
		applicationService.saveBasicInformation(applicationId, request);
	}
	
	@PostMapping("/applications/{applicationId}/tariffs")
	public void saveTariffSelection(
	        @PathVariable UUID applicationId,
	      @Valid  @RequestBody TariffSelectionRequest request) {

	    applicationService.saveTariffSelection(applicationId, request);
	}
	
	@PostMapping("/applications/{applicationId}/bank-details")
	public void saveBankDetail(@PathVariable UUID applicationId, @Valid @RequestBody BankDetailsRequest request) {
		applicationService.saveBankDetails(applicationId, request);
	}
	
	@GetMapping("/applications/{applicationId}/summary")
	public SummaryResponse getSummary(@PathVariable UUID applicationId) {
	    return applicationService.getSummary(applicationId);
	}
	
	@PostMapping("/applications/{applicationId}/submit")
	public void submitApplication(@PathVariable UUID applicationId) {
	    applicationService.submitApplication(applicationId);
	}
}
