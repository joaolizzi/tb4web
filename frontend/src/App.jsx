import { useEffect, useState } from 'react'

const hoje = () => {
  const data = new Date()
  return [data.getFullYear(), String(data.getMonth() + 1).padStart(2, '0'), String(data.getDate()).padStart(2, '0')].join('-')
}

export default function App() {
  const [data, setData] = useState(hoje)
  const [refeicoes, setRefeicoes] = useState([])
  const [nome, setNome] = useState('')
  const [calorias, setCalorias] = useState('')
  const [erro, setErro] = useState('')
  const [salvando, setSalvando] = useState(false)
  const [estimando, setEstimando] = useState(false)
  const [avisoIA, setAvisoIA] = useState('')

  async function carregar() {
    try {
      const resposta = await fetch('/api/refeicoes?data=' + encodeURIComponent(data))
      if (!resposta.ok) throw new Error('Não foi possível buscar as refeições.')
      setRefeicoes(await resposta.json())
      setErro('')
    } catch (e) {
      setErro(e.message)
    }
  }

  useEffect(() => { carregar() }, [data])

  async function estimarCalorias() {
    if (!nome.trim()) {
      setErro('Descreva a refeição antes de usar a IA.')
      return
    }
    setEstimando(true)
    setErro('')
    setAvisoIA('')
    try {
      const resposta = await fetch('/api/ia/estimar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ descricao: nome.trim() })
      })
      if (!resposta.ok) {
        const detalhes = await resposta.json().catch(() => ({}))
        throw new Error(detalhes.erro || 'A estimativa não funcionou. Informe as calorias manualmente.')
      }
      const dados = await resposta.json()
      setCalorias(String(dados.calorias))
      setAvisoIA('Estimativa aproximada da IA. Confira o valor antes de salvar.')
    } catch (e) {
      setErro(e.message)
    } finally {
      setEstimando(false)
    }
  }

  async function adicionar(evento) {
    evento.preventDefault()
    if (!nome.trim() || calorias === '' || Number(calorias) < 0) return
    setSalvando(true)
    try {
      const resposta = await fetch('/api/refeicoes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome: nome.trim(), calorias: Number(calorias), data })
      })
      if (!resposta.ok) throw new Error('Não foi possível salvar a refeição.')
      setNome('')
      setCalorias('')
      setAvisoIA('')
      await carregar()
    } catch (e) {
      setErro(e.message)
    } finally {
      setSalvando(false)
    }
  }

  async function excluir(id) {
    try {
      const resposta = await fetch('/api/refeicoes/' + id, { method: 'DELETE' })
      if (!resposta.ok) throw new Error('Não foi possível excluir a refeição.')
      await carregar()
    } catch (e) {
      setErro(e.message)
    }
  }

  const total = refeicoes.reduce((soma, refeicao) => soma + refeicao.calorias, 0)

  return (
    <main className="pagina">
      <header>
        <span className="marca">● Calorias Diárias</span>
        <h1>Meu diário alimentar</h1>
        <p>Registre suas refeições e acompanhe suas calorias.</p>
      </header>

      <section className="resumo">
        <div><span>Total consumido</span><strong>{total.toLocaleString('pt-BR')} <small>kcal</small></strong></div>
        <label>Data <input type="date" value={data} onChange={e => setData(e.target.value)} /></label>
      </section>

      <section className="painel">
        <h2>Adicionar refeição</h2>
        <form onSubmit={adicionar}>
          <label>Nome da refeição<input required maxLength={150} placeholder="Ex.: Arroz, feijão e frango" value={nome} onChange={e => setNome(e.target.value)} /></label>
          <label>Calorias<input required min="0" step="1" type="number" placeholder="Ex.: 550" value={calorias} onChange={e => setCalorias(e.target.value)} /></label>
          <button disabled={salvando} type="submit">{salvando ? 'Salvando...' : 'Adicionar'}</button>
        </form>
        <div className="ia-ajuda">
          <button type="button" className="botao-ia" disabled={estimando || !nome.trim()} onClick={estimarCalorias}>
            {estimando ? 'Estimando...' : '✨ Estimar com IA'}
          </button>
          <span>Descreva o alimento e a quantidade no nome da refeição para melhorar a estimativa.</span>
        </div>
        {avisoIA && <p className="aviso-ia">{avisoIA}</p>}
      </section>

      <section className="painel">
        <h2>Refeições do dia</h2>
        {erro && <p className="erro" role="alert">{erro}</p>}
        {refeicoes.length === 0 ? <p className="vazio">Nenhuma refeição cadastrada nesta data.</p> :
          <ul>{refeicoes.map(refeicao => (
            <li key={refeicao.id}>
              <span>{refeicao.nome}<small>{refeicao.calorias} kcal</small></span>
              <button className="excluir" onClick={() => excluir(refeicao.id)}>Excluir</button>
            </li>
          ))}</ul>}
      </section>
    </main>
  )
}
