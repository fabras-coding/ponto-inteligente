package com.starter.pontointeligente.api.repositories;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.starter.pontointeligente.api.entities.Empresa;
import com.starter.pontointeligente.api.entities.Funcionario;
import com.starter.pontointeligente.api.entities.Lancamento;
import com.starter.pontointeligente.api.enums.PerfilEnum;
import com.starter.pontointeligente.api.enums.TipoEnum;
import com.starter.pontointeligente.api.utils.PasswordUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest
@ActiveProfiles("test")

public class LancamentoRepositoryTest{
	
	@Autowired
	private ILancamentoRepository lancamentoRepository;
	
	@Autowired
	private IFuncionarioRepository funcionarioRepository;
	
	@Autowired 
	private IEmpresaRepository empresaRepository;
	
	private Long funcionarioId;
	
	@BeforeEach
	public void setUp() throws Exception{
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		this.empresaRepository.deleteAll();
	}
	
	
	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertEquals(2, lancamentos.size());
		
	}
	
	
	@Test
	public void testBuscarLancamentoPorFuncionarioIdPaginado() {
		PageRequest page =  PageRequest.of(0,10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
		
	}
	
	
	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}
	
	private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulano de Tal");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setEmail("email@email.com");
		funcionario.setCpf("24514587845");
		return funcionario;
	}
	
	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");
		return empresa;
	}
	
}

