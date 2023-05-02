package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.UF;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.CargoRepository;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/funcionarios")
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

    @PostMapping("/editar")
    public String editar(Funcionario funcionario, RedirectAttributes attributes) {
        if (funcionario != null) {
            System.out.println(funcionario.getId());
            System.out.println(funcionario.getNome());
            System.out.println(funcionario.getSalario());
            funcionarioRepository.save(funcionario);
        }
        attributes.addFlashAttribute("sucesso", "Funcionario editado com sucesso.");
        return "redirect:/funcionarios/cadastrar";
    }

    @GetMapping("/preEditar/{id}")
    public String preEditar(@PathVariable Long id, ModelMap modelMap) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario != null) {
            System.out.println(funcionario.getId());
            System.out.println(funcionario.getNome());
            System.out.println(funcionario.getSalario());
        }
        modelMap.addAttribute("funcionario", funcionario);
        return "funcionarios/cadastro";
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarPorId/{id}")
    public Optional<Funcionario> buscarPorId(@PathVariable Long id) {
        return funcionarioRepository.findById(id);
    }

    @GetMapping("/buscarPorNome")
    public String buscarPorNome(@RequestParam("nome") String nome, ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", funcionarioRepository.findByNome(nome));
        return "/funcionarios/lista";
    }

    @GetMapping("/buscarPorCargoId")
    public String buscarPorCargo(@RequestParam("cargoId") int cargoId, ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", funcionarioRepository.findByCargoId(cargoId));
        return "/funcionarios/lista";
    }

    @GetMapping("/buscarPorDatas")
    public String buscarPorDatas(@RequestParam(value = "dataEntrada", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrada,
                                 @RequestParam(value = "dataSaida", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataSaida,
                                 ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", buscarPorDatas(dataEntrada, dataSaida));
        return "/funcionarios/lista";
    }

    @Transactional(readOnly = true)
    @GetMapping("/buscarTodos")
    public Iterable<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, ModelMap modelMap) {
        funcionarioRepository.deleteById(id);
        modelMap.addAttribute("sucesso", "Funcionario deletado com sucesso.");
        return listar(modelMap);
    }

    @ModelAttribute("cargos")
    public List<Cargo> getCargos() {
        return (List<Cargo>) cargoRepository.findAll();
    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }

    public List<Funcionario> buscarPorDatas(LocalDate dataEntrada, LocalDate dataSaida) {
        if (dataEntrada != null && dataSaida != null) {
            System.out.println(dataEntrada + " " + dataSaida);
            return funcionarioRepository.findByDataEntradaDataSaida(dataEntrada, dataSaida);
        } else if (dataEntrada != null) {
            System.out.println(dataEntrada);
            return funcionarioRepository.findByDataEntrada(dataEntrada);
        } else if (dataSaida != null) {
            System.out.println(dataSaida);
            return funcionarioRepository.findByDataSaida(dataSaida);
        } else {
            System.out.println("teste");
            return new ArrayList<>();
        }
    }
}
