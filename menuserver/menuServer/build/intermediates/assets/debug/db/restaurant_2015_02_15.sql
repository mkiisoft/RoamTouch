--
-- File generated with SQLiteStudio v3.0.2 on Sun Feb 15 04:30:44 2015
--
-- Text encoding used: windows-1252
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: users
CREATE TABLE "users" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"username" VARCHAR NOT NULL ,"password" VARCHAR NOT NULL , "first_name" VARCHAR NOT NULL, "last_name" VARCHAR NOT NULL, "email" VARCHAR,"status" INTEGER NOT NULL ,"profile_id" INTEGER NOT NULL ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(profile_id) REFERENCES profiles(_id) ON DELETE CASCADE)
INSERT INTO users (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (1, 'admin', '1234', 'Jose', 'Vigil', 'josevigil@roamtouch.com', 0, 1, '2013-02-05', '2013-02-05');
INSERT INTO users (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (2, 'jperez', '1234', 'Juan', 'Perez', 'juanperez@hotmail.com', 0, 2, '2013-02-05', '2013-02-05');
INSERT INTO users (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (3, 'grodriguez', '1234', 'Gonzalo', 'Rodriguez', 'grodriguez@hotmail.com', 0, 2, '2013-02-05', '2013-02-05');
INSERT INTO users (_id, username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) VALUES (4, 'fcastillo', '1234', 'Fabian', 'Castillo', 'fcastillo@hotmail.com', 0, 2, '2013-02-05', '2013-02-05');

-- Table: profiles
CREATE TABLE "profiles" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" VARCHAR NOT NULL )
INSERT INTO profiles (_id, description) VALUES (1, 'admin');
INSERT INTO profiles (_id, description) VALUES (2, 'mozo');

-- Table: order_status
CREATE TABLE "order_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL)
INSERT INTO order_status (_id, description, created_at, updated_at) VALUES (1, 'enviado', '2013-01-09', '2013-01-09');
INSERT INTO order_status (_id, description, created_at, updated_at) VALUES (2, 'cancelado', '2013-01-09', '2013-01-09');
INSERT INTO order_status (_id, description, created_at, updated_at) VALUES (3, 'no impreso', '2013-01-09', '2013-01-09');
INSERT INTO order_status (_id, description, created_at, updated_at) VALUES (4, 'imprimir', '2013-01-09', '2013-01-09');
INSERT INTO order_status (_id, description, created_at, updated_at) VALUES (5, 'imprimiendo', '2013-01-09', '2013-01-09');
INSERT INTO order_status (_id, description, created_at, updated_at) VALUES (6, 'impreso', '2013-01-09', '2013-01-09');

-- Table: menu_item
CREATE TABLE "menu_item" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL, description TEXT, price DOUBLE NOT NULL, order_count NULL DEFAULT 0, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL , code)

-- Table: android_metadata
CREATE TABLE android_metadata (locale TEXT)
INSERT INTO android_metadata (locale) VALUES ('en_US');

-- Table: dining_tables
CREATE TABLE dining_tables (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL, blink INTEGER NOT NULL, dining_table_status_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY (dining_table_status_id) REFERENCES dining_table_status (_id) ON DELETE CASCADE)

-- Table: menu_item_addons
CREATE TABLE "menu_item_addons" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL , "menu_item_id" INTEGER NOT NULL ,  "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE)

-- Table: menu_items_categories
CREATE TABLE "menu_items_categories" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, menu_item_id INTEGER NOT NULL, categories_menus_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE, FOREIGN KEY(categories_menus_id) REFERENCES categories_menus(_id) ON DELETE CASCADE)

-- Table: orders
CREATE TABLE orders (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date DATETIME NOT NULL, dining_table_id INTEGER NOT NULL, observation TEXT, order_status_id INTEGER NOT NULL, total DOUBLE NOT NULL, user_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY (dining_table_id) REFERENCES dining_tables (_id) ON DELETE CASCADE, FOREIGN KEY (order_status_id) REFERENCES order_status (_id) ON DELETE CASCADE, FOREIGN KEY (user_id) REFERENCES users (_id) ON DELETE CASCADE)

-- Table: categories_menus
CREATE TABLE "categories_menus" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, category_id INTEGER NOT NULL, menu_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(category_id) REFERENCES categories(_id) ON DELETE CASCADE, FOREIGN KEY(menu_id) REFERENCES menus(_id) ON DELETE CASCADE)

-- Table: options
CREATE TABLE "options" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"option_name" VARCHAR NOT NULL ,"option_value" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL)
INSERT INTO options (_id, option_name, option_value, created_at, updated_at) VALUES (1, 'printer_baudrate', '9600', '2013-04-13', '2013-04-13');
INSERT INTO options (_id, option_name, option_value, created_at, updated_at) VALUES (2, 'printer_databits', '8', '2013-04-13', '2013-04-13');
INSERT INTO options (_id, option_name, option_value, created_at, updated_at) VALUES (3, 'printer_stopbits', '1', '2013-04-13', '2013-04-13');
INSERT INTO options (_id, option_name, option_value, created_at, updated_at) VALUES (4, 'printer_parity', '0', '2013-04-13', '2013-04-13');

-- Table: promotions
CREATE TABLE "promotions" ("_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "percentage_discount" VARCHAR NOT NULL , "start_date" DATETIME NOT NULL , "end_date" DATETIME NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE)

-- Table: order_detail
CREATE TABLE order_detail (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, order_id INTEGER NOT NULL, menu_item_categories_id INTEGER NOT NULL, addon_id INTEGER, quantity INTEGER NOT NULL DEFAULT 1, price DOUBLE NOT NULL, subtotal DOUBLE NOT NULL, order_detail_status_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY (order_id) REFERENCES dining_tables (_id) ON DELETE CASCADE, FOREIGN KEY (menu_item_categories_id) REFERENCES menu_items_categories (_id) ON DELETE CASCADE, FOREIGN KEY (addon_id) REFERENCES menu_item_addons (_id) ON DELETE CASCADE, FOREIGN KEY (order_detail_status_id) REFERENCES order_status (_id) ON DELETE CASCADE)

-- Table: dining_table_status
CREATE TABLE "dining_table_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL)
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (1, 'abierta', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (2, 'esperando realizar pedido', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (3, 'pedido realizado', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (4, 'pedido entregado', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (5, 'pedido de cierre de cuenta', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (6, 'cierre de cuenta entregado', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (7, 'pago realizado', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (8, 'cobro realizado', '2013-01-09', '2013-01-09');
INSERT INTO dining_table_status (_id, description, created_at, updated_at) VALUES (9, 'cerrada', '2013-01-09', '2013-01-09');

-- Table: menus
CREATE TABLE "menus" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL,"interval_from" VARCHAR, "interval_to" VARCHAR, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL)

-- Table: menu_item_thumbs
CREATE TABLE "menu_item_thumbs" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"menu_item_id" INTEGER NOT NULL ,"img_small" VARCHAR ,"img_medium" VARCHAR ,"img_large" VARCHAR ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE)

-- Table: menu_item_videos
CREATE TABLE "menu_item_videos" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "menu_item_id" INTEGER NOT NULL , "path" VARCHAR NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE)

-- Table: categories
CREATE TABLE "categories" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL)

COMMIT TRANSACTION;
