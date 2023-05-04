package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import org.springframework.data.repository.CrudRepository;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {
}
