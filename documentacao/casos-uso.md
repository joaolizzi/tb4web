# Casos de uso

**Ator principal:** pessoa utilizando o site. Não é necessário fazer login.

**Ator secundário:** serviço externo Gemini, acionado somente no UC04.

**Pré-condição geral (vale para todos os casos de uso):** o backend Spring Boot e o frontend React estão em execução e o banco H2 está disponível.

| Caso de uso | Requisitos relacionados |
|---|---|
| UC01 — Cadastrar refeição | RF01, RNF04 |
| UC02 — Consultar refeições do dia | RF02, RF04 |
| UC03 — Excluir refeição | RF03 |
| UC04 — Estimar calorias com IA | RF07, RF08, RNF06, RNF07 |
| UC05 — Consultar histórico | RF05, RF06 |
| UC06 — Registrar hidratação | RF09, RNF04 |
| UC07 — Consultar hidratação do dia | RF10 |
| UC08 — Excluir registro de água | RF11 |

## UC01 — Cadastrar refeição
**Pré-condições:** uma data está selecionada na tela (por padrão, a data de hoje).

**Fluxo principal**
1. A pessoa informa o nome da refeição e as calorias.
2. Clica em **Adicionar refeição**.
3. O sistema valida os campos e salva a refeição na data selecionada.
4. O sistema limpa o formulário, mostra a mensagem de sucesso e atualiza a lista, o total do dia e o histórico.

**Fluxos alternativos e exceções**
- **A1 — Campo vazio ou inválido:** se o nome estiver em branco, ou as calorias estiverem vazias, negativas ou não forem um número inteiro, a tela mostra uma mensagem pedindo o preenchimento e nada é salvo.
- **A2 — Dados rejeitados pela API:** se a requisição chegar ao backend com dados inválidos (nome em branco ou com mais de 150 caracteres, calorias negativas ou data ausente), a API responde com erro 400, a tela informa que não foi possível salvar e a refeição não é cadastrada.
- **A3 — Backend indisponível:** a tela mostra a mensagem de erro e os dados digitados permanecem no formulário.

**Pós-condição:** a refeição fica gravada no banco e aparece na lista da data selecionada.

## UC02 — Consultar refeições do dia
**Pré-condições:** nenhuma além da pré-condição geral.

**Fluxo principal**
1. A pessoa abre a página ou escolhe outra data no campo **Data selecionada**.
2. O sistema consulta as refeições cadastradas para aquele dia.
3. O sistema mostra a lista, da mais recente para a mais antiga, e o total de calorias do dia.

**Fluxos alternativos e exceções**
- **A1 — Dia sem refeições:** a tela mostra a mensagem "Nenhuma refeição por aqui" e o total do dia é zero.
- **A2 — Backend indisponível:** a tela mostra a mensagem "Não foi possível carregar as refeições."

**Pós-condição:** a tela exibe as refeições e o total da data escolhida.

## UC03 — Excluir refeição
**Pré-condições:** existe ao menos uma refeição na lista do dia selecionado.

**Fluxo principal**
1. A pessoa clica em **Excluir** ao lado de uma refeição.
2. O sistema remove o registro do banco.
3. O sistema mostra a mensagem "Refeição excluída." e atualiza a lista, o total do dia e o histórico.

**Fluxos alternativos e exceções**
- **A1 — Registro inexistente:** se a refeição já tiver sido removida, a API responde com erro 404 e a tela mostra a mensagem "Não foi possível excluir a refeição."
- **A2 — Backend indisponível:** a tela mostra a mesma mensagem de erro e a lista não é alterada.

**Pós-condição:** a refeição deixa de existir no banco e o total do dia é recalculado.

## UC04 — Estimar calorias com IA
**Pré-condições:** o backend foi iniciado com a variável de ambiente `GEMINI_API_KEY` configurada e há acesso à internet. Sem isso, o cadastro manual (UC01) continua disponível.

**Fluxo principal**
1. A pessoa digita, no painel **Estimativa com IA**, a descrição da refeição (até 300 caracteres), de preferência com quantidades e preparo.
2. Clica em **Estimar com IA**.
3. O React envia a descrição ao backend Spring Boot.
4. O backend consulta o Gemini e recebe uma estimativa de calorias.
5. O sistema mostra a estimativa no painel de IA, com o aviso de que o valor é aproximado.
6. A pessoa clica em **Usar no cadastro**. O formulário manual é preenchido com a descrição como nome e a estimativa como calorias.
7. A pessoa confere ou corrige os valores e segue o UC01 para salvar a refeição.

**Fluxos alternativos e exceções**
- **A1 — Descrição vazia:** o botão de estimar fica desabilitado enquanto não houver texto.
- **A2 — Chave ausente ou inválida:** o backend responde com erro 503 e a tela mostra uma mensagem pedindo para conferir a `GEMINI_API_KEY`.
- **A3 — Limite de uso atingido:** o backend responde com erro 429 e a tela sugere tentar mais tarde ou verificar a cota no Google AI Studio.
- **A4 — Modelo indisponível, Gemini fora do ar ou sem resposta:** o backend responde com erro 502 e a tela mostra uma mensagem explicando a falha.
- **A5 — Resposta inválida da IA:** se o Gemini não devolver um número inteiro, o sistema descarta a resposta e pede para detalhar melhor a refeição.
- **A6 — Qualquer falha da IA:** a pessoa pode digitar as calorias manualmente no formulário.

**Pós-condição:** nenhuma refeição é salva automaticamente. Apenas o UC01, acionado pela pessoa, grava dados no banco.

A estimativa é aproximada e depende da descrição enviada. A descrição só é enviada ao Gemini quando a pessoa pede a estimativa.

## UC05 — Consultar histórico
**Pré-condições:** nenhuma além da pré-condição geral.

**Fluxo principal**
1. A pessoa abre a página e vê o painel **Histórico de dias**.
2. O sistema lista os dias que têm refeições, do mais recente para o mais antigo, com o total de calorias, a quantidade de refeições e uma barra de comparação.
3. O sistema mostra nos cartões de resumo a média diária de calorias e a quantidade de dias registrados.
4. A pessoa clica em um dia do histórico.
5. O sistema seleciona aquela data e mostra as refeições do dia (UC02).

**Fluxos alternativos e exceções**
- **A1 — Sem refeições cadastradas:** a média e a quantidade de dias são zero e o painel mostra a mensagem "Seu histórico começa aqui".
- **A2 — Backend indisponível:** a tela mostra a mensagem "Não foi possível carregar o histórico."

**Pós-condição:** a média considera somente os dias com refeições registradas, e não todos os dias do calendário.

## UC06 — Registrar hidratação
**Pré-condições:** uma data está selecionada na tela (por padrão, a data de hoje).

**Fluxo principal**
1. A pessoa escolhe um atalho (**+ 250 ml**, **+ 500 ml**, **+ 600 ml** ou **+ 1000 ml**) ou digita outra quantidade em ml e clica em **Registrar**.
2. O sistema valida a quantidade e salva o registro na data selecionada.
3. O sistema mostra a mensagem de sucesso e atualiza a lista, o total de água do dia e o progresso em relação à meta visual de 3.000 ml.

**Fluxos alternativos e exceções**
- **A1 — Quantidade inválida:** se a quantidade não for um número inteiro entre 1 e 10.000 ml, a tela mostra uma mensagem e nada é salvo.
- **A2 — Dados rejeitados pela API:** se a API receber uma quantidade igual ou menor que zero, ou sem data, responde com erro 400 e o registro não é criado.
- **A3 — Backend indisponível:** a tela mostra a mensagem "Não foi possível registrar a água."

**Pós-condição:** o registro fica gravado no banco e entra no total do dia.

## UC07 — Consultar hidratação do dia
**Pré-condições:** nenhuma além da pré-condição geral.

**Fluxo principal**
1. A pessoa abre a página ou escolhe outra data no campo **Data selecionada**.
2. O sistema consulta os registros de água daquele dia.
3. O sistema mostra a lista de registros, o total em ml e a barra de progresso da meta de 3.000 ml.

**Fluxos alternativos e exceções**
- **A1 — Dia sem registros:** a tela mostra a mensagem "Nenhum consumo registrado" e o total é zero.
- **A2 — Meta superada:** a barra de progresso para em 100%, mas o total continua mostrando a quantidade real consumida.
- **A3 — Backend indisponível:** a tela mostra a mensagem "Não foi possível carregar os registros de água."

**Pós-condição:** a tela exibe os registros e o total de água da data escolhida. A meta de 3.000 ml é apenas uma referência inicial do aplicativo.

## UC08 — Excluir registro de água
**Pré-condições:** existe ao menos um registro de água na lista do dia selecionado.

**Fluxo principal**
1. A pessoa clica em **Excluir** ao lado de um registro feito por engano.
2. O sistema remove o registro do banco.
3. O sistema mostra a mensagem "Registro de água excluído." e atualiza a lista e o total do dia.

**Fluxos alternativos e exceções**
- **A1 — Registro inexistente:** a API responde com erro 404 e a tela mostra a mensagem "Não foi possível excluir o registro de água."
- **A2 — Backend indisponível:** a tela mostra a mesma mensagem de erro e a lista não é alterada.

**Pós-condição:** o registro deixa de existir no banco e o total de água do dia é recalculado.