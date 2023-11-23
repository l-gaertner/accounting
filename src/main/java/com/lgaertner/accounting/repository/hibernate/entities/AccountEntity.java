package com.lgaertner.accounting.repository.hibernate.entities;

import com.lgaertner.accounting.domain.accounts.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountEntity extends Account {

	public AccountEntity () {
		super(0, 0);
	}

	public AccountEntity (int a, int b) {
		super(a, b);
	}

	public AccountEntity(Account account) {
		super(account);
	}

	@Id
	@Column(name="accountId")
	protected int getAccountId() {
		return accountId;
	}

	@Id
	@Column(name="accountId")
	protected void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Column(name="balance")
	public int getBalance() {
		return balance;
	}

	@Column(name="balance")
	protected void setBalance(int balance) {
		this.balance = balance;
	}
	
}
