package com.starter.pontointeligente.api.controllers;

import java.net.URI;


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
public class CadastroPFControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	
	@Test
	public void postCadastroPFOK() throws Exception{
		
		URI uri = new URI("/api/cadastrar-pj");
		
		String json = "{\r\n"
				+ "	\"nome\": \"Fabs Olives\",\r\n"	
				+ "	\"email\": \"fabs@olivs.com\",\r\n"
				+ "	\"senha\": \"123456\",\r\n"
				+ "	\"cpf\": \"11739075056\",\r\n"
				+ "	\"razaoSocial\": \"Fabs Emprendimentos\",\r\n"
				+ "	\"cnpj\": \"93897307000118\"\r\n"
				+ "}";
	
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));
		
		URI uri2 = new URI("/api/cadastrar-pf");
		String json2 = "{\r\n"
				+ "	\"nome\": \"Fulano de Tals\",\r\n"
				+ "	\"email\": \"fulanos@empresas.com\",\r\n"
				+ "	\"senha\": \"654321\",\r\n"
				+ "	\"cpf\": \"90299166058\",\r\n"
				+ "	\"valorHora\": \"40\",\r\n"
				+ "	\"cnpj\": \"93897307000118\"\r\n"
				+ "}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri2)
				.content(json2)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));
		
		
	}
	
}
