package com.lgaertner.accounting.domain.accounts;

public class Account {

	protected int accountId;
	protected int balance;

	public Account(int accountId, int balance) {
		this.accountId = accountId;
		this.balance = balance;
	}

	public Account(Account source) {
		this.accountId = source.accountId;
		this.balance = source.balance;
	}

	void add (int amount) {
		if (amount + balance > 0) {
			balance += amount;
		} else {
			throw new RuntimeException("can't extract more than existing, final balance: " + (amount + balance));
		}
	}
	
}
