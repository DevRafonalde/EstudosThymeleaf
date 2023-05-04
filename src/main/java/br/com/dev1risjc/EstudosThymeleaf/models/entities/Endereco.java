package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Column(name = "bairro")
    @Getter @Setter
    private String bairro;

    @NotBlank
    @Size(min = 9, max = 9, message = "{Size.endereco.cep}")
    @Column(name = "cep", length = 9)
    @Getter @Setter
    private String cep;

    @NotBlank
    @Column(name = "cidade")
    @Getter @Setter
    private String cidade;

    @Size(max = 255)
    @Column(name = "complemento")
    @Getter @Setter
    private String complemento;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(name = "logradouro")
    @Getter @Setter
    private String logradouro;

    @NotNull(message = "{NotNull.endereco.numero}")
    @Digits(integer = 5, fraction = 0)
    @Column(name = "numero", length = 5)
    @Getter @Setter
    private Integer numero;

    @NotNull(message = "{NotNull.endereco.uf}")
    @Column(name = "uf", length = 2)
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UF uf;
}
