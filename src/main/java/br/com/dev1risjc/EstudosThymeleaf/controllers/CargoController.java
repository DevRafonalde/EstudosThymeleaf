package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Departamento;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.CargoRepository;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping("/cadastrar")
    public String cadastrar(Cargo cargo) {
        return "cargos/cadastro";
    }

    @ModelAttribute("departamentos")
    public List<Departamento> listaDepartamentos() {
        return (List<Departamento>) departamentoRepository.findAll();
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelMap) {
        modelMap.addAttribute("cargos", (List<Cargo>) cargoRepository.findAll());
        return "cargos/lista";
    }

    @PostMapping("/novoCargo")
    public String novoCargo(Cargo cargo, RedirectAttributes attributes) {
        cargoRepository.save(cargo);
        attributes.addFlashAttribute("sucesso", "Cargo inserido com sucesso.");
        return "redirect:/cargos/cadastrar";
    }

    @PostMapping("/editar")
    public String editar(Cargo cargo, RedirectAttributes attributes) {
        cargoRepository.save(cargo);
        attributes.addFlashAttribute("sucesso", "Cargo editado com sucesso.");
        return "redirect:/cargos/listar";
    }

    @GetMapping("/preEditar/{id}")
    public String preEditar(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("cargo", cargoRepository.findById(id).orElse(null));
        return "cargos/cadastro";
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

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, ModelMap modelMap) {
        if (cargoVazio(id)) {
            cargoRepository.deleteById(id);
            modelMap.addAttribute("sucesso", "Cargo deletado com sucesso.");
        } else {
            modelMap.addAttribute("falha", "Cargo não removido. Possui funcionário(s) vinculado(s).");
        }
        return listar(modelMap);
    }

    private boolean cargoVazio(int id) {
        return buscarPorId(id).orElse(new Cargo()).getFuncionarios().isEmpty();
    }

}
