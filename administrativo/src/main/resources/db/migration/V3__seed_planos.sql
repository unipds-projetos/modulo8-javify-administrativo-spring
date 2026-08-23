-- V3__seed_planos.sql
-- Dados iniciais dos planos do Javify


INSERT INTO plano (nome, preco, possui_propagandas, limite_membros, modo_offline, descricao)
VALUES
   ('Free', 0.00, TRUE, 1, FALSE,'Gratis com propagandas. Ideal para experimentar o Javify.'),
   ('Standard', 19.90, FALSE, 1, FALSE,'Sem propagandas para uma pessoa. Qualidade de audio premium.'),
   ('Premium', 34.90, FALSE, 6, TRUE, 'Ate 6 membros, modo offline e qualidade maxima. O melhor do Javify.');
