INSERT INTO accounts (email, password, nickname, firstname, lastname, firstname_kana, lastname_kana, birth_day, phone1, phone2, phone3, zip, prefecture, address1, address2, address3, description, created_datetime, modified_datetime) VALUES ('hanako@example.com', '$2a$10$QTUx0Zeg8ABbMPXmG.TTmeACPu6NfC/s9BO/mOs140FByXluP83ku', 'ブーツねこ', '花子', '振間', 'はなこ', 'ふりま', '1999-01-01', '012', '3456', '9789', '1030028', 13, '中央区', '自由市場1-1-1', null, 'ブーツねこです。買ったけどあまり身に着けていない洋服やブーツなどをこちらで売ってます。', now(), null);
INSERT INTO accounts (email, password, nickname, firstname, lastname, firstname_kana, lastname_kana, birth_day, phone1, phone2, phone3, zip, prefecture, address1, address2, address3, description, created_datetime, modified_datetime) VALUES ('taro@example.com', '$2a$10$E/yjqiBnsuKlwTjEIZmviOg.KPkCdKmj08FY4wSY3.Kp3fvf14p8.', 'フリーマン', '太郎', '山田', 'たろう', 'やまだ', '1999-01-02', '012', '3456', '7890', '1030028', 13, '中央区', '自由市場1-2-3', null, 'フリーマンと申します。どうぞよろしくお願いいたします。', now(), null);

-- カテゴリ
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (1, 'ファッション', 0, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (2, 'ホビー・エンタメ', 0, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (3, 'インテリア', 0, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (4, '電化製品', 0, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (5, 'ビューティ', 0, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (6, 'その他', 0, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (7, 'レディース', 1, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (8, 'メンズ', 1, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (9, 'キッズ・ベビー', 1, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (10, 'スポーツウェア＆シューズ', 1, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (11, 'バッグ・小物・アクセサリ', 1, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (12, 'その他ファッション', 1, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (13, '本・雑誌', 2, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (14, 'CD・DVD・ゲーム', 2, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (15, 'スポーツ', 2, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (16, 'アウトドア・レジャー', 2, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (17, '音楽・アート', 2, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (18, 'その他ホビー・エンタメ', 2, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (19, '家具', 3, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (20, 'テキスタイル・布製品', 3, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (21, 'ライト・照明', 3, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (22, 'バス・ランドリー・キッチン', 3, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (23, 'インテリア小物', 3, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (24, 'その他インテリア', 3, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (25, 'パソコン', 4, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (26, 'スマートフォン・モバイル', 4, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (27, 'カメラ・ビデオカメラ', 4, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (28, 'テレビ・オーディオ', 4, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (29, '生活家電', 4, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (30, 'その他電化製品', 4, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (31, 'コスメ', 5, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (32, 'フレグランス', 5, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (33, '各種ケア', 5, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (34, 'リラクゼーション', 5, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (35, 'ダイエット', 5, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (36, 'その他ビューティ', 5, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (37, 'オート・オートパーツ', 6, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (38, 'ペット用品', 6, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (39, 'ステーショナリー', 6, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (40, '食料品', 6, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (41, 'チケット', 6, now(), null);
INSERT INTO categories (category_id, category_name, parent_id, created_datetime, modified_datetime) VALUES (42, 'その他', 6, now(), null);

-- 商品
INSERT INTO goods (goods_name, description, price, state, delivery_charge, delivery_method, delivery_origin, delivery_days, image, thumbnail, account_id, category_id, created_datetime, modified_datetime)
VALUES ('ネコのTシャツ S', 'サイズはSです。半年前に買って一度も着ていません。', 1000, 0, 1, 2, 13, 1, 'no-image.png', 'no-image.png', 1, 8, now(), null);
INSERT INTO goods (goods_name, description, price, state, delivery_charge, delivery_method, delivery_origin, delivery_days, image, thumbnail, account_id, category_id, created_datetime, modified_datetime)
VALUES ('ネコのTシャツ M', 'サイズはMです。半年前に買って一度も着ていません。', 1000, 1, 1, 1, 11, 1, 'no-image.png', 'no-image.png', 1, 8, now(), null);
INSERT INTO goods (goods_name, description, price, state, delivery_charge, delivery_method, delivery_origin, delivery_days, image, thumbnail, account_id, category_id, created_datetime, modified_datetime)
VALUES ('ネコのTシャツ L', 'サイズはLです。半年前に買って一度も着ていません。', 1000, 2, 2, 2, 12, 2, 'no-image.png', 'no-image.png', 1, 8, now(), null);
INSERT INTO goods (goods_name, description, price, state, delivery_charge, delivery_method, delivery_origin, delivery_days, image, thumbnail, account_id, category_id, created_datetime, modified_datetime)
VALUES ('ネコのTシャツ SS', 'サイズはSSです。半年前に買って一度も着ていません。', 1000, 3, 0, 3, 14, 0, 'no-image.png', 'no-image.png', 1, 8, now(), null);
INSERT INTO goods (goods_name, description, price, state, delivery_charge, delivery_method, delivery_origin, delivery_days, image, thumbnail, account_id, category_id, created_datetime, modified_datetime)
VALUES ('ネコのTシャツ LL', 'サイズはLLです。半年前に買って一度も着ていません。', 1000, 4, 1, 4, 15, 1, 'no-image.png', 'no-image.png', 1, 8, now(), null);
INSERT INTO goods (goods_name, description, price, state, delivery_charge, delivery_method, delivery_origin, delivery_days, image, thumbnail, account_id, category_id, created_datetime, modified_datetime)
VALUES ('ネコのTシャツ O', 'サイズはOです。半年前に買って一度も着ていません。', 1000, 5, 2, 0, 16, 2, 'no-image.png', 'no-image.png', 1, 8, now(), null);

-- 都道府県
INSERT INTO prefectures (prefecture_id, prefecture_name)
VALUES (1,'北海道'),
(2,'青森県'),
(3,'岩手県'),
(4,'宮城県'),
(5,'秋田県'),
(6,'山形県'),
(7,'福島県'),
(8,'茨城県'),
(9,'栃木県'),
(10,'群馬県'),
(11,'埼玉県'),
(12,'千葉県'),
(13,'東京都'),
(14,'神奈川県'),
(15,'新潟県'),
(16,'富山県'),
(17,'石川県'),
(18,'福井県'),
(19,'山梨県'),
(20,'長野県'),
(21,'岐阜県'),
(22,'静岡県'),
(23,'愛知県'),
(24,'三重県'),
(25,'滋賀県'),
(26,'京都府'),
(27,'大阪府'),
(28,'兵庫県'),
(29,'奈良県'),
(30,'和歌山県'),
(31,'鳥取県'),
(32,'島根県'),
(33,'岡山県'),
(34,'広島県'),
(35,'山口県'),
(36,'徳島県'),
(37,'香川県'),
(38,'愛媛県'),
(39,'高知県'),
(40,'福岡県'),
(41,'佐賀県'),
(42,'長崎県'),
(43,'熊本県'),
(44,'大分県'),
(45,'宮崎県'),
(46,'鹿児島県'),
(47,'沖縄県')
