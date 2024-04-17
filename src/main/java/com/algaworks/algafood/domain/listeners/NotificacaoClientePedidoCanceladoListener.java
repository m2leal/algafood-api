package com.algaworks.algafood.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent pedidoCanceladoEvent) {
		Pedido pedido = pedidoCanceladoEvent.getPedido();
		
		var mensagem = Mensagem.builder()
			.assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
			.corpo("pedido-cancelado.html")
			.variavel("pedido", pedido)
			.destinatario(pedido.getCliente().getEmail())
			.build();

		envioEmailService.enviar(mensagem);
	}
	
}
