ALTER TABLE shopping_cart

    ADD COLUMN id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,

    DROP PRIMARY KEY,

    ADD PRIMARY KEY (id),

    ADD UNIQUE KEY uq_cart_user_product (user_id, product_id);