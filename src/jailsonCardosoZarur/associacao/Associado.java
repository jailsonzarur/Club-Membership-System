package jailsonCardosoZarur.associacao;

import java.util.ArrayList;
import java.util.Date;

public class Associado {

    private int numero;
	private String nome;
    private String telefone;
    private Date dataAssociacao;
    private Date nascimento;

    ArrayList<Reuniao> reunioes = new ArrayList<>();
    ArrayList<Taxa> taxas = new ArrayList<>();

    public Associado(int numero, String nome, String telefone, long dataAssociacao, long nascimento){
        this.numero = numero;
        this.nome = nome;
        this.telefone = telefone;
        this.dataAssociacao = new Date(dataAssociacao);
        this.nascimento = new Date(nascimento);
    }

    public int getNumero() {
        return numero;
    }

    public Date getDataAssociacao() {
        return dataAssociacao;
    }
    
    public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public Date getNascimento() {
		return nascimento;
	}

    public boolean pesquisarReuniao(Reuniao r){
        for(Reuniao rtemp : reunioes){
            if(r.equals(rtemp)) return false;
        }
        return true;
    }
    public void registrarPresenca(Reuniao r){
        if(pesquisarReuniao(r)){
            reunioes.add(r);
        }
    }
    
    public Taxa pesquisarTaxa(String nome, int vigencia) throws TaxaNaoExistente {
    	for(Taxa txs : taxas) {
    		if(txs.getNome() == nome && txs.getVigencia() == vigencia) {
    			return txs;
    		}
    	}
    	throw new TaxaNaoExistente();
    }
    
    public double statusTaxas() {
    	double total = 0;
    	for(Taxa tx : taxas) {
    		total += tx.getValorAno();
    	}
    	return total;
    }
}
