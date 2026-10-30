package com.example.insuranceproposal.repository;

import java.util.Optional;
import java.util.UUID;

import com.example.insuranceproposal.domain.InsuranceApplication;

public interface ApplicationRepository {

	void save(InsuranceApplication application);
	Optional<InsuranceApplication> findById(UUID uuid);
}
