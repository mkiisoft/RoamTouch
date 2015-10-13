DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE android_metadata (locale TEXT);

DROP TABLE IF EXISTS "menu";
CREATE TABLE "menu" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL );
INSERT INTO "menu" VALUES(1,'Menú Mañana','menu_manana','2013-01-01','2013-01-01');
INSERT INTO "menu" VALUES(2,'Menú Mediodía','menu_mediodia','2013-01-01','2013-01-01');
INSERT INTO "menu" VALUES(3,'Menú Tarde','menu_tarde ','2013-01-01','2013-01-01');
INSERT INTO "menu" VALUES(4,'Menú Noche','menu_noche','2013-01-01','2013-01-01');

DROP TABLE IF EXISTS "categories";
CREATE TABLE "categories" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL , "menu_id" INTEGER NOT NULL, "folder_name" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_id) REFERENCES menu(_id));
INSERT INTO "categories" VALUES(2,'Entrada',2,'entrada','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(3,'Plato Principal',2,'plato_principal','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(4,'Postre',2,'postre','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(5,'Bebidas sin Alcohol',2,'bebidas_sin_alcohol','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(6,'Bebidas con Alcohol',2,'bebidas_con_alcohol','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(7,'Entrada',4,'entrada','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(8,'Plato Principal',4,'plato_principal','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(9,'Postre',4,'postre','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(10,'Bebidas sin Alcohol',4,'bebidas_sin_alcohol','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(11,'Bebidas con Alcohol',4,'bebidas_con_alcohol','2013-01-01','2013-01-01');

DROP TABLE IF EXISTS "menu_item";
CREATE TABLE "menu_item" (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL, description TEXT, price DOUBLE NOT NULL, category_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(category_id) REFERENCES categories(_id));
INSERT INTO "menu_item" VALUES(1,'Ravioles con salsa',NULL,35,3,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(2,'Ravioles con salsa',NULL,35,8,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(3,'Quilmes 1 Litro',NULL,'25,50',10,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(4,'Quilmes 1 Litro',NULL,'25,50',10,'2013-01-01','2013-01-01');

DROP TABLE IF EXISTS "menu_item_thumbs";
CREATE TABLE "menu_item_thumbs" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"menu_item_id" INTEGER NOT NULL ,"img_small" VARCHAR ,"img_medium" VARCHAR ,"img_large" VARCHAR ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));
DROP TABLE IF EXISTS "menu_item_videos";
CREATE TABLE "menu_item_videos" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "menu_item_id" INTEGER NOT NULL , "name" VARCHAR NOT NULL , "path" VARCHAR NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));

DROP TABLE IF EXISTS "promotions";
CREATE TABLE "promotions" ("_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "percentage_discount" VARCHAR NOT NULL , "start_date" DATETIME NOT NULL , "end_date" DATETIME NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));

DROP TABLE IF EXISTS "request_detail";
CREATE TABLE "request_detail" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "request_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "quantity" INTEGER NOT NULL DEFAULT 1, "price" DOUBLE NOT NULL , "addons" TEXT, "subtotal" DOUBLE NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(request_id) REFERENCES requests(_id), FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));

DROP TABLE IF EXISTS "requests";
CREATE TABLE "requests" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "fecha" DATETIME NOT NULL , "table_id" INTEGER NOT NULL , "total" DOUBLE NOT NULL , "state" INTEGER NOT NULL , "observation" TEXT NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(table_id) REFERENCES tables(_id));

DROP TABLE IF EXISTS "tables";
CREATE TABLE "tables" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "state" INTEGER NOT NULL , "observation" TEXT NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL );

DROP TABLE IF EXISTS "users";
CREATE TABLE "users" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"username" VARCHAR NOT NULL ,"password" VARCHAR NOT NULL ,"state" INTEGER NOT NULL ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL );
