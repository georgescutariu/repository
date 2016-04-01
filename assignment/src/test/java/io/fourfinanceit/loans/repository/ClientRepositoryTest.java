package io.fourfinanceit.loans.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.fourfinanceit.loan.AssignmentApplication;
import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loan.model.ClientRepository;
import io.fourfinanceit.loans.util.ClientBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AssignmentApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class ClientRepositoryTest {
	
	@Autowired
	private ClientRepository clientRepo;
	
	Client client;
	
	@Before
	public void init() {
		if (client == null) {
			client = new ClientBuilder().firstName("Bob").lastName("Smit").build();
			clientRepo.save(client);
		}
	}
	
	@Test
    public void testFindClientById() throws Exception {
		
		Client clientFound = clientRepo.findById(client.getId());
		
		assertNotNull(clientFound);
		assertEquals(client.getId(), clientFound.getId());
	}
	
	@Test
    public void testFindClientByIdNotFound() throws Exception {
		
		Client clientFound = clientRepo.findById(500);
		
		assertNull(clientFound);
	}
}
