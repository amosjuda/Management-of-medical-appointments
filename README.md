# Sistema de Gestão de Consultas Médicas

O **Sistema de Gestão de Consultas Médicas** foi desenvolvido para viabilizar a interação eficiente entre profissionais da saúde e cidadãos. Este software facilita a gestão de usuários em unidades de saúde, permitindo as seguintes funcionalidades:

* **Gerenciamento Completo (CRUD)**: Inserção, exclusão, atualização e busca de:
  * **Pacientes**
  * **Médicos**
  * **Consultas Médicas**
* **Flexibilidade para Diferentes Perfis de Usuários**: Adaptável para uso por médicos, enfermeiros e recepcionistas.
* **Adaptabilidade de Ambiente**: Projetado para funcionar eficientemente tanto em ambientes hospitalares quanto em clínicas.

O projeto busca ser uma solução **eficiente e prática**, melhorando o fluxo de trabalho nas instituições de saúde através de uma abordagem moderna e conteinerizada.

---

## Tecnologias Utilizadas

Este projeto é construído com as seguintes tecnologias e ferramentas:

* **Linguagem de Programação**: **JavaSE 21 LTS** (Versão atualizada para maior suporte e otimização).
* **Framework**: **Spring Boot 3.2.5**, para desenvolvimento rápido e robusto de APIs RESTful.
* **Gerenciamento de Dependências e Build**: **Apache Maven**.
* **Banco de Dados**: **PostgreSQL** (com gerenciamento via **pgAdmin 4**), para persistência de dados relacional.
* **Conteinerização**: **Docker**, garantindo ambientes de desenvolvimento e produção consistentes e portáteis.
* **Ambiente de Desenvolvimento**: **IntelliJ IDEA Ultimate Edition**.
* **Testes de API**: **Postman**, para validação de endpoints e interações.
* **Testes Unitários**: **JUnit 5**, utilizado para testar as regras de negócio nas classes de serviço.
* **Sistema Operacional para Desenvolvimento/Execução**: **Linux** (demonstrado em logs de execução e comandos).
* **Documentação Interativa da API**: **SpringDoc OpenAPI (Swagger UI)**, para fácil exploração e teste dos endpoints.
* **Mapeamento de Objetos**: **MapStruct**, para conversão eficiente entre DTOs e entidades.
* **Gerenciamento de Logs**: Configuração detalhada para depuração (`logback` via `application.properties`).
* **Segurança**: **Spring Security**, configurado para autenticação básica e controle de acesso a endpoints.

---

## Funcionalidades Detalhadas

1. **Gerenciamento de Pacientes**:
   * Cadastro de novos pacientes.
   * Atualização de dados pessoais existentes.
   * Exclusão de registros de pacientes.
   * Busca individual ou listagem de todos os pacientes.

2. **Gerenciamento de Médicos**:
   * Inserção de novos médicos.
   * Atualização de informações de médicos.
   * Exclusão de médicos por ID.
   * Busca de médicos por ID e listagem de todos.

3. **Gestão de Consultas**:
   * Adição e exclusão de agendamentos.
   * Marcação e atualização de status de consultas.
   * Controle e acompanhamento de horários e detalhes da consulta.

4. **Flexibilidade de Uso**:
   * Arquitetura adaptável para implantação em clínicas e hospitais de diferentes portes.

---

## Testes

O projeto incorpora uma robusta estratégia de testes para garantir a integridade e o correto funcionamento das regras de negócio. A experiência com **JUnit 5** foi fundamental para o desenvolvimento de testes unitários eficazes, focando nas classes de serviço (Service Layer).

* **JUnit 5**: Utilizado para escrever testes unitários para as classes de serviço, garantindo que a lógica de negócio, validações e interações com o repositório (simuladas ou reais) funcionem conforme o esperado. Isso inclui testes para:
    * Criação, leitura, atualização e exclusão (CRUD) de pacientes, médicos e consultas.
    * Validações de dados de entrada.
    * Cenários de sucesso e falha para cada operação.

---

## Instalação e Configuração

### Pré-requisitos

Certifique-se de ter os seguintes requisitos instalados e configurados em seu ambiente Linux:

* **Java Development Kit (JDK) 21 LTS**.
* **Docker** e **Docker Compose** (para conteinerização).
* **PostgreSQL** (ou utilize um container Docker para o banco de dados).
* **Apache Maven** (geralmente incluído nas IDEs ou como ferramenta de linha de comando).
* **IntelliJ IDEA** (ou sua IDE de preferência).
* **Postman** (ou ferramenta similar para teste de API).

### Passos para Configuração e Execução

1. **Clone este repositório**:
   ```bash
   git clone [https://github.com/seu-usuario/Management-of-medical-appointments.git](https://github.com/amosjuda/Management-of-medical-appointments.git)
   cd Management-of-medical-appointments
