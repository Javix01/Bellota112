package com.Bellota112.demo.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.Bellota112.demo.domain.Incidencia;
import com.Bellota112.demo.domain.IncidenciaConUsuario;


public interface IncidenciaRepository extends MongoRepository<Incidencia, String> {
	}