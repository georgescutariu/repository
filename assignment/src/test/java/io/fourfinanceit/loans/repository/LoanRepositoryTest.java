package io.fourfinanceit.loans.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.fourfinanceit.loan.AssignmentApplication;
import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.model.LoanRepository;
import io.fourfinanceit.loans.util.ClientBuilder;
import io.fourfinanceit.loans.util.LoanBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AssignmentApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class LoanRepositoryTest {

	@Autowired
	private LoanRepository loanRepo;

	@Autowired
	private ClientRepository clientRepo;

	Client client;

	@Before
	public void init() {
		if (client == null) {
			client = new ClientBuilder().firstName("Bob").lastName("Smit").build();
			Client secondClient = new ClientBuilder().firstName("Bob").lastName("Smit").build();
			clientRepo.save(client);
			clientRepo.save(secondClient);

			Loan okloan1 = new LoanBuilder().client(client).ipAddress("127.0.0.1").amount(BigInteger.valueOf(800))
					.term(12).start(LocalDateTime.now()).build();
			Loan okloan2 = new LoanBuilder().client(client).ipAddress("127.0.0.1").amount(BigInteger.valueOf(801))
					.term(12).start(LocalDateTime.now()).build();
			Loan loanOld = new LoanBuilder().client(client).ipAddress("127.0.0.1").amount(BigInteger.valueOf(802))
					.term(12).start(LocalDateTime.now().minusDays(3)).build();
			Loan loanDifferentIp = new LoanBuilder().client(client).ipAddress("127.0.0.2")
					.amount(BigInteger.valueOf(803)).term(12).start(LocalDateTime.now()).build();
			Loan loanDifferentClient = new LoanBuilder().client(secondClient).ipAddress("127.0.0.1")
					.amount(BigInteger.valueOf(804)).term(12).start(LocalDateTime.now()).build();
			loanRepo.save(okloan1);
			loanRepo.save(okloan2);
			loanRepo.save(loanOld);
			loanRepo.save(loanDifferentIp);
			loanRepo.save(loanDifferentClient);
		}
	}

	@Test
	public void testCountByClientAndIpAddressAndStartAfter() throws Exception {

		int count = loanRepo.countByClientAndIpAddressAndStartAfter(client, "127.0.0.1",
				LocalDateTime.now().minusDays(1));
		loanRepo.findAll();
		assertEquals(2, count);
	}
}
