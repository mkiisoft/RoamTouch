package com.roamtouch.menuserver.adapters;

import java.util.ArrayList;

import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.entities.DiningTables;
import com.roamtouch.menuserver.entities.User;
import com.roamtouch.menuserver.requests.RequestOrderDetail;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	    	    
	    ArrayList<ArrayList<DiningTables>> tablesWaiter;
	    ArrayList<User> waiters;
	    GridView grid;
	    	    
	    Context context;
	    Activity activity;
	    
	    @Override
	    public Object getChild(int groupPosition, int childPosition)
	    {	        
	        return null;
	    }
	    
	    public ExpandableListAdapter(Activity activity, ArrayList<User> waiters, ArrayList<ArrayList<DiningTables>> tablesWaiter){
	    	
	    	this.context = activity;
	    	this.activity = activity;
	    	
	    	this.tablesWaiter = tablesWaiter;
	    	this.waiters = waiters;
	   }

	    @Override
	    public long getChildId(int groupPosition, int childPosition)
	    {
	        return childPosition;
	    }  
	    

	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	    {
	    	LinearLayout item = (LinearLayout) getViewGroupChild(convertView, parent);
	        grid = (GridView) item.findViewById(R.id.waitersGridView);    
	        
	       User user = this.waiters.get(groupPosition);
	        
	        ArrayList<DiningTables> tables;
	        
	        tables = (ArrayList<DiningTables>) tablesWaiter.get(groupPosition);
	        GridAdapter gridviewAdapter = new GridAdapter(activity, tables,  groupPosition+1, grid, user); 
	        
	        grid.setAdapter(gridviewAdapter);
	        
	        return item;
	    }

	    private ViewGroup getViewGroupChild(View convertView, ViewGroup parent)
	    {
	        // The parent will be our ListView from the ListActivity
	        if (convertView instanceof ViewGroup)
	        {
	            return (ViewGroup) convertView;
	        }
	        Context context = parent.getContext();
	        LayoutInflater inflater = LayoutInflater.from(context);
	        LinearLayout item = (LinearLayout) inflater.inflate(R.layout.tables_layout_per_waiter, null);
	        return item;
	    }

	    @Override
	    public int getChildrenCount(int groupPosition)
	    {
	        return 1;
	    }

	    @Override
	    public Object getGroup(int groupPosition)
	    {
	        return tablesWaiter.get(groupPosition); // catg[groupPosition];
	    }

	    @Override
	    public int getGroupCount()
	    {
	    	int count = tablesWaiter.size();//-1; 
	        return count;
	    }

	    @Override
	    public long getGroupId(int groupPosition)
	    {
	        return groupPosition;
	    }


	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	    {       
	        
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.tables_waiter_group_item,
	                    null);
	        }
	        
	        TextView waiter = (TextView) convertView.findViewById(R.id.waiter);
	        waiter.setTypeface(null, Typeface.BOLD);
	                
	        User user = waiters.get(groupPosition);
	        
	        String name = user.getFirst_name() + " " + waiters.get(groupPosition).getLast_name();
	        waiter.setText(name);
	        
	        Button order_detail = (Button) convertView.findViewById(R.id.order_detail);
	        order_detail.setTag(groupPosition);
	        order_detail.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {                	
                	int id = (Integer) v.getTag();        	
                	new RequestOrderDetail(activity).execute(waiters.get(id).get_id());       	
                }
            });
	        
	        return convertView;
	        
	    }

	    private View getViewGroupGroup(View convertView, ViewGroup parent)
	    {
	        // The parent will be our ListView from the ListActivity
	        if (convertView instanceof View)
	        {
	            return (View) convertView;
	        }
	        Context context = parent.getContext();
	        LayoutInflater inflater = LayoutInflater.from(context);
	        View item1 = (View) inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);

	        return item1;
	    }

	    @Override
	    public boolean hasStableIds()
	    {	        
	        return false;
	    }

	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition)
	    {	     
	        return true;
	    }

}
