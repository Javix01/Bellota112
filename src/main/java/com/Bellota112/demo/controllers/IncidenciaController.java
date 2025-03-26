package com.Bellota112.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Bellota112.demo.domain.Incidencia;
import com.Bellota112.demo.domain.IncidenciaConUsuario;
import com.Bellota112.demo.domain.Usuario;
import com.Bellota112.demo.services.IncidenciaService;
import com.Bellota112.demo.services.UsuarioService;

@Controller
public class IncidenciaController {

	private final IncidenciaService incidenciaService;
	private final UsuarioService usuarioService;

    @Autowired
    public IncidenciaController(IncidenciaService incidenciaService, UsuarioService usuarioService) {
        this.incidenciaService = incidenciaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/incidencias")
    public String listarIncidencias(@RequestParam(value = "id", required = false) String id, Model model) {
        Usuario usuarioLogueado = usuarioService.obtenerUsuarioLogueado();
        List<IncidenciaConUsuario> incidenciasConUsuario = incidenciaService.obtenerIncidenciasConUsuario();

        // Filtrar las incidencias si el usuario no pertenece a "112 Extremadura"
        if (!"112 Extremadura".equals(usuarioLogueado.getCuerpo())) {
            incidenciasConUsuario = incidenciasConUsuario.stream()
                    .filter(incidenciaConUsuario ->
                            usuarioLogueado.getCuerpo().equals(incidenciaConUsuario.getUsuario().getCuerpo()) &&
                            usuarioLogueado.getZona().equals(incidenciaConUsuario.getUsuario().getZona()))
                    .collect(Collectors.toList());
        }

        // Buscar incidencia seleccionada por ID si está presente
        IncidenciaConUsuario incidenciaSeleccionada = null;
        if (id != null) {
            incidenciaSeleccionada = incidenciasConUsuario.stream()
                    .filter(incidenciaConUsuario -> incidenciaConUsuario.getIncidencia().getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        // Verificar si hay incidencias activas
        boolean hayIncidenciasActivas = incidenciasConUsuario.stream()
                .anyMatch(incidenciaConUsuario -> incidenciaConUsuario.getIncidencia().isActivo());

        model.addAttribute("incidenciaSeleccionada", incidenciaSeleccionada);
        model.addAttribute("incidenciasConUsuario", incidenciasConUsuario);
        model.addAttribute("hayIncidenciasActivas", hayIncidenciasActivas);  // Añadimos esta variable
        return "incidencias";
    }
    
    @GetMapping("/cambiarEstadoIncidencia/{id}")
    public String cambiarEstadoIncidencia(@PathVariable String id, Model model) {
        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(id);
        if (incidencia != null) {
            incidencia.setActivo(!incidencia.isActivo()); // Cambia el estado
            incidenciaService.actualizarIncidencia(incidencia); // Guarda el cambio en la base de datos
            model.addAttribute("success", "Estado de la incidencia actualizado correctamente.");
        } else {
            model.addAttribute("error", "Incidencia no encontrada.");
        }
        return "redirect:/incidencias"; // Redirige a la página de lista de incidencias
    }
}