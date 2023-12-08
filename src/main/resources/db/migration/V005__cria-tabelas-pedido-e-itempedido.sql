create table pedido (
	id bigint not null auto_increment,
	subtotal decimal not null,
	taxa_frete decimal not null,
	valor_total decimal not null,
	data_criacao datetime not null,
	data_corfirmacao datetime null,
	data_cancelamento datetime null,
	data_entrega datetime null,
	status varchar(10) not null,
	
	endereco_cidade_id bigint not null,
	endereco_cep varchar(9) not null,
	endereco_logradouro varchar(100) not null,
	endereco_numero varchar(20) not null,
	endereco_complemento varchar(60) null,
	endereco_bairro varchar(60) not null,
	
	restaurante_id bigint not null,
	usuario_cliente_id bigint not null,
	forma_pagamento_id bigint not null,
	
	primary key (id),
	constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id),
	constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
	constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)
);

create table item_pedido (
	id bigint not null auto_increment,
	quantidade smallint not null,
	preco_unitario decimal not null,
	preco_total decimal not null,
	observacao varchar(255) null,
	pedido_id bigint not null,
	produto_id bigint not null,
  
	primary key (id),
	unique (pedido_id, produto_id),
	
	constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id),
	constraint fk_item_pedido_produto foreign key (produto_id) references produto (id)
);