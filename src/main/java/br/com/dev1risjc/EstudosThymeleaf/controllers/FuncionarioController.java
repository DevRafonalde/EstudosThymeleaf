package br.com.dev1risjc.EstudosThymeleaf.controllers;

import br.com.dev1risjc.EstudosThymeleaf.helpers.validators.FuncionarioValidator;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Cargo;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.Funcionario;
import br.com.dev1risjc.EstudosThymeleaf.models.entities.UF;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.CargoRepository;
import br.com.dev1risjc.EstudosThymeleaf.models.repositories.FuncionarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
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

//    Adiciona o meu validador personalizado
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new FuncionarioValidator());
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Funcionario funcionario) {
        return "funcionarios/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", (List<Funcionario>)funcionarioRepository.findAll());
        return "funcionarios/lista";
    }

    @PostMapping("/novoFuncionario")
    @Transactional(readOnly = false)
    public String novoFuncionario(@Valid Funcionario Funcionario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "funcionarios/cadastro";
        }
        funcionarioRepository.save(Funcionario);
        attributes.addFlashAttribute("sucesso", "Funcionário inserido com sucesso");
        return "redirect:/funcionarios/cadastrar";
    }

    @PostMapping("/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "funcionarios/cadastro";
        }
        funcionarioRepository.save(funcionario);
        attributes.addFlashAttribute("sucesso", "Funcionario editado com sucesso.");
        return "redirect:/funcionarios/cadastrar";
    }

    @GetMapping("/preEditar/{id}")
    public String preEditar(@PathVariable Long id, ModelMap modelMap) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
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
        return "funcionarios/lista";
    }

    @GetMapping("/buscarPorCargoId")
    public String buscarPorCargo(@RequestParam("cargoId") int cargoId, ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", funcionarioRepository.findByCargoId(cargoId));
        return "funcionarios/lista";
    }

    @GetMapping("/buscarPorDatas")
    public String buscarPorDatas(@RequestParam(value = "dataEntrada", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrada,
                                 @RequestParam(value = "dataSaida", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataSaida,
                                 ModelMap modelMap) {
        modelMap.addAttribute("funcionarios", buscarPorDatas(dataEntrada, dataSaida));
        return "funcionarios/lista";
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
            return funcionarioRepository.findByDataEntradaDataSaida(dataEntrada, dataSaida);
        } else if (dataEntrada != null) {
            return funcionarioRepository.findByDataEntrada(dataEntrada);
        } else if (dataSaida != null) {
            return funcionarioRepository.findByDataSaida(dataSaida);
        } else {
            return new ArrayList<>();
        }
    }
}
