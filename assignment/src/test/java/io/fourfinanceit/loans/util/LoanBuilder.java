package io.fourfinanceit.loans.util;

import java.math.BigInteger;
import java.time.LocalDateTime;

import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.Loan;

public class LoanBuilder {
	
	private Loan loan;
	
	public LoanBuilder() {
		loan = new Loan();
	}
	
	public LoanBuilder amount(BigInteger amount) {
		loan.setAmount(amount);
		return this;
	}
	
	public LoanBuilder term(int term) {
		loan.setTerm(term);
		return this;
	}
	
	public LoanBuilder start(LocalDateTime start) {
		loan.setStart(start);
		return this;
	}

	public LoanBuilder client(Client client) {
		loan.setClient(client);
		return this;
	}

	public LoanBuilder id(long id) {
		loan.setId(id);
		return this;
	}
	
	public LoanBuilder ipAddress(String ipAddress) {
		loan.setIpAddress(ipAddress);
		return this;
	}
	
	public Loan build() {
		return loan;
	}
}
