import { useEffect, useState } from 'react'

const hoje = () => {
  const data = new Date()
  return [data.getFullYear(), String(data.getMonth() + 1).padStart(2, '0'), String(data.getDate()).padStart(2, '0')].join('-')
}

const numero = (valor) => Number(valor || 0).toLocaleString('pt-BR')

const dataFormatada = (valor) => {
  if (!valor) return ''
  const [ano, mes, dia] = valor.split('-').map(Number)
  return new Intl.DateTimeFormat('pt-BR', { day: '2-digit', month: 'short', year: 'numeric' })
    .format(new Date(ano, mes - 1, dia))
}

export default function App() {
  const [data, setData] = useState(hoje)
  const [refeicoes, setRefeicoes] = useState([])
  const [historico, setHistorico] = useState({ mediaCalorias: 0, diasRegistrados: 0, dias: [] })
  const [nome, setNome] = useState('')
  const [calorias, setCalorias] = useState('')
  const [descricaoIA, setDescricaoIA] = useState('')
  const [resultadoIA, setResultadoIA] = useState(null)
  const [erro, setErro] = useState('')
  const [erroIA, setErroIA] = useState('')
  const [aviso, setAviso] = useState('')
  const [salvando, setSalvando] = useState(false)
  const [estimando, setEstimando] = useState(false)

  async function carregarDia() {
    try {
      const resposta = await fetch('/api/refeicoes?data=' + encodeURIComponent(data))
      if (!resposta.ok) throw new Error('Não foi possível carregar as refeições.')
      setRefeicoes(await resposta.json())
      setErro('')
    } catch (e) {
      setErro(e.message)
    }
  }

  async function carregarHistorico() {
    try {
      const resposta = await fetch('/api/refeicoes/historico')
      if (!resposta.ok) throw new Error('Não foi possível carregar o histórico.')
      setHistorico(await resposta.json())
    } catch (e) {
      setErro(e.message)
    }
  }

  useEffect(() => { carregarDia() }, [data])
  useEffect(() => { carregarHistorico() }, [])

  async function adicionar(evento) {
    evento.preventDefault()
    if (!nome.trim() || calorias === '' || !Number.isInteger(Number(calorias)) || Number(calorias) < 0) {
      setErro('Preencha o nome e um valor inteiro de calorias.')
      return
    }
    setSalvando(true)
    setErro('')
    try {
      const resposta = await fetch('/api/refeicoes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome: nome.trim(), calorias: Number(calorias), data })
      })
      if (!resposta.ok) throw new Error('Não foi possível salvar a refeição.')
      setNome('')
      setCalorias('')
      setAviso('Refeição cadastrada com sucesso!')
      await Promise.all([carregarDia(), carregarHistorico()])
    } catch (e) {
      setErro(e.message)
    } finally {
      setSalvando(false)
    }
  }

  async function excluir(id) {
    setErro('')
    try {
      const resposta = await fetch('/api/refeicoes/' + id, { method: 'DELETE' })
      if (!resposta.ok) throw new Error('Não foi possível excluir a refeição.')
      setAviso('Refeição excluída.')
      await Promise.all([carregarDia(), carregarHistorico()])
    } catch (e) {
      setErro(e.message)
    }
  }

  async function estimarCalorias() {
    if (!descricaoIA.trim()) {
      setErroIA('Descreva o que você comeu antes de estimar.')
      return
    }
    setEstimando(true)
    setErroIA('')
    setResultadoIA(null)
    try {
      const resposta = await fetch('/api/ia/estimar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ descricao: descricaoIA.trim() })
      })
      if (!resposta.ok) {
        const detalhes = await resposta.json().catch(() => ({}))
        throw new Error(detalhes.erro || 'Não foi possível estimar as calorias. Tente novamente.')
      }
      const dados = await resposta.json()
      if (!Number.isInteger(dados.calorias) || dados.calorias < 0) {
        throw new Error('A IA retornou um valor inválido. Tente novamente.')
      }
      setResultadoIA(dados.calorias)
    } catch (e) {
      setErroIA(e.message)
    } finally {
      setEstimando(false)
    }
  }

  function aproveitarEstimativa() {
    if (resultadoIA === null) return
    setNome(descricaoIA.trim().slice(0, 150))
    setCalorias(String(resultadoIA))
    setAviso('Estimativa preenchida! Confira os dados e clique em Adicionar refeição.')
    document.getElementById('cadastro')?.scrollIntoView({ behavior: 'smooth', block: 'center' })
  }

  function selecionarDia(dia) {
    setData(dia)
    setAviso('')
    document.getElementById('refeicoes-do-dia')?.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }

  const total = refeicoes.reduce((soma, refeicao) => soma + refeicao.calorias, 0)
  const maiorDia = Math.max(1, ...historico.dias.map(dia => dia.totalCalorias))

  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="topbar-inner">
          <div className="brand">
            <span className="brand-icon">✳</span>
            <span>calorias<span className="brand-light">diárias</span></span>
          </div>
          <span className="topbar-tag"><span className="status-dot" /> Seu diário alimentar</span>
        </div>
      </header>

      <main className="pagina">
        <section className="intro">
          <p className="eyebrow">ACOMPANHAMENTO SIMPLIFICADO</p>
          <h1>Alimente-se bem.<br /><span>Organize seu dia.</span></h1>
          <p>Registre o que você come, acompanhe suas calorias e veja sua evolução de forma simples.</p>
        </section>

        <section className="stats-grid" aria-label="Resumo">
          <div className="stat-card stat-destaque">
            <div className="stat-label"><span>Calorias do dia</span><span className="stat-icon">◉</span></div>
            <div className="stat-value">{numero(total)} <small>kcal</small></div>
            <label className="data-controle" htmlFor="data"><span>Data selecionada</span>
              <input id="data" type="date" value={data} onChange={e => e.target.value && setData(e.target.value)} />
            </label>
          </div>
          <div className="stat-card">
            <div className="stat-label"><span>Média por dia</span><span className="stat-icon roxo">◈</span></div>
            <div className="stat-value">{numero(historico.mediaCalorias)} <small>kcal</small></div>
            <p className="stat-footnote">Apenas dias com refeições registradas</p>
          </div>
          <div className="stat-card">
            <div className="stat-label"><span>Dias registrados</span><span className="stat-icon azul">▦</span></div>
            <div className="stat-value">{historico.diasRegistrados} <small>dias</small></div>
            <p className="stat-footnote">Histórico salvo no seu computador</p>
          </div>
        </section>

        <section className="entry-grid" aria-label="Adicionar refeição ou estimar com IA">
          <div className="painel manual-panel" id="cadastro">
            <div className="panel-heading">
              <span className="heading-icon verde">＋</span>
              <div>
                <h2>Adicionar refeição</h2>
                <p>Preencha você mesmo os dados.</p>
              </div>
            </div>
            <form className="form-refeicao" onSubmit={adicionar}>
              <label htmlFor="nome">O que você comeu?</label>
              <input id="nome" required maxLength={150} placeholder="Ex.: Arroz, feijão e frango"
                value={nome} onChange={e => setNome(e.target.value)} />
              <label htmlFor="calorias">Calorias da refeição</label>
              <div className="input-com-unidade">
                <input id="calorias" required type="number" min="0" max="999999" step="1"
                  placeholder="Ex.: 550" value={calorias} onChange={e => setCalorias(e.target.value)} />
                <span>kcal</span>
              </div>
              <button className="botao-principal" type="submit" disabled={salvando}>
                {salvando ? 'Salvando...' : 'Adicionar refeição'} <span>＋</span>
              </button>
            </form>
            {aviso && <p className="mensagem sucesso" role="status">{aviso}</p>}
          </div>

          <div className="painel ia-panel">
            <div className="panel-heading">
              <span className="heading-icon violeta">✦</span>
              <div>
                <h2>Estimativa com IA</h2>
                <p>Descreva o prato e deixe o Gemini estimar.</p>
              </div>
            </div>
            <label className="rotulo-ia" htmlFor="descricao-ia">Descreva sua refeição</label>
            <textarea id="descricao-ia" rows={4} maxLength={300}
              placeholder="Ex.: 150g de arroz cozido, 100g de feijão e 150g de frango grelhado"
              value={descricaoIA} onChange={e => { setDescricaoIA(e.target.value); setResultadoIA(null); setErroIA('') }} />
            <p className="dica"><span>✧</span> Dica: informar gramas e preparo melhora a estimativa.</p>
            <button className="botao-ia" type="button" disabled={estimando || !descricaoIA.trim()} onClick={estimarCalorias}>
              <span>✦</span> {estimando ? 'Calculando estimativa...' : 'Estimar com IA'}
            </button>
            {erroIA && <p className="mensagem erro" role="alert">{erroIA}</p>}
            {resultadoIA !== null && (
              <div className="resultado-ia">
                <span>Estimativa da sua refeição</span>
                <strong>{numero(resultadoIA)} <small>kcal</small></strong>
                <p>Valor aproximado. Confira antes de cadastrar.</p>
                <button className="botao-aproveitar" type="button" onClick={aproveitarEstimativa}>
                  Usar no cadastro <span>↗</span>
                </button>
              </div>
            )}
          </div>
        </section>

        <section className="conteudo-grid">
          <div className="painel refeicoes-panel" id="refeicoes-do-dia">
            <div className="section-heading">
              <div>
                <p className="eyebrow">DIÁRIO ALIMENTAR</p>
                <h2>Refeições do dia</h2>
                <p>{dataFormatada(data)} · {refeicoes.length} {refeicoes.length === 1 ? 'refeição' : 'refeições'}</p>
              </div>
              <span className="section-symbol">☷</span>
            </div>
            {erro && <p className="mensagem erro" role="alert">{erro}</p>}
            {refeicoes.length === 0 ? (
              <div className="estado-vazio"><span>◌</span><strong>Nenhuma refeição por aqui</strong>
                <p>Adicione sua primeira refeição para começar.</p></div>
            ) : (
              <ul className="lista-refeicoes">{refeicoes.map(refeicao => (
                <li key={refeicao.id}>
                  <div className="refeicao-info"><span className="refeicao-marcador">●</span>
                    <div><strong>{refeicao.nome}</strong><small>{numero(refeicao.calorias)} kcal</small></div>
                  </div>
                  <button className="botao-excluir" type="button" onClick={() => excluir(refeicao.id)} aria-label={'Excluir ' + refeicao.nome}>Excluir</button>
                </li>
              ))}</ul>
            )}
          </div>

          <div className="painel historico-panel" id="historico">
            <div className="section-heading">
              <div>
                <p className="eyebrow">SUA EVOLUÇÃO</p>
                <h2>Histórico de dias</h2>
                <p>Totais e média dos dias registrados.</p>
              </div>
              <span className="section-symbol">▥</span>
            </div>
            {historico.dias.length === 0 ? (
              <div className="estado-vazio"><span>▤</span><strong>Seu histórico começa aqui</strong>
                <p>Os dias aparecerão depois que você cadastrar refeições.</p></div>
            ) : (
              <div className="lista-historico">
                {historico.dias.map(dia => (
                  <button type="button" className={'linha-historico' + (dia.data === data ? ' selecionado' : '')}
                    key={dia.data} onClick={() => selecionarDia(dia.data)}
                    title="Ver as refeições deste dia">
                    <span className="historico-meta"><strong>{dataFormatada(dia.data)}</strong>
                      <small>{dia.quantidadeRefeicoes} {dia.quantidadeRefeicoes === 1 ? 'refeição' : 'refeições'}</small>
                    </span>
                    <span className="historico-barra"><span style={{ width: Math.max(3, dia.totalCalorias / maiorDia * 100) + '%' }} /></span>
                    <span className="historico-total">{numero(dia.totalCalorias)} <small>kcal</small></span>
                  </button>
                ))}
              </div>
            )}
          </div>
        </section>

        <footer>Calorias Diárias <span>·</span> Feito para acompanhar sua alimentação de forma simples.</footer>
      </main>
    </div>
  )
}
