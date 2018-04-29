CREATE SCHEMA GOIANESIA;

USE GOIANESIA;

CREATE TABLE GOIANESIA.USUARIO(
	matriculaUsuario VARCHAR(10) NOT NULL,
    nomeUsuario VARCHAR(80) NOT NULL,
    telefoneUsuario VARCHAR(16),
    login VARCHAR(15) UNIQUE NOT NULL,
    senha VARCHAR(10) NOT NULL,
    PRIMARY KEY(matriculaUsuario)
);

CREATE TABLE GOIANESIA.FUNCIONARIO(
	matriculaFuncionario VARCHAR(10) NOT NULL,
    nomeFuncionario VARCHAR(80) NOT NULL,
    telefoneFuncionario VARCHAR(16) NOT NULL,
    cargoFuncionario VARCHAR(1) NOT NULL,
    PRIMARY KEY(matriculaFuncionario)
);

CREATE TABLE GOIANESIA.BDI(
	codBDI VARCHAR(8) NOT NULL,
    dataBDI DATE NOT NULL,
    matCobrador VARCHAR(10) NOT NULL,
    matMotorista VARCHAR(10) NOT NULL,
    nomeCobrador VARCHAR(80) NOT NULL,
    nomeMotorista VARCHAR(80) NOT NULL,
    inicialTalaoD1 VARCHAR(8) NOT NULL,
    finalTalaoD1 VARCHAR(8) NOT NULL,
    valorTotalD1 NUMERIC(10,2) NOT NULL,
    inicialTalaoD8 VARCHAR(8) NOT NULL,
    finalTalaoD8 VARCHAR(8) NOT NULL,
    valorTotalD8 NUMERIC(10,2) NOT NULL,
    totalEmFretes NUMERIC(10,2) NOT NULL,
    PRIMARY KEY(codBDI)
);

