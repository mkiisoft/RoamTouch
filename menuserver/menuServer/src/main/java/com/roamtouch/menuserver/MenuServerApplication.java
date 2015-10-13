package com.roamtouch.menuserver;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.roamtouch.menuserver.admin.AdminActivity;
import com.roamtouch.menuserver.communications.WebSocket;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.FileUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MenuServerApplication extends Application {

	private Context context;
	private AdminActivity adminActivity;	
	private WebSocket webSocket;
	private static DataBaseController database;
	
	private String IP_SERVER;
	private String SDCARD;

	private FileUtils fUtils;	

	@Override
    public void onCreate() {
        super.onCreate();  
        fUtils = new FileUtils(this);
    }


	public WebSocket getWebSocket() {
		return webSocket;
	}

	public void setWebSocket(WebSocket webSocket) {
		this.webSocket = webSocket;
	}
	
	public void renderLog(int action, String coming_address, long thread_pid, String user_agent){
		
		if (adminActivity!=null){
		
			String constant = getConstantName(action);
			if (constant==null){
				Log.v("","");
			}
			String terminal_id = getLogIdByIpAndUserAgent(coming_address, user_agent);			
			
			String log = 
					terminal_id 					+ "-" + 
					coming_address 					+ "-" + 
					getTimeDate("hh:mm:ss.SSS") 	+ "-" + 
					action 							+ "-" + 
					constant						+ "-" +
					thread_pid;
			
			adminActivity.showLogOnTextView(log);
			
			/*String log_store = 
					getTimeDate("dd/MM/yyyy hh:mm:ss.SSS") 	+ " | " +			
					coming_address		+ " | " +	
					action 				+ " | " + 
					constant			+ " | " +
					thread_pid;*/
			
			fUtils.storeLog(log);
		}
	}
	
	public void renderBackupLog(String type, String url, String fileName, String comment){
		
		if (adminActivity!=null){		
			
			String log =			 
			getTimeDate("hh:mm:ss.SSS") 	+ "^" + 
			type 							+ "^" +
			fileName						+ "^" +
			url								+ "^" +
			comment;	
			
			fUtils.storeBackupLog(log);
		}
	}
	
	
	public void renderRestoreLog(String type, String url, String fileName, String comment){
		
		if (adminActivity!=null){		
			
			String log =			 
			getTimeDate("hh:mm:ss.SSS") 	+ "^" + 
			type 							+ "^" +
			fileName						+ "^" +
			url								+ "^" +
			comment;	
			
			fUtils.storeBackupLog(log);
		}
	}
	
	private String getLogIdByIpAndUserAgent(String ip, String agent){
		
		if (IP_SERVER.equals(ip)){			
			ip = "0";			
		} else {
			//user agent here and ip.
			if ( agent.contains("Mozilla") 		&& 
			     agent.contains("AppleWebKit") 	&& 
			     agent.contains("Chrome") 		&&
			     agent.contains("Safari")
			){				
				ip = "1";
			}
			else {
				ip = "2";
				//Terminals
			}
			
		}
		
		return ip;
	}
	
	public static String getTimeDate(String dateFormat)
	{
		long milliSeconds = System.currentTimeMillis(); 
	    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(milliSeconds);
	    return formatter.format(calendar.getTime());
	}	
	
	public String getConstantName(int value) {
		  Class<Constans> c = Constans.class;
		  for (Field f : c.getDeclaredFields()) {
		    int mod = f.getModifiers();
		    Class<String> stringClass = (Class<String>) f.getType();
		    if (
		    	Modifier.isStatic(mod) && 
		    	Modifier.isPublic(mod) && 
		    	Modifier.isFinal(mod)		    	
		    	) {
		      try {
		    	  String name = f.getName();
		    	  if (stringClass.getName().equals("int")){    	  
		    		  Integer const_val = (Integer)f.get(null);		    	   				        
		    	   	  if (const_val==value){
		    	   		  return f.getName();
		    	   	  }
		    	  } else if (stringClass.getName().equals("int")){
		    		  String const_val = (String)f.get(null);
		    		  if (const_val.equals(value)){
		    			  return f.getName() + " " + f.get(name);
		    		  }
		    	  }		    	  
		      } catch (IllegalAccessException e) {
		        e.printStackTrace();
		      }
		    }
		  }
		return "N/A";
	}
	
	public String getIp() {
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);		
		TelephonyManager lTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);		
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String iPAddress = intToIp(ipAddress);
		return iPAddress;
	}
	
	public String intToIp(int i) {
		// return ((i >> 24 ) & 0xFF ) + "." + ((i >> 16 ) & 0xFF) + "." + ((i
		// >> 8 ) & 0xFF) + "." + ( i & 0xFF) ; }
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + ((i >> 24) & 0xFF);
	}
	
	public void refreshAdminTables(){
		adminActivity.refreshAdminTables();
	}
	
	public AdminActivity getAdminActivity() {
		return adminActivity;
	}


	public void setAdminActivity(Context context, AdminActivity adminActivity)
	{
		this.context = context;
		this.adminActivity = adminActivity;
	}
	
	public String get_IP_SERVER() {
		return IP_SERVER;
	}
	
	public void set_IP_SERVER(String iP_SERVER) {
		IP_SERVER = iP_SERVER;
	}
	
	public String getSDCARD() {
		return SDCARD;
	}


	public void setSDCARD(String sDCARD) {
		SDCARD = sDCARD;
	}

	public static DataBaseController getDatabase() {
		return database;
	}


	public static void setDatabase(DataBaseController database) {
		MenuServerApplication.database = database;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
