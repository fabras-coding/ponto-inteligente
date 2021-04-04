package com.starter.pontointeligente.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.starter.pontointeligente.api.entities.Empresa;
import com.starter.pontointeligente.api.services.IEmpresaService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IEmpresaService empresaService;
	
	private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
	private static final Long ID = Long.valueOf(1);
	private static final String CNPJ = "93897307000118";
	private static final String RAZAO_SOCIAL ="empresa XYZ";
	
	@Test
	public void testBuscarEmpresaCnpjINvalido()throws Exception {
		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " +CNPJ));
		
	}
	
	@Test
	public void testBuscarEmpresaCnpjValido () throws Exception{
		
	BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(this.obterDadosEmpresa()));
		
		mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.razaoSocial", equalTo(RAZAO_SOCIAL)))
		.andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
		.andExpect(jsonPath("$.errors").isEmpty());
		
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		
		empresa.setId(ID);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		empresa.setCnpj(CNPJ);
		return empresa;
	}
	
	
	
	
}
