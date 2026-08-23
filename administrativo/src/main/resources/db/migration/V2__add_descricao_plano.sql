-- V2__add_descricao_plano.sql
-- Adiciona campo de descricao de marketing ao plano


ALTER TABLE plano
   ADD COLUMN descricao VARCHAR(500);
