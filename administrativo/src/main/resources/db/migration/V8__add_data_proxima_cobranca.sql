ALTER TABLE assinatura
   ADD COLUMN data_proxima_cobranca DATE;


-- Popular as assinaturas ativas com data futura para demo
UPDATE assinatura
  SET data_proxima_cobranca = CURRENT_DATE + INTERVAL '15 days'
WHERE status_ativa = TRUE;
