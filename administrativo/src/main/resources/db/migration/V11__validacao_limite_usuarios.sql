CREATE OR REPLACE FUNCTION fn_validar_limite_membros()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
   v_limite    INTEGER;
   v_atual     INTEGER;
BEGIN
   -- Buscar limite do plano da assinatura destino
   SELECT p.limite_membros INTO v_limite
     FROM assinatura a
     JOIN plano p ON p.id = a.plano_id
    WHERE a.id = NEW.assinatura_id;


   -- Contar membros atuais
   SELECT COUNT(*) INTO v_atual
     FROM usuario
    WHERE assinatura_id = NEW.assinatura_id;


   IF v_atual >= v_limite THEN
       RAISE EXCEPTION
           'Limite de % membros atingido para esta assinatura',
           v_limite
           USING ERRCODE = 'P0002';
   END IF;


   RETURN NEW;  -- permite a operacao continuar
END;
$$;


CREATE TRIGGER trg_validar_limite_membros
   BEFORE INSERT ON usuario
   FOR EACH ROW
   WHEN (NEW.assinatura_id IS NOT NULL AND NEW.titular = FALSE)
   EXECUTE FUNCTION fn_validar_limite_membros();
