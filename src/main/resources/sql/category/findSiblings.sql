SELECT
    a.category_id,
    a.category_name,
    a.parent_id
FROM
    categories a,
    categories b
WHERE
    b.category_id = :categoryId
    AND
    a.parent_id = b.parent_id
ORDER BY
    a.category_id ASC