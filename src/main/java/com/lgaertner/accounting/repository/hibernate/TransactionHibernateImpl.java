package com.lgaertner.accounting.repository.hibernate;

import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;

import com.lgaertner.accounting.domain.Transaction;

import jakarta.persistence.EntityManager;

public class TransactionHibernateImpl implements Transaction {

	Session session;

	public TransactionHibernateImpl(Session session) {
		this.session = session;
	}

	@Override
	@jakarta.transaction.Transactional
	public <A> void execute(Consumer<A> function, A parameter) {
		// var transaction = session.getTransaction();
		// transaction.begin();
		function.accept(parameter);
		// transaction.commit();
	}

	@Override
	@jakarta.transaction.Transactional
	public <A, B> B execute(Function<A, B> function, A parameter) {
		// var transaction = session.getTransaction();
		// transaction.begin();
		var returnValue = function.apply(parameter); 
		// transaction.commit();
		return returnValue;
	}

	@Override
	// @jakarta.transaction.Transactional
	public void execute(Runnable function) {
		session.getTransaction().begin();
		try {
		function.run();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw(e);
		}
		session.getTransaction().commit();
	} 

	
}
