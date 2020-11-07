CREATE TABLE TB_EMPRESA (
  [id] bigint NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [NUM_CPF_CNPJ] varchar(255) NOT NULL,
  [DT_ATUALIZACAO] datetime2(0) NOT NULL,
  [DT_CRIACAO] datetime2(0) NOT NULL,
  [NM_RAZAO_SOCIAL] varchar(255) NOT NULL
) ;

CREATE TABLE TB_FUNCIONARIO (
  [id] bigint NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [cpf] varchar(255) NOT NULL,
  [dt_atualizacao] datetime2(0) NOT NULL,
  [dt_criacao] datetime2(0) NOT NULL,
  [email] varchar(255) NOT NULL,
  [nome] varchar(255) NOT NULL,
  [perfil] varchar(255) NOT NULL,
  [qtd_horas_almoco] float DEFAULT NULL,
  [qtd_horas_trabalho_dia] float DEFAULT NULL,
  [senha] varchar(255) NOT NULL,
  [valor_hora] decimal(19,2) DEFAULT NULL,
  [empresa_id] bigint DEFAULT NULL
) ;

CREATE TABLE TB_LANCAMENTO (
  [id] bigint  NOT NULL IDENTITY(1,1) PRIMARY KEY,
  [DT_LANCAMENTO] datetime2(0) NOT NULL,
  [DT_ATUALIZACAO] datetime2(0) NOT NULL,
  [DT_CRIACAO] datetime2(0) NOT NULL,
  [NM_DESCRICAO] varchar(255) DEFAULT NULL,
  [DESC_LOCALIZACAO] varchar(255) DEFAULT NULL,
  [NM_TIPO] varchar(255) NOT NULL,
  [funcionario_id] bigint DEFAULT NULL
) ;


--
-- SQLINES DEMO *** able `funcionario`
--
ALTER TABLE [TB_FUNCIONARIO]
  ADD CONSTRAINT [FK4cm1kg523jlopyexjbmi6y54j] FOREIGN KEY ([empresa_id]) REFERENCES TB_EMPRESA ([id]);

--
-- SQLINES DEMO *** able `lancamento`
--
ALTER TABLE [TB_LANCAMENTO]
  ADD CONSTRAINT [FK46i4k5vl8wah7feutye9kbpi4] FOREIGN KEY ([funcionario_id]) REFERENCES TB_FUNCIONARIO ([id]);