package com.algaworks.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if(dataUltimaAtualizacao != null)
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		
		if(request.checkNotModified(eTag))
			return null;
		
		List<FormaPagamento> todasFormasPagamento = formaPagamentoRepository.findAll();
		
		CollectionModel<FormaPagamentoModel> formasPagamentosModel = 
				formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentosModel);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(id);
		
		if(dataUltimaAtualizacao != null)
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		
		if(request.checkNotModified(eTag))
			return null;
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);
		
		FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		formaPagamento = cadastroFormaPagamentoService.salvar(formaPagamento);
		
		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(id);
		
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
			
		formaPagamentoAtual = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);
			
		return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroFormaPagamentoService.excluir(id);
	}
	
}
