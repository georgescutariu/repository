package io.fourfinanceit.loans.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.fourfinanceit.loan.AssignmentApplication;
import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.model.LoanRepository;
import io.fourfinanceit.loan.service.LoanService;
import io.fourfinanceit.loans.util.ClientBuilder;
import io.fourfinanceit.loans.util.LoanBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AssignmentApplication.class, loader = SpringApplicationContextLoader.class)
public class LoanServiceTest {
	
	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanRepository loanRepo;

	@Autowired
	private ClientRepository clientRepo;

	Client client;
	
	Loan loan;

	@Before
	public void init() {
		if (client == null) {
			client = new ClientBuilder().firstName("Bob").lastName("Smit").build();
			client = clientRepo.save(client);
			Loan loan = new LoanBuilder().client(client).ipAddress("127.0.0.1").amount(BigInteger.valueOf(900))
					.term(12).start(LocalDateTime.now()).build();
			loanRepo.save(loan);
		}
	}

	@Test
	public void testCreateLoan() throws Exception {
		
		Loan toCreateLoan = new LoanBuilder().ipAddress("127.0.0.1").amount(BigInteger.valueOf(910))
				.term(12).start(LocalDateTime.now()).build();

		long loanId = loanService.createNewLoan(toCreateLoan, client.getId());
		
		Loan newCreatedLoan = loanRepo.findById(loanId);
		
		assertNotNull("Found Loan", newCreatedLoan);
	}
	
	@Test
	public void testFindLoansByClientId() throws Exception {

		List<Loan> loanIds = loanService.findLoansByClientId(client.getId());
		
		assertEquals("Found Loan", 1, loanIds.size());
	}
}
