[![Build Status](https://travis-ci.org/libelola/ponto-inteligente.svg?branch=master)](https://travis-ci.org/libelola/ponto-inteligente)
# Api Ponto Inteligente
API do sistema de Ponto Inteligente


=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			Resolução de Problemas para rodar o Aplicativo
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

Durante a execução da migration, caso ocorra erro do flyway não conseguir conectar no localhost:1433 fazer o seguinte:
Abrir o Sql Configuration Manager 

C:\WINDOWS\System32\SQLServerManager14.msc (vai de acordo com a versao o numero)

Expandir a opção Sql Server Newtwork Configuration
	> Protocols for MSSQLSERVER (sua instancia)
		> TCP/IP
			Habilitar A opção "Enabled" (YES)
			E na segunda aba, IP Address, checar se a ultima porta IPAII está como 1433. Fazendo isso, é só reiniciar o serviço do sql server (padrão de instancia é MSSQLSERVER)

-------------------------------------------------------------------------------------------------------------------------------------------------

Para corrigir o erro de driver sem suporte a integrated security, fazer os seguintes passos:

Baixar o JDBC Driver de acordo com o JRE que estiver rodando
https://docs.microsoft.com/pt-br/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15

Ir na pasta Java do Program Files (x86) ou então Program Files > Java > jrex.x.x_xxx > bin e colar o arquivo sqljdbc_auth de acordo com a arquitetura x64 ou x86
Fazer o mesmo procedimento na pasta lib


Caso ainda dê erro, ir até a pasta C:\Program Files (x86)\Common Files\Oracle\Java\javapath e colar o mesmo arquivo, de acordo com a arquitetura. Pode ser que na execução ele reclame que está na arquitetura X, neste caso, basta pegar a .dll da pasta de arquitetura oposta e colar nessa mesma pasta.