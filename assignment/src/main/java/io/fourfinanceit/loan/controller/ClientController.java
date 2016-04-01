package io.fourfinanceit.loan.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loan.model.response.SuccessResponse;

@RequestMapping("/clients")
@RestController
public class ClientController {

	private ClientRepository clientRepo;

	@Autowired
	public ClientController(ClientRepository clientRepo) {
		this.clientRepo = clientRepo;
	}

	@RequestMapping(method = POST)
	public ResponseEntity<SuccessResponse> createClient(@Valid @RequestBody Client client) {

		Client savedClient = clientRepo.save(client);
		long clientId = savedClient.getId();

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{clientId}").buildAndExpand(clientId).toUri());

		SuccessResponse response = new SuccessResponse(clientId);
		return new ResponseEntity<>(response, headers, CREATED);
	}

	@RequestMapping(value = "/{clientId}", method = GET)
	public ResponseEntity<Client> findClient(@PathVariable Long clientId) {

		Client client = clientRepo.findById(clientId);
		return new ResponseEntity<>(client, OK);
	}
}
