# Arquitetura

O sistema tem duas partes: frontend em React e backend em Spring Boot. O React faz requisições HTTP para a API, que consulta e altera os registros no banco H2.

## Pastas
```text
tb4web/
├── documentacao/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/br/edu/ifpr/calorias/
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   ├── model/
│       │   └── dto/
│       └── resources/application.properties
└── frontend/
    ├── package.json
    ├── index.html
    └── src/
        ├── App.jsx
        ├── main.jsx
        └── style.css
```

## Camadas
- **Model:** entidades Refeicao (nome, calorias e data) e ConsumoAgua (quantidade em ml e data).
- **Repository:** acesso ao banco de dados com Spring Data JPA.
- **Service:** cadastro, consulta, exclusão, soma de calorias, histórico alimentar e total diário de água.
- **Controller:** endpoints REST chamados pelo React.
- **DTO:** valida os dados recebidos no cadastro.
- **EstimativaService:** consulta a API Gemini através de uma requisição HTTP, usando a chave de ambiente do backend.

## Endpoints
| Método | Caminho | Função |
|---|---|---|
| GET | /api/refeicoes?data=AAAA-MM-DD | Listar refeições |
| POST | /api/refeicoes | Cadastrar refeição |
| DELETE | /api/refeicoes/{id} | Excluir refeição |
| GET | /api/refeicoes/total?data=AAAA-MM-DD | Somar calorias |
| GET | /api/refeicoes/historico | Histórico de dias e média de calorias |
| POST | /api/ia/estimar | Estimar calorias a partir de uma descrição |
| GET | /api/agua?data=AAAA-MM-DD | Listar registros de água do dia |
| POST | /api/agua | Registrar quantidade de água em ml |
| DELETE | /api/agua/{id} | Excluir registro de água |
| GET | /api/agua/total?data=AAAA-MM-DD | Somar água consumida no dia |

O banco H2 é salvo em arquivo na pasta `backend/dados`. O Vite encaminha as requisições `/api` para a porta 8080 durante o desenvolvimento.

A média diária é calculada dividindo a soma das calorias de todos os dias com registros pela quantidade desses dias. Dias sem refeições não entram na média.

A chave `GEMINI_API_KEY` não fica no código e nunca é enviada ao navegador. A descrição vai para o serviço externo Gemini somente quando a pessoa pede uma estimativa. A estimativa pode falhar e não é uma medição nutricional exata.

Como é uma demonstração local, não há autenticação nem separação por usuário. Não deve ser publicado na internet sem medidas de segurança adicionais.
