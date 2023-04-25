package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/funcionarios")
@Transactional(readOnly = true)
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "funcionarios/cadastro";
    }

    @GetMapping("/listar")
    public String listar() {
        return "funcionarios/lista";
    }

    @PostMapping("/novoFuncionario")
    @Transactional(readOnly = false)
    public @ResponseBody Funcionario novoFuncionario(Funcionario Funcionario) {
        funcionarioRepository.save(Funcionario);
        return Funcionario;
    }

    @PutMapping("/editar")
    @Transactional(readOnly = false)
    public Funcionario editar(Funcionario Funcionario) {
        funcionarioRepository.save(Funcionario);
        return Funcionario;
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarPorId/{id}")
    public Optional<Funcionario> buscarPorId(@PathVariable int id) {
        return funcionarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarTodos")
    public Iterable<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional(readOnly = false)
    public void deletar(@PathVariable int id) {
        funcionarioRepository.deleteById(id);
    }
}
