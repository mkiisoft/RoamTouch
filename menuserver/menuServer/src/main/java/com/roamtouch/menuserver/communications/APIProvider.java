package com.roamtouch.menuserver.communications;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.database.DataBaseHelper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class APIProvider extends ContentProvider {

	private static final String AUTHORITY = "com.roamtouch.menuserver.communications.APIProvider";
	public static final int TUTORIALS = 100;
	public static final int TUTORIAL_ID = 110;
	private static final String TUTORIALS_BASE_PATH = "tutorials";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TUTORIALS_BASE_PATH);	
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/mt-tutorial";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/mt-tutorial";
	
	DataBaseHelper mDB;
	
	private MenuServer cM;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, TUTORIALS_BASE_PATH, TUTORIALS);
	    sURIMatcher.addURI(AUTHORITY, TUTORIALS_BASE_PATH + "/#", TUTORIAL_ID);
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {		
		return 0;
	}

	@Override
	public String getType(Uri arg0) {	
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		return null;
	}

	@Override
	public boolean onCreate() {
		
		//mDB = new DatabaseHelper(getContext());
		
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	        String[] selectionArgs, String sortOrder) {
	   
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    /*queryBuilder.setTables(DataBaseHelper.TABLE_TUTORIALS);
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case TUTORIAL_ID:
	        queryBuilder.appendWhere(DataBaseHelper.ID + "="
	                + uri.getLastPathSegment());
	        break;
	    case TUTORIALS:
	        // no filter
	        break;
	    default:
	        throw new IllegalArgumentException("Unknown URI");
	    }*/
	    Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(),
	            projection, selection, selectionArgs, null, null, sortOrder);
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);	
	    return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
