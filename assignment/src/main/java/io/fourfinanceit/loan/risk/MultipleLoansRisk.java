package io.fourfinanceit.loan.risk;

import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.model.LoanRepository;

@Component
public class MultipleLoansRisk implements RiskValidator {
	Logger logger = LoggerFactory.getLogger(MultipleLoansRisk.class);

	private int maximumLoans;

	private LoanRepository loanRepository;

	@Autowired
	public MultipleLoansRisk(@Value("${loan.max.number}") int maximumLoans, LoanRepository loanRepository) {
		this.maximumLoans = maximumLoans;
		this.loanRepository = loanRepository;
	}

	@Override
	public void validate(Loan loan) throws HighRiskException {
		if (isHighRisk(loan)) {
			logger.error("High risk loan detected: {}", loan);
			throw new HighRiskException("Refused loan: client reached the maximum number of applications ("
					+ maximumLoans + ") per day, from a single IP.");
		}
		
		logger.info("Accepted loan: {}" + loan);
	}

	public boolean isHighRisk(Loan loan) {
		int currentLoanCount = loanRepository.countByClientAndIpAddressAndStartAfter(loan.getClient(),
				loan.getIpAddress(), loan.getStart().truncatedTo(ChronoUnit.HOURS));
		return currentLoanCount >= maximumLoans;
	}
}
