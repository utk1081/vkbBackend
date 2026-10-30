package com.example.insuranceproposal.dto;

import jakarta.validation.constraints.NotBlank;

public class BankDetailsRequest {
	@NotBlank(message = "IBAN is required")
	 private String iban;
	@NotBlank(message = "Account holder is required")
	 private String accountHolder;
	 
	 public String getIban() {
		 return iban;
	 }
	 public void setIban(String iban) {
		 this.iban = iban;
	 }
	 public String getAccountHolder() {
		 return accountHolder;
	 }
	 public void setAccountHolder(String accountHolder) {
		 this.accountHolder = accountHolder;
	 }
}
