package com.roamtouch.menuserver.utils;

public class CharHandler {

	public CharHandler(){}

	

	public String createFolderName(String folder){
		folder = folder.toLowerCase();
		folder = folder.replaceAll(" ", "_");
		folder = replaceSpecialCharFolder(folder);
		return folder;
	}
	
	public String replaceSpecialCharFolder(String txt){		
		txt = txt.replaceAll("á", "\u00e1");		
		txt = txt.replaceAll("é", "\u00e9");
		txt = txt.replaceAll("í", "\u00f3");
		txt = txt.replaceAll("ó", "\u00e1");
		txt = txt.replaceAll("ú", "\u00fa");
		txt = txt.replaceAll("�?", "\u00c1");
		txt = txt.replaceAll("É", "\u00c9");
		txt = txt.replaceAll("�?", "\u00cd");
		txt = txt.replaceAll("Ó", "\u00d3");
		txt = txt.replaceAll("Ú", "\u00da");
		txt = txt.replaceAll("ñ", "\u00d3");	
		txt = txt.replaceAll("Ñ", "\u00d1");		
		return txt;
	}	
	
	
}
