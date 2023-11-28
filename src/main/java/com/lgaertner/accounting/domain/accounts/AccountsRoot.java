package com.lgaertner.accounting.domain.accounts;

import com.lgaertner.accounting.domain.Transaction;
import com.lgaertner.accounting.domain.gateway.repositories.AccountsRepository;

public class AccountsRoot {

	private AccountsRepository repository;
	private Transaction transaction;

	public AccountsRoot(AccountsRepository repository, Transaction transaction) {
		this.repository = repository;
		this.transaction = transaction;
	}

	public void transfer(int sourceAccountId, int targetAccountId, int amount) {
		
		var sourceAccount = repository.find(sourceAccountId);
		var targetAccount = repository.find(targetAccountId);

		transaction.execute(() -> {
			targetAccount.add(amount);
			sourceAccount.add(-amount);
		});
	}

    public void addAccount(int id, int balance) {
		transaction.execute(() -> {
			repository.save(new Account(id, balance));
		});
    }
}
