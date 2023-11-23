package com.lgaertner.accounting.repository.hibernate;

import org.hibernate.Session;

import com.lgaertner.accounting.domain.accounts.Account;
import com.lgaertner.accounting.domain.gateway.repositories.AccountsRepository;
import com.lgaertner.accounting.repository.hibernate.entities.AccountEntity;

public class AccountsRepositoryHibernateImpl implements AccountsRepository {

	private Session session;

	public AccountsRepositoryHibernateImpl(Session session) {
		this.session = session;
	}

	@Override
	public AccountEntity save(Account account) {
		var entity = new AccountEntity(account); 
		session.persist(entity);
		return entity;
	}

	public AccountEntity save(AccountEntity account) {
		session.persist(account);
		return account;
	}

	@Override
	public AccountEntity find(int id) {
		return session.createQuery(
				"SELECT a FROM AccountEntity a WHERE a.accountId = :id", AccountEntity.class)
			.setParameter("id", id)
			.getResultList().stream().findFirst().orElseThrow();
	}

    public void dropAll() {
		session.createQuery("DELETE FROM AccountEntity").executeUpdate();
    }
}
