package es.albandroid.feria11.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.albandroid.feria11.util.Constants;

public class Evento implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private String descripcion;
	private String descripcion2; 
	private Double  Coordenada_x;
	private Double  Coordenada_y;
	private Date HoraInicio; //hora de inicio, con fecha incluída
	private Date HoraFin; //hora de fin, con fecha incluída
	private Integer tipo;
	private Integer icono;
	
	public String getDescripcion2() {
		return descripcion2;
	}
	public Integer getTipo() {
		return tipo;
	}
	public Integer getIcono() {
		return icono;
	}
	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}
	public Double getCoordenada_x() {
		return Coordenada_x;
	}
	public void setCoordenada_x(Double coordenada_x) {
		Coordenada_x = coordenada_x;
	}
	public Double getCoordenada_y() {
		return Coordenada_y;
	}
	public void setCoordenada_y(Double coordenada_y) {
		Coordenada_y = coordenada_y;
	}
	public Date getHoraInicio() {
		return HoraInicio;
	}
	public String getHoraInicioFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_HOUR);	
		return sdf.format(HoraInicio);
	}
	public String getDiaInicioFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_HOUR_DAY);	
		return sdf.format(HoraInicio);
	}
	
	public String getDiaFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_DAY);
		return sdf.format(HoraInicio);
	}
	public void setHoraInicio(Date date) {
		this.HoraInicio = date;
	}
	public Date getHoraFin() {
		return HoraFin;
	}
	public void setHoraFin(Date date) {
		this.HoraFin = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Evento(Integer id, String nombre, 
			String descripcion, String descripcion2, 
			Double Coordenada_x, Double Coordenada_y, 
			Date horainicio, Date horafin, Integer tp, Integer icon) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcion2 = descripcion2;
		this.Coordenada_x = Coordenada_x; 
		this.Coordenada_y = Coordenada_y;
		this.HoraInicio = horainicio;
		this.HoraFin = horafin;
		this.tipo = tp;
		this.icono = icon;
	}
	
	@Deprecated
	public Evento(Integer id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	
	}

	public Evento(){
		this.id = new Integer(-1);
		this.nombre = "";
		this.descripcion = "";
		this.descripcion = "";
		this.descripcion2 = "";
		this.Coordenada_x = new Double(0); 
		this.Coordenada_y = new Double(0); 
		this.HoraInicio = new Date();
		this.HoraFin = new Date();
		this.tipo = 0;
		this.icono = 0;
	}
	
}
