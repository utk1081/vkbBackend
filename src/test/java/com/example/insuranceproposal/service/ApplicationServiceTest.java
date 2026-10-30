package com.example.insuranceproposal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.insuranceproposal.domain.ApplicationStatus;
import com.example.insuranceproposal.domain.InsuranceApplication;
import com.example.insuranceproposal.dto.TariffSelectionRequest;
import com.example.insuranceproposal.repository.ApplicationRepository;
import com.example.insuranceproposal.tariff.Tariff;
import com.example.insuranceproposal.tariff.TariffClient;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private TariffClient tariffClient;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void startApplication_shouldCreateAndSaveApplication() {
        ArgumentCaptor<InsuranceApplication> applicationCaptor = ArgumentCaptor.forClass(InsuranceApplication.class);

        UUID applicationId = applicationService.startApplication();
        System.out.println("Created UUID: " + applicationId);

        assertNotNull(applicationId);
        verify(applicationRepository).save(applicationCaptor.capture());

        InsuranceApplication savedApplication = applicationCaptor.getValue();
        assertEquals(applicationId, savedApplication.getApplicationId());
        assertEquals(ApplicationStatus.IN_PROGRESS, savedApplication.getStatus());
    }

    @Test
    void saveTariffSelection_shouldCalculateTotalAmount() {
        UUID applicationId = UUID.randomUUID();
        InsuranceApplication application = new InsuranceApplication();
        application.setApplicationId(applicationId);
        application.setStatus(ApplicationStatus.IN_PROGRESS);

        Tariff healthBasic = new Tariff();
        healthBasic.setTariffId("HEALTH_BASIC");
        healthBasic.setMonthlyPrice(new BigDecimal("80.00"));

        Tariff dentalPlus = new Tariff();
        dentalPlus.setTariffId("DENTAL_PLUS");
        dentalPlus.setMonthlyPrice(new BigDecimal("15.00"));

        TariffSelectionRequest request = new TariffSelectionRequest();
        request.setSelectedTariffIds(List.of("HEALTH_BASIC", "DENTAL_PLUS"));

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(tariffClient.findById("HEALTH_BASIC")).thenReturn(Optional.of(healthBasic));
        when(tariffClient.findById("DENTAL_PLUS")).thenReturn(Optional.of(dentalPlus));

        applicationService.saveTariffSelection(applicationId, request);

        assertEquals(new BigDecimal("95.00"), application.getTotalAmount());
        assertEquals(2, application.getSelectedTariffs().size());
        assertEquals("HEALTH_BASIC", application.getSelectedTariffs().get(0).getTariffId());
        assertEquals("DENTAL_PLUS", application.getSelectedTariffs().get(1).getTariffId());
        verify(applicationRepository).save(application);
    }

    @Test
    void saveTariffSelection_shouldRejectUnknownTariff() {
        UUID applicationId = UUID.randomUUID();
        InsuranceApplication application = new InsuranceApplication();
        application.setApplicationId(applicationId);
        application.setStatus(ApplicationStatus.IN_PROGRESS);

        TariffSelectionRequest request = new TariffSelectionRequest();
        request.setSelectedTariffIds(List.of("UNKNOWN_TARIFF"));

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(tariffClient.findById("UNKNOWN_TARIFF")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> applicationService.saveTariffSelection(applicationId, request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Unknown tariff: UNKNOWN_TARIFF", exception.getReason());
        verify(applicationRepository, never()).save(any(InsuranceApplication.class));
    }
}