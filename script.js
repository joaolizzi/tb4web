// Quando o usuário clicar no botão "Buscar Endereço"
document.getElementById("buscar").addEventListener("click", function () {

    // valor do CEP digitado
    const cep = document.getElementById("cep").value.replace(/\D/g, "");

    // Verifica se o CEP tem exatamente 8 números
    if (cep.length !== 8) {
        alert("CEP inválido! Digite 8 números.");
        return;
    }

    // Monta a URL da API ViaCEP
    const url = `https://viacep.com.br/ws/${cep}/json/`;

    // Faz a requisição da API
    fetch(url)
        .then(response => response.json())
        .then(data => {

            // Se a API retornar erro
            if (data.erro) {
                alert("CEP não encontrado!");
                return;
            }

            // Preenche os campos do formulário com os dados recebidos
            document.getElementById("logradouro").value = data.logradouro;
            document.getElementById("bairro").value = data.bairro;
            document.getElementById("cidade").value = data.localidade;
            document.getElementById("estado").value = data.uf;
        })
        .catch(() => {
            alert("Erro ao buscar o CEP");
        });
});
