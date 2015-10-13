	
/* Variables globales para los metodos de la API */

/******** WEB Socket **********/
var GET_DATA       = 900;
var POST_DATA      = 901;
var SEND_COMMAND   = 902;
var SEND_MESSAGE   = 903;  

/*********** MENUS ****************/
var GET_MENUS 				  = 1000;
var GET_MENU_BY_ID			  = 1001;
var GET_MENUS_BY_DESCRIPTION  = 1002;
var GET_MENUS_BY_CATEGORY_ID  = 1003;

var INSERT_MENU = 2000;
var UPDATE_MENU = 2001;
var DELETE_MENU = 2002;

/*********** CATEGORIES ****************/
var GET_CATEGORIES            		   = 1100; 
var GET_CATEGORY_BY_ID      		   = 1101; 
var GET_CATEGORIES_BY_MENU_ID 		   = 1102;
var GET_CATEGORIES_BY_DESCRIPTION 	   = 1103;
var GET_CATEGORIES_BY_MENUS_IDS		   = 1104;
var GET_CATEGORIES_BY_MENU_ITEM_ID	   = 1105;

var INSERT_CATEGORY = 2100;
var UPDATE_CATEGORY = 2101;
var DELETE_CATEGORY = 2102;

/*********** ITEMS ****************/
var GET_MENU_ITEMS 				  			 		= 1200;
var GET_MENU_ITEM_BY_ID 		  			 		= 1201;
var GET_MENU_ITEMS_BY_NAME		  			 		= 1202;
var GET_MENU_ITEMS_BY_DESCRIPTION 			 		= 1203;
var GET_MENU_ITEM_BY_PRICE 		  			 		= 1204;
var GET_MENU_ITEMS_BY_CATEGORY_ID  			 		= 1205;
var GET_CATEGORIES_AND_MENU_ITEMS_BY_MENU_ID 		= 1206;
var GET_MENU_ITEM_SMALL_THUMB_BY_ID 		 		= 1207;
var GET_MENU_ITEM_MEDIUM_THUMB_BY_ID 		 		= 1208;
var GET_MENU_ITEM_LARGE_THUMB_BY_ID 		 		= 1209;
var GET_MENU_ITEM_THUMBS_BY_ID 				 		= 1210;
var GET_MENU_ITEM_VIDEOS 					 	   	= 1211;
var GET_MENU_ITEM_VIDEOS_BY_ID 				 	   	= 1212;
var GET_MENU_ITEM_THUMB_BY_MENU_ITEM_CATEGORIES_ID 	= 1213;
var GET_ALL_MENU_ITEMS_BY_MENU_ID					= 1214;
var GET_MOST_POPULAR_MENU_ITEMS						= 1215;
var GET_ALL_MENU_ITEMS 								= 1216;

var INSERT_MENU_ITEM 			 		= 2200;
var UPDATE_MENU_ITEM 		 	 		= 2201;
var DELETE_MENU_ITEM 			 		= 2202;
var INSERT_MENU_ITEM_THUMBNAIL 	 		= 2203;
var INSERT_MENU_ITEM_VIDEO 		 		= 2204;
var UPDATE_MENU_ITEM_THUMB		 		= 2205;
var GET_MENU_ITEM_BY_CAT_ID 	 		= 2206;
var UPDATE_MENU_ITEM_ORDER_COUNT 		= 2207;
var UPDATE_MENU_ITEM_VIDEO 		 		= 2208;
var CHECK_MENU_ITEM_CATEGORIES   		= 2209;
var GET_MENU_ITEM_THUMB_AND_VIDEO_BY_ID = 2210;

/******** BACKUP ************/
var SEND_COMMAND_BACKUP_DATABASE 			= 5000;
var SEND_COMMAND_BACKUP_MEDIA 				= 5001;
var SEND_COMMAND_BACKUP_DATABASE_AND_MEDIA 	= 5002;
var SEND_COMMAND_RESTORE_DATABASE 			= 5003;
var SEND_COMMAND_RESTORE_MEDIA 				= 5004;
var SEND_COMMAND_RESTORE_DATABASE_AND_MEDIA = 5005;
var SEND_COMMAND_PRINT_TICKET 				= 5006;

var SEND_COMMAND_IMPORT_EXCEL_FILE			= 5010;
var SEND_COMMAND_IMPORT_GDOCS_FILE			= 5011;

/******** ORDERS ************/
var CREATE_ORDER 					= 9000;
var UPDATE_ORDER 					= 9001;
var DELETE_ORDER 					= 9002;
var GET_ORDERS 						= 9003;
var GET_ORDER_DETAIL 				= 9004;
var GET_ORDER_STATUS 				= 9005;
var GET_DINING_TABLE_STATUS 		= 9006;
var GET_DINING_TABLES 				= 9007;
var GET_ORDER_BY_TABLE_STATUS 		= 9008;
var GET_LAST_ORDER_ID 				= 9009;
var INSERT_ODER_DETAIL 				= 9010;
var UPDATE_DINING_TABLE_STATUS 		= 9011;
var UPDATE_ORDER_BY_STATUS 		 	= 9012;
var DELETE_ORDER_DETAIL 		 	= 9013;
var INSERT_UPDATE_ORDERS_DETAILS 	= 9014;
var UPDATE_ORDER_DETAIL  			= 9015;
var GET_NOT_PRINTED_ORDER_DETAILS   = 9016;
var UPDATE_PRINTED_ORDER    		= 9017;
var UPDATE_PRINTED_ORDER_DETAIL    	= 9018;
var GET_ORDER_DETAIL_STATUS 		= 9019;
var GET_ORDER_BY_ID					= 9020;

/********** USERS ****************/
var CREATE_USER = 10000;
var UPDATE_USER = 10001;
var DELETE_USER = 10002;
var GET_USER_BY_ID 	= 10003;
var GET_USERS 	= 10004;
var GET_USER_PROFILES = 10005;
var CHECK_USER = 10006;

/********** DINING TABLES ***********/
var CREATE_DINING_TABLE = 11000;
var UPDATE_DINING_TABLE = 11001;
var DELETE_DINING_TABLE = 11002;
var GET_DINING_TABLE_BY_ID = 11003;

/********** MENU ITEM ADDONS **********/
var CREATE_MENU_ITEM_ADDON    			= 12000;
var UPDATE_MENU_ITEM_ADDON    			= 12001;
var DELETE_MENU_ITEM_ADDON    			= 12002;
var GET_MENU_ITEM_ADDONS      			= 12003;
var GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID = 12004;
var GET_MENU_ITEM_ADDON_BY_ID 			= 12005;

/*************** COMANDAS **********************/
var GET_COMANDAS = 15000;
var GET_ORDERS_AND_DETAIL = 15001;

/**************** OPTIONS ********************/
var	CREATE_OPTION						= 16000;
var	UPDATE_OPTION						= 16001;
var	DELETE_OPTION						= 16002;
var GET_OPTIONS							= 16003;
var	GET_OPTION_BY_ID					= 16004;
var	GET_OPTIONS_BY_NAME					= 16005;	

/**************** ORDERS ********************/
var	SEND_COMMAND_PRINT_ORDER			= 5007;
var SEND_COMMAND_PRINT_ORDER_ITEM		= 5008;
var SEND_COMMAND_DINING_TABLE_OPENED	= 5009;

/**************** RESTORE ********************/
var RESTORED_NONE 						= 10021;
var RESTORED_MEDIA 						= 10022;
var RESTORED_MEDIA_AND_DATABASE 		= 10023;

function replace_specials_characters(text){
	text = text.replace('á','\u00e1');
	text = text.replace('é','\u00e9');
	text = text.replace('í','\u00ed');
	text = text.replace('ó','\u00f3');
	text = text.replace('ú','\u00fa');
	text = text.replace('Á','\u00c1');
	text = text.replace('É','\u00c9');
	text = text.replace('Í','\u00cd');
	text = text.replace('Ó','\u00d3');
	text = text.replace('Ú','\u00da');
	text = text.replace('ñ','\u00f1');
	text = text.replace('Ñ','\u00d1');

	return text;
}

function encode_utf8( text ) {
	return unescape( encodeURIComponent( text ) );
}

/* Get url href */
var url = window.location.href; 

url = url.split('/');
var url_href = 'http://' + url[2];

/* Connect via WebSocket */
var connection;

var socketURL = url[2];
socketURL = socketURL.replace("5556","8887");    

connection  = new WebSocket( "ws://" + socketURL + "/" ); 

if ( window["WebSocket"] ) {

	connection.onclose = function( event ) {    
	  //alert("close");
	}

	connection.onopen = function( event ) {   
	  //alert("opened");
	  connection.send("web-client-discovery");                     
	}         
	        
	connection.onmessage = function( event ) {          

		/*var message = $.parseJSON( event.data );
		var status = message.status;
		
		switch( status ){
			case 5006: alert(status);
		}
		*/
		//alert( message.status );
		
		//alert("message " + event.data);           
	}           
} else if( !window.WebSocket ) {
	alert('<span style="color: red;"><strong>Error:</strong> This browser does not have support for WebSocket</span>');
	//return;
}

function disconnect(){
//connection.close();       
}   

function get_loading_image(){
	$('#loading_img').attr('src',url_href + '/img/ajax-loader.gif'); 
}

function get_loading_image_insert(){
	$('#loading_img_insert').attr('src',url_href + '/img/ajax-loader.gif'); 	
}

function get_loading_image_edit(){
	$('#loading_img_edit').attr('src',url_href + '/img/ajax-loader.gif');
}

$(document).ready(function(){  

	/*$('#nav_menu').load('templates/nav_menu.html'); 
	$('#sidebar_menu').load('templates/sidebar_menu', function(){
		$('#content').css('display','block');
	});*/	

	$('#nav_menu').load(url_href + '/templates/nav_menu.html', function(){
		$('#brand').attr('href', url_href + '/index.html');
		$('#manage_menus_nav').attr('href', url_href + '/menus/menus_abm.html');
	    $('#manage_categories_nav').attr('href', url_href + '/categories/categories_abm.html');
	    $('#manage_items_nav').attr('href', url_href + '/items/items_abm.html');     
	}); 

	$('#sidebar_menu').load(url_href + '/templates/sidebar_menu.html', function(){
		$('#content').show();
		
		$('#home').attr('href', url_href + '/index.html');		
		$('#manage_items_addons').attr('href', url_href + '/items/items_addons_abm.html');		
		$('#manage_dining_tables').attr('href', url_href + '/dining_tables/dining_tables_abm.html');
		$('#manage_users').attr('href', url_href + '/users/users_abm.html');
	    $('#manage_menus').attr('href', url_href + '/menus/menus_abm.html');
	    $('#manage_categories').attr('href', url_href + '/categories/categories_abm.html');
	    $('#manage_items').attr('href', url_href + '/items/items_abm.html'); 
	    $('#manage_orders').attr('href', url_href + '/orders/orders.html');  
	    $('#manage_items_thumbs').attr('href', url_href + '/items/items_thumbs.html'); 
	    $('#manage_items_videos').attr('href', url_href + '/items/items_videos.html');  

	    $('#print_ports_setup').attr('href', url_href + '/printing/print_ports_setup.html');  
	    //$('#download_print_complements').attr('href', url_href + '/printing/print_complements.html');  
		
		$('#import').attr('href', url_href + '/import/import.html');  

	    $('#create_backup').attr('href', url_href + '/backup/create_backup.html');  
	    $('#restore_backup').attr('href', url_href + '/backup/restore_backup.html');  
		$('#restore_database').attr('href', url_href + '/backup/restore_database.html');  

		$('#log').attr('href', url_href + '/log/log.html');  
		
	    $('#logout').attr('href', url_href + '/index.html');    
	});	 
	
});         
