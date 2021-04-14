package com.starter.pontointeligente.api.controllers;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starter.pontointeligente.api.dtos.LancamentoDto;
import com.starter.pontointeligente.api.entities.Lancamento;
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
		
		@Value("${paginacao.qtd_por_pagina}") // pega o vaor do atributo no application properties
		private int qtdPorPagina;
		
		public LancamentoController() {


		}
		
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
			Page<LancamentoDto> lancamentosDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));
			
			response.setData(lancamentosDto);
			return ResponseEntity.ok(response);
			
			
		}

		private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
			// TODO Auto-generated method stub
			return null;
		}
				
				
		
		
		
		
}
