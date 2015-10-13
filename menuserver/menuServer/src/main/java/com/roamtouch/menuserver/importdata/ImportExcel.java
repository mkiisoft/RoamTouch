package com.roamtouch.menuserver.importdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.backup.BackUpData;
import com.roamtouch.menuserver.communications.APIProtocol;
import com.roamtouch.menuserver.communications.APIProtocolMethods;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.utils.FileUtils;
import com.roamtouch.menuserver.utils.FolderHandler;

import android.content.Context;
import android.util.Log;

public class ImportExcel {

    private String inputFile;
    
    //ArrayList<item> items = new ArrayList<item>(); 
    
    Hashtable items = new Hashtable();   
    
    private static DataBaseController database;
    
    private FileUtils fUtils = new FileUtils();
    
    private APIProtocolMethods APM;
    
    private FolderHandler folderHandler;
    
    private APIProtocol API;
    
    private MenuServer cM;
    
    private BackUpData backup;
    
    int activo 	= -1;
    static final int CODIGO 		= 100;
    static final int NOMBRE 		= 101;
    static final int DESCRIPCION 	= 102;
    static final int PRECIO 		= 103;
    static final int RUBRO 			= 104;
    static final int IMAGEN			= 105;
    static final int VIDEO			= 106;
    static final int AGREGADO		= 107;
    
    private int agregado_numero;

    int menuid;
    
    private MenuServerApplication app;
    
    public ImportExcel(Context context){
    	app = ((MenuServerApplication)context.getApplicationContext());
    }   
    
    public void setInputFiles(String inputFile, File imagesZipFile) {
        
    	this.inputFile = inputFile;   
        
        /*try {
        	
			this.backup.importImagesFromZip(imagesZipFile, "menu_le_pont");
			
		} catch (IOException e) {			
			e.printStackTrace();
		}*/
    }
    
 
    
      
    public boolean importData(MenuServer cM, APIProtocol API, DataBaseController database, FolderHandler folderHandler, APIProtocolMethods APM) throws IOException  {
    	
    	this.cM = cM;
    	this.API = API;
    	this.APM = APM;
    	
    	this.database = database;
    	
    	this.folderHandler = folderHandler;
    	
    	this.backup = new BackUpData(this.cM, folderHandler);
    	
    	
    	//Insert Menu
    	try {
    		
    		String menu = "Menu Le Pont";
    		
			insert_menu(menu, "0:00", "23:30");
			
			JSONArray json = database.GET_MENUS_BY_MENU_DESCRIPTION(menu);		
			
			JSONObject menu_id_obj = json.getJSONObject(0);
			menuid = menu_id_obj.getInt("_id");	
			
		} catch (JSONException e1) {	
						
			e1.printStackTrace();
			return false;
			
		}   	
    	
        File inputWorkbook = new File(inputFile);
        
        Workbook w;
        
        try {
        	
            w = Workbook.getWorkbook(inputWorkbook);
           
            Sheet sheet = w.getSheet(0);
           
            
            for (int j = 0; j < sheet.getColumns(); j++) {  
            	
            	int rows = sheet.getRows();
            	
            	Cell[] cellRow = sheet.getRow(j);           	
            	
                for (int i = 0; i < sheet.getRows(); i++) {         	
                	
                    Cell cell = sheet.getCell(j, i);
                    
                    String columnName = cell.getContents();
                    
                	if (columnName.equals("col_codigo")){                		             
                         
                		activo = CODIGO;
                		
                    } else if (cell.getContents().equals("col_nombre")){                  		
                		
                		activo = NOMBRE;
                		
                    } else if (cell.getContents().equals("col_descripcion")){                  		
                		
                		activo = DESCRIPCION;
                		
                	} else if (cell.getContents().equals("col_precio")){                  		
                		
                    	activo = PRECIO;
                    	
                	} else if (cell.getContents().equals("col_rubro")){                  		
                		
                    	activo = RUBRO;
                    	
                	} else if (cell.getContents().equals("col_imagen")){               		
                		
                    	activo = IMAGEN;             	
                    	
                	} else if (cell.getContents().equals("col_video")){               		
                		
                    	activo = VIDEO;             	
                    	
                	} else if (cell.getContents().contains("col_agregado_")){
                		
                		agregado_numero = Integer.parseInt(cell.getContents().replaceFirst("col_agregado_", ""));
                		
                    	activo = AGREGADO;             	
                    	
                	}
                    
                	if (i>0){
                	
	                    switch (activo) {
	            		
		            		case CODIGO:
		            			
		            			item ic = new item();
		            			
		            			String code = cell.getContents();
		            			
		            			ic.setCodigo(code);
		            			
		            			items.put(i, ic);
		            			
		            			break;
		            			
		            		case NOMBRE:
		            			
		            			String nombre = cell.getContents();
		            			
		            			item id = (item) items.get(i);
		            			
		            			id.setNombre(nombre);
		            			
		            			items.put(i, id);                   			
		            			
		            			break;
		            			
		            		case DESCRIPCION:
		            			
		            			String desc = cell.getContents();
		            			
		            			if (desc.contains("queso")){
		            				Log.v("","");		            				
		            			}
		            			
		            			item ide = (item) items.get(i);
		            			
		            			ide.setDescripcion(desc);
		            			
		            			items.put(i, ide);                   			
		            			
		            			break;
		            		
		            		case PRECIO:
		            			
		            			String precio = cell.getContents();
		            			
		            			item ip = (item) items.get(i);
		            			
		            			ip.setPrecio(precio);
		            			
		            			items.put(i, ip);                   			
		            			
		            			break;	
		            			
		            		case RUBRO:
		            			
		            			String category = cell.getContents();
		            			
		            			item ir = (item) items.get(i);
		            			
		            			if (!category.equals("")){
		            				ir.setCategory(category);		            				
		            			}      			
		            			
		            			items.put(i, ir);                   			
		            			
		            			break;	
		            			
		            		case IMAGEN:
		            			
		            			String imagen_url = cell.getContents();
		            			
		            			item ii = (item) items.get(i);
		            			
		            			 if (!imagen_url.equals("")){
		            				 ii.setImage(imagen_url);
		            				 items.put(i, ii); 
		            			 }        			                  			
		            			
		            			break;	
		            			
		            		case VIDEO:
		            			
		            			String video_url = cell.getContents();
		            			
		            			item iv = (item) items.get(i);
		            			
		            			 if (!video_url.equals("")){
		            				 iv.setVideo(video_url);
		            				 items.put(i, iv); 
		            			 }        			                  			
		            			
		            			break;		
	        		
		            		case AGREGADO:
		            			
		            			String agregado = cell.getContents();
		            			
		            			item ia = (item) items.get(i);
		            			
		            			 if (!agregado.equals("")){
		            				 ia.setAddon(agregado);
		            				 items.put(i, ia); 
		            			 }        			                  			
		            			
		            			break;	
		            			
	                    }
                	}
                }
                    
            }     
            
        } catch (BiffException e) {
            e.printStackTrace();
            return false;
        }
                
        // Crea categorias. 
        
        for (int i=1; i<items.size(); i++){
        	
        	item ic = (item) items.get(i);       	
        	
        	String category = ic.getCategory();       	
        	
        	JSONArray cat = new JSONArray();
        	
        	try {
				
        		 cat = database.GET_CATEGORIES_BY_DESCRIPTION(category);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
				return false;
				
			}       	
        	
        	if (cat.length()==0 && !category.equals("null") && !category.equals(null)){				
        		
        		String category_folder_name = fUtils.createFolderName(category);				
				
        		try {
					
        			JSONArray json = database.INSERT_CATEGORY(category, category_folder_name);        			
        			
        			JSONArray arr = new JSONArray();        			
        			JSONArray bigArr = new JSONArray();	
        			
        			JSONArray json_insert = APM.insert_category_by_id (Integer.toString(menuid), arr, bigArr, category_folder_name, category, true );			
        			
				} catch (JSONException e) {					
					
					e.printStackTrace();
					return false;
				}
        		
        		try {
    				
           		 cat = database.GET_CATEGORIES_BY_DESCRIPTION(category);
   				
	   			} catch (JSONException e) {
	   				
	   				e.printStackTrace();
	   				return false;
	   				
	   			}  
        		
        	}
        	
        	String category_id = null;
        	
        	JSONObject category_object;		
			
			try {
				
				category_object = cat.getJSONObject(0);
				category_id = category_object.getString("_id");	
				
			} catch (JSONException e1) {
				
				e1.printStackTrace();
				return false;
			}
				
			
			ic.setCategory_id(category_id);
        	
        	items.put(i, ic);     
        	
        }
        
        // Crea Menu Items.  
        
        for (int i=1; i<items.size(); i++){
        	
        	item imi = (item) items.get(i);
        	
        	String menu_item_name = imi.getNombre(); 
  		   	String menu_item_description = imi.getDescripcion();  
  		   	String menu_item_price = imi.getPrecio(); 
  		   	String category_id = imi.getCategory_id();
  		   	String code = imi.getCodigo();  		   	
  		   	String image = imi.getImage();
  		   	String video = imi.getVideo();
  		   	
  		   	String menu_id = new Integer(menuid).toString();
  		   	
  		   	/*if (code.equals("1550")){
  		   		Log.v("","");
  		   	}
  		   	
  		   	if (menu_item_name.contains("Mini Bife")){
  		   		Log.v("","");
  		   	}*/
        	
  		   	insert_menu_item (
  		   			
  		   			menu_item_name, 
  		   			menu_item_description, 
  		   			menu_item_price, 
  		   			category_id, 
  		   			menu_id, 
  		   			code, 
  		   			image, 
  		   			video
  		   			
  		   			);
        	
        } 
        
        // Crea Agregados
        
        for (int i=1; i<items.size(); i++){
        	
        	item ia = (item) items.get(i);
        	
        	ArrayList<String> addons = ia.getAddons();
        	String codigo = ia.getCodigo();
        	
        	JSONArray code_id_array;
			
        	try {
				
        		Log.v("","codigo: " + codigo);
        		if (codigo.equals("1550")){
        			Log.v("","");
        		}
				code_id_array = database.GET_MENU_ITEM_BY_CODE_ID(codigo);
				Log.v("",""+code_id_array.toString());
				JSONObject menu_item_object = code_id_array.getJSONObject(0);
				String menu_item_id = menu_item_object.getString("_id");   	
	        	
	        	for (int j=0; j<addons.size(); j++){
	        		
	        		String desc = addons.get(j);
	        		
	        		JSONArray res = database.CREATE_MENU_ITEM_ADDON(desc, menu_item_id);
	        		
	        	}
				
			} catch (JSONException e) {
			
				e.printStackTrace();
			}				    
        	
        }
        
        return true;
        
    }

    
   public class item {
    	
        String codigo;   	
        String nombre;      
		String descripcion;
        String precio;       
		String category;
		String image;	
		String video;
		String category_id;        
		ArrayList<String> addons = new ArrayList<String>();
		
		public void setAddon(String addon){
			this.addons.add(addon);
		}
		
		public ArrayList<String> getAddons(){
			return this.addons;
		}
		
		public String getCodigo() {
			return codigo;
		}
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}			
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}		
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}		
		public String getPrecio() {
			return precio;
		}
		public void setPrecio(String precio) {
			this.precio = precio;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getCategory_id() {
			return category_id;
		}
		public void setCategory_id(String category_id) {
			this.category_id = category_id;
		}
		
		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getVideo() {
			return video;
		}

		public void setVideo(String video) {
			this.video = video;
		}
		
		      
    }
  
   
   private JSONArray insert_menu (String description_menu, String interval_to, String interval_from ) throws JSONException{	   
	   		
		String folder_name = fUtils.createFolderName(description_menu);
		
		String all_path =  "/httpd/media/menus/" + folder_name + "/categories";					
		File file = new File(all_path);					
		File dest = null;					
		
		//Si no existe la creo, si existe la levanto.
		if(!file.isDirectory()){
			folderHandler.copyFilestoSdcard(file.toString(), true);
		}			
		
		//Guardo en la base.
		return database.INSERT_MENU (description_menu, folder_name, interval_from , interval_to);		
		
   }
   
   
   private boolean insert_menu_item (
		   
		   String menu_item_name, 
		   String menu_item_description, 
		   String menu_item_price, 
		   String category_id,
		   String menu_id,
		   String menu_item_code, 
		   String menu_item_image,
		   String menu_item_video
		   
		   ){	   
			
	   String last_item_menu = null;
	   
	    try {
			
	    	JSONArray json = database.INSERT_MENU_ITEM(menu_item_name, menu_item_description, menu_item_price, menu_item_code);
								
		} catch (JSONException e1) {
			e1.printStackTrace();
			return false;
		}		
				
		String category_folder_name = null;
		String menu_folder_name = null;	
		String menu_item_id; 
		
		JSONArray arr = new JSONArray();		
		
		try {
			
			JSONArray categories_menus_id = database.GET_CATEGORIES_MENUS_ID(category_id, menu_id );
			JSONObject categories_menus_id_object = categories_menus_id.getJSONObject(0);
			String cat_menus_id = categories_menus_id_object.getString("_id");
			
			//INSERT MENU POR FORM DE CARGA WEB. 
			JSONArray last_menu_item_id  = database.GET_LAST_MENU_ITEM_ID();
			JSONObject last_menu_item_id_object = last_menu_item_id.getJSONObject(0);
			menu_item_id = last_menu_item_id_object.getString("_id");			
						
			database.INSERT_MENUS_ITEMS_CATEGORIES (menu_item_id, cat_menus_id);
			
			JSONArray c = database.GET_CATEGORY_FOLDER_NAME(category_id);			
			JSONObject category_folder_name_object = c.getJSONObject(0);
			category_folder_name = category_folder_name_object.getString("folder_name");
				
			JSONArray m = database.GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID(category_id, menu_id);	
			JSONObject menu_folder_object = m.getJSONObject(0);
			menu_folder_name = menu_folder_object.getString("folder_name");		
			menu_folder_name = menu_folder_name.replace(" ", "");
			
		} catch (JSONException e) {			
			e.printStackTrace();		
			return false;
		}			
		
		String item_image = null;
	
		if (menu_item_image==null || menu_item_image.equals("")){
		
			item_image = "no_image.jpg";
			
			File no_image_file = new File(app.getSDCARD() + "/httpd/buffer/no_image.jpg");
			
			API.setImageFile(no_image_file);
		
		} else {
			
			File img_file = new File(app.getSDCARD() + "/httpd/backup/temporary/" + menu_item_image  );
		
			if (img_file.exists())
				APM.setImageFile(img_file);	
			
		}
		
		String item_video = null;
		
		if (menu_item_video==null || menu_item_video.equals("")){
		
			item_video = "no_video.mp4";
		
		} else {
			
			
		}
		
		
		
		
		
		JSONArray lmi;
		
		try {
		
			lmi = database.GET_LAST_MENU_ITEM();
			JSONObject last_menu_item_object = lmi.getJSONObject(0);
			last_item_menu = last_menu_item_object.getString("_id");	
			
		} catch (JSONException e1) {			
			e1.printStackTrace();
		}	
		
		
		try {
			
			arr = APM.process_media_from_buffer (				
					item_image, 
					menu_item_name, 
					item_video, 
					menu_folder_name, 
					category_folder_name,
					false,
					arr, 
					last_item_menu );
		
		} catch (JSONException e) {
			
			e.printStackTrace();
			return false;
			
		}	
			
		return true;
	   
   }




	   
   


}