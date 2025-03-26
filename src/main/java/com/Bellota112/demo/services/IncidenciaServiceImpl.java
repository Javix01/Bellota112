package com.Bellota112.demo.services;

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
        return incidenciaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechacreacion"));
    }
    
    public List<IncidenciaConUsuario> obtenerIncidenciasConUsuario() {
        List<Incidencia> incidencias = incidenciaRepository.findAll();

        // Ordenar las incidencias: primero activas, luego por fecha de creación
        incidencias.sort(Comparator.comparing(Incidencia::isActivo).reversed() // Activas primero
                .thenComparing(Incidencia::getFechaCreacion, Comparator.reverseOrder())); // Por fecha descendente

        return incidencias.stream().map(incidencia -> {
            Optional<Usuario> usuario = usuarioRepository.findByBellota(incidencia.getBellota());
            return new IncidenciaConUsuario(incidencia, usuario.orElse(null)); // Usamos un DTO para transportar los datos combinados
        }).collect(Collectors.toList());
    }


    
    
    
    //ELIMINAR
    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public void eliminarIncidencia(String id) {
        incidenciaRepository.deleteById(id);
    }
}