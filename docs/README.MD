# 💸 Splitix

Splitix é uma aplicação web para **gestão de despesas partilhadas** entre grupos de pessoas. Ideal para colegas de casa, viagens em grupo, jantares de amigos ou qualquer situação em que seja necessário dividir contas de forma justa e automática.

---

## 🚀 Funcionalidades

- 👥 Criação de grupos de despesas
- 💰 Registo de despesas por grupo, com divisão automática ou personalizada
- 📊 Cálculo automático de saldos entre utilizadores (quem deve a quem)
- 🔔 Notificações (em implementação)
- 📦 Exportação de despesas e pagamentos (futuramente)
- 🔐 Autenticação com JWT (a implementar)

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Security (futuramente)
- **Base de dados H2 (em memória/local)**

### Frontend (a definir)
- Pretendido: **React**, **Tailwind CSS** (ou outro stack moderno)

---

## 🧱 Estrutura do Projeto

```
splitix/
├── application/       # Casos de uso (use cases, controladores)
├── domain/            # Entidades de negócio
├── persistence/       # Repositórios JPA e acesso a dados
├── services/          # Lógica de apoio (ex: cálculo de saldos)
├── user_management/   # Autenticação e gestão de utilizadores
└── resources/
    └── application.properties
```

---

## 🧪 Como correr o projeto

1. Clonar o repositório:
   ```bash
   git clone https://github.com/goncaloareias/splitix.git
   cd splitix
   ```

2. Abrir no IntelliJ ou outro IDE com suporte a Spring Boot

3. Executar a aplicação:
    - `SplitixApplication.java` (com botão de Run)
    - Ou via terminal:
      ```bash
      ./mvnw spring-boot:run
      ```

4. Aceder à consola H2 (opcional):
    - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `jdbc:h2:./data/shodrone`

---

## 📚 Roadmap (a implementar)

- [ ] Login e registo com Spring Security + JWT
- [ ] UI com React ou outra framework
- [ ] Notificações automáticas
- [ ] Pagamentos manuais e registo de transferências
- [ ] Exportação de histórico (PDF/Excel)
- [ ] Integração MB Way (mock)

---

## 👤 Autor

Desenvolvido por [Gonçalo Areias](https://github.com/goncaloareias) no âmbito de aprendizagem em Java Spring Boot.

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT.  
Ver o ficheiro `LICENSE` para mais detalhes.
