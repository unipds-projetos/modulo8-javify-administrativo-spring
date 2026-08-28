CREATE TABLE auditoria_assinatura (
   id              BIGSERIAL       PRIMARY KEY,
   assinatura_id   INTEGER         NOT NULL,
   plano_anterior  VARCHAR(50),
   plano_novo      VARCHAR(50),
   preco_anterior  NUMERIC(10,2),
   preco_novo      NUMERIC(10,2),
   status_anterior BOOLEAN,
   status_novo     BOOLEAN,
   alterado_em     TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
   origem          TEXT            NOT NULL DEFAULT current_user
);


-- Função de trigger: retorno TRIGGER, sem parâmetros
CREATE OR REPLACE FUNCTION fn_auditar_assinatura()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
   -- Só audita se houve mudança real (evita registro de no-op)
   IF OLD.plano_id IS DISTINCT FROM NEW.plano_id
   OR OLD.status_ativa IS DISTINCT FROM NEW.status_ativa THEN


       INSERT INTO auditoria_assinatura (
           assinatura_id,
           plano_anterior, plano_novo,
           preco_anterior, preco_novo,
           status_anterior, status_novo
       )
       SELECT
           NEW.id,
           p_old.nome,    p_new.nome,
           p_old.preco,   p_new.preco,
           OLD.status_ativa, NEW.status_ativa
       FROM plano p_old, plano p_new
       WHERE p_old.id = OLD.plano_id
         AND p_new.id = NEW.plano_id;


   END IF;
   RETURN NEW;
END;
$$;


-- Criar o trigger na tabela assinatura
CREATE TRIGGER trg_auditar_assinatura
   AFTER UPDATE ON assinatura
   FOR EACH ROW
   EXECUTE FUNCTION fn_auditar_assinatura();
