
DROP TABLE IF EXISTS order_product cascade;
DROP TABLE IF EXISTS shipping_address cascade;
DROP TABLE IF EXISTS order_customer cascade;
DROP TABLE IF EXISTS customer cascade;
DROP TABLE IF EXISTS product cascade;
DROP TABLE IF EXISTS payment_type cascade;

CREATE TABLE IF NOT EXISTS product(
   id BINARY(16) PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   description VARCHAR(100),
   price DECIMAL(10, 2) NOT NULL,
   weight DECIMAL(10, 2)
);

CREATE TABLE  IF NOT EXISTS payment_type(
   id BINARY(16) PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   description VARCHAR(100)
);

CREATE TABLE  IF NOT EXISTS customer(
   id BINARY(16) PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   phone VARCHAR(100),
   email VARCHAR(100) NOT NULL,
   primary_shipping_address BINARY(16),
   CONSTRAINT UC_Customer UNIQUE (name,email)
);

CREATE TABLE   IF NOT EXISTS shipping_address(
   id BINARY(16) PRIMARY KEY,
   number VARCHAR(100) NOT NULL,
   city VARCHAR(100) NOT NULL,
   zip_code VARCHAR(100) NOT NULL,
   country VARCHAR(100) NOT NULL,
   customer_id BINARY(16)  NOT NULL,
   FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE   IF NOT EXISTS order_customer(
   id BINARY(16) PRIMARY KEY,
   customer_shipping_address_id BINARY(16),
   order_date DATE NOT NULL,
   arrival_date DATE,
   payment_type_id BINARY(16) NOT NULL,
   customer_id BINARY(16) NOT NULL,
   order_number INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   FOREIGN KEY (customer_id) REFERENCES customer(id),
   FOREIGN KEY (payment_type_id) REFERENCES payment_type(id),
   CONSTRAINT UC_Order_Customer UNIQUE (order_number)
);

CREATE TABLE   IF NOT EXISTS order_product(
   id BINARY(16) PRIMARY KEY,
   product_id BINARY(16) NOT NULL,
   order_customer_id BINARY(16) NOT NULL,
   quantity INT(11) UNSIGNED NOT NULL,
   FOREIGN KEY (product_id) REFERENCES product(id),
   FOREIGN KEY (order_customer_id) REFERENCES order_customer(id)
);





