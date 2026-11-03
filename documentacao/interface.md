# Interface do sistema

A interface do Calorias Diárias vai aproveitar o estilo do aplicativo original, principalmente o tema escuro, os cartões com informações do dia e os indicadores de progresso. A ideia é deixar o registro de refeições e água rápido, sem exigir muitas etapas.

## Identidade visual

O tema principal será escuro, com textos claros e cores diferentes para ajudar a identificar as informações. As cores abaixo foram tiradas dos estilos do projeto original e podem receber pequenos ajustes durante a adaptação.

| Uso | Cor |
| --- | --- |
| Fundo principal | `#080b10` |
| Fundo dos cartões | `#11161e` |
| Texto principal | `#f4f7fb` |
| Texto secundário | `#8993a4` |
| Destaques e ações principais | `#34d399` |
| Consumo de água | `#38bdf8` |
| Alimentação | `#fb7042` |
| Avisos | `#fbbf24` |
| Erros | `#f87171` |

Os cartões terão cantos arredondados e bordas discretas. Os botões principais usarão a cor verde de destaque, enquanto o azul será reservado principalmente às informações de hidratação.

## Tipografia

A fonte principal será **Inter**, com fontes do sistema como alternativa caso ela não carregue. Os títulos terão peso maior que o texto comum, e os valores de calorias e água terão destaque visual para facilitar a leitura.

O tamanho dos textos será adaptado ao espaço disponível, principalmente no celular. Evitaremos textos muito pequenos em botões e campos de formulário.

## Telas previstas

### Login e cadastro

A entrada do sistema terá formulários simples para e-mail e senha. No cadastro, serão mostrados os campos necessários para criar a conta. Mensagens de erro devem aparecer perto dos campos ou em uma área visível do formulário.

### Painel principal

Será a tela mais usada. Ela vai mostrar a data selecionada, a meta diária de calorias, o total consumido e o consumo de água. Abaixo dos resumos, o usuário poderá consultar as refeições do dia e adicionar novos registros.

As calorias e a água terão indicadores de progresso. O total consumido deverá continuar visível mesmo quando o usuário ultrapassar a meta.

### Refeições

O formulário de refeição terá nome, calorias e data. A lista mostrará os registros do dia, com opções de editar e excluir. Antes de excluir, o sistema pedirá confirmação para evitar remoções por engano.

### Metas

A tela de metas permitirá informar ou alterar os valores diários de calorias e água. Os campos indicarão suas unidades: kcal para calorias e mL para água.

### Histórico

O usuário poderá escolher datas anteriores para consultar os registros. Se o resumo de vários dias for implementado, será exibido em gráfico ou lista, sem esconder os valores numéricos.

## Organização e navegação

A navegação deve permitir chegar ao painel, às metas e ao histórico sem muitos cliques. A opção de sair da conta ficará acessível pelo menu.

Em telas maiores, os resumos poderão ficar lado a lado. No celular, os cartões e formulários serão organizados em uma coluna para evitar rolagem horizontal. Botões e campos deverão ter tamanho adequado ao toque.

## Componentes e comportamento

- **Cartões:** agrupam metas, totais e registros.
- **Botões:** terão aparência diferente para ações principais, secundárias e exclusão.
- **Campos:** terão rótulos visíveis e indicação de unidade quando necessário.
- **Indicadores de progresso:** mostram a relação entre consumo e meta, acompanhados dos valores em texto.
- **Mensagens:** informam quando um registro foi salvo, quando há erro ou quando não existem dados para a data.
- **Carregamento:** enquanto uma consulta estiver em andamento, a interface deverá indicar que os dados ainda estão sendo buscados.

Não usaremos apenas a cor para indicar sucesso ou erro; as mensagens também terão texto. O contraste entre fundo e conteúdo deverá permitir leitura confortável.

## Relação com o frontend original

O aplicativo pessoal já usa React, Vite, a fonte Inter e um conjunto de estilos escuros. Esses elementos servirão de referência para a versão do trabalho.

A adaptação não será apenas visual: as telas precisarão buscar e salvar os dados pela API REST em Spring Boot. Recursos extras do aplicativo original, como inteligência artificial e painel administrativo, não fazem parte das telas obrigatórias desta primeira versão.

Este documento descreve a interface planejada. Quando as telas estiverem implementadas, será revisado para refletir o resultado final.
