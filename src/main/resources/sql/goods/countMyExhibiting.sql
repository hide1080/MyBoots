SELECT
    COUNT(*)
FROM
    goods g
WHERE
    g.account_id = :accountId
    AND
    g.sale_end_date IS NULL
