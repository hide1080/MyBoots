SELECT
    u.user_notice_id  AS user_notice_id,
    u.account_id      AS account_id,
    u.notice_id       AS description,
    u.goods_id        AS goods_id,
    u.status          AS status
FROM
    user_notice AS u
WHERE
    u.account_id = :accountId
    AND
    u.status = 0
ORDER BY
    u.created_datetime,
    u.user_notice_id
