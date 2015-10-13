/*    
    Copyright (C) 2012  Nicola Amatucci

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package it.nicola_amatucci.android.discovery;

import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.roamtouch.menuserver.MenuServer;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class DiscoveryServer extends Thread
{
	public static String DEFAULT_TOKEN = "DISCOVERY_DEFAULT";
	public static int DEFAULT_PORT = 19876;
	public int port_to_share;
	String token;
	String nome;
	int port;
		
	String sentence;
	DatagramPacket receivePacket;
	
	MenuServer cM;
	
	Exception ex;
	
	private Handler handler;
	
	public DiscoveryServer(MenuServer cm, String nome, int port_to_share) throws InterruptedIOException
	{
		this (nome, port_to_share, DEFAULT_PORT, DEFAULT_TOKEN);
		this.cM =  cm;
		
	}
	
	public DiscoveryServer(MenuServer cm, String nome, int port_to_share, String token) throws InterruptedIOException
	{
		this (nome, port_to_share, DEFAULT_PORT, token);
		this.cM = cm;
		init();
	}
	
	public DiscoveryServer(MenuServer cm, String nome, int port_to_share, int port) throws InterruptedIOException
	{
		this (nome, port_to_share, port, DEFAULT_TOKEN);
		this.cM = cm;
		init();
	}	
	
	public DiscoveryServer(String nome, int port_to_share, int port, String token) throws InterruptedIOException
	{
		this.nome = nome;		
		this.token = token;
		this.port = port;
		this.port_to_share = port_to_share;
		this.start();
		init();
	}
	
	private void init(){
		handler = new Handler();
	}

	public void disconnect()
	{
		this.interrupt();
	}
	
	public void run() 
	{
		Log.v("DISCOVERY_SERVER", "SERVER STARTED");
		
		handler.post(new Runnable() {
			
		    public void run() {		    	    	
		    	
		    	cM.showToast("DISCOVERY SERVER STARTED", Toast.LENGTH_SHORT);
		    }
		 });	
		
		DatagramSocket serverSocket = null;
		
		try
		{
	        serverSocket = new DatagramSocket(port);
	        byte[] receiveData;
	        byte[] sendData;
			
	        while (this.isInterrupted() == false)
	        {
	        	receiveData = new byte[128];
	        	sendData = new byte[128];
	        	
	        	try
	        	{
	        		Log.v("DISCOVERY_SERVER", "LISTENING");  	
	        		
	        		handler.post(new Runnable() {
	        			
	        		    public void run() {		    	    	
	        		    	
	        		    	cM.showToast("LISTENING", Toast.LENGTH_SHORT);
	        		    }
	        		 });
	        		
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
			        serverSocket.receive(receivePacket);		           
			        
			        //Waited, received UDP.
			        sentence = new String( receivePacket.getData());
			        
			        if (sentence != null){	 	
			        	
			        	handler.post(new Runnable() {
		        			
		        		    public void run() {		    	    	
		        		    	
		        		    	cM.showToast("RECEIVED: " + sentence.substring(0, receivePacket.getLength()).trim(), Toast.LENGTH_SHORT);
		        		    }
		        		 });
			        	
			        	Log.v("DISCOVERY_SERVER", "RECEIVED: " + sentence.substring(0, receivePacket.getLength()).trim());
			        }
			        
			        
			        if (sentence != null && sentence.substring(0, receivePacket.getLength()).trim().equals(token))
			        {	    	
			        	Log.v("DISCOVERY_SERVER", "SEND '" + nome +"' to " + receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort());        
				        sendData = (nome + "," + port_to_share).getBytes();
				        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
				        serverSocket.send(sendPacket);
			        }
			        
	        	}
	        	catch (Exception e)
	        	{
	        		ex = e;
	        		ex.printStackTrace();
	        		
	        		handler.post(new Runnable() {
	        			
	        		    public void run() {		    	    	
	        		    	cM.showToast("ERROR: " + ex.toString(), Toast.LENGTH_SHORT);
	        		    }
	        		 });
	        		        		
	        		Log.e("DISCOVERY_SERVER", ex.toString());
	        	}
	        }
	    }   	
		catch(Exception e)
		{			
			ex = e;
    		ex.printStackTrace();
    		/*cM.runOnUiThread(new Runnable() {			
    			public void run() {				
    				cM.showToast("ERROR: " + ex.toString(), Toast.LENGTH_SHORT);
    			}
    		});*/			
			Log.e("DISCOVERY_SERVER", e.toString());
		}
		finally
		{
			try { 
				if (serverSocket != null) serverSocket.close(); 
			} catch(Exception e){				
				ex = e;
	    		ex.printStackTrace();
	    		/*cM.runOnUiThread(new Runnable() {			
	    			public void run() {				
	    				cM.showToast("FINALY ERROR: " + ex.toString(), Toast.LENGTH_SHORT);
	    			}
	    		});*/				
			}
		}		
		
	}
	
	
}
