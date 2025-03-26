package com.Bellota112.demo.services;

import java.util.List;

import com.Bellota112.demo.domain.Incidencia;
import com.Bellota112.demo.domain.IncidenciaConUsuario;

public interface IncidenciaService {
	
	public List<Incidencia> obtenerTodasLasIncidencias();

	public Incidencia obtenerIncidenciaPorId(String id);

	public void actualizarIncidencia(Incidencia incidencia);

	//ELIMINAR
	public List<Incidencia> obtenerIncidenciasOrdenadasDesc();
    
	public List<IncidenciaConUsuario> obtenerIncidenciasConUsuario();
    
	
    
    //ELIMINAR
    public Incidencia guardarIncidencia(Incidencia incidencia);
    public void eliminarIncidencia(String id);
}