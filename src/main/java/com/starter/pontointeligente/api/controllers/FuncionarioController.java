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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starter.pontointeligente.api.dtos.FuncionarioDto;
import com.starter.pontointeligente.api.entities.Funcionario;
import com.starter.pontointeligente.api.response.Response;
import com.starter.pontointeligente.api.services.IFuncionarioService;
import com.starter.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

		private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
		
		@Autowired
		private IFuncionarioService funcionarioService;
		
		public FuncionarioController() {
			// TODO Auto-generated constructor stub
		}
		
	
		/**
		 * Atualiza um funcionário pelo Id
		 * @param id
		 * @param funcionarioDto
		 * @param result
		 * @return ResponseEntity<Response<FuncionarioDto>>
		 * @throws NoSuchAlgorithmException
		 */
		@PutMapping(value = "/{id}")
		public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result ) throws NoSuchAlgorithmException {
			
			log.info("Atualizando funcionário: {}", funcionarioDto.toString());
			Response<FuncionarioDto> response = new Response<FuncionarioDto>();
			
			Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
			if(!funcionario.isPresent()) {
				result.addError(new ObjectError("funcionario", "Funcionário não encontrado"));
				
			}
			
			this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);
			
			if(result.hasErrors()) {
				log.error("Erro ao validar funcionário: {}", result.getAllErrors());
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			//Quando tem uma instancia do Optional, o jeito de obter o objeto é nomeObjeto.get()
			this.funcionarioService.persistir(funcionario.get());
			response.setData(this.converterFuncionarioDto(funcionario.get()));
			
			return ResponseEntity.ok(response);
		}



		/**
		 * Converte o objeto funcionário para DTO
		 * @param funcionario
		 * @return FuncionarioDto
		 */
		private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
			FuncionarioDto funcionarioDto = new FuncionarioDto();
			
			funcionarioDto.setId(funcionario.getId());
			funcionarioDto.setNome(funcionario.getNome());
			funcionarioDto.setEmail(funcionario.getEmail());
			funcionario.getQdtHorasAlmocoOpt().ifPresent(horasAlmoco -> funcionarioDto.setQtdHorasAlmoco( Optional.of(Float.toString(horasAlmoco))));
			funcionario.getQdtHorasTrabalhoDiaOpt().ifPresent(horasTrabalho -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(horasTrabalho))));
			funcionario.getValorHoraOpt().ifPresent(valorHora -> funcionarioDto.setValorHora( Optional.of(valorHora.toString())))													;
					
			
			return funcionarioDto;
		}


		/**
		 * Atualiza dados do funcionário pelo que veio no DTO funcionario
		 * @param funcionario
		 * @param funcionarioDto
		 * @param result
		 */
		private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto,
				BindingResult result) {

			funcionario.setNome(funcionarioDto.getNome());
			
			if(!funcionario.getEmail().equals(funcionarioDto.getEmail())){
				this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente." )));
				funcionario.setEmail(funcionarioDto.getEmail());
				
			}
			
			funcionario.setQdtHorasAlmoco(null);
			funcionarioDto.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQdtHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
			
			funcionario.setQdtHorasTrabalhoDia(null);
			funcionarioDto.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabalho -> funcionario.setQdtHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalho)));
			
			funcionario.setValorHora(null);
			funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
			

			if(funcionarioDto.getSenha().isPresent()) { 
				funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
			}
			
		}
		
		
		
		
		
};
