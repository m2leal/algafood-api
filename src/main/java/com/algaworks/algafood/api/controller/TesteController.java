package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping(value = "/testes")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/produtos")
	public List<Produto> produtos() {
		List<Produto> produtos = new ArrayList<>();
		
		restauranteRepository.findAll().forEach((restaurante) -> restaurante.getProdutos().forEach((produto) -> produtos.add(produto)));
		
		return produtos;
	}
	
	@GetMapping("/cozinhas-por-nome-parcial")
	public List<Cozinha> cozinhasPorNomeParcial(String nome) {
		return cozinhaRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas-por-nome")
	public Optional<Cozinha> cozinhasPorNome(String nome) {
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/cozinha-existe")
	public boolean cozinhaExiste(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/restaurantes-por-frete-entre")
	public List<Restaurante> restaurantesPorFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes-por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeEFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes-por-nome-parcial-e-cozinha-id")
	public List<Restaurante> restaurantePorNomeParcialEIdCozinha(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurante-primeiro-contendo")
	public Optional<Restaurante> restaurantePrimeiroComNomeContendo(String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes-top2-contentendo")
	public List<Restaurante> restauranteTop2ComNomeContendo(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes-conta-por-id-cozinha")
	public int restaurantesContaPorIdCozinha(Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}
	
	@GetMapping("/restaurantes-com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("/restaurantes-buscar-primeiro")
	public Optional<Restaurante> restaurantesBuscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas-buscar-primeira")
	public Optional<Cozinha> cozinhasBuscarPrimeiro() {
		return cozinhaRepository.buscarPrimeiro();
	}

}
