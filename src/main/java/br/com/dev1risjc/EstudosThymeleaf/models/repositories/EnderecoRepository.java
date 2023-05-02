package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, Integer> {
}
