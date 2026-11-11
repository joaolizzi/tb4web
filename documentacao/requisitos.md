# Requisitos do sistema

Prioridades: **Alta** (essencial para o funcionamento do sistema), **Média** (importante, mas o sistema funciona sem ela) e **Baixa** (apoio ou conforto de uso).

## Requisitos funcionais

| ID | Descrição | Prioridade |
|---|---|---|
| RF01 | O sistema deve permitir cadastrar uma refeição informando nome, calorias e data. | Alta |
| RF02 | O sistema deve listar as refeições da data escolhida, da mais recente para a mais antiga. | Alta |
| RF03 | O sistema deve permitir excluir uma refeição cadastrada. | Alta |
| RF04 | O sistema deve mostrar o total de calorias consumidas no dia selecionado. | Alta |
| RF05 | O sistema deve mostrar o histórico dos dias com refeições, com o total de calorias e a quantidade de refeições de cada dia, e permitir abrir um dia ao clicar nele. | Média |
| RF06 | O sistema deve calcular a média diária de calorias considerando somente os dias com refeições registradas. | Média |
| RF07 | O sistema deve estimar, opcionalmente, as calorias de uma refeição a partir da sua descrição, usando IA (Gemini), sem cadastrar a refeição automaticamente. | Média |
| RF08 | O sistema deve permitir preencher o formulário de cadastro com a estimativa da IA, para que a pessoa confira e corrija o valor antes de salvar. | Média |
| RF09 | O sistema deve permitir registrar quantidades de água em ml, com atalhos de 250 ml e 500 ml e campo para outra quantidade. | Média |
| RF10 | O sistema deve listar os registros de água da data escolhida e mostrar o total do dia em ml, com o progresso em relação à meta visual de 3.000 ml. | Média |
| RF11 | O sistema deve permitir excluir um registro de água. | Média |

## Requisitos não funcionais

| ID | Descrição | Prioridade |
|---|---|---|
| RNF01 | O backend deve ser uma API REST desenvolvida em Java 21 com Spring Boot, organizada nas camadas Controller, Service, Repository, Model e DTO. | Alta |
| RNF02 | O frontend deve ser desenvolvido em React com Vite e consumir a API REST do backend. | Alta |
| RNF03 | Os dados devem ser persistidos em um banco H2 gravado em arquivo local, sem necessidade de instalar outro banco. | Alta |
| RNF04 | O backend deve validar os dados recebidos: nome da refeição obrigatório com até 150 caracteres, calorias inteiras maiores ou iguais a zero, quantidade de água maior que zero e data obrigatória. Dados inválidos devem ser rejeitados com erro 400. | Alta |
| RNF05 | O backend deve ter testes automatizados que cubram o cadastro, a listagem, a exclusão, o total, o histórico e a validação de refeições e de água. | Média |
| RNF06 | A chave da API do Gemini deve ficar somente no backend, em variável de ambiente, sem ser versionada nem enviada ao navegador. | Alta |
| RNF07 | Se a IA estiver indisponível, sem chave ou sem cota, o sistema deve mostrar uma mensagem de erro clara e manter o cadastro manual funcionando. | Média |
| RNF08 | A interface deve ser simples, em página única, e utilizável em computador e celular, com os painéis reorganizados em uma coluna em telas pequenas. | Média |
| RNF09 | O sistema deve informar a pessoa sobre o resultado das ações (cadastro, exclusão ou erro) com mensagens na própria tela. | Baixa |
| RNF10 | O código deve ser versionado com Git, com commits feitos ao longo de todo o desenvolvimento. | Alta |

## Fora do escopo
Cadastro de usuários, login, edição de refeições, metas nutricionais personalizadas, relatórios complexos e outras funções de IA.