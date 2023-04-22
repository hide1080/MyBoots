SELECT
    COUNT(*)
FROM
    goods AS g
    INNER JOIN
    order_lines AS l
    ON
    l.goods_id = g.goods_id
    INNER JOIN
    orders AS o
    ON
    o.order_id = l.order_id
WHERE
    o.account_id = :accountId
