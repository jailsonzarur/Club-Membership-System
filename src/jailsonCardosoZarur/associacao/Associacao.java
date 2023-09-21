package jailsonCardosoZarur.associacao;

import java.util.ArrayList;

public class Associacao {

    private int num;
    private String nome;
    ArrayList<Associado> associados = new ArrayList<>();
    ArrayList<Reuniao> reunioes = new ArrayList<>();
    ArrayList<Taxa> taxas = new ArrayList<>();
    ArrayList<Pagamento> pagamentos = new ArrayList<>();

    public Associacao(int num, String nome){
        this.num = num;
        this.nome = nome;
    }

    public int getNum() {
        return num;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public Associado pesquisarAssociado(int numero) throws AssociadoNaoExistente {
        for(Associado as : associados){
            if(as.getNumero() == numero) return as;
        }
        throw new AssociadoNaoExistente();
    }

    public Taxa pesquisarTaxa(String nome, int vigencia) throws TaxaNaoExistente {
    	for(Taxa txs : taxas) {
    		if(txs.getNome() == nome && txs.getVigencia() == vigencia) {
    			return txs;
    		}
    	}
    	throw new TaxaNaoExistente();
    }

}
