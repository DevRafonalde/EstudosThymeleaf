package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_Enderecos")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private int id;

    @Column(name = "bairro")
    @Getter @Setter
    private String bairro;

    @Column(name = "cep", length = 9)
    @Getter @Setter
    private String cep;

    @Column(name = "cidade")
    @Getter @Setter
    private String cidade;

    @Column(name = "complemento")
    @Getter @Setter
    private String complemento;

    @Column(name = "logradouro")
    @Getter @Setter
    private String logradouro;

    @Column(name = "numero", length = 5)
    @Getter @Setter
    private int numero;

    @Column(name = "uf", length = 2)
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UF uf;
}
