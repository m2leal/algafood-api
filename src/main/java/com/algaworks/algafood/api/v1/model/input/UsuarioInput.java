package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@Schema(example = "João da Silva")
	@NotBlank
	private String nome;
	
	@Schema(example = "joao.ger@algafood.com.br")
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String senha;
	
}
