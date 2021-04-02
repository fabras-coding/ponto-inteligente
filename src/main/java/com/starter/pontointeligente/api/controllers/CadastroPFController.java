package com.starter.pontointeligente.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starter.pontointeligente.api.dtos.CadastroPFDto;
import com.starter.pontointeligente.api.entities.Empresa;
import com.starter.pontointeligente.api.entities.Funcionario;
import com.starter.pontointeligente.api.enums.PerfilEnum;
import com.starter.pontointeligente.api.response.Response;
import com.starter.pontointeligente.api.services.IEmpresaService;
import com.starter.pontointeligente.api.services.IFuncionarioService;
import com.starter.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

	@Autowired
	private IEmpresaService empresaService;
	
	@Autowired
	private IFuncionarioService funcionarioService;
	
	
	public CadastroPFController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Cadastrando PF: {}", cadastroPFDto.toString());
		Response<CadastroPFDto> response = new Response<CadastroPFDto>();
		
		validarDadosExistentes(cadastroPFDto, result);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFDto, result);
		
		if(result.hasErrors())
		{
			log.error("Erro validando os dados de cadastro PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPFDto(funcionario));
		return ResponseEntity.ok(response);
		
		
	}

	/**
	 * Converte o funcionário de volta para um DTO funcionario.
	 * 
	 * @param funcionario
	 * @return CadastroPFDto
	 */
	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		
		cadastroPFDto.setCpf(funcionario.getCpf());
		cadastroPFDto.setEmail(funcionario.getEmail());
		cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());
		cadastroPFDto.setNome(funcionario.getNome());
		cadastroPFDto.setId(funcionario.getId());

		funcionario.getQdtHorasAlmocoOpt().ifPresent(horasAlmoco -> cadastroPFDto.setQtdHorasAlmoco(Optional.of(horasAlmoco.toString())));
		funcionario.getQdtHorasTrabalhoDiaOpt().ifPresent(horasTrabalho -> cadastroPFDto.setQtdHorasTrabalhoDia(Optional.of(horasTrabalho.toString())));
		funcionario.getValorHoraOpt().ifPresent(valorHora -> cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));
		
		return cadastroPFDto;
	}

	/**
	 * Converte os dados do DTO para funcionário.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return Funcionario
	 * @throws NoSuchAlgorithmException
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto, BindingResult result) throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		
		funcionario.setCpf(cadastroPFDto.getCpf());
		funcionario.setEmail(cadastroPFDto.getEmail());
		funcionario.setNome(cadastroPFDto.getNome());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
		
		cadastroPFDto.getQtdHorasAlmoco()
			.ifPresent(qtd -> funcionario.setQdtHorasAlmoco(Float.valueOf(qtd)));
		
		cadastroPFDto.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHrTr -> funcionario.setQdtHorasTrabalhoDia(Float.valueOf(qtdHrTr)));
		
		cadastroPFDto.getValorHora()
			.ifPresent(vlrHr -> funcionario.setValorHora(new BigDecimal(vlrHr)));
		
		return funcionario;
	}

	/**
	 * Verifica se a empresa está cadastrada e se o funcionário não existe na base de dados.
	 * @param cadastroPFDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpj());
		if(!empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
		}
		this.funcionarioService.buscarPorCpf(cadastroPFDto.getCpf())
			.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));
		
		this.funcionarioService.buscarPorEmail(cadastroPFDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente")));
		
	}
	
}
