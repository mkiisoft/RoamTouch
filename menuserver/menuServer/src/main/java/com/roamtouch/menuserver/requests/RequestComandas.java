package com.roamtouch.menuserver.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class RequestComandas extends AsyncTask<Void, Void, Integer> {
	
	private Context context;
	private Activity activity; 
	
	private MenuServerApplication app;
	
	private RequestJSON request = new RequestJSON();
	
	private TextView tvCountComanda;
	
	public RequestComandas(Activity activity){
		this.context = activity;
		app = ((MenuServerApplication) activity.getApplicationContext());
		tvCountComanda = (TextView) activity.findViewById(R.id.tvCountComanda);
	}
	
	@Override
	protected Integer doInBackground(Void... arg){					
	
	   return getComandas();	
	}
	

	@Override
	protected void onPostExecute(Integer cant) {
		
		tvCountComanda.setText(String.valueOf(cant));
	}
	
	public int getComandas()    
	{
		 JSONArray jsonComandas = null;
		 int cantidad = 0;
		 
		 Param paramPost = new Param();
		 paramPost.addParam("action", Integer.toString(Constans.GET_COMANDAS));       
		 
		 try 
		 { 
			 jsonComandas = request.postData(app.get_IP_SERVER(), paramPost);   
			 
			 // looping through All Users
			 if(jsonComandas != null){
			     for(int i = 0; i < jsonComandas.length(); i++){
			        JSONObject m = jsonComandas.getJSONObject(i);
			        cantidad = m.getInt("cantidad");
			        Log.d("outPut", "cantidad de comandas: " + String.valueOf(m.getInt("cantidad")));	 
			    }
			 }
	     } catch (JSONException e) {
		    e.printStackTrace();
		 }
		 
		 
		 return cantidad;
	}
}
