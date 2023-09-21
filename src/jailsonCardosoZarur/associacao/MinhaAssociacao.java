package jailsonCardosoZarur.associacao;

import java.util.ArrayList;
import java.util.Date;

public class MinhaAssociacao implements InterfaceAssociacao{
    ArrayList<Associacao> associacoes = new ArrayList<>();
    @Override
    public double calcularFrequencia(int numAssociado, int numAssociacao, long inicio, long fim) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente {
    	
    	Associacao as = pesquisarAssociacao(numAssociacao);
    	Associado a = as.pesquisarAssociado(numAssociado);
    	double qtdreuas = 0;
    	double qtdnoint = 0;
    	double qtdreua = 0;
		Date dinicio = new Date(inicio);
		Date dfim = new Date(fim);
    	
    	for(Reuniao rs : as.reunioes) {
    		qtdreuas++;
    		if(rs.getData().getTime() >= inicio && rs.getData().getTime() <= fim) {
    			qtdnoint++;
    			for( Reuniao rsa : a.reunioes ) {
    				if(rs.getData().getTime() == rsa.getData().getTime()) {
    					qtdreua++;
    				}
    			}
    		}
    	}
    	if(qtdnoint == 0) throw new ReuniaoNaoExistente();
    	
    	

        return (qtdreua/qtdreuas);
    }

    @Override
    public void registrarFrequencia(int codigoAssociado, int numAssociacao, long dataReuniao) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente, FrequenciaJaRegistrada, FrequenciaIncompativel {
        Associacao associacao = pesquisarAssociacao(numAssociacao);
        Associado associado = associacao.pesquisarAssociado(codigoAssociado);
		Date ddataReuniao = new Date(dataReuniao);
        if(ddataReuniao.before(associado.getDataAssociacao())){
            throw new FrequenciaIncompativel();
        }
        Reuniao rtemp = pesquisarReuniao(numAssociacao, ddataReuniao);
        if(associado.pesquisarReuniao(rtemp)){
			associado.registrarPresenca(rtemp);
        }else{
			throw new FrequenciaJaRegistrada();
        }

    }

    @Override
    public void registrarPagamento(int numAssociacao, String taxa, int vigencia, int numAssociado, long data, double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido {
    	Date ddata = new Date(data);
		if(taxa.trim().isEmpty() || vigencia <= 0 || numAssociado <= 0 || numAssociacao <= 0 || ddata == null || valor <= 0) throw new ValorInvalido();
    	Associacao as = pesquisarAssociacao(numAssociacao);
    	Associado a = as.pesquisarAssociado(numAssociado);
    	Taxa tx = as.pesquisarTaxa(taxa, vigencia);
    	Taxa txs = a.pesquisarTaxa(taxa, vigencia);
    	if(a instanceof AssociadoRemido && tx.isAdministrativa()) {
    		throw new AssociadoJaRemido();
    	}
    	
    	if(txs.getValorAno() < txs.getValorParcelas()) {
    		if(valor == txs.getValorAno()) {
    			txs.setValorAno(txs.getValorAno()-valor);
    		}
    	}
    	
    	if(valor >= txs.getValorParcelas()) {
    		txs.setValorAno(txs.getValorAno()-valor);
    	}

		if(txs.getValorAno() > txs.getValorParcelas() && valor < txs.getValorParcelas()){
			throw new ValorInvalido();
		}
    	
    	Pagamento p = new Pagamento(valor, a, ddata, tx);
    	as.pagamentos.add(p);
    	if(a.statusTaxas() == 0) {
			long ddas = a.getDataAssociacao().getTime();
			long ddns = a.getNascimento().getTime();
			long ddd = ddata.getTime();
    		AssociadoRemido ar = new AssociadoRemido(a.getNumero(), a.getNome(), a.getTelefone(), ddas, ddns, ddd);
    		a = ar;
    	}
    }



    @Override
    public double somarPagamentoDeAssociado(int numAssociacao, int numAssociado, String nomeTaxa, int vigencia, long inicio, long fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente {
    	Associacao as = pesquisarAssociacao(numAssociacao);
    	Associado a = as.pesquisarAssociado(numAssociado);
    	Taxa tx = as.pesquisarTaxa(nomeTaxa, vigencia);
    	double total = 0;
		Date dinicio = new Date(inicio);
		Date dfim = new Date(fim);
    	
    	for(Pagamento pgs : as.pagamentos) {
    		if(pgs.getTx().equals(tx) && pgs.getA().equals(a)) {
    			if(pgs.getData().getTime() == inicio && pgs.getData().getTime() == fim) {
    				total += pgs.getValor();
    			}
    		}
    	}
    	return total;
    }

    @Override
    public double calcularTotalDeTaxas(int numAssociacao, int vigencia) throws AssociacaoNaoExistente, TaxaNaoExistente {
        double total = 0;
    	Associacao as = pesquisarAssociacao(numAssociacao);
        for(Taxa t : as.taxas) {
        	if(t.getVigencia() == vigencia) {
        		total += t.getValorAno();
        	}
        }
        return total;
    }

    @Override
    public void adicionar(Associacao a) throws AssociacaoJaExistente, ValorInvalido {
		if(a.getNome() == null) throw new ValorInvalido();
        if(a.getNum() <= 0 || a.getNome().trim().isEmpty()) throw new ValorInvalido();
        boolean flag = false;
        for(Associacao as : associacoes){
            if(as.getNum() == a.getNum()) flag = true;
        }
        if(flag) throw new AssociacaoJaExistente();
        associacoes.add(a);
        
    }

    @Override
    public void adicionar(int associacao, Taxa t) throws AssociacaoNaoExistente, TaxaJaExistente, ValorInvalido {
        if(associacao <= 0) throw new ValorInvalido();
        if(t.getNome().trim().isEmpty() || t.getParcelas() <= 0 || t.getValorAno() <= 0 || t.getVigencia() <= 0) throw new ValorInvalido();
        Associacao a = pesquisarAssociacao(associacao);
        for(Taxa ttemp : a.taxas) {
        	if(ttemp.getVigencia() == t.getVigencia() && ttemp.getNome() == t.getNome()) {
        		throw new TaxaJaExistente();
        	}
        }
        a.taxas.add(t);

		for(Associado ass : a.associados){
			ass.taxas.add(t);
		}
    }

    @Override
    public void adicionar(int associacao, Reuniao r) throws AssociacaoNaoExistente, ReuniaoJaExistente, ValorInvalido {
    	if(associacao <= 0) throw new ValorInvalido();
    	if(r.getData() == null || r.getAta().trim().isEmpty()) {
    		throw new ValorInvalido();
    	}
    	Associacao a = pesquisarAssociacao(associacao);
    	for(Reuniao rtemp : a.reunioes) {
    		if(rtemp.equals(r)) {
    			throw new ReuniaoJaExistente();
    		}
    	}
    	a.reunioes.add(r);
    }

    @Override
    public void adicionar(int associacao, Associado a) throws AssociacaoNaoExistente, AssociadoJaExistente, ValorInvalido {
    	if(associacao <= 0) throw new ValorInvalido();
    	if(a.getNumero() <= 0 || a.getNome().trim().isEmpty() || a.getDataAssociacao() == null || a.getNascimento() == null
    			|| a.getTelefone().trim().isEmpty()) {
    		throw new ValorInvalido();
    	}
    	Associacao ass = pesquisarAssociacao(associacao);
    	for(Associado atemp : ass.associados) {
    		if(atemp.getNumero() == a.getNumero()) {
    			throw new AssociadoJaExistente();
    		}
    	}
    	ass.associados.add(a);
    	for(Taxa txs : ass.taxas) {
    		if(a.getDataAssociacao().getYear() <= txs.getVigencia()) {
    			Taxa ttemp = new Taxa(txs.getNome(), txs.getVigencia(), txs.getValorAno(), txs.getParcelas(), txs.isAdministrativa());
    			a.taxas.add(ttemp);
    		}
    	}
    	//a.getDataAssociacao().getYear()
    }

    public Associacao pesquisarAssociacao(int numero) throws AssociacaoNaoExistente{
        for(Associacao as : associacoes){
            if(as.getNum() == numero) return as;
        }
        throw new AssociacaoNaoExistente();
    }

    public Reuniao pesquisarReuniao(int numAssociacao, Date dataReuniao) throws AssociacaoNaoExistente, ReuniaoNaoExistente {
        Associacao associacao = pesquisarAssociacao(numAssociacao);
        for(Reuniao r : associacao.reunioes){
            if(r.getData().equals(dataReuniao)) return r;
        }
        throw new ReuniaoNaoExistente();
    }
}
