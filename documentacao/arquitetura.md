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
- **Model:** entidade Refeicao, com id, nome, calorias e data.
- **Repository:** acesso ao banco de dados com Spring Data JPA.
- **Service:** cadastro, consulta, exclusão e soma de calorias.
- **Controller:** endpoints REST chamados pelo React.
- **DTO:** valida os dados recebidos no cadastro.

## Endpoints
| Método | Caminho | Função |
|---|---|---|
| GET | /api/refeicoes?data=AAAA-MM-DD | Listar refeições |
| POST | /api/refeicoes | Cadastrar refeição |
| DELETE | /api/refeicoes/{id} | Excluir refeição |
| GET | /api/refeicoes/total?data=AAAA-MM-DD | Somar calorias |

O banco H2 é salvo em arquivo na pasta `backend/dados`. O Vite encaminha as requisições `/api` para a porta 8080 durante o desenvolvimento.

Como é uma demonstração local, não há autenticação nem separação por usuário. Não deve ser publicado na internet sem medidas de segurança adicionais.
