package com.roamtouch.menuserver.requests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class RequestOpenDiningTables extends AsyncTask<Void, Void, Integer> {
	
	private Context context;
	private Activity activity; 
	private MenuServerApplication app;
	
	private RequestJSON request = new RequestJSON();
	
	private TextView tvCountOpenTable;
	
	public RequestOpenDiningTables(Activity activity){
		
		this.context = activity;
		this.activity = activity;
		app = ((MenuServerApplication) activity.getApplicationContext());
		
		tvCountOpenTable = (TextView) activity.findViewById(R.id.tvCountOpenTable);
	}
	
	@Override
	protected Integer doInBackground(Void... arg){					
	
	   return getOpenTables();	
	}
	

	@Override
	protected void onPostExecute(Integer cant) {
		
		tvCountOpenTable.setText(String.valueOf(cant));			
	}
	
	public int getOpenTables()
	{
		 JSONArray jsonTables = null;
		 ArrayList<DiningTables> tables = new ArrayList<DiningTables>();
		 
		 Param paramPost = new Param();
		 paramPost.addParam("action", Integer.toString(Constans.GET_DINING_TABLE_BY_STATUS));
		 paramPost.addParam("dining_table_status_id", String.valueOf(Constans.DINING_TABLE_STATUS_ID_OPEN)); //traer todos las mesas que tengan estado abierto
		 
		 try 
		 { 
			 jsonTables = request.postData(app.get_IP_SERVER(), paramPost);   
			 
			 // looping through All Users
			 if(jsonTables != null){
			     for(int i = 0; i < jsonTables.length(); i++){
			        JSONObject m = jsonTables.getJSONObject(i);
			        DiningTables t = new DiningTables();
			        t.set_id(m.getInt("_id"));
			        t.setDescription(m.getString("description"));
			        t.setDining_table_status_id(Integer.parseInt(m.getString("dining_table_status_id")));
			        tables.add(t);
			        Log.d("outPut", "table id: " + String.valueOf(m.getInt("_id")));	 
			    }
			 }
	     } catch (JSONException e) {
		    e.printStackTrace();
		 }
		 
		 
		 return tables.size();  	 
	}
}
