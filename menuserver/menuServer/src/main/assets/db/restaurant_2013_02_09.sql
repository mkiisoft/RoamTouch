DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE android_metadata (locale TEXT);

DROP TABLE IF EXISTS "menus";
CREATE TABLE "menus" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL,"interval_from" VARCHAR, "interval_to" VARCHAR, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL);
INSERT INTO "menus" VALUES(1,'Menu Manana','menu_manana','08:00','11:00','2013-01-01','2013-01-01');
INSERT INTO "menus" VALUES(2,'Menu Mediodia','menu_mediodia','11:00','16:00','2013-01-02','2013-01-02');
INSERT INTO "menus" VALUES(3,'Menu Tarde','menu_tarde ','16:00','21:00','2013-01-04','2013-01-04');
INSERT INTO "menus" VALUES(4,'Menu Noche','menu_noche','21:00','00:00','2013-01-07','2013-01-07');

DROP TABLE IF EXISTS "categories";
CREATE TABLE "categories" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL , "menu_id" INTEGER NOT NULL, "folder_name" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_id) REFERENCES menus(_id));
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
INSERT INTO "categories" VALUES(18,'Cafe',1,'cafe','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(19,'Infusiones',1,'infusiones','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(20,'Licuados',1,'licuados','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(21,'Sandwiches',1,'sandwiches','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(22,'Tortas',1,'tortas','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(23,'Cafe',3,'cafe','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(24,'Infusiones',3,'infusiones','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(25,'Licuados',3,'licuados','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(26,'Sandwiches',3,'sandwiches','2013-01-09','2013-01-09');
INSERT INTO "categories" VALUES(27,'Tortas',3,'tortas','2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "menu_item";
CREATE TABLE "menu_item" (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL, description TEXT, price DOUBLE NOT NULL, category_id INTEGER NOT NULL, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL, FOREIGN KEY(category_id) REFERENCES categories(_id));
INSERT INTO "menu_item" VALUES(1,'Cafe con Leche',NULL,15,18,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(2,'Cortado',NULL,13,18,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(3,'Te',NULL,10,19,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(4,'Banana',NULL,18,20,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(5,'Durazno',NULL,18,20,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(6,'Frutilla',NULL,18,20,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(7,'Jamon y Queso',NULL,25,21,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(8,'Selva Negra',NULL,30,22,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(9,'Hojaldre',NULL,30,22,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(10,'Café con Leche',NULL,15,23,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(11,'Cortado',NULL,13,23,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(12,'Te',NULL,10,24,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(13,'Banana',NULL,18,25,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(14,'Durazno',NULL,18,25,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(15,'Frutilla',NULL,18,25,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(16,'Jamen y Queso',NULL,25,26,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(17,'Selva Negra',NULL,30,27,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(18,'Hojaldre',NULL,30,27,'2013-01-01','2013-01-01');
INSERT INTO "menu_item" VALUES(19,'Rabas',NULL,40,2,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(20,'Brochettes',NULL,40,2,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(21,'Lomo al Verdeo',NULL,75,3,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(22,'Sushi',NULL,95,3,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(23,'Helado',NULL,30,4,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(24,'Ensalada de Frutas',NULL,25,4,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(25,'Agua sin gas',NULL,14,5,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(26,'Agua con gas',NULL,14,5,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(27,'Gaseosa',NULL,15,5,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(28,'Quilmes 1L',NULL,50,6,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(29,'Vino',NULL,70,6,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(30,'Champagne',NULL,100,6,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(31,'Rabas',NULL,40,7,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(32,'Brochettes',NULL,40,7,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(33,'Lomo al Verdeo',NULL,75,8,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(34,'Sushi',NULL,95,8,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(35,'Helado',NULL,30,9,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(36,'Ensalada de Frutas',NULL,25,9,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(37,'Agua sin gas',NULL,14,10,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(38,'Agua con gas',NULL,14,10,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(39,'Gaseosa',NULL,15,10,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(40,'Quilmes 1L',NULL,50,11,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(41,'Vino',NULL,70,11,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(42,'Champagne',NULL,100,11,'2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "menu_item_thumbs";
CREATE TABLE "menu_item_thumbs" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"menu_item_id" INTEGER NOT NULL ,"img_small" VARCHAR ,"img_medium" VARCHAR ,"img_large" VARCHAR ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));
INSERT INTO "menu_item_thumbs" VALUES(1,1,'/media/menus/menu_manana/categories/cafe/cafe_con_leche.jpg','/media/menus/menu_manana/categories/cafe/cafe_con_leche.jpg','/media/menus/menu_manana/categories/cafe/cafe_con_leche.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(2,2,'/media/menus/menu_manana/categories/cafe/cortado.jpg','/media/menus/menu_manana/categories/cafe/cortado.jpg','/media/menus/menu_manana/categories/cafe/cortado.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(3,3,'/media/menus/menu_manana/categories/infusiones/te.jpg','/media/menus/menu_manana/categories/infusiones/te.jpg','/media/menus/menu_manana/categories/infusiones/te.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(4,4,'/media/menus/menu_manana/categories/licuados/licuado_de_banana.jpg','/media/menus/menu_manana/categories/licuados/licuado_de_banana.jpg','/media/menus/menu_manana/categories/licuados/licuado_de_banana.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(5,5,'/media/menus/menu_manana/categories/licuados/licuado_de_durazno.jpg','/media/menus/menu_manana/categories/licuados/licuado_de_durazno.jpg','/media/menus/menu_manana/categories/licuados/licuado_de_durazno.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(6,6,'/media/menus/menu_manana/categories/licuados/licuado_de_frutilla.jpg','/media/menus/menu_manana/categories/licuados/licuado_de_frutilla.jpg','/media/menus/menu_manana/categories/licuados/licuado_de_frutilla.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(7,7,'/media/menus/menu_manana/categories/sandwiches/tostado_de_jamon_y_queso.jpg','/media/menus/menu_manana/categories/sandwiches/tostado_de_jamon_y_queso.jpg','/media/menus/menu_manana/categories/sandwiches/tostado_de_jamon_y_queso.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(8,8,'/media/menus/menu_manana/categories/tortas/selva_negra.jpg','/media/menus/menu_manana/categories/tortas/selva_negra.jpg','/media/menus/menu_manana/categories/tortas/selva_negra.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(9,9,'/media/menus/menu_manana/categories/tortas/hojaldre.jpg','/media/menus/menu_manana/categories/tortas/hojaldre.jpg','/media/menus/menu_manana/categories/tortas/hojaldre.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(10,10,'/media/menus/menu_tarde/categories/cafe/cafe_con_leche.jpg','/media/menus/menu_tarde/categories/cafe/cafe_con_leche.jpg','/media/menus/menu_tarde/categories/cafe/cafe_con_leche.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(11,11,'/media/menus/menu_tarde/categories/cafe/cortado.jpg','/media/menus/menu_tarde/categories/cafe/cortado.jpg','/media/menus/menu_tarde/categories/cafe/cortado.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(12,12,'/media/menus/menu_tarde/categories/infusiones/te.jpg','/media/menus/menu_tarde/categories/infusiones/te.jpg','/media/menus/menu_tarde/categories/infusiones/te.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(13,13,'/media/menus/menu_tarde/categories/licuados/licuado_de_banana.jpg','/media/menus/menu_tarde/categories/licuados/licuado_de_banana.jpg','/media/menus/menu_tarde/categories/licuados/licuado_de_banana.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(14,14,'/media/menus/menu_tarde/categories/licuados/licuado_de_durazno.jpg','/media/menus/menu_tarde/categories/licuados/licuado_de_durazno.jpg','/media/menus/menu_tarde/categories/licuados/licuado_de_durazno.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(15,15,'/media/menus/menu_tarde/categories/licuados/licuado_de_frutilla.jpg','/media/menus/menu_tarde/categories/licuados/licuado_de_frutilla.jpg','/media/menus/menu_tarde/categories/licuados/licuado_de_frutilla.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(16,16,'/media/menus/menu_tarde/categories/sandwiches/tostado_de_jamon_y_queso.jpg','/media/menus/menu_tarde/categories/sandwiches/tostado_de_jamon_y_queso.jpg','/media/menus/menu_tarde/categories/sandwiches/tostado_de_jamon_y_queso.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(17,17,'/media/menus/menu_tarde/categories/tortas/selva_negra.jpg','/media/menus/menu_tarde/categories/tortas/selva_negra.jpg','/media/menus/menu_tarde/categories/tortas/selva_negra.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(18,18,'/media/menus/menu_tarde/categories/tortas/hojaldre.jpg','/media/menus/menu_tarde/categories/tortas/hojaldre.jpg','/media/menus/menu_tarde/categories/tortas/hojaldre.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(19,19,'/media/menus/menu_mediodia/categories/entrada/rabas.jpg','/media/menus/menu_mediodia/categories/entrada/rabas.jpg','/media/menus/menu_mediodia/categories/entrada/rabas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(20,20,'/media/menus/menu_mediodia/categories/entrada/brochettes.jpg','/media/menus/menu_mediodia/categories/entrada/brochettes.jpg','/media/menus/menu_mediodia/categories/entrada/brochettes.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(21,21,'/media/menus/menu_mediodia/categories/plato_principal/lomo_al_verdeo.jpg','/media/menus/menu_mediodia/categories/plato_principal/lomo_al_verdeo.jpg','/media/menus/menu_mediodia/categories/plato_principal/lomo_al_verdeo.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(22,22,'/media/menus/menu_mediodia/categories/plato_principal/sushi.jpg','/media/menus/menu_mediodia/categories/plato_principal/sushi.jpg','/media/menus/menu_mediodia/categories/plato_principal/sushi.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(23,23,'/media/menus/menu_mediodia/categories/postre/helado.jpg','/media/menus/menu_mediodia/categories/postre/helado.jpg','/media/menus/menu_mediodia/categories/postre/helado.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(24,24,'/media/menus/menu_mediodia/categories/postre/ensalada_de_frutas.jpg','/media/menus/menu_mediodia/categories/postre/ensalada_de_frutas.jpg','/media/menus/menu_mediodia/categories/postre/ensalada_de_frutas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(25,25,'/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/agua_sin_gas.jpg','/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/agua_sin_gas.jpg','/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/agua_sin_gas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(26,26,'/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/agua_con_gas.jpg','/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/agua_con_gas.jpg','/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/agua_con_gas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(27,27,'/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/gaseosa.jpg','/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/gaseosa.jpg','/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/gaseosa.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(28,28,'/media/menus/menu_mediodia/categories/bebidas_con_alcohol/quilmes_1L.jpg','/media/menus/menu_mediodia/categories/bebidas_con_alcohol/quilmes_1L.jpg','/media/menus/menu_mediodia/categories/bebidas_con_alcohol/quilmes_1L.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(29,29,'/media/menus/menu_mediodia/categories/bebidas_con_alcohol/vino.jpg','/media/menus/menu_mediodia/categories/bebidas_con_alcohol/vino.jpg','/media/menus/menu_mediodia/categories/bebidas_con_alcohol/vino.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(30,30,'/media/menus/menu_noche/categories/bebidas_con_alcohol/champagne.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/champagne.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/champagne.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(31,31,'/media/menus/menu_noche/categories/entrada/rabas.jpg','/media/menus/menu_noche/categories/entrada/rabas.jpg','/media/menus/menu_noche/categories/entrada/rabas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(32,32,'/media/menus/menu_noche/categories/entrada/brochettes.jpg','/media/menus/menu_noche/categories/entrada/brochettes.jpg','/media/menus/menu_noche/categories/entrada/brochettes.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(33,33,'/media/menus/menu_noche/categories/plato_principal/lomo_al_verdeo.jpg','/media/menus/menu_noche/categories/plato_principal/lomo_al_verdeo.jpg','/media/menus/menu_noche/categories/plato_principal/lomo_al_verdeo.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(34,34,'/media/menus/menu_noche/categories/plato_principal/sushi.jpg','/media/menus/menu_noche/categories/plato_principal/sushi.jpg','/media/menus/menu_noche/categories/plato_principal/sushi.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(35,35,'/media/menus/menu_noche/categories/postre/helado.jpg','/media/menus/menu_noche/categories/postre/helado.jpg','/media/menus/menu_noche/categories/postre/helado.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(36,36,'/media/menus/menu_noche/categories/postre/ensalada_de_frutas.jpg','/media/menus/menu_noche/categories/postre/ensalada_de_frutas.jpg','/media/menus/menu_noche/categories/postre/ensalada_de_frutas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(37,37,'/media/menus/menu_noche/categories/bebidas_sin_alcohol/agua_sin_gas.jpg','/media/menus/menu_noche/categories/bebidas_sin_alcohol/agua_sin_gas.jpg','/media/menus/menu_noche/categories/bebidas_sin_alcohol/agua_sin_gas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(38,38,'/media/menus/menu_noche/categories/bebidas_sin_alcohol/agua_con_gas.jpg','/media/menus/menu_noche/categories/bebidas_sin_alcohol/agua_con_gas.jpg','/media/menus/menu_noche/categories/bebidas_sin_alcohol/agua_con_gas.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(39,39,'/media/menus/menu_noche/categories/bebidas_sin_alcohol/gaseosa.jpg','/media/menus/menu_noche/categories/bebidas_sin_alcohol/gaseosa.jpg','/media/menus/menu_noche/categories/bebidas_sin_alcohol/gaseosa.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(40,40,'/media/menus/menu_noche/categories/bebidas_con_alcohol/quilmes_1L.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/quilmes_1L.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/quilmes_1L.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(41,41,'/media/menus/menu_noche/categories/bebidas_con_alcohol/vino.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/vino.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/vino.jpg','2013-01-09','2013-01-09');
INSERT INTO "menu_item_thumbs" VALUES(42,42,'/media/menus/menu_noche/categories/bebidas_con_alcohol/champagne.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/champagne.jpg','/media/menus/menu_noche/categories/bebidas_con_alcohol/champagne.jpg','2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "menu_item_videos";
CREATE TABLE "menu_item_videos" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "menu_item_id" INTEGER NOT NULL , "name" VARCHAR NOT NULL , "path" VARCHAR NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));

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
CREATE TABLE "dining_tables" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "dining_table_status_id" INTEGER NOT NULL  , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(dining_table_status_id) REFERENCES dining_table_status(_id));
INSERT INTO "dining_tables" VALUES(1,'1',1,'2013-01-09','2013-01-09');
INSERT INTO "dining_tables" VALUES(2,'2',2,'2013-01-09','2013-01-09');
INSERT INTO "dining_tables" VALUES(3,'3',3,'2013-01-09','2013-01-09');
INSERT INTO "dining_tables" VALUES(4,'4',4,'2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "profiles";
CREATE TABLE "profiles" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" VARCHAR NOT NULL );
INSERT INTO "profiles" VALUES(1,'admin');
INSERT INTO "profiles" VALUES(2,'mozo');

DROP TABLE IF EXISTS "promotions";
CREATE TABLE "promotions" ("_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "percentage_discount" VARCHAR NOT NULL , "start_date" DATETIME NOT NULL , "end_date" DATETIME NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));

DROP TABLE IF EXISTS "users";
CREATE TABLE "users" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"username" VARCHAR NOT NULL ,"password" VARCHAR NOT NULL , "first_name" VARCHAR NOT NULL, "last_name" VARCHAR NOT NULL, "email" VARCHAR,"status" INTEGER NOT NULL ,"profile_id" INTEGER NOT NULL ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(profile_id) REFERENCES profiles(_id) );
INSERT INTO "users" VALUES(1,'admin','1234','Jose','Vigil','josevigil@roamtouch.com',0,1,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(2,'jperez','1234','Juan','Perez','juanperez@hotmail.com',0,2,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(3,'grodriguez','1234','Gonzalo','Rodriguez','grodriguez@hotmail.com',0,2,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(4,'fcastillo','1234','Fabian','Castillo','fcastillo@hotmail.com',0,2,'2013-02-05','2013-02-05');

DROP TABLE IF EXISTS "order_status";
CREATE TABLE "order_status" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "description" TEXT NOT NULL, "created_at" TIMESTAMP NOT NULL, "updated_at" DATATIME NOT NULL);
INSERT INTO "order_status" VALUES(1,'enviado','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(2,'procesando','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(3,'terminado','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(4,'cancelado','2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL , "date" DATETIME NOT NULL , "dining_table_id" INTEGER NOT NULL, "observation" TEXT NOT NULL,  "order_status_id" INTEGER NOT NULL, "total" DOUBLE NOT NULL,"user_id" INTEGER NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(dining_table_id) REFERENCES dining_tables(_id), FOREIGN KEY(order_status_id) REFERENCES order_status(_id), FOREIGN KEY(user_id) REFERENCES users(_id));
INSERT INTO "orders" VALUES(1,'2013-01-09',1,'ojo con los tiempos',1,100,2,'2013-01-09','2013-01-09');
INSERT INTO "orders" VALUES(2,'2013-01-09',2,'ninguna',2,130,3,'2013-01-09','2013-01-09');
INSERT INTO "orders" VALUES(3,'2013-01-09',3,'paga con debito',3,55,2,'2013-01-09','2013-01-09');
INSERT INTO "orders" VALUES(4,'2013-01-09',4,'persona celiaca',4,100,4,'2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "order_detail";
CREATE TABLE "order_detail" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "order_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL ,   "addons" TEXT,"quantity" INTEGER NOT NULL DEFAULT 1,  "price" DOUBLE NOT NULL ,"subtotal" DOUBLE NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(order_id) REFERENCES orders(_id), FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id));
INSERT INTO "order_detail" VALUES(1,1,1,NULL,2,15,30,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(2,1,9,NULL,1,30,30,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(3,1,5,NULL,1,20,20,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(4,1,6,NULL,1,20,20,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(5,2,19,NULL,1,40,40,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(6,2,20,NULL,1,40,40,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(7,2,28,NULL,1,50,50,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(8,3,23,NULL,1,30,30,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(9,3,24,NULL,1,25,25,'2013-01-01','2013-01-01');
INSERT INTO "order_detail" VALUES(10,4,30,NULL,1,100,30,'2013-01-01','2013-01-01');