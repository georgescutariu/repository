package io.fourfinanceit.loan.exception;

public class HighRiskException extends Exception {

	private static final long serialVersionUID = 1L;

	public HighRiskException(String message) {
        super(message);
    }
}
