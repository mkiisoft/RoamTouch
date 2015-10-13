package com.roamtouch.menuserver.communications;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.backup.BackUpData;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.httpd.NanoHTTPD;
import com.roamtouch.menuserver.httpd.NanoHTTPD.TempFile;
import com.roamtouch.menuserver.importdata.GoogleSpreadSheetTab;
import com.roamtouch.menuserver.importdata.GoogleSpreadSheetTab.SpreadSheetLoaderListener;
import com.roamtouch.menuserver.importdata.ImportExcel;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.FileUtils;
import com.roamtouch.menuserver.utils.FolderHandler;
import com.roamtouch.menuserver.utils.ImageResize;
import com.roamtouch.menuserver.utils.Parser;

import net.tootallnate.websocket.WebSocket;


public class APIProtocol<V, K> {  
		
	final static String TAG = "APIProtocol";
	
	private static DataBaseController database;
	private com.roamtouch.menuserver.communications.WebSocket client;
	private BackUpData backup;
	private MenuServer cM;
	private FolderHandler folderHandler;
	private ImageResize resize;
	
	private FileUtils fUtils = new FileUtils();
	
	private String description_menu;
	private String interval_from;
	private String interval_to;
	private String description_category;
	private String menu_id;
	private String menus_ids;
	private String category_id;
	private String categories_id;
	private String menu_item;  
	private String menu_item_id;
	private String img_small;
	private String img_medium;
	private String img_large;
	private String categories_menus_id;
	private String menu_item_name;
	private String menu_item_description;
	private String menu_item_price;
	private String menu_item_code;
	private String item_price;
	private String item_name;
	private String item_image;
	private String item_description;
	private String addon_id;
	private String video_name;
	private String video_path;
	private String order_dining_table_id;
	private String order_total;
	private String order_status_id;
	private String order_observation;	
	private String quantity;
	private String price;
	private String addons;
	private String subtotal;
	private String menu_item_categories_id;
	private String path_to_the_video;
	private String include_backup_download;
	private String comment_backup_download;
	
	private String count;
	
	private String path_to_small_image;
	private String path_to_medium_image;
	private String path_to_large_image;
	
	private String order_id;
	private String order_detail_id;
	
	private String dining_table_status_id_1;
	private String dining_table_status_id_2;
	
	private String order_status_id_1;
	private String order_status_id_2;
	
	private String dining_table_id;
	private String dining_table_status_id;
	private String dining_table_description;
	
	private File file_uploaded;
	
	private String base_path;
	private String all_path;
	private File new_file;
	
	private String folder_name;	
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String email;
	private String status;
	private String profile_id;
	private String user_id;
	
	private String description;
	
	private String edit_item_image;  
	private String edit_categories_id; 
	private String edit_item_video; 
	private String edit_menu_item_id; 
	private String edit_menus_ids; 
	private String edit_item_price; 
	private String edit_item_description; 
	private String edit_item_name;
	
	// Opciones
	private String option_id;
	private String option_name;
	private String option_value;
		
	//Uploaded files
	private File imageFile;
	private File videoFile;
	private File backpupFile;
	private File importFile;
	private File databaseFile;
	private MenuServerApplication app;
	
	//Import 
	private String type;
	private String user;
	private String pass;
	private String doc;
	boolean waiting_gdoc_import = false;
	boolean waiting_gdoc_import_with_media = false;
	
	private APIProtocolMethods APM;
	
	private Handler handler;
	
	private String coming_address;
	private int coming_action;
	private long thread_pid;
	private String user_agent;
	  		
	public APIProtocol(DataBaseController db, MenuServer cM, FolderHandler fH, com.roamtouch.menuserver.communications.WebSocket w) {
		this.database = db;
		this.cM = cM; 
		this.backup = new BackUpData(this.cM, fH);		
		this.folderHandler = fH;			
		this.resize = new ImageResize(cM);
		this.client =  w;
		APM = new APIProtocolMethods(cM, database, folderHandler, this.resize);
		db.setAPI(this, APM);		
		handler = new Handler();
		app = ((MenuServerApplication)cM.getApplication());
	}
	
	public APIProtocol getInstance() {
		return this;
	}

	public JSONArray parseParams(String msg, Parser p) throws JSONException, GeneralSecurityException, IOException {
		
		JSONArray json = new JSONArray();
		
		coming_address = p.getAddress();
		coming_action = p.getAction();
		thread_pid = p.getPid();
		user_agent = p.getUser_agent();
		
		
		//Busco la carpeta menu si existe.
		base_path = app.getSDCARD();
		
		// Write Log
			new Thread(new Runnable() {
		        public void run() {	        			    		
		    		app.renderLog(coming_action, coming_address, thread_pid, user_agent);
		        }
		    }).start();
		
		switch (p.getAction()){
		
			case Constans.GET_MENUS:						
				json = database.GET_MENUS();			
				break;	
		
			case Constans.GET_MENU_BY_ID:	
				menu_id = (String) p.getByDescId("menu_id");			
				json = APM.GET_MENU_BY_ID(menu_id);
				break;
				
			case Constans.GET_MENUS_BY_MENU_DESCRIPTION:				
				description_menu = (String) p.getByDescId("description_menu");			
				json = database.GET_MENUS_BY_MENU_DESCRIPTION(description_menu);				
				break;			
				
			case Constans.GET_MENUS_BY_CATEGORY_ID:				
				category_id  = (String) p.getByDescId("category_id");			
				json = database.GET_MENUS_BY_CATEGORY_ID(category_id);				
				break;	
				
			case Constans.INSERT_MENU:
							
				description_menu = (String) p.getByDescId("description_menu");				
				folder_name = fUtils.createFolderName(description_menu);
				
				all_path =  "/httpd/media/menus/" + folder_name + "/categories";					
				File file = new File(all_path);					
				File dest = null;					
				
				//Si no existe la creo, si existe la levanto.
				if(!file.isDirectory()){
					folderHandler.copyFilestoSdcard(file.toString(), true);
				}				
				
				interval_to = (String) p.getByDescId("interval_to");					
				interval_from = (String) p.getByDescId("interval_from");					
				
				//Guardo en la base.
				json = database.INSERT_MENU (description_menu, folder_name,interval_from ,interval_to);
					
				break;
				
			case Constans.UPDATE_MENU:				
							
				description_menu = (String) p.getByDescId("description_menu");					
						
				folder_name = fUtils.createFolderName(description_menu);		
				base_path = app.getSDCARD();				    
		        
				menu_id = (String) p.getByDescId("menu_id");			
				interval_to = (String) p.getByDescId("interval_to");		
				interval_from = (String) p.getByDescId("interval_from");					
				
				JSONArray jM = APM.GET_MENU_BY_ID(menu_id);			
				String  old_description_menu = null;				
				for (int i = 0; i < jM.length(); i++) {
		             JSONObject menuObject = jM.getJSONObject(i);
		             old_description_menu = menuObject.getString("description");		            
		        }
				
				//Paso viejo nombre a tipo nombre carpeta
				old_description_menu = fUtils.createFolderName(old_description_menu);		
				
				//Nevo nombre.
				String oldPath =  base_path + "/httpd/media/menus/" + old_description_menu;				
				
				//Viejo nombre.
				String new_path =  base_path + "/httpd/media/menus/" + folder_name;
				File new_file_menu = new File(new_path);
		        File old_file_menu = new File(oldPath);		        

		        //Renombra
		        boolean success_menu = old_file_menu.renameTo(new_file_menu);
		        if (!success_menu) {
		            System.err.println("Error");
		        }  
		        
		        try {

		        
			        JSONArray catsA = database.GET_CATEGORIES_BY_MENU_ID(menu_id);
			        for (int i = 0; i < catsA.length(); i++) {
			             JSONObject catsObject = catsA.getJSONObject(i);
			             String my_category_id = catsObject.getString("_id");
			             
			             JSONArray my_menu_itemA = database.GET_MENU_ITEMS_BY_CATEGORY_ID(my_category_id, menu_id);
			             
			             JSONObject menuItemObject = my_menu_itemA.getJSONObject(i);
			             String my_menu_item_id = menuItemObject.getString("_id");
			             String image_medium = menuItemObject.getString("img_medium");
			             
			            String[] arr_image = image_medium.split("/categories/");
			            String image_small_path = arr_image[1].replace("medium", "samll");
			            String image_medium_path =  arr_image[1];
			            String image_large_path = arr_image[1].replace("medium", "large");      
			             
			             path_to_small_image = new_path  + "/categories/" + image_small_path;
			             path_to_medium_image = new_path  + "/categories/" + image_medium_path;
			             path_to_large_image = new_path  + "/categories/" + image_large_path;
			             
			             JSONArray insertMenuItemObject =  database.UPDATE_MENU_ITEM_THUMB(
			            		 path_to_small_image, 
			            		 path_to_medium_image, 
			            		 path_to_large_image,
			            		 my_menu_item_id
			            ); 
			        }
		        
		    	} catch (JSONException e) {						
					e.printStackTrace();
				}	 
		        
		        
				json = database.UPDATE_MENU(description_menu, folder_name,interval_from ,interval_to, menu_id);				
				break;		
				
			case Constans.DELETE_MENU:				
				menu_id = (String) p.getByDescId("menu_id");					
				json = database.DELETE_MENU(menu_id);			
				break;				
				
			case Constans.GET_CATEGORIES:
				json = database.GET_CATEGORIES();				
				break;	
				
			case Constans.GET_CATEGORY_BY_ID:
				category_id = (String) p.getByDescId("category_id");
				menu_id = (String) p.getByDescId("menu_id");	
				json = database.GET_CATEGORY_BY_ID(category_id, menu_id);	
				
				break;
				
			case Constans.GET_CATEGORIES_BY_MENU_ID:			
				menu_id = (String) p.getByDescId("menu_id");			
				json = database.GET_CATEGORIES_BY_MENU_ID(menu_id);			
				break;
				
			case Constans.GET_CATEGORIES_BY_DESCRIPTION:			
				description_category = (String) p.getByDescId("description_category");				
				json = database.GET_CATEGORIES_BY_DESCRIPTION(description_category);
				break;
				
			case Constans.GET_CATEGORIES_BY_MENUS_IDS:			
				Object inst_mens = (Object) p.getByDescId("menus_ids");			
				if (inst_mens instanceof Vector){				
					Vector menus_ids = (Vector) p.getByDescId("menus_ids");						
					JSONArray bigArr = new JSONArray();	
					JSONArray arr = new JSONArray();
					for (int i=0; i<menus_ids.size(); i++){
						String id = (String) menus_ids.get(i);
						arr = database.GET_CATEGORIES_BY_MENU_ID( id );
						bigArr.put(arr);
					}
					json = bigArr;
				} else {
					String menu_id = (String) p.getByDescId("menus_ids");			
					json = database.GET_CATEGORIES_BY_MENU_ID( menu_id );
				}	
				Log.v("","");
				break;
				
			case Constans.GET_CATEGORIES_BY_MENU_ITEM_ID :			
				menu_item_id  = (String) p.getByDescId("menu_item_id");			
				json = database.GET_CATEGORIES_BY_MENU_ITEM_ID(menu_item_id );			
				break;			
				
			case Constans.INSERT_CATEGORY:		
								
				description_category = (String) p.getByDescId("description_category");
				
				folder_name = fUtils.createFolderName(description_category);
				
				category_id = (String) p.getByDescId("category_id");		
				
				json = database.INSERT_CATEGORY(description_category, folder_name);			
				
				Object inst_cat = (Object) p.getByDescId("menus_ids");		
				JSONArray arr = new JSONArray();
				
				JSONArray bigArr = new JSONArray();	
				arr = new JSONArray();
				
				if (inst_cat instanceof Vector){
					
					Vector cats_ids = (Vector) p.getByDescId("menus_ids");	
					
					for (int i=0; i<cats_ids.size(); i++){
						
						String id = (String) cats_ids.get(i);
						
						json = APM.insert_category_by_id (id, arr, bigArr, folder_name, description_category, false ); 
						
					}
					
					json = bigArr;
					
				} else {						
					
					String menu_id = (String) p.getByDescId("menus_ids");		
					
					json = APM.insert_category_by_id (menu_id, arr, bigArr, folder_name, description_category, false );					
					
					arr = new JSONArray();
					
				}				
				
				break;	
				
				
			case Constans.UPDATE_CATEGORY:
					
				base_path = app.getSDCARD();
				
				description_category = (String) p.getByDescId("description_category");
				String category_folder_name = fUtils.createFolderName(description_category);
				category_id = (String) p.getByDescId("category_id");			
				
				JSONArray update_category = database.GET_CATEGORY_BY_CAT_ID(category_id);
				JSONObject cat_object = update_category.getJSONObject(0);
				String current_category_name = cat_object.getString("description");			
				
				//Si la descripcion de la categor�a es distinta a la actual.
				if (!description_category.equals(current_category_name)) {     
					
					APM.add_checked_menus ( p,  true,  category_id,  category_folder_name );
					
					//Al final el update para que si pincha antes no lo haga y salga. 
					database.UPDATE_DESCRIPTION_BY_CAT_ID(description_category, category_id);
					
				} else {
					
					//Si la descripcion es igual y el usuario agrega, no renombramos path ni carpeta solo agregamos. 						
					APM.add_checked_menus ( p,  false,  category_id,  category_folder_name );					
					
				}			
				
				// Obtengo los menus ids que fueron desasignados de la categoria			        
		        Object remove_menus_ids = (Object) p.getByDescId("remove_menus_ids");					
				
				if (remove_menus_ids instanceof Vector){
					
					Vector remove_ids = (Vector) p.getByDescId("remove_menus_ids");							
					
					//Itero los menus ids asignados a la categoria. 
					for (int i=0; i<remove_ids.size(); i++){						
						
						String menu_id = (String) remove_ids.get(i);
						
						APM.remove_menus_ids_by_id(menu_id, category_id, category_folder_name);
					}					
				} else {
					
					boolean has_data = p.getLengthDescById("remove_menus_ids");
					
					if (has_data){
						
						String menu_id = (String) p.getByDescId("remove_menus_ids");
						
						APM.remove_menus_ids_by_id(menu_id, category_id, category_folder_name);
					}				
				}	
			
				break;			
				
			case Constans.DELETE_CATEGORY:
				category_id = (String) p.getByDescId("category_id");						
				json = database.DELETE_CATEGORY(category_id);	
				break;
				
			case Constans.DELETE_CATEGORY_BY_CATEGORY_AND_MENU_ID:
				category_id = (String) p.getByDescId("category_id");
				menu_id = (String) p.getByDescId("category_id");
				json = database.DELETE_CATEGORY_BY_CATEGORY_AND_MENU_ID(category_id, menu_id);	
				break;
				
			case Constans.GET_MENU_ITEMS:							
				json = database.GET_MENU_ITEMS();			
				break;
				
			case Constans.GET_MENU_ITEM_BY_ID:
				menu_item_id = (String) p.getByDescId("menu_item_id");					
				json = database.GET_MENU_ITEM_BY_ID(menu_item_id);	
				
				break;
				
			case Constans.GET_MENU_ITEMS_BY_NAME:
				menu_item_name = (String) p.getByDescId("menu_item_name");		
				json = database.GET_MENU_ITEMS_BY_NAME(menu_item_name);				
				break;
				
			case Constans.GET_MENU_ITEMS_BY_DESCRIPTION:
				menu_item_description = (String) p.getByDescId("menu_item_description");							
				json = database.GET_MENU_ITEMS_BY_DESCRIPTION(menu_item_description);								
				break;
				
			case Constans.GET_MENU_ITEM_BY_PRICE:
				menu_item_price = (String) p.getByDescId("menu_item_price");							
				json = database.GET_MENU_ITEM_BY_PRICE(menu_item_price);					
				break;
				
			case Constans.GET_MENU_ITEMS_BY_CATEGORY_ID:
				category_id = (String) p.getByDescId("category_id");				
				menu_id = (String) p.getByDescId("menu_id");
				json = database.GET_MENU_ITEMS_BY_CATEGORY_ID(category_id, menu_id);				
				break;					
				
			case Constans.GET_CATEGORIES_AND_MENU_ITEMS_BY_MENU_ID:	
				
				menu_id = (String) p.getByDescId("menu_id");			 
						
				JSONArray jsonCategories = database.GET_CATEGORIES_BY_MENU_ID(menu_id);	
				
				for (int i = 0; i < jsonCategories.length(); i++) {
		             
					JSONObject menuObject = jsonCategories.getJSONObject(i);
					
		            JSONArray pCat = new JSONArray();
		            
		            int cat_id = menuObject.getInt("_id");        
		           
		            //pCat.put("_id",cat_id); 
		            //pCat.put("description", menuObject.getString("description"));	
		            
		            pCat.put(cat_id); 
		            pCat.put(menuObject.getString("description"));					
		            
		            JSONArray jsonGetMenuItem = database.GET_MENU_ITEM_THUMB_BY_MENU_ID(menu_id);	
		            
		            for (int j = 0; j < jsonGetMenuItem.length(); j++) { 
				    	 
				     	JSONObject obj_gmibcd;    
				     					     				     	
						try {
							
							obj_gmibcd = jsonGetMenuItem.getJSONObject(j);
							int men_it_id 					= obj_gmibcd.getInt("_id");
						    String men_it_name 				= obj_gmibcd.getString("name");
						    String men_it_description 		= obj_gmibcd.getString("description");
						    Double men_it_price 			= obj_gmibcd.getDouble("price");
						    int men_it_category_id 			= obj_gmibcd.getInt("category_id");  
						    String img_medium 				= obj_gmibcd.getString("img_medium");					    
						    
						    JSONObject json_menu_item = new JSONObject();
						    
						    json_menu_item.put("_id", men_it_name);
						    json_menu_item.put("name", men_it_id);
						    json_menu_item.putOpt("description", men_it_description);
						    json_menu_item.putOpt("price", men_it_price);
						    json_menu_item.putOpt("category_id", men_it_category_id);	
						    json_menu_item.putOpt("img_medium", img_medium);	
						  				  
						    
						    pCat.put(json_menu_item);
						    
						    Log.v("outPut", "men_it_id: " + men_it_id + " men_it_name:  " + men_it_name + " men_it_name:  " + men_it_description + " men_it_price:  " + men_it_price + " men_it_name:  " + men_it_category_id + " men_it_category_id:  ");
						    
						} catch (JSONException e) {						
							e.printStackTrace();
						}		                
		            
					json.put(pCat);		             
		            } 	            
		        }					
				break;	
			
			case Constans.GET_MENU_ITEM_SMALL_THUMB_BY_ID:
				menu_item = (String) p.getByDescId("menu_item");						
				json = database.GET_MENU_ITEM_SMALL_THUMB_BY_ID(menu_item);		
				break;
				
			case Constans.GET_MENU_ITEM_MEDIUM_THUMB_BY_ID:
				menu_item_id = (String) p.getByDescId("menu_item_id");						
				json = database.GET_MENU_ITEM_MEDIUM_THUMB_BY_ID (menu_item_id);		
				break;
			
			case Constans.GET_MENU_ITEM_LARGE_THUMB_BY_ID:
				menu_item = (String) p.getByDescId("menu_item");						
				json = database.GET_MENU_ITEM_LARGE_THUMB_BY_ID(menu_item);		
				break;
			
			case Constans.GET_MENU_ITEM_THUMBS_BY_ID:
				menu_item_id  = (String) p.getByDescId("menu_item_id ");						
				json = database.GET_MENU_ITEM_THUMBS_BY_ID(menu_item_id );		
				break;
				
			case Constans.GET_MENU_ITEM_VIDEOS:										
				json = database.GET_MENU_ITEM_VIDEOS();		
				break;
				
			case Constans.GET_MENU_ITEM_VIDEOS_BY_ID:
				menu_item_id  = (String) p.getByDescId("menu_item_id");						
				json = database.GET_MENU_ITEM_VIDEOS_BY_ID(menu_item_id);		
				break;
				
			case Constans.GET_MENU_ITEM_THUMB_BY_MENU_ITEM_CATEGORIES_ID :
				menu_item_categories_id  = (String) p.getByDescId("menu_item_categories_id");						
				json = database.GET_MENU_ITEM_THUMB_BY_MENU_ITEM_CATEGORIES_ID (menu_item_categories_id );		
				break;				
				
			case Constans.INSERT_MENU_ITEM:	
				
				menu_item_name = (String) p.getByDescId("item_name");
				Random r = new Random();
				menu_item_description = (String) p.getByDescId("item_description");
				menu_item_price = (String) p.getByDescId("item_price");
				menu_item_code = (String) p.getByDescId("item_code");	
								
				json = database.INSERT_MENU_ITEM(menu_item_name, menu_item_description, menu_item_price, menu_item_code);
					
				JSONArray ls = database.GET_LAST_MENU_ITEM();	
				JSONObject last_item_menu_object = ls.getJSONObject(0);
				String last_item_menu = last_item_menu_object.getString("_id");	
						
				Object inst_cats = (Object) p.getByDescId("categories_id"); 
				
				//Mando el ultimo parametro menu_item_id "" para que tome el last, no asi cuando hago update. 
				json = APM.insert_categories_id ( p, json, inst_cats, last_item_menu, null);					
				
				imageFile = null;
				videoFile = null;				
				
				break;
				
			case Constans.UPDATE_MENU_ITEM:					
								
				arr = new JSONArray();
				
				edit_item_name = (String) p.getByDescId("edit_item_name");
				edit_item_description = (String) p.getByDescId("edit_item_description");
				edit_item_price = (String) p.getByDescId("edit_item_price");
				edit_menu_item_id = (String) p.getByDescId("menu_item_id");
							
				edit_item_image = (String) p.getByDescId("edit_item_image");				
				edit_item_video = (String) p.getByDescId("edit_item_video");
				
				// ARRAY DE CATEGORIAS O CHECKBOXES CLIQUEADOS
				Object edit_categories_id = (Object) p.getByDescId("edit_categories_ids");			
				
				// ARRAY DE CATEGORIAS DE CHECKBOXES DESMARCADOS
				Object remove_categories_ids = (Object) p.getByDescId("remove_categories_ids");								
				
				// Con el menu item id obtengo el nombre del item y comparo con el nuevo nombre			
				JSONArray menu_item_by_id = database.GET_MENU_ITEM_BY_ID(edit_menu_item_id);	
				JSONObject menu_item_by_id_object = menu_item_by_id.getJSONObject(0);
				String get_menu_item_original_name = menu_item_by_id_object.getString("description");								
				
				get_menu_item_original_name = fUtils.removeExtUpperAddSlash(get_menu_item_original_name);
				
				//Recorro todas las categorias que vienen con items chequeados. 
				if (edit_categories_id instanceof Vector){
					
					Vector categories_ids = new Vector();
					categories_ids = (Vector) edit_categories_id;
					
					String categories_menus_id;
					String category_id;
					String menu_id; 
					
					boolean last;
					
					for(int i=0; i<categories_ids.size(); i++){						
						
						String menu_items_params = (String) categories_ids.get(i);
						
						String[] menu_items_params_array = menu_items_params.split("-");													
						
						category_id = menu_items_params_array[0];
						menu_id 	= menu_items_params_array[1];								
						
						APM.process_category (	
								edit_menu_item_id, p,
								menu_id,
								category_id,
								edit_item_name, 
								get_menu_item_original_name,
								edit_item_image,
								edit_item_video			
								);						
					} 		 
					
				} // Fin de recorrer todas las categorias que vienen con items checkeados.			
				
				// EXITE UNA POSIBILIDAD QUE ENTRE EN UN SOLO CHECK
				// ESTA ES QUITANDO EL UNICO EXISTENTE Y AGREGANDO UNO.
		
				else {
					
					String[] menu_items_params_array = ((String) edit_categories_id).split("-");													
					
					category_id = menu_items_params_array[0];
					menu_id 	= menu_items_params_array[1];								
					
					APM.process_category (	
							edit_menu_item_id, p,
							menu_id,
							category_id,
							edit_item_name, 
							get_menu_item_original_name,
							edit_item_image,
							edit_item_video			
							);					
				}
				
						
				// REMUEVO LA CATEGORIA DEL ITEM
				
				//Recorro las categorias que vienen con los items removidos
				if (remove_categories_ids instanceof Vector){					
					
					Vector categories_ids = new Vector();
					categories_ids = (Vector) remove_categories_ids;
					
					String categories_menus_id;
					String category_id;
					String menu_id; 
					
					boolean last;
					
					for(int i=0; i<categories_ids.size(); i++){						
						
						String menu_items_params = (String) categories_ids.get(i);						
						APM.remove_category_from_menu_item ( menu_items_params, edit_item_name, get_menu_item_original_name, edit_menu_item_id);					
					}	
					
				//Una sola categoria removida. No itero y borro directamente.
				} else {
						
					if (!remove_categories_ids.equals("")){
						APM.remove_category_from_menu_item ( remove_categories_ids.toString(), edit_item_name, get_menu_item_original_name, edit_menu_item_id );
					}
										
				}
						
				// IMAGEN O VIDEO NUEVAS.
				// SI LA IMAGEN O EL VIDEO CAMBIARNO LUEGO CAMBIO DE ITEM O AGREGAR O QUITAR CHECKBOX
				// DEBO VOLVER A RECORRER TODO DE NUEVO Y REEMPLAZAR TODOS LOS ARCHIVOS CON LA IMAGEN NUEVA
				// INCLUYENDO LOS REZISE DE IMAGEN Y VIDEO.  
				
				//Imagen nueva.
				if (!edit_item_image.equals("") || !edit_item_video.equals("") ) {					
					
					// TENGO FOTO, ITERO SOBRE TODAS LAS CATEGORIAS.
					// REEMPLAZO TODA LA MEDIA EXISTENTE POR LA NUEVA PROCESADA. 
					
					//Imagen nueva levanto del buffer y la llevo a todas las categorias la nueva imagen.					
					
					if (edit_categories_id instanceof Vector){
						
						Vector categories_ids = new Vector();
						categories_ids = (Vector) edit_categories_id;
						
						String categories_menus_id;
						String category_id;
						String menu_id; 
						
						boolean last;
						
						for(int i=0; i<categories_ids.size(); i++){						
							
							String menu_items_params = (String) categories_ids.get(i);
							
							String[] menu_items_params_array = menu_items_params.split("-");											
							
							category_id = menu_items_params_array[0];
							menu_id 	= menu_items_params_array[1];
							
							// BORRO ARCHIVOS ORIGINALES Y LOS REEMPLAZO CON EL BUFFER.
							
							if(i==(categories_ids.size()-1)){
								last = true;
							} else {
								last = false;
							}
							
							arr = APM.replace_file_media_process (									
									 edit_menu_item_id,
            						 edit_item_image,
            						 edit_item_video, p,
									 category_id, 
									 menu_id, 
									 edit_item_name, 
									 get_menu_item_original_name,									 
									 edit_categories_id,
									 arr, last									 
								);									
						}
					
					} 
					
					// TENGO FOTO Y UNA SOLA CATEGORIA MARCADA. 
					// REEMPLAZO TODA LA MEDIA EXISTENTE POR LA NUEVA PROCESADA. 
					else {
						
						String[] menu_items_params_array = ((String) edit_categories_id).split("-");											
						
						category_id = menu_items_params_array[0];
						menu_id 	= menu_items_params_array[1];
						
						// BORRO ARCHIVOS ORIGINALES Y LOS REEMPLAZO CON EL BUFFER.
												
						arr = APM.replace_file_media_process (		
								 edit_menu_item_id,
        						 edit_item_image,
        						 edit_item_video, p,
								 category_id, 
								 menu_id, 
								 edit_item_name, 
								 get_menu_item_original_name,									 
								 edit_categories_id,
								 arr, false								 
							);					
					}
					
					//VIDEO ??				
					//Video nuevo.
					
				}
				
				//if (!edit_item_video.equals("")){}
				
				// FINALMENTE Y DESPUES DE TANTO MANEJO 
				// DE DATOS Y ARCHIVOS ACTUALIZO EL MENU ITEM.
				
				/* Actualizo datos del menu item */
				json = database.UPDATE_MENU_ITEM ( edit_item_name, edit_item_description, edit_item_price, edit_menu_item_id );
					
				break;	
			
				
			case Constans.DELETE_MENU_ITEM:				
				menu_item_id = (String) p.getByDescId("menu_item_id");
				json = database.DELETE_MENU_ITEM(menu_item_id);				
				break;
				
			case Constans.INSERT_MENU_ITEM_THUMBNAIL:	
				menu_item_id = (String) p.getByDescId("menu_item_id");			
				img_small = (String) p.getByDescId("img_small");
				img_medium = (String) p.getByDescId("img_medium");
				img_large = (String) p.getByDescId("img_large");				
				json = database.INSERT_MENU_ITEM_THUMBNAIL(menu_item_id, img_small, img_medium, img_large);				
				break;
				
			case Constans.INSERT_MENU_ITEM_VIDEO:	
				menu_item_id = (String) p.getByDescId("menu_item_id");				
				video_path = (String) p.getByDescId("video_path");
				json = database.INSERT_MENU_ITEM_VIDEO(menu_item_id, video_path);				
				break;			
				
			case Constans.UPDATE_MENU_ITEM_THUMB:			
				path_to_small_image = (String) p.getByDescId("path_to_small_image");
				path_to_medium_image = (String) p.getByDescId("path_to_medium_image");
				path_to_large_image = (String) p.getByDescId("path_to_large_image");
				menu_item_id = (String) p.getByDescId("menu_item_id");				
				json = database.UPDATE_MENU_ITEM_THUMB(path_to_small_image, path_to_medium_image, path_to_large_image, menu_item_id);				
				break;
				
			case Constans.GET_MENU_ITEM_BY_CAT_ID :			
				category_id = (String) p.getByDescId("category_id");						
				json = database.GET_MENU_ITEM_BY_CAT_ID(category_id);				
				break;
				
			case Constans.UPDATE_MENU_ITEM_ORDER_COUNT :			
				menu_item_id = (String) p.getByDescId("menu_item_id");
				JSONArray jCOC = database.GET_CURRENT_ORDER_COUNT_BY_ITEM_ID(menu_item_id);		
				JSONObject orderCountObject = jCOC.getJSONObject(0);
				String order_count_by_item = orderCountObject.getString("order_count");	
				int plus_one = Integer.parseInt(order_count_by_item);
				plus_one++;
				order_count_by_item = String.valueOf(plus_one);
				json = database.UPDATE_MENU_ITEM_ORDER_COUNT(order_count_by_item);			
				break;
				
			case Constans.UPDATE_MENU_ITEM_VIDEO :
				path_to_the_video = (String) p.getByDescId("path_to_the_video");
				menu_item_id = (String) p.getByDescId("menu_item_id");
				json = database.UPDATE_MENU_ITEM_VIDEO(path_to_the_video, menu_item_id);
				break;	
				
			case Constans.CHECK_MENU_ITEM_CATEGORIES :
				categories_menus_id = (String) p.getByDescId("categories_menus_id");
				menu_item_id = (String) p.getByDescId("menu_item_id");
				json = database.CHECK_MENU_ITEM_CATEGORIES (categories_menus_id, menu_item_id);
				break;
				
			case Constans.GET_MENU_ITEM_THUMB_AND_VIDEO_BY_ID :
				menu_item_id   = (String) p.getByDescId("menu_item_id");				
				json = database.GET_MENU_ITEM_THUMB_AND_VIDEO_BY_ID (menu_item_id );
				break;	
				
			case Constans.SEND_COMMAND_BACKUP_DATABASE:
								
				include_backup_download   = (String) p.getByDescId("include");
				Boolean downloadatafile = Boolean.valueOf(include_backup_download);				
				comment_backup_download   = (String) p.getByDescId("comment");		
				
				File backUpDatabase;			
				try {					
					backUpDatabase = backup.backDatabase();					
				} catch (IOException e) {										
					json = APM.JSONReturn("status", e);
					e.printStackTrace();
					return json;				
				}				
				JSONObject jsonDatabase = new JSONObject(); 				
				String urlDatabase = "http://" + app.get_IP_SERVER() + ":" + Constans.WEB_SERVER_PORT + "/backup/originaldb/" + backUpDatabase.getParentFile().getName() + "/" + backUpDatabase.getName();				
				try {				
					if (downloadatafile) {						
						jsonDatabase.put("download", 1);					
						jsonDatabase.put("url", urlDatabase);						
					} else {						
						jsonDatabase.put("download", 0);						
					}							
				} catch (JSONException e) {				
					json = APM.JSONReturn("status", -1);
					json = APM.JSONReturn("error", e);
					e.printStackTrace();
					return json;					
				}				
				
				//log
				app.renderBackupLog("Database", urlDatabase, backUpDatabase.getName(), comment_backup_download);				
				json = APM.JSONReturn("status", 1);				
				json.put(jsonDatabase);
				
				break;
				
			case Constans.SEND_COMMAND_RESTORE_DATABASE:
				
				boolean restoreDatabase = false;
				try {
					restoreDatabase = backup.restoreDatabaseFromZip(databaseFile);
				} catch (IOException e) {
					json = APM.JSONReturn("status", e);
					e.printStackTrace();
				}
				
				int restore = Constans.SEND_COMMAND_RESTORE_DATABASE;					
				JSONObject restoreMessage = new JSONObject();
				restoreMessage.put("restore_message", restore);
				
				if(restoreDatabase){ 
					json = APM.JSONReturn("status", 1);
				} else {
					json = APM.JSONReturn("status", "Error al restaurar el archivo, la operaci�n no ha sido realizada.");
				}
							
				json.put(restoreMessage);
				
				// Reinicio de App 
				handler.post(new Runnable() {
					
				    public void run() {		    	    	
				    	
				    	Toast.makeText(cM, "Restore de Base de datos sQlite completo. \n\n  La aplicaci�n se cerrara y abrira nuevamente.",
		   		            	 Toast.LENGTH_LONG).show();		
				    	
				    	try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				    	System.exit(0);
				    }
				    
				 });	
				
				break;		
				
			case Constans.SEND_COMMAND_BACKUP_DATABASE_AND_MEDIA: 
				
				include_backup_download   = (String) p.getByDescId("include");
				Boolean downloadmediafile = Boolean.valueOf(include_backup_download);				
				comment_backup_download   = (String) p.getByDescId("comment");			
				String backUpFile;				
				try {
					backUpFile = backup.backUpAllData();
				} catch (IOException e) {					
					json = APM.JSONReturn("status", e);
					e.printStackTrace();
					return json;
				}				
				JSONObject jsonZip = new JSONObject();			
				String zipUrl = "http://" + app.get_IP_SERVER() + ":" + Constans.WEB_SERVER_PORT + "/backup/"+backUpFile;				
				try {				
					if (downloadmediafile) {						
						jsonZip.put("download", 1);					
						jsonZip.put("url", zipUrl);						
					} else {						
						jsonZip.put("download", 0);						
					}							
				} catch (JSONException e) {				
					json = APM.JSONReturn("status", -1);
					json = APM.JSONReturn("error", e);
					e.printStackTrace();
					return json;					
				}							
				//log
				app.renderBackupLog("All Media", zipUrl, backUpFile, comment_backup_download);				
				json = APM.JSONReturn("status", 1);				
				json.put(jsonZip);			
				break;	
				
			case Constans.SEND_COMMAND_RESTORE_DATABASE_AND_MEDIA:		
				
				int restore_dam = Constans.RESTORED_NONE;			
					
				restore = backup.restoreAllData ( backpupFile );				 
									
				JSONObject restoreDamMessage = new JSONObject();
				restoreDamMessage.put("restore_message", restore_dam);
				
				switch (restore)
				{
				
					case Constans.RESTORED_MEDIA_AND_DATABASE:					
						json = APM.JSONReturn("status", 1);
						break;
						
						
					case Constans.RESTORED_MEDIA:					
						json = APM.JSONReturn("status", 1);
						break;
					
					case Constans.RESTORED_NONE:					
						json = APM.JSONReturn("status", -1); 
						restoreDamMessage.put("error", "Error al restaurar el archivo, la operaci�n no ha sido realizada.");
						
						break;
						
				}
				json.put(restoreDamMessage);
				
				if (restore== Constans.RESTORED_MEDIA_AND_DATABASE) {
				
					// Reinicio de App 
					handler.post(new Runnable() {
						
					    public void run() {		    	    	
					    	
					    	Toast.makeText(cM, "Restore de Media mas Base de datos sQlite completo. \n\n La aplicaci�n se cerrara y abrira nuevamente.",
			   		            	 Toast.LENGTH_LONG).show();		
					    	
					    	try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					    	
					    	System.exit(0);
					    }
					    
					 });
					
				};
				
				break;
				
			case Constans.SEND_MESSAGE_TO_ALL_CLIENTS:
				break;
				
			case Constans.SEND_MESSAGE_TO_MASTER:
				break;
				
			case Constans.SEND_MESSAGE_TO_CLIENT_BY_CLIENT_ID:
				break;			
		
			case Constans.CREATE_ORDER: 
				
				order_total = (String) p.getByDescId("order_total");				
							
				user_id = (String) p.getByDescId("user_id");					
				order_status_id = (String) p.getByDescId("order_status_id");				
				order_dining_table_id = (String) p.getByDescId("order_dining_table_id");			
				order_observation = (String) p.getByDescId("order_observation");		  	
								
				json = database.CREATE_ORDER(order_dining_table_id, order_observation, order_status_id,  order_total, user_id);
				
				// REFRESH ADMIN TABLE 
                app.refreshAdminTables();                
				
				break;			
				
			case Constans.UPDATE_ORDER:
				
				order_dining_table_id  = (String) p.getByDescId("order_dining_table_id");			
				order_observation = (String) p.getByDescId("order_observation");			
				order_status_id = (String) p.getByDescId("order_status_id");			
				order_total = (String) p.getByDescId("order_total");				
				user_id = (String) p.getByDescId("user_id");				
				order_id = (String) p.getByDescId("order_id");			
								
				json = database.UPDATE_ORDER (order_dining_table_id, order_observation, order_status_id, order_total, user_id, order_id);
				
				// REFRESH ADMIN TABLE 
                app.refreshAdminTables();  
				
				break;
			
			case Constans.DELETE_ORDER:				
				order_id = (String) p.getByDescId("order_id");			
				json = database.DELETE_ORDER(order_id);		
				
				// REFRESH ADMIN TABLE 
                app.refreshAdminTables();  
				
				break;
				
			case Constans.GET_ORDERS:
				json = database.GET_ORDERS();			
				break;
				
			case Constans.GET_ORDER_DETAIL:
				order_id = (String) p.getByDescId("order_id");			
				json = database.GET_ORDER_DETAIL(order_id);	
				break;
				
			case Constans.GET_ORDER_STATUS:
				json = database.GET_ORDER_STATUS();	
				break;
				
			case Constans.GET_DINING_TABLE_STATUS:
				json = database.GET_DINING_TABLE_STATUS();	
				break;
				
			case Constans.GET_DINING_TABLES:
				json = database.GET_DINING_TABLES();	
				break;	
				
			case Constans.GET_TOTAL_ORDER:
				order_id = (String) p.getByDescId("order_id");			
				json = database.GET_TOTAL_ORDER(order_id);	
				break;	

			case Constans.UPDATE_TOTAL_ORDER:
				order_id = (String) p.getByDescId("order_id");			
				json = database.UPDATE_TOTAL_ORDER(order_id);	
				break;				
				
			case Constans.GET_ORDER_BY_TABLE_STATUS:			
				user_id = (String) p.getByDescId("user_id");
				order_status_id_1 = (String) p.getByDescId("order_status_id_1");
				order_status_id_2 = (String) p.getByDescId("order_status_id_2");
				dining_table_status_id_1 = (String) p.getByDescId("dining_table_status_id_1");			
				dining_table_status_id_2 = (String) p.getByDescId("dining_table_status_id_2");						
				json = database.GET_ORDER_BY_TABLE_STATUS(user_id, order_status_id_1,order_status_id_2, dining_table_status_id_1, dining_table_status_id_2);	
				break;
				
			case Constans.GET_LAST_ORDER_ID:
				json = database.GET_LAST_ORDER_ID();			
				break;
				
			case Constans.INSERT_ORDER_DETAIL:				
				
				quantity = (String) p.getByDescId("quantity");				
				menu_item_categories_id = (String) p.getByDescId("menu_item_categories_id");				
				price = (String) p.getByDescId("price");
				addon_id = (String) p.getByDescId("addon_id");				
				subtotal = (String) p.getByDescId("subtotal");			
				order_id = (String) p.getByDescId("order_id");		
				
				
				json = database.INSERT_ORDER_DETAIL (order_id, menu_item_categories_id, quantity, price, addon_id, subtotal, Constans.ORDER_DETAIL_NOT_PRINTED);
				
				database.GET_CURRENT_TOTAL_ORDER(order_id, subtotal);
				
				break;
				
			case Constans.INSERT_UPDATE_ORDERS_DETAILS:
				
				count = (String) p.getByDescId("count");
				dining_table_id = (String) p.getByDescId("dining_table_id");
                int c = Integer.parseInt(count);                
                Hashtable orders = new Hashtable();     
                JSONArray allOrders = new JSONArray();                
                for (int t=0; t<c; t++){                                 
                    String order = "order_" + t;
                    Vector details_vector = (Vector) p.getByDescId(order);              
                    for (int i=0; i<details_vector.size(); i++){                 
                        String valVector = (String) details_vector.get(i);
                        valVector = valVector.replaceAll("\"", "");     
                        String[] splitVal = valVector.split(":");                       
                        String param = splitVal[0];
                        String value = splitVal[1];                     
                        orders.put(param, value);                                           
                    }                               
                    order_detail_id = (String) orders.get("order_detail_id");                   
                    int checker = Integer.parseInt(order_detail_id);
                    quantity = (String) orders.get("quantity");
                    menu_item_categories_id = (String) orders.get("menu_item_categories_id");
                    price = (String) orders.get("price");
                    subtotal = (String) orders.get("subtotal");
                    addon_id = (String) orders.get("addon_id");
                    order_id = (String) orders.get("order_id");                                             
                    if ( checker > 0 ){                  
                        json = database.UPDATE_ORDER_DETAIL (order_detail_id, order_id, menu_item_categories_id, addon_id, quantity, price, subtotal);                    
                    } else {
                    	json = database.INSERT_ORDER_DETAIL (order_id, menu_item_categories_id, quantity, price, addon_id, subtotal, Constans.ORDER_DETAIL_NOT_PRINTED);                    
                    }
                    database.GET_CURRENT_TOTAL_ORDER(order_id, subtotal);                    
                    allOrders.put(json);                   
                }                       
                json = allOrders;    
                
                // UPDATE DATABASE TO BLINK ON 
                if (database.SET_COMMAND_BLINK_TABLE_BY_TABLE_ID(dining_table_id, "1")){                
                	//REFRESH ADMIN PANEL
                	app.refreshAdminTables();
                }
                
       		 	//SEND UPDATE SOCKET TO WEB
        		sendUpdateOrderToSocket(Constans.SEND_COMMAND_PRINT_ORDER_ITEM, order_id);
                
                break;  

				
			case Constans.UPDATE_ORDER_BY_STATUS:					
				order_status_id = (String) p.getByDescId("order_status_id");
				order_id = (String) p.getByDescId("order_id");			
				json = database.UPDATE_ORDER_BY_STATUS(order_status_id, order_id);				
				break;	
				
			case Constans.UPDATE_ORDER_DETAIL:									
				quantity = (String) p.getByDescId("quantity");				
				menu_item_categories_id = (String) p.getByDescId("menu_item_categories_id");				
				price = (String) p.getByDescId("price");
				addon_id = (String) p.getByDescId("addon_id");				
				subtotal = (String) p.getByDescId("subtotal");			
				order_id = (String) p.getByDescId("order_id");			
				json = database.UPDATE_ORDER_DETAIL (order_detail_id, order_id, menu_item_categories_id, addon_id, quantity, price, subtotal);		
				break;
			
			case Constans.GET_NOT_PRINTED_ORDER_DETAILS:
				order_id = (String) p.getByDescId("order_id");					
				json = database.GET_NOT_PRINTED_ORDER_DETAILS(order_id);	
				break;
				
			case Constans.UPDATE_PRINTED_ORDER:
				order_id = (String) p.getByDescId("order_id");					
				json = database.UPDATE_PRINTED_ORDER (order_id);	
				break;
				
			case Constans.UPDATE_PRINTED_ORDER_DETAIL:
				order_detail_id  = (String) p.getByDescId("order_detail_id");					
				json = database.UPDATE_PRINTED_ORDER_DETAIL (order_detail_id );	
				break;				 
				
			case Constans.GET_ORDER_DETAIL_STATUS:
				order_detail_id  = (String) p.getByDescId("order_detail_id");					
				json = database.GET_ORDER_DETAIL_STATUS  (order_detail_id );	
				break;
				
			case Constans.GET_ORDER_BY_ID:
				order_id  = (String) p.getByDescId("order_id");					
				json = database.GET_ORDER_BY_ID (order_id );	
				break;
				
			case Constans.DELETE_ORDER_DETAIL:					
				order_detail_id  = (String) p.getByDescId("order_detail_id");					
				json = database.DELETE_ORDER_DETAIL(order_detail_id);				
				break;				
				
			case Constans.UPDATE_DINING_TABLE_STATUS:				
				
				dining_table_id = (String) p.getByDescId("dining_table_id");			
				dining_table_status_id = (String) p.getByDescId("dining_table_status_id");			
				order_id = (String) p.getByDescId("order_id");				
				
				json = database.UPDATE_DINING_TABLE_STATUS (dining_table_id, dining_table_status_id);
				
				// Si el estado de la mesa es CERRAR entonces se manda a imprimir el ticket. 
				if ( Integer.parseInt(dining_table_status_id) == Constans.DINING_TABLE_STATUS_CLOSE ){
					// Actualizo el estado de la orden
					database.UPDATE_ORDER_BY_STATUS("4", order_id); 
					//SEND UPDATE SOCKET TO WEB
					sendUpdateOrderToSocket(Constans.SEND_COMMAND_PRINT_ORDER, order_id);
				} else if ( Integer.parseInt(dining_table_status_id) == Constans.DINING_TABLE_STATUS_OPEN ){
					//SEND UPDATE SOCKET TO WEB
					sendUpdateOrderToSocket(Constans.SEND_COMMAND_DINING_TABLE_OPENED, order_id);
				}			
				break;
				
			case Constans.SEND_COMMAND_IMPORT_GDOCS_FILE:		
				
				waiting_gdoc_import =  false; 
				waiting_gdoc_import_with_media = false;
				
				item_image = (String) p.getByDescId("item_image");
				
				user = (String) p.getByDescId("username");
				pass = (String) p.getByDescId("password");
				doc = (String) p.getByDescId("documento");
					
				boolean include_media=false;
					
				if (!item_image.equals("") && this.backpupFile!=null) {
					include_media=true;					
					this.backup.restoreMediaFromZip(this.backpupFile);
				} 
					
				GoogleSpreadSheetTab gdocs = new GoogleSpreadSheetTab(app, database, user, pass, doc, include_media);
					
				gdocs.setSpreadSheetLoaderListener(new SpreadSheetLoaderListener(){

					@Override
					public void onSpreadSheetLoaded	() {
						waiting_gdoc_import = true;									
					}

					@Override
					public void onSpreadSheetLoadedWithMedia	() {
						waiting_gdoc_import_with_media = true;
						backpupFile=null;									
					}
				
				});
				
				gdocs.syncronize();
				
				if (!include_media)
				{
					while(!waiting_gdoc_import)
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}          
					}
				} else {
					while(!waiting_gdoc_import_with_media)
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}  
					}
				}
				
						
				json = APM.JSONReturn("status", 1);				
							
				break;
				
			
			case Constans.SEND_COMMAND_IMPORT_EXCEL_FILE:	
				
				ImportExcel importexcel = new ImportExcel(this.cM);
				importexcel.setInputFiles(importFile.toString(), this.backpupFile);
				
				boolean imp = false;
				
				try {
					imp = importexcel.importData(cM, this, this.database, this.folderHandler, this.APM);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(imp){ 
					json = APM.JSONReturn("status", 1);
				} else {
					json = APM.JSONReturn("status", "Error al restaurar el archivo, la operaci�n no ha sido realizada.");
				}
				
				//log
				//app.renderMediaLog(Constans.SEND_COMMAND_IMPORT_EXCEL_FILE, "Excel: " + this.backpupFile.toString());
				
				break;
				
			case Constans.CREATE_USER:
				
				username = (String) p.getByDescId("username");				
				password = (String) p.getByDescId("password");								
				first_name = (String) p.getByDescId("first_name");			
				last_name = (String) p.getByDescId("last_name");				
				email = (String) p.getByDescId("email");		
				status = (String) p.getByDescId("status");		
				profile_id = (String) p.getByDescId("profile_id");				
								
				json = database.CREATE_USER(username, password, first_name, last_name, email, status, profile_id);		
				break;
				
			case Constans.UPDATE_USER:		
				
				user_id = (String) p.getByDescId("user_id");
				first_name = (String) p.getByDescId("first_name");		
				username = (String) p.getByDescId("username");						
				status = (String) p.getByDescId("status");			
				email = (String) p.getByDescId("email");			
				last_name = (String) p.getByDescId("last_name");					
				password = (String) p.getByDescId("password");
				profile_id = (String) p.getByDescId("profile_id");				

				json = database.UPDATE_USER(username, password, first_name, last_name, email, status, profile_id, user_id);		
				break;
			
			case Constans.UPDATE_STATUS_USER:		
				user_id = (String) p.getByDescId("user_id");		
				status = (String) p.getByDescId("status");			
				json = database.UPDATE_STATUS_USER(user_id, status);		
				break;
				
			case Constans.DELETE_USER:				
				user_id = (String) p.getByDescId("user_id");				
				json = database.DELETE_USER(user_id);			
				break;
			
			case Constans.GET_USER_BY_ID:				
				user_id = (String) p.getByDescId("user_id");					
				json = database.GET_USER_BY_ID(user_id);					
				break;
				
			case Constans.GET_USERS:
				json = database.GET_USERS();			
				break;
				
			case Constans.GET_USER_PROFILES:
				
				username = (String) p.getByDescId("username");					
							
				json = database.GET_USER_PROFILES();			
				break;
				
			case Constans.GET_USERS_BY_STATUS:
				status = (String) p.getByDescId("status");					
				json = database.GET_USERS_BY_STATUS(status);			
				break;
				
			case Constans.CHECK_USER:
				
				username = (String) p.getByDescId("username");		
				username = username.replaceFirst("username=", "");				
				password = (String) p.getByDescId("password");		
						
				json = database.CHECK_USER(username, password);			
				break;
			
			case Constans.CREATE_DINING_TABLE:
				
				description = (String) p.getByDescId("description");						
				dining_table_status_id = (String) p.getByDescId("dining_table_status_id");		
				dining_table_id = (String) p.getByDescId("dining_table_id");
				json = database.CREATE_DINING_TABLE(description, dining_table_status_id);		
								
                // UPDATE DATABASE TO BLINK ON 
                /*if (database.SET_COMMAND_BLINK_TABLE_BY_TABLE_ID(dining_table_id, "1")){                
                	//REFRESH ADMIN PANEL
                	app.refreshAdminTables();
                }*/
				
				break;
			
			case Constans.UPDATE_DINING_TABLE:
				   
				dining_table_id = (String) p.getByDescId("dining_table_id");					
				dining_table_status_id = (String) p.getByDescId("dining_table_status_id");		
				dining_table_description = (String) p.getByDescId("dining_table_description");			
				
				json = database.UPDATE_DINING_TABLE(dining_table_description, dining_table_status_id, dining_table_id);			
				break;
				
			case Constans.DELETE_DINING_TABLE:				
				dining_table_id = (String) p.getByDescId("dining_table_id");			
				json = database.DELETE_DINING_TABLE(dining_table_id);			
				break;
				
			case Constans.GET_DINING_TABLE_BY_ID:				
				dining_table_id = (String) p.getByDescId("dining_table_id");				
				json = database.GET_DINING_TABLE_BY_ID(dining_table_id);			
				break;
				
			case Constans.GET_DINING_TABLE_BY_STATUS:				
				dining_table_status_id = (String) p.getByDescId("dining_table_status_id");				
				json = database.GET_DINING_TABLE_BY_STATUS(dining_table_status_id);			
				break;
				
			case Constans.GET_DINING_TABLE_BY_USER:				
				user_id = (String) p.getByDescId("user_id");				
				json = database.GET_DINING_TABLE_BY_USER(user_id);			
				break;
				
			case Constans.GET_COMANDAS:				
				json = database.GET_COMANDAS();			
				break;
				
			case Constans.GET_ORDERS_AND_DETAIL:				
				user_id = (String) p.getByDescId("user_id");			
				json = database.GET_ORDERS_AND_DETAIL(user_id);			
				break;			
				
			case Constans.INSERT_MENUS_ITEMS_CATEGORIES:
				menu_item_id = (String) p.getByDescId("menu_item_id");		
				categories_menus_id = (String) p.getByDescId("categories_menus_id");	
				json = database.INSERT_MENUS_ITEMS_CATEGORIES(menu_item_id, categories_menus_id);	
				break;	
				
			case Constans.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID:
				category_id = (String) p.getByDescId("category_id");
				menu_id = (String) p.getByDescId("menu_id");	
				json = database.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID(category_id, menu_id);	
				break;		
				
			case Constans.GET_CATEGORIES_MENUS_ID:
				category_id = (String) p.getByDescId("category_id");
				menu_id = (String) p.getByDescId("menu_id");	
				json = database.GET_CATEGORIES_MENUS_ID(category_id, menu_id);	
				break;			
				
			case Constans.CREATE_MENU_ITEM_ADDON:			
				description = (String) p.getByDescId("description");
				menu_item_id = (String) p.getByDescId("menu_item_id");							
				json = database.CREATE_MENU_ITEM_ADDON(description, menu_item_id);				
				break;		
			
			case Constans.UPDATE_MENU_ITEM_ADDON:				
				description = (String) p.getByDescId("description");
				menu_item_id = (String) p.getByDescId("menu_item_id");
				addon_id = (String) p.getByDescId("addon_id");			
				json = database.UPDATE_MENU_ITEM_ADDON(description, menu_item_id, addon_id);			
				break;
				
			case Constans.DELETE_MENU_ITEM_ADDON:				
				addon_id = (String) p.getByDescId("addon_id");
				json = database.DELETE_MENU_ITEM_ADDON(addon_id);			
				break;
				
			case Constans.GET_MENU_ITEM_ADDONS:								
				json = database.GET_MENU_ITEM_ADDONS();			
				break;
				
			case Constans.GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID:
				menu_item_id = (String) p.getByDescId("menu_item_id");
				json = database.GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID(menu_item_id);			
				break;						
			
			case Constans.GET_MENU_ITEM_ADDON_BY_ID:				
				addon_id = (String) p.getByDescId("addon_id");
				json = database.GET_MENU_ITEM_ADDON_BY_ID(addon_id);			
				break;
				
			case Constans.GET_ALL_MENU_ITEMS_BY_MENU_ID:			
				menu_id = (String) p.getByDescId("menu_id");
				json = database.GET_ALL_MENU_ITEMS_BY_MENU_ID(menu_id);			
				break;
			
				
			case Constans.GET_MOST_POPULAR_MENU_ITEMS:			
				json = database.GET_MOST_POPULAR_MENU_ITEMS();			
				break;
				
			case Constans.GET_ALL_MENU_ITEMS:			
				json = database.GET_ALL_MENU_ITEMS();
				break;
			
			case Constans.GET_ALL_MENU_DATA:			
				json = database.GET_ALL_MENU_DATA();
				break;
				
			case Constans.CREATE_OPTION:
				option_name = (String) p.getByDescId("option_name");
				option_value = (String) p.getByDescId("option_value");
				json = database.CREATE_OPTION(option_name, option_value);
				break;	
				
			case Constans.UPDATE_OPTION:
				option_name = (String) p.getByDescId("option_name");
				option_value = (String) p.getByDescId("option_value");
				option_id = (String) p.getByDescId("option_id");
				json = database.UPDATE_OPTION(option_name, option_value, option_id);
				break;	
			
			case Constans.DELETE_OPTION:
				option_id = (String) p.getByDescId("option_id");				
				json = database.DELETE_OPTION(option_id);
				break;
						
			case Constans.GET_OPTIONS:				
				json = database.GET_OPTIONS();
				break;
				
			case Constans.GET_OPTION_BY_ID:
				option_id = (String) p.getByDescId("option_id");				
				json = database.GET_OPTION_BY_ID(option_id);
				break;
				
			case Constans.GET_OPTIONS_BY_NAME:
				option_name = (String) p.getByDescId("option_name");
				json = database.GET_OPTIONS_BY_NAME(option_name);
				break;			
							
		}		
		
		return json;
		
	}	
	
	
	
	/**
	 * Send Update Order To Socket ADMIN
	 */
	private void sendUpdateOrderToSocket( int action, String order_id ){
	
	    JSONArray socketUpdate = new JSONArray();
	    JSONObject socketObj = new JSONObject();	
	    
	    try {
			
	    	socketObj.put("action", action);
	    	socketObj.put("order_id", order_id);
			socketUpdate.put(socketObj);			    
			client.send(socketUpdate.toString());
			 
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	   
	    
	}
	
	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		APM.setImageFile(imageFile);
		this.imageFile = imageFile;
	}

	public File getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(File videoFile) {
		APM.setVideoFile(videoFile);
		this.videoFile = videoFile;
	}
	
	public File getBackpupFile() {
		return backpupFile;
	}

	public void setBackpupFile(File backpupFile) {
		APM.setBackpupFile(backpupFile);
		this.backpupFile = backpupFile;
	}
	
	public File getDatabaseFile() {
		return databaseFile;
	}

	public void setDatabaseFile(File databaseFile) {
		APM.setDatabaseFile(databaseFile);
		this.databaseFile = databaseFile;
	}
	
	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		APM.setImportFile(importFile);
		this.importFile = importFile;
	}
	
	
}	
	
	
		
	