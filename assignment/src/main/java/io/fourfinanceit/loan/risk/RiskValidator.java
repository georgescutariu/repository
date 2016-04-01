package io.fourfinanceit.loan.risk;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Loan;

public interface RiskValidator {

    void validate(Loan loan) throws HighRiskException;
}
