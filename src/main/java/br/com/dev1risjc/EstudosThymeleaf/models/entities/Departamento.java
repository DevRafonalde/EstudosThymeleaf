package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
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

    @Column(name = "nome")
    @Getter @Setter
    private String nome;

}
