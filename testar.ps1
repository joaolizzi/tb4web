# Execute na raiz do projeto: .\testar.ps1
$ErrorActionPreference = 'Stop'
$raiz = Split-Path -Parent $MyInvocation.MyCommand.Path

Write-Host ''
Write-Host '=== 1/2 - Testes do backend Spring Boot ===' -ForegroundColor Cyan
Push-Location (Join-Path $raiz 'backend')
try {
    mvn test
    if ($LASTEXITCODE -ne 0) { throw 'Os testes do backend falharam.' }
} finally { Pop-Location }

Write-Host ''
Write-Host '=== 2/2 - Build do frontend React ===' -ForegroundColor Cyan
Push-Location (Join-Path $raiz 'frontend')
try {
    if (Test-Path 'package-lock.json') {
        npm ci
    } else {
        npm install
    }
    if ($LASTEXITCODE -ne 0) { throw 'Nao foi possivel instalar as dependencias do frontend.' }

    npm run build
    if ($LASTEXITCODE -ne 0) { throw 'O build do frontend falhou.' }
} finally { Pop-Location }

Write-Host ''
Write-Host 'Testes do backend e build do frontend concluidos sem erros!' -ForegroundColor Green
Write-Host 'A chamada real ao Gemini precisa ser testada no navegador com sua chave configurada.'
