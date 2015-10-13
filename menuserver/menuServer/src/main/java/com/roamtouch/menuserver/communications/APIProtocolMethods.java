package com.roamtouch.menuserver.communications;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.FileUtils;
import com.roamtouch.menuserver.utils.FolderHandler;
import com.roamtouch.menuserver.utils.ImageResize;
import com.roamtouch.menuserver.utils.Parser;

public class APIProtocolMethods {
	
	MenuServer cM;
	FolderHandler fH;
	JSONArray json;
	JSONObject partial;
	DataBaseController database;
	
	//Uploaded files
	private File imageFile;
	private File videoFile;
	private File backpupFile;
	private File importFile;
	private File databaseFile;
	
	private File destination_video;
	
	String base_path;
	
	private FileUtils fUtils = new FileUtils();
	
	private MenuServerApplication app;
	
	ImageResize resize;
	
	public APIProtocolMethods( MenuServer cM, DataBaseController database, FolderHandler fH,  ImageResize resize){
		this.cM = cM;
		this.database = database;
		this.fH = fH;		
		this.resize = resize;
		app = ((MenuServerApplication)cM.getApplication());
		base_path = app.getSDCARD();
	}
	
	public JSONArray insert_category_by_id (	
			
			String id, 
			JSONArray arr, 
			JSONArray bigArr,			
			String folder_name,
			String description_category,
			boolean fromImport
			
			)  {	
		
		JSONArray last_cat = null;	
		String insert_menu_folder_name = null;
		
		JSONObject last_cat_object;
		
		try {
			
			last_cat = database.GET_LAST_CATEGORY_ID();
			
			last_cat_object = last_cat.getJSONObject(0);
			
			String last_category = last_cat_object.getString("_id");					
			
			arr = database.INSERT_CATEGORY_PER_MENU_ID(last_category, id);
			bigArr.put(arr);	
			
			JSONArray menu = database.GET_MENU_FOLDER_NAME(id);			
			JSONObject menu_folder_name_object = menu.getJSONObject(0);
			insert_menu_folder_name = menu_folder_name_object.getString("folder_name");	
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
					
										
		//Busco la carpeta menu si existe.
		String all_path =  "httpd/media/menus/" + insert_menu_folder_name + "/categories/" + folder_name;				
		File new_file = new File(all_path);							
		
		//Si no existe la creo, si existe la levanto.				
		File newCategory = null;
		
		if(!new_file.isDirectory()){
			newCategory = fH.copyFilestoSdcard(new_file.toString(), true);
		}
		
		if (!fromImport){
		
			try {
				
				if(newCategory.exists()){
					
					json = database.INSERT_CATEGORY (description_category, folder_name);
					
				} else {
					String insert_menu_item_error = Integer.toString(Constans.CANNOT_CREATE_CATEGORY_FOLDER);				 
					partial.put("error", insert_menu_item_error);	
					json.put(partial);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
		return json;
		
	}
	
	/**
	 * remove_menus_ids_by_id
	 * @param menu_id
	 * @param category_id
	 * @param category_folder_name
	 * @throws JSONException
	 */
	public void remove_menus_ids_by_id (String menu_id, String category_id, String category_folder_name) throws JSONException {
		
		//Consulto si la categoria existe en la table categories_menus.
		JSONArray has = database.GET_CATEGORY_BY_ID(category_id, menu_id);
		JSONObject has_cat_object = has.getJSONObject(0);
		String id_last_cat = has_cat_object.getString("_id");
		
		if (!id_last_cat.equals("")){		
			
			//Obtengo el nombre de la carpeta del menu que fue desasignado
			JSONArray menu = database.GET_MENU_FOLDER_NAME(menu_id);			
			JSONObject menu_folder_name_object = menu.getJSONObject(0);
			String menu_folder_name = menu_folder_name_object.getString("folder_name");	
			
			//ELimino la carpeta de la categoria del menu asignado						
			JSONArray c = database.GET_CATEGORY_FOLDER_NAME(category_id);			
			JSONObject category_folder_name_object = c.getJSONObject(0);
			category_folder_name = category_folder_name_object.getString("folder_name");
			
			//Elimino directorio
			String delete_cat_path =  base_path + "/httpd/media/menus/" + menu_folder_name + "/categories/" + category_folder_name;
			File delete_cat_file = new File(delete_cat_path);							
			
			//Remuevo items dentro de la carpeta antes de borrarla. 
			if (delete_cat_file.isDirectory()) {
		        String[] children = delete_cat_file.list();
		        for (int i = 0; i < children.length; i++) {
		            new File(delete_cat_file, children[i]).delete();
		        }
		    }
			
			//Borra carpeta.
	        boolean success_category = delete_cat_file.delete();
	        if (!success_category) {
	            System.err.println("Error");
	        } else {						
	        	//Elimino la catagoria asignada al menu_id en cuenstion. 
	        	database.DELETE_CATEGORY_BY_CATEGORY_AND_MENU_ID(category_id, menu_id);					        	
	        }			
		}		
	}
	
	
	public void add_menus_ids_by_id (boolean modified_description, String menu_id, String category_id, String new_category_folder_name) throws JSONException {
		
		//Consulto si la categor�a existe en la tabla categories_menus.
		JSONArray has = database.GET_CATEGORY_BY_ID(category_id, menu_id);
		JSONObject has_cat_object = has.getJSONObject(0);
		String id_has_cat = has_cat_object.getString("_id");		

		JSONArray menu = database.GET_MENU_FOLDER_NAME(menu_id);			
		JSONObject menu_folder_name_object = menu.getJSONObject(0);
		String menu_folder_name = menu_folder_name_object.getString("folder_name");	
		
		//Directorio nueva categoria			
		String new_cat_path =  base_path + "/httpd/media/menus/" + menu_folder_name + "/categories/" + new_category_folder_name;
		File new_cat_folder = new File(new_cat_path);	
		
		if (!id_has_cat.equals("") && modified_description){				
			
			//ELimino la carpeta de la categoria del menu asignado						
			JSONArray c = database.GET_CATEGORY_FOLDER_NAME(category_id);			
			JSONObject category_folder_name_object = c.getJSONObject(0);
			String category_folder_name = category_folder_name_object.getString("folder_name");
			
			//Directorio vieja categoria			
			String old_cat_path =  base_path + "/httpd/media/menus/" + menu_folder_name + "/categories/" + category_folder_name;
			File old_cat_folder = new File(old_cat_path);						       
	        
	        boolean success_rename_category = old_cat_folder.renameTo(new_cat_folder);
	        if (!success_rename_category) {
	            System.err.println("Error");
	        }	
	        
	        JSONArray menu_items_by_category = database.GET_MENU_ITEMS_BY_CATEGORY_ID(category_id, menu_id);

	        for (int t = 0; t < menu_items_by_category.length(); t++) {				        	
	        
	             JSONObject catsObject = menu_items_by_category.getJSONObject(t);
	             String menu_item_id = catsObject.getString("_id");	
	             
	             JSONArray menu_items_thumbs_by_menu_item_id = database.GET_MENU_ITEM_THUMBS_BY_ID(menu_item_id);
	             JSONObject menu_items_thumbs_by_menu_item_id_object = menu_items_thumbs_by_menu_item_id.getJSONObject(0);    
	             
	             
	             String menu_item_image_small = menu_items_thumbs_by_menu_item_id_object.getString("img_small");					             
	             String menu_item_image_medium = menu_items_thumbs_by_menu_item_id_object.getString("img_medium");
	             String menu_item_image_large = menu_items_thumbs_by_menu_item_id_object.getString("img_large");				             
	             
	             String replace = "/" + category_folder_name + "/";
	             String to_replace = "/" + new_category_folder_name + "/";
	             
	             menu_item_image_small = menu_item_image_small.replace(replace, to_replace);
	             menu_item_image_medium = menu_item_image_medium.replace(replace, to_replace);
	             menu_item_image_large = menu_item_image_large.replace(replace, to_replace);
	             
	             database.UPDATE_MENU_ITEM_THUMB(menu_item_image_small, menu_item_image_medium, menu_item_image_large, menu_item_id);
			
	        }	        
		
		} else if ((id_has_cat.equals("") && modified_description) || (id_has_cat.equals("") && !modified_description)) {
			
			//Si encuentra registro, hacer insert.
			database.INSERT_CATEGORY_PER_MENU_ID(category_id, menu_id);						 
			
			//Creo el directorio
			try {
				new_cat_folder.createNewFile(); 
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
		} 
			
	}
	
	/**
	 * 
	 * @param p
	 * @param modified_description
	 * @param category_id
	 * @param category_folder_name
	 * @throws JSONException
	 */
	public void add_checked_menus (Parser p, boolean modified_description, String category_id, String category_folder_name ) throws JSONException{
		
		Object updt_cat = (Object) p.getByDescId("menus_ids");		        
		
		if (updt_cat instanceof Vector){
			
			Vector cats_ids = (Vector) p.getByDescId("menus_ids");											
			
			//Itero los menus ids asignados a la categoria. 
			for (int i=0; i<cats_ids.size(); i++){
				
				String menu_id = (String) cats_ids.get(i);							
				add_menus_ids_by_id(modified_description, menu_id, category_id, category_folder_name);
				
			}						
			
		} else {
			
			//un solo menu_id
			String menu_id = (String) p.getByDescId("menus_ids");	
			add_menus_ids_by_id( modified_description, menu_id, category_id, category_folder_name);
			
		}	
		
	}
	
	/**
	 * process_category
	 * @param p
	 * @param menu_id
	 * @param category_id
	 * @param edit_item_name
	 * @param get_menu_item_original_name
	 * @param edit_item_image
	 * @param edit_item_video
	 * @throws JSONException
	 */
	public void process_category (	
			String edit_menu_item_id,
			Parser p,
			String menu_id,
			String category_id,
			String edit_item_name, 
			String get_menu_item_original_name,
			String edit_item_image,
			String edit_item_video			
			) throws JSONException{
		
		JSONArray categories_menus_id_array = database.GET_CATEGORIES_MENUS_ID(category_id, menu_id);
		JSONObject categories_menus_id_object = categories_menus_id_array.getJSONObject(0);
		String cat_menus_id = categories_menus_id_object.getString("_id");
		
		//Verifico si existe la categoria asignada al menu item.
		JSONArray jCMIC = database.CHECK_MENU_ITEM_CATEGORIES(cat_menus_id, edit_menu_item_id);
		
		String menu_item_category_id;						
		if (jCMIC.length()>0){
			JSONObject checkMenuItemCatObject = jCMIC.getJSONObject(0);
			menu_item_category_id = checkMenuItemCatObject.getString("_id");
		} else {
			menu_item_category_id = "";
		}
		
		File current_file_path = get_current_path(category_id, menu_id);		

		/*
		 * Si el item name es distinto al actual, hago:
		 *  - renanme de archivo 
		 *  - update path en tabla menu items
		 *  - update de categorias asignadas
		 */		
		String new_edit_item_name = fUtils.removeExtUpperAddSlash(edit_item_name);				
		
		//Variable que verifica si el nombre del itme es nuevo para ser pasado como parametro a coreEditMenuItem();
		boolean item_is_new;
		
		//Si el nombre del item nuevo es distinto del actual.				
		if (!get_menu_item_original_name.equals(new_edit_item_name)){	
			item_is_new = true;
		} else {
			item_is_new = false;
		}
			
		//Si la cateogria asignada es nueva, tengo q hacer un INSERT en menu item categories
		if (menu_item_category_id.equals("")){		
		
			//Metodo para hacer el manejo de la categoria insertada si no existe. 
			json = insert_category_from_checkbox (p, edit_menu_item_id, new_edit_item_name, category_id, menu_id);							
		
		/* Si la cateogria asignada ya existia, nos fijamos si modifico la imagen y el video */
		} else {	
		
			//Solo si cambio el nombre del item modifico la categoria. 
			if (item_is_new){
				
				//Cambio toda la categor�a de acuerdo al nuevo nombre y actualizo archivos y base.
				json = coreEditMenuItem (									
						json,
						item_is_new,
						current_file_path,
						get_menu_item_original_name,
						edit_item_image,
						edit_item_name,
						edit_item_video,
						get_menu_id_folder_name(),
						get_ategory_folder_name(),
						edit_menu_item_id 
						);			
			}			
		}	
	}
	
	
	/**
	 * insertMenuItem
	 * @param p
	 * @param last_item_menu
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENU_ITEM_MEDIA (
			
			Parser p, 
			String last_item_menu,			
			String category_id, 
			String menu_id, 
			boolean eraseOrigin,
			String menu_item_id
									) throws JSONException {						
		
		String category_folder_name = null;
		String menu_folder_name = null;	
		
		JSONArray arr = new JSONArray();		
		
		try {
			
			JSONArray categories_menus_id = database.GET_CATEGORIES_MENUS_ID(category_id, menu_id );
			JSONObject categories_menus_id_object = categories_menus_id.getJSONObject(0);
			String cat_menus_id = categories_menus_id_object.getString("_id");
			
			//INSERT MENU POR FORM DE CARGA WEB. 
			if (menu_item_id == null){
				JSONArray last_menu_item_id  = database.GET_LAST_MENU_ITEM_ID();
				JSONObject last_menu_item_id_object = last_menu_item_id.getJSONObject(0);
				menu_item_id = last_menu_item_id_object.getString("_id");
			}
						
			database.INSERT_MENUS_ITEMS_CATEGORIES (menu_item_id, cat_menus_id);
			
			JSONArray c = database.GET_CATEGORY_FOLDER_NAME(category_id);			
			JSONObject category_folder_name_object = c.getJSONObject(0);
			category_folder_name = category_folder_name_object.getString("folder_name");
				
			JSONArray m = database.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID(category_id, menu_id);	
			JSONObject menu_folder_object = m.getJSONObject(0);
			menu_folder_name = menu_folder_object.getString("folder_name");		
			menu_folder_name = menu_folder_name.replace(" ", "");
			
		} catch (JSONException e) {
			arr = JSONReturn("status",e);		
			e.printStackTrace();
			return arr;
		}			
		
		String item_name = (String) p.getByDescId("item_name");
		String item_image = null; 
		
		item_image = (String) p.getByDescId("item_image");
		
		if (item_image==null) {
			item_image = (String) p.getByDescId("edit_item_image");
		}
		
		String item_video = null;
		
		item_video = (String) p.getByDescId("item_video");	
		
		if (item_video==null) {
			item_video = (String) p.getByDescId("edit_item_video");
		}
		
		
		arr = process_media_from_buffer (				
				item_image, 
				item_name, 
				item_video, 
				menu_folder_name, 
				category_folder_name,
				eraseOrigin,
				arr, 
				last_item_menu );
		
		arr = JSONReturn("status",1);	
		return arr;	
		
	}
	
	
	/**
	 * remove_category_process
	 * @param p
	 * @param category_id
	 * @param menu_id
	 * @param edit_item_name
	 * @param get_menu_item_original_name
	 * @param edit_categories_id
	 * @param arr
	 * @return
	 * @throws JSONException
	 */
	public JSONArray replace_file_media_process (
			String edit_menu_item_id,
			String edit_item_image,			 
			String edit_item_video,
			Parser p,
			String category_id, 
			String menu_id, 
			String edit_item_name, 
			String get_menu_item_original_name,			
			Object edit_categories_id,
			JSONArray arr,
			boolean eraseOriginal 
			
			) throws JSONException {
		
		// OBTENGO EL PATH DE LOS CURRENT FILES 
		File current_file_path = get_current_path(category_id, menu_id);		
		
		// BORRO LOS ARCHIVOS ENCONTRADOS EN LAS CATEGORIAS SI LA IMAGEN FUE MODIFICADA. 		
		if(!edit_item_image.equals(""))
			delete_imaqes_items_by_current_path_for_replacement (current_file_path, edit_item_name, get_menu_item_original_name);
		
		// LEVANTAR ARCHIVO DE IMAGEN DEL BUFFER Y COPIARLO AL CURRENT FILE PATH.		
		// PROCESO LA IMAGEN A LOS TRES ARCHIVOV.		
		// SI SE SUBIO VIDEO HAGO LO MISMO PERO SIN PROCESA. 		
		
		JSONArray m = database.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID(category_id, menu_id);	
		JSONObject menu_folder_object = m.getJSONObject(0);
		String menu_folder_name = menu_folder_object.getString("folder_name");		
		menu_folder_name = menu_folder_name.replace(" ", "");
		
		JSONArray c = database.GET_CATEGORY_FOLDER_NAME(category_id);			
		JSONObject category_folder_name_object = c.getJSONObject(0);
		String category_folder_name = category_folder_name_object.getString("folder_name");
		
		String last_item_menu = null;
		if (!edit_item_video.equals("")){
			delete_video_items_by_current_path_for_replacement (current_file_path, edit_item_name, get_menu_item_original_name);
			last_item_menu = edit_menu_item_id;
		} 
		
		arr = process_media_from_buffer (				
				edit_item_image, 
				edit_item_name, 
				edit_item_video, 
				menu_folder_name, 
				category_folder_name,
				eraseOriginal, 	arr, last_item_menu // ULTIMO CAMPO NULL PARA NO PROCESAR BASE DE DATOS CUANDO SOLO SE NECESITA ARCHIVO.				
				);
		
		return arr;
		
	}
	
	/**
	 * process_media_from_buffer
	 * @param item_image
	 * @param item_name
	 * @param item_video
	 * @param menu_folder_name
	 * @param eraseOrigin
	 * @param arr
	 * @param last_item_menu
	 * @return
	 * @throws JSONException
	 */
	public JSONArray process_media_from_buffer (
			
			String item_image, 
			String item_name, 
			String item_video, 
			String menu_folder_name,
			String category_folder_name,
			boolean eraseOrigin,
			JSONArray arr, 
			String last_item_menu
			
			) throws JSONException {
		
			//IMAGE
			File destination_image = null;
			
			boolean copiedImage = false;
			
			if (imageFile!=null){
				
				try {
					
						
					if (imageFile.exists()){		
						
						if (!item_image.contains("jpg") && !item_image.contains("png")){
							item_image += ".jpg";
						}
						
						String complete_image_name = fUtils.removeExtUpperAddSlash(item_name);
						
						String image_item_name = complete_image_name + "." + fUtils.getFileExtension(item_image);
						
						destination_image = new File(base_path + "/httpd/media/menus/" + menu_folder_name + "/categories/" + category_folder_name + "/" + image_item_name);
						
						// SOLUCIONAR moveFile agregando categoria cuando se cambia la imagen.
						// Sacar este false, estaba eraseOrigin y se tomaba para que 
						// se borre el buffer en el ultimo item verificar que no se haya agregado imagen. 
						copiedImage = fH.moveFile(imageFile, destination_image, false); 
					}
					
					if (copiedImage){			
						destination_image = new File(destination_image.toString());
					}
					
					arr = resize_images_to_formats (arr, destination_image, last_item_menu);
						
					
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}					
			}
			
			//VIDEO
				
			boolean copiedVideo = false;
			
			if (videoFile!=null){
			
				if ( !item_video.contains(".mp4")) {
					item_video += ".mp4";
				}
			
				if (videoFile.exists()){
					
				   String complete_video_name = fUtils.removeExtUpperAddSlash(item_name);
					
				  String video_item_name = "video_" + fUtils.removeExtention(complete_video_name) + "." + fUtils.getFileExtension(item_video);
					
				   destination_video = new File(base_path + "/httpd/media/menus/" + menu_folder_name+ "/categories/" + category_folder_name + "/" + video_item_name);
				
				   copiedVideo = fH.moveFile(videoFile, destination_video, false);
				}
				
				File videoUploaded = null;
				
				if (copiedVideo)
					videoUploaded = new File(destination_video.toString());
				
				// SI EL VIDEO ESTA PERO last_item_menu ES NULL POR QUE SOLO HACE FALTA PROCESO DE ARCHIVO Y NO DE 
				// BASE DE DATOS, SE GUARDA EN LA BASE, SINO SOLO ARCHIVO. 
				
				if (videoUploaded.exists() && last_item_menu!=null) {
					
					try {
						
						String pathVideo = 	destination_video.toString();
						
						String[] respVideo = pathVideo.split("httpd");
						
						return database.INSERT_MENU_ITEM_VIDEO(last_item_menu, respVideo[1]);
						
					} catch (JSONException e) {		
						arr = JSONReturn("status",e);		
						e.printStackTrace();
						return arr;
					}
					
				}			
			}
			
			return arr;
		
	}
	
	/**
	 * get_current_path
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public File get_current_path (String category_id, String menu_id) throws JSONException{
		
		File current_file_path = null; 
		
		try {
			
			JSONArray menu_id_folder_name_category_and_menu_id = database.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID(category_id, menu_id);								
			JSONObject menu_id_folder_name_category_and_menu_id_object;
			menu_id_folder_name_category_and_menu_id_object = menu_id_folder_name_category_and_menu_id.getJSONObject(0);
		
			menu_id_folder_name = menu_id_folder_name_category_and_menu_id_object.getString("folder_name");
			
			JSONArray c = database.GET_CATEGORY_FOLDER_NAME(category_id);			
			JSONObject category_folder_name_object = c.getJSONObject(0);
			category_folder_name = category_folder_name_object.getString("folder_name");
										
			current_file_path = new File(base_path + "/httpd/media/menus/" + menu_id_folder_name + "/categories/" + category_folder_name);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return current_file_path;
	}
	
	/**
	 * remove_categry_from_menu_item
	 * @param menu_items_params
	 * @param edit_item_name
	 * @param get_menu_item_original_name
	 * @throws JSONException
	 */
	public void remove_category_from_menu_item ( 
			
			String menu_items_params, 
			String edit_item_name, 
			String get_menu_item_original_name,
			String edit_menu_item_id
			
			) throws JSONException {
	
		String[] menu_items_params_array = menu_items_params.split("-");

		String category_id 		= menu_items_params_array[0];
		String menu_id 			= menu_items_params_array[1];		
		
		File current_file_path = null;
		
		try {
			
			current_file_path = get_current_path(category_id, menu_id);
			JSONArray categories_menus_id_array = database.GET_CATEGORIES_MENUS_ID(category_id, menu_id);
			JSONObject categories_menus_id_object = categories_menus_id_array.getJSONObject(0);
			String cat_menus_id = categories_menus_id_object.getString("_id");
			
			//BORRO ARCHIVOS. 
			delete_media_items_by_current_path_for_replacement (current_file_path, edit_item_name, get_menu_item_original_name);
			
			//VERIFICO QUE EL PATH ACTUAL DONDE ESTA LA IMAGEN NO SEA EL MISMO DE LA TABLA MENU ITEM THUMBS.		
			JSONArray menu_items_thumbs_array = database.GET_MENU_ITEM_THUMBS_BY_ID ( edit_menu_item_id );
			JSONObject menu_items_thumbs_object = menu_items_thumbs_array.getJSONObject(0);
			String menu_items_thumb_path = menu_items_thumbs_object.getString("img_small");		
			
			//ME FIJO SI SON IGUALES.
			String[] split_path = menu_items_thumb_path.split("/");
			String remove_path = split_path[split_path.length-1];
			menu_items_thumb_path = menu_items_thumb_path.replace(remove_path, "");
			
			menu_items_thumb_path = base_path + "/httpd" + menu_items_thumb_path; 
			
			String match_current_file_path = current_file_path + "/";
			
			if(match_current_file_path.equals(menu_items_thumb_path)){							

				// Obtengo el id de la categoria y menu actual
				JSONArray match_categories_menu_array = database.GET_CATEGORIES_MENUS_ID( category_id, menu_id );
				JSONObject match_categories_menu_object = match_categories_menu_array.getJSONObject(0);
				String categories_menus_id = match_categories_menu_object.getString("_id");		
				
				//categories_menus_id = SELECT _id FROM categories_menus WHERE category_id = 1 AND menu_id = 3;

				// Obtengo el categories_menus_id q sea distinto al actual.				
				JSONArray new_categories_menus_id_array = database.GET_EXIST_CATEGORIES_MENUS_ID_FOR_REMOVE_CATEGORY( edit_menu_item_id, categories_menus_id );
				JSONObject new_categories_menus_id_object = new_categories_menus_id_array.getJSONObject(0);
				String new_categories_menus_id = new_categories_menus_id_object.getString("categories_menus_id");	
				
				//SELECT categories_menus_id FROM menu_items_categories WHERE menu_item_id = 1 AND categories_menus_id <> 19;

				// Obtengo category_id y menu_id para obtener un path existente
				JSONArray category_and_menu_array = database.GET_CATEGORY_AND_MENU_BY_CATEGORIES_MENUS_ID ( new_categories_menus_id  );
				JSONObject category_and_menu_object = category_and_menu_array.getJSONObject(0);
				
				String new_category_replacement = category_and_menu_object.getString("category_id");
				String new_menu_replacement = category_and_menu_object.getString("menu_id");
				
				//OBTENGO LA CARPETA DE LA NUEVA CATEGORIA
				JSONArray new_menu_folder_name_array = database.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID ( new_category_replacement, new_menu_replacement );
				JSONObject new_menu_folder_name_object = new_menu_folder_name_array.getJSONObject(0);
				String new_menu_folder_name = new_menu_folder_name_object.getString("folder_name");			
				
				// OBTENGO EL NOMBRE DE LA CATEGORIA. 
				JSONArray new_category_folder_name_array = database.GET_CATEGORY_FOLDER_NAME ( new_category_replacement );
				JSONObject new_category_folder_name_object = new_category_folder_name_array.getJSONObject(0);			
				String new_category_folder_name = new_category_folder_name_object.getString("folder_name");
				
				//SACO EL NOMBRE DEL ARCHIVO PARA CONCATENARLO AL ATCHIVO.
				edit_item_name = fUtils.removeExtUpperAddSlash(edit_item_name);
				
				// CONTRUYO EL ARCHIVO SIN EXTENSION. 
				String second_root_path = base_path + "/httpd/media/menus/" + new_menu_folder_name + "/categories/" + new_category_folder_name + "/";
				String second_file_path = second_root_path + edit_item_name; 
				
				// SACO LA EXTENSION PROVISORIO, BORRAR ESTO, NO TENGO MODO DE SABER EL ARCHIVO. 
				File new_file = null;
				
				String new_file_name_jpg = second_file_path + ".jpg";
				String new_file_name_png = second_file_path + ".png";
				
				if (new File ( new_file_name_jpg ).exists()){
					new_file = new File (new_file_name_jpg);
				} else if (new File ( new_file_name_png ).exists()){
					new_file = new File (new_file_name_png);
				}	
				
				second_root_path = second_root_path.replace("mnt/sdcard/MenuServer/httpd/", "");
				
				//EXTENCION
				String extension = fUtils.getFileExtension(new_file.toString());
				
				//SACO EL NOMBRE
				String file_name = fUtils.removeExtention(edit_item_name);
				
				// ARMO LOS PATHS				
				String path_to_small_image = second_root_path + file_name + "_small." +  extension;
				String path_to_medium_image = second_root_path + file_name + "_medium." +  extension;
				String path_to_large_image = second_root_path + file_name + "_large." +  extension;				
				
				//ACTUALIZO THUMBS EN BASE
				json = database.UPDATE_MENU_ITEM_THUMB (
						path_to_small_image, 
						path_to_medium_image, 
						path_to_large_image, 
						edit_menu_item_id);				
				
				
			}
			
			//BORRO CATEGORIA DE LA BASE DE DATOS.						
			database.DELETE_MENU_ITEM_CATEGORIES_BY_ID( cat_menus_id );		
			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
		
		//SI LA CATEGORIA NO TIENE ARCHIVOS LA BORRO. 
		int current_size = current_file_path.listFiles().length;
		if (current_size<=0){
			current_file_path.delete();
		}
		
		 
	}
	
	
	public String get_ategory_folder_name() {
		return category_folder_name;
	}

	public void set_category_folder_name(String category_folder_name) {
		this.category_folder_name = category_folder_name;
	}

	public String get_menu_id_folder_name() {
		return menu_id_folder_name;
	}

	public void set_menu_id_folder_name(String menu_id_folder_name) {
		this.menu_id_folder_name = menu_id_folder_name;
	}

	
	
	public void delete_media_items_by_current_path_for_replacement (File current_file_path, String edit_item_name, String get_menu_item_original_name) throws JSONException{
		
		File[] f = current_file_path.listFiles();	
		
		File mediaFile;
		
		for (int t=0; t<f.length; t++){
		
			mediaFile = f[t];					
			String extension = fUtils.getFileExtension(mediaFile.toString());		
			
			String complete_image_name = fUtils.removeExtUpperAddSlash(edit_item_name);
			
			boolean is_image = isImage(get_menu_item_original_name, extension.toString(), mediaFile.getName());
			boolean is_video = isVideo(get_menu_item_original_name, extension.toString(), mediaFile.getName());
			boolean is_original = isOriginal(fUtils.removeExtention(mediaFile.getName()), fUtils.removeExtention(complete_image_name), mediaFile.getName());
			
			if ( is_image || is_video || is_original ) {									
				mediaFile.delete();								
			}			
			
		}
		
	}
	
	public void delete_imaqes_items_by_current_path_for_replacement (File current_file_path, String edit_item_name, String get_menu_item_original_name) throws JSONException{
		
		File[] f = current_file_path.listFiles();	
		
		File mediaFile;
		
		for (int t=0; t<f.length; t++){
		
			mediaFile = f[t];					
			String extension = fUtils.getFileExtension(mediaFile.toString());		
			
			String complete_image_name = fUtils.removeExtUpperAddSlash(edit_item_name);
			
			boolean is_image = isImage(get_menu_item_original_name, extension.toString(), mediaFile.getName());			
			boolean is_original = isOriginal(fUtils.removeExtention(mediaFile.getName()), fUtils.removeExtention(complete_image_name), mediaFile.getName());
			
			if ( is_image || is_original ) {									
				mediaFile.delete();								
			}			
			
		}
		
	}
	
	public void delete_video_items_by_current_path_for_replacement (File current_file_path, String edit_item_name, String get_menu_item_original_name) throws JSONException{
		
		File[] f = current_file_path.listFiles();	
		
		File mediaFile;
		
		for (int t=0; t<f.length; t++){
		
			mediaFile = f[t];					
			String extension = fUtils.getFileExtension(mediaFile.toString());		
			
			String complete_image_name = fUtils.removeExtUpperAddSlash(edit_item_name);
						
			boolean is_video = isVideo(get_menu_item_original_name, extension.toString(), mediaFile.getName());			
			
			if ( is_video ) {									
				mediaFile.delete();								
			}			
			
		}
		
	}
	
	
	/**
	 * COMO EL CHECKBOX ES NUEVO ESTE BLOQUE ES UN FAKE SIMULANDO QUE ESTOY 
	 * INSERTANDO UN ITEM NUEVO. TOMO LAS IMAGENES ORIGINALES DEL VIDEO E IMAGEN
	 * DE UN ITEM YA CONOCIDO EN OTRA CATEGORIA Y LO PONGO EN EL MISMO BUFFER
	 * DONDE SE HUBIERA ALOJADO DE SER SER SUBIDO POR FORM. ERA LA UNICA MANERA QUE 
	 * ENCONTRE PARA NO REPETIR TODO DE VUELTA Y REUSAR CODIGO.	
	 *	 
	 * insert_category_from_checkbox 
	 * @param p
	 * @param menu_item_id
	 * @param new_edit_item_name
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray insert_category_from_checkbox (
			
			Parser p, 
			String menu_item_id, 
			String new_edit_item_name, 
			String category_id,
			String menu_id
			
			) throws JSONException {
		
		json = new JSONArray();
		
		JSONArray lmi = database.GET_LAST_MENU_ITEM();	
		JSONObject last_menu_item_object = lmi.getJSONObject(0);
		String last_menu_item = last_menu_item_object.getString("_id");	
		
		JSONArray mit = database.GET_MENU_ITEM_THUMBS_BY_ID(menu_item_id);
		JSONObject mit_object = mit.getJSONObject(0);
		String small_img_path = mit_object.getString("img_small");	
		
		String ori_img_path = small_img_path.replace("_small", "");
		
		ori_img_path = app.getSDCARD() + "/httpd/" + ori_img_path;
		
		File ori_img_file = new File( ori_img_path );
		
		File dest_img_file = new File(app.getSDCARD() + "/httpd/buffer/" + ori_img_file.getName() );						
										
		if (ori_img_file.exists()){								
			if (fUtils.copyFile(ori_img_file, dest_img_file)){
				//Le miento al parser como si estuviera subiendo una imagen y usar insert_categories_id
				p.setParamById("item_name", new_edit_item_name);
				p.setParamById("item_image", dest_img_file.getName());				
				boolean success_rename_original = this.imageFile.renameTo(dest_img_file);
				if (success_rename_original)
					setImageFile(dest_img_file);
			}
		}								
		
		//Verifico si el item tiene video, la imagen era obligatoria.
		JSONArray miv = database.GET_MENU_ITEM_VIDEOS_BY_ID(menu_item_id);								
		if (miv.length()>0){
		
			JSONObject miv_object = miv.getJSONObject(0);
			String miv_path = miv_object.getString("path");
			
			File video_file = new File(miv_path);
			
			File dest_video_file = new File(app.getSDCARD() + "/httpd/buffer/" + video_file.getName() );
					
			if (video_file.exists()){								
				if (fUtils.copyFile(video_file, dest_video_file)){
					p.setParamById("item_video", video_file.getName());											
					setVideoFile(dest_video_file);
				}
			}									
		}

		//Estoy haciendo un fake entonces. 
		//Mando solo el category_id para que no vuelva a iterar si lo que
		//quiero es solo que entre en esta categoria.							
		
		String new_category_vector = category_id  + "-" + menu_id;
		//new_category_vector.add(category_id  + "-" + menu_id);	
		p.setParamById("edit_categories_id", new_category_vector);	
			
		//Finalmente llamo al mismo metodo que se llama desde INSERT_MENU_ITEM
		json = insert_categories_id ( p, json, new_category_vector, last_menu_item,  menu_item_id );
		
		return json;
		
	}
	
	
	public JSONArray JSONReturn (String tag, Object o) throws JSONException {
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put(tag, o);
		json.put(jo);
		
		return json;
		
	}
	
	
	/**
	 * GET_MENU_BY_ID
	 * @param menu_id
	 * @return
	 */
	public JSONArray GET_MENU_BY_ID(String menu_id){	
		JSONArray json = new JSONArray();
		try {
			json = database.GET_MENU_BY_ID(menu_id);
		} catch (JSONException e) {
			Log.v("","");
			e.printStackTrace();
		}	 
		return json;
	}
	
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}	

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}

	public void setBackpupFile(File backpupFile) {
		this.backpupFile = backpupFile;
	}
	
	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}
	
	public void setDatabaseFile(File databaseFile) {
		this.databaseFile = databaseFile;
	}
	
	/**
	 * resize_images_to_formats
	 * @param arr
	 * @param destination_image
	 * @param last_item_menu
	 * @return
	 * @throws JSONException
	 */
	private JSONArray resize_images_to_formats (JSONArray arr, File destination_image, String last_item_menu) throws JSONException {
			
		if (destination_image.exists()){			
			
			String[] duplicated = null;
			try {			
				duplicated = resize.RESIZE_TO_FORMATS(destination_image);
			} catch (IOException e) {	
				arr = JSONReturn("status",e);		
				e.printStackTrace();
				return arr;				
			}
			
			// SI last_item_menu VIENE NULL ES POR QUE SE PROCESAN SOLO LAS IMAGENES 
			// Y NO SE LAS GUARDAN LOS DATOS EN LA BASE.			
			
			JSONArray existImage = null;
			int image_already_loaded = 0;
			try {				
				File smallName = new File(duplicated[0]);
				existImage = database.GET_MENU_ITEM_THUMBS_BY_SMALL_IMAGE_NAME(smallName.getName());			
			} catch (JSONException e) {
				arr = JSONReturn("status",e);		
				e.printStackTrace(); 
				return arr;
			}				
			
			if (existImage.length()==0 && last_item_menu!=null){				
				try {					
					arr = database.INSERT_MENU_ITEM_THUMBNAIL(last_item_menu, duplicated[0], duplicated[1], duplicated[2]);					
				} catch (JSONException e) {
					arr = JSONReturn("status",e);		
					e.printStackTrace();
					return arr;
				}
			}
			
			
		}
		
		return arr;
		
	}
	
	File mediaFile;
	File new_item_vdeo;
	
	String edit_menu_item_id;
	File ori_item;
	String menu_id_folder_name, category_folder_name;
	
	boolean is_video;
	boolean is_image;
	
	private String small;
	private String medium;
	private String large;
	
	private File new_video;		
	
	private Hashtable renamedImagesFiles;
	
	/**
	 * coreEditMenuItem
	 * 
	 * 
	 * 
	 * @param json
	 * @param current_file_path
	 * @param get_menu_item_name
	 * @param edit_item_image
	 * @param edit_item_name
	 * @param edit_item_video
	 * @param menu_id_folder_name
	 * @param category_folder_name
	 * @param edit_menu_item_id
	 * @throws JSONException
	 */
	public JSONArray coreEditMenuItem (			
			
			JSONArray json,
			boolean rename,
			File current_file_path,
			String get_menu_item_original_name,
			String edit_item_image,
			String edit_item_name,
			String edit_item_video,
			String menu_id_folder_name,
			String category_folder_name,
			String edit_menu_item_id
			
			) throws JSONException {
		
		this.json = json;
		this.edit_menu_item_id = edit_menu_item_id;
		
		/* Obtenemos el path del archivo viejo */
		
		File[] f = current_file_path.listFiles();	
		
		small = null;
		medium = null;
		large = null;	
		
		for (int t=0; t<f.length; t++){
		
			mediaFile = f[t];
			
			this.ori_item = mediaFile;
			
			this.ori_item = ori_item;
			String extension = fUtils.getFileExtension(ori_item.toString());		
			
			is_image = false;
			is_video = false;		
			
			String complete_image_name = fUtils.removeExtUpperAddSlash(edit_item_name);
						
			//Pregunta si el archivo recorrido esta dentro de la imagen o video sino no hace nada con ellos y sigue. 
			if (isImage(get_menu_item_original_name, extension.toString(), mediaFile.getName())){
				
				is_image = true;
				is_video = false;
				
			} else if ( isVideo(get_menu_item_original_name, extension.toString(), mediaFile.getName()) ) {
				
				is_image = false;
				is_video = true;
				
			} else if (isOriginal(fUtils.removeExtention(mediaFile.getName()), fUtils.removeExtention(complete_image_name), mediaFile.getName())){
				
				//Renomboro el original
				File new_image = new File(current_file_path.toString() + "/" + complete_image_name + "." + extension);	
				boolean success_rename_original = ori_item.renameTo(new_image);
			    if (!success_rename_original) {
			    	json = JSONReturn("status",1); 
			    	return json;			        
			    } else {
			    	if (ori_item.exists())
			    		ori_item.delete();		    	
			    }
				
			}
			
			if (is_image || is_video){
								
				String size = null; 
				File new_image = null;
				new_video = null;		
				
				this.menu_id_folder_name = menu_id_folder_name;
				this.category_folder_name = category_folder_name;				
				
				if (is_video){
							
					new_video = new File(base_path + "/httpd/media/menus/" + menu_id_folder_name+ "/categories/" + category_folder_name + "/" + complete_image_name + "." + extension );
													
				} else if (is_image) {	 
					
					renamedImagesFiles = new Hashtable();					
					String image_item_name = complete_image_name + "." + extension;	
					
					if (mediaFile.toString().contains("_small")){
						
						size = "_small";
						
					} else if (mediaFile.toString().contains("_medium")){
						
						size = "_medium";
						
					} else if (mediaFile.toString().contains("_large")){
						
						size = "_large";
						
					}
					
					if (size!=null){                
						new_image = new File(current_file_path.toString() + "/" + complete_image_name + size + "." + extension);
						renamedImagesFiles.put(size, new_image);
					}
					
				}				
						
				
				//1) Renombro nueva imagen nuevo video 
				if (rename && !edit_item_image.equals("") && !edit_item_video.equals("")) {				
						
					if (is_image){
						
						if (!create_new_media(true)){ json = JSONReturn("status",1); return json; }
						
					} else if (is_video){
						
						if (!create_new_media(false)){ json = JSONReturn("status",1); return json; }			
						
					}			
				
				//2) Renombro nueva imagen nuevo video 
				} else if (rename && !edit_item_image.equals("") && edit_item_video.equals("")) {
					
					if (is_image){
											
						if (!create_new_media(true)){ json = JSONReturn("status",1); return json; }	
						
					} else if (is_video){
						
						if (!rename_media(new_video)){ json = JSONReturn("status",1); return json; } 
					}	
					
				//3) Renombre no modifica imagen pero si video	 
				} else if (rename && edit_item_image.equals("") && !edit_item_video.equals("")) {
					
					if (is_image){
						
						if (!rename_media(new_image)){ json = JSONReturn("status",1); return json; } 							
						
					} else if (is_video){
						
						if (!create_new_media(true)){ json = JSONReturn("status",1); return json; }	 
						
						if (!create_new_media(false)){ json = JSONReturn("status",1); return json; }
						
					}
				
				//4) Renombre de imagen y video no modifica imagen y video 	
				} else if (rename && edit_item_image.equals("") && edit_item_video.equals("")) {
					
					if (is_image){
						
						if (!rename_media(new_image)){ json = JSONReturn("status",1); return json; }		
								
						
					} else if (is_video){
				
						if (!rename_media(new_video)){ json = JSONReturn("status",1); return json; }					
						
					}
					
				//5) Borra no hay rename pero modifica imagen y video 
				} else if (!rename && !edit_item_image.equals("") && !edit_item_video.equals("")) {

					if (is_image){
						
						if (!create_new_media(true)){ json = JSONReturn("status",1); return json; }						
						
					} else if (is_video){
						
						if (!create_new_media(false)){ json = JSONReturn("status",1); return json; }				
						
					}
				
				//6) Borra, no modifica vide elimina imagen y crea imagen y hace rename de video
				} else if (!rename && !edit_item_image.equals("") && edit_item_video.equals("")) {
					
					if (is_image){
						
						if (!create_new_media(true)){ json = JSONReturn("status",1); return json; }		
						
					} 
				
				//7) Borra, no modifica imagen pero crea video 	
				} else if (!rename && edit_item_image.equals("") && !edit_item_video.equals("")) { 
					
					if (is_video){
						
						if (!create_new_media(false)){ json = JSONReturn("status",1); return json; }	
						
					}
					
				//8) NO HACE NADA 		
				} else if (!rename && edit_item_image.equals("") && edit_item_video.equals("")) {	}		
				
				if (is_image){
					
					if (checkKey("_small")){
						String sm = renamedImagesFiles.get("_small").toString();
						if (sm!=null){
							small = sm.replace("/mnt/sdcard/CobyCarta/httpd", "");						
						}
					}
					if (checkKey("_medium")){
						String me = renamedImagesFiles.get("_medium").toString();
						if(me!=null){
							medium = me.replace("/mnt/sdcard/CobyCarta/httpd", "");
						}
					}
					if (checkKey("_large")){
						String la = renamedImagesFiles.get("_large").toString();
						if(la!=null){ 
							large = la.replace("/mnt/sdcard/CobyCarta/httpd", "");
						}
					}
				}				
			}					
		}	
		
		json = database.UPDATE_MENU_ITEM_THUMB(small, medium, large, edit_menu_item_id);
		
		if (is_video){		
			
			database.UPDATE_MENU_ITEM_VIDEO(new_video.toString(), edit_menu_item_id); //path vido desde media;	
			
		}			
		
		return json;		
			
	}
	
	/**
	 * insert_categories_id
	 * @param p
	 * @param json
	 * @param inst_cats
	 * @param last_item_menu
	 * @return
	 * @throws JSONException
	 */
	public JSONArray insert_categories_id ( Parser p, JSONArray json, Object inst_cats, String last_item_menu, String menu_item_id ) throws JSONException{
		
		this.json = json;
		
		if (inst_cats instanceof Vector){
			
			Vector cats = new Vector();
			cats = (Vector) inst_cats;
			
			boolean last;
			
			for(int i=0; i<cats.size(); i++){						
				
				String menu_category_id = (String) cats.get(i);
				
				String[] menu_cat = menu_category_id.split("-");		
				
				if(i==(cats.size()-1)){
					last = true;
				} else {
					last = false;
				}						
				
				json = INSERT_MENU_ITEM_MEDIA (p, last_item_menu, menu_cat[0], menu_cat[1], last, menu_item_id);
				
			}
			
		} else {
			
			String categories_id = (String) p.getByDescId("categories_id");	
			if (categories_id==null){
				categories_id = (String) p.getByDescId("edit_categories_id");	
			}			
			String[] cats = categories_id.split("-");	
			json = INSERT_MENU_ITEM_MEDIA (p, last_item_menu,  cats[0], cats[1], true, menu_item_id);				
							
		}
		
		return json;
		
	}
	
	
	public boolean checkKey(String key){		
		Enumeration keys = renamedImagesFiles.keys();		
		while(keys.hasMoreElements()){
			String k = (String) keys.nextElement();
			if (k.contains(key)){			
				return true;
			}
		}		
		return false;		
	}
	
	
	/**
	 * isImage
	 * @param ext
	 * @param file_name
	 * @return
	 */
	public boolean isImage (String get_menu_item_original_name, String ext, String file_name){
		
		//Si enctontramos el archivo con el mismo nombre que el menu item name. Seguimos comparando. 
		if (file_name.contains(get_menu_item_original_name)){
		
			if ( ext.equals("png") || ext.equals("jpg")){		
				
				if (file_name.contains("_small") || file_name.contains("_medium") || file_name.contains("_large"))  {
					return true;
				} else {
					return false;				
				}
				
			} else {
				return false;
			}
			
		} else {
			return false;			
		}
	}
	
	/**
	 * isVideo
	 * @param ext
	 * @return
	 */
	public boolean isVideo (String get_menu_item_original_name, String ext, String file_name){
		
		//Si enctontramos el archivo con el mismo nombre que el menu item name. Seguimos comparando. 
		if (file_name.contains(get_menu_item_original_name)){
		
			if ( ext.equals("mp4") || ext.equals("ogg")){
				return true;
			} else {
				return false;	
			}
		} else {
			return false;
		}		
	}
	
	//Borra y crea de nuevo
	private boolean create_new_media (	boolean image ) {
		boolean ret = true; 
		 try {
			 boolean success_delete_menu_item = ori_item.delete();
	         if (!success_delete_menu_item) {
	             System.err.println("Error");                
	             String destiny = base_path + "/httpd/media/menus/" + menu_id_folder_name+ "/categories/" + category_folder_name + "/";
	             if (image){
	            	fH.copyFile(destiny, this.imageFile.toString());	            	
					json = resize_images_to_formats (json, mediaFile, edit_menu_item_id);					
	             } else {
	            	 fH.copyFile(destiny, this.videoFile.toString());
	             }                         
	         }     
		} catch (JSONException e) {
			ret = false;
			e.printStackTrace();
		}
		 return ret;
	}	

	//Renombra
	private boolean rename_media (File new_item ) {			
		
	    boolean success_rename_menu_item = ori_item.renameTo(new_item);
	    if (!success_rename_menu_item) {
	        System.err.println("Error");
	        return false;
	    } else {
	    	return true;
	    }
	    
	}		
	
	//Busca si el archivo es el original 
	boolean isOriginal ( String menu_item_original_name, String menu_item_modified_name, String file_name ){		 
		
		boolean is = false;
		
		if (!file_name.contains("_small") || !file_name.contains("_medium") || !file_name.contains("_large"))  {
			
			if (menu_item_modified_name.contains(menu_item_original_name)){		
				is = true;
			} else {
				is = false;
			}
			
		}
		return is;
		
	}
	

}
