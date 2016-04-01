package io.fourfinanceit.loans.risk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

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
import io.fourfinanceit.loan.risk.NightRisk;
import io.fourfinanceit.loans.util.ClientBuilder;
import io.fourfinanceit.loans.util.LoanBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AssignmentApplication.class, loader = SpringApplicationContextLoader.class)
public class NightRiskTest {
	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private NightRisk risk;

	Client client = new ClientBuilder().firstName("Bob").lastName("Smit").build();

	@Test
	public void testIsHighRisk() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(8000);

		boolean isRisk = risk.isHighRisk(loan);
		assertTrue("Sould be high risk", isRisk);
	}

	@Test
	public void testIsNotHighRiskBeforeMidhight() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(8000);
		loan.setStart(LocalTime.MIDNIGHT.minusMinutes(1).atDate(LocalDate.now()));

		boolean isRisk = risk.isHighRisk(loan);
		assertFalse("Sould NOT be high risk", isRisk);
	}

	@Test
	public void testIsNotHighRiskAfter6() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(8000);
		loan.setStart(LocalTime.MIDNIGHT.plusHours(6).plusMinutes(1).atDate(LocalDate.now()));

		boolean isRisk = risk.isHighRisk(loan);
		assertFalse("Sould NOT be high risk", isRisk);
	}

	@Test
	public void testIsNotHighRiskNotMaxAmount() throws Exception {
		Loan loan = getLoanWithClient();
		loan.setId(8000);
		loan.setAmount(BigInteger.valueOf(999));

		boolean isRisk = risk.isHighRisk(loan);
		assertFalse("Sould NOT be high risk", isRisk);
	}

	private Loan getLoanWithClient() {
		Client client = new ClientBuilder().firstName("Bob").lastName("Smit").build();
		clientRepo.save(client);
		Loan loan = new LoanBuilder().client(client).ipAddress("127.0.0.1").amount(BigInteger.valueOf(1000)).term(12)
				.start(LocalTime.MIDNIGHT.plusMinutes(1).atDate(LocalDate.now())).build();
		return loan;
	}
}
