## Desafio proposto pela empresa Sicred

### Cenário de negócio
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de
contas para enviar ao Banco Central. <br/><br/>
Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi
já possiu mais de 4 milhões de contas ativas. <br/><br/>
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal,
antes as 10:00 da manhã na abertura das agências.

### Requisito
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

### Funcionalidade
1) Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
2) Processa um arquivo CSV de entrada com o formato abaixo.
3) Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
4) Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma
   nova coluna.

> #### Formato CSV: <br/>
> agencia;conta;saldo;status <br/>
> 0101;12225-6;100,00;A <br/>
> 0101;12226-8;3200,50;A <br/>
> 3202;40011-1;-35,12;I <br/>
> 3202;54001-2;0,00;P <br/>
> 3202;00321-2;34500,00;B

### Tecnologias
- Java 11
- Spring Boot 2.7.0
- Maven 3.6.3
- OpenCSV 5.2
- Lombok

### Utilização
- Clone o repositório: https://github.com/rodrigocpsr/desafio-sicred.git
- Execute o comando: ./mvnw clean install
- Execute o comando: java -jar target/sincronizacao-receita-0.0.1-SNAPSHOT.jar entrada.csv