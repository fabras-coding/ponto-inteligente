package com.starter.pontointeligente.api.entities;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import org.springframework.data.annotation.Id;

import com.starter.pontointeligente.api.enums.PerfilEnum;

@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private BigDecimal valorHora;
	private Float qdtHorasTrabalhoDia;
	private Float qdtHorasAlmoco;
	private PerfilEnum perfil;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private Empresa empresa;
	private List<Lancamento> lancamentos;
	
	public Funcionario() {
		
	}
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable=false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	@Column(name = "email", nullable =false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	@Column(name="senha", nullable = false) 
	public String getSenha()
	{
		return	senha; 
	}
	  
	public void setSenha(String senha) 
	{ 
		this.senha = senha; 
	}
	 
	
	@Column(name= "cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name = "valor_hora", nullable = true)
	public BigDecimal getValorHora() {
		return valorHora;
	}
	
	@Transient
	public Optional<BigDecimal> getValorHoraOpt(){
		return Optional.ofNullable(valorHora);
	}
	
	public void setValorHora (BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	@Column(name="qtd_horas_trabalho_dia", nullable = true)
	public Float getQdtHorasTrabalhoDia() {
		return qdtHorasTrabalhoDia;
	}
	
	@Transient
	public Optional<Float> getQdtHorasTrabalhoDiaOpt() {
		return Optional.ofNullable(qdtHorasTrabalhoDia);
	}
	

	public void setQdtHorasTrabalhoDia(Float qdtHorasTrabalhoDia) {
		this.qdtHorasTrabalhoDia = qdtHorasTrabalhoDia;
	}

	@Column(name="qtd_horas_almoco", nullable = true)
	public Float getQdtHorasAlmoco() {
		return qdtHorasAlmoco;
	}

	@Transient
	public Optional<Float> getQdtHorasAlmocoOpt() {
		return Optional.ofNullable(qdtHorasAlmoco);
	}
	
	public void setQdtHorasAlmoco(Float qdtHorasAlmoco) {
		this.qdtHorasAlmoco = qdtHorasAlmoco;
	}
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="perfil", nullable = false)
	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	@Column(name="dt_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(name="dt_atualizacao", nullable = false)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@OneToMany(mappedBy = "TB_FUNCIONARIO", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
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
		return "Funcionario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qdtHorasTrabalhoDia=" + qdtHorasTrabalhoDia + ", qdtHorasAlmoco="
				+ qdtHorasAlmoco + ", perfil=" + perfil + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + ", empresa=" + empresa + ", lancamentos=" + lancamentos + "]";
	}


	
}
