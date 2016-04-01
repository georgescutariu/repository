package io.fourfinanceit.loan.risk;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.exception.LoanExceptionHandler;
import io.fourfinanceit.loan.model.Loan;

@Component
public class RiskManager {
	
	Logger logger = LoggerFactory.getLogger(LoanExceptionHandler.class);

    private List<RiskValidator> rules;

    @Autowired
    public RiskManager(List<RiskValidator> rules) {
        this.rules = rules;
        logger.debug("Active validation rules: {}", rules);
    }
    
    public void addRule(RiskValidator rule) {
    	 this.rules.add(rule);
    	logger.debug("Added new validation rule: {}", rule);
    }
    
    public void visitValidationRules(Loan loan) throws HighRiskException {
        for (RiskValidator rule : rules) {
            rule.validate(loan);
        }
    }
}
