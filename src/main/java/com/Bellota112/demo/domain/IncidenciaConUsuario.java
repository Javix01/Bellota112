package com.Bellota112.demo.domain;

import java.time.LocalDateTime;
import java.util.Date;


public class IncidenciaConUsuario {
    private Incidencia incidencia;
    private Usuario usuario;

    public IncidenciaConUsuario(Incidencia incidencia, Usuario usuario) {
        this.incidencia = incidencia;
        this.usuario = usuario;
    }

    public Incidencia getIncidencia() { return incidencia; }
    public Usuario getUsuario() { return usuario; }
    //Getters para acceder a los datos de incidencia y usuario
    public String getId() {return incidencia.getId();}
    public int getBellota() {return incidencia.getBellota();}
    public String getLocalizacion() {return incidencia.getLocalizacion();}
    public int getIncidenciaTipo() {return incidencia.getIncidencia();}
    public LocalDateTime getFechaCreacion() {return incidencia.getFechaCreacion();}
    public boolean isActivo() {return incidencia.isActivo();}
}