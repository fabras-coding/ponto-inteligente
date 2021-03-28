package com.starter.pontointeligente.api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import com.starter.pontointeligente.api.entities.Funcionario;
import com.starter.pontointeligente.api.repositories.IFuncionarioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {
	
	@MockBean
	private IFuncionarioRepository funcionarioRepository;
	
	@Autowired
	private IFuncionarioService funcionarioService;
	
	private static final String CPF = "12345678987";
	private static final String EMAIL = "email@email.com";
	
	
	@BeforeEach
	public void setUp() throws Exception{
		
		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
		
				
	}
		
	@Test
	public void testPersistirFuncionario() {
		Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());
		assertNotNull(funcionario);
	}
	
	@Test
	public void testBuscarFuncionarioPorId() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(1L);
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void buscarPorEmail() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail(EMAIL);
		assertTrue(funcionario.isPresent());
		
	}
	
	@Test
	public void testBuscarPorCpf() {
		
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf(CPF);
		assertTrue(funcionario.isPresent());
		
	}

	
	


}
