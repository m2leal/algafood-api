package com.algaworks.algafood.core.springdoc;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Parameter(
		in = ParameterIn.QUERY,
		name = "page",
		description = "Número da página (0..N)",
		schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
		in = ParameterIn.QUERY,
		name = "size",
		description = "Quantidade de elementos por página",
		schema = @Schema(type = "integer", defaultValue = "10")
)
@Parameter(
		in = ParameterIn.QUERY,
		name = "sort",
		description = "Critério de ordenação: propriedade(asc|desc)",
		examples = {
				@ExampleObject("nome"),
				@ExampleObject("nome,asc"),
				@ExampleObject("nome,desc")
		}
)
public @interface PageableParameter {

}
