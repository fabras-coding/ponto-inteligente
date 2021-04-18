package com.starter.pontointeligente.api.controllers;

import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starter.pontointeligente.api.dtos.LancamentoDto;
import com.starter.pontointeligente.api.entities.Funcionario;
import com.starter.pontointeligente.api.entities.Lancamento;
import com.starter.pontointeligente.api.enums.TipoEnum;
import com.starter.pontointeligente.api.response.Response;
import com.starter.pontointeligente.api.services.IFuncionarioService;
import com.starter.pontointeligente.api.services.ILancamentoService;


@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins="*")
public class LancamentoController {

		private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
		private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		@Autowired
		private ILancamentoService lancamentoService;
		
		@Autowired
		private IFuncionarioService funcionarioService;
		
		@Value("${paginacao.qtd_por_pagina}") // pega o valor do atributo no application properties
		private int qtdPorPagina;
		
		public LancamentoController() {


		}
		
		/**
		 * Retorna a listagem de lançamentos de um funcionário.
		 * @param funcionarioId
		 * @param pag
		 * @param ord
		 * @param dir
		 * @return ResponseEntity<Response<LancamentoDto>>
		 */
		@GetMapping(value ="/funcionario/{funcionarioId}")
		public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
				@PathVariable("funcionarioId") long funcionarioId,
				@RequestParam(value = "pag", defaultValue = "0") int pag,
				@RequestParam(value = "ord", defaultValue = "id") String ord,
				@RequestParam(value = "dir", defaultValue = "DESC") String dir){
			
			log.info("Buscando lançamentos por ID do funcionário : {}, página {}", funcionarioId, pag);
			Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();
			
			PageRequest pageRequest =  PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
			Page<Lancamento> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
			Page<LancamentoDto> lancamentosDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento)); //map (utilitario do java8)
			
			response.setData(lancamentosDto);
			return ResponseEntity.ok(response);
			
			
		}
		
		/**
		 * Retorna o lançamento do Id
		 * @param id
		 * @return ReponseEntity<Reponse<LancamentoDto>>
		 */
		@GetMapping(value = "/{id}")
		public ResponseEntity<Response<LancamentoDto>> listarPorId(@PathVariable("id") Long id){
			
			log.info("Buscando lançamento por ID: {}", id);
			Response<LancamentoDto> response = new Response<LancamentoDto>();
			Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);
			
			if(!lancamento.isPresent())
			{
				log.info("Lançamento não encontrado para od ID: {}", id);
				response.getErrors().add("Lançamento não encontrado para o id {}"+ id);
				return ResponseEntity.badRequest().body(response);				
			}
			
			response.setData(this.converterLancamentoDto(lancamento.get()));
			return ResponseEntity.ok(response);
			
		}
		
		/**
		 * Cria um novo lançamento
		 * @param lancamentoDto
		 * @param result
		 * @return ResponseEntity<Response<LancamentoDto>>
		 * @throws ParseException
		 * @throws java.text.ParseException 
		 */
		@PostMapping
		public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto,
				BindingResult result) throws ParseException, java.text.ParseException {
			
			log.info("Adicionando lançamento: {}", lancamentoDto.toString());
			Response<LancamentoDto> response = new Response<LancamentoDto>();
			validarFuncionario(lancamentoDto, result);
			Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);
			
			if(result.hasErrors())
			{
				log.error("Erro validando lançamento: {}", result.getAllErrors());
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			lancamento = this.lancamentoService.persistir(lancamento);
			response.setData(this.converterLancamentoDto(lancamento));
			
			return ResponseEntity.ok(response);
			
		}
		
		/**
		 * Atualiza os dados de um lançamento
		 * @param id
		 * @param lancamentoDto
		 * @param result
		 * @return ResponseEntity<Response<LancamentoDto>>
		 * @throws ParseException
		 * @throws java.text.ParseException 
		 */
		@PutMapping(value = "/{id}")
		public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id")Long id, @Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException, java.text.ParseException {
			
			log.info("Atualizando lançamento {}", lancamentoDto.toString());
			Response<LancamentoDto> response = new Response<LancamentoDto>();
			validarFuncionario(lancamentoDto,result);
			lancamentoDto.setId(Optional.of(id));
			Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);
			
			if(result.hasErrors())
			{
				log.error("Erro validando o lançamento: {}", result.getAllErrors());
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			lancamento = this.lancamentoService.persistir(lancamento);
			response.setData(this.converterLancamentoDto(lancamento));
			return ResponseEntity.ok(response);
			
		}
		
		/**
		 * remove um lançamento por um id
		 * @param id
		 * @return ResponseEntity<Response<String>>
		 */
		@DeleteMapping(value= "/{id}")
		public ResponseEntity<Response<String>> remover (@PathVariable("id") Long id){
			
			log.info("Removendo Lançamento {}", id);
			Response<String> response = new Response<String>();
			Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);
			
			if(!lancamento.isPresent()) {
				log.info("Erro ao remover devido ao lançamento ID: {} ser inválido." , id);
				response.getErrors().add("Erro ao remover lançamento. Registro não encontrado para o id: {}" + id);
				return ResponseEntity.badRequest().body(response);
			
			}
			
			this.lancamentoService.remove(id);
			return ResponseEntity.ok(new Response<String>());
			
			
		}
		
		
		

		private Lancamento converterDtoParaLancamento(@Valid LancamentoDto lancamentoDto, BindingResult result) throws ParseException, java.text.ParseException {
			
			Lancamento lancamento = new Lancamento();
			
			if(lancamentoDto.getId().isPresent()) {
				Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDto.getId().get());
				
				if(lanc.isPresent()) {
					lancamento = lanc.get();
				} else {
					result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
				}
				
			}
			else {
				lancamento.setFuncionario(new Funcionario());
				lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
			}
			
			lancamento.setDescricao(lancamentoDto.getDescricao());
			lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
			lancamento.setData(this.dateFormat.parse(lancamentoDto.getData()));
			
			if(EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
				lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
			}else {
				result.addError(new ObjectError("tipo", "Tipo inválido"));
			}
			
			return lancamento;
			
		}

		/**
		 * Valida um funcionário, verificando se ele é existente e válido no sistema
		 * 
		 * @param lancamentoDto
		 * @param result
		 */
		private void validarFuncionario(@Valid LancamentoDto lancamentoDto, BindingResult result) {
			
			if(lancamentoDto.getFuncionarioId() == null) {
				result.addError(new ObjectError("funcionario", "Funcionário não informado."));
				return;
			}
			
			log.info("Validando funcionário do Id {}:", lancamentoDto.getFuncionarioId());
			Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(lancamentoDto.getFuncionarioId());
			
			if(!funcionario.isPresent()) {
				result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
			}
			
		}

		private LancamentoDto converterLancamentoDto(Lancamento lancamento) {

			LancamentoDto lancamentoDto = new LancamentoDto();
			
			lancamentoDto.setId(Optional.of(lancamento.getId()));
			lancamentoDto.setDescricao(lancamento.getDescricao());
			lancamentoDto.setData(this.dateFormat.format(lancamento.getData()));
			lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());
			lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
			lancamentoDto.setTipo(lancamento.getTipo().toString());
			
			return lancamentoDto;
			
			
		}
				
				
		
		
		
		
}
