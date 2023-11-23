package com.lgaertner.accounting.domain.gateway.repositories;

import com.lgaertner.accounting.domain.accounts.Account;

public interface AccountsRepository {
	Account save(Account account);
	Account find(int id);
}
