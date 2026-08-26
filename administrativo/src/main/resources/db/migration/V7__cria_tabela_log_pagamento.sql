-- V7__cria_tabela_log_pagamento.sql
-- Adiciona coluna de versão para controle de concorrência otimista


CREATE TABLE log_pagamento (
   id              SERIAL PRIMARY KEY,
   assinatura_id   INT          NOT NULL,
   motivo          VARCHAR(255) NOT NULL,
   ocorrido_em     TIMESTAMP    NOT NULL,


   CONSTRAINT fk_log_pagamento_assinatura
       FOREIGN KEY (assinatura_id)
       REFERENCES assinatura(id)
);
