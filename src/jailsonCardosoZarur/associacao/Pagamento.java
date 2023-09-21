package jailsonCardosoZarur.associacao;

import java.util.Date;

public class Pagamento {
	private double valor;
	private Associado a;
	private Date data;
	private Taxa tx;
	
	public Pagamento(double valor, Associado a, Date data, Taxa tx) {
		this.valor = valor;
		this.a = a;
		this.data = data;
		this.tx = tx;
	}

	public double getValor() {
		return valor;
	}

	public Associado getA() {
		return a;
	}

	public Date getData() {
		return data;
	}

	public Taxa getTx() {
		return tx;
	}
}
