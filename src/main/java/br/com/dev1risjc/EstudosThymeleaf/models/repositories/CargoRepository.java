package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {
	
}
