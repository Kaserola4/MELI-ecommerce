-- ---------------------------
-- Clients
-- ---------------------------
CREATE TABLE CLIENTS (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name TEXT,
                         address TEXT,
                         age INT
);

CREATE TABLE ITEMS (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name TEXT,
                       description TEXT,
                       price DOUBLE NOT NULL
);

CREATE TABLE ORDERS (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        id_client BIGINT,
                        id_item BIGINT,
                        purchase_date DATE,
                        delivery_date DATE,
                        CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES CLIENTS(id),
                        CONSTRAINT fk_item FOREIGN KEY (id_item) REFERENCES ITEMS(id)
);

INSERT INTO clients (id, name, address, age) VALUES (1, 'Juan', 'Calle 1', 25);
INSERT INTO clients (id, name, address, age) VALUES (2, 'Maria', 'Calle 2', 30);
INSERT INTO clients (id, name, address, age) VALUES (3, 'Carlos', 'Calle 3', 28);
INSERT INTO clients (id, name, address, age) VALUES (4, 'Ana', 'Calle 4', 22);
INSERT INTO clients (id, name, address, age) VALUES (5, 'Luis', 'Calle 5', 35);

-- ---------------------------
-- Items
-- ---------------------------
INSERT INTO ITEMS (id, name, description, price) VALUES (1, 'Laptop', 'Gaming Laptop', 1200.00);
INSERT INTO ITEMS (id, name, description, price) VALUES (2, 'Phone', 'Smartphone', 800.00);
INSERT INTO ITEMS (id, name, description, price) VALUES (3, 'Headphones', 'Noise-cancelling', 150.00);
INSERT INTO ITEMS (id, name, description, price) VALUES (4, 'Keyboard', 'Mechanical Keyboard', 100.00);
INSERT INTO ITEMS (id, name, description, price) VALUES (5, 'Monitor', '27 inch Monitor', 300.00);

-- ---------------------------
-- Orders
-- ---------------------------
-- INSERT INTO ORDERS (id, id_client, id_item, purchase_date, delivery_date)
-- VALUES (1, 1, 1, '2025-10-01', '2025-10-05');
--
-- INSERT INTO ORDERS (id, id_client, id_item, purchase_date, delivery_date)
-- VALUES (2, 2, 2, '2025-10-02', '2025-10-06');
--
-- INSERT INTO ORDERS (id, id_client, id_item, purchase_date, delivery_date)
-- VALUES (3, 3, 3, '2025-10-03', '2025-10-07');
--
-- INSERT INTO ORDERS (id, id_client, id_item, purchase_date, delivery_date)
-- VALUES (4, 4, 4, '2025-10-04', '2025-10-08');
--
-- INSERT INTO ORDERS (id, id_client, id_item, purchase_date, delivery_date)
-- VALUES (5, 5, 5, '2025-10-05', '2025-10-09');
