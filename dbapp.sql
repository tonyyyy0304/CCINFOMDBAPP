CREATE DATABASE ecommerce_db;
USE ecommerce_db;

-- Create the tables
CREATE TABLE store (
    store_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    store_name VARCHAR(45) NOT NULL,
    contact_id INT NOT NULL,
    location_id INT NOT NULL,
    registration_date DATE DEFAULT (CURDATE())
);

CREATE TABLE locations (
    location_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    lot_number INT NOT NULL,
    street_name VARCHAR(45) NOT NULL,
    city_name VARCHAR(45) NOT NULL,
    zip_code INT NOT NULL,
    country_name VARCHAR(45) NOT NULL
);

CREATE TABLE contact_information (
    contact_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    phone_number BIGINT NOT NULL UNIQUE,
    email_address VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    location_id INT NOT NULL,
    contact_id INT NOT NULL,
    customer_status ENUM('Active', 'Inactive') NOT NULL,
    registration_date DATE DEFAULT (CURDATE()),
    birthdate DATE NOT NULL
);

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    store_id INT NOT NULL,
    stock_count INT UNSIGNED NOT NULL,
    description VARCHAR(200) NOT NULL,
    category ENUM('Clothing', 'Electronics', 'Beauty & Personal Care', 'Food & Beverages', 'Toys', 'Appliances', 'Home & Living') NOT NULL,
    r18 ENUM('T', 'F') NOT NULL
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    customer_id INT NOT NULL,
    delivery_location_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    order_date DATE DEFAULT (CURDATE()),
    price DECIMAL(10, 2) NOT NULL,
    payment_method ENUM('Credit', 'Debit', 'Cash') NOT NULL,
    shipping_price DECIMAL(5, 2) NOT NULL DEFAULT 50.00
);

CREATE TABLE shipping (
    shipping_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_id INT NOT NULL,
    logistics_company_id INT NOT NULL,
    expected_arrival_date DATE NOT NULL
);

CREATE TABLE logistics_companies (
    logistics_company_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    location_id INT NOT NULL,
    logistics_company_name VARCHAR(50) NOT NULL,
    shipment_scope ENUM('Domestic', 'International') NOT NULL
);

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_id INT NOT NULL,
    store_id INT NOT NULL,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    amount_paid DECIMAL(10, 2) UNSIGNED NOT NULL DEFAULT 0.0,
    payment_status ENUM('Completed', 'Pending', 'Failed') NOT NULL DEFAULT 'Pending',
    payment_date DATE DEFAULT (CURDATE())
);

CREATE TABLE stock_adjustment_history (
    stock_adjustment_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    product_id INT NOT NULL,
    store_id INT NOT NULL,
    customer_id INT,
    adjustment_type ENUM('Increase', 'Decrease') NOT NULL,
    adjustment_amount INT NOT NULL,
    adjustment_date DATE DEFAULT (CURDATE())
);

-- Define foreign key relationships
ALTER TABLE store ADD CONSTRAINT fk_store_contact FOREIGN KEY (contact_id) REFERENCES contact_information(contact_id);
ALTER TABLE store ADD CONSTRAINT fk_store_location FOREIGN KEY (location_id) REFERENCES locations(location_id);

ALTER TABLE customers ADD CONSTRAINT fk_customers_location FOREIGN KEY (location_id) REFERENCES locations(location_id);
ALTER TABLE customers ADD CONSTRAINT fk_customers_contact FOREIGN KEY (contact_id) REFERENCES contact_information(contact_id);

ALTER TABLE products ADD CONSTRAINT fk_products_store FOREIGN KEY (store_id) REFERENCES store(store_id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_location FOREIGN KEY (delivery_location_id) REFERENCES locations(location_id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_product FOREIGN KEY (product_id) REFERENCES products(product_id);

ALTER TABLE shipping ADD CONSTRAINT fk_shipping_order FOREIGN KEY (order_id) REFERENCES orders(order_id);
ALTER TABLE shipping ADD CONSTRAINT fk_shipping_logistics FOREIGN KEY (logistics_company_id) REFERENCES logistics_companies(logistics_company_id);

ALTER TABLE logistics_companies ADD CONSTRAINT fk_logistics_location FOREIGN KEY (location_id) REFERENCES locations(location_id);

ALTER TABLE payments ADD CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders(order_id);
ALTER TABLE payments ADD CONSTRAINT fk_payments_store FOREIGN KEY (store_id) REFERENCES store(store_id);
ALTER TABLE payments ADD CONSTRAINT fk_payments_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id);
ALTER TABLE payments ADD CONSTRAINT fk_payments_product FOREIGN KEY (product_id) REFERENCES products(product_id);

ALTER TABLE stock_adjustment_history ADD CONSTRAINT fk_stock_history_product FOREIGN KEY (product_id) REFERENCES products(product_id);
ALTER TABLE stock_adjustment_history ADD CONSTRAINT fk_stock_history_store FOREIGN KEY (store_id) REFERENCES store(store_id);
ALTER TABLE stock_adjustment_history ADD CONSTRAINT fk_stock_history_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id);

-- Triggers
DELIMITER //

CREATE TRIGGER set_default_delivery_location
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
    DECLARE customer_location_id INT;
    
    -- Get the customer's location_id
    SELECT location_id INTO customer_location_id
    FROM customers
    WHERE customer_id = NEW.customer_id;
    
    -- Set the delivery_location_id to the customer's location_id
    SET NEW.delivery_location_id = customer_location_id;
END //

DELIMITER ;

DELIMITER //

CREATE TRIGGER set_default_order_price
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
    DECLARE product_price DECIMAL(10, 2);
    
    -- Get the product's price
    SELECT price INTO product_price
    FROM products
    WHERE product_id = NEW.product_id;
    
    -- Set the price to quantity * product price
    SET NEW.price = NEW.quantity * product_price;
END //

DELIMITER ;

-- Insert sample data
INSERT INTO locations (lot_number, street_name, city_name, zip_code, country_name) VALUES
    (6153, 'South Super Highway', 'Makati', 1210, 'Philippines'), -- location_ID 1
    (7685, 'St. Paul Road', 'Makati', 1203, 'Philippines'), -- location_ID 2
    (14, 'Zaragoza St.', 'Makati', 1223, 'Philippines'), -- location_ID 3
    (5, 'Kennedy Drive', 'Quezon City', 1116, 'Philippines'), -- location_ID 4
    (27, 'Jade Heights', 'Muntinlupa', 1773, 'Philippines'), -- location_ID 5
    (2401, 'Taft Avenue', 'Manila', 1004, 'Philippines'), -- location_ID 6
    (84, 'Kaingin Road', 'Quezon City', 1100, 'Philippines'), -- location_ID 7
    (56, 'Buendia Avenue', 'Makati', 1211, 'Philippines'), -- location_ID 8
    (78, 'Pasong Tamo', 'Makati', 1212, 'Philippines'), -- location_ID 9
    (90, 'Gil Puyat Avenue', 'Makati', 1213, 'Philippines'), -- location_ID 10
    (123, 'EDSA', 'Quezon City', 1100, 'Philippines'), -- location_ID 11
    (456, 'Roxas Blvd', 'Pasay', 1300, 'Philippines'), -- location_ID 12
    (789, 'Ortigas Ave', 'Pasig', 1600, 'Philippines'), -- location_ID 13
    (101, '5th Ave', 'New York', 10001, 'USA'), -- location_ID 14
    (202, 'Oxford St', 'London', 12345, 'UK'); -- location_ID 15

INSERT INTO contact_information (phone_number, email_address) VALUES
    (9178304474, 'chinese_antiques@gmail.com'), -- contact_ID 1
    (8995457, 'frames@gmail.com'), -- contact_ID 2
    (8137310, 'apt@rose.com'), -- contact_ID 3
    (4558618, 't-shirts@yahoo.com'), -- contact_ID 4
    (5683463, 'artsncrafts@outlook.com'), -- contact_ID 5
    (3647734, 'juan_dela_cruz@yahoo.com'), -- contact_ID 6
    (9224098691, 'jane_doe@gmail.com'), -- contact_ID 7
    (9224444844, 'john_doe@yahoo.com'), -- contact_ID 8
    (9012345678, 'darna@outlook.com'), -- contact_ID 9
    (9123456789, 'batman@yahoo.com'); -- contact_ID 10

INSERT INTO store (store_name, contact_id, location_id, registration_date) VALUES
    ('Chinese Antiques', 1, 1, '2020-01-01'), -- store_ID 1
    ('Frames & More', 2, 2, '2020-02-01'), -- store_ID 2
    ('Apt. 23', 3, 3, '2020-03-01'), -- store_ID 3
    ('T-Shirts Galore', 4, 4, '2020-04-01'), -- store_ID 4
    ('Arts & Crafts', 5, 5, '2020-05-01'); -- store_ID 5

INSERT INTO customers (first_name, last_name, location_id, contact_id, customer_status, registration_date, birthdate) VALUES
    ('Juan', 'Dela Cruz', 6, 6, 'Active', '2020-01-01', '1990-01-01'), -- customer_ID 1
    ('Jane', 'Doe', 7, 7, 'Active', '2020-02-01', '1995-02-01'), -- customer_ID 2
    ('John', 'Doe', 8, 8, 'Active', '2020-03-01', '1998-03-01'), -- customer_ID 3
    ('Darna', 'Dyesebel', 9, 9, 'Active', '2020-04-01', '1992-04-01'), -- customer_ID 4
    ('Batman', 'Superman', 10, 10, 'Active', '2020-05-01', '1993-05-01'); -- customer_ID 5

INSERT INTO products (product_name, price, store_id, stock_count, description, category, r18) VALUES
    ('Antique Vase', 1500.00, 1, 10, 'A beautiful antique vase from the Ming dynasty.', 'Home & Living', 'T'), -- product_ID 1
    ('Chinese Painting', 3000.00, 1, 5, 'An exquisite painting from the Qing dynasty.', 'Home & Living', 'T'), -- product_ID 2
    ('Wooden Frame', 500.00, 2, 20, 'A handcrafted wooden frame.', 'Home & Living', 'F'), -- product_ID 3
    ('Modern Art Piece', 2000.00, 3, 15, 'A contemporary piece of modern art.', 'Home & Living', 'F'), -- product_ID 4
    ('Graphic T-Shirt', 250.00, 4, 50, 'A trendy graphic t-shirt.', 'Clothing', 'F'); -- product_ID 5

INSERT INTO logistics_companies (location_id, logistics_company_name, shipment_scope) VALUES
    (11, 'LBC Express', 'Domestic'), -- logistics_company_ID 1
    (12, 'JRS Express', 'Domestic'), -- logistics_company_ID 2
    (13, '2GO Express', 'Domestic'), -- logistics_company_ID 3
    (14, 'FedEx', 'International'), -- logistics_company_ID 4
    (15, 'DHL Express', 'International'); -- logistics_company_ID 5

INSERT INTO orders (customer_id, delivery_location_id, product_id, quantity, order_date, price, payment_method, shipping_price) VALUES
    (1, 1, 1, 2, '2021-01-15', 3000.00, 'Credit', 50.00), -- order_ID 1
    (2, 2, 2, 1, '2021-02-20', 3000.00, 'Debit', 75.00), -- order_ID 2
    (3, 3, 3, 4, '2021-03-25', 2000.00, 'Cash', 100.00), -- order_ID 3
    (4, 4, 4, 3, '2021-04-30', 6000.00, 'Credit', 150.00), -- order_ID 4
    (5, 5, 5, 5, '2021-05-05', 1250.00, 'Debit', 200.00), -- order_ID 5
    (1, 6, 1, 1, '2022-06-10', 1500.00, 'Cash', 50.00), -- order_ID 6
    (2, 7, 2, 2, '2022-07-15', 6000.00, 'Credit', 75.00), -- order_ID 7
    (3, 8, 3, 3, '2022-08-20', 1500.00, 'Debit', 100.00), -- order_ID 8
    (4, 9, 4, 4, '2022-09-25', 8000.00, 'Cash', 150.00), -- order_ID 9
    (5, 10, 5, 5, '2022-10-30', 1250.00, 'Credit', 200.00); -- order_ID 10

INSERT INTO shipping (order_id, logistics_company_id, expected_arrival_date) VALUES
    (1, 1, '2021-01-20'), -- shipping_ID 1
    (2, 2, '2021-02-25'), -- shipping_ID 2
    (3, 3, '2021-03-30'), -- shipping_ID 3
    (4, 4, '2021-05-05'), -- shipping_ID 4
    (5, 5, '2021-06-10'), -- shipping_ID 5
    (6, 1, '2022-06-15'), -- shipping_ID 6
    (7, 2, '2022-07-20'), -- shipping_ID 7
    (8, 3, '2022-08-25'), -- shipping_ID 8
    (9, 4, '2022-10-01'), -- shipping_ID 9
    (10, 5, '2022-11-05'); -- shipping_ID 10

INSERT INTO payments (order_id, store_id, customer_id, product_id, amount_paid, payment_status) VALUES
    (1, 1, 1, 1, 3050.00, 'Completed'), -- payment_ID 1
    (2, 1, 2, 2, 3075.00, 'Completed'), -- payment_ID 2
    (3, 2, 3, 3, 2100.00, 'Completed'), -- payment_ID 3
    (4, 3, 4, 4, 6150.00, 'Completed'), -- payment_ID 4
    (5, 4, 5, 5, 1450.00, 'Completed'), -- payment_ID 5
    (6, 1, 1, 1, 1550.00, 'Completed'), -- payment_ID 6
    (7, 1, 2, 2, 6075.00, 'Completed'), -- payment_ID 7
    (8, 2, 3, 3, 1600.00, 'Completed'), -- payment_ID 8
    (9, 3, 4, 4, 8150.00, 'Completed'), -- payment_ID 9
    (10, 4, 5, 5, 1450.00, 'Completed'); -- payment_ID 10

INSERT INTO stock_adjustment_history (product_id, store_id, customer_id, adjustment_type, adjustment_amount) VALUES
    (1, 1, 1, 'Decrease', 2), -- stock_adjustment_ID 1
    (2, 1, 2, 'Decrease', 1), -- stock_adjustment_ID 2
    (3, 2, 3, 'Decrease', 4), -- stock_adjustment_ID 3
    (4, 3, 4, 'Decrease', 3), -- stock_adjustment_ID 4
    (5, 4, 5, 'Decrease', 5), -- stock_adjustment_ID 5
    (1, 1, 1, 'Decrease', 1), -- stock_adjustment_ID 6
    (2, 1, 2, 'Decrease', 2), -- stock_adjustment_ID 7
    (3, 2, 3, 'Decrease', 3), -- stock_adjustment_ID 8
    (4, 3, 4, 'Decrease', 4), -- stock_adjustment_ID 9
    (5, 4, 5, 'Decrease', 5), -- stock_adjustment_ID 10
    (1, 1, NULL, 'Increase', 5), -- stock_adjustment_ID 11
    (2, 1, NULL, 'Increase', 10); -- stock_adjustment_ID 12