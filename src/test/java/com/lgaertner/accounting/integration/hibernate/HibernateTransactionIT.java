package com.lgaertner.accounting.integration.hibernate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lgaertner.accounting.domain.accounts.AccountsRoot;
import com.lgaertner.accounting.repository.hibernate.AccountsRepositoryHibernateImpl;
import com.lgaertner.accounting.repository.hibernate.TransactionHibernateImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateTransactionIT {

	private TransactionHibernateImpl transaction;
	private AccountsRepositoryHibernateImpl repository;

	@BeforeEach
	public void setup() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.lgaertner.accounting");
		EntityManager em = emf.createEntityManager();

		transaction = new TransactionHibernateImpl(em.unwrap(Session.class));
		repository = new AccountsRepositoryHibernateImpl(em.unwrap(Session.class));

		transaction.execute(() -> repository.dropAll());
	}

	@Test
	public void shouldExecuteTransfer() {
		var managedAccount = new AccountsRoot(repository, transaction);

		managedAccount.addAccount(1, 50);
		managedAccount.addAccount(2, 100);

		assertDoesNotThrow(() -> managedAccount.transfer(1, 2, 30));

		var acc1 = repository.find(1);
		assertEquals(20, acc1.getBalance());

		var acc2 = repository.find(2);
		assertEquals(130, acc2.getBalance());
	}

	@Test
	public void shouldNotExecuteTransferIfBalancesReducedToLEZero() {
		var managedAccount = new AccountsRoot(repository, transaction);

		managedAccount.addAccount(1, 50);
		managedAccount.addAccount(2, 100);

		assertThrows(Exception.class, () -> managedAccount.transfer(1, 2, 60));
		
		var acc1 = repository.find(1);
		assertEquals(50, acc1.getBalance());

		var acc2 = repository.find(2);
		assertEquals(100, acc2.getBalance());
	}

	@Test
	public void shouldOnlyExecuteFirstTransactionIfSecondReducesBalanceToLEZero() {
		var managedAccount = new AccountsRoot(repository, transaction);

		managedAccount.addAccount(1, 50);
		managedAccount.addAccount(2, 100);

		assertDoesNotThrow(() -> managedAccount.transfer(1, 2, 30));
		assertThrows(Exception.class, () -> managedAccount.transfer(1, 2, 30));
		
		var acc1 = repository.find(1);
		assertEquals(20, acc1.getBalance());

		var acc2 = repository.find(2);
		assertEquals(130, acc2.getBalance());
	}
}
