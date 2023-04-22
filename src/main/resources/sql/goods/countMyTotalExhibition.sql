SELECT
    COUNT(*)
FROM
    goods g
WHERE
    g.account_id = :accountId
