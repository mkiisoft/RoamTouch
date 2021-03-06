DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE android_metadata (locale TEXT);

DROP TABLE IF EXISTS "menus";
CREATE TABLE "menus" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL,"interval_from" VARCHAR, "interval_to" VARCHAR, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL);
INSERT INTO "menus" VALUES(1,'Menu Manana','menu_manana','08:00','11:00','2013-01-01','2013-01-01');
INSERT INTO "menus" VALUES(2,'Menu Mediodia','menu_mediodia','11:00','16:00','2013-01-02','2013-01-02');
INSERT INTO "menus" VALUES(3,'Menu Tarde','menu_tarde ','16:00','21:00','2013-01-04','2013-01-04');
INSERT INTO "menus" VALUES(4,'Menu Noche','menu_noche','21:00','00:00','2013-01-07','2013-01-07');

DROP TABLE IF EXISTS "categories";
CREATE TABLE "categories" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL);
INSERT INTO "categories" VALUES(1,'Cafe','cafe','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(2,'Infusiones','infusiones','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(3,'Jugos','jugos','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(4,'Licuados','licuados','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(5,'Tostados','tostados','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(6,'Tortas','tortas','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(7,'Entradas','entradas','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(8,'Ensaladas','ensaladas','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(9,'Sopas','sopas','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(10,'Pastas','pastas','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(11,'Pescados','pescados','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(12,'Sandwiches','sandwiches','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(13,'Carnes','carnes','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(14,'Postres','postres','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(15,'Bebidas sin Alcohol','bebidas_sin_alcohol','2013-01-01','2013-01-01');
INSERT INTO "categories" VALUES(16,'Bebidas con Alcohol','bebidas_con_alcohol','2013-01-01','2013-01-01');

DROP TABLE IF EXISTS "categories_menus";
CREATE TABLE "categories_menus" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, category_id INTEGER NOT NULL, menu_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(category_id) REFERENCES categories(_id) ON DELETE CASCADE, FOREIGN KEY(menu_id) REFERENCES menus(_id) ON DELETE CASCADE);
INSERT INTO "categories_menus" VALUES(1,1,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(2,2,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(3,3,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(4,4,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(5,5,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(6,6,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(7,15,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(8,16,1,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(9,7,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(10,8,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(11,9,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(12,10,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(13,11,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(14,12,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(15,13,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(16,14,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(17,15,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(18,16,2,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(19,1,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(20,2,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(21,3,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(22,4,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(23,5,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(24,6,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(25,15,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(26,16,3,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(27,7,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(28,8,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(29,9,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(30,10,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(31,11,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(32,12,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(33,13,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(34,14,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(35,15,4,'2013-02-15','2013-02-15');
INSERT INTO "categories_menus" VALUES(36,16,4,'2013-02-15','2013-02-15');

DROP TABLE IF EXISTS "menu_item";
CREATE TABLE "menu_item" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL, description TEXT, price DOUBLE NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL );
INSERT INTO "menu_item" VALUES(1,'Cafe con Leche',NULL,15,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(2,'Cortado',NULL,15,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(3,'Te',NULL,13,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(4,'Jugo de Limon',NULL,18,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(5,'Jugo de Naranja',NULL,18,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(6,'Jugo de Pomelo',NULL,18,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(7,'Licuado de Frutilla',NULL,20,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(8,'Licuado de Durazno',NULL,20,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(9,'Licuado de Banana',NULL,20,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(10,'Tostado Mixto',NULL,23,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(11,'Tostado de Ternera',NULL,25,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(12,'Selva Negra',NULL,17,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(13,'Hojaldre',NULL,16,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(14,'Agua sin Gas',NULL,12,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(15,'Agua con Gas',NULL,13,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(16,'Gaseosa',NULL,15,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(17,'Vino',NULL,48,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(18,'Champagne',NULL,100,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(19,'Rabas',NULL,40,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(20,'Brochettes',NULL,30,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(21,'Ensalada Cesar',NULL,28,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(22,'Sopa de Verduras',NULL,24,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(23,'Ravioles',NULL,38,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(24,'Tallarines',NULL,35,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(25,'Merluza',NULL,27,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(26,'Milanesa',NULL,25,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(27,'Ternera',NULL,30,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(28,'Hamburguesa',NULL,22,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(29,'Lomo al Verdeo',NULL,75,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(30,'Milanesa a la Napolitana',NULL,35,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(31,'Suprema a la Napolitana',NULL,32,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(32,'Helado',NULL,30,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(33,'Ensalada de Frutas',NULL,25,'2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "menu_item_addons";
CREATE TABLE "menu_item_addons" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL , "menu_item_id" INTEGER NOT NULL ,  "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "menu_item_thumbs";
CREATE TABLE "menu_item_thumbs" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"menu_item_id" INTEGER NOT NULL ,"img_small" VARCHAR ,"img_medium" VARCHAR ,"img_large" VARCHAR ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "menu_item_videos";
CREATE TABLE "menu_item_videos" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "menu_item_id" INTEGER NOT NULL , "name" VARCHAR NOT NULL , "path" VARCHAR NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "menu_items_categories";
CREATE TABLE "menu_items_categories" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, menu_item_id INTEGER NOT NULL, categories_menus_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE, FOREIGN KEY(categories_menus_id) REFERENCES categories_menus(_id) ON DELETE CASCADE);
INSERT INTO "menu_items_categories" VALUES(1,1,1,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(2,1,19,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(3,2,1,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(4,2,19,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(5,3,2,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(6,3,20,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(7,4,3,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(8,5,3,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(9,6,3,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(10,4,21,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(11,5,21,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(12,6,21,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(13,7,4,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(14,8,4,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(15,9,4,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(16,7,22,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(17,8,22,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(18,9,22,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(19,10,5,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(20,11,5,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(21,10,23,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(22,11,23,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(23,12,6,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(24,13,6,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(25,12,24,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(26,13,24,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(27,14,7,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(28,14,17,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(29,14,25,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(30,14,35,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(31,15,7,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(32,15,17,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(33,15,25,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(34,15,35,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(35,16,7,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(36,16,17,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(37,16,25,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(38,16,35,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(39,17,8,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(40,17,18,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(41,17,26,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(42,17,36,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(43,18,8,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(44,18,18,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(45,18,26,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(46,18,36,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(47,19,9,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(48,19,27,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(49,20,9,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(50,20,27,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(51,21,10,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(52,21,28,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(53,22,11,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(54,22,29,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(55,23,12,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(56,23,30,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(57,24,12,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(58,24,30,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(59,25,13,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(60,25,31,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(61,26,14,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(62,26,32,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(63,27,14,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(64,27,32,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(65,28,14,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(66,28,32,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(67,29,15,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(68,29,33,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(69,30,15,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(70,30,33,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(71,31,15,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(72,31,33,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(73,32,16,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(74,32,34,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(75,33,16,'2013-02-15','2013-02-15');
INSERT INTO "menu_items_categories" VALUES(76,33,34,'2013-02-15','2013-02-15');

DROP TABLE IF EXISTS "dining_table_status";
CREATE TABLE "dining_table_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL);
INSERT INTO "dining_table_status" VALUES(1,'abierta','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(2,'esperando realizar pedido','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(3,'pedido realizado','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(4,'pedido entregado','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(5,'pedido de cierre de cuenta','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(6,'cierre de cuenta entregado','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(7,'pago realizado','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(8,'cobro realizado','2013-01-09','2013-01-09');
INSERT INTO "dining_table_status" VALUES(9,'cerrada','2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "dining_tables";
CREATE TABLE "dining_tables" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "dining_table_status_id" INTEGER NOT NULL  , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(dining_table_status_id) REFERENCES dining_table_status(_id) ON DELETE CASCADE);
INSERT INTO "dining_tables" VALUES(1,'Mesa 1',2,'2013-01-09','2013-01-09');
INSERT INTO "dining_tables" VALUES(2,'Mesa 2',2,'2013-01-09','2013-01-09');
INSERT INTO "dining_tables" VALUES(3,'Mesa 3',2,'2013-01-09','2013-01-09');
INSERT INTO "dining_tables" VALUES(4,'Mesa 4',2,'2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "order_status";
CREATE TABLE "order_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL);
INSERT INTO "order_status" VALUES(1,'enviado','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(2,'procesando','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(3,'terminado','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(4,'cancelado','2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL , "date" DATETIME NOT NULL , "dining_table_id" INTEGER NOT NULL, "observation" TEXT,  "order_status_id" INTEGER NOT NULL, "total" DOUBLE NOT NULL,"user_id" INTEGER NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(dining_table_id) REFERENCES dining_tables(_id) ON DELETE CASCADE, FOREIGN KEY(order_status_id) REFERENCES order_status(_id) ON DELETE CASCADE, FOREIGN KEY(user_id) REFERENCES users(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "order_detail";
CREATE TABLE "order_detail" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "order_id" INTEGER NOT NULL , "menu_item_categories_id" INTEGER NOT NULL , "addon_id" INTEGER,"quantity" INTEGER NOT NULL DEFAULT 1,  "price" DOUBLE NOT NULL ,"subtotal" DOUBLE NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(order_id) REFERENCES orders(_id) ON DELETE CASCADE, FOREIGN KEY(menu_item_categories_id) REFERENCES menu_items_categories(_id) ON DELETE CASCADE, FOREIGN KEY(addon_id) REFERENCES menu_item_addons(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "promotions";
CREATE TABLE "promotions" ("_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "percentage_discount" VARCHAR NOT NULL , "start_date" DATETIME NOT NULL , "end_date" DATETIME NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "profiles";
CREATE TABLE "profiles" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" VARCHAR NOT NULL );
INSERT INTO "profiles" VALUES(1,'admin');
INSERT INTO "profiles" VALUES(2,'mozo');

DROP TABLE IF EXISTS "users";
CREATE TABLE "users" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"username" VARCHAR NOT NULL ,"password" VARCHAR NOT NULL , "first_name" VARCHAR NOT NULL, "last_name" VARCHAR NOT NULL, "email" VARCHAR,"status" INTEGER NOT NULL ,"profile_id" INTEGER NOT NULL ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(profile_id) REFERENCES profiles(_id) ON DELETE CASCADE);
INSERT INTO "users" VALUES(1,'admin','1234','Jose','Vigil','josevigil@roamtouch.com',0,1,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(2,'jperez','1234','Juan','Perez','juanperez@hotmail.com',0,2,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(3,'grodriguez','1234','Gonzalo','Rodriguez','grodriguez@hotmail.com',0,2,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(4,'fcastillo','1234','Fabian','Castillo','fcastillo@hotmail.com',0,2,'2013-02-05','2013-02-05');


