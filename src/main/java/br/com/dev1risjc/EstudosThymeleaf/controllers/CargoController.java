package br.com.dev1risjc.EstudosThymeleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "cargos/cadastro";
    }

    @GetMapping("/listar")
    public String listar() {
        return "cargos/lista";
    }
}
