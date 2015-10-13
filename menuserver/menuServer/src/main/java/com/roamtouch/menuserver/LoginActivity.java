package com.roamtouch.menuserver;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.roamtouch.menuserver.entities.User;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends SherlockActivity implements Constans, OnKeyListener{
	
	private EditText etUser, etPassword;
	private String ip_maestra;
	public User user;
	private RequestJSON request = new RequestJSON();
	private Button btnLogin;
	private Handler mHandler;
	private TextView tvLogo;
	private Typeface mLogoFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		getSupportActionBar().hide();

		etUser = (EditText) findViewById(R.id.etUser);
		etUser.setOnKeyListener(this);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		tvLogo = (TextView) findViewById(R.id.tvLogo);

		mLogoFont = Typeface.createFromAsset(getResources().getAssets(), "logo.ttf");
		tvLogo.setTypeface(mLogoFont);

		this.ip_maestra = getIntent().getExtras().getString("ip_maestra");
		
		btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				if (ip_maestra.equals("0.0.0.0")){ 
					TextView ini =  (TextView) findViewById(R.id.inicialization);		
					ini.setText("No se puede obtener una direccion de red. \n Por favor conecte el dispositivo a su red wifi \n y vuelva a intentarlo.");
				} else {
					login();
				}	
			}
			
		});
		
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {

			public void run() {  
				
				if (ip_maestra.equals("0.0.0.0")){ 
					TextView ini =  (TextView) findViewById(R.id.inicialization);		
					ini.setText("No se puede obtener una direccion de red. \n Por favor conecte el dispositivo a su red wifi \n y vuelva a intentarlo.");
				} else {
					login();
				}			
				
			}
			
		}, 5000);
		
	}
	
	private void login(){	
		
		String username = etUser.getText().toString().trim();
		String password = etPassword.getText().toString().trim();
		try{
			if(username.length() > 0 && password.length() > 0){
				new RequestCheckUser().execute(username,password);
			}else{
				Toast.makeText(LoginActivity.this, "Ingrese usuario y contrasenia", Toast.LENGTH_SHORT).show();
			}
		}catch(Exception e){
			Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	@Override
	 public boolean onKey(View view, int keyCode, KeyEvent event) {
	   
		//if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
			login();
		//}	
		
	    return false; 
	 }
	
	private class RequestCheckUser extends AsyncTask<String, Void, User> {
		
		@Override
		protected User doInBackground(String... arg){					
		   return checkUser(arg[0],arg[1]);
		}
		

		@Override
		protected void onPostExecute(User u) {
			// TODO Auto-generated method stub
			if(u != null){
				if(u.getProfile_id() == PROFILE_ADMIN_ID){
					user = u;
					Intent go_to_cartacliente = new Intent("com.roamtouch.menuserver.ADMIN");
					Bundle args = new Bundle();
					args.putString("ip_maestra", ip_maestra);  
					go_to_cartacliente.putExtras(args);
					startActivity(go_to_cartacliente);
				}else{
					Toast.makeText(LoginActivity.this, "Acceso denegado para el usuario ingresado!", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(LoginActivity.this, "Usuario o contrasenia incorrecto", Toast.LENGTH_SHORT).show();
			}
			
		}
		
		public User checkUser(String username, String password)
		{
			 JSONArray jsonUser = null;   
		     User u = null;
		     
			 Param paramPost = new Param();
			 paramPost.addParam("action", Integer.toString(CHECK_USER));
			 paramPost.addParam("username", username);
			 paramPost.addParam("password", password);
			 
			 try    
			 { 
				 jsonUser = request.postData(ip_maestra, paramPost);   
				 
				 // looping
				 if(jsonUser != null){
				     for(int i = 0; i < jsonUser.length(); i++){
				        JSONObject m = jsonUser.getJSONObject(i);
				        u = new User();
				        u.set_id(m.getInt("_id"));
				        u.setUsername(m.getString("username"));
				        u.setPassword(m.getString("password"));
				        u.setFirst_name(m.getString("first_name"));
				        u.setFirst_name(m.getString("last_name"));
				        u.setEmail(m.getString("email"));
				        u.setStatus(m.getInt("status"));  
				        u.setProfile_id(m.getInt("profile_id"));
				        u.setProfile_description(m.getString("description"));
				        u.setCreated_at(m.getString("created_at"));
				        u.setUpdated_at(m.getString("updated_at"));
				        Log.d("outPut", "user username: " + String.valueOf("username"));
				        
				     }
				 }
		     } catch (JSONException e) {
			    e.printStackTrace();
			 }
			 
			 return u;  
		}
	}
		
}
