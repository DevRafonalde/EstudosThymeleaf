package br.com.dev1risjc.EstudosThymeleaf.models.repositories;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
    @Query("Select x from Funcionario x where x.nome like %:nome%")
    List<Funcionario> findByNome(String nome);

    @Query("Select x from Funcionario x where x.cargo.id = :cargoId")
    List<Funcionario> findByCargoId(int cargoId);

    @Query("Select x from Funcionario x where x.dataEntrada >= :dataEntrada order by x.dataEntrada asc")
    List<Funcionario> findByDataEntrada(LocalDate dataEntrada);

    @Query("Select x from Funcionario x where x.dataSaida <= :dataSaida order by x.dataEntrada asc")
    List<Funcionario> findByDataSaida(LocalDate dataSaida);

    @Query("Select x from Funcionario x where x.dataEntrada >= :dataEntrada and x.dataSaida <= :dataSaida order by x.dataEntrada asc")
    List<Funcionario> findByDataEntradaDataSaida(LocalDate dataEntrada, LocalDate dataSaida);
}
