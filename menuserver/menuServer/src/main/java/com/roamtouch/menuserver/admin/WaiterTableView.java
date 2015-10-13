package com.roamtouch.menuserver.admin;

import java.util.Set;

import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.R;
import com.roamtouch.menuserver.adapters.GridAdapter;
import com.roamtouch.menuserver.entities.DiningTables;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WaiterTableView extends RelativeLayout {

	DiningTables diningTable;
	MenuServerApplication app;
	int imageResource;
	ImageView imageView;
	WaiterTableView table;	
	private Animation blinkanimation;
	
	Handler handler = new Handler(); 
	
	int count=0;
    boolean flash = true;
	

	public DiningTables getDiningTable() {
		return diningTable;
	}

	public void setDiningTable(DiningTables diningTable) {
		this.diningTable = diningTable;
	}

	
	public WaiterTableView(Context context) {
        super(context);
         init(context); 
    }
    public WaiterTableView(Context context, AttributeSet attrs, int defStyle)
    {
    	super(context, attrs, defStyle);    
    	init(context); 
    }
    public WaiterTableView(Context context, AttributeSet attrs) {
    	super(context, attrs);    
    	init(context); 
    }
    
    private void init(Context context){  	
    	table = this;    	
    }

    public Animation getBlinkanimation() {
		return blinkanimation;
	}
	public void setBlinkanimation(Animation blinkanimation) {
		this.blinkanimation = blinkanimation;
	}

}
