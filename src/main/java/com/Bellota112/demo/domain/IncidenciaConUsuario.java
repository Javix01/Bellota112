package com.Bellota112.demo.domain;

import java.time.LocalDateTime;

public class IncidenciaConUsuario {
    private Incidencia incidencia;
    private Usuario usuario;

    public IncidenciaConUsuario(Incidencia incidencia, Usuario usuario) {
        this.incidencia = incidencia;
        this.usuario = usuario;
    }

    // Getters principales
    public Incidencia getIncidencia() { return incidencia; }
    public Usuario getUsuario() { return usuario; }

    // Getters delegados para los datos de incidencia
    public String getId() { return incidencia.getId(); }
    public int getBellota() { return incidencia.getBellota(); }
    public Incidencia.Localizacion getLocalizacion() { return incidencia.getLocalizacion(); }
    public int getIncidencias() { return incidencia.getIncidencias(); }
    public boolean isActivo() { return incidencia.isActivo(); }
    public String getFoto() { return incidencia.getFoto(); }
    public LocalDateTime getFechaCreacion() { return incidencia.getFechaCreacion(); }
    public LocalDateTime getCreatedAt() { return incidencia.getCreatedAt(); }
    public LocalDateTime getUpdatedAt() { return incidencia.getUpdatedAt(); }
    public int get__v() { return incidencia.get__v(); }

    // Getters delegados para los datos de usuario (si existen)
    // Aquí deberías agregar los getters para los campos del usuario que necesites
    // Por ejemplo:
    // public String getNombreUsuario() { return usuario.getNombre(); }
    // public String getEmailUsuario() { return usuario.getEmail(); }
}