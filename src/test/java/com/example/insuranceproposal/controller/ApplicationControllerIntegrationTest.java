package com.example.insuranceproposal.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void acceptsFrontendPersonalInformationDateFormat() throws Exception {
		MvcResult createdApplication = mockMvc.perform(post("/applications"))
				.andExpect(status().isOk())
				.andReturn();
		String applicationId = createdApplication.getResponse().getContentAsString().replace("\"", "").trim();

		mockMvc.perform(post("/applications/" + applicationId + "/basic-information")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "vorname": "Max",
								  "nachname": "Mustermann",
								  "dateOfBirths": "1990-01-15",
								  "beruf": "Tester",
								  "email": "max@example.com"
								}
								"""))
				.andExpect(status().isOk());

		mockMvc.perform(get("/applications/" + applicationId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.basicInformation.dateOfBirth").value("1990-01-15"));
	}

	@Test
	void returnsHelpfulErrorForInvalidDate() throws Exception {
		MvcResult createdApplication = mockMvc.perform(post("/applications"))
				.andExpect(status().isOk())
				.andReturn();
		String applicationId = createdApplication.getResponse().getContentAsString().replace("\"", "").trim();

		mockMvc.perform(post("/applications/" + applicationId + "/basic-information")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "vorname": "Max",
								  "nachname": "Mustermann",
								  "dateOfBirths": "1990-02-31",
								  "beruf": "Tester",
								  "email": "max@example.com"
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(
						"Date of birth must be a valid calendar date in YYYY-MM-DD format."));
	}

	@Test
	void savesSelectedTariffsInTheFrontendRequestShape() throws Exception {
		MvcResult createdApplication = mockMvc.perform(post("/applications"))
				.andExpect(status().isOk())
				.andReturn();
		String applicationId = createdApplication.getResponse().getContentAsString().replace("\"", "").trim();

		mockMvc.perform(get("/tariffs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].tariffId").exists())
				.andExpect(jsonPath("$[0].monthlyPrice").exists());

		mockMvc.perform(post("/applications/" + applicationId + "/tariffs")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"selectedTariffIds":["HEALTH_BASIC"]}
								"""))
				.andExpect(status().isOk());

		mockMvc.perform(get("/applications/" + applicationId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.selectedTariffs[0].tariffId").value("HEALTH_BASIC"));
	}
}
