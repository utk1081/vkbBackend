package com.example.insuranceproposal.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.insuranceproposal.domain.ApplicationStatus;
import com.example.insuranceproposal.domain.BankDetails;
import com.example.insuranceproposal.domain.BasicInformation;
import com.example.insuranceproposal.domain.InsuranceApplication;
import com.example.insuranceproposal.dto.BankDetailsRequest;
import com.example.insuranceproposal.dto.BasicInformationRequest;
import com.example.insuranceproposal.dto.SummaryResponse;
import com.example.insuranceproposal.dto.TariffSelectionRequest;
import com.example.insuranceproposal.repository.ApplicationRepository;
import com.example.insuranceproposal.tariff.Tariff;
import com.example.insuranceproposal.tariff.TariffClient;

import org.springframework.http.HttpStatus;
import com.example.insuranceproposal.domain.SelectedTariff;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final TariffClient tariffClient;
	public ApplicationService (ApplicationRepository applicationRepository,TariffClient tariffClient) {
		this.applicationRepository=applicationRepository;
		this.tariffClient=tariffClient;
		//this is construction injection in spring example
	}
	public UUID startApplication() {
		InsuranceApplication application=new InsuranceApplication();
		UUID applicationId=UUID.randomUUID();
		application.setApplicationId(applicationId);
		application.setStatus(ApplicationStatus.IN_PROGRESS);
		applicationRepository.save(application);// this is Dependency injection IOC in spring example
		return applicationId;
		
	}

public InsuranceApplication getApplication(UUID applicationId) {
    return applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Application not found" ));
	
}

public void saveBasicInformation(UUID aplicationId,BasicInformationRequest request) {
	InsuranceApplication application=applicationRepository.findById(aplicationId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Applcation not found"));
	ensureApplicationIsInProgress(application);
	BasicInformation basicInformation=new BasicInformation();
    basicInformation.setVorname(request.getVorname());
    basicInformation.setNachname(request.getNachname());
    LocalDate.parse(request.getDateOfBirths());
    basicInformation.setDateOfBirth(request.getDateOfBirths());
    basicInformation.setProfession(request.getBeruf());
    basicInformation.setEmail(request.getEmail());
    application.setBasicInformation(basicInformation);
    applicationRepository.save(application);
}

public void saveTariffSelection(UUID applicationId, TariffSelectionRequest request) {
	InsuranceApplication application = applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Application not found"));
	ensureApplicationIsInProgress(application);
	//List<SelectedTariff> selectedTariffs=request.getSelectedTariffIds().stream().map(tariffId-> {SelectedTariff tariff= new SelectedTariff();tariff.setTariffId(tariffId);return tariff;}).toList();
	List<SelectedTariff> selectedTariffs=new ArrayList<SelectedTariff>();
	BigDecimal totalAmount=BigDecimal.ZERO;
	 for (String tariffId : request.getSelectedTariffIds()) {

	        Tariff tariff = tariffClient.findById(tariffId)
	                .orElseThrow(() -> new ResponseStatusException(
	                        HttpStatus.BAD_REQUEST,
	                        "Unknown tariff: " + tariffId));

	        SelectedTariff selectedTariff = new SelectedTariff();
	        selectedTariff.setTariffId(tariff.getTariffId());
	        selectedTariffs.add(selectedTariff);

	        totalAmount = totalAmount.add(tariff.getMonthlyPrice());
	    }

	    application.setSelectedTariffs(selectedTariffs);
	    application.setTotalAmount(totalAmount);

	    applicationRepository.save(application);
}

public void saveBankDetails(UUID applicationId,BankDetailsRequest request) {

    InsuranceApplication application = applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Application not found"));
    ensureApplicationIsInProgress(application);
    BankDetails bankDetails = new BankDetails();
    bankDetails.setIban(request.getIban());
    bankDetails.setAccountHolder(request.getAccountHolder());

    application.setBankDetails(bankDetails);

    applicationRepository.save(application);
}

public SummaryResponse getSummary(UUID applicationId) {

    InsuranceApplication application = applicationRepository
            .findById(applicationId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Application not found"));
    SummaryResponse response = new SummaryResponse();

    response.setApplicationId(application.getApplicationId());
    response.setStatus(application.getStatus());
    response.setBasicInformation(application.getBasicInformation());
    response.setSelectedTariffs(application.getSelectedTariffs());
    response.setBankDetails(application.getBankDetails());
    response.setTotalAmount(application.getTotalAmount());

    return response;
}

public void submitApplication(UUID applicationId) {

	InsuranceApplication application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

	if (application.getStatus() != ApplicationStatus.IN_PROGRESS) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only applications in progress can be submitted");
	}

	if (application.getBasicInformation() == null) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Basic information is missing");
	}

	if (application.getSelectedTariffs() == null || application.getSelectedTariffs().isEmpty()) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one tariff must be selected");
	}

	if (application.getBankDetails() == null) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank details are missing");
	}

	if (application.getTotalAmount() == null) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Total amount has not been calculated");
	}

	application.setStatus(ApplicationStatus.SUBMITTED);

	applicationRepository.save(application);
}

private void ensureApplicationIsInProgress(
        InsuranceApplication application) {

    if (application.getStatus() != ApplicationStatus.IN_PROGRESS) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Only applications in progress can be modified");
    }
}

}
