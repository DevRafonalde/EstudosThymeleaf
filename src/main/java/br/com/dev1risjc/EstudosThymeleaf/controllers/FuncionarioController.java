package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.UF;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.CargoRepository;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/funcionarios")
@Transactional(readOnly = true)
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/cadastrar")
    public String cadastrar(Funcionario funcionario) {
        return "funcionarios/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", (List<Funcionario>)funcionarioRepository.findAll());
        return "/funcionarios/lista";
    }

    @PostMapping("/novoFuncionario")
    @Transactional(readOnly = false)
    public String novoFuncionario(Funcionario Funcionario, RedirectAttributes attributes) {
        funcionarioRepository.save(Funcionario);
        attributes.addFlashAttribute("sucesso", "Funcionário inserido com sucesso");
        return "redirect:/funcionarios/cadastrar";
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

    @ModelAttribute("cargos")
    public List<Cargo> getCargos() {
        return (List<Cargo>) cargoRepository.findAll();
    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }
}
