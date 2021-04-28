package com.starter.pontointeligente.api.services.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.starter.pontointeligente.api.entities.Lancamento;
import com.starter.pontointeligente.api.repositories.ILancamentoRepository;
import com.starter.pontointeligente.api.services.ILancamentoService;

import java.util.Optional;

import org.slf4j.Logger;


@Service
public class LancamentoServiceImpl implements ILancamentoService {

	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	private ILancamentoRepository lancamentoRepository;
	
	
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lançamentos para o funcionário ID {} ", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Cacheable("lancamentoPorId")
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando um lançamento por Id {}" , id);
		return this.lancamentoRepository.findById(id);
	}

	@CachePut("lancamentoPorId")
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo o lançamento {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
		
	}

	
	public void remove(Long id) {
		log.info("Deletando o lançamento id {}", id);
		this.lancamentoRepository.deleteById(id);;
		
	}
}
