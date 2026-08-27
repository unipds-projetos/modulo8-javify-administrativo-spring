CREATE OR REPLACE VIEW v_mrr_por_plano AS
SELECT
    p.nome                          AS plano,
    p.preco                         AS preco_unitario,
    COUNT(a.id)                     AS assinaturas_ativas,
    SUM(p.preco)                    AS mrr_plano,
    ROUND(AVG(p.preco), 2)          AS ticket_medio
FROM assinatura a
INNER JOIN plano p ON p.id = a.plano_id
WHERE a.status_ativa = TRUE
GROUP BY p.nome, p.preco
ORDER BY mrr_plano DESC;

-- Uso: identico a consultar uma tabela
SELECT * FROM v_mrr_por_plano;
