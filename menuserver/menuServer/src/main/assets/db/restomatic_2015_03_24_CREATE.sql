--
-- File generated with SQLiteStudio v3.0.2 on Tue Mar 24 13:42:09 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: categories_menus
CREATE TABLE "categories_menus" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, category_id INTEGER NOT NULL, menu_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(category_id) REFERENCES categories(_id) ON DELETE CASCADE, FOREIGN KEY(menu_id) REFERENCES menus(_id) ON DELETE CASCADE);

-- Table: order_status
CREATE TABLE "order_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL);


-- Table: order_detail
CREATE TABLE order_detail (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, order_id INTEGER NOT NULL, menu_item_categories_id INTEGER NOT NULL, addon_id INTEGER, quantity INTEGER NOT NULL DEFAULT 1, price DOUBLE NOT NULL, subtotal DOUBLE NOT NULL, order_detail_status_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY (order_id) REFERENCES dining_tables (_id) ON DELETE CASCADE, FOREIGN KEY (menu_item_categories_id) REFERENCES menu_items_categories (_id) ON DELETE CASCADE, FOREIGN KEY (addon_id) REFERENCES menu_item_addons (_id) ON DELETE CASCADE, FOREIGN KEY (order_detail_status_id) REFERENCES order_status (_id) ON DELETE CASCADE);

-- Table: menus
CREATE TABLE "menus" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL,"interval_from" VARCHAR, "interval_to" VARCHAR, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL);

-- Table: options
CREATE TABLE "options" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"option_name" VARCHAR NOT NULL ,"option_value" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL);

-- Table: users
CREATE TABLE "users" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"username" VARCHAR NOT NULL ,"password" VARCHAR NOT NULL , "first_name" VARCHAR NOT NULL, "last_name" VARCHAR NOT NULL, "email" VARCHAR,"status" INTEGER NOT NULL ,"profile_id" INTEGER NOT NULL ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(profile_id) REFERENCES profiles(_id) ON DELETE CASCADE);

-- Table: promotions
CREATE TABLE "promotions" ("_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "percentage_discount" VARCHAR NOT NULL , "start_date" DATETIME NOT NULL , "end_date" DATETIME NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

-- Table: dining_table_status
CREATE TABLE "dining_table_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL);

-- Table: categories
CREATE TABLE "categories" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL);

-- Table: dining_tables
CREATE TABLE dining_tables (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL, blink INTEGER NOT NULL, dining_table_status_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY (dining_table_status_id) REFERENCES dining_table_status (_id) ON DELETE CASCADE);

-- Table: profiles
CREATE TABLE "profiles" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" VARCHAR NOT NULL );


-- Table: menu_item_thumbs
CREATE TABLE "menu_item_thumbs" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"menu_item_id" INTEGER NOT NULL ,"img_small" VARCHAR ,"img_medium" VARCHAR ,"img_large" VARCHAR ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

-- Table: menu_item_videos
CREATE TABLE "menu_item_videos" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "menu_item_id" INTEGER NOT NULL , "path" VARCHAR NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

-- Table: orders
CREATE TABLE "orders" (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date DATETIME NOT NULL, dining_table_id INTEGER NOT NULL, observation TEXT, order_status_id INTEGER NOT NULL, total DOUBLE NOT NULL, user_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY (dining_table_id) REFERENCES dining_tables (_id) ON DELETE CASCADE, FOREIGN KEY (order_status_id) REFERENCES order_status (_id) ON DELETE CASCADE, FOREIGN KEY (user_id) REFERENCES users (_id) ON DELETE CASCADE);

-- Table: android_metadata
CREATE TABLE "android_metadata" (locale TEXT);


-- Table: menu_item
CREATE TABLE "menu_item" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL, description TEXT, price DOUBLE NOT NULL, order_count NULL DEFAULT 0, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL , code);

-- Table: menu_items_categories
CREATE TABLE "menu_items_categories" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, menu_item_id INTEGER NOT NULL, categories_menus_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE, FOREIGN KEY(categories_menus_id) REFERENCES categories_menus(_id) ON DELETE CASCADE);

-- Table: menu_item_addons
CREATE TABLE "menu_item_addons" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL , "menu_item_id" INTEGER NOT NULL ,  "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

COMMIT TRANSACTION;
