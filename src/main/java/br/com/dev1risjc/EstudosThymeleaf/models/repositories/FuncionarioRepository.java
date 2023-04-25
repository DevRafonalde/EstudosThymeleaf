package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
}
