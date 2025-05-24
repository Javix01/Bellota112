package com.Bellota112.demo.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "incidencias")
public class Incidencia {

	@Id
	private String id;
	private int bellota;
	private Localizacion localizacion;
	private int incidencias;
	private boolean activo;
	private String foto; //En base64
	private LocalDateTime fechaCreacion;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int __v;

	// Clase interna para la localización
	public static class Localizacion {
		private double latitud;
		private double longitud;

		public Localizacion() {}

		public Localizacion(double latitud, double longitud) {
			this.latitud = latitud;
			this.longitud = longitud;
		}

		// Getters y setters
		public double getLatitud() {
			return latitud;
		}

		public void setLatitud(double latitud) {
			this.latitud = latitud;
		}

		public double getLongitud() {
			return longitud;
		}

		public void setLongitud(double longitud) {
			this.longitud = longitud;
		}
	}

	public Incidencia() {}

	// Getters y setters
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

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	public int getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(int incidencias) {
		this.incidencias = incidencias;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int get__v() {
		return __v;
	}

	public void set__v(int __v) {
		this.__v = __v;
	}
}