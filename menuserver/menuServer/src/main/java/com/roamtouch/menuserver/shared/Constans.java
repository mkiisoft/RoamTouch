package com.roamtouch.menuserver.shared;

public interface Constans {	
	
	static final int WEB_SERVER_PORT = 5556; 
	static final int WEB_SOCKET_PORT = 8887; 
	static final int UDP_PORT = 123456;
	
	static final int GET_MENUS 									= 1000;	
	static final int GET_MENU_BY_ID   		 					= 1001;	
	static final int GET_MENUS_BY_MENU_DESCRIPTION				= 1002;
	static final int GET_MENUS_BY_CATEGORY_ID 					= 1003;
	
	static final int GET_CATEGORIES 							= 1100;
	static final int GET_CATEGORY_BY_ID  						= 1101;
	static final int GET_CATEGORIES_BY_MENU_ID					= 1102;
	static final int GET_CATEGORIES_BY_DESCRIPTION				= 1103;
	static final int GET_CATEGORIES_BY_MENUS_IDS 				= 1104;
	static final int GET_CATEGORIES_BY_MENU_ITEM_ID				= 1105;
	static final int GET_CATEGORIES_BY_ID 						= 1106;
	
	static final int GET_MENU_ITEMS 							= 1200;
	static final int GET_MENU_ITEM_BY_ID  						= 1201;
	static final int GET_MENU_ITEMS_BY_NAME						= 1202;
	static final int GET_MENU_ITEMS_BY_DESCRIPTION				= 1203;	
	static final int GET_MENU_ITEM_BY_PRICE						= 1204;	
	static final int GET_MENU_ITEMS_BY_CATEGORY_ID				= 1205;
	static final int GET_CATEGORIES_AND_MENU_ITEMS_BY_MENU_ID   = 1206;
	
	// Server
	static final int GET_MENU_ITEM_SMALL_THUMB_BY_ID   			= 1207;
	// Client
	static final int GET_MENU_ITEM_THUMB_BY_MENU_ID             = 1207;
	
	static final int GET_MENU_ITEM_MEDIUM_THUMB_BY_ID  			= 1208;	
	static final int GET_MENU_ITEM_LARGE_THUMB_BY_ID   			= 1209;	
	static final int GET_MENU_ITEM_THUMBS_BY_ID		   			= 1210;
	static final int GET_MENU_ITEM_VIDEOS		   				= 1211;
	static final int GET_MENU_ITEM_VIDEOS_BY_ID	   				= 1212;	
	static final int GET_MENU_ITEM_THUMB_BY_MENU_ITEM_CATEGORIES_ID = 1213;
	static final int GET_ALL_MENU_ITEMS_BY_MENU_ID				= 1214;	
	static final int GET_MOST_POPULAR_MENU_ITEMS				= 1215;
	static final int GET_ALL_MENU_ITEMS							= 1216;
	static final int GET_ALL_MENU_DATA							= 1217;	
	
	static final int INSERT_MENU	 							= 2000;
	static final int UPDATE_MENU	 							= 2001;
	static final int DELETE_MENU	 							= 2002;
	
	static final int INSERT_CATEGORY	 						= 2100;
	static final int UPDATE_CATEGORY 							= 2101;
	static final int DELETE_CATEGORY 							= 2102;
	static final int DELETE_CATEGORY_BY_CATEGORY_AND_MENU_ID	= 2103;

	public static final int INSERT_MENU_ITEM 					= 2200;
	static final int UPDATE_MENU_ITEM 							= 2201;
	static final int DELETE_MENU_ITEM 							= 2202;
	static final int INSERT_MENU_ITEM_THUMBNAIL					= 2203;	 
	static final int INSERT_MENU_ITEM_VIDEO 					= 2204;
	static final int UPDATE_MENU_ITEM_THUMB 					= 2205; 
	static final int GET_MENU_ITEM_BY_CAT_ID 					= 2206;
	static final int UPDATE_MENU_ITEM_ORDER_COUNT				= 2207; 
	static final int UPDATE_MENU_ITEM_VIDEO						= 2208;
	static final int CHECK_MENU_ITEM_CATEGORIES 				= 2209;
	static final int GET_MENU_ITEM_THUMB_AND_VIDEO_BY_ID		= 2210;
	static final int DELETE_MENU_ITEM_CATEGORIES_BY_ID 			= 2211;
	
	static final int SEND_COMMAND_BACKUP_DATABASE				= 5000;
	static final int SEND_COMMAND_BACKUP_MEDIA					= 5001;
	static final int SEND_COMMAND_BACKUP_DATABASE_AND_MEDIA		= 5002;
	static final int SEND_COMMAND_RESTORE_DATABASE				= 5003;
	static final int SEND_COMMAND_RESTORE_DATABASE_AND_MEDIA	= 5005;	
	static final int SEND_COMMAND_ORDERS_UPDATED				= 5006;	
	
	static final int SEND_COMMAND_PRINT_ORDER					= 5007;
	static final int SEND_COMMAND_PRINT_ORDER_ITEM				= 5008;
	static final int SEND_COMMAND_DINING_TABLE_OPENED			= 5009;
	
	static final int SEND_COMMAND_IMPORT_EXCEL_FILE				= 5010;
	static final int SEND_COMMAND_IMPORT_GDOCS_FILE				= 5011;
	static final int SET_COMMAND_BLINK_TABLE_BY_TABLE_ID		= 5012;

	static final int SEND_MESSAGE_TO_ALL_CLIENTS				= 7000;
	static final int SEND_MESSAGE_TO_MASTER						= 7001;
	static final int SEND_MESSAGE_TO_CLIENT_BY_CLIENT_ID		= 7002;
	
	static final int GET_CATEGORY_FOLDER_NAME					= 8000;
	static final int GET_MENUS_FOLDER_AME						= 8001;	
	
	static final int CREATE_ORDER								= 9000;	
	static final int UPDATE_ORDER								= 9001;
	static final int DELETE_ORDER								= 9002;
	static final int GET_ORDERS									= 9003;
	static final int GET_ORDER_DETAIL							= 9004;
	
	static final int GET_ORDER_STATUS							= 9005;
	static final int GET_DINING_TABLE_STATUS					= 9006;
	static final int GET_DINING_TABLES							= 9007;	
	static final int GET_ORDER_BY_TABLE_STATE 					= 9008;
	
	static final int GET_TOTAL_ORDER							= 8008;
	static final int UPDATE_TOTAL_ORDER							= 8009;
	
	static final int GET_ORDER_BY_TABLE_STATUS					= 9008;
	static final int GET_LAST_ORDER_ID							= 9009;
	static final int INSERT_ORDER_DETAIL						= 9010;
	static final int UPDATE_DINING_TABLE_STATUS					= 9011;
	static final int UPDATE_ORDER_BY_STATUS						= 9012;
	static final int DELETE_ORDER_DETAIL						= 9013;
	static final int INSERT_UPDATE_ORDERS_DETAILS				= 9014;
	static final int UPDATE_ORDER_DETAIL						= 9015;
	
	static final int GET_NOT_PRINTED_ORDER_DETAILS 				= 9016;
	static final int UPDATE_PRINTED_ORDER 						= 9017;
	static final int UPDATE_PRINTED_ORDER_DETAIL 				= 9018;
	static final int GET_ORDER_DETAIL_STATUS  					= 9019;	
	static final int GET_ORDER_BY_ID 		  					= 9020;
	
	static final int CREATE_USER								= 10000;
	static final int UPDATE_USER								= 10001;
	static final int DELETE_USER								= 10002;	
	static final int GET_USER_BY_ID								= 10003;
	static final int GET_USERS									= 10004;
	static final int GET_USER_PROFILES							= 10005;
	static final int CHECK_USER									= 10006;
	static final int UPDATE_STATUS_USER							= 10007; 
	static final int GET_USERS_BY_STATUS						= 10008; 
	
	static final int CREATE_DINING_TABLE						= 11000;
	static final int UPDATE_DINING_TABLE						= 11001;
	static final int DELETE_DINING_TABLE						= 11002;
	static final int GET_DINING_TABLE_BY_ID						= 11003;
	static final int GET_DINING_TABLE_BY_STATUS					= 11004;
	static final int GET_DINING_TABLE_BY_USER					= 11005;
	
	static final int CREATE_MENU_ITEM_ADDON						= 12000;
	static final int UPDATE_MENU_ITEM_ADDON						= 12001;
	static final int DELETE_MENU_ITEM_ADDON						= 12002;	
	static final int GET_MENU_ITEM_ADDONS						= 12003;	
	static final int GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID		= 12004;
	static final int GET_MENU_ITEM_ADDON_BY_ID					= 12005;	
	
	static final int INSERT_MENUS_ITEMS_CATEGORIES						= 14000;
	static final int GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID	= 14001;
	static final int GET_CATEGORIES_MENUS_ID							= 14002;
	static final int GET_MENU_ITEM_THUMBS_BY_SMALL_IMAGE_NAME			= 14003;
	
	static final int GET_COMANDAS								= 15000;
	static final int GET_ORDERS_AND_DETAIL						= 15001;	
	
	static final int CREATE_OPTION								= 16000;
	static final int UPDATE_OPTION								= 16001;
	static final int DELETE_OPTION								= 16002;
	static final int GET_OPTIONS								= 16003;
	static final int GET_OPTION_BY_ID							= 16004;
	static final int GET_OPTIONS_BY_NAME						= 16005;
	
	public static final int INSERT_MENU_EXISTS 							= -100;
	public static final int INSERT_CATEGORY_EXISTS						= -101;
	public static final int INSERT_MENU_ITEM_EXISTS 					= -102;
	public static final int CANNOT_CREATE_CATEGORY_FOLDER				= -103;
	public static final int CANNOT_MOVE_TEMP_FILE						= -104;
	
	//PRINT VARIABLES
	public static final int ORDER_DETAIL_NOT_PRINTED					= 3;
	public static final int ORDER_DETAIL_PRINT							= 4;
	public static final int ORDER_DETAIL_PRINTING						= 5;
	public static final int ORDER_DETAIL_PRINTED						= 6;
	
	static final int PROFILE_ADMIN_ID = 1; 
	static final int DINING_TABLE_STATUS_ID_OPEN = 1;
	static final int DINING_TABLE_STATUS_ID_CLOSE = 9;
	
	//MENUS'FIEDLS 
	static final String TAG_MENUS = "menus";
	static final String TAG_MENU_ID = "_id";
	static final String TAG_MENU_DESCRIPTION = "description";
	static final String TAG_MENU_FOLDER_NAME = "folder_name";
	static final String TAG_MENU_CREATED_AT = "created_at";
	static final String TAG_MENU_UPDATED_AT = "updated_at";
	static final String TAG_MENU_ACTIVE = "active";
	static final String TAG_MENU_CATEGORIES_MENU = "categories_menus";
	
	//CATEGORIES'FIELDS
	static final String TAG_CATEGORIES = "categories";
	static final String TAG_CATEGORIE_ID = "_id";
	static final String TAG_CATEGORIE_MENU_ID = "menu_id";
	static final String TAG_CATEGORIE_DESCRIPTION = "description";
	static final String TAG_CATEGORIE_FOLDER_NAME = "folder_name";
	static final String TAG_CATEGORIE_CREATED_AT = "created_at";
	static final String TAG_CATEGORIE_UPDATED_AT = "updated_at";
	static final String TAG_CATEGORIE_CATEGORIES_MENU_ID = "categories_menu_id";
	
	//MENU ITEM'FIELDS
	static final String TAG_MENU_ITEM = "menu_item";
	static final String TAG_MENU_ITEM_ID = "_id";
	static final String TAG_MENU_ITEM_NAME = "name";
	static final String TAG_MENU_ITEM_DESCRIPTION = "description";
	static final String TAG_MENU_ITEM_IMG_MEDIUM = "img_medium";
	static final String TAG_MENU_ITEM_PRICE = "price";
	static final String TAG_MENU_ITEM_CATEGORY_ID = "category_id";
	static final String TAG_MENU_ITEM_CATEGORY_MENU_ID = "categories_menus_id";  
	static final String TAG_MENU_ITEM_CREATED_AT = "created_at";
	static final String TAG_MENU_ITEM_UPDATED_AT = "updated_at";
	static final String TAG_MENU_ITEM_IMG_LARGE = "img_large";
	
	//TABLES'FIELDS
	static final String TAG_DINING_TABLES_ITEM = "dining_tables";
	static final String TAG_DINING_TABLE_ID = "_id";
	static final String TAG_DINING_TABLE_DESCRIPTION = "description";
	static final String TAG_DINING_TABLE_STATUS_ID = "dining_table_status_id";
	static final String TAG_DINING_TABLE_STATUS_DESCRIPTION = "dining_table_status_description";
	static final String TAG_DINING_TABLE_CREATED_AT = "created_at";
	static final String TAG_DINING_TABLE_UPDATED_AT = "updated_at";
	
	//ORDERS'FIELDS
	static final String TAG_ORDERS = "orders";
	static final String TAG_ORDER_ID = "_id";
	static final String TAG_ORDER_DATE = "date";
	static final String TAG_ORDER_DINING_TABLE_ID = "dining_table_id";
	static final String TAG_ORDER_DINING_TABLE_DESCRIPTION = "dining_table_description";
	static final String TAG_ORDER_DINING_TABLE_STATUS_ID = "dining_table_status_id";
	static final String TAG_ORDER_DINING_TABLE_STATUS_DESCRIPTION = "dining_table_status_description";
	static final String TAG_ORDER_OBSERVATION= "observation";
	static final String TAG_ORDER_STATUS_ID = "order_status_id";
	static final String TAG_ORDER_STATUS_DESCRIPTION = "description";
	static final String TAG_ORDER_TOTAL = "total";
	static final String TAG_ORDER_CREATED_AT = "created_at";
	static final String TAG_ORDER_UPDATED_AT = "updated_at";
	static final String TAG_ORDER_USER_ID = "user_id";

	//ORDER DETAIL'FIELDS
	static final String TAG_ORDERS_DETAIL = "order_detail";
	static final String TAG_ORDER_DETAIL_ID = "_id";
	static final String TAG_ORDER_DETAIL_ORDER_ID = "order_id";
	static final String TAG_ORDER_DETAIL_MENU_ITEM_ID= "menu_item_id";
	static final String TAG_ORDER_DETAIL_MENU_ITEM_NAME= "menu_item_name";
	static final String TAG_ORDER_DETAIL_MENU_ITEM_IMG_SMALL= "menu_item_img_small";
	static final String TAG_ORDER_DETAIL_ADDON_ID = "addon_id";
	static final String TAG_ORDER_DETAIL_ADDON_DESCRIPTION = "addon_description";
	static final String TAG_ORDER_DETAIL_MENU_ITEM_CATEGORY_ID= "menu_item_category_id";
	static final String TAG_ORDER_DETAIL_ADDONS = "addons";
	static final String TAG_ORDER_DETAIL_QUANTITY = "quantity";
	static final String TAG_ORDER_DETAIL_PRICE = "price";
	static final String TAG_ORDER_DETAIL_SUBTOTAL = "subtotal";
	static final String TAG_ORDER_DETAIL_CREATED_AT = "created_at";
	static final String TAG_ORDER_DETAIL_UPDATED_AT = "updated_at";
	static final String TAG_ORDER_DETAIL_CATEGORIES_MENU_ID= "categories_menu_id";
	static final String TAG_ORDER_DETAIL_CATEGORY_ID = "category_id";
	static final String TAG_ORDER_DETAIL_MENU_ITEM_CATEGORIES_ID= "menu_item_categories_id";
	
	static final int DINING_TABLE_STATUS_OPEN 							= 1; // "abierta"
	static final int DINING_TABLE_STATUS_WAITING_FOR_ORDER 				= 2; // "esperando realizar pedido"
	static final int DINING_TABLE_STATUS_ORDERED 						= 3; // "pedido realizado"
	static final int DINING_TABLE_STATUS_ORDER_DELIVERED				= 4; // "pedido entregado"
	static final int DINING_TABLE_STATUS_ORDER_CLOSE_REQUESTED			= 5; // "pedido de cierre de cuenta"
	static final int DINING_TABLE_STATUS_ORDER_CLOSE_REQUEST_DELIVERED	= 6; // "cierre de cuenta entregado"
	static final int DINING_TABLE_STATUS_ORDER_PAYED					= 7; // "pago realizado"	
	static final int DINING_TABLE_STATUS_ORDER_PAYMENT_ADDRESSED		= 8; // "cobro realizado"
	static final int DINING_TABLE_STATUS_CLOSE 							= 9; // "cerrada"
	 
	static final int DINING_TABLE_ACTION_ACTIVE = 0;	
	
	
	//////////////////////////////////
	///// CLIENT
	//////////////////////////////////
	
	static final int SCREENLAYOUT_SIZE_LARGE 	= 70001;
	static final int SCREENLAYOUT_SIZE_NORMAL 	= 70002;
	static final int SCREENLAYOUT_SIZE_SMALL 	= 70003;
	static final int SCREENLAYOUT_SIZE_XLARGE 	= 70004;	
	
	static final int MENU_PEDIDOS_ID = 100;
	static final int MENU_TODOS_ID = 101;
	static final int MENU_MAS_PEDIDOS_ID = 102;
	
	static final int CONDICION_MAS_PEDIDOS = 5;
	static final int FLAG_MENU_ITEM_PEDIDO = 1;
	static final int FLAG_MENU_ITEM_ORDENADO = 2;
	static final int FLAG_ADDON_NINGUNO = 10000;	
	
	//CATEGORIES_MENUS'FIELDS
	static final String TAG_CATEGORIES_MENUS = "categories_menus";
	static final String TAG_CATEGORIES_MENUS_ID = "_id";
	static final String TAG_CATEGORIES_MENUS_CATEGORY_ID = "category_id";
	static final String TAG_CATEGORIES_MENUS_CATEGORY_DESCRIPTION = "category_description";
	static final String TAG_CATEGORIES_MENUS_MENU_ID = "menu_id";
	static final String TAG_CATEGORIES_MENUS_MENU_ITEMS = "menu_items";
	
	//MENU ITEM CATEGORIES'FIELDS
	static final String TAG_MENU_ITEM_CATEGORIES = "menu_items_categories";
	static final String TAG_MENU_ITEM_CATEGORIES_ID = "_id";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_ID = "menu_item_id";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_NAME = "menu_item_name";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_DESCRIPTION = "menu_item_description";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_PRICE = "menu_item_price";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_IMG_MEDIUM = "menu_item_img_medium";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_IMG_LARGE = "menu_item_img_large";
	static final String TAG_MENU_ITEM_CATEGORIES_CATEGORIES_MENUS_ID = "categories_menus_id";
	static final String TAG_MENU_ITEM_CATEGORIES_MENU_ITEM_ORDER_COUNT = "menu_item_order_count";
	static final String TAG_MENU_ITEM_CATEGORIES_CATEGORIES_ADDONS = "addons";
	
	//MENU_ITEM_ADDONS DETAIL'FIELDS
	static final String TAG_MENU_ITEM_ADDONS = "menu_item_addons";
	static final String TAG_MENU_ITEM_ADDON_ID = "_id";
	static final String TAG_MENU_ITEM_ADDON_DESCRIPTION = "description";
	static final String TAG_MENU_ITEM_ADDON_MENU_ITEM_ID= "menu_item_id";
	static final String TAG_MENU_ITEM_ADDON_MENU_ITEM_NAME= "menu_item_name";
	static final String TAG_MENU_ITEM_ADDON_CREATED_AT = "created_at";
	static final String TAG_MENU_ITEM_ADDON_UPDATED_AT = "updated_at";	
	
	static final int SWIPE_RIGHT = 1;
	static final int SWIPE_LEFT = 2;
	static final int SWIPE_NONE = -1;
	static final int SWIPE_UP = 3;
	static final int SWIPE_DOWN = 4;
	
	static final int RESTORED_NONE 					= 10021;
	static final int RESTORED_MEDIA 				= 10022;
	static final int RESTORED_MEDIA_AND_DATABASE 	= 10023;
	
	
}
