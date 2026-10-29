# Requisitos do sistema

Este documento reúne os requisitos previstos para o Calorias Diárias. A implementação será feita por etapas, e os itens poderão ser ajustados durante o desenvolvimento para manter a documentação de acordo com o sistema entregue.

## Requisitos funcionais

| ID | Requisito | Prioridade |
| --- | --- | --- |
| RF01 | Permitir que o usuário crie uma conta. | Alta |
| RF02 | Permitir que o usuário entre e saia da própria conta. | Alta |
| RF03 | Permitir que o usuário informe e altere sua meta diária de calorias. | Alta |
| RF04 | Permitir o cadastro de refeições, com nome, quantidade de calorias e data. | Alta |
| RF05 | Mostrar as refeições registradas em um dia escolhido. | Alta |
| RF06 | Permitir editar e excluir refeições cadastradas. | Alta |
| RF07 | Somar as calorias das refeições do dia e comparar o total com a meta definida. | Alta |
| RF08 | Permitir definir uma meta diária de consumo de água. | Média |
| RF09 | Permitir registrar a quantidade de água consumida durante o dia. | Média |
| RF10 | Mostrar o total de água consumida e o quanto falta para atingir a meta. | Média |
| RF11 | Permitir consultar registros de dias anteriores. | Média |
| RF12 | Exibir um resumo do consumo de calorias ao longo dos últimos dias. | Baixa |

## Requisitos não funcionais

| ID | Requisito | Prioridade |
| --- | --- | --- |
| RNF01 | O backend deve ser desenvolvido em Java com Spring Boot e disponibilizar uma API REST. | Alta |
| RNF02 | O frontend deve consumir os dados por meio da API do backend. | Alta |
| RNF03 | Os dados de usuários, metas e registros devem ser armazenados de forma persistente. | Alta |
| RNF04 | Cada usuário deve acessar somente os próprios registros. | Alta |
| RNF05 | O sistema deve validar os dados recebidos e informar erros de preenchimento. | Alta |
| RNF06 | A interface deve funcionar em computadores e celulares. | Média |
| RNF07 | Os nomes de classes, métodos, arquivos e rotas devem seguir um padrão consistente. | Média |
| RNF08 | O código deve ser versionado com Git durante o desenvolvimento. | Alta |
| RNF09 | A aplicação deve apresentar mensagens claras quando uma operação não puder ser concluída. | Média |

## Prioridades

- **Alta:** necessária para a versão principal do trabalho funcionar.
- **Média:** importante para completar a experiência de acompanhamento diário.
- **Baixa:** pode ser implementada depois das funcionalidades principais.

## Observações

A primeira versão será concentrada no registro de refeições, nas metas e na consulta dos dados. Recursos do aplicativo original, como análise de alimentos por inteligência artificial e painel administrativo, ficam fora do escopo inicial. Se forem incluídos mais adiante, os requisitos e os casos de uso serão atualizados.

A autenticação, o modelo do banco e os detalhes dos endpoints serão definidos na documentação de arquitetura e durante a implementação.
