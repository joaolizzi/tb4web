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
- Consultar o histórico dos dias com refeições, seus totais e a média diária
- Pedir ao Gemini uma estimativa opcional de calorias pela descrição da refeição, em um painel separado

## Habilitar a estimativa com IA (opcional)

Crie uma chave da API Gemini no [Google AI Studio](https://aistudio.google.com/apikey). No **PowerShell**, antes de iniciar o backend, execute:

```powershell
$env:GEMINI_API_KEY="SUA_CHAVE_AQUI"
mvn spring-boot:run
```

Execute esses comandos dentro da pasta `backend`. Não cole sua chave em arquivos versionados nem a envie a terceiros.

O modelo padrão é `gemini-3.8-flash`, escolhido para melhorar a estimativa. Se sua chave não tiver acesso a ele ou a cota for insuficiente, você pode usar o modelo mais leve:

```powershell
$env:GEMINI_MODEL="gemini-3.5-flash-lite"
```

Sem a chave, o cadastro manual continua funcionando normalmente. A IA gera apenas uma estimativa, não salva refeições automaticamente e pode errar; confirme o valor antes de adicionar. No painel de IA, clique em **Usar no cadastro** para preencher os campos do formulário manual.

Se você já havia configurado `GEMINI_MODEL` no terminal para o modelo antigo, mude essa variável para `gemini-3.8-flash` e reinicie o backend. Confira o acesso, a cota e eventuais cobranças do seu projeto no Google AI Studio.

## Endpoints

| Método | Caminho | Descrição |
|---|---|---|
| GET | `/api/refeicoes?data=2026-11-03` | Lista as refeições do dia |
| POST | `/api/refeicoes` | Cadastra refeição |
| DELETE | `/api/refeicoes/{id}` | Exclui refeição |
| GET | `/api/refeicoes/total?data=2026-11-03` | Retorna o total do dia |
| GET | `/api/refeicoes/historico` | Retorna os dias com registros e a média diária |
| POST | `/api/ia/estimar` | Retorna estimativa de calorias |

Exemplo do corpo do POST:

```json
{"nome":"Almoço","calorias":550,"data":"2026-11-03"}
```

Exemplo do corpo da estimativa: `{"descricao":"150g de arroz, 100g de feijão e 150g de frango"}`. O retorno é `{"calorias":580}` (número ilustrativo; o valor real depende da IA).

A média do histórico considera **somente os dias com refeições registradas**, não todos os dias do calendário.

O banco é criado automaticamente na pasta `backend/dados` quando a API inicia.

Este projeto não possui login nem separação de dados por usuário. Foi feito para uma demonstração acadêmica local, não para uso público.
