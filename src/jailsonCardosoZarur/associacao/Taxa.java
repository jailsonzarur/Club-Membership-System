package jailsonCardosoZarur.associacao;

public class Taxa {

    private String nome;
    private int vigencia;
    private double valorAno;
    private int parcelas;

    private double valorParcelas;
    private boolean administrativa;

    public Taxa(String nome, int vigencia, double valorAno, int parcelas, boolean administrativa){
        this.nome = nome;
        this.vigencia = vigencia;
        this.valorAno = valorAno;
        this.parcelas = parcelas;
        this.administrativa = administrativa;
        this.valorParcelas = valorAno/parcelas;
    }

    public String getNome() {
        return nome;
    }

    public int getVigencia() {
        return vigencia;
    }

    public double getValorAno() {
        return valorAno;
    }
    

    public void setValorAno(double valorAno) {
		this.valorAno = valorAno;
	}

	public int getParcelas() {
        return parcelas;
    }

    public double getValorParcelas(){
        return valorParcelas;
    }
    public boolean isAdministrativa() {
        return administrativa;
    }
}
