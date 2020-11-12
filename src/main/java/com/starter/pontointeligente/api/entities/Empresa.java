package com.starter.pontointeligente.api.entities;

import java.io.Serializable;

import java.util.*;

import javax.persistence.*;
import org.springframework.data.annotation.Id;


@Entity
@Table(name ="TB_EMPRESA")
public class Empresa implements Serializable{

	
		/**
	 * 
	 */
	private static final long serialVersionUID = 3687488708682617455L;
		/**
	 * 
	 */
	

	private Long id;
	private String razaoSocial;
	private String cnpj;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private List<Funcionario> funcionarios;
	
	public Empresa() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Column(name = "NM_RAZAO_SOCIAL", nullable = false)
		public String getRazaoSocial() {
			return razaoSocial;
		}

		public void setRazaoSocial(String razaoSocial) {
			this.razaoSocial = razaoSocial;
		}

		@Column(name = "NUM_CPF_CNPJ", nullable = false)
		public String getCnpj() {
			return cnpj;
		}

		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
		}

		@Column(name = "DT_CRIACAO", nullable = false)
		public Date getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(Date dataCriacao) {
			this.dataCriacao = dataCriacao;
		}

		@Column(name = "DT_ATUALIZACAO", nullable = false)
		public Date getDataAtualizacao() {
			return dataAtualizacao;
		}

		public void setDataAtualizacao(Date dataAtualizacao) {
			this.dataAtualizacao = dataAtualizacao;
		}

		@OneToMany(mappedBy = "TB_EMPRESA", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		public List<Funcionario> getFuncionarios() {
			return funcionarios;
		}

		public void setFuncionarios(List<Funcionario> funcionarios) {
			this.funcionarios = funcionarios;
		}
		
		@PreUpdate
		public void preUpdate() {
			dataAtualizacao = new Date();
		}
		
		@PrePersist
		public void prePersist() {
			final Date atual = new Date();
			dataCriacao = atual;
			dataAtualizacao = atual;
		}
		
		
		@Override
		public String toString() {
			return "Empresa [id=" + id + " , razaoSocial=" + razaoSocial + ", cnpj=" + cnpj +", dataCriacao =" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + "]";
		}

}
