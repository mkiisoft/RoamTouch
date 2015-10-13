package com.roamtouch.menuserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.roamtouch.menuserver.MenuServer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class ImageResize {

	private MenuServer cM;	
	private FileUtils fUtils;
	
	public ImageResize (MenuServer cM){		
		this.cM = cM;	
		fUtils = new FileUtils();
	}
	
	/**
	 * RESIZE_TO_FORMATS
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String[] RESIZE_TO_FORMATS (File file) throws IOException{
		
		String path = file.toString();		
		String filename = file.getName();
		String fileFolder = file.getParent();
		
		String[] res = new String[3];
		
		int[] dims = getImageSize(path);
				
		String small = fileFolder + "/" + fUtils.removeExtention(filename) + "_small." + fUtils.getFileExtension(filename);
		String rs = processImageUponSize(dims, GlobalVars.SMALL_IMGAGE_SIZE, path, small); 
		String[] resp_small = rs.split("httpd"); 
		res[0] = resp_small[1];
					
		String medium = fileFolder + "/" + fUtils.removeExtention(filename) + "_medium." + fUtils.getFileExtension(filename); 
		String rm = processImageUponSize(dims, GlobalVars.MEDIUM_IMGAGE_SIZE, path, medium);				
		String[] resp_med = rm.split("httpd"); 
		res[1] = resp_med[1];
										
		String large = fileFolder + "/" + fUtils.removeExtention(filename) + "_large." + fUtils.getFileExtension(filename);		
		String rl = processImageUponSize(dims, GlobalVars.LARGE_IMGAGE_SIZE, path, large);
		String[] resp_larg = rl.split("httpd"); 
		res[2] = resp_larg[1];							
		
		//file.delete();
		
		return res;
	}  
	
	/**
	 * resizeImage
	 * @param path
	 * @param fileName
	 * @param size
	 * @throws IOException
	 */
	public String resizeImageByFormat (File file, int format) throws IOException{
		
		String path = file.toString();		
		String filename = file.getName();
		String fileFolder = file.getParent();
				
		String res = null;
		
		int[] dims = getImageSize(path);	
		
		switch (format){		
			
			case GlobalVars.RESIZE_IMAGE_TO_SMALL:
				String small = fileFolder + "/" + fUtils.removeExtention(filename) + "_small." + fUtils.getFileExtension(filename);
				res = processImageUponSize(dims, GlobalVars.SMALL_IMGAGE_SIZE, path, small);				
				if (res!=null)
					Log.v("image_resize", filename + " SMALL_IMGAGE_SIZE resized:" + res);
					
				break;	
				
			case GlobalVars.RESIZE_IMAGE_TO_MEDIUM:			
				String medium = fileFolder + "/" + fUtils.removeExtention(filename) + "_medium." + fUtils.getFileExtension(filename); 
				res = processImageUponSize(dims, GlobalVars.MEDIUM_IMGAGE_SIZE, path, medium);				
						
				if (res!=null)
					Log.v("image_resize", filename + " MEDIUM_IMGAGE_SIZE resized:" + res);
				
				break;
		
			case GlobalVars.RESIZE_IMAGE_TO_LARGE:		
				String large = fileFolder + "/" + fUtils.removeExtention(filename) + "_large." + fUtils.getFileExtension(filename); 
				res = processImageUponSize(dims, GlobalVars.LARGE_IMGAGE_SIZE, path, large);							
				if (res!=null)
					Log.v("image_resize", filename + " LARGE_IMGAGE_SIZE resized:" + res);
				
				break;						
		}
		
		String[] resp = res.split("httpd");
		
		return resp[1];
	}
	
	/**
	 * processImageUponSize
	 * @param dims
	 * @param size
	 * @param path
	 * @param pre_desc
	 * @param fileName
	 * @throws IOException
	 */
	private String processImageUponSize(int[] dims, int size, String path, String finalPath) throws IOException{
		int height;
		Bitmap b;		
		int w = dims[0];
		int h = dims[1];
		height = (size * h) / w;			
		b = getResizedBitmap(path, size, height);	
		return storeImageToSDCard(b, finalPath);	
	}
	
	/**
	 * getImageSiz-*e
	 * @param path
	 * @return
	 */
	private int[] getImageSize(String path){
		
		int[] dims = new int[2];
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		//Returns null, sizes are in the options variable
		BitmapFactory.decodeFile( path, options);
		dims[0] = options.outWidth;
		dims[1] = options.outHeight;
		
		return dims;		
	}
	
	/**
	 * getResizedBitmap
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @return
	 */
	private Bitmap getResizedBitmap(String path, int newHeight, int newWidth) {
		
		File imgFile = new  File(path);
		
		Bitmap resizedBitmap = null;
			
			if(imgFile.exists()){
				
				Bitmap bm = BitmapFactory.decodeFile(imgFile.toString());
				
				if(bm==null){
					Log.v("","");
					
				}
				
				if (bm==null){
					Log.v("","");
				}

				int width = bm.getWidth();

				int height = bm.getHeight();

				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;
				// create a matrix for the manipulation

				Matrix matrix = new Matrix();
				// resize the bit map

				matrix.postScale(scaleWidth, scaleHeight);
				// recreate the new Bitmap

				resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);		
				
				//int scaleWidthScaled = newWidth / width;
				//int scaleHeightScaled = newHeight / height;		
				//resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap, scaleWidthScaled, scaleHeightScaled, true);
			}			

		return resizedBitmap;

	}
	
	/**
	 * storeImageToSDCard
	 * @param imageBitmap
	 * @param thumPath
	 * @throws IOException
	 */
	private String storeImageToSDCard(Bitmap imageBitmap, String thumPath) throws IOException{
		
		byte[] imageData = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        imageData = baos.toByteArray();

        FileOutputStream fos = null;
        //File newImage = new File(thumPath);
        //newImage.mkdirs();
        
		try {
			fos = new FileOutputStream(thumPath);
		} catch (Exception e) {			
			e.printStackTrace();
		}

        fos.write(imageData);
        fos.close();
        imageBitmap.recycle();
        
        File generated = new File(thumPath);
        if (generated.exists()){
        	return thumPath;
        } else {
        	return null;
        }   
        
		
	}
	
	
}
