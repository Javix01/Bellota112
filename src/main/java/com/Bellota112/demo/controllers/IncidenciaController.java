package com.Bellota112.demo.controllers;

import java.time.LocalDateTime;
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
    public String listarIncidencias(@RequestParam(value = "id", required = false) String id,
                                    @RequestParam(value = "filtro", required = false) String filtro,
                                    Model model) {
        Usuario usuarioLogueado = usuarioService.obtenerUsuarioLogueado();
        List<IncidenciaConUsuario> incidenciasConUsuario = incidenciaService.obtenerIncidenciasConUsuario();

        // Filtrar las incidencias si el usuario no pertenece a "112 Extremadura"
        if (!"112 Extremadura".equals(usuarioLogueado.getCuerpo())) {
            incidenciasConUsuario = incidenciasConUsuario.stream()
                    .filter(incidenciaConUsuario ->
                            incidenciaConUsuario.getUsuario() != null &&
                                    usuarioLogueado.getCuerpo().equals(incidenciaConUsuario.getUsuario().getCuerpo()) &&
                                    usuarioLogueado.getZona().equals(incidenciaConUsuario.getUsuario().getZona()))
                    .collect(Collectors.toList());
        }

        // Aplicar filtro adicional si está presente
        if (filtro != null && !filtro.isEmpty()) {
            incidenciasConUsuario = incidenciasConUsuario.stream()
                    .filter(incidenciaConUsuario ->
                            String.valueOf(incidenciaConUsuario.getIncidencias()).contains(filtro) ||
                                    (incidenciaConUsuario.getLocalizacion() != null &&
                                            String.valueOf(incidenciaConUsuario.getLocalizacion().getLatitud()).contains(filtro)) ||
                                    (incidenciaConUsuario.getUsuario() != null &&
                                            incidenciaConUsuario.getUsuario().getNombre().toLowerCase().contains(filtro.toLowerCase())))
                    .collect(Collectors.toList());
        }

        // Buscar incidencia seleccionada por ID si está presente
        IncidenciaConUsuario incidenciaSeleccionada = null;
        if (id != null) {
            incidenciaSeleccionada = incidenciasConUsuario.stream()
                    .filter(incidenciaConUsuario -> id.equals(incidenciaConUsuario.getId()))
                    .findFirst()
                    .orElse(null);
        }

        // Verificar si hay incidencias activas
        boolean hayIncidenciasActivas = incidenciasConUsuario.stream()
                .anyMatch(incidenciaConUsuario -> incidenciaConUsuario.isActivo());

        model.addAttribute("incidenciaSeleccionada", incidenciaSeleccionada);
        model.addAttribute("incidenciasConUsuario", incidenciasConUsuario);
        model.addAttribute("hayIncidenciasActivas", hayIncidenciasActivas);
        model.addAttribute("filtro", filtro); // Para mantener el filtro en la vista
        return "incidencias";
    }

    @GetMapping("/cambiarEstadoIncidencia/{id}")
    public String cambiarEstadoIncidencia(@PathVariable String id, Model model) {
        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(id);
        if (incidencia != null) {
            incidencia.setActivo(!incidencia.isActivo());
            incidencia.setUpdatedAt(LocalDateTime.now()); // Actualizar fecha de modificación
            incidenciaService.actualizarIncidencia(incidencia);
            model.addAttribute("success", "Estado de la incidencia actualizado correctamente.");
        } else {
            model.addAttribute("error", "Incidencia no encontrada.");
        }
        return "redirect:/incidencias";
    }

    @PostMapping("/incidencias/guardar")
    public String guardarIncidencia(@ModelAttribute Incidencia incidencia, Model model) {
        try {
            if (incidencia.getId() == null || incidencia.getId().isEmpty()) {
                // Nueva incidencia
                incidencia.setFechaCreacion(LocalDateTime.now());
                incidencia.setCreatedAt(LocalDateTime.now());
            }
            incidencia.setUpdatedAt(LocalDateTime.now());

            incidenciaService.guardarIncidencia(incidencia);
            model.addAttribute("success", "Incidencia guardada correctamente.");
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la incidencia: " + e.getMessage());
        }
        return "redirect:/incidencias";
    }
}