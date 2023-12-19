package com.lgaertner.accounting.domain.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class AccountTypeInterfaceTest {

	@Test
	public void shouldReturnNullForNameOfAccountTypeA() {
		AccountTypeInterface a = AccountTypeA.PRIVATE;
		var nameOfA = a.name();
		assertTrue(nameOfA.equals("PRIVATE"));
	}

	@Test
	public void shouldReturnNameForAccountTypeB() {
		AccountTypeInterface b = AccountTypeB.LARGE;
		var nameOfB = b.name();
		assertTrue(nameOfB.equals("LARGE"));
	}
	
}
