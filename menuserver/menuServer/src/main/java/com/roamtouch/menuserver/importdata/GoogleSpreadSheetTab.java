package com.roamtouch.menuserver.importdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.util.IOUtils;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android2.AndroidHttp;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.database.DataBaseController;

public class GoogleSpreadSheetTab {
	
	public String GOOGLE_ACCOUNT_USERNAME;
    public String GOOGLE_ACCOUNT_PASSWORD;
    private String SPREADSHEET_SERVICE_URL;

	private TabAsync tabsasync;

    private boolean include_media;

	private JsonFactory JSON_FACTORY;
	private HttpTransport httpTransport;
	private Credential credential;

	private MenuServerApplication app;
    
    private static DataBaseController database;
    
    private String[] TABS = {
    		"menus",
    		"categories",    		    		
    		"categories_menus",
    		"menu_item",
    		"menu_items_categories",
    		"menu_item_thumbs",
    		"menu_item_videos"    		
    };     
	
    public GoogleSpreadSheetTab(MenuServerApplication app, DataBaseController database, String user, String pass, String fileName, boolean include_media) throws GeneralSecurityException, IOException {

		this.app = app;

    	this.database = database;
    	this.include_media = include_media;
		
		this.GOOGLE_ACCOUNT_USERNAME = user; 
		this.GOOGLE_ACCOUNT_PASSWORD = pass; 
		
		this.SPREADSHEET_SERVICE_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

		try
		{

			Context c = app.getContext();
			AssetManager am = c.getAssets();
			InputStream initialStream = am.open("credentials/Restomatic-89c16c6dd8be.p12");

			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);

			File targetFile = new File("mnt/sdcard/Restomatic-89c16c6dd8be.p12");
			targetFile.createNewFile();
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			JSON_FACTORY = JacksonFactory.getDefaultInstance();
			httpTransport = AndroidHttp.newCompatibleTransport();

			List<String> SCOPES_ARRAY = Arrays.asList(
					"https://www.googleapis.com/auth/drive.file",
					"https://www.googleapis.com/auth/userinfo.email",
					"https://www.googleapis.com/auth/userinfo.profile",
					"https://docs.google.com/feeds",
					"https://spreadsheets.google.com/feeds");

			credential = new GoogleCredential.Builder()
					.setTransport(httpTransport)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId("329748141446-g8i68qubpons8t9dvh49kpumghfhb6i6@developer.gserviceaccount.com")
					.setServiceAccountPrivateKeyFromP12File(targetFile)
					.setServiceAccountScopes(SCOPES_ARRAY).build();


		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (GeneralSecurityException e)
		{
			e.printStackTrace();
		}
	    
	}	
	
	public void syncronize()
	{
		tabsasync = new TabAsync();
		tabsasync.execute();
	}
	

	private void loadMenus(List<Map<String,Object>> rows){
		
		for (int i=0;i<rows.size(); i++){		
			
			HashMap map = (HashMap) rows.get(i);
			
			for (int j=0; j<map.size(); j++){			
				
				String menu_id = (String) map.get("id");
				
				try {
					
					JSONArray json = database.GET_MENUS();
					
					JSONArray result;
					String description_menu = (String) map.get("description");
					String folder_name = (String) map.get("foldername");				
					
					String interval_from = (String) map.get("intervalfrom");
					String interval_to = (String) map.get("intervalto");					
					
					boolean exist = false; 
					
					if (json.length()>0){
						
						for (int k=0; k<json.length(); k++){				
							if (menu_id.equals(json.get(0))){
								exist = true;
							}
						}
					} 					
					
					if (!exist){	
						
						result = database.INSERT_MENU(description_menu, folder_name, interval_from, interval_to);
						
					} else {
						
						result = database.UPDATE_MENU (description_menu, folder_name, interval_from, interval_to, menu_id);								
				
					}
					
					
					Log.v("","");
					
				} catch (JSONException e) {							
					e.printStackTrace();
				}	
				
			}			
		}		
	}
	
	private void loadCategories(List<Map<String,Object>> rows){
		
		for (int i=0;i<rows.size(); i++){		
			
			HashMap map = (HashMap) rows.get(i);
			
			//for (int j=0; j<map.size(); j++){			
				
				String category_id = (String) map.get("id");
				
				try {
					
					JSONArray json = database.GET_CATEGORIES_BY_ID(category_id);
					
					JSONArray result;
					String description_category = (String) map.get("description");
					String folder_name = (String) map.get("foldername");
					
					if (json.length()==0){	
						
						result = database.INSERT_CATEGORY(description_category, folder_name);
						
					} else {
						
						result = database.UPDATE_CATEGORY(description_category, folder_name, category_id);
						
					}
					
					
					Log.v("","");
					
				} catch (JSONException e) {							
					e.printStackTrace();
				}	
				
			//}			
		}		
	}
	
	private void loadCategoriesMenus(List<Map<String,Object>> rows){
		
		for (int i=0;i<rows.size(); i++){		
			
			HashMap map = (HashMap) rows.get(i);
			
			for (int j=0; j<map.size(); j++){			
				
				String categories_menus_id = (String) map.get("id");
				
				try {
					
					JSONArray json = database.GET_CATEGORY_BY_ID(categories_menus_id);
					
					JSONArray result;
					String category_id = (String) map.get("categoryid");
					String menu_id = (String) map.get("menuid");		
					
					if (json.length()==0){		
											
						result = database.INSERT_CATEGORY_PER_MENU_ID(category_id, menu_id);			
				
					} else {
						
						result = database.UPDATE_CATEGORIES_MENUS(categories_menus_id, menu_id, category_id);
				
					}					
					
					Log.v("","");
					
				} catch (JSONException e) {							
					e.printStackTrace();
				}	
				
			}			
		}		
	}
	
	private void loadMenuItem(List<Map<String,Object>> rows){
		
		for (int i=0;i<rows.size(); i++){		
			
			HashMap map = (HashMap) rows.get(i);
			
			for (int j=0; j<map.size(); j++){			
				
				String menu_item_id = (String) map.get("id");
				
				try {
					
					JSONArray json = database.GET_MENU_ITEM_BY_ID(menu_item_id);
					
					JSONArray result;
					String code = (String) map.get("code");
					String name = (String) map.get("name");
					String description = (String) map.get("description");	
					String price = (String) map.get("price");	
					String order_count = (String) map.get("ordercount");
					
					if (json.length()==0){		
											
						result = database.INSERT_MENU_ITEM(name, description, price, code);		
				
					} else {
						
						result = database.UPDATE_MENU_ITEM_BY_MENU_ITEM_ID (menu_item_id, name, description, price, code);					
						
					}					
					
					Log.v("","");
					
				} catch (JSONException e) {							
					e.printStackTrace();
				}	
				
			}			
		}		
	}
	
	private void loadMenuItemsCategories(List<Map<String,Object>> rows){
		
		for (int i=0;i<rows.size(); i++){		 
			
			HashMap map = (HashMap) rows.get(i);
			
			//for (int j=0; j<map.size(); j++){			
				
				String menu_items_categories_id = (String) map.get("id");
				
				try {
					
					JSONArray json = database.GET_MENU_ITEM_CATEGORIES_BY_MENU_ITEM_CATEGORIES_ID(menu_items_categories_id);
					
					JSONArray result;
					String menu_item_id = (String) map.get("menuitemid");
					String categories_menus_id = (String) map.get("categoriesmenusid");			
					
					if (json.length()==0){		
											
						result = database.INSERT_MENU_ITEM_CATEGORIES(menu_item_id, categories_menus_id);			
				
					} else {
						
						result = database.UPDATE_MENU_ITEM_CATEGORIES_BY_MENU_ITEM_CATEGORIES_ID(menu_item_id, categories_menus_id, menu_items_categories_id);
				
					}					
					
					Log.v("","");
					
				} catch (JSONException e) {							
					e.printStackTrace();
				}	
				
			//}			
		}		
	}
	
	private void loadMenuItemsThumbs(List<Map<String,Object>> rows){	

		int size = rows.size();

		for (int i=0;i<size; i++)
		{

			if (i>92)
			{
				Log.v("","");
			}
			
			HashMap map = (HashMap) rows.get(i);		

			String menu_item_thumbs_id = (String) map.get("id");

			try
			{

				JSONArray json = database.GET_MENU_ITEM_THUMB_BY_MENU_ITEM_THUMBS_ID(menu_item_thumbs_id);

				JSONArray result;
				String menu_item_id = (String) map.get("menuitemid");
				String img_small = (String) map.get("imgsmall");
				String img_medium = (String) map.get("img_medium");
				String img_large = (String) map.get("imglarge");

				if (json.length()==0){

					result = database.INSERT_MENU_ITEM_THUMBNAIL(menu_item_id, img_small, img_medium, img_large);

				} else {

					result = database.UPDATE_MENU_ITEM_THUMBNAIL_BY_MENU_ITEM_THUMBNAIL_ID (menu_item_id, img_small, img_medium, img_large, menu_item_thumbs_id);

				}

				Log.v("","");

			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	private void loadMenuItemsVideos(List<Map<String,Object>> rows){
		
		for (int i=0;i<rows.size(); i++){		 
			
			HashMap map = (HashMap) rows.get(i);
			
			for (int j=0; j<map.size(); j++){			
				
				String _id = (String) map.get("id");
				
				try {
					
					JSONArray json = database.GET_MENU_ITEM_VIDEOS_BY_MENU_ITEM_VIDEOS_ID(_id);
					
					JSONArray result;					
					String path = (String) map.get("path");			
					
					if (json.length()==0){		
											
						result = database.INSERT_MENU_ITEM_VIDEO(_id, path);			
				
					} else {
						
						result = database.UPDATE_MENU_ITEM_VIDEO_BY_MENU_ITEM_VIDEO_ID (_id, path);
				
					}					
					
				} catch (JSONException e) {							
					e.printStackTrace();
				}	
				
			}			
		}		
	}
	
	private class TabAsync extends AsyncTask<String, Void, List<Map<String,Object>>> {

		List<Map<String,Object>> rows;

	    SpreadsheetService service = new SpreadsheetService("spreadsheetservice");
	    
	    private String tabName;
	    			
	    @Override
		protected List<Map<String,Object>> doInBackground(String... params) {

			try
			{

				service.setOAuth2Credentials(credential);

				String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/1fHAJPvSkXrrXmOdhtx557j8plgCaqlZmNo--dEr2RLc"; //Fill in google spreadsheet URI

				URL metafeedUrl = new URL(SPREADSHEET_URL);
				SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);

				List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();

				// Iterate through each worksheet in the spreadsheet.
				for (int i=0; i< worksheets.size(); i++)
				{

					rows = new ArrayList<Map<String,Object>>();

					tabName = spreadsheet.getWorksheets().get(i).getTitle().getPlainText();

					int rowCount = spreadsheet.getWorksheets().get(i).getRowCount();
					int colCount = spreadsheet.getWorksheets().get(i).getColCount();

					// Print the fetched information to the screen for this worksheet.
					System.out.println("\t" + tabName + "- rows:" + rowCount + " cols: " + colCount);

					URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(i)).getListFeedUrl();

					ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);

					for (ListEntry row : feed.getEntries())
					{
						Map<String,Object> rowValues = getRowData(row);
						rows.add(rowValues);
					}

					if (tabName.equals(TABS[0]))
					{ // menus
						if (rows.size()>0)
						{
							loadMenus(rows);
						}
					} else if (tabName.equals(TABS[1]))
					{ // Categories
						if (rows.size()>0)
						{
							loadCategories(rows);
						}
					} else if (tabName.equals(TABS[2]))
					{ // categories_menus
						if (rows.size()>0)
						{
							loadCategoriesMenus(rows);
						}
					} else if (tabName.equals(TABS[3]))
					{  // menu_item
						if (rows.size()>0)
						{
							loadMenuItem(rows);
						}
					} else if (tabName.equals(TABS[4]))
					{ // menu_items_categories
						if (rows.size()>0)
						{
							loadMenuItemsCategories(rows);
							if (!include_media)
							{
								loaded_listener.onSpreadSheetLoaded();
								tabsasync.cancel(true);
							}
						}
					} else if (tabName.equals(TABS[5]) && include_media)
					{ // menu_item_thumbs
						if (rows.size()>0)
						{
							loadMenuItemsThumbs(rows);
						}
					} else if (tabName.equals(TABS[6]) && include_media)
					{ // menu_item_videos
						if (rows.size()>0)
						{
							loadMenuItemsVideos(rows);
							loaded_listener.onSpreadSheetLoadedWithMedia();
							tabsasync.cancel(true);
						}
					}
				}

			} catch (com.google.gdata.util.AuthenticationException ex)
			{
	    		ex.printStackTrace();
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ServiceException e)
			{
				e.printStackTrace();
			}

			return rows;

	    }



		@Override
        protected void onPostExecute(List<Map<String,Object>> map) {
	    	onGetSpreadSheetTab.tabDownloaded(map, tabName);    	
        }	    
	    
	    private SpreadsheetEntry getSpreadsheet(String sheetName) {
	        
	    	try {
	            URL spreadSheetFeedUrl = new URL(SPREADSHEET_SERVICE_URL);
	
	            SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(
	            spreadSheetFeedUrl);
	            spreadsheetQuery.setTitleQuery(sheetName);
	            spreadsheetQuery.setTitleExact(true);
	            SpreadsheetFeed spreadsheet = service.getFeed(spreadsheetQuery,
	                                                   SpreadsheetFeed.class);
	
	            if (spreadsheet.getEntries() != null
	                     && spreadsheet.getEntries().size() == 1) {
	                return spreadsheet.getEntries().get(0);
	            } else {
	                return null;
	            }
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
	
	        return null;
	    }
	    
	    private WorksheetEntry getWorkSheet(String sheetName, String workSheetName) {
	        try {
	            SpreadsheetEntry spreadsheet = getSpreadsheet(sheetName);
	
	            if (spreadsheet != null) {
	                WorksheetFeed worksheetFeed = service.getFeed(
	                      spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
	                List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	
	                for (WorksheetEntry worksheetEntry : worksheets) {
	                     String wktName = worksheetEntry.getTitle().getPlainText();
	                     if (wktName.equals(workSheetName)) {
	                         return worksheetEntry;
	                     }
	                 }
	             }
	         } catch (Exception ex) {
	        	 ex.printStackTrace();
	        }
	
	        return null;
	    }  
	    
	    
	    private Map<String, Object> getRowData(ListEntry row) {
	        Map<String, Object> rowValues = new HashMap<String, Object>();
	        for (String tag : row.getCustomElements().getTags()) {
	            Object value = row.getCustomElements().getValue(tag);
	            rowValues.put(tag, value);
	        }
	        return rowValues;
	    }

	    private ListEntry createRow(Map<String, Object> rowValues) {
	        ListEntry row = new ListEntry();
	        for (String columnName : rowValues.keySet()) {
	            Object value = rowValues.get(columnName);
	            row.getCustomElements().setValueLocal(columnName,
	                             String.valueOf(value));
	        }
	        return row;
	    }

	    private void updateRow(ListEntry row, Map<String, Object> rowValues) {
	        for (String columnName : rowValues.keySet()) {
	            Object value = rowValues.get(columnName);
	            row.getCustomElements().setValueLocal(columnName,
	                             String.valueOf(value));
	        }
	    }
	    
	    public void setOnGetSpreadSheetTab(OnGetSpreadSheetTab onGSST){
			onGetSpreadSheetTab = onGSST;		
		}
	    
	}
	
	public OnGetSpreadSheetTab onGetSpreadSheetTab;	
    
	public interface OnGetSpreadSheetTab {		
		public void tabDownloaded(List<Map<String,Object>> map, String tabName);
	}
	
	public SpreadSheetLoaderListener loaded_listener;	
	
	public static interface SpreadSheetLoaderListener {	        
    	void onSpreadSheetLoaded();
    	void onSpreadSheetLoadedWithMedia();
	}	
	
	public void setSpreadSheetLoaderListener(SpreadSheetLoaderListener spll) {
       this.loaded_listener = spll;
	}	
	
	

}
