package io.fourfinanceit.loans.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import io.fourfinanceit.loan.AssignmentApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AssignmentApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@Ignore
public class ControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	protected Gson gson;

	protected static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public void init() throws Exception {
		if (mockMvc == null || gson == null) {
			mockMvc = webAppContextSetup(webApplicationContext).build();
			gson = new Gson();
		}
	}
}
