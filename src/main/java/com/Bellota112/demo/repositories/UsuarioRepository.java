package com.Bellota112.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Bellota112.demo.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);
    List<Usuario> findByCuerpoAndZona(String cuerpo, String zona);
    Optional<Usuario> findByBellota(int bellota);
    boolean existsByBellota(int bellota);
}