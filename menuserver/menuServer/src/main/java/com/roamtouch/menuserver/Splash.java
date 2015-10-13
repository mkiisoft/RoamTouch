package com.roamtouch.menuserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class Splash extends SherlockActivity{
	
	private MenuServer mBoundService;
	private boolean mIsBound;
	
	String ip_maestra;
	Button tryagain;
	
	private MenuServerApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		//run service
		new StartServiceRequest().execute();
		
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        // This is called when the connection with the service has been
	        // established, giving us the service object we can use to
	        // interact with the service.  Because we have bound to a explicit
	        // service that we know is running in our own process, we can
	        // cast its IBinder to a concrete class and directly access it.	        
	    	mBoundService = ((MenuServer.LocalBinder)service).getService();
	    	ip_maestra = mBoundService.getIpMaestra();
	        // Tell the user about status service.
	        /*Toast.makeText(Splash.this, R.string.local_service_connected,
	                Toast.LENGTH_SHORT).show();*/
	        Log.d("SERVICE", "Servicio inicializado");
	        
	        tryConnection();    
	        
	    }
	    
	    public void onServiceDisconnected(ComponentName className) {
	        // This is called when the connection with the service has been
	        // unexpectedly disconnected -- that is, its process crashed.
	        // Because it is running in our same process, we should never
	        // see this happen.
	        mBoundService = null;
	        mIsBound = false;
	        Log.d("SERVICE", "Servicio detenido");
	    }
	};

	private void tryConnection(){
		
		 app = ((MenuServerApplication)getApplication());
		 
		 ip_maestra = app.getIp();
		 
	     if (ip_maestra.equals("0.0.0.0")){ 
			
	    	TextView ini =  (TextView) findViewById(R.id.inicialization);		
			ini.setText("No se puede obtener una direccion de red. \n Por favor conecte el dispositivo a su red wifi \n y vuelva a intentarlo.");
			tryagain = (Button) findViewById(R.id.btnTryAgain);
			tryagain.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					 tryConnection(); 			
				}
				
			});
			tryagain.setVisibility(View.VISIBLE);
			
		} else {
			goAdminCartaMaestra();
		}
	    
    }
	
	private class StartServiceRequest extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... arg){					
			doBindService();
			return null;
		}
		
		public void doBindService() {
		    // Establish a connection with the service.  We use an explicit
		    // class name because we want a specific service implementation that
		    // we know will be running in our own process (and thus won't be
		    // supporting component replacement by other applications).
		    if(bindService(new Intent(Splash.this, MenuServer.class), mConnection, Context.BIND_AUTO_CREATE)){
		    	mIsBound = true;
		    }else{
		    	mIsBound = false;
		    }
		    
		}
		
	}

	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        unbindService(mConnection);
	        mIsBound = false;
	    }
	}
	
	public void goAdminCartaMaestra()
	{          
		Log.d("BOUND", "estado: " + String.valueOf(mIsBound));
		if(mIsBound){			
			Intent go_to_login = new Intent("com.roamtouch.menuserver.LOGIN");
			Bundle args = new Bundle();
			args.putString("ip_maestra", ip_maestra);
			go_to_login.putExtras(args);
			startActivity(go_to_login);
		}
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    doUnbindService();
	}
}
