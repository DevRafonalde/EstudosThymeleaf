package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "cargos/cadastro";
    }

    @GetMapping("/listar")
    public String listar() {
        return "cargos/lista";
    }

    @PostMapping("/novoCargo")
    public @ResponseBody Cargo novoCargo(Cargo cargo) {
        cargoRepository.save(cargo);
        return cargo;
    }

    @PutMapping("/editar")
    public Cargo editar(Cargo cargo) {
        cargoRepository.save(cargo);
        return cargo;
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarPorId/{id}")
    public Optional<Cargo> buscarPorId(@PathVariable int id) {
        return cargoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarTodos")
    public Iterable<Cargo> buscarTodos() {
        return cargoRepository.findAll();
    }

    @DeleteMapping("/deletar/{id}")
    public void deletar(@PathVariable int id) {
        cargoRepository.deleteById(id);
    }

}
