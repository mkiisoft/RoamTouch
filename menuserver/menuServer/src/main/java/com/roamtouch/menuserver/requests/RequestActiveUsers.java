package com.roamtouch.menuserver.requests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.adapters.ExpandableListAdapter;
import com.roamtouch.menuserver.admin.AdminActivity;
import com.roamtouch.menuserver.admin.AdminActivity.OnTablesLoaded;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.entities.User;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RequestActiveUsers extends AsyncTask<Void, Void, ArrayList<User>> {
	
	private TextView tvIpAddress, tvCountActiveUsers;
	
	public int count;
	private Context context;
	public Activity activity;
	private MenuServerApplication app;
	
	private ArrayList<User> arrUsers = new ArrayList<User>();
	private RequestJSON request = new RequestJSON();
	
	ArrayList<ArrayList<DiningTables>> arrTables = new ArrayList<ArrayList<DiningTables>>();
	
	private ExpandableListView expandable_list_waiters;
	private ExpandableListAdapter expListAdapter;
	
	public RequestActiveUsers(Activity act, ExpandableListView expandable_list_waiters){
		
		this.context = act;
		this.activity = act;
		
		this.expandable_list_waiters = expandable_list_waiters;
		expandable_list_waiters.setGroupIndicator(null);
		expandable_list_waiters.setClickable(true);
		
		app = ((MenuServerApplication) context.getApplicationContext());
		this.activity = activity;		
		
		tvIpAddress = (TextView) activity.findViewById(R.id.tvIpAddress);		
		tvCountActiveUsers = (TextView) activity.findViewById(R.id.tvCountActiveUsers);		
		final String ip = app.get_IP_SERVER();
		final int port = Constans.WEB_SERVER_PORT;
		
		this.activity.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {
		    	 tvIpAddress.setText("http://" + ip  + ":" + port);
		     }
		});
		
	}
	
	@Override
	protected ArrayList<User> doInBackground(Void... arg){					
	   arrUsers.clear();
	   return getUsers();	
	}
	
	public ArrayList<User> getUsers()
	{
		JSONArray jsonUsers = null;
		 ArrayList<User> users = new ArrayList<User>();
		 
		 Param paramPost = new Param();
		 paramPost.addParam("action", Integer.toString(Constans.GET_USERS_BY_STATUS));    
		 paramPost.addParam("status", "1"); //traer todos los usuarios que tengan estado activo
		 
		 try 
		 { 
			 jsonUsers = request.postData(app.get_IP_SERVER(), paramPost);   
			 
			 // looping through All Users
			 if(jsonUsers != null){
			     for(int i = 0; i < jsonUsers.length(); i++){
			        JSONObject m = jsonUsers.getJSONObject(i);
			        User u = new User();
			        u.set_id(m.getInt("_id"));
			        u.setUsername(m.getString("username"));
			        //u.setPassword(m.getString("password"));
			        u.setFirst_name(m.getString("first_name"));
			        u.setLast_name(m.getString("last_name"));
			        u.setEmail(m.getString("email"));
			        u.setStatus(m.getInt("status"));  
			        u.setProfile_id(m.getInt("profile_id"));
			        u.setProfile_description(m.getString("description"));
			        u.setCreated_at(m.getString("created_at"));
			        u.setUpdated_at(m.getString("updated_at"));
			        users.add(u);
			        Log.d("outPut", "user username: " + String.valueOf("username"));	 
			    }
			 }
	     } catch (JSONException e) {
		    e.printStackTrace();
		 }
		 
		 return users; 
	}
	

	@Override
	protected void onPostExecute(ArrayList<User> users) {			
		arrUsers = users;
		tvCountActiveUsers.setText(String.valueOf(users.size()));
		setExpandableAdapter(users);
	}
	
	public void setExpandableAdapter(final ArrayList<User> waiters)
	{   				
		
		final int length = waiters.size();
		count=1;
		arrTables = new ArrayList<ArrayList<DiningTables>>();
		
		for (int i=0; i<waiters.size(); i++) {
											
			RequestDiningTablesByWaiter tbw = new RequestDiningTablesByWaiter(activity);
			
			tbw.setOnTablesLoaded(new OnTablesLoaded(){

				@Override
				public void onTablesLoaded(ArrayList<DiningTables> tables) {
					
					arrTables.add(tables);
					
					if (count==length){						  
						
						expListAdapter = new ExpandableListAdapter (
								activity, arrUsers, arrTables);
					 
						expandable_list_waiters.setAdapter(expListAdapter); 
						expListAdapter.notifyDataSetChanged();			
						
						int count = expListAdapter.getGroupCount();
						for (int position = 1; position <= count; position++){
							expandable_list_waiters.expandGroup(position - 1);
						}
						
				        tvCountActiveUsers.setText(String.valueOf(waiters.size()));      
				        
					}
					
					count ++;
					
				}
				
			});
			
			int id = waiters.get(i).get_id();
			
			tbw.execute(id);	
			
		}        
	}
	
}
