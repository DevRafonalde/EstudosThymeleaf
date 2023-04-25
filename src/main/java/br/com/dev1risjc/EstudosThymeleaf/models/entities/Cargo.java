package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "tbl_Cargos")
@NoArgsConstructor
@EqualsAndHashCode
//@ToString
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "nome")
    @Getter @Setter
    private String nome;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "idDepartamentoFk")
    private Departamento departamento;

    @OneToMany(mappedBy = "cargo")
    @Getter @Setter
    private List<Funcionario> funcionarios;
}
