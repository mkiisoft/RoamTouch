package com.roamtouch.menuserver.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.R.id;
import com.roamtouch.menuserver.R.layout;
import com.roamtouch.menuserver.adapters.AdapterOrderDetail;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.entities.OrderDetail;
import com.roamtouch.menuserver.entities.User;
import com.roamtouch.menuserver.importdata.GoogleSpreadSheetTab;
import com.roamtouch.menuserver.requests.RequestActiveUsers;
import com.roamtouch.menuserver.requests.RequestComandas;
import com.roamtouch.menuserver.requests.RequestOpenDiningTables;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

public class AdminActivity extends SherlockActivity implements Constans {

	private final static int INTERVAL_UPDATE_ADMIN_MASTRA = 15000; // 5 seg
	Handler handlerIntervalAdminMaestra;
	private Activity activity;
	
	private Timer tableTimer;
	
	private MenuServerApplication app;
	            
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.admin_activity);
		
		this.activity = this;
		
		//Application constants
		app = ((MenuServerApplication) this.getApplication());
		app.setAdminActivity(this, this);
		app.set_IP_SERVER(getIntent().getExtras().getString("ip_maestra"));		
		
		//REFRESH TABLES
		refreshAdminTables();	
		setAlarm();
		
		//Log
		logListView = (ListView) findViewById(R.id.serverLog);
    	logAdapter = new LoglistAdapter(activity, R.layout.log_row, loglist);     
    	logListView.setAdapter(logAdapter);
    	logListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {            	  
          	  int itemPosition     = position;
          	  String  itemValue    = (String) logListView.getItemAtPosition(position);
              
            }
    	});    	 
    	
	}
	
	
	public void refreshAdminTables(){
		
		new Thread()
		{
			@Override
			public void run() 
			{
				ExpandableListView elw = (ExpandableListView) findViewById(R.id.expandable_list_waiters);
		        new RequestActiveUsers(activity, elw).execute();	        
		 		new RequestOpenDiningTables(activity).execute();   
		 		new RequestComandas(activity).execute();		 		
			}
		}.start();		
	}
	
    ArrayList<String> loglist = new ArrayList<String>();    
    LoglistAdapter logAdapter;
    ListView logListView;    
	
	public void showLogOnTextView(final String log) {		
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	loglist.add( log );   
        		logAdapter.notifyDataSetChanged();  
        		scrollMyListViewToBottom();
	        }
	    });      
		
	}
	
	private void scrollMyListViewToBottom() {
		logListView.post(new Runnable() {
	        @Override
	        public void run() {	            
	        	logListView.setSelection(logAdapter.getCount() - 1);
	        }
	    });
	}
	
	public class LoglistAdapter extends ArrayAdapter<String> {

		public LoglistAdapter(Context context, int textViewResourceId) {
		    super(context, textViewResourceId);
		}

		public LoglistAdapter(Context context, int resource, List<String> items) {
		    super(context, resource, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;	    
		    LayoutInflater vi;
		    
		    if (v == null) {
                vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.log_row, parent, false);
            }		    
		    
		    String item = getItem(position);
		    
		    String[] spl = item.split("-");
		    
		    
		    ImageView imageView = (ImageView) v.findViewById(R.id.icon_flag);
		    
		    TextView server_log = (TextView) v.findViewById(R.id.server_log); 
		    
		    
		    String logId = spl[0];
		    String colorLog = "white";
		    
		    
		    if (logId.equals("0")) {	    	 
		    	 imageView.setImageResource(R.drawable.log_server_flag);
		    	 colorLog="blue";
		    } else if (logId.contains("1")) {	 
		    	imageView.setImageResource(R.drawable.log_web_flag);
		    	colorLog="red";
		    } else {	
		    	imageView.setImageResource(R.drawable.log_terminal_1_flag);
		    	colorLog="green";
		    }
		    
		    if (server_log!=null){	    			    	
		    	server_log.setTextColor(Color.WHITE);
		    	String out = "<p><font size=\"3\" color=\"white\">" +	    			 
		    			spl[2] 	+ " "  + "<i>"  + 
		    			spl[1] 	+ " "  + "</i>" +
		    			spl[3] 	+ " "  + "<i>"  +
		    			spl[5]  + " "  + "</i>" +			
		    			"</font><font size=\"5\" color=\""+colorLog+"\"><b>" + " "  +		    			
		    			spl[4]	+ "</b></font></p>";		    	
		    	server_log.setText(Html.fromHtml(out));
	    	}
		    return v;

		}
	}
	
	private void setAlarm() {      

		Intent intent = new Intent(this, AlarmReceiver.class);
		 PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		  AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		  
		  //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000), sender);
		  
		  Calendar calNow = Calendar.getInstance();
		  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calNow.getTimeInMillis(), 5*60*1000, pendingIntent);
		   Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();

    }  
	
	public class AlarmReceiver extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context k1, Intent k2) {
	         Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();
	    }

	}	
	
	@Override
    public void onDestroy() {
		super.onDestroy();

    }	
	
	public interface OnTablesLoaded {
		  public void onTablesLoaded(ArrayList<DiningTables> tables);   
	}
	
	
}
