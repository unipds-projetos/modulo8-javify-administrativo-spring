CREATE MATERIALIZED VIEW mv_mrr_por_plano AS
SELECT
    p.nome                          AS plano,
    p.preco                         AS preco_unitario,
    COUNT(a.id)                     AS assinaturas_ativas,
    SUM(p.preco)                    AS mrr_plano,
    ROUND(AVG(p.preco), 2)          AS ticket_medio,
    NOW()                           AS atualizado_em
FROM assinatura a
INNER JOIN plano p ON p.id = a.plano_id
WHERE a.status_ativa = TRUE
GROUP BY p.nome, p.preco
ORDER BY mrr_plano DESC
WITH DATA;  -- executa imediatamente e popula o snapshot

-- Índice necessário para REFRESH CONCURRENTLY
CREATE UNIQUE INDEX idx_mv_mrr_plano ON mv_mrr_por_plano (plano);

-- Refresh sem lock de leitura (requer o índice único acima)
REFRESH MATERIALIZED VIEW CONCURRENTLY mv_mrr_por_plano;
