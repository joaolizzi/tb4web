# Guia para apresentar o Calorias Diárias

Este roteiro é um apoio para a apresentação. O importante é demonstrar o sistema rodando e explicar o que cada parte faz com suas próprias palavras.

## Preparação

- Na pasta `backend`, iniciar com `mvn spring-boot:run`.
- Na pasta `frontend`, iniciar com `npm run dev`.
- Abrir `http://localhost:5173`.
- Se for mostrar a IA, configurar `GEMINI_API_KEY` **no terminal do backend** antes de iniciá-lo. Nunca exibir ou falar a chave.
- Deixar algumas refeições em datas diferentes para o histórico não ficar vazio.
- Executar `.\testar.ps1` na raiz antes da apresentação para verificar os testes do backend e o build do frontend.

## Roteiro de demonstração (3 a 5 minutos)

**1. Introdução — 30 segundos**

"Nosso projeto é um diário de calorias. A pessoa pode cadastrar o que comeu e acompanhar quantas calorias consumiu no dia. Fizemos com React no frontend, Spring Boot no backend e H2 para armazenar os dados."

**2. Cadastro e exclusão — 1 minuto**

- Selecionar uma data.
- Cadastrar uma refeição, como "Arroz, feijão e frango", com 550 kcal.
- Mostrar o total de calorias e a refeição na lista.
- Excluir a refeição e mostrar o total diminuindo.

**3. Histórico — 40 segundos**

- Abrir o histórico de dias.
- Mostrar o total de calorias de cada dia.
- Explicar: "A média soma as calorias dos dias cadastrados e divide pela quantidade de dias com registros. Os dias vazios não entram na conta."
- Clicar em um dia e mostrar suas refeições.

**4. Estimativa com IA — 40 segundos (opcional)**

- Descrever uma refeição com quantidades, por exemplo: "150g de arroz cozido, 100g de feijão e 150g de frango grelhado".
- Clicar em **Estimar com IA**.
- Mostrar o valor sugerido e o botão **Usar no cadastro**.
- Explicar: "O React envia o texto ao Spring Boot. O backend consulta o Gemini e devolve uma estimativa. A IA não salva nada automaticamente, e podemos corrigir as calorias antes de cadastrar."
- Se a API estiver sem cota ou sem internet, demonstrar apenas o cadastro manual.

**5. Código — 1 minuto**

Abrir estes arquivos no VS Code:

| Arquivo | O que explicar |
|---|---|
| `frontend/src/App.jsx` | Mostra a tela e faz as requisições à API com `fetch`. |
| `backend/src/main/java/br/edu/ifpr/calorias/controller/RefeicaoController.java` | Recebe requisições HTTP, como GET, POST e DELETE. |
| `backend/src/main/java/br/edu/ifpr/calorias/service/RefeicaoService.java` | Contém a lógica para cadastrar, excluir e calcular totais e média. |
| `backend/src/main/java/br/edu/ifpr/calorias/repository/RefeicaoRepository.java` | Usa JPA para acessar o banco. |
| `backend/src/main/java/br/edu/ifpr/calorias/model/Refeicao.java` | Representa os dados de uma refeição na tabela do banco. |
| `backend/src/main/java/br/edu/ifpr/calorias/service/EstimativaService.java` | Faz a chamada externa para o Gemini usando uma chave de ambiente. |

O fluxo principal é: **React → Controller → Service → Repository → banco H2**. A resposta percorre o caminho de volta.

## Perguntas que o professor pode fazer

**Por que Spring Boot?** Porque a disciplina pede uma API REST em Java. Ele facilita organizar as rotas e o acesso ao banco.

**O que é uma API REST?** É a parte do sistema que recebe requisições HTTP e devolve dados, geralmente em JSON.

**Por que separar Controller, Service e Repository?** Para deixar cada responsabilidade em um lugar: receber pedidos, aplicar regras e consultar o banco.

**Para que serve o H2?** É um banco simples que salva as refeições em arquivo, sem precisar instalar um servidor PostgreSQL.

**Como a média funciona?** Soma-se o total de calorias de cada data que contém registros e divide-se pela quantidade dessas datas. Se não houver datas, a média será zero.

**A IA sabe exatamente quantas calorias tem um prato?** Não. Ela faz uma aproximação a partir da descrição e das quantidades informadas.

**A chave do Gemini fica no React?** Não. Ela é lida como variável de ambiente somente pelo backend.

**O sistema precisa de internet?** O cadastro, o histórico e o banco local podem funcionar sem internet. A chamada à IA precisa acessar o serviço do Gemini.

**Como trabalharam em dupla?** Respondam de acordo com as tarefas que cada integrante realmente realizou. Não é necessário inventar contribuições ou alterar datas de commits.

## Última conferência

1. Fazer um cadastro manual e conferir a lista e o total.
2. Excluir um registro.
3. Atualizar a página e confirmar que o banco guardou os dados.
4. Selecionar dias diferentes e verificar a média do histórico.
5. Testar a IA se a chave e a conexão estiverem disponíveis.
6. Abrir o histórico de commits no GitHub e conseguir explicar as mudanças.
