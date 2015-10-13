package com.roamtouch.menuserver.utils;

import java.util.Hashtable;
import java.util.Vector;

public class Param extends Hashtable {
		
	private int action;
	
	private int amount;
	
	public void addParam(String action, String value){		
	
		if (amount==0)
			this.action = Integer.parseInt(value);		
		
		Vector v = new Vector();
		v.add(action);
		v.add(value);
		
		super.put(amount, v);	
		
		amount++;
		
	}
	
	public int getAmount() {
		return amount;
	}

	public int getAction(){
		return action;
	}

	
	
}
