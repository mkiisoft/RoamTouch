package com.roamtouch.menuserver.communications;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.utils.FolderHandler;

import android.util.Log;
import net.tootallnate.websocket.WebSocketServer;

public class WebSocket extends WebSocketServer {
	
	private static DataBaseController database;
	private APIProtocol wsProtocol;
	private MenuServer cM;
	
	private net.tootallnate.websocket.WebSocket CLIENTE_WEB;
	private net.tootallnate.websocket.WebSocket CLIENTE_TABLET;

	public WebSocket(int port, DataBaseController db, MenuServer cM, FolderHandler fH){
		super(port);	
		database = db;
		this.cM = cM; 
		wsProtocol = new APIProtocol(db, cM, fH, this);
		start();				
	}
	
	@Override
	public void onClientOpen(net.tootallnate.websocket.WebSocket conn) {
		// TODO Auto-generated method stub		
		Log.v("websocket","Open: " + conn); 
	}

	@Override
	public void onClientClose(net.tootallnate.websocket.WebSocket conn) {
		// TODO Auto-generated method stub
		Log.v("websocket","Closed: " + conn); 
		
	}

	@Override
	public void onClientMessage(net.tootallnate.websocket.WebSocket conn, String msg) {
		
		if (msg.equals("web-client-discovery")){
			CLIENTE_WEB = (net.tootallnate.websocket.WebSocket) conn;
		} else if (msg.equals("tablet-client-discovery")){
			CLIENTE_TABLET = (net.tootallnate.websocket.WebSocket) conn;
		}
		
		/*JSONArray json = new JSONArray();
		try {
			json = wsProtocol.parseParams(msg);
		} catch (JSONException e) {
			Log.v("error",""+e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.v("error",""+e);
			e.printStackTrace();
		}		
		
		if (conn!=null){			
			try {
				conn.send(json.toString());
			} catch (IOException e) {
				Log.v("error",""+e);
				e.printStackTrace();
			}
		}*/
		
	}

	@Override
	public void onError(Throwable ex) {
		// TODO Auto-generated method stub
		Log.v("websocket","Error: " + ex);
	}
	
	public void sendToAll(String msg){
		this.sendToAll(msg);
	}

}
