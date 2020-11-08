package com.starter.pontointeligente.api.repositories;

import com.starter.pontointeligente.api.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long>{

	
		Funcionario findByCpf(String cpf);
		
		Funcionario findByEmail(String email );
		
		Funcionario findByCpfOrEmail(String cpf, String email);
		
}
