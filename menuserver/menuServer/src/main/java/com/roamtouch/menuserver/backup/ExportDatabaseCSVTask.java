package com.roamtouch.menuserver.backup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.roamtouch.menuserver.database.DataBaseController;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>

{

	Context context;
	DBBackUp backup;
	SQLiteDatabase database;
	
	ExportDatabaseCSVTask (Context context, SQLiteDatabase database){
		this.context = context;
		backup = new DBBackUp(context);
		this.database = database; 
	}
	
	private final ProgressDialog dialog = new ProgressDialog(this.context);

	@Override
	protected void onPreExecute()
	{
	
	    this.dialog.setMessage("Exporting database...");
	
	    this.dialog.show();
	
	}
	


	protected Boolean doInBackground(final String... args)
	
	{
	
	
	    File dbFile = backup.getCurrentDatabaseFile(); //getDatabasePath("database_name");
	    
	    //AABDatabaseManager dbhelper = new AABDatabaseManager(getApplicationContext());
	    //AABDatabaseManager dbhelper = new AABDatabaseManager(this.context) ;
	    //System.out.println(dbFile);  // displays the data base path in your logcat 
	
	
	    File exportDir = new File(Environment.getExternalStorageDirectory(), "");        
	
	    if (!exportDir.exists()) 
	
	    {
	        exportDir.mkdirs();
	    }
	
	
	    File file = new File(exportDir, "excerDB.csv");
	
	
	    try
	
	    {
	
	        if (file.createNewFile()){
	            System.out.println("File is created!");
	            System.out.println("myfile.csv "+file.getAbsolutePath());
	          }else{
	            System.out.println("File already exists.");
	          }
	
	        CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
	      //SQLiteDatabase db = dbhelper.getWritableDatabase();
	
	        Cursor curCSV = database.rawQuery("select * from menu_item",null);		// + db.TABLE_NAME, null);
	
	        csvWrite.writeNext(curCSV.getColumnNames());
	
	        while(curCSV.moveToNext())
	
	        {
	
	            String arrStr[] ={curCSV.getString(0),curCSV.getString(1),curCSV.getString(2)};
	
	         /*curCSV.getString(3),curCSV.getString(4)};*/
	
	            csvWrite.writeNext(arrStr);
	
	
	        }
	
	        csvWrite.close();
	        curCSV.close();
	        /*String data="";
	        data=readSavedData();
	        data= data.replace(",", ";");
	        writeData(data);*/
	
	        return true;
	
	    }
	
	    catch(SQLException sqlEx)
	
	    {
	
	        Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
	
	        return false;
	
	    }
	
	    catch (IOException e)
	
	    {
	
	        Log.e("MainActivity", e.getMessage(), e);
	
	        return false;
	
	    }
	
	}
	
	protected void onPostExecute(final Boolean success)
	
	{
	
	    if (this.dialog.isShowing())
	
	    {
	
	        this.dialog.dismiss();
	
	    }
	
	    if (success)
	
	    {
	
	        Toast.makeText(this.context, "Export succeed", Toast.LENGTH_SHORT).show();
	
	    }
	
	    else
	
	    {
	
	        Toast.makeText(this.context, "Export failed", Toast.LENGTH_SHORT).show();
	
	    }
	}
}
