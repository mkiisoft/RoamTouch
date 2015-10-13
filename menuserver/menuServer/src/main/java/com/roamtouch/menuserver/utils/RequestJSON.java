package com.roamtouch.menuserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.roamtouch.menuserver.shared.Constans;

import android.util.Log;

public class RequestJSON {
	
	public JSONArray postData(String url, Param param) {
		
		JSONArray json = null;
		
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    String url_port = "http://" + url + ":" + Constans.WEB_SERVER_PORT + "/";
	    HttpPost httppost = new HttpPost(url_port);	   
	   
	    try {

	    	List<NameValuePair> nameValuePairs = setPOSTParams(param);
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        HttpEntity httpEntity = response.getEntity();

	        if (httpEntity != null) {
	            InputStream is = httpEntity.getContent();
	            String result = StringUtils.convertStreamToString(is);
	            try {
	            	json = new JSONArray(result);
				} catch (JSONException e) {
					Log.v("error", "error: " + e);
					e.printStackTrace();
				}
	            Log.v("result", "Result: " + result);
	        }

		    httpclient.getConnectionManager().shutdown(); 
	        
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    return json;
	} 


	private JSONArray getData(String url, Param params){		
		  
		  JSONArray json = new JSONArray();
		 
		  try {
		    
			HttpClient httpclient = new DefaultHttpClient();  	
			
			 url = "http://" + url + ":" + Constans.WEB_SERVER_PORT;
			 url += "?";
			 
		    String finalGetURL = setHttpParams(url, params);	 
		  
		    HttpResponse response = httpclient.execute(new HttpGet(finalGetURL));      
		    
		    HttpEntity httpEntity = response.getEntity();

	        if (httpEntity != null) {
	            InputStream is = httpEntity.getContent();
	            String result = StringUtils.convertStreamToString(is);
	            try {
	            	json = new JSONArray(result);
				} catch (JSONException e) {
					Log.v("error", "error: " + e);
					e.printStackTrace();
				}
	            Log.v("result", "Result: " + result);
	        }

		    httpclient.getConnectionManager().shutdown(); 
		    
		    
		  } catch (Exception e) {
		    Log.v("error", "Network exception", e);
		  }
		    return json;
		}

	/*private void doHttpPost(String path) throws Exception{

		String url = discoveredIp;	
		
		String[] pathArray = path.split("/");
		int pathLength = pathArray.length;
		String pathPath = "/";
		
		for(int i=0; i<pathLength-1; i++){
			pathPath += pathArray[i] + "/";
		}
		
		String fileName = pathArray[pathLength-1];		
		
	    HttpParams httpParameters = new BasicHttpParams();
	
	    // Set the timeout in milliseconds until a connection is established.
	    int timeoutConnection = 9000000;
	    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
	    
	    // Set the default socket timeout (SO_TIMEOUT) 
	    // in milliseconds which is the timeout for waiting for data.
	    
	    int timeoutSocket = 9000000;
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	
	    HttpClient client = new DefaultHttpClient(httpParameters);
	
	    client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
	
	    HttpPost postRequest = new HttpPost();
	    postRequest.setURI(new URI("http://" + url + ":5556/"));
	
	    MultipartEntity multiPartEntity = new MultipartEntity();
	    multiPartEntity.addPart(fileName, new FileBody(new File(path)));
	    postRequest.setEntity(multiPartEntity);
	
	    long before = TrafficStats.getTotalTxBytes();
	    long start = System.currentTimeMillis();
	    HttpResponse response = client.execute(postRequest);
	    long end = System.currentTimeMillis(); 
	    long after = TrafficStats.getTotalTxBytes();
	
	    Log.v("http", "HTTP Post Execution took " + (end - start) + " ms.");
	
	    if( before != TrafficStats.UNSUPPORTED && after != TrafficStats.UNSUPPORTED)
	        Log.v("http", (after-before) + " bytes transmitted to the server");
	    else
	        Log.v("http", "This device doesnot support Network Traffic Stats");
	
	    HttpEntity responseEntity = response.getEntity();
	
	    if (responseEntity != null) {
	        responseEntity.consumeContent();
	        Log.v("http", "HTTP Post Response " + response.getEntity().getContent().toString() );
	    }
	
	    client.getConnectionManager().shutdown(); 
	
	}*/
		
		private String setHttpParams(String url, Param param){	
			
			for (int i=0; i<param.getAmount(); i++){
				
				Vector v =  (Vector) param.get(i);
				
				String action = (String) v.get(0);
				String value = (String) v.get(1);
				
				if (i>0)
					url += "&";		
				
				url += action+"="+value;			
			}		
			return url;		 
		}
		
		private List<NameValuePair> setPOSTParams(Param param){
			
			List<NameValuePair> nameValuePairs = null;
			nameValuePairs = new ArrayList<NameValuePair>(param.getAmount());
			
			for (int i=0;i<param.getAmount(); i++){
				
				Vector v =  (Vector) param.get(i);
				
				String action = (String) v.get(0);
				String value = (String) v.get(1);
				
				
				nameValuePairs.add(new BasicNameValuePair(action, value));	
			}
		
			return nameValuePairs;
			
		}

}
