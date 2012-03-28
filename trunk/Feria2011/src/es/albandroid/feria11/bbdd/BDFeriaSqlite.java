package es.albandroid.feria11.bbdd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import es.albandroid.feria11.beans.Evento;

public class BDFeriaSqlite implements BDFeria{

	@Override
	public List<Evento> getEventoDia(Date date) {
		
		return null;
	}

	@Override
	public List<Evento> getEventoHora(Date date) {
		
		return null;
	}

	@Override
	public Evento getEventoById(Integer id) {
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date(System.currentTimeMillis()));

		return new Evento(new Integer(-1), 
				"TIRO DE PRECISION. ", 
				"“Tirada de Armas Históricas III Centenario”. En las instalaciones del Club Albacetense de Tiro de Precisión, Carretera de Madrid, Km. 234. Organiza C.A.T.P.", 
				"", 
				38.997274*1E6, 
				-1.870422*1E6, 
				cal.getTime(), 
				null, 0, 0);
	}

	@Override
	public void open() {
		
		
	}
	
	@Override
	public void close() {
		
		
	}

	@Override
	public List<Evento> getEventoDiaIcono(Date date, Integer icono) {
		
		return null;
	}

	@Override
	public List<Evento> getEventoDiaTipo(Date date, Integer tipo) {
		
		return null;
	}

	@Override
	public ArrayList<Evento>[] getEventoDiaTipoIcono(Date date, Integer tipo,
			Integer icono) {
		
		return null;
	}

	@Override
	public List<Evento> buscar(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
