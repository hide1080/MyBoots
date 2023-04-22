CREATE TABLE accounts (
  account_id  INT auto_increment PRIMARY KEY,
  email             VARCHAR(100) not null,
  password          VARCHAR(255) not null,
  nickname          VARCHAR(20) not null,
  firstname         VARCHAR(20) not null,
  lastname          VARCHAR(20) not null,
  firstname_kana    VARCHAR(30) not null,
  lastname_kana     VARCHAR(30) not null,
  birth_day         DATE not null,
  phone1            VARCHAR(10) not null,
  phone2            VARCHAR(10) not null,
  phone3            VARCHAR(10) not null,
  zip               VARCHAR(10) not null,
  prefecture        INT not null,           -- 都道府県
  address1          VARCHAR(20) not null,   -- 市区町村
  address2          VARCHAR(40) not null,   -- 番地
  address3          VARCHAR(40),            -- 建物名
  description       VARCHAR(500),
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP
);

CREATE TABLE categories (
  category_id       INT PRIMARY KEY,
  category_name     VARCHAR(30) not null,
  parent_id         INT,
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP
);

CREATE TABLE goods (
  goods_id        INT auto_increment PRIMARY KEY,
  goods_name      VARCHAR(50) not null,
  description     VARCHAR(500) not null,
  price           INT not null,
  state           INT not null, -- 0:新品・未使用、1:きれい、2:目立つ傷や汚れなし、3:やや傷や汚れあり、4:全体的に状態が悪い
  delivery_charge INT not null, -- 0:送料込み（出品者負担）、1:送料着払い（購入者負担）、2:手渡し（送料負担なし）
  delivery_method INT not null, -- 0:未定、1:ゆうメール、2:ポスパケット、3:ゆうパック、4:宅急便
  delivery_origin INT not null,
  delivery_days   INT not null, -- 0:1～2日で発送、1:2～3日で発送、2:4～7日で発送、3:8日以降に発送
  image           VARCHAR(100) not null,
  thumbnail       VARCHAR(100) not null,
  account_id      INT not null, 
  category_id     INT not null,
  sale_end_date   DATE,
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts (account_id),
  FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE orders (
  order_id   INT auto_increment PRIMARY KEY,
  account_id INT,
  order_date DATE,
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);

CREATE TABLE order_lines (
  order_id  INT,
  line_no   INT,
  goods_id  INT,
  quantity  INT,
  status    INT, -- 0:取引中、1:支払済み、2:発送済み、3:受取済み
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP,
  PRIMARY KEY (order_id, line_no),
  FOREIGN KEY (order_id) REFERENCES orders (order_id),
  FOREIGN KEY (goods_id) REFERENCES goods (goods_id)
);


CREATE TABLE prefectures (
  prefecture_id   INT PRIMARY KEY,
  prefecture_name VARCHAR(10) not null
);

CREATE TABLE delivery_addresses (
  address_id        INT auto_increment PRIMARY KEY,
  account_id        INT,
  firstname         VARCHAR(20) not null,
  lastname          VARCHAR(20) not null,
  firstname_kana    VARCHAR(30) not null,
  lastname_kana     VARCHAR(30) not null,
  phone1            VARCHAR(10) not null,
  phone2            VARCHAR(10) not null,
  phone3            VARCHAR(10) not null,
  zip               VARCHAR(10) not null,
  prefecture        INT not null,
  address1          VARCHAR(20) not null,
  address2          VARCHAR(40) not null,
  address3          VARCHAR(40),
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);


CREATE TABLE user_notice (
  user_notice_id    INT auto_increment PRIMARY KEY,
  account_id        INT not null,
  notice_id         INT not null,
  goods_id          INT,
  status            INT not null, -- 0:未通知、1:通知済み
  created_datetime  TIMESTAMP,
  modified_datetime TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);
