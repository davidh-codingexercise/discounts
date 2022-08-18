DROP TABLE IF EXISTS discounts;
CREATE TABLE discounts (
   code                    VARCHAR(3)    NOT NULL,
   type                    VARCHAR(15)   NOT NULL,
   percentage_discount     DECIMAL(10,2),
   for_item_type           VARCHAR(15),
   for_cost                DECIMAL(10,2),
   for_quantity            INTEGER,
   for_quantity_item_id    INTEGER,
   PRIMARY KEY (code)
);

