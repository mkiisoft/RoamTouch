package com.roamtouch.menuserver.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.database.DataBaseHelper;
import com.roamtouch.menuserver.utils.FileUtils;

import android.content.Context;
import android.util.Log;

public class DBBackUp {

	private static final String DATABASE_NAME = DataBaseHelper.getDB_NAME();
	
	private MenuServerApplication app;
	private Context mContext;
	
	private MenuServer cM;
	
	private String databasePath;
	
	public DBBackUp(Context mC){		
		mContext = mC;
		cM = (MenuServer) mC;
		databasePath = "/data/data/" + mContext.getPackageName() + "/databases/";
		app = ((MenuServerApplication)cM.getApplication());
	}
	
	public File getCurrentDatabaseFile() {
	    return new File(databasePath, DATABASE_NAME);
	}
	
	public File backupDatabase(){
		
		File from = this.getCurrentDatabaseFile();
		
		//Session time
		String session = cM.getSessionTime();
				
		//Generate root directory name
		Timestamp time = cM.getCurrentTimeStamp();
		String[] splitTime = time.toString().split(" "); 
		String newRootFolder = "backup_" + splitTime[0] + "_"+session;
		
	    File to = this.getBackupDatabaseFile(newRootFolder);
	    
	    try {
	        FileUtils.copyFile(from, to);	       
	    } catch (Exception e) {	        
	       Log.v("error", "Error backuping up database: " + e.getMessage(), e);
	    }	     
	    return to;	    
	}
	
	public File getBackupDatabaseFile(String databaseDirectory) {	
		
		String path = app.getSDCARD() +"/httpd/backup/originaldb/" + databaseDirectory;		
	    File folder = new File( path );
	    
	    boolean success = true;
	    if (!folder.exists()) {
	        success = folder.mkdir();
	    }	  
	    if (success)
	    	return new File(folder, DATABASE_NAME);
	    
		return null;
	}
	
	public static void copyFile(File src, File dst) throws IOException {
	    FileInputStream in = new FileInputStream(src);
	    FileOutputStream out = new FileOutputStream(dst);
	    FileChannel fromChannel = null, toChannel = null;
	    try {
	        fromChannel = in.getChannel();
	        toChannel = out.getChannel();
	        fromChannel.transferTo(0, fromChannel.size(), toChannel); 
	    } finally {
	        if (fromChannel != null) 
	            fromChannel.close();
	        if (toChannel != null) 
	            toChannel.close();
	    }
	}
	
	public boolean restoreDataBase(String database){
		
		boolean res;
		
		try 
		{
			
            OutputStream newOutput;   
            newOutput = new FileOutputStream( databasePath + DATABASE_NAME);                     
                       
            InputStream backDataInput = null;
            
            File restored_database = new File(database + "/" + DATABASE_NAME);
            
            if (restored_database.exists()){
            	backDataInput = new FileInputStream(restored_database); 
            } 


            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = backDataInput.read(buffer))>0)
            {
            	newOutput.write(buffer, 0, length);
            }


            // Close and clear the streams
            newOutput.flush();

            newOutput.close();

            newOutput.close();   
           
            res=true;
            
		}
		
		catch (Exception e) {	        
		       Log.v("error", "Error restoring database: " + e.getMessage(), e);
		       res=false;
		}
      return res;
		
	}
	
}
