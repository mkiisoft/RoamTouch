package com.roamtouch.menuserver.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.communications.APIProtocol;
import com.roamtouch.menuserver.communications.APIProtocolMethods;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.DateTools;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

public class DataBaseController {
	
	protected static final String TAG = "DataBaseController";
	
	private final Context mContext;
    private SQLiteDatabase db;
    private DataBaseHelper mDbHelper;
    
	private APIProtocolMethods APM; 
    
	private DateTools dTools;	
	
	private MenuServerApplication app;
	
	private APIProtocol API;
				
	public DataBaseController(Context context)
	{
						
		this.mContext = context;
		
		app = ((MenuServerApplication) this.mContext.getApplicationContext());		
        mDbHelper = new DataBaseHelper(mContext);       
        dTools = new DateTools();
        
	}
	
	public DataBaseController createDatabase() throws SQLException 
    {
        try 
        {
          boolean created = mDbHelper.createDataBase();
        } 
        catch (IOException mIOException) 
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }
	
	public DataBaseController open() throws SQLException 
    {
        try 
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            db = mDbHelper.getReadableDatabase();
        } 
        catch (SQLException mSQLException) 
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }
	
	public void close() 
    {
        mDbHelper.close();
    }
	
	public DataBaseHelper getmDbHelper() {
			return mDbHelper;
	}
	
	public void setAPI(APIProtocol API, APIProtocolMethods APM){
		this.API = API;
		this.APM = APM;	
	}
	 
	/**
	 * GET_MENUS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENUS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM menus",null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("folder_name", c.getString(2));					
						p.put("interval_from", c.getString(3));		
						p.put("interval_to", c.getString(4));					
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						String one = c.getString(3);
						String two = c.getString(4);
						
						boolean isActive = dTools.compareDates(one, two);
						
						String result;
						
						if (isActive){
							result = "1";
						} else {
							result = "0";
						}
						
						p.put("active", result);
						
						//ACTIVE=1
						//Parsear todos, en el que esta activo mandar
						//p.put("active", 1);
						
						arr.put(p);
						c.moveToNext();
			 								
				}	
			}	
				
			} finally {				
			    c.close();			    
			}
			
			
		}
		
		return arr;
	}		
	
	/**
	 * GET_MENU_BY_ID
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_BY_ID  (String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
				
			Cursor c = db.rawQuery("SELECT * FROM menus WHERE _id =" + menu_id + ";", null);		
			
			try {
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("folder_name", c.getString(2));		
						p.put("interval_from", c.getString(3));		
						p.put("interval_to", c.getString(4));		
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}
			} finally {
			    c.close();
			}			
		
		}
		
		return arr;
	}
	
	/**
	 * GET_MENUS_BY_MENU_DESCRIPTION
	 * @param description_menu
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENUS_BY_MENU_DESCRIPTION  (String description_menu) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			Cursor c = db.rawQuery("SELECT * FROM menus WHERE description LIKE '%"+ description_menu + "%';", null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("folder_name", c.getString(2));		
						p.put("interval_from", c.getString(3));		
						p.put("interval_to", c.getString(4));		
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));					
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}
				
			} finally {
			    c.close();
			}
				
			
		}
		
		return arr;
	}	
	
	/**
	 * GET_MENUS_BY_CATEGORY_ID
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENUS_BY_CATEGORY_ID  (String category_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			String query = "SELECT c._id, c.description, cm._id, cm.menu_id, m.description, c.created_at, c.updated_at FROM categories c " +
					"INNER JOIN categories_menus cm " + 
					"ON c._id = cm.category_id " +
					"INNER JOIN menus m " +
					"ON m._id = cm.menu_id " +
					"WHERE c._id = " + category_id + ";";
						   
			Cursor c = db.rawQuery(query, null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
				        
						p.put("description", c.getString(1));        
                        p.put("categories_menus_id", c.getInt(2));
                        p.put("menu_id", c.getInt(3));                                                
                        p.put("description_menu", c.getString(4));                                                
                        p.put("created_at", c.getString(5));
                        p.put("updated_at", c.getString(6));
                        
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}
				
			} finally {
			    c.close();
			}
				
			
		}
		
		return arr;
	}	
	
	
	/**
	 * INSERT_MENU
	 * @param description
	 * @param folderName
	 * @param interval_from
	 * @param interval_to
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENU (String description_menu, String folder_name, String interval_from, String interval_to) throws JSONException {
		
		JSONArray arr = APM.JSONReturn("status" ,1);				
		//db = this.myDbHelper.openDataBase();	
		Timestamp time = getCurrentTimeStamp();		
		String querySelect = "SELECT * FROM menus WHERE description='" + description_menu + "';";		
		Cursor c = db.rawQuery(querySelect, null);			
		
		try {
			
			if(c!=null){
				int count = c.getCount();
				if(count>0){			
					String insert_menu_error = Integer.toString(Constans.INSERT_MENU_EXISTS);		
					arr = APM.JSONReturn("status",insert_menu_error);				
					return arr;
				} else {						
					try
					{			
						
						String query = "INSERT INTO menus(description, folder_name, interval_from, interval_to, created_at, updated_at) "+						
									   "VALUES('"+description_menu+"','"+folder_name+"','"+interval_from+"','"+interval_to+"','"+time+"','"+time+"');";				
						
						db.execSQL(query);					
					}
					catch(Exception e)
					{
						e.printStackTrace();
						arr = APM.JSONReturn("status",e);				
						return arr;
					}			
				}				
			}
			
		} finally {
		    c.close();
		}				
		
		return arr;
	}	
			
	/**
	 * UPDATE_MENU
	 * @param description_menu
	 * @param folder_name
	 * @param interval_from
	 * @param interval_to
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU (String description_menu, String folder_name, String interval_from, String interval_to, String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			String query = "UPDATE menus SET description = '" + description_menu + "'" + 
				", folder_name = '" + folder_name + "'" + 
				", interval_from = '" + interval_from + "'" + 
				", interval_to = '" + interval_to + "'" + 
				", updated_at ='" + time + "'" +
				" WHERE _id = " + menu_id + ";";
					
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		arr.put(partial);
				
		return arr;
	}
	
	/**
	 * DELETE_MENU
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_MENU (String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM menus WHERE _id = " + menu_id + ";";			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		arr.put(partial);
			
		return arr;
	}
	

	/**
	 * GET_CATEGORIES
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORIES  () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{	
			
			String query = 
					"SELECT c._id, c.description, cm.menu_id, m.description, c.created_at, c.updated_at FROM categories c " +
					"INNER JOIN categories_menus cm " +
					"ON c._id = cm.category_id " +
					"INNER JOIN menus m " +
					"ON m._id = cm.menu_id;";					
			
			Cursor c = db.rawQuery(query, null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));				
						p.put("menu_id", c.getString(2));
						p.put("description_menu", c.getString(3));
						p.put("created_at", c.getString(4));
						p.put("updated_at", c.getString(5));
						
						Timestamp time = getCurrentTimeStamp();	
						
						arr.put(p);
						c.moveToNext();		 								
					}				
				}
				
			} finally {
			    c.close();
			}
			
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_CATEGORY_BY_ID
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORY_BY_ID (String category_id, String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = 
					"SELECT c._id, c.description, cm.menu_id, m.description, c.created_at, c.updated_at FROM categories c " +
						"INNER JOIN categories_menus cm " +
						"ON c._id = cm.category_id " +
						"INNER JOIN menus m " + 
						"ON m._id = cm.menu_id " +
						"WHERE c._id = " + category_id + " AND cm.menu_id = " + menu_id + ";";		
			
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					
					JSONObject p = new JSONObject();
					
					if (count==0){
						p.put("_id", "");
						arr.put(p);
						return arr;
					}
					
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 						
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));				
						
						arr.put(p);
						c.moveToNext();
			 								
					}
					
				}

			} finally {
			    c.close();
			}		
				
		}
		
		return arr;
	}
	
	/**
	 * GET_CATEGORIES_BY_MENU_ID
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORIES_BY_MENU_ID  (String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();	
		
		//if (!db.isOpen())
			//db = this.myDbHelper.openDataBase();		
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
				
			String query = "SELECT c._id, c.description, cm.menu_id, c.created_at, c.updated_at, cm._id as categories_menu_id FROM categories c " +
					"INNER JOIN categories_menus cm " +
					"ON c._id = cm.category_id " +
					"WHERE cm.menu_id  =  " + menu_id +";";
			
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));
						p.put("menu_id", c.getInt(2));						
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));
						p.put("categories_menu_id", c.getString(5));
						
						arr.put(p);
						c.moveToNext();			 								
					}	
				}		

			} finally {
				if (!c.isClosed())
					c.close();
			}		
					
		}
		
		return arr;
	}
	

	/**
	 * GET_CATEGORIES_BY_MENU_ITEM_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORIES_BY_MENU_ITEM_ID   (String menu_item_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();	
		
		//if (!db.isOpen())
			//db = this.myDbHelper.openDataBase();		
		
		//JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
				
			String query = 
					
					"SELECT cm.* FROM menu_item mi " +
					"INNER JOIN menu_items_categories mic " + 
					"ON mi._id = mic.menu_item_id " +
					"INNER JOIN categories_menus cm " +
					"ON cm._id = mic.categories_menus_id " +
					"WHERE mi._id = " + menu_item_id + ";";		
			
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("category_id", c.getInt(1));
						p.put("menu_id", c.getInt(2));						
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));	
						
						
						arr.put(p);
						c.moveToNext();			 								
					}	
				}		

			} finally {
				if (!c.isClosed())
					c.close();
			}		
					
		}
		
		return arr;
	}
	
	
	
	/**
	 * GET_CATEGORIES_BY_ID
	 * @param id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORIES_BY_ID   (String id ) throws JSONException {
		
		JSONArray arr = new JSONArray();	
		
		if(db != null)
		{
				
			String query = 
					
					"SELECT * FROM categories WHERE _id =  " + id + ";";						
			
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getInt(1));					
						p.put("folder_name", c.getInt(2));						
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));	
						
						
						arr.put(p);
						c.moveToNext();			 								
					}	
				}		

			} finally {
				if (!c.isClosed())
					c.close();
			}		
					
		}
		
		return arr;
	}
	
	
	/**
	 * GET_CATEGORIES_BY_DESCRIPTION
	 * @param description_category
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORIES_BY_DESCRIPTION  (String description_category) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{					
			String query = "SELECT _id, description, created_at, updated_at FROM categories WHERE description='"+description_category+"';"; //description LIKE '%" + description_category + "%';";		
			
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));				
						p.put("created_at", c.getString(2));
						p.put("updated_at", c.getString(3));
						
						arr.put(p);
						c.moveToNext();			 								
					}	
				}	

			} finally {
			    c.close();
			}	
		
		}
		
		return arr;
	}
	
	/**
	 * INSERT_CATEGORY
	 * @param description_category
	 * @param folder_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_CATEGORY (String description_category, String folder_name) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();	
		Timestamp time = getCurrentTimeStamp();		
		String querySelect = "SELECT * FROM menus WHERE description='"+description_category+"';";		
		Cursor c = db.rawQuery(querySelect, null);
		JSONObject partial = new JSONObject();		
		
		try {
			
			if(c!=null){
				int count = c.getCount();
				if(count>0){	
					String insert_category_error = Integer.toString(Constans.INSERT_CATEGORY_EXISTS);				 
					partial.put("error", insert_category_error);					
				} else {						
					try
					{			
						String query = "INSERT INTO categories(description, folder_name, created_at, updated_at) " +
								"VALUES('"+description_category+"','"+folder_name+"','"+time+"','"+time+"');";					
						db.execSQL(query);					
					}
					catch(Exception e)
					{
						e.printStackTrace();
						partial.put("error", e);
					}			
				}			
			}


		} finally {
		    c.close();
		}	
	
		
		arr.put(partial);
		return arr;
	}	
	
	
	/**
	 * INSERT_CATEGORY_PER_MENU_ID
	 * @param category_id
	 * @param menu_id
	 * @param folder_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_CATEGORY_PER_MENU_ID (String category_id, String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		Timestamp time = getCurrentTimeStamp();		
		String querySelect = "SELECT * FROM menus WHERE description='"+category_id+"';";		
		Cursor c = db.rawQuery(querySelect, null);
		JSONObject partial = new JSONObject();		
		
		try {
			if(c!=null){
				int count = c.getCount();
				if(count>0){	
					String insert_category_error = Integer.toString(Constans.INSERT_CATEGORY_EXISTS);				 
					partial.put("error", insert_category_error);					
				} else {						
					try
					{	
						
						String query = "INSERT INTO categories_menus(category_id, menu_id, created_at, updated_at) " +
								"VALUES(" + category_id + ", " + menu_id + ", '" + time + "', '" +time + "');";						
		
						db.execSQL(query);					
					}
					catch(Exception e)
					{
						e.printStackTrace();
						partial.put("error", e);
					}			
				}			
			}
		} finally {
		    c.close();
		}	
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * GET_LAST_CATEGORY_ID
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_LAST_CATEGORY_ID  () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{					
			String query = "SELECT _id FROM categories  ORDER BY _id DESC LIMIT 1;";					
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));				
						
						arr.put(p);
						c.moveToNext();			 								
					}	
				}	

			} finally {
			    c.close();
			}	
		
		}
		
		return arr;
	}
	
	/**
	 * GET_CATEGORY_BY_CAT_ID
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORY_BY_CAT_ID  ( String category_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{					
			
			String query = "SELECT c._id, c.description, c.folder_name,c.created_at, c.updated_at FROM categories c WHERE c._id = "+ category_id + ";";							
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));								
						p.put("description", c.getString(1));
						p.put("folder_name", c.getString(2));
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));		
						
						arr.put(p);
						c.moveToNext();			 								
					}	
				}	

			} finally {
			    c.close();
			}	
		
		}
		
		return arr;
	}
	
	/**
	 * UPDATE_DESCRIPTION_BY_CAT_ID
	 * @param description
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_DESCRIPTION_BY_CAT_ID (String description, String category_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			
			String query = "UPDATE categories SET description = '" + description + "' WHERE _id = " + category_id + ";";		
					
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	

	
	/**
	 * UPDATE_CATEGORY
	 * @param description_category
	 * @param folder_name
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_CATEGORY (String description_category, String folder_name, String category_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			String query = "UPDATE categories SET description ='" + description_category + "', folder_name ='" + folder_name + "', updated_at ='" + time + "' WHERE _id = " + category_id + ";";
					
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * UPDATE_CATEGORY_PER_CATEGORIES_MENU_ID
	 * @param menu_id
	 * @param categories_menus_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_CATEGORY_PER_CATEGORIES_MENU_ID  (String menu_id, String categories_menus_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			String query = "UPDATE categories_menus SET menu_id = " + menu_id + " updated_at ='" + time + "'" + " WHERE _id = " + categories_menus_id + ";";			
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * UPDATE_CATEGORIES_MENUS
	 * @param _id
	 * @param menu_id
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_CATEGORIES_MENUS  (String _id, String menu_id, String category_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			String query = "UPDATE categories_menus SET category_id = " + category_id + ", menu_id = " + menu_id + ", updated_at ='" + time + "'" + " WHERE _id = " + _id + ";";			
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}

	/**
	 * DELETE_CATEGORY
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_CATEGORY (String category_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM categories WHERE _id = " + category_id + ";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * DELETE_CATEGORY_BY_CATEGORY_AND_MENU_ID
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_CATEGORY_BY_CATEGORY_AND_MENU_ID (String category_id, String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM categories_menus WHERE category_id = "+category_id + " AND menu_id = "+menu_id+";";			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	
	
	
		
	/**
	 * GET_MENU_ITEMS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEMS () throws JSONException {
		
		JSONArray arr = new JSONArray();
				
		if(db != null)
		{			
			
			Cursor c = db.rawQuery("SELECT _id, name, description, price, created_at, updated_at FROM menu_item;",null);	
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));					
						p.put("description", c.getString(2));
						p.put("price", c.getString(3));
						p.put("created_at", c.getString(4));
						p.put("updated_at", c.getString(5));		
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}
				
			}  catch(Exception e) {
				
				arr = APM.JSONReturn("status",e);				
				return arr;
					
				
			} finally {
			    c.close();
			}			
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_MENU_ITEM_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_BY_ID  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{	
			String query = "SELECT _id, name, description, price, created_at, updated_at FROM menu_item WHERE _id = " + menu_item_id + ";";
			
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){	
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));
						p.put("name", c.getString(2));
						p.put("price", c.getDouble(3));
						p.put("created_at", c.getString(4));
						p.put("updated_at", c.getString(5));
						
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}		
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEMS_BY_NAME
	 * @param menu_item_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEMS_BY_NAME  (String menu_item_name) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
			String query = "SELECT _id, name, description, price, created_at, updated_at FROM menu_item " +
						"WHERE name LIKE '%" + menu_item_name + "%';";	
			
			Cursor c = db.rawQuery(query, null);				
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));				
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}	
				
			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEMS_BY_EXACT_NAME
	 * @param menu_item_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEMS_BY_EXACT_NAME  (String menu_item_name) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
			String query = "SELECT _id, name, description, price, created_at, updated_at FROM menu_item " +
						"WHERE name LIKE '" + menu_item_name + "';";	
			
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));				
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}		
		
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEMS_BY_DESCRIPTION
	 * @param menu_item_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEMS_BY_DESCRIPTION  (String menu_item_description) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{				
			String query = "SELECT _id, name, description, price, created_at, updated_at FROM menu_item " +
					"WHERE description LIKE ='%" + menu_item_description + "%';";
			
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(1));			
						p.put("price", c.getString(1));			
						p.put("category_id", c.getString(1));						
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEM_BY_CODE_ID
	 * @param menu_item_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_BY_CODE_ID  (String code_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{				
			String query = "SELECT _id, name, description, price, created_at, updated_at FROM menu_item WHERE code = '" + code_id + "';";
			
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getString(3));			
						p.put("category_id", c.getString(4));						
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEM_BY_PRICE
	 * @param menu_item_price
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_BY_PRICE  (String menu_item_price) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
			String query = "SELECT _id, name, description, price, created_at, updated_at FROM menu_item WHERE price ='%" + menu_item_price + "%';";
			Cursor c = db.rawQuery(query, null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getString(3));			
						p.put("category_id", c.getString(4));					
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
			
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEMS_BY_CATEGORY_ID
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEMS_BY_CATEGORY_ID  (String category_id, String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{		
			String query = 
					"SELECT mi._id, mi.name, mi.description,mi.price, mic.categories_menus_id, cm.menu_id, cm.category_id, mit.img_medium, mic._id as menu_item_categories_id FROM menu_item mi " +
					"INNER JOIN menu_items_categories mic " +
					"ON mi._id = mic.menu_item_id " +
					"INNER JOIN categories_menus cm " +
					"ON cm._id = mic.categories_menus_id " +
					"INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id "  + 
					"WHERE cm.category_id = " + category_id + "  AND cm.menu_id = " + menu_id + ";";		
								
			Cursor c = db.rawQuery(query , null);					
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getDouble(3));
						
						p.put("categories_menus_id", c.getInt(4));
						p.put("menu_id", c.getInt(5));
						p.put("category_id", c.getInt(6));
						p.put("img_medium", c.getString(7));
						p.put("menu_item_categories_id", c.getString(8));
						
						//p.put("created_at", c.getString(7));
						//p.put("updated_at", c.getString(8));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}


			} finally {
			    c.close();
			}	
			
				
		}
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEM_SMALL_THUMB_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_SMALL_THUMB_BY_ID  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
			
			String query = "SELECT mi._id, mi.name, mi.description, mi.price, mit.img_small, mi.created_at, mi.updated_at " +
					"FROM menu_item mi INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id WHERE mi._id = " + menu_item_id + " GROUP BY mi._id;";	
			
			Cursor c = db.rawQuery(query , null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getDouble(3));
						p.put("img_small", c.getInt(4));						
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}	
				
		}
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEM_MEDIUM_THUMB_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_MEDIUM_THUMB_BY_ID  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{	
			
			String query = "SELECT mi._id, mi.name, mi.description, mi.price, mit.img_medium, mi.created_at, mi.updated_at " + 
					"FROM menu_item mi INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id  WHERE mi._id = " + menu_item_id + " GROUP BY mi._id;"; 
			
			Cursor c = db.rawQuery(query , null);					
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getDouble(3));
						p.put("img_medium", c.getInt(4));						
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
			
		}
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEM_LARGE_THUMB_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_LARGE_THUMB_BY_ID  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{	
			
			String query = "SELECT mi._id, mi.name, mi.description, mi.price, mit.img_large, mi.created_at, mi.updated_at " +
					"FROM menu_item mi INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id WHERE mi._id = " + menu_item_id + " GROUP BY mi._id;";
			
			Cursor c = db.rawQuery(query , null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getDouble(3));
						p.put("img_large", c.getInt(4));						
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
		} 
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEM_VIDEOS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_VIDEOS  () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
					
			String query =		
			"SELECT miv._id, miv.menu_item_id, mi.name, miv.path, miv.created_at, miv.updated_at FROM menu_item_videos miv " + 
			"INNER JOIN menu_item mi " +  
			"ON mi._id = miv.menu_item_id;";
			
			Cursor c = db.rawQuery(query , null);					
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("menu_item_id", c.getInt(1));
						p.put("name", c.getString(2));			
						p.put("path", c.getDouble(3));												
						p.put("created_at", c.getString(4));
						p.put("updated_at", c.getString(5));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}	

			} finally {
			    c.close();
			}			
		
		}
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEM_VIDEOS_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_VIDEOS_BY_ID  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			  
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
					
			String query =		
			"SELECT miv._id, miv.menu_item_id, mi.name, miv.path, miv.created_at, miv.updated_at FROM menu_item_videos miv " +
			"INNER JOIN menu_item mi " + 
			"ON mi._id = miv.menu_item_id "+
			"WHERE miv.menu_item_id = " + menu_item_id + ";";
			
			Cursor c = db.rawQuery(query , null);	
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("menu_item_id", c.getInt(1));
						p.put("name", c.getString(2));			
						p.put("path", c.getString(3));												
						p.put("created_at", c.getString(4));
						p.put("updated_at", c.getString(5));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEM_VIDEOS_BY_MENU_ITEM_VIDEOS_ID
	 * @param _id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_VIDEOS_BY_MENU_ITEM_VIDEOS_ID  (String _id) throws JSONException {
		
		JSONArray arr = new JSONArray();			  
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
					
			String query =		
			"SELECT * FROM menu_item_videos WHERE _id = " + _id + ";";
			
			Cursor c = db.rawQuery(query , null);	
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));						
						p.put("name", c.getString(2));			
						p.put("path", c.getString(3));												
						p.put("created_at", c.getString(4));
						p.put("updated_at", c.getString(5));
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEM_THUMB_BY_MENU_ITEM_CATEGORIES_ID
	 * @param menu_item_categories_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMB_BY_MENU_ITEM_CATEGORIES_ID  (String menu_item_categories_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
			String query = 
					"SELECT mi._id, mi.name, mi.description, mi.price, mit.img_large, mic._id as menu_item_categories_id, mi.created_at, mi.updated_at " +    
					"FROM menu_items_categories as mic " + 
					"INNER JOIN menu_item mi ON mi._id = mic.menu_item_id " +
					"INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id " + 
					"WHERE mic._id =  " + menu_item_categories_id + ";";		
			Cursor c = db.rawQuery(query , null);					
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getDouble(3));
						p.put("img_large", c.getString(4));
						p.put("menu_item_categories_id", c.getInt(5));			
						p.put("created_at", c.getString(6));
						p.put("updated_at", c.getString(7));
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}					
		}		
		
		return arr;
	}
	
	
	/**
	 * GET_MENU_ITEM_THUMBS_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMBS_BY_ID  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
					
			String query = 
					"SELECT mi._id, mi.name, mi.description, mi.price, mit.img_small, mit.img_medium, mit.img_large, mi.created_at, mi.updated_at " +
					"FROM menu_item mi " +
					"INNER JOIN menu_item_thumbs mit " +
					"ON mi._id = mit.menu_item_id " +
					"WHERE mi._id = " + menu_item_id + " " +
					"GROUP BY mi._id;";					
			
			Cursor c = db.rawQuery(query , null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));			
						p.put("price", c.getDouble(3));
						p.put("img_small", c.getString(4));	
						p.put("img_medium", c.getString(5));	
						p.put("img_large", c.getString(6));							
						p.put("created_at", c.getString(7));
						p.put("updated_at", c.getString(8));
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}
	
	/**
	 * INSERT_MENU_ITEM
	 * @param menu_item_name
	 * @param menu_item_description
	 * @param menu_item_price
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENU_ITEM (String menu_item_name, String menu_item_description, String menu_item_price, String menu_item_code) throws JSONException {
		
		JSONArray arr = null;
		
		Timestamp time = getCurrentTimeStamp();		
		String querySelect = "SELECT * FROM menu_item WHERE name='"+menu_item_name+"';";		
		Cursor c = db.rawQuery(querySelect, null);				
		
		try {
			if(c!=null){
				int count = c.getCount();
				if(count>0){					
					String insert_menu_item_error = Integer.toString(Constans.INSERT_MENU_ITEM_EXISTS);				 
					arr = APM.JSONReturn("status",insert_menu_item_error);					
					return arr;
				} else {						
					try
					{	
						String query = "INSERT INTO menu_item(name, description, price, code, created_at, updated_at) " +					
						"VALUES('"+menu_item_name+"','"+menu_item_description+"','"+menu_item_price+"','"+menu_item_code+"','"+time+"','"+time+"');";					
						db.execSQL(query);	
						arr = APM.JSONReturn("status" ,1);
		
					}
					catch(Exception e)
					{					
						e.printStackTrace();
						arr = APM.JSONReturn("status",e);
						return arr;
					}			
				}		
			}
		} finally {
		    c.close();
		}	
		
		return arr;
	}	
	
	
	/**
	 * UPDATE_MENU_ITEM
	 * @param menu_item_name
	 * @param menu_item_description
	 * @param menu_item_price
	 * @param category_id 
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM (String menu_item_name, String menu_item_description, String menu_item_price, String menu_item_id ) throws JSONException {		
		
		JSONArray arr = new JSONArray();
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			String query = "UPDATE menu_item SET name = '"+menu_item_name+"', description = '"+menu_item_description+"', price ='"+menu_item_price+"', updated_at ='"+time+"' WHERE _id = " + menu_item_id +";";			
			db.execSQL(query);			
			arr = APM.JSONReturn("status" ,1);
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			arr = APM.JSONReturn("status",e);
		}		
		
		return arr;
	}
	
	/**
	 * UPDATE_MENU_ITEM_BY_MENU_ITEM_ID
	 * @param _id
	 * @param menu_item_name
	 * @param menu_item_description
	 * @param menu_item_price
	 * @param code
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_BY_MENU_ITEM_ID (String _id, String menu_item_name, String menu_item_description, String menu_item_price, String code) throws JSONException {
		
		JSONArray arr = new JSONArray();				
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();	
			String query = "UPDATE menu_item SET name = '" + menu_item_name + "', description = '" + menu_item_description + "', price ='" + menu_item_price + "', code ='" + code + "', updated_at ='"+time+"' WHERE _id = " + _id +";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	
	/**
	 * UPDATE_MENU_ITEM_PER_CATEGORIES_ID
	 * @param categories_menus_id
	 * @param menu_item_categories_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_PER_CATEGORIES_ID (String categories_menus_id, String menu_item_categories_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();	
			String query = "UPDATE menu_items_categories SET categories_menus_id = " + categories_menus_id + " WHERE _id = " + menu_item_categories_id + ";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * DELETE_MENU_ITEM
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_MENU_ITEM (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			String query = "DELETE FROM menu_item WHERE _id =" + menu_item_id + ";";			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	
	/**
	 * INSERT_MENU_ITEM_THUMBNAIL
	 * @param menu_item_id
	 * @param img_small
	 * @param img_medium
	 * @param img_large
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENU_ITEM_THUMBNAIL (String menu_item_id, String img_small, String img_medium, String img_large) throws JSONException {
		
		JSONArray arr = new JSONArray();
			
		Timestamp time = getCurrentTimeStamp();		
		
		
		try
		{	
			String query = "INSERT INTO menu_item_thumbs(" +
					"menu_item_id, " +					
					"img_small, " +
					"img_medium, " +
					"img_large, " +											
					"created_at, " +
					"updated_at" +					
					") VALUES(" + menu_item_id + ",'" + img_small+"','"+img_medium+"','"+img_large+"','"+time+"','"+time+"');";			
		
			db.execSQL(query);	
			
			arr = APM.JSONReturn("status" ,1);	
		}		
		catch(Exception e)
		{
			arr = APM.JSONReturn("status",e);		
			e.printStackTrace();
			return arr;					
		}		
		return arr;		
		
	}	

	/**
	 * INSERT_MENU_ITEM_VIDEO
	 * @param menu_item_id
	 * @param name
	 * @param path
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENU_ITEM_VIDEO (String menu_item_id, String path) throws JSONException {
		
		JSONArray arr = APM.JSONReturn("status" ,1);		
				
		Timestamp time = getCurrentTimeStamp();		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			String query = "INSERT INTO menu_item_videos(" +
					"menu_item_id, " +					
					"path, " +												
					"created_at, " +
					"updated_at" +					
					") VALUES(" + menu_item_id + ", '" + path + "','"+time+"','"+time+"');";

			db.execSQL(query);			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			arr = APM.JSONReturn("status",e);		
			return arr;	
		}		
		
		return arr;		
		
	}	
	
	/**
	 * UPDATE_MENU_ITEM_THUMB
	 * @param path_to_small_image
	 * @param path_to_medium_image
	 * @param path_to_large_image
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_THUMB  (String path_to_small_image, String path_to_medium_image, String path_to_large_image, String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE menu_item_thumbs SET img_small = '" + path_to_small_image + "', " + 
							"img_medium = '" + path_to_medium_image + "', " +
							"img_large = '" + path_to_large_image + "', " + 
							"updated_at = '" + time + "' WHERE menu_item_id = " + menu_item_id + ";";		
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * UPDATE_MENU_ITEM_THUMBNAIL_BY_MENU_ITEM_THUMBNAIL_ID
	 * @param path_to_small_image
	 * @param path_to_medium_image
	 * @param path_to_large_image
	 * @param _id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_THUMBNAIL_BY_MENU_ITEM_THUMBNAIL_ID  (String menu_item_id, String path_to_small_image, String path_to_medium_image, String path_to_large_image, String _id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE menu_item_thumbs SET " +
							"menu_item_id = '" + menu_item_id + "', " +					
							"img_small = '" + path_to_small_image + "', " + 
							"img_medium = '" + path_to_medium_image + "', " +
							"img_large = '" + path_to_large_image + "', " + 
							"updated_at = '" + time + "' WHERE _id = " + _id + ";";		
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * GET_MENU_ITEM_BY_CAT_ID
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_BY_CAT_ID  (String category_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{	
			String query =  "SELECT mic.menu_item_id FROM menu_items_categories mic " +
							"INNER JOIN categories_menus cm " +  
							"ON cm._id = mic.categories_menus_id " +
							"WHERE cm.category_id = " + category_id + " " + 
							"GROUP BY menu_item_id;";
			
			Cursor c = db.rawQuery(query , null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("menu_item_id", c.getInt(0));					
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}	
	
	/**
	 * UPDATE_MENU_ITEM_ORDER_COUNT
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_ORDER_COUNT  (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE menu_item SET order_count = " + menu_item_id + " WHERE _id = menu_item_id;";		
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	
	/**
	 * UPDATE_MENU_ITEM_VIDEO
	 * @param path_to_the_video
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_VIDEO  (String path_to_the_video, String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();		 	
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE menu_item_videos SET path = '" + path_to_the_video + "', updated_at = '" + time + "' WHERE menu_item_id = " + menu_item_id + ";";		
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * UPDATE_MENU_ITEM_VIDEO_BY_MENU_ITEM_VIDEO_ID
	 * @param _id
	 * @param path_to_the_video
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_VIDEO_BY_MENU_ITEM_VIDEO_ID  (String _id, String path_to_the_video) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();		 	
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE menu_item_videos SET path = '" + path_to_the_video + ", updated_at = '" + time + "' WHERE _id = " + _id + ";";		
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * CHECK_MENU_ITEM_CATEGORIES
	 * @param categories_menus_id
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CHECK_MENU_ITEM_CATEGORIES (String categories_menus_id, String menu_item_id) throws JSONException {		
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			String query = "SELECT _id FROM menu_items_categories WHERE categories_menus_id = " + categories_menus_id + " AND menu_item_id = " + menu_item_id + ";";	
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;	
	}
	
	
	/**
	 * GET_MENU_ITEM_CATEGORIES_BY_MENU_ITEM_CATEGORIES_ID
	 * @param _id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_CATEGORIES_BY_MENU_ITEM_CATEGORIES_ID (String _id) throws JSONException {		
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			String query = "SELECT * FROM menu_items_categories WHERE _id = " + _id + ";";		
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;	
	}
	
	
	/**
	 * GET_MENU_ITEM_THUMB_AND_VIDEO_BY_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMB_AND_VIDEO_BY_ID (String menu_item_id) throws JSONException {		
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			String query = "SELECT mi._id, mi.name, mi.description, mi.price, mi.order_count, mit.img_small, mit.img_medium, mit.img_large, miv.path, mi.created_at, mi.updated_at " +
					"FROM menu_item mi " + 
					"LEFT JOIN menu_item_thumbs mit " +
					"ON mi._id = mit.menu_item_id " +
					"LEFT JOIN menu_item_videos miv " +
					"ON mi._id = miv.menu_item_id " +
					"WHERE mi._id = " + menu_item_id + ";";			
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();								
						
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));
						p.put("price", c.getString(3));
						p.put("order_count", c.getInt(4));
						p.put("img_small", c.getString(5));
						p.put("img_medium", c.getString(6));
						p.put("img_large", c.getString(7));
						p.put("path", c.getString(8));
						p.put("created_at", c.getString(9));
						p.put("updated_at", c.getString(10));
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
		
		
	}
	
	/**
	 * CREATE_ORDER
	 * @param order_dining_table_id
	 * @param order_observation
	 * @param order_status_id
	 * @param order_total
	 * @param user_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CREATE_ORDER (String order_dining_table_id, String order_observation, String order_status_id, String order_total, String user_id) throws JSONException {
		
		JSONArray arr = new JSONArray();		
		ContentValues valores = new ContentValues();
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();		
		JSONObject partial = new JSONObject();	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();	
		
		valores.put("date", dateFormat.format(date));
		valores.put("dining_table_id", order_dining_table_id);
		valores.put("observation", order_observation);
		valores.put("order_status_id", order_status_id);		
		valores.put("total", order_total);		
		valores.put("user_id", user_id);		
		valores.put("created_at", dateFormat.format(date));	
		valores.put("updated_at", dateFormat.format(date));
		
		try
		{			
			long rowsid = db.insert("orders", null, valores);
			if(rowsid != -1){
				partial.put("status", (int) rowsid);
			}else{
				partial.put("status", "-1");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("status", "-1");
		}
		
		arr.put(partial);
		return arr;
	
	}	
	
	/**
	 * INSERT_ORDER_DETAIL
	 * @param last_order_id
	 * @param menu_item_id
	 * @param quantity
	 * @param price
	 * @param subtotal
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_ORDER_DETAIL (String last_order_id, String menu_item_categories_id, String quantity, String price, String addon_id, String subtotal, int order_detail_print_status) throws JSONException {

		JSONArray arr = new JSONArray();
		ContentValues valores = new ContentValues();
		//db = this.myDbHelper.openDataBase();
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		valores.put("order_id", last_order_id);
		valores.put("menu_item_categories_id", menu_item_categories_id);
		valores.put("quantity", quantity);
		valores.put("price", price);
		valores.put("addon_id", addon_id);
		valores.put("subtotal", subtotal);
		valores.put("order_detail_status_id", order_detail_print_status);
		valores.put("created_at", dateFormat.format(date));
		valores.put("updated_at", dateFormat.format(date));		

		try
		{
			long rowsid = db.insert("order_detail", null, valores);
			if(rowsid != -1){
				partial.put("status", (int) rowsid);
			}else{
				partial.put("status", "-1");
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("status", "-1");
		}
		
		arr.put(partial);
		return arr;
	}

	/**
	 * UPDATE_ORDER_DETAIL
	 * @param order_detail_id
	 * @param order_id
	 * @param menu_item_categories_id
	 * @param addon_id
	 * @param quantity
	 * @param price
	 * @param subtotal
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_ORDER_DETAIL  (String order_detail_id, String order_id, String menu_item_categories_id, String addon_id, String quantity, String price, String subtotal) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();		 	
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE order_detail SET order_id = " + order_id + ", menu_item_categories_id = " + menu_item_categories_id + ", addon_id = " + addon_id + ", quantity = " + quantity + ", price = " + price + ", subtotal = " + subtotal + ", updated_at = '" + time +" WHERE _id = " + order_detail_id + ";"; 	
			db.execSQL(query);
			partial.put("status", "1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("status", "-1");
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * SET_COMMAND_BLINK_TABLE_BY_TABLE_ID
	 * @param dining_table_id
	 * @param value
	 * @return
	 */
	public boolean SET_COMMAND_BLINK_TABLE_BY_TABLE_ID(String dining_table_id, String value){
		
		JSONArray arr = new JSONArray();			 	
		Timestamp time = getCurrentTimeStamp();	
		
		try
		{		
			String query = "UPDATE dining_tables SET updated_at ='"+time+"', blink ='"+value+"' WHERE _id = " + dining_table_id + ";"; 	
			db.execSQL(query);
			
		}
		catch(Exception e)
		{			
			e.printStackTrace();
			return false;
		}		
		
		return true;
		
	}
	
	/**
	 * GET_NOT_PRINTED_ORDER_DETAILS
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_NOT_PRINTED_ORDER_DETAILS  (String order_id) throws JSONException {
		
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = 	"SELECT " +
							"od._id, " +
							"od.order_id, " +
							"od.menu_item_categories_id, " +
							"m.name as menu_item_name, " +
							"m.code, " +
							"od.addon_id, " +
							"ma.description as addon_description, " +
							"od.quantity, " +
							"od.price, " +
							"od.subtotal, " +
							"od.order_detail_status_id, " +
							"od.created_at, " +
							"od.updated_at " + 								
							"FROM order_detail as od " +
							"INNER JOIN order_status os ON  os._id = od.order_detail_status_id " +
							"INNER JOIN menu_items_categories as mic ON mic._id = od.menu_item_categories_id " +
							"INNER JOIN menu_item as m ON m._id = mic.menu_item_id " +
							"INNER JOIN categories_menus as cm ON cm._id = mic.categories_menus_id " +
							"INNER JOIN menu_item_thumbs as mt  ON mt.menu_item_id = m._id " +
							"LEFT JOIN menu_item_addons ma ON od.addon_id = ma._id " +
							"WHERE od.order_id = " + order_id + " AND od.order_detail_status_id = 3;";

			
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));
						p.put("order_id", c.getInt(1));		
						p.put("menu_item_categories_id", c.getInt(2));		
						p.put("menu_item_name", c.getString(3));
						p.put("code", c.getDouble(4));		 
						p.put("addon_id", c.getInt(5));		
						p.put("addon_description", c.getString(6));		
						p.put("quantity", c.getInt(7));		
						p.put("price", c.getDouble(8));		
						p.put("subtotal", c.getDouble(9));		
						p.put("order_detail_status_id", c.getInt(10));
						p.put("created_at", c.getString(11));		
						p.put("updated_at", c.getString(12));					
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
		
	}
	
	/**
	 * UPDATE_PRINTED_ORDER
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_PRINTED_ORDER  ( String order_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();		 	
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE orders SET order_status = 6 WHERE _id = " + order_id + ";"; 	
			db.execSQL(query);
			partial.put("status", "1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("status", "-1");
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * UPDATE_PRINTED_ORDER_DETAIL
	 * @param order_detail_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_PRINTED_ORDER_DETAIL  ( String order_detail_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();		 	
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{					               
			String query = "UPDATE order_detail SET order_detail_status_id = 6 WHERE _id = " + order_detail_id + ";";
			db.execSQL(query);
			partial.put("status", "1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("status", "-1");
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * GET_ORDER_DETAIL_STATUS
	 * @param order_detail_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDER_DETAIL_STATUS  ( String order_detail_id ) throws JSONException {			
		
		JSONArray arr = new JSONArray();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = "SELECT * FROM order_detail od INNER JOIN order_status os ON od.order_detail_status_id = os._id WHERE od._id = " + order_detail_id + ";";
						
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));
						p.put("order_id", c.getInt(1));
						p.put("menu_item_categories_id", c.getInt(2));				
						p.put("quantity", c.getInt(3));
						p.put("price", c.getDouble(4));
						p.put("code", c.getDouble(5));	
						p.put("subtotal", c.getDouble(6));
						p.put("order_detail_status_id", c.getInt(7));						
						p.put("created_at", c.getString(8));
						p.put("updated_at", c.getString(9));						
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
		
		/*
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "SELECT * FROM order_detail od INNER JOIN order_status os ON od.order_detail_status_id = os._id WHERE od._id = " + order_detail_id + ";";
			db.execSQL(query);		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		*/
	}
	
	
	/**
	 * GET_ORDER_BY_ID
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDER_BY_ID  ( String order_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = "SELECT o._id, o.date, o.dining_table_id, os.description, o.observation, o.order_status_id, o.user_id, o.total, o.created_at, o.updated_at FROM orders o INNER JOIN order_status os ON o.order_status_id = os._id AND o._id = " + order_id + ";";
						
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));
						p.put("date", c.getString(1));		
						p.put("dining_table_id", c.getInt(2));		
						p.put("description", c.getString(3));		
						p.put("observation", c.getString(4));		
						p.put("order_status_id", c.getInt(5));		
						p.put("user_id", c.getInt(6));		
						p.put("total", c.getDouble(7));												
						p.put("created_at", c.getString(8));		
						p.put("updated_at", c.getString(9));					
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
				
	}
	
	
	
	
	/**
	 * GET_CURRENT_TOTAL_ORDER
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CURRENT_TOTAL_ORDER  (String order_id, String subtotal) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE orders SET total = (total + " + subtotal + ") WHERE _id = " + order_id + ";";		
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	/**
	 * GET_CURRENT_ORDER_COUNT_BY_ITEM_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CURRENT_ORDER_COUNT_BY_ITEM_ID (String menu_item_id) throws JSONException {		
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			Cursor c = db.rawQuery("SELECT order_count FROM menu_item WHERE id = " + menu_item_id + ";", null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("order_count", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
		
		
	}
		
	/**
	 * GET_MENU_ITEM_THUMB_BY_MENU_ID
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMB_BY_MENU_ID  (String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			

			Cursor c = db.rawQuery("SELECT mi._id, mi.name, mi.description, mi.price, mit.img_medium, mi.created_at, mi.updated_at " +
							"FROM menu_item mi INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id WHERE mi._id = " + menu_id + ";", null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));
						p.put("name", c.getString(1));
						p.put("description", c.getString(2));
						p.put("price", c.getString(3));
						p.put("img_medium", c.getString(4));
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}
	
	/**
	 * UPDATE_ORDER
	 * @param order_dining_table_id
	 * @param order_observation
	 * @param order_status_id
	 * @param order_total
	 * @param user_id
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_ORDER (String order_dining_table_id , String order_observation, String order_status_id, String order_total, String user_id, String order_id) throws JSONException {
		
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();
		JSONObject partial = new JSONObject();	
		
		try
		{		
			String query = "UPDATE orders SET fecha = '"+time+"', " +
					"dining_table_id  = "+order_dining_table_id +", " +
					"observation ='"+order_observation+"', " +
					"order_status_id ="+order_status_id+", " +
					"total ='"+order_total+"', " +
					"user_id ="+ user_id + "," + 				
					"updated_at '"+time+"' WHERE _id = " + order_id + ";);";	
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	
	/**
	 * DELETE_REQUEST_ORDER
	 * @param request_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_REQUEST_ORDER (String request_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			String query = "DELETE * FROM requests WHERE _id =" + request_id + ";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
			
	/**
	 * CONSULTAS INTERNAS
	 * GET_CATEGORY_FOLDER_NAME
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORY_FOLDER_NAME (String category_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT folder_name from categories where _id ="+category_id+";",null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();						
						p.put("folder_name", c.getString(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
	}
	
	/**
	 * GET_LAST_ORDER_ID
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_LAST_ORDER_ID ( ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT _id FROM requests ORDER BY _id DESC LIMIT 1;",null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();						
						p.put("_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}			

			} finally {
			    c.close();
			}
			
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_LAST_MENU_ITEM_ID
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_LAST_MENU_ITEM_ID ( ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT _id FROM menu_item ORDER BY _id DESC LIMIT 1;",null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();						
						p.put("_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}		

			} finally {
			    c.close();
			}			
			
		}
		
		return arr;
	}	
		
	/**
	 * CONSULTAS INTERNAS
	 * GET_MENU_FOLDER_NAME
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_FOLDER_NAME (String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT folder_name FROM menus WHERE _id =" + menu_id + ";",null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();						
						p.put("folder_name", c.getString(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}			
		
		}
		
		return arr;
	}	
	
	
	/**
	 * CONSULTAS INTERNAS
	 * GET_LAST_MENU_ITEM
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_LAST_MENU_ITEM () throws JSONException {
		
		JSONArray arr = new JSONArray();
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT _id FROM menu_item ORDER BY _id DESC LIMIT 1;",null);
			
			try {


				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();						
						p.put("_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} 
			
			catch (JSONException e) {		
				arr = APM.JSONReturn("status",e);		
				e.printStackTrace();
				return arr;
			}
			
			finally {
			    c.close();
			}		
			
		}				
		return arr;
	}	
	
	/**
	 * DELETE_ORDER
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_ORDER (String order_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM orders WHERE _id =" + order_id + ";";			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}

		arr.put(partial);
		return arr;
	}
	
	/**
	 * GET_ORDERS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDERS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = "SELECT o._id, o.date, o.dining_table_id, os.description, o.observation, o.order_status_id, o.user_id, o.total, o.created_at, o.updated_at FROM orders o INNER JOIN order_status os ON o.order_status_id = os._id;";
			Cursor c = db.rawQuery(query, null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();	
						
						p.put("_id", c.getInt(0));
						p.put("date", c.getString(1));
						p.put("dining_table_id", c.getInt(2));
						p.put("description", c.getString(3));
						p.put("observation", c.getString(4));	
						p.put("order_status_id", c.getInt(5));
						p.put("user_id", c.getString(6));
						p.put("total", c.getDouble(7));
						p.put("created_at", c.getString(8));
						p.put("updated_at", c.getString(9));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}	
			
		}
		
		return arr;
	}	

	/**
	 * GET_ORDER_DETAIL
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDER_DETAIL (String order_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{			
			String query =				
					"SELECT " +
					  "od._id, " +
					  "od.order_id, " + 
					  "od.menu_item_categories_id, " +
					  "mic.menu_item_id, " +					  
					  "m.name as menu_item_name, " +
					  "m.code, " +
					  "cm.category_id, " +
					  "od.addon_id, " +
					  "ma.description as addon_description, " + 
					  "od.quantity, " +
					  "od.price, " +					  
					  "od.subtotal, " +
					  "od.order_detail_status_id, " +
					  "os.description, " +
					  "od.created_at, " +
					  "od.updated_at, " +
					  "mt.img_small as menu_item_img_small," +
					  "cm._id as categories_menu_id " +  
					"FROM order_detail as od  " +
					"INNER JOIN order_status os ON os._id = od.order_detail_status_id " +  
					"INNER JOIN menu_items_categories as mic ON mic._id = od.menu_item_categories_id " +
					"INNER JOIN menu_item as m ON m._id = mic.menu_item_id " +
					"INNER JOIN categories_menus as cm ON cm._id = mic.categories_menus_id " +
					"INNER JOIN menu_item_thumbs as mt  ON mt.menu_item_id = m._id " +
					"LEFT JOIN menu_item_addons ma ON od.addon_id = ma._id " +
					"WHERE od.order_id = " + order_id+ ";";
			
			Cursor c = db.rawQuery(query,null);	
			
			try {

				if (c!=null){   
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();	
						
						p.put("_id", c.getInt(0));
						p.put("order_id", c.getInt(1));
						p.put("menu_item_categories_id", c.getInt(2));
						p.put("menu_item_id", c.getInt(3));
						p.put("menu_item_name", c.getString(4));
						p.put("code", c.getDouble(5));
						p.put("category_id", c.getInt(6));
						p.put("addon_id", c.getInt(7));
						p.put("addon_description", c.getString(8));
						p.put("quantity", c.getInt(9));
						p.put("price", c.getDouble(10));					
						p.put("subtotal", c.getDouble(11));
						p.put("order_detail_status_id", c.getInt(12));
						p.put("order_detail_status_description", c.getString(13));
						p.put("created_at", c.getString(14));
						p.put("updated_at", c.getString(15));
						p.put("menu_item_img_small", c.getString(16));
						p.put("categories_menu_id", c.getString(17));
						//p.put("menu_item_img_small", "none");
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}
			
			
		}
		return arr;
	}	
	
	/**
	 * GET_ORDER_STATUS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDER_STATUS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM order_status;",null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();	
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("created_at", c.getString(2));
						p.put("updated_at", c.getString(3));				
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}	
			
			
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_DINING_TABLE_STATUS
	 * @return
	 * @throws JSONException
	 */	
	public JSONArray GET_DINING_TABLE_STATUS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();	
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
		
			Cursor c = db.rawQuery("SELECT * FROM dining_table_status;",null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();	
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));
						p.put("description_status", c.getString(2));	
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));		
												
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}
			
			
		}
		
		return arr;
	}	
	
	/**
	 * GET_DINING_TABLES
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_DINING_TABLES () throws JSONException {

		JSONArray arr = new JSONArray();
		//db = this.myDbHelper.openDataBase();
		JSONObject partial = new JSONObject();

		if(db != null)
		{
			String query = "SELECT dt._id, dt.description, dts.description as dining_table_status_description, dt.created_at, dt.updated_at, dt.dining_table_status_id FROM dining_tables dt INNER JOIN dining_table_status dts ON dt.dining_table_status_id = dts._id;";
			
			Cursor c = db.rawQuery(query,null);
	
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);
					int num = 0;
					while (!c.isAfterLast() && num < count){
			
						JSONObject p = new JSONObject();
				
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));
						p.put("dining_table_status_description", c.getString(2));
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));
						p.put("dining_table_status_id", c.getString(5));
				
						arr.put(p);
						c.moveToNext();
					}
					
				}

			} finally {
			    c.close();
			}
			
		}
		
		return arr;
	}

	
	
	/**
	 * GET_ORDER_BY_TABLE_STATUS
	 * @param dining_table_status_id_1
	 * @param dining_table_status_id_2
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDER_BY_TABLE_STATUS (String user_id, String order_status_id_1,String order_status_id_2, String dining_table_status_id_1, String dining_table_status_id_2) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();	
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{		
			
			/*String query = "SELECT o.*, d.dining_table_status_id, ds.description as dining_table_status_description, d.description as dining_table_description FROM orders as o INNER JOIN dining_tables as d ON d._id = o.dining_table_id " + 
					"INNER JOIN dining_table_status as ds ON ds._id = d.dining_table_status_id WHERE o.user_id = "+ user_id+" AND o.order_status_id = " + order_status_id + " AND (d.dining_table_status_id = " + dining_table_status_id_1 + " or d.dining_table_status_id = " + dining_table_status_id_2 + ");";*/	
			
			String query = "SELECT o.*, d.dining_table_status_id, ds.description as dining_table_status_description, d.description as dining_table_description FROM orders as o INNER JOIN dining_tables as d ON d._id = o.dining_table_id " + 
					"INNER JOIN dining_table_status as ds ON ds._id = d.dining_table_status_id WHERE o.user_id = "+ user_id+" AND (o.order_status_id = " + order_status_id_1 + " OR o.order_status_id = " + order_status_id_2 + ") AND (d.dining_table_status_id = " + dining_table_status_id_1 + " or d.dining_table_status_id = " + dining_table_status_id_2 + ");";	
			
			Cursor c = db.rawQuery(query, null);		
						
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
				 					
						p.put("_id", c.getInt(0));
						p.put("date", c.getString(1));	
						p.put("dining_table_id", c.getString(2));
						p.put("observation", c.getString(3));				
						p.put("order_status_id", c.getInt(4));					
						p.put("total", c.getDouble(5));
						p.put("user_id", c.getDouble(6));
						p.put("created_at", c.getString(7));
						p.put("updated_at", c.getString(8));
						p.put("dining_table_status_id", c.getString(9));
						p.put("dining_table_status_description", c.getString(10));						
						p.put("dining_table_description", c.getString(11));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}		

			} finally {
			    c.close();
			}
			
			
		}
		
		return arr;
	}	
	
	
	/**
	 *  UPDATE_ORDER_BY_STATUS
	 * @param order_status_id
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_ORDER_BY_STATUS (String order_status_id, String order_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		if(db != null){
			try
			{			
				Timestamp time = getCurrentTimeStamp();			
				
				String query = "UPDATE orders set order_status_id = "+order_status_id+" WHERE _id = "+order_id+";";				
									
				db.execSQL(query);		
				partial.put("status", "ok");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				partial.put("status", "error");
			}
			
			
		}
		arr.put(partial);
		return arr;
	}
	
	/**
	  * DELETE_ORDER_DETAIL
	  * @param order_detail_id
	  * @return
	  * @throws JSONException
	  */
	 public JSONArray DELETE_ORDER_DETAIL (String order_detail_id) throws JSONException {
	  
	  JSONArray arr = new JSONArray();   
	  //db = this.myDbHelper.openDataBase();   
	  
	  JSONObject partial = new JSONObject(); 
	  if(db != null){
		  try
		  {  
			  	int cant = db.delete("order_detail", "_id="+order_detail_id+"",null);
				if(cant == 1){
					partial.put("status", "ok");
				}else{
					partial.put("status", "error");
				}
		  }
		  catch(Exception e)
		  {
		   e.printStackTrace();
		   partial.put("status", "error");
		  }
		  
	  }

	  arr.put(partial);
	  return arr;
	 }
	
	
	/**
	 * UPDATE_DINING_TABLE_STATUS
	 * @param dining_table_id
	 * @param dining_table_status_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_DINING_TABLE_STATUS(String dining_table_id, String dining_table_status_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		if(db != null){
			try
			{			
				Timestamp time = getCurrentTimeStamp();			
				
				String query = "UPDATE dining_tables SET dining_table_status_id = "+dining_table_status_id+", updated_at ='"+time+"' WHERE _id = "+dining_table_id+";";				
									
				db.execSQL(query);		
				partial.put("status", "ok");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				partial.put("status", "error");
			}
			
		}
		arr.put(partial);
		return arr;
	}

		
	/**
	 * Timestamp
	 * @return
	 */
	public static Timestamp getCurrentTimeStamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());
		return currentTimestamp;
	}
	
	
	/**
	 * CREATE_USER
	 * @param username
	 * @param password
	 * @param first_name
	 * @param last_name
	 * @param email
	 * @param user_status
	 * @param profile_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CREATE_USER (String username, String password, String first_name, String  last_name, String  email, String  status, String profile_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();		
			
			String query = 
			
					
			"INSERT INTO users(username, password, first_name, last_name, email, status, profile_id, created_at, updated_at) " +
			"VALUES('"+username+"','"+password+"','"+first_name+"','"+last_name+"','"+email+"',"+status+","+profile_id+",'"+time+"','"+time+"');";		
			
			db.execSQL(query);	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	
	}	
	
	/**
	 * UPDATE_USER
	 * @param username
	 * @param password
	 * @param first_name
	 * @param last_name
	 * @param email
	 * @param user_status
	 * @param profile_id
	 * @param user_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_USER(String username, String password, String first_name, String last_name, String email, String status, String profile_id, String user_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			//String md5Pass = Encripter.md5(password);
			
			String query = "UPDATE users SET username = '"+username+"', password = '"+password+"', first_name = '"+first_name+"', last_name = '"+last_name+"', email = '"+email+"', status = "+status+", profile_id = "+profile_id+", updated_at = '"+time+"' WHERE _id = "+user_id+";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * UPDATE_STATUS_USER
	 * @param user_id
	 * @param status
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_STATUS_USER(String user_id, String status) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();			
			
			String query = "UPDATE users SET status = "+status+", updated_at ='"+time+"' WHERE _id = "+user_id+";";				
								
			db.execSQL(query);		
			partial.put("status", "ok");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("status", "error");
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * DELETE_USER
	 * @param user_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_USER (String user_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM users WHERE _id = " + user_id + ";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}


	public JSONArray GET_USER_BY_ID  (String user_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			if(db != null)
			{
				String query =		
				"SELECT u._id, u.username, u.password, u.first_name, u.last_name, u.email, u.status, u.profile_id, p.description, u.created_at, u.updated_at " +
				"FROM users u INNER JOIN profiles p ON u.profile_id = p._id WHERE u._id = "+user_id+";";
							
				Cursor c = db.rawQuery(query,null);		
				
				try {

					if (c!=null){
						
						int count = c.getCount();
						c.moveToPosition(0);    	
				    	int num = 0;	    	    	
						while (!c.isAfterLast() && num < count){ 	
							
							JSONObject p = new JSONObject();
							
							p.put("_id", c.getInt(0));
							p.put("username", c.getString(1));
							p.put("password", c.getString(2));	
							p.put("first_name", c.getString(3));	
							p.put("last_name", c.getString(4));	
							p.put("email", c.getString(5));	
							p.put("status", c.getString(6));	
							p.put("profile_id", c.getInt(7));	
							p.put("description", c.getString(8));	
							p.put("created_at", c.getString(9));	
							p.put("created_at", c.getString(10));	
							
							arr.put(p);
							c.moveToNext();			 								
						}
					}		

				} finally {
				    c.close();
				}				
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		
		
		
		
		return arr;
	}
	
	/**
	 * GET_USER
	 * @param user_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_USER (String user_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			if(db != null)
			{
				String query = "DELETE FROM users WHERE _id = " + user_id + ";";						
				Cursor c = db.rawQuery(query,null);		
				
				try {

					if (c!=null){
						
						int count = c.getCount();
						c.moveToPosition(0);    	
				    	int num = 0;	    	    	
						while (!c.isAfterLast() && num < count){ 	
							
							JSONObject p = new JSONObject();
							
							p.put("_id", c.getInt(0));
							p.put("username", c.getString(1));
							p.put("password", c.getString(2));	
							p.put("first_name", c.getString(3));	
							p.put("last_name", c.getString(4));	
							p.put("email", c.getString(5));	
							p.put("status", c.getString(6));	
							p.put("profile_id", c.getInt(7));									
							p.put("created_at", c.getString(8));	
							p.put("created_at", c.getString(9));	
							
							arr.put(p);
							c.moveToNext();			 								
						}
					}		

				} finally {
				    c.close();
				}
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		
		
		
		
		return arr;
	}	
	
	/**
	 * GET_USERS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_USERS ( ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT u._id, u.username, u.first_name, u.last_name, u.email,u.status, u.profile_id, p.description, u.created_at, u.updated_at " +
					"FROM users u INNER JOIN profiles p ON u.profile_id = p._id;";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("username", c.getString(1));						
						p.put("first_name", c.getString(2));	
						p.put("last_name", c.getString(3));						
						p.put("email", c.getString(4));
						p.put("status", c.getString(5));
						p.put("profile_id", c.getInt(6));	
						p.put("description", c.getString(7));	
						p.put("created_at", c.getString(8));	
						p.put("updated_at", c.getString(9));			
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}			
				
		}
		
		return arr;
	}	
	
	/**
	 * GET_USERS_BY_STATUS
	 * @param String status
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_USERS_BY_STATUS ( String status) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT u._id, u.username, u.first_name, u.last_name, u.email,u.status, u.profile_id, p.description, u.created_at, u.updated_at " +
					"FROM users u INNER JOIN profiles p ON u.profile_id = p._id WHERE u.status = "+status+" AND u.profile_id=2;";
			
			Cursor c = db.rawQuery(query ,null);
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("username", c.getString(1));						
						p.put("first_name", c.getString(2));	
						p.put("last_name", c.getString(3));						
						p.put("email", c.getString(4));
						p.put("status", c.getString(5));
						p.put("profile_id", c.getInt(6));	
						p.put("description", c.getString(7));	
						p.put("created_at", c.getString(8));	
						p.put("updated_at", c.getString(9));			
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}
			
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_USER_PROFILES
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_USER_PROFILES ( ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT * FROM profiles;";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));	
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}
				
			} finally {
			    c.close();
			}			
		}
		
		return arr;
	}	
	
	/**
	 * 	CHECK_USER
	 * @param username
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CHECK_USER (String username, String password) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			//String md5Pass = Encripter.md5(password);
			
			String query =		
			"SELECT u._id, u.username, u.password, u.first_name, u.last_name, u.email, u.status, u.profile_id, p.description, u.created_at, u.updated_at " +
			"FROM users u INNER JOIN profiles p ON u.profile_id = p._id WHERE u.username = '" + username + "' AND u.password = '" + password + "';";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("username", c.getString(1));
						p.put("password", c.getString(2));	
						p.put("first_name", c.getString(3));	
						p.put("last_name", c.getString(4));	
						p.put("email", c.getString(5));	
						p.put("status", c.getInt(6));
						p.put("profile_id", c.getInt(7));	
						p.put("description", c.getString(8));	
						p.put("created_at", c.getString(9));
						p.put("updated_at", c.getString(10));					
						Log.d("outPut", "user username: " + c.getString(1));
						arr.put(p);
						c.moveToNext();			 								
					}
				}
	

			} finally {
			    c.close();
			}			
		
		}
		
		return arr;
	}	
	
	/**
	 * CREATE_DINING_TABLE
	 * @param description
	 * @param dining_table_status_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CREATE_DINING_TABLE (String description, String dining_table_status_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();		
			String query = "INSERT INTO dining_tables(description, blink, dining_table_status_id, created_at, updated_at) " +
					"VALUES('"+description+"','0',"+dining_table_status_id+",'"+time+"','"+time+"');";				
								
			db.execSQL(query);	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	
	}	
	
	/**
	 * UPDATE_DINING_TABLE
	 * @param description
	 * @param dining_table_status_id
	 * @param dining_table_id
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray UPDATE_DINING_TABLE(String description, String dining_table_status_id, String dining_table_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();		
			String query = "UPDATE dining_tables SET description = '"+description+"', dining_table_status_id = "+dining_table_status_id+", updated_at = '"+time+"' WHERE _id = "+dining_table_id+";";
					
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * DELETE_DINING_TABLE
	 * @param dining_table_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_DINING_TABLE (String dining_table_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM dining_tables WHERE _id = " + dining_table_id + ";";
			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * GET_DINING_TABLE_BY_ID
	 * @param dining_table_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_DINING_TABLE_BY_ID ( String dining_table_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT * FROM dining_tables WHERE _id = " + dining_table_id + ";";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("dining_table_status_id", c.getInt(2));						
						p.put("created_at", c.getString(3));	
						p.put("updated_at", c.getString(4));			
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}						
		}
		
		return arr;
	}	
	
	/**
	 * GET_DINING_TABLE_BY_STATUS
	 * @param dining_table_status_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_DINING_TABLE_BY_STATUS ( String dining_table_status_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT * FROM dining_tables WHERE dining_table_status_id = " + dining_table_status_id + ";";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("dining_table_status_id", c.getInt(2));						
						p.put("created_at", c.getString(3));	
						p.put("updated_at", c.getString(4));			
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}			
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_DINING_TABLE_BY_USER
	 * @param user_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_DINING_TABLE_BY_USER  ( String user_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = 
					"SELECT " +
					"d._id AS _id, " +
					"d.description AS description, " +
					"d.blink AS blink, " +
					"ds.description AS status, " +
					"u._id AS user_id " +
					"FROM orders as o " +
					"INNER JOIN users as u ON (o.user_id = u._id) " +
					"INNER JOIN dining_tables as d ON (o.dining_table_id = d._id) " +
					"INNER JOIN dining_table_status as ds ON (d.dining_table_status_id = ds._id) " +
					"WHERE ds._id != '9' AND user_id = " + user_id + ";";
						
			Cursor c = db.rawQuery(query, null);				
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
										
						p.put("_id", c.getInt(0));				
						p.put("description", c.getString(1));	
						p.put("blink", c.getString(2));
						p.put("status", c.getString(3));											
						
						arr.put(p);
						c.moveToNext();
				 								
					}	
				}

			} finally {
			    c.close();
			}
		
		}
		
		return arr;
				
	}
	
	/**
	 * GET_COMANDAS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_COMANDAS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT COUNT(*) as cant from dining_tables as dt INNER JOIN orders as o ON o.dining_table_id = dt._id INNER JOIN order_detail as od ON od.order_id = o._id WHERE dt.dining_table_status_id = 1;";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("cantidad", c.getInt(0));			
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
		
			} finally {
			    c.close();
			}
						
		}
		
		return arr;
	}	
	
	
	/**
	 * GET_ORDERS_AND_DETAIL
	 * @param user_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDERS_AND_DETAIL (String user_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = "SELECT " +
			  "o._id as order_id, " +
			  "o.total as order_total, " + 
			  "o.dining_table_id, " +
			  "u.last_name as user_last_name, " + 
			  "u.first_name as user_first_name, " +
			  "od._id as order_detail_id, " +
			  "od.quantity, " +
			  "od.price, " +
			  "od.subtotal, " +
			  "od.addon_id, " +
			  "mia.description as addon_description, " +
			  "od.menu_item_categories_id, " +
			  "mi._id as menu_item_id, " +
			  "mi.name as menu_item_name " +			
			  "FROM orders as o " +
			  "INNER JOIN order_detail as od ON od.order_id = o._id " +
			  "INNER JOIN users as u ON u._id = o.user_id " +
			  "INNER JOIN dining_tables as dt ON dt._id = o.dining_table_id " +
			  "INNER JOIN menu_items_categories as mic ON mic._id = od.menu_item_categories_id " +   
			  "INNER JOIN menu_item as mi ON mi._id = mic.menu_item_id " +
			  "LEFT JOIN menu_item_addons as mia ON mia._id = od.addon_id " +
			  "WHERE u._id = "+user_id+" AND dt.dining_table_status_id = 1;";		
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("order_id", c.getInt(0));
						p.put("order_total", c.getString(1));
						p.put("dining_table_id", c.getInt(2));
						p.put("user_last_name", c.getString(3));
						p.put("user_first_name", c.getString(4));
						p.put("order_detail_id", c.getString(5));
						p.put("quantity", c.getString(6));
						p.put("price", c.getString(7));
						p.put("subtotal", c.getString(8));
						p.put("addon_id", c.getString(9));
						if(c.getString(10) != null){
							p.put("addon_description", c.getString(10));
						}else{
							p.put("addon_description", "none" );
						}
							
						p.put("menu_item_categories_id", c.getInt(11));					
						p.put("menu_item_id", c.getString(12));
						p.put("menu_item_name", c.getString(13));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}

			} finally {
			    c.close();
			}		
							
		}
		
		return arr;
	}	
	
	/**
	 * GET_ORDERS_AND_DETAIL_BY_DINING_TABLE_ID
	 * @param dining_table_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ORDERS_AND_DETAIL_BY_DINING_TABLE_ID (String dining_table_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = "SELECT " +
			  "o._id as order_id, " +
			  "o.total as order_total, " + 
			  "o.dining_table_id, " +
			  "u.last_name as user_last_name, " + 
			  "u.first_name as user_first_name, " +
			  "od._id as order_detail_id, " +
			  "od.quantity, " +
			  "od.price, " +
			  "od.subtotal, " +
			  "od.addon_id, " +
			  "mia.description as addon_description, " +
			  "od.menu_item_categories_id, " +
			  "mi._id as menu_item_id, " +
			  "mi.name as menu_item_name " +			
			  "FROM orders as o " +
			  "INNER JOIN order_detail as od ON od.order_id = o._id " +
			  "INNER JOIN users as u ON u._id = o.user_id " +
			  "INNER JOIN dining_tables as dt ON dt._id = o.dining_table_id " +
			  "INNER JOIN menu_items_categories as mic ON mic._id = od.menu_item_categories_id " +   
			  "INNER JOIN menu_item as mi ON mi._id = mic.menu_item_id " +
			  "LEFT JOIN menu_item_addons as mia ON mia._id = od.addon_id " +
			  "WHERE o.dining_table_id = "+dining_table_id+";";		
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("order_id", c.getInt(0));
						p.put("order_total", c.getString(1));
						p.put("dining_table_id", c.getInt(2));
						p.put("user_last_name", c.getString(3));
						p.put("user_first_name", c.getString(4));
						p.put("order_detail_id", c.getString(5));
						p.put("quantity", c.getString(6));
						p.put("price", c.getString(7));
						p.put("subtotal", c.getString(8));
						p.put("addon_id", c.getString(9));
						if(c.getString(10) != null){
							p.put("addon_description", c.getString(10));
						}else{
							p.put("addon_description", "none" );
						}
							
						p.put("menu_item_categories_id", c.getInt(11));					
						p.put("menu_item_id", c.getString(12));
						p.put("menu_item_name", c.getString(13));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}

			} finally {
			    c.close();
			}		
							
		}
		
		return arr;
	}
	
	/**
	 * CREATE_MENU_ITEM_ADDON
	 * @param description
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CREATE_MENU_ITEM_ADDON (String description, String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();			
			String query = "INSERT INTO menu_item_addons(description, menu_item_id, created_at, updated_at) " +
					"VALUES('"+description+"',"+menu_item_id+",'"+time+"','"+time+"');";				
								
			db.execSQL(query);	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	
	}	
	
	
	/**
	 * UPDATE_MENU_ITEM_ADDON
	 * @param description
	 * @param menu_item_id
	 * @param addon_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_ADDON (String description, String menu_item_id, String addon_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();		
			String query = "UPDATE menu_item_addons SET description = '"+description+"', menu_item_id = "+menu_item_id+", updated_at = '"+time+"' WHERE _id = "+addon_id+";";
					
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	
	/**
	 * DELETE_MENU_ITEM_ADDON
	 * @param addon_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_MENU_ITEM_ADDON (String addon_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM menu_item_addons WHERE _id = " + addon_id + ";";		
			db.execSQL(query);						
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	
	/**
	 * DELETE_MENU_ITEM_CATEGORIES
	 * @param menu_item_categorie_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_MENU_ITEM_CATEGORIES_BY_ID ( String menu_item_categorie_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE from menu_items_categories where categories_menus_id = " + menu_item_categorie_id + ";";		
			db.execSQL(query);						
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * UPDATE_MENU_ITEM_CATEGORIES_BY_MENU_ITEM_CATEGORIES_ID
	 * @param menu_item_id
	 * @param categories_menus_id
	 * @param _id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_MENU_ITEM_CATEGORIES_BY_MENU_ITEM_CATEGORIES_ID (String menu_item_id, String categories_menus_id, String _id  ) throws JSONException {
		
		JSONArray arr = new JSONArray();		
		JSONObject partial = new JSONObject();	
		
		Timestamp time = getCurrentTimeStamp();			
		
		try
		{
			 String query = "UPDATE menu_items_categories SET menu_item_id = '"+menu_item_id+"', categories_menus_id = "+categories_menus_id+", updated_at = '"+time+"' WHERE _id = "+_id+";";
			
			db.execSQL(query);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * GET_MENU_ITEM_ADDONS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_ADDONS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT mia._id, mia.description, mia.menu_item_id, mi.name, mia.created_at, mia.updated_at " +
					"FROM menu_item_addons mia INNER JOIN menu_item mi ON mia.menu_item_id = mi._id";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("menu_item_id", c.getInt(2));
						p.put("menu_item_name", c.getString(3));
						p.put("created_at", c.getString(4));	
						p.put("updated_at", c.getString(5));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
	
			} finally {
			    c.close();
			}			
				
		}
		
		return arr;
	}
	
	/**
	 * GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID
	 * @param menu_item_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID (String menu_item_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		if(db == null){
			//db = this.myDbHelper.openDataBase();			
		}
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT * FROM menu_item_addons WHERE menu_item_id = " + menu_item_id + ";";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
		
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();					
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("menu_item_id", c.getInt(2));						
						p.put("created_at", c.getString(3));	
						p.put("updated_at", c.getString(4));

						arr.put(p);
						c.moveToNext();			 								
					}
				}	
	
			} finally {
			    c.close();
			}
			
		}
		//
		return arr;
	}
		

	/**
	 * GET_MENU_ITEM_ADDON_BY_ID
	 * @param addon_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_ADDON_BY_ID (String addon_id) throws JSONException {
	
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT mia._id, mia.description, mia.menu_item_id, mia.created_at, mia.updated_at, mi.name " +
					"FROM menu_item_addons mia INNER JOIN menu_item mi ON " +
					"mia.menu_item_id = mi._id WHERE mia._id = "+ addon_id +";";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("menu_item_id", c.getInt(2));						
						p.put("created_at", c.getString(3));	
						p.put("updated_at", c.getString(4));
						p.put("menu_item_name", c.getString(5));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
			} finally {
			    c.close();
			}
				
		}
		
		return arr;
	}
	
	
	/**
	 * GET_TOTAL_ORDER
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_TOTAL_ORDER (String order_id) throws JSONException {
	
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT total FROM orders WHERE _id = " + order_id + ";";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("total", c.getDouble(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
		
			} finally {
			    c.close();
			}
					
		}
		
		return arr;
	}
	
	
	/**
	 * UPDATE_TOTAL_ORDER
	 * @param order_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_TOTAL_ORDER (String order_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();		
			String query = "UPDATE orders SET total = (total_anterior + subtotal_detalle) WHERE _id = " + order_id + ";";			
					
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	}
	
	/**
	 * INSERT_MENUS_ITEMS_CATEGORIES
	 * @param menu_item_id
	 * @param categories_menus_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENUS_ITEMS_CATEGORIES (String menu_item_id, String categories_menus_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
			
		Timestamp time = getCurrentTimeStamp();		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			String query = "INSERT INTO menu_items_categories(" +
					"menu_item_id, " +
					"categories_menus_id, " +														
					"created_at, " +
					"updated_at" +					
					") VALUES(" + menu_item_id + "," + categories_menus_id + ",'"+time+"','"+time+"');";			
			db.execSQL(query);	
			
		}
		
		catch(Exception e)
		{
			arr = APM.JSONReturn("status",e);		
			e.printStackTrace();
			return arr;	
		}
		
		arr = APM.JSONReturn("status" ,1);	
		return arr;		
		
	}	
	
	
	

	/**
	 * GET_MENU_ITEM_THUMBS_BY_SMALL_IMAGE_NAME
	 * @param small_image_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMBS_BY_SMALL_IMAGE_NAME (String small_image_name) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT menu_item_id FROM menu_item_thumbs WHERE img_small LIKE '%" + small_image_name + "';";
			 
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("menu_item_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
					
			} finally {
			    c.close();
			}
				
		}
		
		return arr;
		
	}	
	
	/**
	 * GET_MENU_ITEM_THUMBS_BY_MEDIUM_IMAGE_NAME
	 * @param medium_image_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMBS_BY_MEDIUM_IMAGE_NAME  (String medium_image_name) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT menu_item_id FROM menu_item_thumbs WHERE img_medium LIKE '%"+ medium_image_name + "';";
			 
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("menu_item_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}
			} finally {
			    c.close();
			}
						
		}
		
		return arr;		
	}	
	
	
	/**
	 * GET_EXIST_CATEGORIES_MENUS_ID_FOR_REMOVE_CATEGORY
	 * @param menu_item_id
	 * @param categories_menus_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_EXIST_CATEGORIES_MENUS_ID_FOR_REMOVE_CATEGORY   (String menu_item_id, String categories_menus_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();					
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT categories_menus_id FROM menu_items_categories WHERE menu_item_id = " + menu_item_id + " AND categories_menus_id <> " + categories_menus_id + ";";
			 
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("categories_menus_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}
			} finally {
			    c.close();
			}
						
		}
		
		return arr;		
	}	
	

	/**
	 * GET_CATEGORY_AND_MENU_BY_CATEGORIES_MENUS_ID
	 * @param categories_menus_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORY_AND_MENU_BY_CATEGORIES_MENUS_ID  (String categories_menus_id  ) throws JSONException {
		
		JSONArray arr = new JSONArray();					
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT category_id, menu_id FROM categories_menus WHERE _id = " + categories_menus_id + ";";
			 
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("category_id", c.getInt(0));		
						p.put("menu_id", c.getInt(1));		
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}
			} finally {
			    c.close();
			}
						
		}
		
		return arr;		
	}	
	
	
	/**
	 * GET_CATEGORY_BY_ID
	 * @param _id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORY_BY_ID  ( String _id  ) throws JSONException {
		
		JSONArray arr = new JSONArray();					
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT * FROM categories_menus WHERE _id = " + _id + ";";
			 
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("category_id", c.getInt(0));		
						p.put("menu_id", c.getInt(1));		
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}
			} finally {
			    c.close();
			}
						
		}
		
		return arr;		
	}
	
		
	/**
	 * GET_MENU_ITEM_THUMBS_BY_LARGE_IMAGE_NAME
	 * @param large_image_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMBS_BY_LARGE_IMAGE_NAME   (String large_image_name) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT menu_item_id FROM menu_item_thumbs WHERE img_large LIKE '%"+ large_image_name + "';";
			 
			Cursor c = db.rawQuery(query ,null);		
			try {
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("menu_item_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
			} finally {
			    c.close();
			}
		}
		
		return arr;
		
	}	
	
	
	/**
	 * GET_MENU_ITEM_THUMB_BY_MENU_ITEM_THUMBS_ID
	 * @param _id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEM_THUMB_BY_MENU_ITEM_THUMBS_ID (String _id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			String query = "SELECT * FROM menu_item_thumbs WHERE _id=" + _id + ";";
			 
			Cursor c = db.rawQuery(query ,null);		
			try {
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));			
						p.put("menu_item_id", c.getInt(1));
						p.put("img_small", c.getString(2));		
						p.put("img_medium", c.getString(3));
						p.put("img_large", c.getString(4));
						p.put("created_at", c.getString(5));
						p.put("updated_at", c.getString(6));
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
			} finally {
			    c.close();
			}
		}
		
		return arr;
		
	}
	
	
	
	/**
	 * GET_CATEGORIES_MENUS_ID
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_CATEGORIES_MENUS_ID (String category_id, String menu_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT _id FROM categories_menus cm WHERE cm.category_id = " + category_id + " AND cm.menu_id = " + menu_id + ";";
			 
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
		
			} finally {
			    c.close();
			}
					
		}
		
		return arr;
		
	}	
	
	
	/**
	 * GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_FOLDER_NAME_BY_CATEGORY_ID_AND_MENU_ID (String category_id, String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			
			 String query = "SELECT m.folder_name FROM menus m INNER JOIN categories_menus cm ON m._id = cm.menu_id " +
						   "WHERE cm.category_id = " + category_id + " AND cm.menu_id = " + menu_id + ";";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();		
						
						p.put("folder_name", c.getString(0));						
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	
		
			} finally {
			    c.close();
			}
						
		}
		
		return arr;
	}
	

	/**
	 * category_id
	 * @param menu_item_id
	 * @param category_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray INSERT_MENU_ITEM_CATEGORIES (String menu_item_id, String categories_menus_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();	
			
			String query = "INSERT INTO menu_items_categories(menu_item_id, categories_menus_id, created_at, updated_at) " + 
			"VALUES(" + menu_item_id + ", " + categories_menus_id + ", '" + time + "','" + time + "');";								
			db.execSQL(query);	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
	
	}
	
	
	/**
	 * GET_ALL_MENU_ITEMS_BY_MENU_ID
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ALL_MENU_ITEMS_BY_MENU_ID  (String menu_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		if(db != null)
		{		
			String query_a = 
					"SELECT " +
					"cm._id,"  +
					"c._id as category_id, " +
					"c.description as category_description, " +
					"cm.menu_id " +
					"FROM categories_menus cm " +
					"INNER JOIN categories c ON c._id = cm.category_id " +
					"WHERE cm.menu_id  =  " + menu_id +";";		
								
			Cursor c = db.rawQuery(query_a , null);					
			
			try {

				if (c!=null){
					//	
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getString(0));
						p.put("category_id", c.getString(1));
						p.put("category_description", c.getString(2));			
						p.put("menu_id", c.getString(3));						
						
						JSONArray jsonMenuItems = new JSONArray();
						jsonMenuItems = GET_MENU_ITEMS_BY_CATEGORIES_MENU_ID(c.getString(0));
						
						p.put("menu_items",jsonMenuItems);
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}


			} finally {
			    c.close();
			}	
			
				
		}
		
		return arr;
	}
	
	/**
	 * GET_MOST_POPULAR_MENU_ITEMS
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MOST_POPULAR_MENU_ITEMS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		if(db != null)
		{		
			String query_a = 
				"SELECT " +
				"mic._id, " + 
				"mi._id as menu_item_id, " +
				"mi.name as menu_item_name, " +
				"mi.description as menu_item_description, " +
				"mi.price as menu_item_price, " +
				"mi.order_count as menu_item_order_count, " + 
				"mic.categories_menus_id " +
				"FROM menu_items_categories mic " +
				"INNER JOIN menu_item mi ON mi._id = mic.menu_item_id " +
				"WHERE mi.order_count >= 5 " +
				"GROUP BY mi._id";										
			Cursor c = db.rawQuery(query_a , null);					
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("menu_item_id", c.getInt(1));
						p.put("menu_item_name", c.getString(2));			
						p.put("menu_item_description", c.getString(3));
						p.put("menu_item_price", c.getDouble(4));
						p.put("menu_item_order_count", c.getInt(5));
						p.put("categories_menus_id", c.getInt(6));				
												
						JSONArray jsonAddons = new JSONArray();
					    jsonAddons = GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID(c.getString(1));
					    p.put("addons",jsonAddons);
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}


			} finally {
			    c.close();
			}	
			
				
		}
		
		return arr;
	}
	
	/**
	 * GET_ALL_MENU_ITEMS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ALL_MENU_ITEMS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		if(db != null)
		{		
			String query_a = 
				"SELECT " + 
				"mic._id, " + 
				"mi._id as menu_item_id, " +
				"mi.name as menu_item_name, " +
				"mi.description as menu_item_description, " +
				"mi.price as menu_item_price, " +
				"mi.order_count as menu_item_order_count, " + 
				"mic.categories_menus_id " +
				"FROM menu_items_categories mic " +
				"INNER JOIN menu_item mi ON mi._id = mic.menu_item_id " +
				"GROUP BY mi._id";
										
			Cursor c = db.rawQuery(query_a , null);					
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("menu_item_id", c.getInt(1));
						p.put("menu_item_name", c.getString(2));			
						p.put("menu_item_description", c.getString(3));
						p.put("menu_item_price", c.getDouble(4));
						p.put("menu_item_order_count", c.getInt(5));
						p.put("categories_menus_id", c.getInt(6));							
						
						JSONArray jsonAddons = new JSONArray();
					    jsonAddons = GET_MENU_ITEM_ADDON_BY_MENU_ITEM_ID(c.getString(1));
					    p.put("addons",jsonAddons);
						
						arr.put(p);
						c.moveToNext();
			 								
					}	
				}


			} finally {
			    c.close();
			}	
			
				
		}
		
		return arr;
	}
	
	
	
	/**
	 * GET_MENU_ITEMS_BY_CATEGORIES_MENU_ID
	 * @param category_id
	 * @param menu_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_MENU_ITEMS_BY_CATEGORIES_MENU_ID  (String categories_menus_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		
		//if(db == null){
			////db = this.myDbHelper.openDataBase();
		//}
		
		if(db != null)
		{		
			String query = 
					"SELECT " +
					"mic._id,  " +		
					"mi._id as menu_item_id, " +
					"mi.name as menu_item_name, " +
					"mi.description as menu_item_description," +
					"mi.price as menu_item_price, " +
					"mit.img_medium as menu_item_img_medium, " +
					"mic.categories_menus_id," +
					"mi.order_count as menu_item_order_count, " +
					"mit.img_large as menu_item_img_large " +
					
					"FROM menu_items_categories mic " +
					"INNER JOIN menu_item mi ON mi._id = mic.menu_item_id " +
					"INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id "  + 
					"WHERE mic.categories_menus_id = " + categories_menus_id + ";";		
								
			Cursor f = db.rawQuery(query , null);					
			
			try {

				if (f!=null){
					
					int count = f.getCount();
					f.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!f.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", f.getString(0));
						p.put("menu_item_id", f.getString(1));
						p.put("menu_item_name", f.getString(2));			
						p.put("menu_item_description", f.getString(3));
						p.put("menu_item_price", f.getString(4));
						p.put("menu_item_img_medium", f.getString(5));
						p.put("categories_menus_id", f.getString(6));
						p.put("menu_item_order_count", f.getString(7));
						p.put("menu_item_img_large", f.getString(8));
						
						JSONArray jsonAddons = new JSONArray();
						
						String query2 = "SELECT * FROM menu_item_addons WHERE menu_item_id = " + f.getString(1) + ";";
						
						Cursor e = db.rawQuery(query2 ,null);		
						
						try {

							if (e!=null){
					
								int count2 = e.getCount();
								e.moveToPosition(0);    	
						    	int num2 = 0;	    	    	
								while (!e.isAfterLast() && num2 < count2){ 	
									
									JSONObject p2 = new JSONObject();					
									p2.put("_id", e.getInt(0));
									p2.put("description", e.getString(1));						
									p2.put("menu_item_id", e.getInt(2));						
									p2.put("created_at", e.getString(3));	
									p2.put("updated_at", e.getString(4));

									jsonAddons.put(p2);
									e.moveToNext();			 								
								}
							}	
				
						} finally {
						    e.close();
						}
						
						
						p.put("addons",jsonAddons);
						
						arr.put(p);
						f.moveToNext();
			 								
					}	
				}


			} finally {
			    f.close();
			}	
			
			//	
		}
		
		return arr;
	}
	
	
	/**
	 * GET_ALL_MENU_DATA
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_ALL_MENU_DATA () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//if(db == null)
			//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM menus",null);		
			
			try {
				
				if (c!=null){
					//	
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("description", c.getString(1));						
						p.put("folder_name", c.getString(2));					
						p.put("interval_from", c.getString(3));		
						p.put("interval_to", c.getString(4));					
						
						String one = c.getString(3);
						String two = c.getString(4);
						
						boolean isActive = dTools.compareDates(one, two);
						
						String result;
						
						if (isActive){
							result = "1";
						} else {
							result = "0";
						}
						
						p.put("active", result);
						
						JSONArray jsonCategoriesMenus = new JSONArray();
						
						String query_a = 
								"SELECT " +
								"cm._id,"  +
								"c._id as category_id, " +
								"c.description as category_description, " +
								"cm.menu_id " +
								"FROM categories_menus cm " +
								"INNER JOIN categories c ON c._id = cm.category_id " +
								"WHERE cm.menu_id  =  " + c.getString(0) +";";		
											
						Cursor d = db.rawQuery(query_a , null);					
						
						try {

							if (d!=null){
								//	
								int count2 = d.getCount();
								d.moveToPosition(0);    	
						    	int num2 = 0;	    	    	
								while (!d.isAfterLast() && num2 < count2){ 	
									
									JSONObject q = new JSONObject();
									
									q.put("_id", d.getString(0));
									q.put("category_id", d.getString(1));
									q.put("category_description", d.getString(2));			
									q.put("menu_id", d.getString(3));						
									
									JSONArray jsonMenuItems = new JSONArray();
									
									String query_c = 
											"SELECT " +
											"mic._id,  " +		
											"mi._id as menu_item_id, " +
											"mi.name as menu_item_name, " +
											"mi.description as menu_item_description," +
											"mi.price as menu_item_price, " +
											"mit.img_medium as menu_item_img_medium, " +
											"mic.categories_menus_id," +
											"mi.order_count as menu_item_order_count, " +
											"mit.img_large as menu_item_img_large " +
											
											"FROM menu_items_categories mic " +
											"INNER JOIN menu_item mi ON mi._id = mic.menu_item_id " +
											"INNER JOIN menu_item_thumbs mit ON mi._id = mit.menu_item_id "  + 
											"WHERE mic.categories_menus_id = " + d.getString(0) + ";";		
														
									Cursor f = db.rawQuery(query_c , null);					
									
									try {

										if (f!=null){
											
											int count3 = f.getCount();
											f.moveToPosition(0);    	
									    	int num3 = 0;	    	    	
											while (!f.isAfterLast() && num3 < count3){ 	
												
												JSONObject r = new JSONObject();
												
												r.put("_id", f.getString(0));
												r.put("menu_item_id", f.getString(1));
												r.put("menu_item_name", f.getString(2));			
												r.put("menu_item_description", f.getString(3));
												r.put("menu_item_price", f.getString(4));
												r.put("menu_item_img_medium", f.getString(5));
												r.put("categories_menus_id", f.getString(6));
												r.put("menu_item_order_count", f.getString(7));
												r.put("menu_item_img_large", f.getString(8));
												
												JSONArray jsonAddons = new JSONArray();
												
												String query2 = "SELECT * FROM menu_item_addons WHERE menu_item_id = " + f.getString(1) + ";";
												
												Cursor e = db.rawQuery(query2 ,null);		
												
												try {

													if (e!=null){
											
														int count4 = e.getCount();
														e.moveToPosition(0);    	
												    	int num4 = 0;	    	    	
														while (!e.isAfterLast() && num4 < count4){ 	
															
															JSONObject p2 = new JSONObject();					
															p2.put("_id", e.getInt(0));
															p2.put("description", e.getString(1));						
															p2.put("menu_item_id", e.getInt(2));						
															p2.put("created_at", e.getString(3));	
															p2.put("updated_at", e.getString(4));

															jsonAddons.put(p2);
															e.moveToNext();			 								
														}
													}	
										
												} finally {
												    e.close();
												}
												
												
												r.put("addons",jsonAddons);
												
												jsonMenuItems.put(r);
												f.moveToNext();
									 								
											}	
										}

									} finally {
									    f.close();
									}
									
									q.put("menu_items",jsonMenuItems);
									
									jsonCategoriesMenus.put(q);
									d.moveToNext();
						 								
								}	
							}


						} finally {
						    d.close();
						}	
						
						p.put("categories_menus",jsonCategoriesMenus);
						
						
						arr.put(p);
						c.moveToNext();
			 								
				}	
			}	
				
			} finally {				
			    c.close();			    
			}
			
			
		}
		
		return arr;
	}
	
	
	/**
	 * CREATE_OPTION
	 * @param option_name
	 * @param option_value
	 * @return
	 * @throws JSONException
	 */
	public JSONArray CREATE_OPTION ( String option_name, String option_value ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		Timestamp time = getCurrentTimeStamp();		
		JSONObject partial = new JSONObject();	
		
		try
		{			
			String query = "INSERT INTO options VALUES( '" + option_name + "','" + option_value + "'," + time + "," + time + ");";			
			db.execSQL(query);			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;	
		
	}	
	
	
	/**
	 * UPDATE_OPTION
	 * @param option_name
	 * @param option_value
	 * @param option_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray UPDATE_OPTION ( String option_name, String option_value, String option_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();
		JSONObject partial = new JSONObject();	
		
		try
		{			
			Timestamp time = getCurrentTimeStamp();
			
			String query = "UPDATE options SET option_name = '"+option_name+"', option_value = '"+option_value+"', updated_at = '" + time + "' WHERE _id = "+option_id+";";			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		
		arr.put(partial);
		return arr;
		
	}
	
	
	/**
	 * DELETE_OPTION
	 * @param option_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray DELETE_OPTION (String option_id) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		try
		{
			String query = "DELETE FROM options WHERE _id = "+option_id+";";			
			db.execSQL(query);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			partial.put("error", e);
		}
		arr.put(partial);
			
		return arr;
	}
	
	
	/**
	 * GET_OPTIONS
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_OPTIONS () throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT _id, option_name, option_value, created_at, updated_at FROM options;",null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("option_name", c.getString(1));						
						p.put("option_value", c.getString(2));										
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));						
						
						arr.put(p);
						c.moveToNext();
			 								
				}	
			}	
				
			} finally {				
			    c.close();			    
			}
			
			
		}
		
		return arr;
	}		
	
	
	/**
	 * GET_OPTION_BY_ID
	 * @param option_id
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_OPTION_BY_ID ( String option_id ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT _id, option_name, option_value, created_at, updated_at FROM options WHERE _id = "+option_id+";",null);		
			
			try {
				
				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	
						
						JSONObject p = new JSONObject();
						
						p.put("_id", c.getInt(0));
						p.put("option_name", c.getString(1));						
						p.put("option_value", c.getString(2));										
						p.put("created_at", c.getString(3));
						p.put("updated_at", c.getString(4));						
						
						arr.put(p);
						c.moveToNext();
			 								
				}	
			}	
				
			} finally {				
			    c.close();			    
			}
			
			
		}
		
		return arr;
	}		
	
	
	/**
	 * GET_OPTIONS_BY_NAME
	 * @param option_name
	 * @return
	 * @throws JSONException
	 */
	public JSONArray GET_OPTIONS_BY_NAME( String option_name ) throws JSONException {
		
		JSONArray arr = new JSONArray();			
		//db = this.myDbHelper.openDataBase();			
		
		JSONObject partial = new JSONObject();	
		
		if(db != null)
		{
			String query = "SELECT _id, option_name, option_value, created_at, updated_at FROM options WHERE option_name LIKE '%"+option_name+"%';";
			
			Cursor c = db.rawQuery(query ,null);		
			
			try {

				if (c!=null){
					
					int count = c.getCount();
					c.moveToPosition(0);    	
			    	int num = 0;	    	    	
					while (!c.isAfterLast() && num < count){ 	 
						
						JSONObject p = new JSONObject();		
						
						p.put("_id", c.getInt(0));
						p.put("option_name", c.getString(1));						
						p.put("option_value", c.getString(2));						
						p.put("created_at", c.getString(3));	
						p.put("updated_at", c.getString(4));			
						
						arr.put(p);
						c.moveToNext();			 								
					}
				}	

			} finally {
			    c.close();
			}			
				
		}
		
		return arr;
	}	
	
	
}	
	


