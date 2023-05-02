package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O nome do cargo é obrigatório.")
    @Size(max = 60, message = "O nome do cargo deve conter no máximo 60 caracteres")
    @Column(name = "nome")
    @Getter @Setter
    private String nome;

    @NotNull(message = "Selecione o departamento relativo ao cargo.")
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "idDepartamentoFk")
    private Departamento departamento;

    @OneToMany(mappedBy = "cargo")
    @Getter @Setter
    private List<Funcionario> funcionarios;
}
