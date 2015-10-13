package com.roamtouch.menuserver.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import android.text.format.Time;
import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.database.DataBaseHelper;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.FileUtils;
import com.roamtouch.menuserver.utils.FolderHandler;

public class BackUpData {
	
	private MenuServerApplication app;
	private DBBackUp dbB;
	private ZipUtility zip;
	private MenuServer cM;
	private FolderHandler fH;
	private FileUtils fU;
	
	public BackUpData(MenuServer cM, FolderHandler fH){
		this.cM = cM;
		this.dbB = new DBBackUp(cM);
		zip = new ZipUtility();
		this.fH = fH;
		this.fU = new FileUtils();
		app = ((MenuServerApplication)cM.getApplication());
	}
	
	/**
	 * backUpAllData
	 * @returnEx
	 * @throws IOException
	 */
	public String backUpAllData() throws IOException {
		
		//Session time
		String session = cM.getSessionTime();
	
		//Generate root directory name
		Timestamp time = cM.getCurrentTimeStamp();
		String[] splitTime = time.toString().split(" "); 
		String newRootFolder = "backup_" + splitTime[0] + "_"+session;
		
		//Copy Database 
		File databaseBackedUpFile = dbB.backupDatabase();
		
		//Copy media directory;
		File media = new File(app.getSDCARD() + "/httpd/media/");
		
		String rootFolder = databaseBackedUpFile.getParentFile().getName();
		String completePath = app.getSDCARD() +"/httpd/backup/" + rootFolder + "/";
		
		File destination = new File(completePath);
		
		fH.copyDirectory(media, destination);		
		
		File completeFile = new File(completePath);	
		
		String zipName = "MenuServer_Backup_" + splitTime[0]+ "_" + session + ".zip";
		
		File zipFile = new File( app.getSDCARD() + "/httpd/backup/" + zipName);
		zip.zipDirectory(completeFile, zipFile);		
		
		return zipName;		
		
	}
	
	
	/**
	 * restoreAllData
	 * @param zipFile
	 * @return
	 * @throws IOException
	 */
	public int restoreAllData(File zipFile) 
	{
		
		int restored = Constans.RESTORED_NONE;
		
		File temporary = restoreMediaFromZip ( zipFile );
		
		if ( temporary != null ) 
		{
		
			File dbTemporary = new File( temporary.toString() + "/" + DataBaseHelper.DB_NAME );
			
			if ( dbTemporary.exists() ) 
			{
					
				if ( restoreDatabaseFromFolder ( temporary ) ) 
				{
					
					fU.deleteDirectory ( temporary );
					
					restored = Constans.RESTORED_MEDIA_AND_DATABASE;
					
				} else 
				{
					restored = Constans.RESTORED_MEDIA;
				}
				
			} else {
				
				restored = Constans.RESTORED_MEDIA;
				
			}
			
			
		} else {
			
			restored = Constans.RESTORED_NONE;
		}
		
		return restored;
		
	}	
	
	/**
	 * restoreMediaFromZip
	 * @param zipFile
	 * @return
	 */
	public File restoreMediaFromZip(File zipFile){
		
		String sipFileName = zipFile.getName(); 
		sipFileName = fH.removeExtention(sipFileName);
		
		//Copy media directory;
		File temporary = new File( app.getSDCARD() + "/httpd/backup/temporary/" + sipFileName ); //+ "/");
		
		try 
		{		
			//Unzip
			if (zip.unzip(zipFile, temporary)){		
			
				//rename media
				File media = new File(app.getSDCARD() + "/httpd/media/");
				File media_backup = new File(app.getSDCARD() + "/httpd/media_backup/");				
						
				//New temporary on temporaty. 
				File zipped_temporat = new File ( temporary.toString() );	
				
				File origin = new File(zipped_temporat.toString()+"/menus"); 		
				File destination = new File(app.getSDCARD() + "/httpd/media/menus");
				destination.mkdirs();	
					
				try {
					
					if ( fH.copyDirectory (origin, destination) )
					{				
						//Delete media;						
						fU.deleteDirectory(media_backup);
						return temporary;				
					}
					
				} catch (IOException e) 
				{			
					e.printStackTrace();
					return null;
				}		
			
			} else {
				
				return null;
				
			}
			
		} catch (Exception e) 
		{			
			e.printStackTrace();
			return null;
		}	
			
		
		return null;
		
		
	}
	
	public boolean restoreDatabaseFromFolder(File zipped_temporat)
	{
     	 
		boolean res = false;
		
		 //Restore database
		 if (dbB.restoreDataBase(zipped_temporat.toString())){		
		
			 //Delete database;
			 File database = dbB.getCurrentDatabaseFile();
			 if (database.delete())
			 {
				 res = true;
			 }
			 
		 }	
		 
		return res; 		
	}
	
	
	/**
	 * importImagesFromZip
	 * @param zipFile
	 * @param menu_folder
	 * @return
	 * @throws IOException
	 */
	public boolean importImagesFromZip(File zipFile, String menu_folder) throws IOException{
		
		String sipFileName = zipFile.getName(); 
		sipFileName = fH.removeExtention(sipFileName);
		
		//Copy media directory;
		File temporary = new File( app.getSDCARD() + "/httpd/backup/temporary/" + sipFileName ); //+ "/");
			
		//Unzip
		zip.unzip(zipFile, temporary);		
		
		return true;
	}	
	
	/**
	 * backDatabase
	 * @returnEx
	 * @throws IOException
	 */
	public File backDatabase() throws IOException {
		
		//Session time
		String session = cM.getSessionTime();
		
		//Copy Database 
		File backUpDir = dbB.backupDatabase();
			
		String rootFolder = backUpDir.getParentFile().getName();
		String completePath = app.getSDCARD() +"/httpd/backup/originaldb/" + rootFolder + "/";			
		
		File original = new File(completePath + "/" +  DataBaseHelper.DB_NAME);	
		
		String newName = fU.removeExtention(DataBaseHelper.DB_NAME);
		
		File backedUpDb = new File(completePath + "/" + newName + "_" + rootFolder + ".db");
		
		try 
		{
			
			if (original.renameTo(backedUpDb)){	
				return backedUpDb;		
			} else {
				return null;
			}
		
		
		} catch (Exception e) 
		{			
			e.printStackTrace();
			return null;
		}	
	
	}
	
	
	/**
	 * restoreDatabaseFromZip
	 * @param zipFile
	 * @return
	 * @throws IOException
	 */
	public boolean restoreDatabaseFromZip(File dbFile) throws IOException{
		
		boolean res = false;	
						
		//Copy media directory;
		File tempDb = new File( app.getSDCARD() + "/httpd/backup/temporary/" + dbFile.getName() ); //+ "/");
		
		if (fU.copyFile(dbFile, tempDb)){

			//Get database;
			File database = dbB.getCurrentDatabaseFile();	
	
			//backup original.
			File backUpDir = dbB.backupDatabase();
			
			// si no pude hacer backup no borro el original
			if (backUpDir.exists()){
				
				//Borrar base original bacapeada.  
				if (database.delete()){
					
					//Restore database
					boolean restore = dbB.restoreDataBase(tempDb.getParent().toString());				
					
					if (restore){		
						
						fU.deleteDirectory(tempDb);
						
						res = true;
						
					} else {
						
						res = false;
						
					}		
				
				} else {
					
					res = false;
					
				}	
				
			} else {
				
				res = false;
				
			}
			
		}
		
		return res;
	}	
	
}
