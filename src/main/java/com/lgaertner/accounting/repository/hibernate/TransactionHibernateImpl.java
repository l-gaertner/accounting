package com.lgaertner.accounting.repository.hibernate;

import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;

import com.lgaertner.accounting.domain.Transaction;

public class TransactionHibernateImpl implements Transaction {

	Session session;

	public TransactionHibernateImpl(Session session) {
		this.session = session;
	}

	@Override
	public <A> void execute(Consumer<A> function, A parameter) {
		session.getTransaction().begin();
		try {
			function.accept(parameter);
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw(e);
		}
		session.getTransaction().commit();
	}

	@Override
	public <A, B> B execute(Function<A, B> function, A parameter) {
		B returnValue;

		session.getTransaction().begin();
		try {
			returnValue = function.apply(parameter); 
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw(e);
		}
		session.getTransaction().commit();

		return returnValue;
	}

	@Override
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
