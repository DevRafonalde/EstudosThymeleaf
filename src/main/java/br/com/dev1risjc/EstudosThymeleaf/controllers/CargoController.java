package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.helpers.paginacao.Paginacao;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Departamento;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.CargoRepository;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.DepartamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
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
    public String listar(ModelMap modelMap, @RequestParam("pagina") Optional<Integer> pagina, @RequestParam("direcao") Optional<String> direcao) {
        int paginaAtual = pagina.orElse(1);
        String ordem = direcao.orElse("normal");
        Paginacao<Cargo> paginaCargo = buscaPaginada(paginaAtual, ordem);

        modelMap.addAttribute("paginaCargo", paginaCargo);
        return "cargos/lista";
    }

    @PostMapping("/novoCargo")
    public String novoCargo(@Valid Cargo cargo, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "cargos/cadastro";
        }
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

    public Paginacao<Cargo> buscaPaginada(int pagina, String direcao) {
        int tamanho = 5;
        int inicio = (pagina - 1) * tamanho;
        List<Cargo> cargos = (List<Cargo>) cargoRepository.findAll();
        if (direcao.equalsIgnoreCase("normal")) {
            cargos.sort(Comparator.comparing(Cargo::getNome));
        } else {
            cargos.sort(Collections.reverseOrder(Comparator.comparing(Cargo::getNome)));
        }

//        System.out.println("Início: " + inicio);
//        for (Cargo c: cargos) {
//            System.out.println(c.getNome());
//            System.out.println(cargos.indexOf(c));
//            System.out.println(cargos.indexOf(c) >= inicio);
//        }
        List<Cargo> paginaCargo = cargos.stream().filter(cargo -> cargos.indexOf(cargo) >= inicio).limit(tamanho).toList();

        int totalPaginas = (cargos.size() + (tamanho - 1)) / tamanho;

        return new Paginacao<>(tamanho, pagina, totalPaginas, direcao, paginaCargo);
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, ModelMap modelMap) {
        if (cargoVazio(id)) {
            cargoRepository.deleteById(id);
            modelMap.addAttribute("sucesso", "Cargo deletado com sucesso.");
        } else {
            modelMap.addAttribute("falha", "Cargo não removido. Possui funcionário(s) vinculado(s).");
        }
        return "cargos/lista";
    }

    private boolean cargoVazio(int id) {
        return buscarPorId(id).orElse(new Cargo()).getFuncionarios().isEmpty();
    }

}
