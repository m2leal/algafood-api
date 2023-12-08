create table estado (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	
	primary key (id)
);

insert into estado (nome) select distinct nome_estado from cidade;

alter table public.cidade add column estado_id bigint not null;

update cidade c set c.estado_id = (select e.id from estado e where e.nome = c.nome_estado);

alter table public.cidade add constraint fk_cidade_estado foreign key (estado_id) references estado (id);

alter table public.cidade drop column nome_estado;

alter table public.cidade rename column nome_cidade to nome;

alter table public.cidade alter nome varchar(80) not null;