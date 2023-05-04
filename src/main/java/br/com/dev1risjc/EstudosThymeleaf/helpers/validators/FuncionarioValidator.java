package br.com.dev1risjc.EstudosThymeleaf.helpers.validators;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class FuncionarioValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Funcionario f = (Funcionario) object;
        if (f.getDataSaida() != null) {
            if (f.getDataSaida().isBefore(f.getDataEntrada())) {
                errors.rejectValue("dataSaida", "PosteriorDataEntrada.funcionario.dataSaida");
            }
        }
    }
}
