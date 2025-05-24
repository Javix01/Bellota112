package com.Bellota112.demo.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.Bellota112.demo.domain.Incidencia;
import com.Bellota112.demo.domain.IncidenciaConUsuario;
import com.Bellota112.demo.domain.Usuario;
import com.Bellota112.demo.repositories.IncidenciaRepository;
import com.Bellota112.demo.repositories.UsuarioRepository;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository, UsuarioRepository usuarioRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Incidencia> obtenerTodasLasIncidencias() {
        return incidenciaRepository.findAll();
    }

    public Incidencia obtenerIncidenciaPorId(String id) {
        return incidenciaRepository.findById(id).orElse(null);
    }

    public void actualizarIncidencia(Incidencia incidencia) {
        incidenciaRepository.save(incidencia);
    }

    public List<Incidencia> obtenerIncidenciasOrdenadasDesc() {
        // Cambiado de "fechacreacion" a "fechaCreacion" para coincidir con el campo actualizado
        return incidenciaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion"));
    }

    public List<IncidenciaConUsuario> obtenerIncidenciasConUsuario() {
        List<Incidencia> incidencias = incidenciaRepository.findAll();

        // Ordenar las incidencias: primero activas, luego por fecha de creación
        incidencias.sort(Comparator.comparing(Incidencia::isActivo).reversed() // Activas primero
                .thenComparing(Incidencia::getFechaCreacion, Comparator.reverseOrder())); // Por fecha descendente

        return incidencias.stream().map(incidencia -> {
            Optional<Usuario> usuario = usuarioRepository.findByBellota(incidencia.getBellota());
            return new IncidenciaConUsuario(incidencia, usuario.orElse(null));
        }).collect(Collectors.toList());
    }

    public Incidencia guardarIncidencia(Incidencia incidencia) {
        // Establecer fechas de creación y actualización si no están establecidas
        if(incidencia.getFechaCreacion() == null) {
            incidencia.setFechaCreacion(LocalDateTime.now());
        }
        if(incidencia.getCreatedAt() == null) {
            incidencia.setCreatedAt(LocalDateTime.now());
        }
        incidencia.setUpdatedAt(LocalDateTime.now());

        return incidenciaRepository.save(incidencia);
    }

    public void eliminarIncidencia(String id) {
        incidenciaRepository.deleteById(id);
    }

    // Método adicional para buscar por localización (ejemplo)
    public List<Incidencia> obtenerIncidenciasPorLocalizacion(double latitud, double longitud, double radio) {
        // Implementación dependería de tu repositorio y necesidades
        // Esto es solo un ejemplo conceptual
        return incidenciaRepository.findAll().stream()
                .filter(incidencia -> {
                    Incidencia.Localizacion loc = incidencia.getLocalizacion();
                    // Filtro simplificado (deberías usar una fórmula de distancia real)
                    return Math.abs(loc.getLatitud() - latitud) <= radio &&
                            Math.abs(loc.getLongitud() - longitud) <= radio;
                })
                .collect(Collectors.toList());
    }
}