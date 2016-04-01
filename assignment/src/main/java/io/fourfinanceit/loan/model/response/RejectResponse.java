package io.fourfinanceit.loan.model.response;

import org.springframework.http.HttpStatus;

public class RejectResponse {

	private HttpStatus httpStatus;
	
    private String message;

    public RejectResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
