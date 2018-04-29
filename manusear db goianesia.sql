SELECT * FROM GOIANESIA.USUARIO;
SELECT * FROM GOIANESIA.FUNCIONARIO;
SELECT * FROM GOIANESIA.BDI;

DROP TABLE USUARIO;

USE GOIANESIA;

INSERT INTO USUARIO (matriculaUsuario, nomeUsuario, 
	telefoneUsuario, login, senha) 
	VALUES('02097', 'Silvio Júnior', 
    '(61) 9 8609-3016', 'silvio', '123');
    
SELECT * FROM USUARIO WHERE login='silvio' AND senha=123;

DESCRIBE USUARIO;

SELECT * FROM FUNCIONARIO;

-- Consultas no banco de dados
-- '%A%' qualquer cadeida de caracteres que tenha 'A' no meio
-- 'A%' qualquer cadeida de caracteres que comece com 'A'
-- '%A' qualquer cadeida de caracteres que termine com 'A'
-- A linha abaixo consulta por qualquer cadeia de caracteres que possua j no meio
SELECT * FROM USUARIO WHERE nomeUsuario LIKE '%j%';

UPDATE usuario SET nomeUsuario = 'SILVIO JÚNIOR', login = 'SILVIO' 
	WHERE matriculaUsuario = '02097';
    
UPDATE USUARIO SET matriculaUsuario = '02099' WHERE matriculaUsuario = '02097';
SELECT * FROM USUARIO;
    
    