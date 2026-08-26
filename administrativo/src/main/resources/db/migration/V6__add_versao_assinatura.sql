-- V6__add_versao_assinatura.sql
-- Adiciona coluna de versão para controle de concorrência otimista
ALTER TABLE assinatura
   ADD COLUMN versao BIGINT NOT NULL DEFAULT 0;
