package com.lgaertner.accounting.domain;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Transaction {
	void execute(Runnable function);
	
	<A> void execute(Consumer<A> function, A parameter);

	<A,B > B execute(Function<A,B> function, A parameter);
}
