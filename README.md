# 🛡️ Voll.med API — Backend Seguro & Autenticação Stateless



API REST corporativa desenvolvida em **Java** com o framework **Spring Boot 3**, focada em integridade de dados, performance e uma camada robusta de segurança baseada em **autenticação e autorização Stateless com Spring Security 6 e Tokens JWT**.

---

## 🎯 Objetivo do Projeto

O projeto simula a infraestrutura backend para a clínica fictícia **Voll.med**, atendendo requisitos de:
- Cadastro, consulta, atualização e desativação lógica (Soft Delete) de **Médicos** e **Pacientes**.
- Regras de negócio desacopladas e validações rigorosas de dados via DTOs e Bean Validation.
- Proteção de endpoints sensíveis contra acesso não autenticado e controle granular de permissões.

---

## 🔐 Camada de Segurança (Spring Security & JWT)

O grande diferencial deste projeto é a sua implementação de segurança seguindo as práticas recomendadas pelo OWASP e pelos padrões bancários/corporativos:

1. **Arquitetura Stateless (`SessionCreationPolicy.STATELESS`):**
   - A API não armazena sessões no servidor (JSESSIONID desativado), garantindo alta escalabilidade horizontal.
2. **Proteção CSRF Desabilitada com Segurança:**
   - Desativada de forma consciente, uma vez que a autenticação é via token em Headers (`Bearer Token`) e não por cookies de sessão vulneráveis.
3. **Criptografia de Senhas com BCrypt:**
   - As senhas dos usuários nunca são salvas em texto plano; são submetidas a funções de hash criptográfico salgado (**BCryptPasswordEncoder**) no banco de dados.
4. **Security Filter Chain Customizado (`SecurityFilter`):**
   - Interceptador antes do filtro padrão do Spring (`UsernamePasswordAuthenticationFilter`) que extrai o token do cabeçalho `Authorization`, valida a assinatura/expiração via biblioteca **Auth0 / java-jwt** e injeta a autenticação no contexto do Spring Security (`SecurityContextHolder`).
5. **Ciclo de Vida do Token JWT:**
   - Tokens gerados com algoritmo de assinatura seguro (**HMAC256**), segredo de assinatura parametrizado via variáveis de ambiente e tempo de expiração curto (2 horas) para mitigação de ataques por roubo de sessão.

---

## 🛠️ Tecnologias & Bibliotecas Utilizadas

- **Linguagem:** Java 17+
- **Framework Core:** Spring Boot 3
- **Módulos do Ecossistema Spring:**
  - `Spring Web`: Criação de Controllers RESTful e gestão de requisições HTTP.
  - `Spring Security 6`: Configuração de filtros, segurança de rotas e RBAC.
  - `Spring Data JPA / Hibernate`: Mapeamento Objeto-Relacional e consultas ao banco.
  - `Bean Validation (@Valid)`: Validação de contratos de entrada de dados.
- **Banco de Dados & Versionamento:**
  - Driver JDBC (PostgreSQL / MySQL).
  - `Flyway Migration`: Versionamento controlado e reproduzível do schema do banco.
- **Autenticação:** `com.auth0:java-jwt` para emissão e verificação de tokens.
- **Produtividade:** Lombok (redução de boilerplate).

---

## 🏗️ Arquitetura do Código

A estrutura do projeto segue o princípio de responsabilidade única (Clean Architecture / MVC desacoplado):

```text
src/main/java/med/voll/api/
├── controller/         # Endpoints REST (Médicos, Pacientes, Autenticação)
├── domain/             # Entidades JPA, Repositories e DTOs de Request/Response
│   ├── consulta/       # Regras e agendamento de consultas
│   ├── medico/         # Dados e regras da entidade Médico
│   ├── paciente/       # Dados e regras da entidade Paciente
│   └── usuario/        # Usuários e UserDetailsService para autenticação
└── infra/              # Camada de Infraestrutura e Cross-Cutting Concerns
    ├── errors/         # @RestControllerAdvice e DTOs para tratamento global de erros (400, 403, 404, 500)
    └── security/       # SecurityConfigurations, SecurityFilter e TokenService
```

---

## 🚦 Endpoints Principais & Fluxo de Acesso

### 1. Autenticação (Acesso Público)
- `POST /login` 
  - **Body:** `{ "login": "usuario@voll.med", "senha": "123" }`
  - **Retorno (200 OK):** `{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..." }`

### 2. Endpoints Protegidos (Requer Cabeçalho `Authorization: Bearer <token>`)
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/medicos` | Cadastro de novo médico |
| `GET` | `/medicos` | Listagem paginada (`?page=0&size=10&sort=nome`) |
| `PUT` | `/medicos` | Atualização cadastral de médico |
| `DELETE` | `/medicos/{id}` | Exclusão lógica (soft delete) |
| `POST` | `/pacientes` | Cadastro de paciente |
| `GET` | `/pacientes` | Listagem paginada de pacientes |

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
- JDK 17 ou superior instalado.
- Maven instalado (ou use o wrapper `./mvnw`).
- Banco de dados PostgreSQL ou MySQL rodando.

### Passos:

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/SEU-USUARIO/voll-med-api.git
   cd voll-med-api
   ```

2. **Configure as variáveis de ambiente:**
   No arquivo `src/main/resources/application.properties`, defina as configurações do seu banco e o segredo JWT:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/vollmed_api
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha

   api.security.token.secret=${JWT_SECRET:12345678}
   ```

3. **Execute as Migrations e a Aplicação:**
   ```bash
   # O Flyway criará as tabelas automaticamente no boot
   ./mvnw spring-boot:run
   ```

4. **Acesse a API:**
   A API estará rodando por padrão em `http://localhost:8080`.

---

## 👨‍💻 Autor

Desenvolvido por **Fernando Maiorani Costa**  
*Foco em Engenharia de Software Backend, Arquitetura Segura e Sistemas Confiáveis.*
