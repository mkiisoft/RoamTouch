package com.roamtouch.menuserver;

import it.nicola_amatucci.android.discovery.DiscoveryServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.json.JSONArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import com.roamtouch.menuserver.communications.WebSocket;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.httpd.CobyWebServer;
import com.roamtouch.menuserver.httpd.NanoHTTPD;
import com.roamtouch.menuserver.importdata.GoogleSpreadSheetTab;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.ExternalStorage;
import com.roamtouch.menuserver.utils.FolderHandler;


public class MenuServer extends Service {

	private CobyWebServer HTTPServer;
	
	private String IP_ADDRESS;
	
	private static DataBaseController database;
	
	Handler mHandler = new Handler();
	
	WebSocket socketServer;
	
	private AssetManager assets_directory;
	
	private FolderHandler folderHandler;  
	
	//Base Local Folder on SDCARD
	private static String BASE_PATH;// = Environment.getExternalStorageDirectory() + "/MenuServer";
	
	private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;
    
    private MenuServerApplication app;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        MenuServer getService() {
            return MenuServer.this;
        }
    }
    
    @Override
    public void onCreate() {  
    	  
    	mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);      
        
        app = ((MenuServerApplication)getApplication());

        //IP_ADDRESS = "192.168.0.102";
    	//IP_ADDRESS = "127.0.0.1";          
        
        IP_ADDRESS = app.getIp();
        
        app.set_IP_SERVER(IP_ADDRESS);
        
        // External Storage
        Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
        File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
        File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
        
        BASE_PATH =  sdCard.toString() + "/MenuServer";        
        app.setSDCARD(BASE_PATH); 
        
        assets_directory = this.getAssets();  

        if (IP_ADDRESS.equals("0.0.0.0")){         	
        	 
            mHandler.postDelayed(new Runnable() {

      				public void run() {   
      					finish();  
      				}
      				
      		}, 20000);
              
        	//Poner aca que hay que conectarlo a wifi.
        	showToast("No se puede obtener una direccion de red, por favor conecte la tableta Coby a Internet y vuelva a intentarlo. ");
        	
        } else {
           
        	 //COPIA TODA LA ESTRUCTURA ASSETS
	        folderHandler = new FolderHandler(this);       
	        
        	//INICIALIZA BASE DATOS
	        //SQLITE INITIALIZATION        
 	        database = new DataBaseController(this);        
	        database.createDatabase();      
	        database.open();
	        app.setDatabase(database);
	        
	        folderHandler.setDB(database);
	        
	        try {
				
	        	folderHandler.createAssetsFolder();
				
			} catch (IOException e1) {
				e1.printStackTrace();				
			}
	        
	        
	        if (isNetworkOnline()){	        
				
				try {
					
					//WEB SOCKET SERVERs			
					socketServer = new WebSocket(Constans.WEB_SOCKET_PORT, database, this, folderHandler);
				
				} catch (Exception e){
					Log.d("ERROR: ", e.toString());
					e.printStackTrace();
				}
				
		        
				File f = new File( BASE_PATH + "/httpd/" );
				//File f = new File( "." );
				
				try {
				
					HTTPServer = new CobyWebServer(IP_ADDRESS, Constans.WEB_SERVER_PORT, f, this, database, folderHandler, socketServer);
					HTTPServer.startHTTPD();
				
				} catch (Exception e){
					Log.d("ERROR: ", e.toString());
					e.printStackTrace();
				}
				
				// UDP DISCOVERY.
				try {
					DiscoveryServer ds = new DiscoveryServer(this, "MENUSERVER", Constans.UDP_PORT, "MYAPP_TOKEN");
				} catch (InterruptedIOException e) {				
					showToast("ERROR: " + e.toString(), Toast.LENGTH_SHORT);
					Log.d("ERROR: ", e.toString());
					e.printStackTrace();
				}
				
				showNotification();		
	        } 
	        
	        else {
	        	
	        	showToast("Device is offline, please connect Wifi.");
	        	
	        } 
	        
			// http://www.dnsjava.org/download/
			/*String name = "menu.server";

	        try {
	            Lookup lookup = new Lookup(name, Type.A);
	            
	            lookup.setResolver(new SimpleResolver(IP_ADDRESS));// + ":5556"));

	            final Record[] frecords = lookup.run();

	            Log.i("DNS", "records found: " + frecords.length); // gives 0
	            Log.i("DNS", lookup.getErrorString()); // gives "timeout"
	        
	        } catch (TextParseException e) {
	        	
	            e.printStackTrace();
	            
	        } catch (UnknownHostException e) {
	        	
	            e.printStackTrace();
	            
	        }*/
			     
			//TextView text = (TextView) findViewById(R.id.ip_address);
			//text.setText("http://" + IP_ADDRESS + ":" + UDP_PORT + "/index.html");
			
			 // Display a notification about us starting.  We put an icon in the status bar. 
        }
			 
    }
    
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();  
            return false;
        }
        return status;

    }    
    
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() 
    {
    	if (database.getmDbHelper() != null) {
        	database.getmDbHelper().close();
        }
    	// Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        //Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        Log.d("SERVICE", "Servicio detenido en onDestroy");
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    
    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
    
    
    public CobyWebServer getHTTPServer() {
		return HTTPServer;
	}

	//Entrega el directorio assets
    public AssetManager getAssets_directory() {    	
		return assets_directory;
	}  
        

	public String writeURLOnHTML(String uri) throws IOException {      
    	
    	String str="";
    	StringBuffer buf = new StringBuffer();    	   
    	
    	File file = getBaseContext().getFileStreamPath(uri);
    	
        if(file.exists()) { 
    	
	    	InputStream is = this.getAssets().open(uri);   	
	    	
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    	
	    	if (is!=null) {							  
	    		while ((str = reader.readLine()) != null) {	
	    			if (str.contains("localhost")){
	    				str = str.replace("localhost", IP_ADDRESS);
	    			}
	    			buf.append(str + "\n" );
	    		}				
	    	}		
	    	is.close();
	    	
	    	//Toast.makeText(getBaseContext(), 
	    	//		buf.toString(), Toast.LENGTH_LONG).show();
    	
	    	return buf.toString();	
        } 
        
        else {
        	
        	return null;
        	
        }

    }
    
    public void showToast(String msg){    	
    	Toast.makeText(getApplicationContext(), msg,
    	Toast.LENGTH_LONG).show();	
    }
     
    
    public boolean isSdCardReady(){
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);  
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
	
	
	public String getSessionTime(){
		Calendar c = Calendar.getInstance(); 
		int hours = c.get(Calendar.HOUR);
		int seconds = c.get(Calendar.SECOND);			
		return hours + "-" + seconds; 
	}
	
    
    public void showToast(String msg, int time) {
		CharSequence text = msg;
		int duration = time;
		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
	}     

    /**
     * Show a notification while this service is running.
     */

	private void showNotification() {
        // In this app, we'll use the same text for the ticker and the expanded notification
        //CharSequence text = getText(R.string.local_service_started);
        String msg = "Servicio CartaMaestra inicializado correctamente. http://" + IP_ADDRESS + ":" + Constans.UDP_PORT + "/index.html";
        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.notification_message, "CartaMaestra",
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        Intent login_intent = new Intent(this, LoginActivity.class);
        Bundle args = new Bundle();
        args.putString("ip_maestra", IP_ADDRESS);
        login_intent.putExtras(args);
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,login_intent, 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                       msg, contentIntent);
        
        //permanecer en background
        startForeground(NOTIFICATION, notification);
        
        // Send the notification.
        mNM.notify(NOTIFICATION, notification);  
    }

	public String getIpMaestra(){
    	return IP_ADDRESS;      
    }



	// Close app.
	public void finish() {
		finish();		
	}
    
}
