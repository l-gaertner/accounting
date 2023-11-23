package com.lgaertner.accounting.integration.hibernate;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import com.lgaertner.accounting.domain.accounts.AccountsRoot;
import com.lgaertner.accounting.repository.hibernate.AccountsRepositoryHibernateImpl;
import com.lgaertner.accounting.repository.hibernate.TransactionHibernateImpl;
import com.lgaertner.accounting.repository.hibernate.entities.AccountEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ManagedAccountsIT {


	@Test
	public void testTransfer() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.lgaertner.accounting");
		EntityManager em = emf.createEntityManager();

		TransactionHibernateImpl transaction = new TransactionHibernateImpl(em.unwrap(Session.class));

		var repository = new AccountsRepositoryHibernateImpl(em.unwrap(Session.class));
		transaction.execute(() -> repository.dropAll());
		var managedAccount = new AccountsRoot(repository, transaction);

		transaction.execute(() -> {
		managedAccount.addAccount(1, 50);
		managedAccount.addAccount(2, 100);
		});

		try {
		managedAccount.transfer(1, 2, 30);
		managedAccount.transfer(1, 2, 30);
		} catch (Exception e) {
		}
		
		var acc1 = repository.find(1);
		var acc2 = repository.find(2);

		System.out.println("acc1 balance:" + acc1.getBalance());
		System.out.println("acc2 balance:" + acc2.getBalance());
	}
}
