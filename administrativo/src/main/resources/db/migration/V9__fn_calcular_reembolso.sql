-- V9__fn_calcular_reembolso.sql
CREATE OR REPLACE FUNCTION calcular_reembolso(p_assinatura_id INTEGER)
RETURNS NUMERIC(10,2)
LANGUAGE plpgsql
AS $$
DECLARE
   v_preco             NUMERIC(10,2);
   v_data_prox_cobr    DATE;
   v_dias_restantes    INTEGER;
   v_dias_no_ciclo     INTEGER := 30;
   v_valor_reembolso   NUMERIC(10,2);
BEGIN
   -- Buscar dados da assinatura com lock pessimista
   SELECT p.preco, a.data_proxima_cobranca
     INTO v_preco, v_data_prox_cobr
     FROM assinatura a
     JOIN plano p ON p.id = a.plano_id
    WHERE a.id = p_assinatura_id
      AND a.status_ativa = TRUE
      FOR UPDATE;


   IF NOT FOUND THEN
       RAISE EXCEPTION
           'Assinatura % nao encontrada ou ja cancelada',
           p_assinatura_id
           USING ERRCODE = 'P0001';
   END IF;


   -- Calcular dias restantes no ciclo atual
   v_dias_restantes := GREATEST(0, v_data_prox_cobr - CURRENT_DATE);


   -- Proporcao: dias restantes / 30 * preco
   v_valor_reembolso := ROUND(
       v_preco * v_dias_restantes::NUMERIC / v_dias_no_ciclo,
       2
   );


   RETURN v_valor_reembolso;
END;
$$;
