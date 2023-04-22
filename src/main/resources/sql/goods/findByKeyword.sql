SELECT
    g.goods_id        AS goods_id,
    g.goods_name      AS goods_name,
    g.description     AS description,
    g.price           AS price,
    g.state           AS state,
    g.delivery_charge AS delivery_charge,
    g.delivery_method AS delivery_method,
    g.delivery_days   AS delivery_days,
    g.account_id      AS account_id,
    g.image           AS image,
    g.thumbnail       AS thumbnail,
    g.sale_end_date   AS sale_end_date,
    a.account_id      AS account_id,
    a.nickname        AS nickname,
    c.category_id     AS category_id,
    c.category_name   AS category_name,
    c.parent_id       AS parent_id,
    p.prefecture_id   AS prefecture_id,
    p.prefecture_name AS prefecture_name,
    CASE WHEN g.sale_end_date IS NULL THEN 0 ELSE 1 END AS sold_out
FROM
    goods AS g
    INNER JOIN
    categories AS c
    ON
    g.category_id = c.category_id
    INNER JOIN
    accounts AS a
    ON
    a.account_id = g.account_id
    INNER JOIN
    prefectures AS p
    ON
    p.prefecture_id = g.delivery_origin
WHERE
    g.goods_name LIKE :keyword
    OR
    g.description LIKE :keyword
