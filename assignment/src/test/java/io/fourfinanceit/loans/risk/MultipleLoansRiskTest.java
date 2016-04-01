package io.fourfinanceit.loans.risk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.fourfinanceit.loan.AssignmentApplication;
import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.model.LoanRepository;
import io.fourfinanceit.loan.risk.MultipleLoansRisk;
import io.fourfinanceit.loans.util.ClientBuilder;
import io.fourfinanceit.loans.util.LoanBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AssignmentApplication.class, loader = SpringApplicationContextLoader.class)
public class MultipleLoansRiskTest {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private LoanRepository loanRepo;

	@Autowired
	private MultipleLoansRisk risk;

	Client client = new ClientBuilder().firstName("Bob").lastName("Smit").build();

	@Test
	public void testIsHighRisk() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(9000);
		loanRepo.save(loan);
		loan.setId(9001);
		loanRepo.save(loan);
		loan.setId(9002);
		loanRepo.save(loan);
		loan.setId(9003);

		boolean isRisk = risk.isHighRisk(loan);
		assertTrue("Sould be high risk", isRisk);
	}
	
	@Test
	public void testIsNotHighRiskNotLastAttempt() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(9100);
		loanRepo.save(loan);
		loan.setId(9101);
		loanRepo.save(loan);
		loan.setId(9102);

		boolean isRisk = risk.isHighRisk(loan);
		assertFalse("Sould not be high risk", isRisk);
	}
	
	@Test
	public void testIsNotHighRiskDifferntIP() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(9200);
		loanRepo.save(loan);
		loan.setId(9201);
		loanRepo.save(loan);
		loan.setId(9202);
		loanRepo.save(loan);
		loan.setId(9203);
		loan.setIpAddress("127.0.0.10");

		boolean isRisk = risk.isHighRisk(loan);
		assertFalse("Sould not be high risk", isRisk);
	}
	
	@Test(expected=HighRiskException.class)
	public void testVaidatorThrowsException() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(9400);
		loanRepo.save(loan);
		loan.setId(9401);
		loanRepo.save(loan);
		loan.setId(9402);
		loanRepo.save(loan);
		loan.setId(9403);

		risk.validate(loan);
	}
	
	private Loan getLoanWithClient() {
		Client client = new ClientBuilder().firstName("Bob").lastName("Smit").build();
		clientRepo.save(client);
		Loan loan = new LoanBuilder().client(client).ipAddress("127.0.0.1").amount(BigInteger.valueOf(1000)).term(12)
				.start(LocalDateTime.now()).build();
		return loan;
	}
}
