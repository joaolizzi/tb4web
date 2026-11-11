# Visão do projeto — Calorias Diárias

## Problema
Quem quer acompanhar a alimentação costuma esbarrar em aplicativos pesados: pedem cadastro e login, exigem preencher metas, perfis e bancos de alimentos, e tornam lenta uma tarefa que deveria levar segundos. Sem um registro simples, fica difícil saber quantas calorias foram consumidas no dia e se a alimentação segue um padrão ao longo da semana. Outro obstáculo é não saber quantas calorias tem uma refeição na hora de anotá-la.

## Ideia
Calorias Diárias é um site simples para anotar as refeições do dia, somar as calorias consumidas e acompanhar a água bebida, sem cadastro e sem configuração.

## Para quem é
Pessoas que querem registrar sua alimentação de maneira rápida, sem criar conta e sem precisar de um plano nutricional detalhado. O uso é individual, no próprio computador.

## Proposta de valor
- **Rapidez:** cadastrar uma refeição leva poucos segundos, com apenas nome e calorias.
- **Simplicidade:** uma única página reúne o total do dia, o histórico e a hidratação, sem login e sem menus.
- **Ajuda opcional da IA:** quando a pessoa não sabe as calorias de um prato, pode descrevê-lo e receber uma estimativa para conferir e corrigir antes de salvar. A IA nunca registra nada sozinha.
- **Dados sob controle:** os registros ficam em um banco local, no computador da pessoa. Só a descrição da refeição vai para o serviço externo, e apenas quando ela pede uma estimativa.
- **Visão da evolução:** o histórico mostra o total de cada dia e a média diária, para comparar os dias de relance.

## O que o sistema faz
- Cadastra uma refeição informando nome, calorias e data.
- Mostra as refeições de uma data escolhida.
- Exclui uma refeição.
- Soma as calorias registradas no dia.
- Mostra um histórico dos dias com refeições, seus totais e a média diária, considerando apenas os dias com registros.
- Pode estimar as calorias a partir da descrição de uma refeição usando IA (Gemini), como ajuda opcional.
- Registra a quantidade de água consumida, com atalhos de 250, 500, 600 e 1000 ml, e mostra o total do dia em relação a uma meta diária recomendada de 3.000 ml.

## Tecnologias
O frontend é feito em React com Vite. O backend é uma API REST em Java com Spring Boot. Os dados ficam salvos em um banco H2 local, gravado em arquivo.

## Objetivo do trabalho
Demonstrar a comunicação entre frontend, backend e banco de dados, usando as camadas Controller, Service, Repository e Model.

## Limites do projeto
Não há login, cadastro de usuários, edição de refeições nem metas nutricionais personalizadas. A meta de hidratação é um valor fixo de 3.000 ml, definido pelo aplicativo e igual para todas as pessoas. A IA serve apenas para sugerir uma estimativa de calorias, que é aproximada e pode ser corrigida antes de salvar. O projeto é uma demonstração para uso local, mantido pequeno e fácil de apresentar.