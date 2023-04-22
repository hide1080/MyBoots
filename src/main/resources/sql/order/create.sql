INSERT INTO orders (
    account_id,
    order_date,
    created_datetime
)
VALUES (
    :accountId,
    :orderDate,
    now()
)