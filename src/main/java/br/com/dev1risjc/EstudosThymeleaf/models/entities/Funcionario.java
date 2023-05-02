package br.com.dev1risjc.EstudosThymeleaf.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_Funcionarios")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    //https://stackoverflow.com/questions/51268935/set-thvalue-in-thymeleaf-date-input
    @Column(name = "dataEntrada", columnDefinition = "DATE")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    @Getter @Setter
    private LocalDate dataEntrada;

    @Column(name = "dataSaida", columnDefinition = "DATE")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    @Getter @Setter
    private LocalDate dataSaida;

    @Column(name = "nome")
    @Getter @Setter
    private String nome;

    @Column(name = "salario", columnDefinition = "DECIMAL(7,2)")
    @Getter @Setter
    private BigDecimal salario;

    @ManyToOne
    @JoinColumn(name = "cargoIdFk")
    @Getter @Setter
    private Cargo cargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enderecoIdFk")
    @Getter @Setter
    private Endereco endereco;
}
