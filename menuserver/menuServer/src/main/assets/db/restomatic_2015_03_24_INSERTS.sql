PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

INSERT INTO "android_metadata" (locale) VALUES ('en_US');

INSERT INTO "profiles" (_id, description) VALUES (1, 'admin');
INSERT INTO "profiles" (_id, description) VALUES (2, 'mozo');

INSERT INTO "options" (_id, option_name, option_value, created_at, updated_at) VALUES (1, 'printer_baudrate', '9600', '2013-04-13', '2013-04-13');
INSERT INTO "options" (_id, option_name, option_value, created_at, updated_at) VALUES (2, 'printer_databits', '8', '2013-04-13', '2013-04-13');
INSERT INTO "options" (_id, option_name, option_value, created_at, updated_at) VALUES (3, 'printer_stopbits', '1', '2013-04-13', '2013-04-13');
INSERT INTO "options" (_id, option_name, option_value, created_at, updated_at) VALUES (4, 'printer_parity', '0', '2013-04-13', '2013-04-13');

INSERT INTO "users" (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (1, 'admin', '1234', 'Jose', 'Vigil', 'josevigil@roamtouch.com', 0, 1, '2013-02-05', '2013-02-05');
INSERT INTO "users" (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (2, 'jperez', '1234', 'Juan', 'Perez', 'juanperez@hotmail.com', 0, 2, '2013-02-05', '2013-02-05');
INSERT INTO "users" (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (3, 'grodriguez', '1234', 'Gonzalo', 'Rodriguez', 'grodriguez@hotmail.com', 0, 2, '2013-02-05', '2013-02-05');
INSERT INTO "users" (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (4, 'fcastillo', '1234', 'Fabian', 'Castillo', 'fcastillo@hotmail.com', 0, 2, '2013-02-05', '2013-02-05');

INSERT INTO "order_status" (_id, description, created_at, updated_at) VALUES (1, 'enviado', '2013-01-09', '2013-01-09');
INSERT INTO "order_status" (_id, description, created_at, updated_at) VALUES (2, 'cancelado', '2013-01-09', '2013-01-09');
INSERT INTO "order_status" (_id, description, created_at, updated_at) VALUES (3, 'no impreso', '2013-01-09', '2013-01-09');
INSERT INTO "order_status" (_id, description, created_at, updated_at) VALUES (4, 'imprimir', '2013-01-09', '2013-01-09');
INSERT INTO "order_status" (_id, description, created_at, updated_at) VALUES (5, 'imprimiendo', '2013-01-09', '2013-01-09');
INSERT INTO "order_status" (_id, description, created_at, updated_at) VALUES (6, 'impreso', '2013-01-09', '2013-01-09');

INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (1, 'abierta', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (2, 'esperando realizar pedido', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (3, 'pedido realizado', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (4, 'pedido entregado', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (5, 'pedido de cierre de cuenta', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (6, 'cierre de cuenta entregado', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (7, 'pago realizado', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (8, 'cobro realizado', '2013-01-09', '2013-01-09');
INSERT INTO "dining_table_status" (_id, description, created_at, updated_at) VALUES (9, 'cerrada', '2013-01-09', '2013-01-09');

COMMIT TRANSACTION;

