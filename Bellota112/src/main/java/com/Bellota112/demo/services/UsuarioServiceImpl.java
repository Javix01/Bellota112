package com.Bellota112.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Bellota112.demo.domain.Usuario;
import com.Bellota112.demo.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        // Encriptar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> autenticarUsuario(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && passwordEncoder.matches(password, usuario.get().getPassword())) {
            return usuario;
        }
        return Optional.empty();
    }

    @Override
    public boolean existeUsuarioConEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> getUsuarioById(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario updateUsuario(String id, Usuario updatedUsuario) {
        return usuarioRepository.findById(id).map(usuario -> {
            // Actualizamos los campos del usuario solo si hay un cambio
            if (updatedUsuario.getEmail() != null && !updatedUsuario.getEmail().equals(usuario.getEmail())) {
                usuario.setEmail(updatedUsuario.getEmail());
            }
            if (updatedUsuario.getNombre() != null && !updatedUsuario.getNombre().equals(usuario.getNombre())) {
                usuario.setNombre(updatedUsuario.getNombre());
            }
            if (updatedUsuario.getPassword() != null && !updatedUsuario.getPassword().equals(usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(updatedUsuario.getPassword())); // Encriptamos la nueva contraseña
            }
            if (updatedUsuario.getRole() != null && !updatedUsuario.getRole().equals(usuario.getRole())) {
                usuario.setRole(updatedUsuario.getRole());
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void deleteUsuario(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public Optional<Usuario> findUsuarioById(String usuarioId) {
        // Retornamos el usuario encontrado por su id
        return usuarioRepository.findById(usuarioId);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        // Aquí no es necesario verificar si el usuario existe ya que si no existe lo creará automáticamente
        usuarioRepository.save(usuario);
    }

    public void deleteUsuarioById(String usuarioId) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(usuarioId);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @Override
    public Usuario obtenerUsuarioLogueado() {
        // Obtener el usuario logueado a través de Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();  // El nombre del usuario suele ser el email
        return usuarioRepository.findByEmail(email).orElse(null);
    }
    
    @Override
    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id).orElse(null);  // MongoDB utiliza IDs de tipo String
    }
    
    @Override
    public Usuario obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> obtenerUsuariosPorCuerpoYZona(String cuerpo, String zona) {
        return usuarioRepository.findByCuerpoAndZona(cuerpo, zona);
    }
    
    public Optional<Usuario> buscarPorBellota(int bellota) {
        return usuarioRepository.findByBellota(bellota);
    }
}