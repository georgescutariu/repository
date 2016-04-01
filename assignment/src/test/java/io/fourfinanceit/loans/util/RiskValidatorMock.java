package io.fourfinanceit.loans.util;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.risk.RiskValidator;

public class RiskValidatorMock implements RiskValidator {
	
	private boolean visited = false;

	@Override
	public void validate(Loan loan) throws HighRiskException {
		visited = true;
	}

	public boolean isVisited() {
		return visited;
	}
}
