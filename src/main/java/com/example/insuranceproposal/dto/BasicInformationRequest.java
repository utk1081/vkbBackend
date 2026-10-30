package com.example.insuranceproposal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BasicInformationRequest {

	@NotBlank(message = "First name is required")
	private String vorname;

	@NotBlank(message = "Last name is required")
	private String nachname;

	@NotBlank(message = "Date of birth is required")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of birth must use YYYY-MM-DD format")
	private String dateOfBirths;

	@NotBlank(message = "Profession is required")
	private String beruf;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	private String email;
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getDateOfBirths() {
		return dateOfBirths;
	}
	public void setDateOfBirths(String dateOfBirths) {
		this.dateOfBirths = dateOfBirths;
	}
	public String getBeruf() {
		return beruf;
	}
	public void setBeruf(String beruf) {
		this.beruf = beruf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
