alter table consultas add activo tinyint;
update consultas set activo=1;
alter table consultas add motivoCancelamiento varchar(100);