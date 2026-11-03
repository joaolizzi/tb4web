# Visão do projeto — Calorias Diárias

## Ideia
Criar um site simples para anotar as refeições do dia e saber quantas calorias foram consumidas.

## Para quem é
Pessoas que querem registrar sua alimentação de maneira rápida.

## O que o sistema faz
- Cadastra uma refeição informando nome, calorias e data.
- Mostra as refeições de uma data escolhida.
- Exclui uma refeição.
- Soma as calorias registradas no dia.
- Mostra um histórico dos dias com refeições, seus totais e a média diária.
- Pode estimar as calorias a partir da descrição de uma refeição usando IA (Gemini), como ajuda opcional.
- Registra a quantidade de água consumida e mostra o total do dia.

## Tecnologias
O frontend será feito em React com Vite. O backend será uma API REST em Java com Spring Boot. Os dados ficarão salvos em um banco H2 local.

## Objetivo do trabalho
Demonstrar a comunicação entre frontend, backend e banco de dados, usando as camadas Controller, Service, Repository e Model.

Não teremos login nem metas nutricionais complexas. A hidratação terá um acompanhamento simples em ml, com uma referência inicial de 2.000 ml. A IA será usada apenas para sugerir uma estimativa de calorias, que a pessoa pode corrigir antes de salvar. A ideia é manter o projeto pequeno e fácil de apresentar.
