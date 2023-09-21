package jailsonCardosoZarur.associacao;

import java.util.Date;

public class AssociadoRemido extends Associado{

    private Date dataRemissao;

    public AssociadoRemido(int numero, String nome, String telefone, long dataAssociacao, long nascimento, long dataRemissao){
        super(numero, nome, telefone, dataAssociacao, nascimento);
        this.dataRemissao = new Date(dataRemissao);
    }
}
