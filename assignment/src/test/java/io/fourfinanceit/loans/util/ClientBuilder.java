package io.fourfinanceit.loans.util;

import java.util.List;

import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.Loan;

public class ClientBuilder {
	
	private Client client;
	
	public ClientBuilder() {
		client = new Client();
	}
	
	public ClientBuilder firstName(String firstName) {
		client.setFirstName(firstName);
		return this;
	}
	
	public ClientBuilder lastName(String lastName) {
		client.setLastName(lastName);
		return this;
	}

	public ClientBuilder loans(List<Loan> loans) {
		client.setLoans(loans);
		return this;
	}

	public ClientBuilder id(long id) {
		client.setId(id);
		return this;
	}
	
	public Client build() {
		return client;
	}
}
