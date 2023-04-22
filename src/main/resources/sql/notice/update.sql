UPDATE user_notice SET
    status = ?,
    modified_datetime = now()
WHERE
    account_id = ?
    AND
    goods_id = ?
