# Casos de uso — Calorias Diárias

Os casos de uso abaixo mostram o que o usuário poderá fazer no sistema. Todos descrevem o comportamento previsto para o trabalho; durante a implementação, vamos atualizar os fluxos se alguma funcionalidade mudar.

## Atores

- **Visitante:** pessoa que ainda não entrou no sistema.
- **Usuário:** pessoa cadastrada e autenticada, que acompanha seus próprios registros.

## UC01 — Criar conta

**Ator:** Visitante  
**Requisito relacionado:** RF01

**Pré-condição:** Não ter uma conta com o e-mail informado.

**Fluxo principal:**
1. O visitante acessa a tela de cadastro.
2. Informa os dados solicitados, incluindo e-mail e senha.
3. O sistema valida os campos.
4. O sistema cria a conta e informa que o cadastro foi concluído.

**Fluxos alternativos e exceções:**
- Se algum campo obrigatório estiver vazio ou inválido, o sistema informa o problema e solicita a correção.
- Se o e-mail já estiver cadastrado, o sistema informa que não é possível criar outra conta com ele.

## UC02 — Entrar e sair da conta

**Ator:** Usuário  
**Requisito relacionado:** RF02

**Pré-condição:** Ter uma conta cadastrada.

**Fluxo principal:**
1. O usuário abre a tela de login.
2. Informa e-mail e senha.
3. O sistema confere as credenciais e libera o acesso à área principal.
4. Quando desejar, o usuário escolhe a opção de sair.
5. O sistema encerra a sessão e retorna à tela de entrada.

**Fluxos alternativos e exceções:**
- Se o e-mail ou a senha estiverem incorretos, o acesso é negado e uma mensagem é exibida.
- Se houver falha na comunicação com o servidor, o sistema informa que não conseguiu concluir a operação.

## UC03 — Definir ou alterar as metas diárias

**Ator:** Usuário  
**Requisitos relacionados:** RF03 e RF08

**Pré-condição:** Estar autenticado.

**Fluxo principal:**
1. O usuário acessa a área de metas.
2. Informa a quantidade de calorias que deseja consumir por dia.
3. Informa também a meta diária de água, em mililitros.
4. Confirma as alterações.
5. O sistema salva os valores e passa a utilizá-los nos resumos diários.

**Fluxos alternativos e exceções:**
- Se um valor for inválido, como um número negativo ou igual a zero, o sistema pede a correção.
- Se o salvamento falhar, o sistema mostra uma mensagem de erro e não apresenta os novos valores como salvos.

## UC04 — Registrar uma refeição

**Ator:** Usuário  
**Requisito relacionado:** RF04

**Pré-condição:** Estar autenticado.

**Fluxo principal:**
1. O usuário abre a opção de adicionar refeição.
2. Informa o nome da refeição ou alimento, a quantidade de calorias e a data.
3. Confirma o cadastro.
4. O sistema salva a refeição vinculada ao usuário.
5. O registro aparece na lista do dia correspondente.

**Fluxos alternativos e exceções:**
- Se o nome não for informado ou as calorias forem inválidas, o sistema indica o campo que precisa ser corrigido.
- Se houver erro ao salvar, o sistema avisa e mantém os dados preenchidos para uma nova tentativa.

## UC05 — Consultar as refeições e o total do dia

**Ator:** Usuário  
**Requisitos relacionados:** RF05 e RF07

**Pré-condição:** Estar autenticado.

**Fluxo principal:**
1. O usuário abre o painel principal.
2. Seleciona a data que deseja consultar; inicialmente, é mostrado o dia atual.
3. O sistema busca as refeições registradas naquela data.
4. Exibe a lista e soma as calorias dos registros.
5. Mostra o total consumido em comparação à meta diária.

**Fluxos alternativos e exceções:**
- Se não houver refeições para a data, o sistema apresenta a lista vazia e o total de zero calorias.
- Se não existir uma meta definida, o sistema mostra o total consumido e orienta o usuário a configurar a meta.
- Se a consulta falhar, o sistema exibe uma mensagem de erro.

## UC06 — Editar uma refeição

**Ator:** Usuário  
**Requisito relacionado:** RF06

**Pré-condição:** Estar autenticado e ter uma refeição cadastrada.

**Fluxo principal:**
1. O usuário encontra uma refeição na lista.
2. Escolhe a opção de editar.
3. Altera os dados desejados e confirma.
4. O sistema verifica se a refeição pertence ao usuário e valida as informações.
5. Salva as alterações e atualiza o total de calorias do dia.

**Fluxos alternativos e exceções:**
- Se os novos dados forem inválidos, o sistema solicita a correção.
- Se a refeição não existir ou não pertencer ao usuário, a alteração não é permitida.

## UC07 — Excluir uma refeição

**Ator:** Usuário  
**Requisito relacionado:** RF06

**Pré-condição:** Estar autenticado e ter uma refeição cadastrada.

**Fluxo principal:**
1. O usuário escolhe a opção de excluir em uma refeição.
2. O sistema solicita a confirmação.
3. O usuário confirma a exclusão.
4. O sistema verifica se o registro pertence ao usuário e o remove.
5. A lista e o total de calorias são atualizados.

**Fluxos alternativos e exceções:**
- Se o usuário cancelar a confirmação, a refeição continua cadastrada.
- Se a refeição não existir ou não pertencer ao usuário, a exclusão não é realizada.

## UC08 — Registrar o consumo de água

**Ator:** Usuário  
**Requisitos relacionados:** RF09 e RF10

**Pré-condição:** Estar autenticado.

**Fluxo principal:**
1. O usuário acessa a área de hidratação.
2. Informa a quantidade de água consumida, em mililitros.
3. Confirma o registro.
4. O sistema salva o consumo na data correspondente.
5. O sistema atualiza o total diário e compara o valor à meta de água, quando houver uma meta definida.

**Fluxos alternativos e exceções:**
- Se a quantidade informada for inválida, o registro não é salvo e o sistema solicita outro valor.
- Se ainda não houver meta de água, o consumo continua sendo registrado e o sistema mostra apenas o total.

## UC09 — Consultar registros anteriores

**Ator:** Usuário  
**Requisito relacionado:** RF11

**Pré-condição:** Estar autenticado.

**Fluxo principal:**
1. O usuário acessa a consulta por data.
2. Escolhe um dia anterior.
3. O sistema busca os registros de refeições e de água daquela data.
4. Exibe os registros disponíveis e os totais do dia.

**Fluxos alternativos e exceções:**
- Se não houver registros na data escolhida, o sistema informa que não encontrou dados.
- Se ocorrer um problema na consulta, o sistema mostra uma mensagem de erro.

## UC10 — Visualizar o histórico de calorias

**Ator:** Usuário  
**Requisito relacionado:** RF12

**Pré-condição:** Estar autenticado.

**Fluxo principal:**
1. O usuário acessa a área de histórico.
2. O sistema busca as calorias registradas nos últimos dias.
3. Agrupa os valores por data.
4. Apresenta um resumo visual da evolução do consumo.

**Fluxos alternativos e exceções:**
- Se houver poucos registros, o sistema apresenta somente os dias com dados disponíveis.
- Se não houver nenhum registro, o sistema mostra uma orientação para começar a registrar as refeições.

## Regras gerais

- Cada usuário só pode visualizar e modificar os próprios dados.
- As informações enviadas pelo frontend precisam ser validadas pelo backend.
- O sistema não deve confirmar uma operação antes de receber a resposta de sucesso da API.
- Caso alguma funcionalidade prevista aqui não seja implementada na versão final, este documento deverá ser ajustado antes da entrega.
