package es.albandroid.feria11.beans;

import java.io.Serializable;

public class Alarma implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer idEvento;
	private Integer idAlarma;
	
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	public Integer getIdAlarma() {
		return idAlarma;
	}
	public void setIdAlarma(Integer idAlarma) {
		this.idAlarma = idAlarma;
	}
	public Alarma(Integer idEvento, Integer idAlarma) {
		super();
		this.idEvento = idEvento;
		this.idAlarma = idAlarma;
	}
	public Alarma() {
		super();
		this.idEvento = null;
		this.idAlarma = null;
	}
}
