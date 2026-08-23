-- V1__schema_inicial.sql
-- Schema completo do Javify


CREATE TABLE plano (
   id                SERIAL PRIMARY KEY,
   nome              VARCHAR(50)     NOT NULL,
   preco             DECIMAL(10,2)   NOT NULL,
   possui_propagandas BOOLEAN        NOT NULL DEFAULT false,
   limite_membros    INT             NOT NULL DEFAULT 1,
   modo_offline      BOOLEAN         NOT NULL DEFAULT FALSE
);


CREATE TABLE endereco (
   codigo_postal     VARCHAR(8)         PRIMARY KEY,
   logradouro        VARCHAR(150),
   bairro            VARCHAR(100)
);


CREATE TABLE assinatura (
   id                SERIAL          PRIMARY KEY,
   plano_id          INT             NOT NULL,
   status_ativa      BOOLEAN         NOT NULL DEFAULT TRUE
);


CREATE TABLE usuario (
   id                BIGSERIAL       PRIMARY KEY,
   nome              VARCHAR(100)    NOT NULL,
   email             VARCHAR(150)    NOT NULL UNIQUE,
   senha_hash        VARCHAR(60)     NOT NULL,
   titular           BOOLEAN         NOT NULL DEFAULT TRUE,
   data_nascimento   DATE,
   cep               VARCHAR(8),
   assinatura_id     INT
);


CREATE TABLE usuario_telefone (
   id                SERIAL          PRIMARY KEY,
   usuario_id        BIGINT          NOT NULL,
   numero            VARCHAR(20),
   tipo              VARCHAR(20) CHECK (tipo IN ('CELULAR','COMERCIAL','FIXO'))
);


CREATE TABLE cartao_credito (
   id                      SERIAL      PRIMARY KEY,
   assinatura_id           INT     NOT NULL,
   nome_titular            VARCHAR(100),
   ultimos_quatro_digitos  VARCHAR(4),
   token_gateway           VARCHAR(255),
   validade                DATE
);


-- FKs declaradas após todas as tabelas — sem problema de ordem
ALTER TABLE assinatura
   ADD CONSTRAINT fk_assinatura_plano
       FOREIGN KEY (plano_id) REFERENCES plano(id);


ALTER TABLE usuario
   ADD CONSTRAINT fk_usuario_endereco
       FOREIGN KEY (cep) REFERENCES endereco(codigo_postal),
   ADD CONSTRAINT fk_usuario_assinatura
       FOREIGN KEY (assinatura_id) REFERENCES assinatura(id);


ALTER TABLE usuario_telefone
   ADD CONSTRAINT fk_telefone_usuario
       FOREIGN KEY (usuario_id) REFERENCES usuario(id);


ALTER TABLE cartao_credito
   ADD CONSTRAINT fk_cartao_assinatura
       FOREIGN KEY (assinatura_id) REFERENCES assinatura(id);
