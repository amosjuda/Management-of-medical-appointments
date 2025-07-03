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
* **Sistema Operacional para Desenvolvimento/Execução**: **Linux** (demonstrado em logs de execução e comandos).
* **Documentação Interativa da API**: **SpringDoc OpenAPI (Swagger UI)**, para fácil exploração e teste dos endpoints.
* **Mapeamento de Objetos**: **MapStruct**, para conversão eficiente entre DTOs e entidades.
* **Gerenciamento de Logs**: Configuração detalhada para depuração (`logback` via `application.properties`).
* **Segurança**: **Spring Security**, configurado para autenticação básica e controle de acesso a endpoints.

---

## Funcionalidades Detalhadas

1.  **Gerenciamento de Pacientes**:
    * Cadastro de novos pacientes.
    * Atualização de dados pessoais existentes.
    * Exclusão de registros de pacientes.
    * Busca individual ou listagem de todos os pacientes.

2.  **Gerenciamento de Médicos**:
    * Inserção de novos médicos.
    * Atualização de informações de médicos.
    * Exclusão de médicos por ID.
    * Busca de médicos por ID e listagem de todos.

3.  **Gestão de Consultas**:
    * Adição e exclusão de agendamentos.
    * Marcação e atualização de status de consultas.
    * Controle e acompanhamento de horários e detalhes da consulta.

4.  **Flexibilidade de Uso**:
    * Arquitetura adaptável para implantação em clínicas e hospitais de diferentes portes.

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

1.  **Clone este repositório**:
    ```bash
    git clone [https://github.com/seu-usuario/Management-of-medical-appointments.git](https://github.com/seu-usuario/Management-of-medical-appointments.git)
    cd Management-of-medical-appointments
    ```

2.  **Configuração do Banco de Dados**:
    * Certifique-se de que sua instância PostgreSQL está em execução e configurada conforme `src/main/resources/application.properties` (ou configure-a via Docker Compose, se preferir).

3.  **Construa o Projeto Maven**:
    ```bash
    ./mvnw clean package -DskipTests
    ```
    Este comando compilará o projeto e gerará o arquivo JAR executável na pasta `target/`.

4.  **Execute a Aplicação via Docker (Método Recomendado)**:
    * Crie um arquivo `Dockerfile` na raiz do projeto com o seguinte conteúdo:
        ```dockerfile
        # Usa uma imagem base oficial do OpenJDK 21
        FROM openjdk:21-jdk-slim

        # Define o diretório de trabalho dentro do container
        WORKDIR /app

        # Copia o arquivo JAR compilado para o container
        COPY target/Management-of-medical-appointments-0.0.1-SNAPSHOT.jar app.jar

        # Expõe a porta que a aplicação Spring Boot usa
        EXPOSE 8080

        # Comando para executar a aplicação quando o container é iniciado
        ENTRYPOINT ["java","-jar","app.jar"]
        ```
    * Construa a imagem Docker:
        ```bash
        docker build -t medical-appointments-app .
        ```
        (O `.` no final significa "construir a imagem usando o Dockerfile neste diretório atual").

    * Execute o container Docker, mapeando a porta 8080:
        ```bash
        docker run -p 8080:8080 medical-appointments-app
        ```
        (O `-p 8080:8080` mapeia a porta 8080 do seu computador para a porta 8080 dentro do container).

    * *Opcional (e altamente recomendado para orquestração):* Considere usar **Docker Compose** para gerenciar o container da aplicação e do banco de dados juntos.

5.  **Teste a API**:
    * Com a aplicação em execução (seja diretamente via JAR ou em um container Docker), use o Postman para interagir com os endpoints da API em `http://localhost:8080/`.
    * Acesse a documentação interativa da API via Swagger UI em: `http://localhost:8080/swagger-ui.html`.

### Execução Direta na IDE (Alternativa para Desenvolvimento)

* Abra o projeto no IntelliJ IDEA.
* Localize a classe principal do aplicativo (e.g., `ManagementOfMedicalAppointmentsApplication.java`).
* Execute-a diretamente da IDE para um ciclo de desenvolvimento mais rápido, aproveitando o hot-reload e o depurador.

---

## Capturas de Tela e Recursos Visuais

-   **Configurações e Dependências do Projeto (Spring Initializr)**:
    [https://github.com/amosjuda/Management-of-medical-appointments/blob/main/src/images/Spring-Initializr-%5Bstart.spring.io%5D.png](https://github.com/amosjuda/Management-of-medical-appointments/blob/main/src/images/Spring-Initializr-%5Bstart.spring.io%5D.png)

-   **Testes Bem-sucedidos no Postman**:
    [https://github.com/amosjuda/Management-of-medical-appointments/tree/main/src/images](https://github.com/amosjuda/Management-of-medical-appointments/tree/main/src/images)

---

## Aceitação de Feedbacks e Contribuições

Contribuições para o desenvolvimento do sistema são muito bem-vindas!
Se você tiver sugestões, correções, melhorias ou identificar oportunidades, não hesite em abrir uma [issue](https://github.com/amosjuda/Management-of-medical-appointments/issues) ou enviar um [pull request](https://github.com/amosjuda/Management-of-medical-appointments/pulls).

Agradecemos sua colaboração para tornar este projeto ainda melhor.

### Licença

Este projeto é de código aberto e está sob a licença [MIT License](https://opensource.org/licenses/MIT) (ou outra licença que você deseje especificar). Sinta-se à vontade para utilizar, modificar e distribuir conforme necessário.
