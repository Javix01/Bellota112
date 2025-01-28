package com.Bellota112.demo.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Incidencia")  // Define el nombre de la colección
public class Incidencia {
	
	@Id
    private String id;
	
	private int bellota;
	private String localizacion;
    private int incidencia;
    private LocalDateTime fechacreacion;
    private boolean activo;
    

	public Incidencia() {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getBellota() {
		return bellota;
	}


	public void setBellota(int bellota) {
		this.bellota = bellota;
	}

	public String getLocalizacion() {
		return localizacion;
	}


	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}


	public int getIncidencia() {
		return incidencia;
	}


	public void setIncidencia(int incidencia) {
		this.incidencia = incidencia;
	}


	public LocalDateTime getFechaCreacion() {
		return fechacreacion;
	}


	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechacreacion = fechaCreacion;
	}


	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this. activo = activo;
	}

	
}