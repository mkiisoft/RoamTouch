package com.roamtouch.menuserver.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Parser {
	
	private int action;
	private Vector data;	
	private Hashtable table;
	private String address;
	private long pid;
	private String user_agent; 

	public Parser(String msg, String adress, long pid, String agent){
		
		this.address = adress;
		this.pid = pid;
		this.user_agent = agent;
		
		String params = msg.toString();	
		Log.d("PARSER", msg.toString());
		params = params.replaceFirst("\\{", "");
		params = params.replaceFirst("\\}", "");		
				
		String[] paramsArray = params.split(", ");
		
		int length = paramsArray.length;	
		int actionPosition; 
		
		String actionRemove = null;
		for (int i=0; i<length; i++){
			if (paramsArray[i].contains("action=")){
				actionRemove = paramsArray[i];
				if (paramsArray[i].contains("edit_action=")) {			
					action = Integer.parseInt(paramsArray[i].replaceFirst("edit_action=", ""));
				} else if (paramsArray[i].contains("action=")){
					action = Integer.parseInt(paramsArray[i].replaceFirst("action=", ""));
				}
			}
		}		
		
		if (length>=1){
			
			actionRemove += ", ";
			msg = msg.replaceFirst(actionRemove, ""); 
			
			String pWithout = msg.toString();	
			
			pWithout = pWithout.replaceAll("\\{", "");
			pWithout = pWithout.replaceAll("\\}", "");
			//pWithout = replaceLast(pWithout, "}", "");
			
			//REMUEVO COMAS INTERNAR POR BUG.
			if (pWithout.contains(",")){
				pWithout = pWithout.replaceAll(", ", "@ ");
				if (pWithout.contains(":")){
					pWithout = pWithout.replaceAll(",", "|");
				}
				pWithout = pWithout.replaceAll("@ ", ", ");
			}
			
			String[] pWithoutArray = pWithout.split(", ");
					
			try {
				
				table = parseParams(pWithoutArray.length, pWithoutArray);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
				
			}			
		}
	}
	
	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public static String replaceLast(String string, String toReplace, String replacement) {
	    int pos = string.lastIndexOf(toReplace);
	    if (pos > -1) {
	        return string.substring(0, pos)
	             + replacement
	             + string.substring(pos + toReplace.length(), string.length());
	    } else {
	        return string;
	    }
	}
	
	/**
	 * parseParams
	 * @param lenth
	 * @param data
	 * @return 
	 * @throws JSONException 
	 * */	
	private Hashtable parseParams(int lenth, String[] data) throws JSONException{
		Hashtable hash = new Hashtable();
		if (lenth>=1){		
			int countParams = 0;	
			for (int j=0; j<lenth; j++){			 		
				String[] sep = data[j].split("=");				
				String[] ver = null;
				if (sep.length>1){
					String values = sep[1].toString(); 
					if (values.contains("|")){
						ver = sep[1].split("\\|");
					}	
					if (values.contains(",") && !sep[0].equals("item_description") && !sep[0].equals("edit_item_description")){ //HACK HORRIBLE, REPLANTEAR
						ver = sep[1].split(",");
					}	
				}
				if (ver!=null){							
					String id = sep[0];								
					Vector ob = new Vector();					
					for (int k=0; k<ver.length; k++){
						ob.add(ver[k]);
					}					
					hash.put(id, ob);
				} else {
					if (sep.length>1){
						hash.put(sep[0], sep[1]);
					} else {
						hash.put(sep[0], "");
					}
				}							 
			}
		} 		
		return hash;		
	}	
	
	public int getAction() {
		return action;
	}
	
	public void setParamById(String key, String param){
		table.put(key, param);
	}

	public Object getByDescId(String id){
		return (Object) table.get(id);
	}
	
	public boolean getLengthDescById(String key){
		
		boolean ret = false;
		
		Enumeration keys = table.keys();
		
		while(keys.hasMoreElements()){
			String k = (String) keys.nextElement();
			if (k.contains(key)){
				String ver = (String) table.get(key);
				if (ver.equals("")){
					ret = false;
				} else {
					ret = true;
				}
			}				
		}	
		
		return ret;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}
	
}
