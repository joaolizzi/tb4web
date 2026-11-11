# Interface do sistema

A aplicação tem uma única página, com tema escuro, cartões arredondados e um destaque em verde. A interface é em português do Brasil: datas e números seguem o formato `pt-BR` (por exemplo, `03 de nov. de 2026` e `1.250 kcal`). O layout se adapta a computador e celular.

## Identidade visual

### Paleta de cores
| Uso | Cor |
|---|---|
| Fundo da página | `#080c12`, com um leve brilho verde no canto superior direito |
| Cartões e painéis | degradê de `#121a25` para `#111720`, com borda `#25313d` |
| Texto principal | `#f3f6fc` |
| Texto secundário | `#96a4b7` e `#8595a8` |
| Campos de formulário | fundo `#0c121b`, borda `#303e4c` e borda `#47caa0` quando em foco |
| Destaque e botão principal (alimentação) | verde `#34d399`, com hover `#65e9ba` e texto escuro `#063b2c` |
| Estimativa com IA | violeta, com botão em degradê de `#7961d6` para `#9773e9` e textos em `#c4b5fd` |
| Hidratação | azul, com barra de progresso de `#38aee2` para `#69d5ed` e textos em `#7dd3fc` |
| Erro e exclusão | vermelho suave: texto `#fecaca` sobre `#361e27` nas mensagens e `#fda4af` sobre `#271e27` no botão Excluir |
| Sucesso | texto `#a7f3d0` sobre `#102c25` |

Cada cor tem um significado fixo: **verde** para alimentação e ações principais, **violeta** para a IA, **azul** para a água e **vermelho** para erros e exclusões. Assim, a pessoa identifica o assunto de cada painel pela cor.

### Tipografia
- **Fonte:** Inter, com alternativas do sistema (`ui-sans-serif`, `system-ui`, `Segoe UI` e `sans-serif`) caso a Inter não esteja instalada no computador.
- **Título principal (h1):** de 35 a 56 px conforme a largura da tela, peso 800 e espaçamento entre letras negativo.
- **Títulos de painel (h2):** 18 px nos formulários e 21 px nas seções de refeições, histórico e hidratação, em negrito.
- **Valores dos cartões de resumo:** de 29 a 40 px, peso 800, com a unidade (`kcal`, `ml`) em tamanho menor ao lado.
- **Rótulos de campos:** 13 px, peso 700.
- **Textos de apoio, dicas e notas:** de 10 a 12 px, em cor secundária.
- **Chamadas de seção:** 11 px, em maiúsculas, com espaçamento largo, em verde claro (por exemplo, "DIÁRIO ALIMENTAR").

### Ícones
A interface usa símbolos de texto (`✳`, `◉`, `◈`, `▦`, `＋`, `✦`, `☷`, `▥`, `💧`) em vez de uma biblioteca de ícones, para manter o projeto simples e sem dependências extras. Eles aparecem em quadrados de cantos arredondados com fundo na cor do assunto.

## Organização da tela
A página tem largura máxima de 1210 px, centralizada, e é composta por estes blocos, de cima para baixo:

1. **Barra superior:** marca "calorias diárias" com o ícone, e a etiqueta "Seu diário alimentar".
2. **Introdução:** título "Alimente-se bem. Organize seu dia." e uma frase curta explicando o uso.
3. **Cartões de resumo:** três cartões lado a lado.
   - **Calorias do dia:** o total do dia e o campo para escolher a data.
   - **Média por dia:** a média de calorias dos dias com refeições registradas.
   - **Dias registrados:** a quantidade de dias com refeições.
4. **Cadastro e IA:** dois painéis lado a lado.
   - **Adicionar refeição:** formulário manual com nome da refeição e calorias.
   - **Estimativa com IA:** descrição da refeição, botão **Estimar com IA** e, depois do cálculo, o resultado com o botão **Usar no cadastro**.
5. **Refeições e histórico:** dois painéis lado a lado.
   - **Refeições do dia:** lista com nome, calorias e botão **Excluir** em cada item.
   - **Histórico de dias:** um botão por dia com data, quantidade de refeições, barra de comparação e total. O dia selecionado fica destacado.
6. **Hidratação:** painel em largura total, com o total de água do dia, a barra de progresso da meta de 3.000 ml, os atalhos de 250 ml, 500 ml, 600 ml e 1000 ml, o campo para outra quantidade e a lista de registros com botão **Excluir**.

Não há tela de login, rodapé nem navegação entre páginas.

### Responsividade
- **Até 1000 px de largura:** os cartões de resumo passam para duas colunas, com o cartão de calorias ocupando a linha inteira, e os painéis passam a ocupar uma coluna cada.
- **Até 640 px (celular):** a barra superior mostra só o ponto de status, os espaçamentos e as fontes diminuem, a linha do histórico fica mais compacta e, na hidratação, o resumo, os atalhos e o campo de quantidade se empilham, com o botão **Registrar** em largura total.

## Diretrizes gerais de UI
- **Tema escuro com bom contraste:** textos claros sobre fundos escuros, com o verde reservado às ações principais.
- **Feedback sempre visível:** cada ação mostra uma mensagem de sucesso ou de erro na tela, com `role="status"` ou `role="alert"` para leitores de tela.
- **Estados vazios explicados:** quando não há refeições, histórico ou registros de água, o painel mostra uma caixa tracejada com uma frase que orienta o próximo passo.
- **Estados dos botões:** o botão escurece ou clareia ao passar o mouse, desce 1 px ao ser pressionado e fica translúcido e bloqueado enquanto salva ou calcula, evitando cliques repetidos.
- **Formulários claros:** todo campo tem rótulo associado, com unidade visível (`kcal`, `ml`), texto de exemplo e foco destacado em verde.
- **Acessibilidade básica:** os botões de excluir têm `aria-label` com o nome do item.
- **IA como ajuda opcional:** o painel de IA é separado do cadastro manual, avisa que o valor é aproximado e só preenche o formulário quando a pessoa clica em **Usar no cadastro**. Nada é salvo automaticamente.
- **Tom simples:** textos curtos e diretos, sem termos técnicos.

## Uso
A pessoa abre o site, seleciona uma data, cadastra uma refeição e vê o total atualizado. Também pode pedir uma estimativa de calorias à IA antes de salvar, conferir o valor e excluir registros. O histórico permite selecionar um dia para ver suas refeições, e a média considera apenas os dias com registros. O painel de hidratação soma os registros da data selecionada e compara o total com a meta visual de 3.000 ml, indicada na tela como meta diária recomendada.

Não haverá telas de login, cadastro de usuário, metas personalizadas ou relatórios complexos.