# Requisitos do sistema

## Funcionais
- **RF01:** cadastrar refeição com nome, calorias e data.
- **RF02:** listar refeições de uma data escolhida.
- **RF03:** excluir uma refeição cadastrada.
- **RF04:** mostrar o total de calorias do dia.
- **RF05:** sugerir opcionalmente as calorias de uma refeição a partir de sua descrição por IA, sem cadastrar automaticamente.
- **RF06:** mostrar o histórico por data e a média de calorias dos dias com refeições registradas.

## Não funcionais
- **RNF01:** backend em Java com Spring Boot e API REST.
- **RNF02:** frontend em React consumindo a API.
- **RNF03:** dados persistidos em banco H2 local.
- **RNF04:** validar nome, calorias e data no cadastro.
- **RNF05:** interface simples e utilizável em celular e computador.
- **RNF06:** versionamento do código com Git.
- **RNF07:** a chave do Gemini deve ficar somente no backend, em variável de ambiente.

## Fora do escopo
Cadastro de usuários, login, edição de refeições, metas, água, relatórios complexos e outras funções de IA.
