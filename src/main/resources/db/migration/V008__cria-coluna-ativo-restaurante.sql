alter table restaurante add ativo bit not null;
update restaurante set ativo = true;