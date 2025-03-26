package com.Bellota112.demo.commandLineRunner;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Bellota112.demo.domain.Incidencia;
import com.Bellota112.demo.domain.Usuario;
import com.Bellota112.demo.repositories.IncidenciaRepository;
import com.Bellota112.demo.repositories.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final IncidenciaRepository incidenciaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MyCommandLineRunner(UsuarioRepository usuarioRepository,
                                IncidenciaRepository incidenciaRepository,
                                BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.incidenciaRepository = incidenciaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
    	/*Incidencia incidencia = new Incidencia();
    	incidencia.setBellota(0);
    	incidencia.setLocalizacion("0.00,0.00");
    	incidencia.setIncidencia(2);
    	incidencia.setFechaCreacion(LocalDateTime.now());
        incidencia.setActivo(true);

        incidenciaRepository.save(incidencia);
        
        Incidencia incidencia1 = new Incidencia();
        incidencia1.setBellota(5);
        incidencia1.setLocalizacion("0.00,0.00");
        incidencia1.setIncidencia(1);
        incidencia1.setFechaCreacion(LocalDateTime.now());
        incidencia1.setActivo(false);

        incidenciaRepository.save(incidencia1);
        
        Incidencia incidencia2 = new Incidencia();
        incidencia2.setBellota(20);
        incidencia2.setLocalizacion("0.00,0.00");
        incidencia2.setIncidencia(1);
        incidencia2.setFechaCreacion(LocalDateTime.now());
        incidencia2.setActivo(true);

        incidenciaRepository.save(incidencia2);*/
        System.out.println("Usuario guardado en MongoDB: " + usuarioRepository.findAll());
        System.out.println("Incidencia guardada en MongoDB: " + incidenciaRepository.findAll());
    }
    /*public void run(String... args) throws Exception {
        System.out.println("\t MyCommandLineRunner is running! ");
        poblarBD();
    }*/

    /*void poblarBD() {
        // Crear el primer usuario con contraseña encriptada
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Luiky");
        usuario1.setEmail("prueba@prueba.es");
        usuario1.setPassword(passwordEncoder.encode("prueba")); // Contraseña encriptada
        usuario1.setRole("ADMIN");
        usuario1.setTelefono("910000001");
        usuario1.setActivo(true);
        usuario1.setBellota(0);
        usuario1.setZona("Extremadura");
        usuario1.setCuerpo("112");
        usuario1.setRango("Rango 1");
        usuario1.setIdentificacion("Ex-001");
        // Guardar el usuario
        Usuario usuario1Saved = usuarioRepository.save(usuario1);

        // ---- Crear el segundo usuario y sus datos
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Pedro");
        usuario2.setEmail("pedro@unex.es");
        usuario2.setPassword(passwordEncoder.encode("securePassword")); // Contraseña encriptada
        usuario2.setRole("USER");
        usuario2.setTelefono("910000001");
        usuario2.setActivo(true);
        usuario2.setBellota(0);
        usuario2.setZona("Extremadura");
        usuario2.setCuerpo("112");
        usuario2.setRango("Rango 1");
        usuario2.setIdentificacion("Ex-002");
        Usuario usuario2Saved = usuarioRepository.save(usuario2);
        
     // Crear el primer usuario con contraseña encriptada
        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Javier García Sánchez");
        usuario3.setEmail("javier@javier.es");
        usuario3.setPassword(passwordEncoder.encode("javier")); // Contraseña encriptada
        usuario3.setRole("USER");
        usuario3.setTelefono("910000003");
        usuario3.setIdentificacion("ID-003");
        usuario3.setZona("Zona Este");
        usuario3.setCuerpo("Cuerpo Técnico");
        usuario3.setRango("Rango 3");
        usuario3.setBellota(10);
        usuario3.setActivo(false);
        // Guardar el usuario
        Usuario usuario3Saved = usuarioRepository.save(usuario3);

        System.out.println("Datos de prueba para usuarios, establecimientos y reservas creados exitosamente.");
    }*/
}