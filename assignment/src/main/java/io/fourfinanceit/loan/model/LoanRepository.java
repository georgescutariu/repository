package io.fourfinanceit.loan.model;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long> {

	Loan findById(long id);
	
	int countByClientAndIpAddressAndStartAfter(Client client, String ipAddress, LocalDateTime start);
}
