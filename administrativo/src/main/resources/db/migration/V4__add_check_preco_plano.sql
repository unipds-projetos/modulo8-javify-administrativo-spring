-- V4__add_check_preco_plano.sql
-- Adiciona constraint de validacao de preco negativo — esquecida no V1


ALTER TABLE plano
   ADD CONSTRAINT chk_plano_preco_nao_negativo
       CHECK (preco >= 0);
