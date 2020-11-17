package com.starter.pontointeligente.api.repositories;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.starter.pontointeligente.api.entities.Empresa;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;


@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
	
	
	@Autowired //injeção de dependencia
	private IEmpresaRepository empresaRepository;

	public static final String CNPJ = "51479301000180";
	
	@BeforeEach //executa antes do teste
	public void setUp() throws Exception{
		
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de Exemplo");
		empresa.setCnpj("01123456789");
		this.empresaRepository.save(empresa);
	}
	
	@After //...
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarPorCnpj() {
		Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
		assertEquals(CNPJ,empresa.getCnpj());
	}
	
	
}
