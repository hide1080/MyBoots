UPDATE goods SET
    sale_end_date = :saleEndDate
WHERE
    goods_id = :goodsId
    AND
    sale_end_date IS NULL
