package com.Bellota112.demo.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.Bellota112.demo.domain.Usuario;
import com.Bellota112.demo.services.UsuarioService;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Mostrar la información del usuario logueado
     */
    @GetMapping("/verUsuario")
    public String verUsuario(Model model) {
        // Obtener el usuario logueado
        Usuario usuarioLogueado = usuarioService.obtenerUsuarioLogueado();
        
        
        // Pasar el usuario al modelo
        model.addAttribute("usuario", usuarioLogueado);

        return "verUsuario";  // Vista que muestra la información del usuario
    }

    /**
     * Mostrar el formulario de registro de usuario
     */
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    /**
     * Registrar un nuevo usuario
     */
    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario, Model model) {
        // Verificar si el email ya está en uso
        if (usuarioService.existeUsuarioConEmail(usuario.getEmail())) {
            model.addAttribute("error", "El email ya está en uso.");
            model.addAttribute("usuario", usuario);  // Mantener los datos ya ingresados
            return "registro";  // Volver al formulario con el mensaje de error
        }

        // Asignar el rol por defecto "USER" si no está asignado
        if (usuario.getRole() == null || usuario.getRole().isEmpty()) {
            usuario.setRole("USER");
        }

        // Registrar al usuario
        usuarioService.registrarUsuario(usuario);
        return "redirect:/personal";  // Redirigir al login con un mensaje de éxito
    }

    /**
     * Mostrar el formulario de actualización de usuario
     * Se recibe el 'id' para obtener el usuario a actualizar
     */
    @GetMapping("/actualizarUsuario/{id}")
    public String mostrarFormularioActualizarUsuario(@PathVariable String id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);  // Obtener el usuario por su ID
        model.addAttribute("usuario", usuario);  // Pasar el usuario al formulario
        return "actualizarUsuario";  // Vista para editar la información del usuario
    }
    
    /**
     * Procesar la actualización de los datos del usuario
     */
    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario(@ModelAttribute Usuario usuarioFormulario) throws IOException {
        // Obtener el usuario logueado
        Usuario usuarioLogueado = usuarioService.obtenerUsuarioLogueado();

        // Solo actualizar si el usuario está autorizado a hacerlo
        if (usuarioLogueado != null) {
            // Actualizar los campos de nombre si han cambiado
            if (usuarioFormulario.getNombre() != null && !usuarioFormulario.getNombre().equals(usuarioLogueado.getNombre())) {
                usuarioLogueado.setNombre(usuarioFormulario.getNombre());
            }

            if (usuarioFormulario.getTelefono() != null && !usuarioFormulario.getTelefono().equals(usuarioLogueado.getTelefono())) {
                usuarioLogueado.setTelefono(usuarioFormulario.getTelefono());
            }

            // Si se ha proporcionado una nueva contraseña, actualizarla
            if (usuarioFormulario.getPassword() != null && !usuarioFormulario.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(usuarioFormulario.getPassword());  // Cifra la nueva contraseña
                usuarioLogueado.setPassword(encodedPassword);  // Asigna la contraseña cifrada
            }

            // Guardar el usuario actualizado en la base de datos
            usuarioService.actualizarUsuario(usuarioLogueado);
        }

        return "redirect:/verUsuario";  // Redirigir a la página de ver usuario
    }
    
    /**
     * Mostrar el formulario de actualización de usuario
     * Se recibe el 'id' para obtener el usuario a actualizar
     */
    @GetMapping("/actualizarUsuarioAdmin/{id}")
    public String mostrarFormularioActualizarUsuarioAdmin(@PathVariable String id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            model.addAttribute("error", "Usuario no encontrado.");
            return "redirect:/personal"; // Redirige si el usuario no existe
        }
        
        Usuario usuarioLogueado = usuarioService.obtenerUsuarioLogueado();
        
        model.addAttribute("usuarioActual", usuarioLogueado);
        model.addAttribute("usuario", usuario);
        
        return "actualizarUsuarioAdmin";
    }

    /**
     * Procesar la actualización de los datos del usuario
     */
    @PostMapping("/actualizarUsuarioAdmin/{id}")
    public String actualizarUsuarioAdmin(@PathVariable String id, @ModelAttribute Usuario usuarioFormulario) {
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioExistente == null) {
            return "redirect:/personal"; // Redirige si el usuario no existe
        }

     // Actualizar los campos con la información proporcionada en el formulario
        if (usuarioFormulario.getNombre() != null) {
            usuarioExistente.setNombre(usuarioFormulario.getNombre());
        }
        if (usuarioFormulario.getRole() != null) {
            usuarioExistente.setRole(usuarioFormulario.getRole());
        }
        if (usuarioFormulario.getTelefono() != null && !usuarioFormulario.getTelefono().trim().isEmpty()) {
            usuarioExistente.setTelefono(usuarioFormulario.getTelefono());
        }
        if (usuarioFormulario.getIdentificacion() != null) {
            usuarioExistente.setIdentificacion(usuarioFormulario.getIdentificacion());
        }
        if (usuarioFormulario.getZona() != null) {
            usuarioExistente.setZona(usuarioFormulario.getZona());
        }
        if (usuarioFormulario.getCuerpo() != null) {
            usuarioExistente.setCuerpo(usuarioFormulario.getCuerpo());
        }
        if (usuarioFormulario.getRango() != null) {
            usuarioExistente.setRango(usuarioFormulario.getRango());
        }
        if (usuarioFormulario.getBellota() >= 0) {
            usuarioExistente.setBellota(usuarioFormulario.getBellota());
        }
        if (usuarioFormulario.getPassword() != null && !usuarioFormulario.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(usuarioFormulario.getPassword());
            usuarioExistente.setPassword(encodedPassword);
        }

        // Guardar los cambios en la base de datos
        usuarioService.actualizarUsuario(usuarioExistente);

        return "redirect:/personal"; // Redirige a la lista de usuarios
    }

    
    @GetMapping("/personal")
    public String listarPersonal(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.obtenerUsuarioLogueado();

        if (usuarioLogueado == null) {
            throw new IllegalStateException("No se encontró el usuario logueado.");
        }

        List<Usuario> usuarios = null;

        switch (usuarioLogueado.getRole()) {
	        case "ADMIN":
	            usuarios = usuarioService.obtenerTodosLosUsuarios(); // Use el servicio
	            break;
            case "ADMINLOCAL":
                usuarios = usuarioService.obtenerUsuariosPorCuerpoYZona(usuarioLogueado.getCuerpo(), usuarioLogueado.getZona()); // Usar el servicio
                break;
            case "USER": // Rol "USER" u otros
                usuarios = usuarioService.obtenerUsuariosPorCuerpoYZona(usuarioLogueado.getCuerpo(), usuarioLogueado.getZona()); // Usar el servicio
                break;
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioActual", usuarioLogueado); // Para mostrar acciones condicionales en la vista
        return "personal";
    }
    
    @GetMapping("/desactivarUsuario/{id}")
    public String desactivarUsuario(@PathVariable String id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            usuario.setActivo(false); // Cambiar el estado a inactivo
            usuarioService.actualizarUsuario(usuario); // Guardar cambios en la base de datos
            model.addAttribute("success", "Usuario desactivado correctamente.");
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
        }
        return "redirect:/personal"; // Redirigir a la página de lista de usuarios
    }

    // Método para activar usuario
    @GetMapping("/activarUsuario/{id}")
    public String activarUsuario(@PathVariable String id, Model model) {
    	Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            usuario.setActivo(true); // Cambiar el estado a inactivo
            usuarioService.actualizarUsuario(usuario); // Guardar cambios en la base de datos
            model.addAttribute("success", "Usuario desactivado correctamente.");
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
        }
        return "redirect:/personal"; // Redirigir a la página de lista de usuarios
    }
    
    @GetMapping("/verUsuario/{id}")
    public String verUsuario(@PathVariable String id, Model model) {
        Usuario usuario =  usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "detalleUsuario"; // Renderiza la página detalleUsuario.html
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "redirect:/personal"; // Redirige a la página de lista de usuarios si no encuentra el usuario
        }
    }

}