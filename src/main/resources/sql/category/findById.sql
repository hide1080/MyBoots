SELECT
    category_id,
    category_name,
    parent_id
FROM
    categories
WHERE
    category_id = :categoryId
