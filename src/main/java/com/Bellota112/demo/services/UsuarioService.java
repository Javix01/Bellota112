package com.Bellota112.demo.services;

import java.util.List;
import java.util.Optional;

import com.Bellota112.demo.domain.Usuario;

public interface UsuarioService {

	// Método para registrar un nuevo usuario
    Usuario registrarUsuario(Usuario usuario);

    // Método para autenticar un usuario
    Optional<Usuario> autenticarUsuario(String email, String password);

    // Método para verificar si un email ya está registrado
    boolean existeUsuarioConEmail(String email);

	Optional<Usuario> findUsuarioById(String usuarioId);
	
	public Optional<Usuario> getUsuarioById(String id);
	
	public Usuario updateUsuario(String id, Usuario updatedUsuario);
	
	public void deleteUsuario(String id);
	
	public void actualizarUsuario(Usuario usuario);

	public void deleteUsuarioById(String id);
	
	public Usuario obtenerUsuarioLogueado();
	
	public Usuario obtenerUsuarioPorId(String id);
	
    //void registrarUsuario(Usuario usuario);
	public Usuario obtenerUsuarioPorNombre(String nombre);
    List<Usuario> obtenerTodosLosUsuarios();
    List<Usuario> obtenerUsuariosPorCuerpoYZona(String cuerpo, String zona);
    
    public Optional<Usuario> buscarPorBellota(int bellota);

	boolean existeUsuarioConBellota(int bellota);

	Optional<Usuario> obtenerUsuarioPorEmail(String email);

}