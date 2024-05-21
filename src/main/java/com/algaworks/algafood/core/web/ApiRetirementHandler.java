package com.algaworks.algafood.core.web;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiRetirementHandler implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		LocalDate dataExpiracao = LocalDate.of(2024, 6, 20);
		
		if (request.getRequestURI().startsWith("/v1/")) {
			if(LocalDate.now().compareTo(dataExpiracao) >= 0) {
				response.setStatus(HttpStatus.GONE.value());
				return false;
			}
			
			response.addHeader("X-AlgaFood-Deprecated",
				"Essa versão da API está depreciada e deixará de existir a partir de 20/05/2024."
						+ "Use a versão mais atual da API.");
		}
		
		return true;
	}
	
}
