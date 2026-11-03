# Casos de uso

**Ator:** pessoa utilizando o site. Não é necessário fazer login.

## UC01 — Cadastrar refeição
1. A pessoa informa nome, calorias e data.
2. Clica em Adicionar.
3. O sistema valida os campos e salva a refeição no banco.
4. A lista e o total de calorias são atualizados.

**Alternativa:** se faltar um campo ou o valor for inválido, o cadastro não é realizado.

## UC02 — Consultar refeições
1. A pessoa escolhe uma data.
2. O sistema consulta as refeições cadastradas para aquele dia.
3. Mostra a lista e o total de calorias.

**Alternativa:** quando não há registros, a tela mostra uma mensagem informativa.

## UC03 — Excluir refeição
1. A pessoa clica em Excluir ao lado de uma refeição.
2. O sistema remove o registro do banco.
3. A lista e o total são atualizados.

**Alternativa:** se o registro não existir mais, a API retorna 404.

## UC04 — Estimar calorias com IA
1. A pessoa digita uma descrição da refeição, de preferência com quantidades.
2. Clica em **Estimar com IA**.
3. O React envia a descrição ao Spring Boot.
4. O backend consulta o Gemini e devolve uma estimativa de calorias.
5. O valor aparece no formulário e pode ser corrigido antes do cadastro.

**Alternativa:** se a API estiver indisponível ou sem chave, é possível digitar as calorias manualmente.

O valor gerado por IA é aproximado e depende da descrição enviada.

## UC05 — Consultar histórico
1. A pessoa abre a página e visualiza os dias em que cadastrou refeições.
2. O sistema mostra o total de calorias e a quantidade de refeições de cada dia.
3. O sistema calcula a média dos dias com registros.
4. Ao clicar em uma data do histórico, a lista de refeições daquele dia aparece.

**Alternativa:** sem refeições cadastradas, a média é zero e o histórico mostra uma mensagem informativa.
