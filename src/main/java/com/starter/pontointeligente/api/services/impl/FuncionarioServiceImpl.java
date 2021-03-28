package com.starter.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starter.pontointeligente.api.entities.Funcionario;
import com.starter.pontointeligente.api.repositories.IFuncionarioRepository;
import com.starter.pontointeligente.api.services.IFuncionarioService;

@Service
public class FuncionarioServiceImpl implements IFuncionarioService {

		private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);
		
		@Autowired
		private IFuncionarioRepository funcionarioRepository;

				
		public Funcionario persistir(Funcionario funcionario) {
		
			log.info("Persistindo funcinário {} ", funcionario);
			return this.funcionarioRepository.save(funcionario);
			
		}

		
		public Optional<Funcionario> buscarPorCpf(String cpf) {
			log.info("Buscando funcionário por Cpf {} ", cpf);
			return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
		}

		
		public Optional<Funcionario> buscarPorEmail(String email) {
			log.info("Buscando funcionário por email {}", email);
			return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
			
		}

		
		public Optional<Funcionario> buscarPorId(Long id) {
			log.info("Buscando funcionário por Id {}", id);
			return this.funcionarioRepository.findById(id);
		}
}
