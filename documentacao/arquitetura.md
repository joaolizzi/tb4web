# Arquitetura do sistema

## Organização geral

O Calorias Diárias terá um frontend em React e um backend em Java com Spring Boot. O navegador não acessará o banco de dados diretamente: as operações serão feitas por requisições HTTP à API REST.

A proposta é aproveitar a experiência visual do projeto original, mas adaptar o acesso aos dados para a API Java exigida no trabalho.

```text
Navegador (React)
       |
       | HTTP / JSON
       v
API REST (Spring Boot)
       |
       v
Banco de dados PostgreSQL
```

O PostgreSQL foi escolhido por ser relacional e funcionar bem com Spring Data JPA. A configuração de conexão ficará em variáveis de ambiente, sem senhas no repositório.

## Estrutura prevista

```text
tb4web/
├── documentacao/
│   ├── visao.md
│   ├── requisitos.md
│   ├── casos-uso.md
│   ├── arquitetura.md
│   └── interface.md
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/br/edu/ifpr/calorias/
│       │   │   ├── controller/
│       │   │   ├── service/
│       │   │   ├── repository/
│       │   │   ├── model/
│       │   │   ├── dto/
│       │   │   ├── config/
│       │   │   └── exception/
│       │   └── resources/
│       │       └── application.properties
│       └── test/
└── frontend/
    ├── package.json
    ├── src/
    │   ├── components/
    │   ├── pages/
    │   ├── services/
    │   ├── contexts/
    │   └── styles/
    └── public/
```

Essa é a organização planejada, não uma descrição de pastas já implementadas.

## Backend

A API será dividida em camadas para separar as responsabilidades:

- **Controller:** recebe as requisições, valida os dados de entrada e devolve as respostas HTTP.
- **Service:** concentra as regras de negócio, como conferir a propriedade de um registro e calcular os totais do dia.
- **Repository:** acessa o banco de dados com Spring Data JPA.
- **Model:** representa as entidades que serão persistidas.
- **DTO:** define os dados recebidos e enviados pela API, evitando expor as entidades diretamente.
- **Config:** reúne configurações da aplicação, incluindo segurança e CORS.
- **Exception:** organiza o tratamento de erros e as mensagens retornadas.

As principais entidades previstas são `Usuario`, `Meta`, `Refeicao` e `RegistroAgua`. Cada meta e registro ficará associado ao usuário responsável.

A API deverá validar os dados com Bean Validation. As senhas não serão armazenadas em texto puro; a autenticação será implementada no backend com Spring Security e hash seguro de senhas. A estratégia de sessão ou token será definida na implementação.

## Endpoints previstos

Os caminhos abaixo servem como referência para a implementação. Poderão ser ajustados se houver necessidade, desde que o frontend e a documentação acompanhem as mudanças.

| Método | Rota | Função |
| --- | --- | --- |
| POST | `/api/usuarios` | Criar conta |
| POST | `/api/auth/login` | Entrar no sistema |
| GET | `/api/metas` | Consultar as próprias metas |
| PUT | `/api/metas` | Salvar ou atualizar metas |
| GET | `/api/refeicoes?data=AAAA-MM-DD` | Listar refeições de uma data |
| POST | `/api/refeicoes` | Cadastrar refeição |
| PUT | `/api/refeicoes/{id}` | Alterar refeição |
| DELETE | `/api/refeicoes/{id}` | Excluir refeição |
| GET | `/api/agua?data=AAAA-MM-DD` | Consultar consumo de água |
| POST | `/api/agua` | Registrar consumo de água |
| GET | `/api/resumo?data=AAAA-MM-DD` | Consultar totais diários |
| GET | `/api/historico` | Consultar histórico de calorias |

As rotas de dados pessoais exigirão autenticação. O backend deverá identificar o usuário pela autenticação, e não aceitar livremente um ID de usuário enviado pelo frontend para acessar registros.

## Frontend

O frontend será feito com React e Vite. As telas previstas são login, cadastro, painel principal, registro de refeições, metas e histórico. Os componentes reutilizáveis ficarão separados das páginas.

A pasta `services/` terá as funções responsáveis pelas chamadas à API. Assim, uma tela não precisará repetir os endereços e as configurações das requisições. Os estados de carregamento, sucesso e erro serão tratados na interface.

O projeto original utiliza React, Vite e Supabase. Na versão do trabalho, as operações de autenticação e persistência deverão passar pelo backend Spring Boot. Não basta manter o acesso direto ao Supabase e criar uma API Java sem uso real.

## Comunicação e respostas

O frontend enviará e receberá JSON. A API usará os métodos HTTP adequados: GET para consultas, POST para criação, PUT para alterações e DELETE para exclusão.

As respostas terão códigos coerentes com o resultado, por exemplo:

- `200 OK` para consultas e atualizações bem-sucedidas.
- `201 Created` quando um registro for criado.
- `204 No Content` quando uma exclusão for concluída sem corpo de resposta.
- `400 Bad Request` para dados inválidos.
- `401 Unauthorized` quando faltar autenticação válida.
- `403 Forbidden` quando o acesso não for permitido.
- `404 Not Found` para recursos inexistentes.

## Padrão de nomes

- **Java:** classes em PascalCase (`RefeicaoController`, `RefeicaoService`); métodos e atributos em camelCase (`buscarPorData`).
- **Pacotes Java:** nomes em minúsculas (`controller`, `repository`).
- **React:** componentes e páginas em PascalCase (`DashboardPage.jsx`, `RefeicaoForm.jsx`).
- **JavaScript:** funções e variáveis em camelCase (`listarRefeicoes`).
- **Rotas REST:** substantivos em minúsculas e, quando fizer sentido, no plural (`/api/refeicoes`).
- **Banco:** tabelas e colunas em snake_case (`registro_agua`, `usuario_id`).

## Desenvolvimento e testes

A implementação será feita em etapas: primeiro a base da API e do banco, depois as regras de negócio, os endpoints e a integração com as telas. Serão incluídos testes das operações principais, especialmente validações, cálculos e restrições de acesso.

O código será versionado no GitHub durante o desenvolvimento, com commits que correspondam às mudanças realizadas. Antes da entrega, a documentação será revisada para representar o sistema que estiver funcionando.
