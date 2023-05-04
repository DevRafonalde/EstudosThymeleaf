package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Departamento;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.DepartamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping("/cadastrar")
    //Esse objeto Departamento colocado como parâmetro do método é para que a página receba uma instância dele para conseguir abrir
    public String cadastrar(Departamento departamento) {
        return "departamentos/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("departamentos", (List<Departamento>)buscarTodos());
        return "departamentos/lista";
    }


    @PostMapping("/novoDepartamento")
    public String novoDepartamento(@Valid Departamento Departamento, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "departamentos/cadastro";
        }
        departamentoRepository.save(Departamento);
        attributes.addFlashAttribute("sucesso", "Departamento criado com sucesso.");
        return "redirect:/departamentos/cadastrar";
    }

    @PostMapping("/editar")
    public String editar(Departamento Departamento, RedirectAttributes attributes) {
        departamentoRepository.save(Departamento);
        attributes.addFlashAttribute("sucesso", "Departamento editado com sucesso.");
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/preEditar/{id}")
    public String preEditar(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("departamento", departamentoRepository.findById(id).orElse(null));
        return "departamentos/cadastro";
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarPorId/{id}")
    public Optional<Departamento> buscarPorId(@PathVariable int id) {
        return departamentoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarTodos")
    public Iterable<Departamento> buscarTodos() {
        return departamentoRepository.findAll();
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, ModelMap modelMap) {
        if (departamentoVazio(id)) {
            departamentoRepository.deleteById(id);
            modelMap.addAttribute("sucesso", "Departamento deletado com sucesso.");
        } else {
            modelMap.addAttribute("falha", "Departamento não removido. Possui cargo(s) vinculado(s).");
        }
        return listar(modelMap);
    }

    private boolean departamentoVazio(int id) {
        return buscarPorId(id).orElse(new Departamento()).getCargos().isEmpty();
    }
}
