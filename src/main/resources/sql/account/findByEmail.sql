SELECT
    account_id,
    email,
    password,
    nickname,
    firstname,
    lastname,
    firstname_kana,
    lastname_kana,
    birth_day,
    phone1,
    phone2,
    phone3,
    zip,
    prefecture,
    address1,
    address2,
    address3,
    description
FROM
    accounts
WHERE
    email = :email