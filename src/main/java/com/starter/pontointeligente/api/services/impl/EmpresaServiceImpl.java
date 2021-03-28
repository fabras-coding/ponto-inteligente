package com.starter.pontointeligente.api.services.impl;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starter.pontointeligente.api.entities.Empresa;
import com.starter.pontointeligente.api.repositories.IEmpresaRepository;
import com.starter.pontointeligente.api.services.IEmpresaService;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired	
	private IEmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {

		log.info("Buscando empresa para o CNPJ {}", cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
		
	}

	@Override
	public Empresa persistir(Empresa empresa) {

		log.info("Inserindo a empresa: {}", empresa);
		return this.empresaRepository.save(empresa);
		
	}

}
