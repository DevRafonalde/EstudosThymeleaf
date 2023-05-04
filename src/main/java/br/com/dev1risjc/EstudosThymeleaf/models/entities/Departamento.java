package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_Departamentos")
@NoArgsConstructor
@EqualsAndHashCode
//@ToString
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private int id;

    @Getter @Setter
    @OneToMany(mappedBy = "departamento")
    private List<Cargo> cargos;

    @NotBlank(message = "Informe um nome.")
    @Size(min = 3, max = 60, message = "O nome do departamento deve ter entre {min} e {max} caracteres.")
    @Column(name = "nome")
    @Getter @Setter
    private String nome;

}
