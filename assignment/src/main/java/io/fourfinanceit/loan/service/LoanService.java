package io.fourfinanceit.loan.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.model.LoanRepository;
import io.fourfinanceit.loan.risk.RiskManager;

@Transactional
@Service
public class LoanService {

	Logger logger = LoggerFactory.getLogger(LoanService.class);

	private ClientRepository clientRepo;
	private LoanRepository loanRepo;
	private RiskManager riskManager;

	@Autowired
	public LoanService(ClientRepository clientService, LoanRepository loanRepository, RiskManager riskManager) {
		this.clientRepo = clientService;
		this.loanRepo = loanRepository;
		this.riskManager = riskManager;
	}

	public Loan findLoanById(Long loanId) {
		Loan loan = loanRepo.findById(loanId);
		if (loan == null) {
			throw new ResourceNotFoundException("Missing Loan with id: " + loanId);
		}

		logger.debug("Loaded loan: {}", loan);
		return loan;
	}

	public Long createNewLoan(Loan loan, long clientId) throws HighRiskException {
		Preconditions.checkArgument(loan != null);

		Loan enrichedLoan = enrichLoan(loan, clientId);
		riskManager.visitValidationRules(loan);
		Loan createdLoan = loanRepo.save(enrichedLoan);
		logger.info("Created new loan: {}", createdLoan);

		return createdLoan.getId();
	}

	public List<Loan> findLoansByClientId(Long clientId) {
		Preconditions.checkArgument(clientId != null);

		Client client = clientRepo.findById(clientId);
		return client.getLoans();
	}

	private Loan enrichLoan(Loan loan, long clientId) {
		Client client = clientRepo.findById(clientId);
		loan.setClient(client);

		return loan;
	}
}
