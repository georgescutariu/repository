package io.fourfinanceit.loan.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.fourfinanceit.loan.exception.HighRiskException;
import io.fourfinanceit.loan.model.Loan;
import io.fourfinanceit.loan.model.response.SuccessResponse;
import io.fourfinanceit.loan.service.LoanService;

@RequestMapping("/clients/{clientId}/loans")
@RestController
public class LoanController {

	private LoanService loanService;

	@Autowired
	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<SuccessResponse> createLoan(@PathVariable("clientId") long clientId,
			@Valid @RequestBody Loan loan, HttpServletRequest req) throws HighRiskException {

		enrichLoan(loan, req);
		Long loanId = loanService.createNewLoan(loan, clientId);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{loanId}").buildAndExpand(loanId).toUri());

		SuccessResponse response = new SuccessResponse(loanId);
		return new ResponseEntity<>(response, headers, CREATED);
	}

	@RequestMapping(value = "/{loanId}", method = GET)
	public ResponseEntity<Loan> findLoan(@PathVariable Long clientId, @PathVariable Long loanId) {

		Loan loan = loanService.findLoanById(loanId);
		if (clientId != loan.getClient().getId()) {
			throw new ResourceNotFoundException();
		}
		return new ResponseEntity<>(loan, OK);
	}

	@RequestMapping(value = "", method = GET)
	public ResponseEntity<List<Loan>> findCustomerLoans(@PathVariable("clientId") long clientId) {

		List<Loan> loans = loanService.findLoansByClientId(clientId);
		return new ResponseEntity<>(loans, OK);
	}
	
	private void enrichLoan(Loan loan, HttpServletRequest req) {
		loan.setIpAddress(req.getRemoteAddr());
		loan.setStart(LocalDateTime.now());
	}
}
