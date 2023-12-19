package com.lgaertner.accounting.domain.dto;

public enum AccountTypeB implements AccountTypeInterface {
	LARGE("large"),
	SMALL("small");

	String formattedName;
	AccountTypeB(String name) {
		formattedName = name;
	}
}
