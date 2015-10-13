DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE android_metadata (locale TEXT);

DROP TABLE IF EXISTS "menus";
CREATE TABLE "menus" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL, "folder_name" TEXT NOT NULL,"interval_from" VARCHAR, "interval_to" VARCHAR, "created_at" TIMESTAMP NOT NULL, "updated_at" DATETIME NOT NULL);
INSERT INTO "menus" VALUES(1,'Menu Manana','menu_manana','08:00','11:00','2013-01-01','2013-01-01');
INSERT INTO "menus" VALUES(2,'Menu Mediodia','menu_mediodia','11:00','16:00','2013-01-02','2013-01-02');
INSERT INTO "menus" VALUES(3,'Menu Tarde','menu_tarde','16:00','21:00','2013-01-04','2013-01-04');
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
CREATE TABLE "menu_item" ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL, description TEXT, price DOUBLE NOT NULL, order_count NULL DEFAULT 0, created_at TIMESTAMP NOT NULL, updated_at DATETIME NOT NULL );
INSERT INTO "menu_item" VALUES(1,'Cafe con Leche','El café con leche: café (como infusión) y leche, con una proporción que varía de acuerdo a las costumbres locales, pero ronda la mezcla por partes iguales. Generalmente, el término café con leche lleva implícito también el tamaño de la taza que se usa, la cual suele ser grande, es decir entre 200 y 250 ml.',15,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(2,'Cortado','Un cortado es un café expreso con una pequeña cantidad de leche caliente para reducir la amargura. Es popular en España y Portugal, así como en América latina. En Cuba y en Puerto Rico, es conocido como cortadito. En el País Vasco y Navarra en euskera se llama ebaki ("cortado").',15,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(3,'Te','El té es una infusión de las hojas y brotes de la planta del té (Camellia sinensis).1 La popularidad de esta bebida es solamente sobrepasada por el agua. Su sabor es fresco, ligeramente amargo y astringente; este gusto es agradable para mucha gente.',13,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(4,'Jugo de Limon','El jugo de limón es el líquido obtenido del endocarpio de los limones al ser exprimido (generalmente se hace con un aparato exprimidor de limones). Suele ser aproximadamente el 30% del peso del fruto. Se suele extraer de forma casera directamente de los limones (a mano o con un exprimidor), aunque existen jugos envasados o en forma de extractos (liofilización o secados) de zumo de limón.',18,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(5,'Jugo de Naranja','El jugo de naranja es un jugo de frutas en forma de líquido obtenido de exprimir el interior de la naranjas (Citrus sinensis), generalmente con un instrumento denominado exprimidor. El mayor exportador de jugo de naranja es Brasil, seguido de Estados Unidos (principalmente Florida).',18,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(6,'Jugo de Pomelo','El jugo de poemlo es un jugo de frutas en forma de líquido obtenido de exprimir el interior de los pomelos (Citrus sinensis), generalmente con un instrumento denominado exprimidor. El mayor exportador de jugo de pomelo es Brasil, seguido de Estados Unidos (principalmente Florida).',18,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(7,'Licuado de Frutilla','En la gastronomía argentina o uruguaya así como en la mexicana se conoce con el nombre de licuado a toda aquella preparación de frutas o verduras que han sido procesadas y batidas con algún líquido hasta obtener un batido.',20,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(8,'Licuado de Durazno','En la gastronomía argentina o uruguaya así como en la mexicana se conoce con el nombre de licuado a toda aquella preparación de frutas o verduras que han sido procesadas y batidas con algún líquido hasta obtener un batido.',20,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(9,'Licuado de Banana','En la gastronomía argentina o uruguaya así como en la mexicana se conoce con el nombre de licuado a toda aquella preparación de frutas o verduras que han sido procesadas y batidas con algún líquido hasta obtener un batido.',20,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(10,'Tostado Mixto','El sándwich mixto es un emparedado elaborado con jamón y queso dentro de dos rebanadas de pan de molde. Se puede servir frío o bien tostado, tras untar el pan con mayonesa.',23,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(11,'Tostado de Ternera','El sándwich de ternera es un emparedado elaborado con ternera y queso dentro de dos rebanadas de pan de molde. Se puede servir frío o bien tostado, tras untar el pan con mayonesa.',25,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(12,'Selva Negra','La torta Selva Negra, se trata de uno de los postres más conocidos de la cocina de Baden y uno de los más preciados en la cocina alemana. Es una tarta de entre 25 y 30 cm de diámetro recubierta de crema con virutas de chocolate sobre la parte superior y adornada con cerezas.',17,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(13,'Hojaldre','Torta elaborada con puro hojaldre, cuatro capas de hojaldre unidas con dulce de leche, recubiertas completamente con chocolate y decoradas con galletitas Kokoas y Setitas. Las Kokoas y las Setitas son una deliciosa combinación de crujiente galleta y auténtico chocolate blanco.',16,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(14,'Agua sin Gas','El agua mineral sin gas es agua que contiene minerales u otras sustancias disueltas que alteran su sabor o le dan un valor terapéutico. Sales, compuestos sulfurados y gases están entre las sustancias que pueden estar disueltas en el agua; esta puede ser, en ocasiones, efervescente. El agua mineral puede ser preparada o puede producirse naturalmente.',12,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(15,'Agua con Gas','El agua mineral con gas es agua que contiene minerales u otras sustancias disueltas que alteran su sabor o le dan un valor terapéutico. Sales, compuestos sulfurados y gases están entre las sustancias que pueden estar disueltas en el agua; esta puede ser, en ocasiones, efervescente. El agua mineral puede ser preparada o puede producirse naturalmente.',13,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(16,'Gaseosa','La gaseosa (también llamada refresco, bebida carbonatada, soda o cola (en Ecuador) es una bebida saborizada, efervescente (carbonatada) y sin alcohol. Estas bebidas suelen consumirse frías, para ser más refrescantes y para evitar la pérdida de dióxido de carbono, que le otorga la efervescencia. Se ofrecen diversos sabores de gaseosas, entre otros cola, naranja, lima limón, uva, cereza y ponche.',15,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(17,'Vino','El vino (del latín vinum) es una bebida obtenida de la uva (especie Vitis vinifera) mediante la fermentación alcohólica de su mosto o zumo.2 La fermentación se produce por la acción metabólica de levaduras que transforman los azúcares del fruto en alcohol etílico y gas en forma de dióxido de carbono.',48,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(18,'Champagne','El vino (del latín vinum) es una bebida obtenida de la uva (especie Vitis vinifera) mediante la fermentación alcohólica de su mosto o zumo.2 La fermentación se produce por la acción metabólica de levaduras que transforman los azúcares del fruto en alcohol etílico y gas en forma de dióxido de carbono.',100,0,'2013-02-12','2013-02-12');
INSERT INTO "menu_item" VALUES(19,'Rabas','Los calamares fritos, en ocasiones también denominados calamares a la romana o rabas en Cantabria, es un plato preparado a base de calamares fritos rebozados en harina que se puede encontrar en muchos bares y restaurantes de los países que disfrutan de la cocina mediterránea.',40,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(20,'Brochettes','En cocina, una brocheta (del francés brochette, se refiere a la comidas servidas ensartadas en un pincho (brochette). En otros países también se conoce a este platillo como chuzo o pincho.',30,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(21,'Ensalada Cesar','La Ensalada César es una ensalada de lechuga romana, trocitos de pan tostado aliñados (llamados croutons en francés, o bien picatostes en español). El inventor de esta ensalada fue el cocinero Alex Cardini, la ensalada lleva en su honor el nombre del dueño del restaurante llamado César Cardini.1 2 La ensalada es muy popular y ha llegado a internacionalizarse, siendo posible encontrarla en diversos restaurantes del mundo.',28,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(22,'Sopa de Verduras','Una sopa de verduras con todas las variedades de pimientos, sumadas a zanahorias, cebolla, coreanito, etc.',24,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(23,'Ravioles','Los raviolis (del italiano ravioli, plural del genovés raviolo, ‘plegado’), conocidos en el área rioplatense y en Perú como ravioles,1 son básicamente cuadrados de pasta replegados y rellenos, variando estos según las diferentes regiones, recetas y culturas culinarias. ',38,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(24,'Tallarines','Los tallarines son un tipo de masa (pasta) alargada, de pequeño ancho y forma achatada que integran el conjunto de las paste asciute (pastas secas) de origen italiano.',35,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(25,'Merluza','Merluza (en latín Merluccius, debido al parecido con el pez carnívoro de agua dulce) es el nombre común de varios peces marinos del orden de los gadiformes. Estos peces realizan dos tipos de migraciones: una de carácter diario, ascendiendo durante la noche a las capas superiores del mar para alimentarse y descendiendo durante el día, y otra de tipo estacional, relacionada al ciclo reproductivo de cada especie.',27,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(26,'Milanesa','La milanesa es un filete fino de carne, normalmente de vaca, pasado por huevo batido y luego por pan rallado, que se cocina frito o (menos comúnmente) al horno y suele acompañarse con una guarnición, como papas fritas. Por extensión, se llama milanesa a cualquier rebanada de un ingrediente que se pase por huevo batido y pan rallado, existiendo así milanesas de pollo, de pescado, de soja, de berenjena, de mozzarella, etcétera.',25,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(27,'Ternera','El matambre puede ser preparado de varias formas. La más conocida es el matambre arrollado, preparado originario y típico de Argentina, el cual se consume mucho en este país en época de la Navidad. Se trata de una receta para preparar una "carne fiambre", que se puede tomar entre horas para lo que su nombre indica: "matar el hambre". Este preparado se lo consume como entrada o acompañado con una guarnición, raramente se lo come solo. Otra opción es con panes a modo de sándwich.',30,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(28,'Hamburguesa','Una hamburguesa es un alimento procesado en forma de sándwich o bocadillo de carne picada aglutinada en forma de filete, cocinado a la parrilla o a la plancha, aunque también puede freírse u hornearse. Fuera del ámbito de habla hispana es más común encontrar la denominación burger.',22,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(29,'Lomo al Verdeo','El lomo saltado es un plato típico de la gastronomía del Perú cuyos registros datan de fines del siglo XIX, donde se le conocía como «lomito de vaca», «lomito saltado» o «lomito a la chorrillana».',75,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(30,'Milanesa a la Napolitana','La milanesa napolitana o milanesa a la napolitana es un plato creado en Buenos Aires y típico de la Gastronomía argentina. Consiste en una milanesa, habitualmente de carne vacuna, llevada al horno para ser recubierta como una pizza, con salsa de tomate y queso mozzarella, añadiéndosele distintos ingredientes (jamón, panceta, atún, cebolla...).',35,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(31,'Suprema a la Napolitana','La milanesa napolitana o milanesa a la napolitana es un plato creado en Buenos Aires y típico de la Gastronomía argentina. Consiste en una milanesa, habitualmente de carne vacuna, llevada al horno para ser recubierta como una pizza, con salsa de tomate y queso mozzarella, añadiéndosele distintos ingredientes (jamón, panceta, atún, cebolla...).',32,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(32,'Helado','En su forma más simple, el helado o crema helada es un postre congelado hecho de agua, leche, crema de leche o natilla combinadas con saborizantes, edulcorantes o azúcar. En la actualidad, se añaden otros ingredientes tales como huevos, frutas, chocolate, frutos secos, yogur y sustancias estabilizantes. El helado surgió en China.',30,0,'2013-01-09','2013-01-09');
INSERT INTO "menu_item" VALUES(33,'Ensalada de Frutas','La macedonia de frutas es una combinación de variadas frutas cortadas en trozos pequeños, aderezada con azúcar, licor o zumo de frutas muy típica en algunos países que se toma como postre. También es llamada ensalada de frutas por algunos o tutti frutti en Chile.',25,0,'2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "menu_item_addons";
CREATE TABLE "menu_item_addons" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" TEXT NOT NULL , "menu_item_id" INTEGER NOT NULL ,  "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);
INSERT INTO "menu_item_addons" VALUES(1,'Salsa blanca',24,'','');
INSERT INTO "menu_item_addons" VALUES(2,'Salsa bolognesa',24,'','');
INSERT INTO "menu_item_addons" VALUES(3,'Con agua',7,'','');
INSERT INTO "menu_item_addons" VALUES(4,'Con Leche',7,'','');
INSERT INTO "menu_item_addons" VALUES(5,'Limon',3,'','');
INSERT INTO "menu_item_addons" VALUES(6,'Naranja',3,'','');
INSERT INTO "menu_item_addons" VALUES(7,'Comun',3,'','');
INSERT INTO "menu_item_addons" VALUES(8,'Canela',3,'','');

DROP TABLE IF EXISTS "menu_item_thumbs";
CREATE TABLE "menu_item_thumbs" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"menu_item_id" INTEGER NOT NULL ,"img_small" VARCHAR ,"img_medium" VARCHAR ,"img_large" VARCHAR ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "menu_item_videos";
CREATE TABLE "menu_item_videos" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "menu_item_id" INTEGER NOT NULL , "path" VARCHAR NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);
INSERT INTO "menu_item_videos" VALUES(1,19,'/media/menus/menu_mediodia/categories/entradas/rabas.mp4','2013-02-21','2013-02-21');

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
INSERT INTO "order_status" VALUES(2,'cancelado','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(3,'imprimir','2013-01-09','2013-01-09');
INSERT INTO "order_status" VALUES(4,'impreso','2013-01-09','2013-01-09');

DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL , "date" DATETIME NOT NULL , "dining_table_id" INTEGER NOT NULL, "observation" TEXT,  "order_status_id" INTEGER NOT NULL, "total" DOUBLE NOT NULL,"user_id" INTEGER NOT NULL, "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(dining_table_id) REFERENCES dining_tables(_id) ON DELETE CASCADE, FOREIGN KEY(order_status_id) REFERENCES order_status(_id) ON DELETE CASCADE, FOREIGN KEY(user_id) REFERENCES users(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "order_detail";
CREATE TABLE "order_detail" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "order_id" INTEGER NOT NULL , "menu_item_categories_id" INTEGER NOT NULL , "addon_id" INTEGER,"quantity" INTEGER NOT NULL DEFAULT 1,  "price" DOUBLE NOT NULL ,"subtotal" DOUBLE NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(order_id) REFERENCES orders(_id) ON DELETE CASCADE, FOREIGN KEY(menu_item_categories_id) REFERENCES menu_items_categories(_id) ON DELETE CASCADE, FOREIGN KEY(addon_id) REFERENCES menu_item_addons(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "profiles";
CREATE TABLE "profiles" ("_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "description" VARCHAR NOT NULL );
INSERT INTO "profiles" VALUES(1,'admin');
INSERT INTO "profiles" VALUES(2,'mozo');

DROP TABLE IF EXISTS "promotions";
CREATE TABLE "promotions" ("_id" INTEGER NOT NULL , "menu_item_id" INTEGER NOT NULL , "percentage_discount" VARCHAR NOT NULL , "start_date" DATETIME NOT NULL , "end_date" DATETIME NOT NULL , "created_at" TIMESTAMP NOT NULL , "updated_at" DATETIME NOT NULL, FOREIGN KEY(menu_item_id) REFERENCES menu_item(_id) ON DELETE CASCADE);

DROP TABLE IF EXISTS "users";
CREATE TABLE "users" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"username" VARCHAR NOT NULL ,"password" VARCHAR NOT NULL , "first_name" VARCHAR NOT NULL, "last_name" VARCHAR NOT NULL, "email" VARCHAR,"status" INTEGER NOT NULL ,"profile_id" INTEGER NOT NULL ,"created_at" TIMESTAMP NOT NULL ,"updated_at" DATETIME NOT NULL, FOREIGN KEY(profile_id) REFERENCES profiles(_id) ON DELETE CASCADE);
INSERT INTO "users" VALUES(1,'admin','1234','Jose','Vigil','josevigil@roamtouch.com',0,1,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(2,'jperez','1234','Juan','Perez','juanperez@hotmail.com',0,2,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(3,'grodriguez','1234','Gonzalo','Rodriguez','grodriguez@hotmail.com',0,2,'2013-02-05','2013-02-05');
INSERT INTO "users" VALUES(4,'fcastillo','1234','Fabian','Castillo','fcastillo@hotmail.com',0,2,'2013-02-05','2013-02-05');