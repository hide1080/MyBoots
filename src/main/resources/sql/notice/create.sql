INSERT INTO user_notice (
    account_id,
    notice_id,
    goods_id,
    status,
    created_datetime
)
VALUES (
    :accountId,
    :noticeId,
    :goodsId,
    :status,
    now()
)