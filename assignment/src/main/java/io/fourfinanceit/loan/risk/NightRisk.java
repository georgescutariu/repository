package io.fourfinanceit.loan.risk;

import java.math.BigInteger;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.util.DateUtil;

@Component
public class NightRisk implements RiskValidator {

	Logger logger = LoggerFactory.getLogger(NightRisk.class);

	private BigInteger maxAmout;

	@Autowired
	public NightRisk(@Value("${loan.max.amount}") BigInteger maxAmout) {
		this.maxAmout = maxAmout;
	}

	@Override
	public void validate(Loan loan) throws HighRiskException {
		if (isHighRisk(loan)) {
			logger.info("Refused loan: {}" + loan);
			throw new HighRiskException("Refused loan: attempt to take loan is made between " + DateUtil.NIGHT_START
					+ " to " + DateUtil.NIGHT_END + " AM with maximum possible amount.");
		}
			
		logger.info("Accepted loan: {}" + loan);
	}

	public boolean isHighRisk(Loan loan) {
		LocalTime time = loan.getStart().toLocalTime();
		BigInteger amont = loan.getAmount();
		return isNightTime(time) && isOverMaxAmount(amont);
	}

	private boolean isNightTime(LocalTime time) {
		return time.isAfter(DateUtil.NIGHT_START) && time.isBefore(DateUtil.NIGHT_END);
	}

	private boolean isOverMaxAmount(BigInteger amont) {
		return amont.compareTo(maxAmout) >= 0;
	}
}
