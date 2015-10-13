package com.roamtouch.menuserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.shared.Constans;

public class FolderHandler {
	
	private MenuServerApplication app;
	private MenuServer cM;
	private ImageResize resize;
	private DataBaseController dB;
	private FileUtils fUtils = new FileUtils();
	
	public FolderHandler(MenuServer cM){
		this.cM = cM;		
		this.dB = dB;
		app = ((MenuServerApplication)cM.getApplication());
		 fUtils = new FileUtils(cM);
	}
	
	public void setDB(DataBaseController dB){
		this.dB = dB;
	}

	public void createAssetsFolder() throws IOException{
		
		 if(cM.isSdCardReady()){	
			 
			try {
				
				createFoldersCopyFiles();
				createAboutStoreLog();
				
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}		 
			 
			//SOLO UNA VEZ CREA THUMBNAILS Y LOS GUARDA EN BASE DE DATOS
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cM);
			if(prefs.getBoolean("firstTime", false)) {
			    				
				CreateThumbnails cT = new CreateThumbnails();
				cT.execute();
				
			    SharedPreferences.Editor editor = prefs.edit();
			    editor.putBoolean("firstTime", true);
			    editor.commit();
			}
			
		}							
	}
	
	private void createAboutStoreLog(){
		String time = app.getTimeDate("dd/MM/yyyy hh:mm:ss.SSS");
		String media_log = time + " | " + "Application installed.";
		fUtils.storeAboutLog(media_log);
	}
	
	
	/**
	 * createFoldersCopyFiles
	 * @throws InterruptedException 
	 * @throws IOException
	 */
	public void createFoldersCopyFiles() throws InterruptedException { 	
		
		// menus
    	Thread cp = new Thread(){
			@Override                      
	        public void run() { 
				copyAssetsFiles();
	        }
	    };
	    cp.start();
	    cp.join();
		
	    Thread imageApk = new Thread(){
			@Override                      
	        public void run() { 
				
				String base = app.getSDCARD();			
				
				// No Image for Import
				InputStream no_image;
				
				try {
					
					no_image = cM.getAssets_directory().open("images_presets/no_image.jpg");
					moveFileFromAssets(no_image, new File(base + "/httpd/buffer/no_image.jpg"));
					
					String indexString = null;		
					
					String tortas = "httpd/download/";		
					InputStream apk = cM.getAssets_directory().open("httpd/download/MenuClient.apk");	
					moveFileFromAssets(apk, new File(base + "/download/MenuClient.apk"));
					
				} catch (IOException e) {					
					e.printStackTrace();
				}	
					
				
	        }
	    };
	    imageApk.start();  
	    imageApk.join();

		
		//CATEGORIAS Y MENUES		
		
		/*copyFilestoSdcard("httpd/media/menus/menu_manana/categories/cafe/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/infusiones/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/jugos/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/licuados/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/tostados/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/tortas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/bebidas_sin_alcohol/", true);
		copyFilestoSdcard("httpd/media/menus/menu_manana/categories/bebidas_con_alcohol/", true);

		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/entradas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/ensaladas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/sopas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/pastas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/pescados/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/sandwiches/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/carnes/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/postres/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/bebidas_sin_alcohol/", true);
		copyFilestoSdcard("httpd/media/menus/menu_mediodia/categories/bebidas_con_alcohol/", true);

		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/cafe/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/infusiones/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/jugos/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/licuados/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/tostados/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/tortas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/bebidas_sin_alcohol/", true);
		copyFilestoSdcard("httpd/media/menus/menu_tarde/categories/bebidas_con_alcohol/", true);

		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/entradas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/ensaladas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/sopas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/pastas/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/pescados/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/sandwiches/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/carnes/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/postres/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/bebidas_sin_alcohol/", true);
		copyFilestoSdcard("httpd/media/menus/menu_noche/categories/bebidas_con_alcohol/", true);*/		
						
		//MENU MANIANA
		//copyMenuMananaTardeToAssets("menu_manana");
		//MENU TARDE
		//copyMenuMananaTardeToAssets("menu_tarde");			
		
		//MENU MEDIODIA
		//copyMenuMediodiaNocheToAssets("menu_mediodia");
		//MENU NOCHE
		//copyMenuMediodiaNocheToAssets("menu_noche");
	    
	}
	
	public void copyAssetsFiles(){
		
		copyFilestoSdcard("httpd/bootstrap/css", true);
		copyFilestoSdcard("httpd/bootstrap/img", true);
		copyFilestoSdcard("httpd/bootstrap/js", true);
								
		copyFilestoSdcard("httpd/categories", true);
		
		copyFilestoSdcard("httpd/css", true);
		
		copyFilestoSdcard("httpd/dining_tables", true);
		
		copyFilestoSdcard("httpd/img", true);				
				
		copyFilestoSdcard("httpd/items", true);
		
		copyFilestoSdcard("httpd/js", true);	
		copyFilestoSdcard("httpd/menus", true);			
		
		copyFilestoSdcard("httpd/log", true);			
		copyFilestoSdcard("httpd/log/files", true);			
		
		copyFilestoSdcard("httpd/orders", true);
		
		copyFilestoSdcard("httpd/printing", true);
		copyFilestoSdcard("httpd/printing/lib", true);
		
		copyFilestoSdcard("httpd/automation", true);		
						
		copyFilestoSdcard("httpd/backup", true);
		copyFilestoSdcard("httpd/backup/temporary", true);
		copyFilestoSdcard("httpd/backup/originaldb", true);
		
		copyFilestoSdcard("httpd/media", true);
		copyFilestoSdcard("httpd/media/menus", true);
		copyFilestoSdcard("httpd/media/log", true);
		
		copyFilestoSdcard("httpd/buffer", true);
		
		copyFilestoSdcard("httpd/templates", true);
		
		copyFilestoSdcard("httpd/users", true);
		
		copyFilestoSdcard("httpd/download", true);
		
		copyFilestoSdcard("httpd/import", true);
		
		copyFile("httpd", "index.html");	
		copyFile("httpd", "info.html");
	}
	
	
	/**
	 * generateAssetsThumbNails
	 * @throws IOException
	 */
	public void generateAssetsThumbNails() throws IOException{				
		
		File menus = new File(app.getSDCARD() + "/httpd/media/menus/");
		File[] m = menus.listFiles();
		
		resize = new ImageResize(cM);
		
		for (int i=0; i<m.length; i++){			
			
			File[] c = m[i].listFiles();
			
			for (int t=0; t<c.length; t++){
										
				File[] cats = c[t].listFiles();
				
				for (int k=0; k<cats.length; k++){
					
					items = cats[k].listFiles();
				
					for (int j=0; j<items.length; j++){
						
						small = resize.resizeImageByFormat(items[j], GlobalVars.RESIZE_IMAGE_TO_SMALL);
						medium = resize.resizeImageByFormat(items[j], GlobalVars.RESIZE_IMAGE_TO_MEDIUM);
						large = resize.resizeImageByFormat(items[j], GlobalVars.RESIZE_IMAGE_TO_LARGE);				
					
						String name = fUtils.removeExtUpperAddSlash(items[j].getName());					
						
						JSONArray mIdArray;	
						
						try {													
							
							mIdArray = dB.GET_MENU_ITEMS_BY_EXACT_NAME(name);
							
							JSONObject itemObject = mIdArray.getJSONObject(0);
				            int menu_item_id = itemObject.getInt("_id");				
							
				            JSONArray existImage = null;
							int image_already_loaded = 0;
							try {				
								File smallFile = new File(small);
								existImage = dB.GET_MENU_ITEM_THUMBS_BY_SMALL_IMAGE_NAME(smallFile.getName());							
							} catch (JSONException e) {							
								e.printStackTrace();
							}				
							
							if (existImage.length()==0){							
							
					            if (menu_item_id>0){
					            	dB.INSERT_MENU_ITEM_THUMBNAIL (String.valueOf(menu_item_id), small, medium, large);
					            } 
					            else {
					            	Log.v("","");						            	
					            }      
					            
							}
							
							
						} catch (JSONException e) {									
							e.printStackTrace();
						}
						
						//items[j].delete();				
						
					}
				}	
			}	
		}
		
	}
	
	
	 private class CreateThumbnails extends AsyncTask<Void, Integer, Void>{    
		 	

			@Override
			protected Void doInBackground(Void... params) {
				
				try {
					//createFoldersCopyFiles();
					generateAssetsThumbNails();
					copyVideoToSDCARD();
					
				} catch (IOException e) {					
					e.printStackTrace();
				}
				
				return null;
			}
			
			@Override
		 	protected void onPostExecute(Void result) {		 		
		 		super.onPreExecute();   
		 	}
	 }
	
	 private void copyVideoToSDCARD() throws IOException{
		 
		 String base = app.getSDCARD();	
		 
		//menu_manana/categories/cafe/
		String menu_1 = "menu_mediodia"; 
		String rabas_1 = "httpd/media/menus/" + menu_1 + "/categories/entradas/";
		
		InputStream irabas_1 = cM.getAssets_directory().open("images_presets/rabas.mp4");	
		moveFileFromAssets(irabas_1, new File(base + "/" + rabas_1 + "/rabas.mp4"));					
		 
		String menu_2 = "menu_noche";
		String rabas_2 = "httpd/media/menus/" + menu_2 + "/categories/entradas/";
		
		InputStream irabas_2 = cM.getAssets_directory().open("images_presets/rabas.mp4");	
		moveFileFromAssets(irabas_2, new File(base + "/" + rabas_2 + "/rabas.mp4"));
		
		 
	 }
	
	private File[] items;
	private String small;
	private String medium;
	private String large;
		
	public static String removeExtention(String filePath) {
	    // These first few lines the same as Justin's
	    File f = new File(filePath);

	    // if it's a directory, don't remove the extention
	    if (f.isDirectory()) return filePath;

	    String name = f.getName();

	    // Now we know it's a file - don't need to do any special hidden
	    // checking or contains() checking because of:
	    final int lastPeriodPos = name.lastIndexOf('.');
	    if (lastPeriodPos <= 0)
	    {
	        // No period after first character - return name as it was passed in
	        return filePath;
	    }
	    else
	    {
	        // Remove the last period and everything after it
	        File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
	        return renamed.getPath();
	    }
	}
		
	
	
	/**
	 * copyMenuMananaTardeToAssets
	 * @param menu
	 * @throws IOException
	 */
	private void copyMenuMananaTardeToAssets(String menu) throws IOException{
		
		String base = app.getSDCARD();		

		try {
			
			//COPIA BEBIDAS CON Y SIN ALCOHOL
			copyBebidas(menu, base);
			
			//menu_manana/categories/cafe/
			String cafe = "httpd/media/menus/"+menu+"/categories/cafe/";
		
				InputStream cafe_con_leche = cM.getAssets_directory().open("images_presets/cafe_con_leche.jpg");	
				moveFileFromAssets(cafe_con_leche, new File(base + "/" + cafe+"/cafe_con_leche.jpg"));						
													
				InputStream cafe_cortado = cM.getAssets_directory().open("images_presets/cortado.jpg");	
				moveFileFromAssets(cafe_cortado, new File(base + "/" + cafe+"/cortado.jpg"));	
			
			//menu_manana/categories/infusiones/
			String infusiones = "httpd/media/menus/"+menu+"/categories/infusiones/";
					
				InputStream infusiones_te = cM.getAssets_directory().open("images_presets/te.jpg");	
				moveFileFromAssets(infusiones_te, new File(base + "/" + infusiones + "/te.jpg"));
				
			//menu_manana/categories/jugos/
			String jugos = "httpd/media/menus/"+menu+"/categories/jugos/";
				
					InputStream jugo_de_limon = cM.getAssets_directory().open("images_presets/jugo_de_limon.jpg");	
					moveFileFromAssets(jugo_de_limon, new File(base + "/" + jugos + "/jugo_de_limon.jpg"));		
					
					InputStream jugo_de_naranja = cM.getAssets_directory().open("images_presets/jugo_de_naranja.jpg");	
					moveFileFromAssets(jugo_de_naranja, new File(base + "/" + jugos + "/jugo_de_naranja.jpg"));			
						
					InputStream jugo_de_pomelo = cM.getAssets_directory().open("images_presets/jugo_de_pomelo.jpg");	
					moveFileFromAssets(jugo_de_pomelo, new File(base + "/" + jugos + "/jugo_de_pomelo.jpg"));			
			
			//menu_manana/categories/licuados/
			String licuados = "httpd/media/menus/"+menu+"/categories/licuados/";
			
				InputStream licuado_banana = cM.getAssets_directory().open("images_presets/licuado_de_banana.jpg");	
				moveFileFromAssets(licuado_banana, new File(base + "/" + licuados + "/licuado_de_banana.jpg"));		
				
				InputStream licuado_durazno = cM.getAssets_directory().open("images_presets/licuado_de_durazno.jpg");	
				moveFileFromAssets(licuado_durazno, new File(base + "/" + licuados + "/licuado_de_durazno.jpg"));		
					
				InputStream licuado_frutilla = cM.getAssets_directory().open("images_presets/licuado_de_frutilla.jpg");	
				moveFileFromAssets(licuado_frutilla, new File(base + "/" + licuados + "/licuado_de_frutilla.jpg"));		
	
			//menu_manana/categories/tostados/
			String tostados = "httpd/media/menus/"+menu+"/categories/tostados/";
			
				InputStream tostado_de_jamon_y_queso = cM.getAssets_directory().open("images_presets/tostado_mixto.jpg");	
				moveFileFromAssets(tostado_de_jamon_y_queso, new File(base + "/" + tostados + "/tostado_mixto.jpg"));
				
				InputStream tostado_de_ternera = cM.getAssets_directory().open("images_presets/tostado_de_ternera.jpg");	
				moveFileFromAssets(tostado_de_ternera, new File(base + "/" + tostados + "/tostado_de_ternera.jpg"));	
			
			//menu_manana/categories/tortas/
			String tortas = "httpd/media/menus/"+menu+"/categories/tortas/";
			
				InputStream torta_selva_negra = cM.getAssets_directory().open("images_presets/selva_negra.jpg");	
				moveFileFromAssets(torta_selva_negra, new File(base + "/" + tortas + "/selva_negra.jpg"));	
				
				InputStream torta_hojaldre = cM.getAssets_directory().open("images_presets/hojaldre.jpg");	
				moveFileFromAssets(torta_hojaldre, new File(base + "/" + tortas + "/hojaldre.jpg"));	
			
		} catch (IOException e) {			
			e.printStackTrace();
		}	
		
	}
	
	private void copyApkToSDCARD() throws IOException{		
		
	}
	
	/**
	 * 
	 * @param menu
	 */
	private void copyMenuMediodiaNocheToAssets(String menu){
		
		String base = app.getSDCARD();		
		
		try {
			
			//COPIA BEBIDAS CON Y SIN ALCOHOL
			copyBebidas(menu, base);
			
			//categories/entradas/
			String entradas = "httpd/media/menus/"+menu+"/categories/entradas/";
		
				InputStream rabas = cM.getAssets_directory().open("images_presets/rabas.jpg");	
				moveFileFromAssets(rabas, new File(base + "/" + entradas + "/rabas.jpg"));						
													
				InputStream brochettes = cM.getAssets_directory().open("images_presets/brochettes.jpg");	
				moveFileFromAssets(brochettes, new File(base + "/" + entradas + "/brochettes.jpg"));
				
			//categories/ensaladas/
			String ensaladas = "httpd/media/menus/"+menu+"/categories/ensaladas/";
			
				InputStream ensalada_cesar = cM.getAssets_directory().open("images_presets/ensalada_cesar.jpg");	
				moveFileFromAssets(ensalada_cesar, new File(base + "/" + ensaladas + "/ensalada_cesar.jpg"));			
						
			//categories/sopas/
			String sopas = "httpd/media/menus/"+menu+"/categories/sopas/";
			
				InputStream sopa_de_verduras = cM.getAssets_directory().open("images_presets/sopa_de_verduras.jpg");	
				moveFileFromAssets(sopa_de_verduras, new File(base + "/" + sopas + "/sopa_de_verduras.jpg"));	
				
			//categories/pastas/
			String pastas = "httpd/media/menus/"+menu+"/categories/pastas/";
					
				InputStream ravioles = cM.getAssets_directory().open("images_presets/ravioles.jpg");	
				moveFileFromAssets(ravioles, new File(base + "/" + pastas + "/ravioles.jpg"));		
				
				InputStream tallarines = cM.getAssets_directory().open("images_presets/tallarines.jpg");	
				moveFileFromAssets(tallarines, new File(base + "/" + pastas + "/tallarines.jpg"));			
					
			//categories/pescados/
			String pescados = "httpd/media/menus/"+menu+"/categories/pescados/";	
			
				InputStream merluza = cM.getAssets_directory().open("images_presets/merluza.jpg");	
				moveFileFromAssets(merluza, new File(base + "/" + pescados + "/merluza.jpg"));		
			
			//categories/sandwiches/
			String sandwiches = "httpd/media/menus/"+menu+"/categories/sandwiches/";	
				
				InputStream milanesa = cM.getAssets_directory().open("images_presets/milanesa.jpg");	
				moveFileFromAssets(milanesa, new File(base + "/" + sandwiches + "/milanesa.jpg"));		
				
				InputStream ternera = cM.getAssets_directory().open("images_presets/ternera.jpg");	
				moveFileFromAssets(ternera, new File(base + "/" + sandwiches + "/ternera.jpg"));
				
				InputStream hamburguesa = cM.getAssets_directory().open("images_presets/hamburguesa.jpg");	
				moveFileFromAssets(hamburguesa, new File(base + "/" + sandwiches + "/hamburguesa.jpg"));
				
			//categories/carnes/
			String carnes = "httpd/media/menus/"+menu+"/categories/carnes/";
				
				InputStream lomo_al_verdeo = cM.getAssets_directory().open("images_presets/lomo_al_verdeo.jpg");	
				moveFileFromAssets(lomo_al_verdeo, new File(base + "/" + carnes + "/lomo_al_verdeo.jpg"));
				
				InputStream milanesa_a_la_napolitana = cM.getAssets_directory().open("images_presets/milanesa_a_la_napolitana.jpg");	
				moveFileFromAssets(milanesa_a_la_napolitana, new File(base + "/" + carnes + "/milanesa_a_la_napolitana.jpg"));
				
				InputStream suprema_a_la_napolitana = cM.getAssets_directory().open("images_presets/suprema_a_la_napolitana.jpg");	
				moveFileFromAssets(suprema_a_la_napolitana, new File(base + "/" + carnes + "/suprema_a_la_napolitana.jpg"));
				
			//categories/postres/
			String postres = "httpd/media/menus/"+menu+"/categories/postres/";
			
				InputStream helado = cM.getAssets_directory().open("images_presets/helado.jpg");	
				moveFileFromAssets(helado, new File(base + "/" + postres + "/helado.jpg"));		
				
				InputStream ensalada_de_frutas = cM.getAssets_directory().open("images_presets/ensalada_de_frutas.jpg");	
				moveFileFromAssets(ensalada_de_frutas, new File(base + "/" + postres + "/ensalada_de_frutas.jpg"));			
			
			
		} catch (IOException e) {			
			e.printStackTrace();
		}	
		
		
		
	}
	
	public void copyBebidas(String menu, String base) throws IOException{
		
		//categories/bebidas_sin_alcohol/
		String bebidas_sin_alcohol = "httpd/media/menus/"+menu+"/categories/bebidas_sin_alcohol/";
				
			InputStream agua_sin_gas = cM.getAssets_directory().open("images_presets/agua_sin_gas.jpg");	
			moveFileFromAssets(agua_sin_gas, new File(base + "/" + bebidas_sin_alcohol + "/agua_sin_gas.jpg"));
			
			InputStream agua_con_gas = cM.getAssets_directory().open("images_presets/agua_con_gas.jpg");	
			moveFileFromAssets(agua_con_gas, new File(base + "/" + bebidas_sin_alcohol + "/agua_con_gas.jpg"));		

			InputStream gaseosa = cM.getAssets_directory().open("images_presets/gaseosa.jpg");	
			moveFileFromAssets(gaseosa, new File(base + "/" + bebidas_sin_alcohol + "/gaseosa.jpg"));
		
		//categories/bebidas_con_alcohol/
		String bebidas_con_alcohol = "httpd/media/menus/"+menu+"/categories/bebidas_con_alcohol/";
		
			InputStream vino = cM.getAssets_directory().open("images_presets/vino.jpg");	
			moveFileFromAssets(vino, new File(base + "/" + bebidas_con_alcohol + "/vino.jpg"));	
		
			InputStream champagne = cM.getAssets_directory().open("images_presets/champagne.jpg");	
			moveFileFromAssets(champagne, new File(base + "/" + bebidas_con_alcohol + "/champagne.jpg"));	
			
	}	
	
	
	/**
	 * copyFilestoSdcard
	 * @param dir
	 * @param force
	 * @return
	 */
	public File copyFilestoSdcard(String dir, boolean force){
		
		File file = null;
		File d = null;
		try{
			
			String arr[] = cM.getAssets_directory().list(dir);		
			
			String finalfile = app.getSDCARD() + "/"+dir;
			
			d = new File(finalfile);
			//File d = new File(dir);//Guarda con esto
			
			if(!d.exists() || force == true){
				
				d.mkdirs();

				int count = arr.length;

				for(int i=0;i<count;i++){				
					
					file = copyFile(dir, arr[i]);
				}
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if (file==null){
			file = d;
		}
		
		return file;
	}
    
     
    public File copyFile(String dir, String file) {
    	
    	File fileResult;
    	InputStream is;
    	
		try {
			
			is = cM.getAssets_directory().open(dir+"/"+file);
			String out = app.getSDCARD() + "/"+dir+"/"+file;
			
			FileOutputStream myOutput = new FileOutputStream(out);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer))>0)
			{
				myOutput.write(buffer, 0, length);
			}

			//Close the streams
			myOutput.flush();
			myOutput.close();
			is.close();
			
			fileResult = new File(out);
			
			if (!fileResult.exists()){
				Log.v("","");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fileResult = null;
		}	
		
		return fileResult;
    	
    }
    
    
    public boolean moveFileFromAssets(InputStream in, File destFile) {
    	boolean result = false;
    	try {
            try {
                result = copyToFile(in, destFile);
            } finally  {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }  	
    	return result;
    }
    
    
    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    public boolean moveFile(File srcFile, File destFile, boolean erase) {
    	boolean result = false;
    	try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally  {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }    	
    	if (result && erase){    		
    		srcFile.delete();
    	}    	
    	return result;
    }
    
    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     * @throws IOException 
     */
    public static boolean copyToFile(InputStream inputStream, File destFile) throws IOException {
        
        OutputStream out = new FileOutputStream(destFile);
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            out.close();
        }
        return true;
       
    }  
    
    /**
     * copyDirectory
     * @param sourceLocation
     * @param targetLocation
     * @throws IOException
     */
    public boolean copyDirectory(File sourceLocation, File targetLocation)
    	      throws IOException {

    	try {
		    if (sourceLocation.isDirectory()) {
		      if (!targetLocation.exists()) {
		        targetLocation.mkdirs();
		      }
	
		      String[] children = sourceLocation.list();
		      for (int i = 0; i < children.length; i++) {
		        copyDirectory(new File(sourceLocation, children[i]), new File(
		            targetLocation, children[i]));
		      }
		    } else {
		    	copyFileFromDirectory(sourceLocation, targetLocation);
		    }
    	} catch (IOException e) {
            return false;
        }    
    	
    	return true;
    	
	  }

	  /**
	   * @param sourceLocation
	   * @param targetLocation
	   * @throws FileNotFoundException
	   * @throws IOException
	   */
	  public static void copyFileFromDirectory(File sourceLocation, File targetLocation)
	      throws FileNotFoundException, IOException {
	    InputStream in = new FileInputStream(sourceLocation);
	    OutputStream out = new FileOutputStream(targetLocation);

	    // Copy the bits from instream to outstream
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	      out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	  }

	  static public boolean deleteDirectory(File path) {
	    if (path.exists()) {
	      File[] files = path.listFiles();
	      for (int i = 0; i < files.length; i++) {
	        if (files[i].isDirectory()) {
	          deleteDirectory(files[i]);
	        } else {
	          files[i].delete();
	        }
	      }
	    }
	    return (path.delete());
	  }
    
    
    
    
	
}
