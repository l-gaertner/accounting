package com.lgaertner.accounting.integration.hibernate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lgaertner.accounting.domain.accounts.AccountsRoot;
import com.lgaertner.accounting.repository.hibernate.AccountsRepositoryHibernateImpl;
import com.lgaertner.accounting.repository.hibernate.TransactionHibernateImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateConcurrencyIT {

	private TransactionHibernateImpl transaction;
	private AccountsRepositoryHibernateImpl repository;

	@BeforeAll
	public void setup() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.lgaertner.accounting");
		EntityManager em = emf.createEntityManager();

		transaction = new TransactionHibernateImpl(em.unwrap(Session.class));
		repository = new AccountsRepositoryHibernateImpl(em.unwrap(Session.class));

		transaction.execute(() -> repository.dropAll());
	}
}
