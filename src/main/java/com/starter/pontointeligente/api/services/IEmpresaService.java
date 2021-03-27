package com.starter.pontointeligente.api.services;

import java.util.Optional;

import com.starter.pontointeligente.api.entities.Empresa;

public interface IEmpresaService {

	
		/**
		 * Retorna uma empresa dado um CNPJ
		 * 
		 * @param cnpj
		 * @return Optional<Empresa>
		 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
		
	
	/**
	 * Cadastra uma nova empresa na base de dados.
	 * 
	 * @param empresa
	 * @return Empresa
	 */
	Empresa persistir(Empresa empresa);
	
	
}
