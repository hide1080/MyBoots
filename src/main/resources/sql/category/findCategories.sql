SELECT
    category_id,
    category_name,
    parent_id
FROM
    categories
WHERE
    parent_id = :parentId
ORDER BY
    category_id ASC