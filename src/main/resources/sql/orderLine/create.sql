INSERT INTO order_lines (
    order_id,
    line_no,
    goods_id,
    quantity,
    created_datetime
)
VALUES (
    :orderId,
    :lineNo,
    :goodsId,
    :quantity,
    now()
);