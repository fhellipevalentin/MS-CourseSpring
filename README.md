# MS-CourseSpring
Aprendendo sobre Micro Serviços

Créditos: Nelio Alves - Udemy

# OBJETIVO

Essas sequencias de commits mostra a desenvoltura de um programa totalmente voltada para a simulação de um programa que atua 
através de micro-serviços. São ao todo: 7 micro-serviços e cada um desempenha a devida função e compõem uma aplicação
única, e assim o aprendizado mostra explicitamente a vantagem de manutenção e reutilização dos módulos da aplicação como um todo.

Abaixo: Você pode notar a existência dos micro serviços como módulos através da IDE utilizada, que é o IntelliJ.

![image](https://user-images.githubusercontent.com/65087845/191244701-97c6b179-3974-4a6b-b306-53a63ca023f9.png)

A execução do programa é a de como qualquer aplicação de micro serviço, ou seja, para ser executada, é necessário executar os dependentes.

1º:  hr-config-server (Servidor de Configuração, no qual, guarda as variáveis de configuração)

2º:  hr-eureka-server (Servidor de Registros, no qual, será responsável por gerenciar o status e a localização dos micro serviços)

3º:  hr-api-gateway-zull (Serviço que é o Gateway da nossa rede, que fará a responsabilidade de conectar os nossos serviços, utilizando uma
rede, que por enquanto, se dá pela utilização das portas locais da maquina em que está executando).

4º:  hr-oauth (Servidor de Autenticação, para fins de login da instancias cadastradas)

5º:  hr-user (Serviço que define as regras dos perfis dos usuários, atribuindo os privilégios segundo o nível de usuário registrado)

6º:  hr-worker (Serviço que carrega as intancias que vão manipular os recursos da aplicação, à cada nova instancia gerada - o que podem ser
multiplas - será devidamente conectada à aplicação mesmo que através de uma porta aleatória)

7º:  hr-payroll (Serviço que manipula uma operação simples de cálculo de horas trabalhadas das instancias de "hr-worker" registradas);

# Funcionamento do programa 

Abaixo, o programa em execução, carregando três instancias de hr-worker (no perfil de teste), e ao mesmo tempo, todos em pleno funcionamento
rodando em diferentes portas, ao mesmo tempo, conectados localmente.

![image](https://user-images.githubusercontent.com/65087845/191249632-1201c97e-80cd-411d-99e7-ca78122106b4.png)

# Funcionamento da visualização do servidor Eureka

Abaixo, visualmente é possível identificar as instancias registradas no Servidor Eureka, e assim, contectadas através do Gateway Zull
e também, as três instancias de worker identificadas.. Assim, nos facilita a questão do balanceamento de carga.

![image](https://user-images.githubusercontent.com/65087845/191251334-822aa10c-78c1-4dac-8015-b52c6fd2eaf6.png)

## Testando Requisições

O programa assim, como está desde o ultimo commit, pode já fazer requisições através de programas como Postman. Com a ordem de primeiramente,
fazer o login segundo os usuários registrados e seus respectivos perfis.

## Requisição de login em pleno funcionamento

![image](https://user-images.githubusercontent.com/65087845/191251658-38990f4e-2d12-444c-be0a-2e3caa32df73.png)

## Requisição de cálculo de dias trabalhados com valores inseridos diretamente no endpoint

![image](https://user-images.githubusercontent.com/65087845/191253028-24eadb39-dde8-4699-b366-e6567dbcc369.png)


# Fases de criação do programa

Graças ao passo a passo abaixo o programa pôde ser realizado com sucesso. De requisito, precisamos de uma versão do Java 11 instalado localmente, 
das dependências segundo os passos abaixo, inclusive a base de dados para ser lançada no banco de dados de testes H2 para ao menos as requisições 
serem usadas localmente.

# Fase 1: Comunicação simples, Feign, Ribbon

### 1.1 Criar projeto hr-worker

### 1.2 Implementar projeto hr-worker

Script SQL
```sql
INSERT INTO tb_worker (name, daily_Income) VALUES ('Bob', 200.0);
INSERT INTO tb_worker (name, daily_Income) VALUES ('Maria', 300.0);
INSERT INTO tb_worker (name, daily_Income) VALUES ('Alex', 250.0);
```

application.properties
```
spring.application.name=hr-worker
server.port=8001
# Database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 1.3 Criar projeto hr-payroll

application.properties
```
spring.application.name=hr-payroll
server.port=8101
```

### 1.4 Implementar projeto hr-payroll (mock)

### 1.5 RestTemplate

### 1.6 Feign

### 1.7 Ribbon load balancing

Run configuration
```
-Dserver.port=8002
```
# Fase 2: Eureka, Hystrix, Zuul

### 2.1 Criar projeto hr-eureka-server

### 2.2 Configurar hr-eureka-server

Porta padrão: 8761

Acessar o dashboard no navegador: http://localhost:8761

### 2.3 Configurar clientes Eureka

Eliminar o Ribbon de hr-payroll:
- Dependência Maven
- Annotation no programa principal
- Configuração em application.properties

Atenção: aguardar um pouco depois de subir os microsserviços

### 2.4 Random port para hr-worker

```
server.port=${PORT:0}
eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
```

Atenção: deletar as configurações múltiplas de execução de hr-worker

### 2.5 Tolerância a falhas com Hystrix

### 2.6 Timeout de Hystrix e Ribbon

Atenção: testar antes sem a annotation do Hystrix

```
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
ribbon.ConnectTimeout=10000
ribbon.ReadTimeout=20000
```

### 2.7 Criar projeto hr-zuul-server

### 2.8 Configurar hr-zuul-server

Porta padrão: 8765

### 2.9 Random port para hr-payroll


### 2.10 Zuul timeout

Mesmo o timeout de Hystrix e Ribbon configurado em um microsserviço, se o Zuul não tiver seu timeout configurado, para ele será um problema de timeout. Então precisamos configurar o timeout no Zuul.

Se o timeout estiver configurado somente em Zuul, o Hystrix vai chamar o método alternativo no microsserviço específico.

# Fase 3: Configuração centralizada

### 3.1 Criar projeto hr-config-server

### 3.2 Configurar projeto hr-config-server

Quando um microsserviço é levantado, antes de se registrar no Eureka, ele busca as configurações no repositório central de configurações.

hr-worker.properties
```
test.config=My config value default profile
```
hr-worker-test.properties
```
test.config=My config value test profile
```
Teste:
```
http://localhost:8888/hr-worker/default
http://localhost:8888/hr-worker/test
```

### 3.3 hr-worker como cliente do servidor de configuração, profiles ativos

No arquivo bootstrap.properties configuramos somente o que for relacionado com o servidor de configuração, e também o profile do projeto.

Atenção: as configurações do bootstrap.properties tem prioridade sobre as do application.properties

### 3.4 Actuator para atualizar configurações em runtime

Atenção: colocar @RefreshScope em toda classe que possua algum acesso às configurações

### 3.5 Repositório Git privativo

Atenção: reinicie a IDE depois de adicionar as variáveis de ambiente

# Fase 4: autenticação e autorização

### 4.1 Criar projeto hr-user

### 4.2 Configurar projeto hr-user

### 4.3 Entidades User, Role e associação N-N

### 4.4 Carga inicial do banco de dados
```sql
INSERT INTO tb_user (name, email, password) VALUES ('Nina Brown', 'nina@gmail.com', '$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu');
INSERT INTO tb_user (name, email, password) VALUES ('Leia Red', 'leia@gmail.com', '$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu');
INSERT INTO tb_role (role_name) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
```

### 4.5 UserRepository, UserResource, Zuul config

### 4.6 Criar projeto hr-oauth

### 4.7 Configurar projeto hr-oauth

### 4.8 UserFeignClient

### 4.9 Login e geração do Token JWT

Source -> Override -> configure(AuthenticationManagerBuilder)

Source -> Override -> authenticationManager()

Basic authorization = "Basic " + Base64.encode(client-id + ":" + client-secret)

### 4.10 Autorização de recursos pelo gateway Zuul

### 4.11 Deixando o Postman top

Variáveis:
- api-gateway: http://localhost:8765
- config-host: http://localhost:8888
- client-name: CLIENT-NAME
- client-secret: CLIENT-SECRET
- username: leia@gmail.com
- password: 123456
- token: 

Script para atribuir token à variável de ambiente do Postman:
```js
if (responseCode.code >= 200 && responseCode.code < 300) {
    var json = JSON.parse(responseBody);
    postman.setEnvironmentVariable('token', json.access_token);
}
```
### 4.12 Configuração de segurança para o servidor de configuração

### 4.13 Configurando CORS

Teste no navegador:
```js
fetch("http://localhost:8765/hr-worker/workers", {
  "headers": {
    "accept": "*/*",
    "accept-language": "en-US,en;q=0.9,pt-BR;q=0.8,pt;q=0.7",
    "sec-fetch-dest": "empty",
    "sec-fetch-mode": "cors",
    "sec-fetch-site": "cross-site"
  },
  "referrer": "http://localhost:3000",
  "referrerPolicy": "no-referrer-when-downgrade",
  "body": null,
  "method": "GET",
  "mode": "cors",
  "credentials": "omit"
});
```
