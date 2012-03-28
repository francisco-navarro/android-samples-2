package es.albandroid.feria11.bbdd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.albandroid.feria11.beans.Evento;

public interface BDFeria {
	
	public List<Evento> getEventoDia(Date date);
	
	public List<Evento> getEventoHora(Date date);
	
	public Evento getEventoById(Integer id);
	
	public void open();

	public void close();
	
	public List<Evento> getEventoDiaIcono(Date date, Integer icono);

	public List<Evento> getEventoDiaTipo(Date date, Integer tipo);

	public ArrayList<Evento>[] getEventoDiaTipoIcono(Date date, Integer tipo, Integer icono);

	List<Evento> buscar(String query);
}
