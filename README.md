# Calorias Diárias

Trabalho de Programação Web: uma aplicação simples para registrar refeições e somar as calorias consumidas em cada dia.

## Tecnologias

- Backend: Java 21, Spring Boot, Spring Data JPA e H2
- Frontend: React e Vite
- Banco H2 gravado em arquivo, sem instalação de PostgreSQL

## Como executar

É necessário ter Java 21, Maven e Node.js instalados.

**1. Backend:** abra um terminal na pasta `backend` e execute:

```bash
mvn spring-boot:run
```

**2. Frontend:** em outro terminal, na pasta `frontend`, execute:

```bash
npm install
npm run dev
```

Acesse `http://localhost:5173`. O Vite encaminha as chamadas `/api` para o Spring Boot em `http://localhost:8080`.

## Funcionalidades

- Cadastrar uma refeição com nome, calorias e data
- Listar as refeições de uma data
- Excluir uma refeição
- Ver a soma de calorias do dia

## Endpoints

| Método | Caminho | Descrição |
|---|---|---|
| GET | `/api/refeicoes?data=2026-11-03` | Lista as refeições do dia |
| POST | `/api/refeicoes` | Cadastra refeição |
| DELETE | `/api/refeicoes/{id}` | Exclui refeição |
| GET | `/api/refeicoes/total?data=2026-11-03` | Retorna o total do dia |

Exemplo do corpo do POST:

```json
{"nome":"Almoço","calorias":550,"data":"2026-11-03"}
```

O banco é criado automaticamente na pasta `backend/dados` quando a API inicia.

Este projeto não possui login nem separação de dados por usuário. Foi feito para uma demonstração acadêmica local, não para uso público.
