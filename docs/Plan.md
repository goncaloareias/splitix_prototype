# Planeamento de Desenvolvimento - Aplicação Splitix

## 🧩 Visão Geral

Splitix permite a criação de grupos de utilizadores que registam despesas em conjunto e sabem, em tempo real, quem deve quanto a quem.

## 🗂️ Épicos e User Stories

### 1. Autenticação e Gestão de Utilizadores

- **US1** - Como utilizador, quero poder registar-me para poder usar a aplicação.
- **US2** - Como utilizador, quero autenticar-me com email e password para aceder à minha conta.
- **US3** - Como utilizador, quero recuperar a password caso a esqueça.
- **US4** - Como utilizador autenticado, quero ver e editar o meu perfil.

### 2. Gestão de Grupos

- **US26** - Como utilizador, quero adicionar amigos para os poder incluir em grupos ou dividir despesas.
- **US5** - Como utilizador, quero criar um grupo para partilhar despesas com amigos.
- **US6** - Como utilizador, quero adicionar ou remover membros do meu grupo.
- **US7** - Como utilizador, quero visualizar os grupos em que participo.

### 3. Registo e Partilha de Despesas

- **US8** - Como membro de grupo, quero adicionar uma despesa para dividir com os restantes.
- **US9** - Como membro de grupo, quero escolher quem participou na despesa.
- **US10** - Como membro, quero dividir a despesa de forma igual ou personalizada.
- **US11** - Como membro, quero ver o histórico de despesas do grupo.

### 4. Cálculo e Gestão de Saldos

- **US12** - Como membro, quero ver o saldo de cada pessoa (quem deve a quem).
- **US13** - Como membro, quero ver um resumo visual das dívidas do grupo.
- **US14** - Como membro, quero registar pagamentos manuais entre membros.
- **US15** - Como membro, quero exportar o histórico de despesas (PDF/Excel).

### 5. Notificações e Feedback

- **US16** - Como utilizador, quero ser notificado quando sou adicionado a um grupo.
- **US17** - Como utilizador, quero ser notificado quando me é atribuída uma nova despesa.
- **US18** - Como utilizador, quero feedback visual ao adicionar despesas e pagamentos.

### 6. Frontend / Interface do Utilizador

- **US19** - Como utilizador, quero uma UI simples para navegar entre grupos e despesas.
- **US20** - Como utilizador, quero ver gráficos e ícones intuitivos.
- **US21** - Como novo utilizador, quero uma página de onboarding com explicações.

### 7. Administração e Manutenção (para ti)

- **US22** - Como developer, quero configurar o projeto com Spring Boot, H2 e JPA.
- **US23** - Como developer, quero organizar a arquitetura em `application`, `domain`, `persistence`, etc.
- **US24** - Como developer, quero guardar os dados em ficheiros com H2 persistente.
- **US25** - Como developer, quero criar testes unitários para os serviços principais.

## 📌 Sugestão de Organização (Kanban)

### To Do
- US1 - Registo de Utilizador
- US5 - Criar Grupo
- US8 - Adicionar Despesa

### Em Progresso
- US22 - Setup Spring Boot + H2
- US23 - Organização da arquitetura

### Feito
- GitHub Repository criado
- README.md + LICENSE adicionados

## 🛠️ Tecnologias Planeadas

- Backend: Java + Spring Boot + JPA + H2
- Frontend: React (futuramente)
- Autenticação: Spring Security + JWT
- Extras futuros: Email, Notificações, Exportações, MB Way (mock)
