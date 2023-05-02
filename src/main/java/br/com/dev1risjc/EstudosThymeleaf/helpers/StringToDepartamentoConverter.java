package br.com.dev1risjc.EstudosThymeleaf.helpers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Departamento;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDepartamentoConverter implements Converter<String, Departamento> {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    public Departamento convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return departamentoRepository.findById(id).orElse(null);
    }
}
