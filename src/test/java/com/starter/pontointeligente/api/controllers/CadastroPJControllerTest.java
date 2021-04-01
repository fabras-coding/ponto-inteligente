package com.starter.pontointeligente.api.controllers;

import java.net.URI;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroPJControllerTest {

	
	@Autowired 
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() throws Exception {
		
		
	}
	
	@Test
	public void testaPostBonitao() throws Exception {
		URI uri = new URI("/api/cadastrar-pj");
		
		String json = "{\r\n"
				+ "	\"nome\": \"Fabs\",\r\n"
				+ "	\"email\": \"fabs@fabs.com\",\r\n"
				+ "	\"senha\": \"123456\",\r\n"
				+ "	\"cpf\": \"42181987807\",\r\n"
				+ "	\"razaoSocial\": \"Fabs Emps\",\r\n"
				+ "	\"cnpj\": \"43115716000147\"\r\n"
				+ "}";
	
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));
				
		
	}
	

	
	@Test
	public void testaPostCpfInvalido() throws Exception {
		URI uri = new URI("/api/cadastrar-pj");
		
		String json = "{\r\n"
				+ "	\"nome\": \"Fabs\",\r\n"
				+ "	\"email\": \"fabs@fabs.com\",\r\n"
				+ "	\"senha\": \"123456\",\r\n"
				+ "	\"cpf\": \"12345\",\r\n"
				+ "	\"razaoSocial\": \"Fabs Emps\",\r\n"
				+ "	\"cnpj\": \"43115716000147\"\r\n"
				+ "}";
	
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
				
		
	}
	

	@Test
	public void testaPostCnpjInvalido() throws Exception {
		URI uri = new URI("/api/cadastrar-pj");
		
		String json = "{\r\n"
				+ "	\"nome\": \"Fabs\",\r\n"
				+ "	\"email\": \"fabs@fabs.com\",\r\n"
				+ "	\"senha\": \"123456\",\r\n"
				+ "	\"cpf\": \"42181987807\",\r\n"
				+ "	\"razaoSocial\": \"Fabs Emps\",\r\n"
				+ "	\"cnpj\": \"12345\"\r\n"
				+ "}";
	
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
				
		
	}
	

	@Test
	public void testaPostNomeNull() throws Exception {
		URI uri = new URI("/api/cadastrar-pj");
		
		String json = "{\r\n"
				+ "	\"nome\": null,\r\n"
				+ "	\"email\": \"fabs@fabs.com\",\r\n"
				+ "	\"senha\": \"123456\",\r\n"
				+ "	\"cpf\": \"42181987807\",\r\n"
				+ "	\"razaoSocial\": \"Fabs Emps\",\r\n"
				+ "	\"cnpj\": \"43115716000147\"\r\n"
				+ "}";
	
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
				
		
	}
	
	
	
}
