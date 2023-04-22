SELECT
    COUNT(*)
FROM
    goods AS g
WHERE
    g.account_id = :accountId
    AND
    g.sale_end_date IS NOT NULL
