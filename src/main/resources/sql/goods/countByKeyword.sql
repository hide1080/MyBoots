SELECT
    COUNT(*)
FROM
    goods AS g
WHERE
    g.goods_name LIKE :keyword
    OR
    g.description LIKE :keyword
