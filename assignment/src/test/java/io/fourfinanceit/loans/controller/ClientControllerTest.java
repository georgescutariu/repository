package io.fourfinanceit.loans.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import io.fourfinanceit.loan.controller.ClientController;
import io.fourfinanceit.loan.model.Client;
import io.fourfinanceit.loans.util.ClientBuilder;

public class ClientControllerTest extends ControllerTest {

	@InjectMocks
	private ClientController controller;

	@Before
	@Override
	public void init() throws Exception {
		super.init();
	}

	@Test
	public void testCreateNewClientIntegration() throws Exception {
		Client newClient = new ClientBuilder().firstName("Bob").lastName("Smit").build();

		mockMvc.perform(post("/clients").contentType(CONTENT_TYPE).content(gson.toJson(newClient)))
				.andExpect(status().isCreated());
				//.andExpect(jsonPath("$.newReference", is(1)));

		mockMvc.perform(get("/clients/1"))
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	public void testCreateNewClientMissingName() throws Exception {
		Client client = new Client();
		client.setFirstName("Bob");

		mockMvc.perform(post("/clients").contentType(CONTENT_TYPE).content(gson.toJson(client)))
				.andExpect(status().isBadRequest());
	}
}