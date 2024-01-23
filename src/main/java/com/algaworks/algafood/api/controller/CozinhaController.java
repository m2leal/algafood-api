package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Cozinha buscar(@PathVariable Long id) {
		return cadastroCozinhaService.buscarOuFalhar(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}
	
	@PutMapping("/{id}")
	public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
		Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual);
			
		return cozinhaSalva;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinhaService.excluir(id);
	}
	
}
