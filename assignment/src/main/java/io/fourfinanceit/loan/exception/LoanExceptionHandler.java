package io.fourfinanceit.loan.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.fourfinanceit.loan.model.response.RejectResponse;

@ControllerAdvice
public class LoanExceptionHandler {
	Logger logger = LoggerFactory.getLogger(LoanExceptionHandler.class);

    @ExceptionHandler(HighRiskException.class)
    public ResponseEntity<RejectResponse> handleHighRiskException(HighRiskException ex) {
    	logger.info(ex.getMessage());
    	RejectResponse error = new RejectResponse(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RejectResponse> handleHighRiskException(ResourceNotFoundException ex) {
    	logger.info(ex.getMessage());
    	RejectResponse error = new RejectResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, error.getHttpStatus());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RejectResponse> handleException(Exception ex) {
    	logger.info(ex.getMessage());
    	RejectResponse error = new RejectResponse("Not Available!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, error.getHttpStatus());
    }
}
