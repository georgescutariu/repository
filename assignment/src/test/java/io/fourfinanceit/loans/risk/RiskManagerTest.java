package io.fourfinanceit.loans.risk;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.risk.RiskManager;
import io.fourfinanceit.loan.risk.RiskValidator;
import io.fourfinanceit.loans.util.LoanBuilder;
import io.fourfinanceit.loans.util.RiskValidatorMock;

public class RiskManagerTest {
	
	RiskValidatorMock riskValidator = new RiskValidatorMock();
	List<RiskValidator> riskValidators = new ArrayList<RiskValidator>();
	RiskManager riskManager = new RiskManager(riskValidators);
	Loan loan = new LoanBuilder().amount(BigInteger.valueOf(800)).term(12).build();
	
	@Test
	public void testValidators() throws HighRiskException {
		riskValidators.add(riskValidator);
		riskManager.visitValidationRules(loan);
		assertTrue("Visided Validator", riskValidator.isVisited());
	}

}
