package com.roamtouch.menuserver.adapters;

import java.util.ArrayList;

import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.entities.OrderDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterOrderDetail extends ArrayAdapter<OrderDetail>
{
    private static final int    TYPE_SECTION_HEADER1 = 0;
    private static final int    TYPE_SECTION_HEADER2 = 1;
    private static final int    TYPE_LIST_ITEM  = 2;

    Context mContext;
    ArrayList<OrderDetail> arrOrderDetail;
    LayoutInflater mInflater;

    public AdapterOrderDetail(Context context,ArrayList<OrderDetail> items)
    {
        super(context, R.layout.listitem_orderdetail, items);
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrOrderDetail = items;
    }

    @Override
    public OrderDetail getItem(int position)
    {
        return arrOrderDetail.get(position);
    }

    @Override
    public int getCount()
    {
        return arrOrderDetail.size();
    }

    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    @Override
    public int getItemViewType(int position)
    {
        OrderDetail item = arrOrderDetail.get(position);
        if (item.isHearder1())
        {
            return TYPE_SECTION_HEADER1;
        }
        else if(item.isHearder2())
        {
        	return TYPE_SECTION_HEADER2;
            
        }else{
        	return TYPE_LIST_ITEM;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        OrderDetail item = getItem(position);

        if (convertView == null)
        {
            if (item.isHearder1())
            {
                convertView = mInflater.inflate(R.layout.listitem_haeder1_orderdetail, null);
                holder = new ViewHolder();
                holder.tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
                holder.tvTableDescription = (TextView)convertView.findViewById(R.id.tvTableDescription);
                holder.tvOrderTotal = null;
                holder.tvQuantity = null;
                holder.tvSubtotal = null;
                convertView.setTag(holder);
                
            }else if(item.isHearder2()){
            	convertView = mInflater.inflate(R.layout.listitem_header2_orderdetail, null);
                holder = new ViewHolder();
                holder.tvOrderTotal = (TextView)convertView.findViewById(R.id.tvOrderTotal);
                holder.tvUserName = null;
                holder.tvTableDescription = null;
                holder.tvQuantity = null;
                holder.tvSubtotal = null;
                convertView.setTag(holder);
            }
            else
            {
                convertView = mInflater.inflate(R.layout.listitem_orderdetail, null);
                holder = new ViewHolder();
                holder.tvItemName = (TextView)convertView.findViewById(R.id.tvItemName);
                holder.tvQuantity = (TextView)convertView.findViewById(R.id.tvQuantity);
                holder.tvSubtotal = (TextView)convertView.findViewById(R.id.tvSubtotal);
                holder.tvUserName = null;
                holder.tvTableDescription = null;
                holder.tvOrderTotal = null;
                convertView.setTag(holder);
            }
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        
        if (holder.tvUserName != null)
        {
            holder.tvUserName.setText(item.getUser_last_name() + " " + item.getUser_first_name());
            holder.tvTableDescription.setText("Mesa " + String.valueOf(item.getDining_table_id()));
        }
        if (holder.tvOrderTotal != null)
        {
        	holder.tvOrderTotal.setText("$ " + String.valueOf(item.getOrder_total()));
        }
        if (holder.tvItemName != null)
        {
        	holder.tvItemName.setText(item.getMenu_item_name());
        	holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        	holder.tvSubtotal.setText("$ " + String.valueOf(item.getSubtotal()));
        }
	        
        return convertView;
    }
    
    private class ViewHolder
    {
        public TextView tvUserName;
        public TextView tvTableDescription;
        public TextView tvOrderTotal;
        public TextView tvItemName;
        public TextView tvQuantity;
        public TextView tvSubtotal;
    }

}
