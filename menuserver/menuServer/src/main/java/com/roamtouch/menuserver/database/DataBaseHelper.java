package com.roamtouch.menuserver.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
	
	//destination path (location) of our database on device
	private static String DB_PATH = ""; 
	public static String DB_NAME ="restomatic.db";// Database name
	
	// Update from dump
	static String DUMP_NAME = "restomatic_2015_02_24.sql";
	static String DUMP_DB_FILE = "db/" + DUMP_NAME;
	
	private static int VERSION = 2;
	
	private SQLiteDatabase mDataBase; 
	private final Context mContext;

	public DataBaseHelper(Context context) 
	{
	    super(context, DB_NAME, null, VERSION);// 1? its Database Version
	    
	    if(android.os.Build.VERSION.SDK_INT >= 17){
	       DB_PATH = context.getApplicationInfo().dataDir + "/databases/";         
	    }
	    else
	    {
	       DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
	    }
	    this.mContext = context;
	}   

	public boolean createDataBase() throws IOException
	{
		
		boolean res = false;
		
	    //If database not exists copy it from the assets
	    boolean mDataBaseExist = checkDataBase();
	    if(!mDataBaseExist)
	    {
	        this.getReadableDatabase();
	        this.close();
	        try 
	        {
	            //Copy the database from assests
	            copyDataBase();	            
	            res =  openDataBase();    
	            
	    	    //boolean sql = importaSQLFromFile(DUMP_DB_FILE);    	    
	    	    //if (sql)res=true; else res=false; 

	        } 
	        catch (IOException mIOException) 
	        {
	        	res=false;
	            throw new Error("ErrorCopyingDataBase");
	        }
	    }    
	    
	    return res;
	    
	}
	    //Check that the database exists here: /data/data/your package/databases/Da Name
	    private boolean checkDataBase()
	    {
	        File dbFile = new File(DB_PATH + DB_NAME);
	        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
	        return dbFile.exists();
	    }

	    //Copy the database from assets
	    private void copyDataBase() throws IOException
	    {
	        InputStream mInput = mContext.getAssets().open(DB_NAME);
	        String outFileName = DB_PATH + DB_NAME;
	        OutputStream mOutput = new FileOutputStream(outFileName);
	        byte[] mBuffer = new byte[4096];
	        int mLength;
	        while ((mLength = mInput.read(mBuffer))>0)
	        {
	            mOutput.write(mBuffer, 0, mLength);
	        }
	        mOutput.flush();
	        mOutput.close();
	        mInput.close();
	    }

	    //Open the database, so we can query it
	    public boolean openDataBase() throws SQLException
	    {
	        String mPath = DB_PATH + DB_NAME;
	        //Log.v("mPath", mPath);
	        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
	        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	        return mDataBase != null;
	    }

	    @Override
	    public synchronized void close() 
	    {
	        if(mDataBase != null)
	            mDataBase.close();
	        super.close();
	    }

		@Override
		public void onCreate(SQLiteDatabase db) {		
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			// Not Working
			//restoreSQLFile();
			
		}
		
		public static String getDB_NAME() {
			return DB_NAME;
		}
		
		 /**
         * This reads a file from the given Resource-Id and calls every line of it as a SQL-Statement
         * 
         * @param context
         *       
         * @return Number of SQL-Statements run
         * @throws IOException
         */
        public int restoreSQLFile() {
            
        	// Reseting Counter
            int result = 0;

            // Open the resource
            InputStream insertsStream = null;
            
    		try {
    			insertsStream = mContext.getAssets().open(DUMP_DB_FILE);
    		} catch (IOException e) {				
    			e.printStackTrace();
    		} 
            
    		BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

            // Iterate through lines (assuming each insert has its own line and theres no other stuff)
            try {
    			
            	while (insertReader.ready()) {
    			    String insertStmt = insertReader.readLine();
    			    getWritableDatabase().execSQL(insertStmt);
    			    result++;
    			}
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            try {
            	
    			insertReader.close();
    			
    		} catch (IOException e) {			
    			e.printStackTrace();
    		}


            return result;
        }     
        
        
        private boolean importaSQLFromFile(String fileName)
        {
        	boolean res;
            BufferedReader in = null;
            try
            {
                in = new BufferedReader(new InputStreamReader(mContext.getResources().getAssets().open(fileName)));
                String line;      
                while ((line = in.readLine()) != null) {
                	mDataBase.execSQL(line);                 
                }        
            }
            catch (final IOException e)
            {                
                res=false;
                e.printStackTrace();
            }
            finally
            {
                try
                {
                	res=true;
                    in.close();            
                }
                catch (IOException e)
                {
                	res=false;
                    // ignore //
                }
            }
            
            return res;
            
        }


}
