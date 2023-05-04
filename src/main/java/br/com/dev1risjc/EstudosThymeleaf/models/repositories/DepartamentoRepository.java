package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Departamento;
import org.springframework.data.repository.CrudRepository;

public interface DepartamentoRepository extends CrudRepository<Departamento, Integer> {
}
