package com.roamtouch.menuserver.requests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.admin.AdminActivity.OnTablesLoaded;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RequestDiningTablesByWaiter extends AsyncTask<Integer, Void, ArrayList<DiningTables>> {
	
	private com.roamtouch.menuserver.admin.AdminActivity.OnTablesLoaded onTablesLoaded;	
	
	private Context context;
	
	private RequestJSON request = new RequestJSON();
	
	private MenuServerApplication app;
	private Activity activity;
	
	public RequestDiningTablesByWaiter(Activity activity){
		this.context = activity;
		this.activity = activity;
		app = ((MenuServerApplication) context.getApplicationContext());
	}
	
	public void setOnTablesLoaded(OnTablesLoaded onTablesLoaded){
		this.onTablesLoaded = onTablesLoaded;		
	}
	
	@Override
	protected ArrayList<DiningTables> doInBackground(Integer... params) {
		int userId = params[0];
		return getTablesByWaiter(userId);	
	}		

	@Override
	protected void onPostExecute(ArrayList<DiningTables> tables) {
		
		this.onTablesLoaded.onTablesLoaded(tables);
	}
	
	public ArrayList<DiningTables> getTablesByWaiter(int userId)
	{
		 JSONArray jsonTables = null;
		 ArrayList<DiningTables> tables = new ArrayList<DiningTables>();
		 
		 Param paramPost = new Param();
		 paramPost.addParam("action", Integer.toString(Constans.GET_DINING_TABLE_BY_USER));
		 paramPost.addParam("user_id", Integer.toString(userId)); //traer todos las mesas de un mozo
		 
		 try 
		 { 
			 jsonTables = request.postData(app.get_IP_SERVER(), paramPost);   
			 
			 // looping through All Users
			 if(jsonTables != null){
			     for(int i = 0; i < jsonTables.length(); i++){
			        JSONObject m = jsonTables.getJSONObject(i);
			        DiningTables t = new DiningTables();
			        t.set_id(m.getInt("_id"));			        
			        t.setFlashId(t.get_id());
			        t.setDescription(m.getString("description"));
			        t.setBlink(m.getInt("blink"));
			        t.setStatus(m.getString("status"));			        
			        tables.add(t);
			        
			        Log.d("outPut", "table id: " + String.valueOf(m.getInt("_id")));	 
			    }
			 }
	     } catch (JSONException e) {
		    e.printStackTrace();
		 }
		 
		 
		 return tables;  	 
	}		

}
