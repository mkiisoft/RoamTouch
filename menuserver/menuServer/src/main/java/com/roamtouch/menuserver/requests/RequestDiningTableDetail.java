package com.roamtouch.menuserver.requests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.adapters.AdapterOrderDetail;
import com.roamtouch.menuserver.admin.AdminActivity;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.entities.OrderDetail;
import com.roamtouch.menuserver.entities.User;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.Param;
import com.roamtouch.menuserver.utils.RequestJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class RequestDiningTableDetail extends AsyncTask<Integer, Void, ArrayList<OrderDetail>> {
	
	private Context context;
	private AdapterOrderDetail adapter_order_detail;
	private RequestJSON request = new RequestJSON();
	
	private ListView lvOrderDetail;
	
	private MenuServerApplication app;
	private Activity activity;
	
	WindowManager manager;
	PopupWindow pop;
	View popupView;
	Button bYesClear;
	int mCurrentX;
	int mCurrentY;
	
	DiningTables table;
	User user;
	
	
	public RequestDiningTableDetail(Activity activity, DiningTables table, User user){
		this.context = activity;
		this.activity = activity;
		this.table = table;
		this.user = user;
		app = ((MenuServerApplication) activity.getApplicationContext());
		createWindow();
	}
	
	@Override
	protected ArrayList<OrderDetail> doInBackground(Integer... arg){
	   int id = this.user.get_id();
	   return getOrderDetail(id); //arg[0]);	
	}

	@Override
	protected void onPostExecute(ArrayList<OrderDetail> orderdetail) {
		
		Log.d("ORDER CON DETALLE", String.valueOf(orderdetail.size()));
		//orderArrOrder(orderdetail);
		for(int i=0; i<orderdetail.size(); i++){
			Log.d("order header", "order id: " + String.valueOf(orderdetail.get(i).getOrder_id()) + " idHeader1: " + String.valueOf(orderdetail.get(i).isHearder1()) + " idHeader2: " + String.valueOf(orderdetail.get(i).isHearder2()));
		}		
		adapter_order_detail = new AdapterOrderDetail(activity, orderdetail);	
		lvOrderDetail.setAdapter(adapter_order_detail);			
	}	
	
	public void createWindow() {
		
	    LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = (FrameLayout) inflater.inflate(R.layout.detail_table_orders, null);
        	        
        pop = new PopupWindow(popupView, 400, 500, true);        
        pop.setContentView(popupView);
        pop.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        
        lvOrderDetail = (ListView) popupView.findViewById(R.id.lvOrderDetail);	        
	      
        bYesClear = (Button) popupView.findViewById(R.id.bClear);
    	bYesClear.setOnClickListener(new Button.OnClickListener(){		
		    @Override
		    public void onClick(View v) {	
		    	if (pop.isShowing()) {
		    		pop.dismiss();
	            }			    	
		    }}
	    );  
	    
	    popupView.post(new Runnable() {
	        public void run() {	        	

	            pop.showAtLocation(popupView, Gravity.NO_GRAVITY, 400, 500);            
	        	
	        	pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
			        @Override
			        public void onDismiss() {

			            new Handler().postDelayed(new Runnable() {
			                @Override
			                public void run() {
			                	int id = table.get_id();
			                	onPopupInterface.popupClosed(id);			                	
			                }
			            }, 1000);  
			        };
			    });     	
	        	if (onPopupInterface!=null)
	        		onPopupInterface.popupShowed();
	        }
	    });	
	    
	    OnTouchListener otl = new OnTouchListener() {
	        private float mDx;
	        private float mDy;

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            int action = event.getAction();
	            if (action == MotionEvent.ACTION_DOWN) {
	                mDx = mCurrentX - event.getRawX();
	                mDy = mCurrentY - event.getRawY();
	            } else
	            if (action == MotionEvent.ACTION_MOVE) {
	                mCurrentX = (int) (event.getRawX() + mDx);
	                mCurrentY = (int) (event.getRawY() + mDy);
	                pop.update(mCurrentX, mCurrentY, -1, -1);
	            }
	            return true;
	        }
	    };
	    lvOrderDetail.setOnTouchListener(otl);

	    //mCurrentX = 20;
	    //mCurrentY = 50;
	    popupView.post(new Runnable() {
	        @Override
	        public void run() {
	        	pop.showAtLocation(lvOrderDetail, Gravity.NO_GRAVITY, mCurrentX, mCurrentY);
	        }
	    });
	    
	    
	}
	
	public ArrayList<OrderDetail> getOrderDetail(int user_id)
	{
		 JSONArray jsonOrderDetail = null;
		 ArrayList<OrderDetail> orderdetail = new ArrayList<OrderDetail>();
		 
		 Param paramPost = new Param();
		 paramPost.addParam("action", Integer.toString(Constans.GET_ORDERS_AND_DETAIL));
		 paramPost.addParam("user_id", Integer.toString(user_id));
		 
		 try 
		 { 
			 jsonOrderDetail = request.postData(app.get_IP_SERVER(), paramPost);   
			 
			 if(jsonOrderDetail != null){
				 int size = jsonOrderDetail.length() - 2;
			     for(int i = 0; i < jsonOrderDetail.length(); i++)
			     {
			        JSONObject m1 = jsonOrderDetail.getJSONObject(i);
			        int order_id = Integer.parseInt(m1.getString("order_id"));
					if(i == 0){
						OrderDetail o3 = new OrderDetail();
					    o3.setOrder_id(Integer.parseInt(m1.getString("order_id")));
					    o3.setOrder_total(Double.parseDouble(m1.getString("order_total")));
					    o3.setDining_table_id(Integer.parseInt(m1.getString("dining_table_id")));
					    o3.setUser_last_name(m1.getString("user_last_name"));
					    o3.setUser_first_name(m1.getString("user_first_name"));
					    o3.setHearder1(true);
					    orderdetail.add(o3);
					}
			        if(i <= size)
					{
						JSONObject m2 = jsonOrderDetail.getJSONObject(i+1);
						int order_id2 = Integer.parseInt(m2.getString("order_id"));
						if(order_id != order_id2){
							OrderDetail o = new OrderDetail();
					        o.setOrder_id(Integer.parseInt(m1.getString("order_id")));
					        o.setOrder_total(Double.parseDouble(m1.getString("order_total")));
					        o.setDining_table_id(Integer.parseInt(m1.getString("dining_table_id")));
					        o.setUser_last_name(m1.getString("user_last_name"));
					        o.setUser_first_name(m1.getString("user_first_name"));
					        o.setOrder_detail_id(Integer.parseInt(m1.getString("order_detail_id")));
					        o.setQuantity(Integer.parseInt(m1.getString("quantity")));
					        o.setPrice(Double.parseDouble(m1.getString("price")));
					        o.setSubtotal(Double.parseDouble(m1.getString("subtotal")));
					        o.setAddon_id(Integer.parseInt(m1.getString("addon_id")));
					        o.setAddon_description(m1.getString("addon_description"));
					        o.setMenu_item_categories_id(Integer.parseInt(m1.getString("menu_item_categories_id")));
					        o.setMenu_item_id(Integer.parseInt(m1.getString("menu_item_id")));
					        o.setMenu_item_name(m1.getString("menu_item_name"));
					        orderdetail.add(o);
					        
					        OrderDetail o2 = new OrderDetail();
					        o2.setOrder_id(Integer.parseInt(m1.getString("order_id")));
					        o2.setOrder_total(Double.parseDouble(m1.getString("order_total")));
					        o2.setDining_table_id(Integer.parseInt(m1.getString("dining_table_id")));
					        o2.setUser_last_name(m1.getString("user_last_name"));
					        o2.setUser_first_name(m1.getString("user_first_name"));
							o2.setHearder2(true);
							orderdetail.add(o2);
							 
							OrderDetail o3 = new OrderDetail();
						    o3.setOrder_id(Integer.parseInt(m2.getString("order_id")));
						    o3.setOrder_total(Double.parseDouble(m2.getString("order_total")));
						    o3.setDining_table_id(Integer.parseInt(m2.getString("dining_table_id")));
						    o3.setUser_last_name(m2.getString("user_last_name"));
						    o3.setUser_first_name(m2.getString("user_first_name"));
						    o3.setHearder1(true);
							orderdetail.add(o3);
								 
						}else{
							OrderDetail o = new OrderDetail();
					        o.setOrder_id(Integer.parseInt(m1.getString("order_id")));
					        o.setOrder_total(Double.parseDouble(m1.getString("order_total")));
					        o.setDining_table_id(Integer.parseInt(m1.getString("dining_table_id")));
					        o.setUser_last_name(m1.getString("user_last_name"));
					        o.setUser_first_name(m1.getString("user_first_name"));
					        o.setOrder_detail_id(Integer.parseInt(m1.getString("order_detail_id")));
					        o.setQuantity(Integer.parseInt(m1.getString("quantity")));
					        o.setPrice(Double.parseDouble(m1.getString("price")));
					        o.setSubtotal(Double.parseDouble(m1.getString("subtotal")));
					        o.setAddon_id(Integer.parseInt(m1.getString("addon_id")));
					        o.setAddon_description(m1.getString("addon_description"));
					        o.setMenu_item_categories_id(Integer.parseInt(m1.getString("menu_item_categories_id")));
					        o.setMenu_item_id(Integer.parseInt(m1.getString("menu_item_id")));
					        o.setMenu_item_name(m1.getString("menu_item_name"));
					        orderdetail.add(o);
						}
					}
					if(i == size + 1){
				        OrderDetail o = new OrderDetail();
				        o.setOrder_id(Integer.parseInt(m1.getString("order_id")));
				        o.setOrder_total(Double.parseDouble(m1.getString("order_total")));
				        o.setDining_table_id(Integer.parseInt(m1.getString("dining_table_id")));
				        o.setUser_last_name(m1.getString("user_last_name"));
				        o.setUser_first_name(m1.getString("user_first_name"));
				        o.setOrder_detail_id(Integer.parseInt(m1.getString("order_detail_id")));
				        o.setQuantity(Integer.parseInt(m1.getString("quantity")));
				        o.setPrice(Double.parseDouble(m1.getString("price")));
				        o.setSubtotal(Double.parseDouble(m1.getString("subtotal")));
				        o.setAddon_id(Integer.parseInt(m1.getString("addon_id")));
				        o.setAddon_description(m1.getString("addon_description"));
				        o.setMenu_item_categories_id(Integer.parseInt(m1.getString("menu_item_categories_id")));
				        o.setMenu_item_id(Integer.parseInt(m1.getString("menu_item_id")));
				        o.setMenu_item_name(m1.getString("menu_item_name"));
				        orderdetail.add(o);
				        
				        OrderDetail o3 = new OrderDetail();
					    o3.setOrder_id(Integer.parseInt(m1.getString("order_id")));
					    o3.setOrder_total(Double.parseDouble(m1.getString("order_total")));
					    o3.setDining_table_id(Integer.parseInt(m1.getString("dining_table_id")));
					    o3.setUser_last_name(m1.getString("user_last_name"));
					    o3.setUser_first_name(m1.getString("user_first_name"));
					    o3.setHearder2(true);
						orderdetail.add(o3);
					}
			       
			        //Log.d("outPut", "cantidad de comandas: " + String.valueOf(m.getInt("cantidad")));	 
			    }
			 }
	     } catch (JSONException e) {
		    e.printStackTrace();
		 }		 
		 
		 return orderdetail;
	}	
	
	private OnPopupInterface onPopupInterface;
	
	public interface OnPopupInterface {		
		public void popupShowed();
		public void popupClosed(int id);
	}
	
	public void setOnPopupInterfaceListener (OnPopupInterface pop){
		this.onPopupInterface = pop;	
	}
	
	
}
