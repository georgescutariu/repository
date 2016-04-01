package io.fourfinanceit.loans.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.util.DateUtil;
import io.fourfinanceit.loans.util.ClientBuilder;
import io.fourfinanceit.loans.util.LoanBuilder;

public class LoanControllerTest extends ControllerTest {
	
	@Autowired
	private ClientRepository clientRepo;

	Long newClientId;
	
	@Before
	@Override
	public void init() throws Exception {
		super.init();
		if (newClientId == null) {
			Client newClient = new ClientBuilder().firstName("Bob").lastName("Smit").build();
			newClientId = clientRepo.save(newClient).getId();
		}
	}

	@Test
	public void testCreateNewLoanIntegration() throws Exception {
		Loan loan = new LoanBuilder().amount(BigInteger.valueOf(800)).term(12).build();
		
		mockMvc.perform(post("/clients/" + newClientId + "/loans").contentType(CONTENT_TYPE).content(gson.toJson(loan)))
				.andExpect(status().isCreated());
		
		Long loandID = clientRepo.findById(newClientId).getLoans().get(0).getId();
		
		mockMvc.perform(get("/clients/" + newClientId + "/loans/" + loandID))
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(loandID.intValue())))
				.andExpect(jsonPath("$.start", startsWith(DateUtil.formatDate(LocalDateTime.now()))))
				.andExpect(jsonPath("$.ipAddress", is("127.0.0.1")));
	}

	@Test
	public void testCreateNewLoanMissingTerm() throws Exception {
		Loan loan = new LoanBuilder().amount(BigInteger.valueOf(800)).build();

		mockMvc.perform(post("/clients/1/loans").contentType(CONTENT_TYPE).content(gson.toJson(loan)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreateNewLoanMissingAmount() throws Exception {
		Loan loan = new LoanBuilder().term(12).build();

		mockMvc.perform(post("/clients/1/loans").contentType(CONTENT_TYPE).content(gson.toJson(loan)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreateNewLoanMissingClient() throws Exception {
		Loan loan = new LoanBuilder().term(12).build();

		mockMvc.perform(post("/clients/500/loans").contentType(CONTENT_TYPE).content(gson.toJson(loan)))
				.andExpect(status().isBadRequest());
	}
}