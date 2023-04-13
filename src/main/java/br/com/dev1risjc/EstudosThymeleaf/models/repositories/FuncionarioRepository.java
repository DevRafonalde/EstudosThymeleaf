package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

}
