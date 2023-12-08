package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	//Atributos da RFC 7807
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	//Atributos extendidos
	private String userMessage;
	private LocalDateTime timestamp;
	
}
