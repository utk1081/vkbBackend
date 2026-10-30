/**
 * 
 */
package com.example.insuranceproposal;

import java.util.List;

public class TestJavaFeatures {

	public TestJavaFeatures() {
	}

	public static void main(String[] args) {
List<String> ids=List.of("TARIFF-101", "TARIFF-205");
List<String> loweredCaseId=ids.stream().map(id -> id.toLowerCase()).toList();
System.out.println(loweredCaseId);
	}

}
