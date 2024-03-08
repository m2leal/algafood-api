alter table restaurante add aberto bit not null;
update restaurante set aberto = false;