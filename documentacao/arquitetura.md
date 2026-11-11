# Arquitetura

O sistema tem duas partes independentes: um frontend em React (Vite) e um backend em Spring Boot que expõe uma API REST. O React envia requisições HTTP com JSON para a API, que valida os dados, aplica as regras e consulta ou altera os registros no banco H2. Para estimar calorias, o backend também consulta a API externa do Gemini.

```text
Navegador (React) ──/api──▶ Vite (proxy, :5173) ──▶ Spring Boot (:8080) ──▶ H2 (arquivo)
                                                          │
                                                          └──▶ API Gemini (somente em /api/ia/estimar)
```

## Estrutura de pastas
```text
tb4web/
├── README.md
├── testar.ps1
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
│       │   │   ├── CaloriasApplication.java
│       │   │   ├── controller/
│       │   │   ├── service/
│       │   │   ├── repository/
│       │   │   ├── model/
│       │   │   ├── dto/
│       │   │   └── exception/
│       │   └── resources/application.properties
│       └── test/java/br/edu/ifpr/calorias/
│           ├── controller/
│           └── service/
└── frontend/
    ├── package.json
    ├── vite.config.js
    ├── index.html
    └── src/
        ├── main.jsx
        ├── App.jsx
        └── style.css
```

## Backend (Spring Boot)
O backend é organizado em camadas, cada uma em um pacote de `br.edu.ifpr.calorias`:

| Pacote | Responsabilidade | Classes |
|---|---|---|
| `controller` | Recebe as requisições HTTP, aciona a validação e devolve a resposta com o código de status adequado. | `RefeicaoController`, `AguaController`, `EstimativaController` |
| `service` | Regras de negócio: cadastro, exclusão, somas por dia, histórico, média e consulta ao Gemini. | `RefeicaoService`, `ConsumoAguaService`, `EstimativaService` |
| `repository` | Acesso ao banco com Spring Data JPA. | `RefeicaoRepository`, `ConsumoAguaRepository` |
| `model` | Entidades JPA mapeadas para tabelas. | `Refeicao` (nome, calorias, data) e `ConsumoAgua` (quantidade em ml, data) |
| `dto` | Objetos de entrada e saída da API, definidos como `record`. A entrada é validada com Bean Validation. | `RefeicaoDados`, `AguaDados`, `EstimativaPedido`, `EstimativaResposta`, `HistoricoResposta` |
| `exception` | Tratamento central de erros, que converte as falhas em JSON no formato `{"erro": "mensagem"}`. | `ErrosEstimativa` |

O fluxo de uma requisição é sempre Controller → Service → Repository → banco. O controller não acessa o repositório diretamente, e o `EstimativaService` não usa o banco.

**Configuração (`application.properties`):** o servidor usa a porta 8080 e o banco H2 é gravado em arquivo, em `backend/dados/calorias`, criado automaticamente na primeira execução. As tabelas são geradas pelo Hibernate (`ddl-auto=update`).

**Testes:** `RefeicaoServiceTest` testa o histórico e a média com Mockito. `RefeicaoControllerIntegracaoTest` e `AguaControllerIntegracaoTest` sobem o Spring e chamam a API com MockMvc, usando um banco H2 em memória, para não alterar os dados reais.

## Frontend (React)
O frontend é uma aplicação de página única criada com Vite, sem roteador nem biblioteca de estado:

- `main.jsx` é o ponto de entrada e monta o componente `App` na `div#root` do `index.html`.
- `App.jsx` contém o componente `App`, que concentra a tela inteira: resumo, cadastro manual, estimativa com IA, refeições do dia, histórico e hidratação. O estado é controlado com `useState` e as consultas à API são disparadas com `useEffect`.
- `style.css` reúne todos os estilos da aplicação, incluindo as regras responsivas.

## Comunicação entre frontend e backend
- O React usa `fetch` para chamar a API com caminhos relativos (`/api/...`), enviando e recebendo JSON.
- Em desenvolvimento, o Vite encaminha as requisições `/api` para `http://localhost:8080` (`vite.config.js`), o que evita problemas de CORS. Os controllers de refeição e de água também liberam a origem `http://localhost:5173`.
- Ao carregar a tela ou mudar a data, o frontend consulta as refeições e a água da data escolhida. Depois de cadastrar ou excluir, ele recarrega as listas e o histórico.
- A API responde com `200` (consulta), `201` (cadastro), `204` (exclusão), `400` (dados inválidos), `404` (registro inexistente) e, na estimativa por IA, `502`, `503` ou `429`, sempre com uma mensagem legível.

## Endpoints
| Método | Caminho | Função |
|---|---|---|
| GET | /api/refeicoes?data=AAAA-MM-DD | Listar refeições do dia |
| POST | /api/refeicoes | Cadastrar refeição |
| DELETE | /api/refeicoes/{id} | Excluir refeição |
| GET | /api/refeicoes/total?data=AAAA-MM-DD | Somar calorias do dia |
| GET | /api/refeicoes/historico | Histórico de dias e média de calorias |
| POST | /api/ia/estimar | Estimar calorias a partir de uma descrição |
| GET | /api/agua?data=AAAA-MM-DD | Listar registros de água do dia |
| POST | /api/agua | Registrar quantidade de água em ml |
| DELETE | /api/agua/{id} | Excluir registro de água |
| GET | /api/agua/total?data=AAAA-MM-DD | Somar água consumida no dia |

A média diária é calculada dividindo a soma das calorias de todos os dias com registros pela quantidade desses dias. Dias sem refeições não entram na média.

## Integração com o Gemini
O `EstimativaService` envia a descrição da refeição ao Gemini por uma requisição HTTP (`HttpClient` do Java) e espera como resposta apenas um número inteiro. A chave é lida da variável de ambiente `GEMINI_API_KEY` e o modelo, de `GEMINI_MODEL` (padrão `gemini-3.8-flash`). A chave não fica no código e nunca é enviada ao navegador. A descrição vai para o serviço externo somente quando a pessoa pede uma estimativa, que pode falhar e não é uma medição nutricional exata.

## Padrão de nomenclatura
**Pastas e arquivos**
- Pastas em minúsculas e sem espaços: `documentacao`, `backend`, `frontend`, `controller`, `service`.
- Pacotes Java em minúsculas, sob `br.edu.ifpr.calorias`, um por camada.
- Arquivos Java com o nome da classe em PascalCase, em português, com um sufixo que indica a camada: `RefeicaoController`, `RefeicaoService`, `RefeicaoRepository`. As entidades usam o nome do conceito no singular (`Refeicao`, `ConsumoAgua`) e os DTOs terminam em `Dados`, `Pedido` ou `Resposta`.
- Testes com o nome da classe testada mais `Test`, ou `IntegracaoTest` quando sobem o Spring (`RefeicaoServiceTest`, `RefeicaoControllerIntegracaoTest`).
- No frontend, o componente fica em PascalCase (`App.jsx`) e os demais arquivos, em minúsculas (`main.jsx`, `style.css`, `vite.config.js`).
- Documentos em Markdown com nomes em minúsculas, separados por hífen (`casos-uso.md`).

**Código**
- Variáveis, métodos e funções em camelCase e em português, como `listar`, `cadastrar`, `carregarHistorico`.
- Classes CSS em minúsculas, separadas por hífen, como `stat-card` e `botao-principal`.
- Tabelas do banco em minúsculas, no plural e separadas por sublinhado: `refeicoes` e `consumos_agua`.

**API**
- Caminhos em minúsculas, em português e no plural para coleções: `/api/refeicoes`, `/api/agua`, `/api/ia/estimar`.
- Os métodos HTTP indicam a ação: GET consulta, POST cadastra e DELETE exclui.
- Os campos do JSON seguem camelCase: `quantidadeMl`, `mediaCalorias`.

## Segurança e limites
Como é uma demonstração local, não há autenticação nem separação por usuário. O projeto não deve ser publicado na internet sem medidas de segurança adicionais.