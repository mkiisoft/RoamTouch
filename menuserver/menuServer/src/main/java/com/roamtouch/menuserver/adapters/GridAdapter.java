package com.roamtouch.menuserver.adapters;

import java.util.ArrayList;
import java.util.Set;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.admin.WaiterTableView;
import com.roamtouch.menuserver.entities.DiningTableDetail;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.entities.OrderDetail;
import com.roamtouch.menuserver.entities.User;
import com.roamtouch.menuserver.requests.RequestDiningTableDetail;
import com.roamtouch.menuserver.requests.RequestDiningTableDetail.OnPopupInterface;
import com.roamtouch.menuserver.requests.RequestOrderDetail;
import com.roamtouch.menuserver.shared.Constans;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

	 private MenuServerApplication app;
	 private static Activity activity;
	 private ArrayList<DiningTables> tables;	 
	 private int id;	 	
	 private GridView grid;	 
	 private User user;
	  
	 protected static final String TAG = "GridAdapter";
	 
		public static MediaPlayer player;	
		public static final int SOUND_VOLUME = 255;	
	 
	 	public GridAdapter(Activity activity, ArrayList<DiningTables> tables, int id, GridView grid, User user) {
	          this.activity = activity;	          
	          this.tables = tables;   
	          this.id = id;	        
	          this.grid = grid;  
	          this.user = user;
	          app = ((MenuServerApplication) activity.getApplication());
	    }

		@Override
	    public int getCount() {	      
	      return tables.size();
	    }
	    @Override
	    public Object getItem(int position) {
	      return position;
	    }
	    @Override
	    public long getItemId(int position) {	    
	      return 0;
	    }
	    
	    Animation anim;
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) { 
	    		    	
	      WaiterTableView waiterTableView = null;
	      
	      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	          
	      	if (convertView == null) {	          
	      			      		
	      		waiterTableView = (WaiterTableView) inflater.inflate(R.layout.table, null);   		
	      		
	      		TextView textView = (TextView) waiterTableView.findViewById(R.id.grid_text);
	            ImageView imageView = (ImageView) waiterTableView.findViewById(R.id.grid_image);
	            
	            DiningTables table = tables.get(position);  

	            waiterTableView.setDiningTable(table);
	            
	            if (table.getBlink()==1){            

		            Animation blinkanimation = AnimationUtils.loadAnimation(activity, R.anim.blink);
		            
		            blinkanimation.setDuration(5000);
		            blinkanimation.setStartOffset(500);
		            blinkanimation.setFillAfter(true);
		            
		            blinkanimation.setAnimationListener(new AnimationListener(){
	
						@Override
						public void onAnimationStart(Animation animation) {
							playSound(R.raw.wan, false);
							Log.v(TAG, " Animation Started");			
						}
	
						@Override
						public void onAnimationEnd(Animation animation) {						
							
						}
	
						@Override
						public void onAnimationRepeat(Animation animation) {
							playSound(R.raw.chime, false);
							Log.v(TAG, " Animation Repeated");		
						}
		            	
		            });	            
		            	            	            
		            imageView.setAnimation(blinkanimation);		            
		            waiterTableView.setBlinkanimation(blinkanimation);
	            
	            }
	      		
	      		waiterTableView.setOnClickListener(new View.OnClickListener() {

	                public void onClick(View v) {  	                	
	                	
	                	DiningTables table = ((WaiterTableView) v).getDiningTable();    
	                	
	                	Animation blinkanimation = ((WaiterTableView) v).getBlinkanimation();	                	
	                	
	                	v.clearAnimation();
	                	
	                	RequestDiningTableDetail rdtd = new RequestDiningTableDetail(activity, table, user);
	                	
	                	rdtd.setOnPopupInterfaceListener(new OnPopupInterface(){

							@Override
							public void popupShowed() {		
								
							}

							@Override
							public void popupClosed(int dining_table_id) {								
								
								String id = Integer.toString(dining_table_id);								
								boolean blink = app.getDatabase().SET_COMMAND_BLINK_TABLE_BY_TABLE_ID(id, "0");	
								
								if (blink)
									// REFRESH ADMIN TABLE 
									app.refreshAdminTables();  
							}
	                		
	                	});
	                	rdtd.execute();

	                }
	            }); 	
	            
	            waiterTableView.setDiningTable(table);
	            
	            String  table_number = String.valueOf(table.get_id());
	            
	            textView.setText(table_number);   
	            
	            int imageid = table.getFlashId();
	            
	            imageView.setImageResource(getImageAsset(imageid));

	            
	          } else {
	        	  
	        	  waiterTableView = (WaiterTableView) convertView;
	            
	          }
	      return waiterTableView;
	    }
	    
	    private int getImageAsset(int id){	    	
	    	
	    	switch(id){
	    	
	    	case 1:
	    		id = R.drawable.image1;
	    		break;
	    	case 2:
	    		id = R.drawable.image2;
	    		break;
	    	case 3:
	    		id = R.drawable.image3;
	    		break;
	    	case 4:
	    		id = R.drawable.image4;
	    		break;
	    	case 5:
	    		id = R.drawable.image5;
	    		break;
	    	case 6:
	    		id = R.drawable.image6;
	    		break;
	    	case 7:
	    		id = R.drawable.image7;
	    		break;	    		
	    	case 8:
	    		id = R.drawable.image8;
	    	case 100:
	    		id = R.drawable.image100;
	    		break;
	    	}
	    	
	    	return id;
	    	
	    }
	    
	    public static void playSound(final int s, final boolean looping){
			
			// Downloaded. 
			Thread thread = new Thread() {
				
			    @Override
			    public void run() {
			        
			    	try {
			        	
			    		player = MediaPlayer.create(activity, s);
			    		
			    	    player.setVolume(8f, 8f);	
			    	    
			    	    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			    	    	
			    	        @Override
			    	        public void onPrepared(MediaPlayer mp) {
			    	        	mp.start();
			    	        }
			    	        
			    	    });
			        	        	
			        	
			    		if (looping){
			    			
			    			player.setLooping(true);
			    			
			    		}
			    			
		    			player.setOnCompletionListener(new OnCompletionListener() {
			    		    
			    			public void onCompletion(MediaPlayer mp) {	    				
			    				mp.release();
			    		    };
			    		    
			    		});		    		
			    		
			        } catch (Exception e) {			        	
			            e.printStackTrace();
			        }
			    }
			};			
			thread.start();
			
		}
	    
	    public static void stopPlayerLoop(){
			
			if(player != null){
				
					try 
					{					
						if (player.isPlaying()) 				
								player.stop(); 
						
						player.reset();
						player.release();							
							
				   } catch (Exception e) {
					   
					   e.printStackTrace();       
				       
				   }
			}
		}        

}
