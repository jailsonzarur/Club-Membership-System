package jailsonCardosoZarur.associacao;

import java.util.Date;

public class Reuniao {

    private Date data;

	private String ata;

    public Reuniao(long data, String ata){
        this.data = new Date(data);
        this.ata = ata;
    }

    public Reuniao(Date data, String ata){
        this.data = data;
        this.ata = ata;
    }

    public Date getData() {
        return data;
    }
    
    public String getAta() {
		return ata;
	}
}
