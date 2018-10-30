create database db_mcmv;

use db_mcmv;


create user user_mcmv identified by 'admin';

grant all on db_mcmv.* to user_mcmv;


create table principal (
id int(5) unsigned zerofill auto_increment primary key,
nome varchar(50) not null,
sexo varchar(1) not null,
est_civil varchar(15) not null,
dt_nasc date,
cpf varchar(14) unique,
rg varchar(10),
endereco varchar(100),
bairro varchar(30),
zona int(1),
telefone varchar(14),
email varchar(40),
naturalidade varchar(40),
tempo int(3),
ocupacao varchar(30),
remuneracao double,
outras_rendas double,
cadunico boolean,
nis varchar(14),
bolsa_familia boolean,
bpc boolean,
escolaridade varchar(22),
imovel varchar(15),
comodos int(2),
aluguel double,
risco boolean,
deficiencia varchar(25),
observ text
) engine = innodb;


create table dependentes (
fk_id int(5) unsigned zerofill,
dependente int(2),
nome varchar(50),
idade int(3),
parentesco varchar(20),
documentacao varchar(15),
ocupacao varchar(30),
remuneracao double,
outras_rendas double,
foreign key (fk_id) references principal(id),
primary key (fk_id, dependente)
) engine = innodb;