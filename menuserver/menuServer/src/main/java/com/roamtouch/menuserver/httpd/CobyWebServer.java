package com.roamtouch.menuserver.httpd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.roamtouch.menuserver.MenuServer;
import com.roamtouch.menuserver.MenuServerApplication;
import com.roamtouch.menuserver.communications.APIProtocol;
import com.roamtouch.menuserver.database.DataBaseController;
import com.roamtouch.menuserver.httpd.NanoHTTPD.Response;
import com.roamtouch.menuserver.httpd.NanoHTTPD.Response.Status;
import com.roamtouch.menuserver.shared.Constans;
import com.roamtouch.menuserver.utils.FileUtils;
import com.roamtouch.menuserver.utils.FolderHandler;
import com.roamtouch.menuserver.utils.Parser;

public class CobyWebServer extends NanoHTTPD {
	
	/**
	 * NOTA: Implementacion COBY NanoHTTPD
	 * 
	 * 1) La variable QUERY_STRING_PARAMETER le pone un string al parametro "NanoHttpd.QUERY_STRING", comentar lineas 829 a 834 donde hace el put.
	 * 
	 * 2) UTF linea 303 "txt.getBytes("UTF-8")" reemplazar por txt.getBytes("ISO-8859-1");
	 * 
	 */
	
	
	//RoamTouch	
	private MenuServerApplication app;
	protected CobyWebServer server;
	private static APIProtocol API; 
	private MenuServer cM;
    private static String uploadDirectory; 
    private FileUploadedManagerFactory factory;
          
	
    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static final Map<String, String> MIME_TYPES;
    static {
        Map<String, String> mime = new HashMap<String, String>();
        mime.put("css", 	"text/css");
        mime.put("htm", 	"text/html");
        mime.put("html", 	"text/html");
        mime.put("xml", 	"text/xml");
        mime.put("txt", 	"text/plain");
        mime.put("asc", 	"text/plain");
        mime.put("gif", 	"image/gif");
        mime.put("jpg", 	"image/jpeg");
        mime.put("jpeg", 	"image/jpeg");
        mime.put("png", 	"image/png");
        mime.put("mp3", 	"audio/mpeg");
        mime.put("m3u", 	"audio/mpeg-url");
        mime.put("mp4", 	"video/mp4");
        mime.put("ogv", 	"video/ogg");
        mime.put("flv", 	"video/x-flv");
        mime.put("mov", 	"video/quicktime");
        mime.put("swf", 	"application/x-shockwave-flash");
        mime.put("js", 		"application/javascript");
        mime.put("json", 	"application/json");
        mime.put("pdf", 	"application/pdf");
        mime.put("doc", 	"application/msword");
        mime.put("ogg", 	"application/x-ogg");
        mime.put("zip", 	"application/octet-stream");
        mime.put("exe", 	"application/octet-stream");
        mime.put("class", 	"application/octet-stream");
        mime.put("db", 		"application/octet-stream");
        MIME_TYPES = mime;
    }

    /**
     * The distribution licence
     */
    private static final String LICENCE = "Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\n"
            + "and Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n" + "\n"
            + "Redistribution and use in source and binary forms, with or without\n"
            + "modification, are permitted provided that the following conditions\n" + "are met:\n" + "\n"
            + "Redistributions of source code must retain the above copyright notice,\n"
            + "this list of conditions and the following disclaimer. Redistributions in\n"
            + "binary form must reproduce the above copyright notice, this list of\n"
            + "conditions and the following disclaimer in the documentation and/or other\n"
            + "materials provided with the distribution. The name of the author may not\n"
            + "be used to endorse or promote products derived from this software without\n"
            + "specific prior written permission. \n"
            + " \n" + "THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\n"
            + "IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\n"
            + "OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\n"
            + "IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\n"
            + "INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\n"
            + "NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\n"
            + "DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\n"
            + "THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n"
            + "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\n"
            + "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";

    private File rootDir;
    
    

    public CobyWebServer(String host, int port, File wwwroot, MenuServer cm, DataBaseController db, FolderHandler fH, com.roamtouch.menuserver.communications.WebSocket w) { 		
        super(host, port);        
        this.rootDir = wwwroot;     
        API = new APIProtocol(db, cm, fH, w);             
        //setAssets(wwwroot, cm, db, fH, w);
        server = this;
        this.cM = cm;
        app = ((MenuServerApplication)cm.getApplication());
        uploadDirectory = app.getSDCARD() + "/httpd/buffer";
        factory = new FileUploadedManagerFactory();
        this.setTempFileManagerFactory(factory);        
    }    

    public File getRootDir() {
        return rootDir;
    }

    /**
     * URL-encodes everything between "/"-characters. Encodes spaces as '%20' instead of '+'.
     */
    private String encodeUri(String uri) {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/"))
                newUri += "/";
            else if (tok.equals(" "))
                newUri += "%20";
            else {
                try {
                    newUri += URLEncoder.encode(tok, "UTF-8");
                } catch (UnsupportedEncodingException ignored) {
                }
            }
        }
        return newUri;
    }

    /**
     * Serves file from homeDir and its' subdirectories (only). Uses only URI, ignores all headers and HTTP parameters.
     */
    public Response serveFile(String uri, Map<String, String> header, File homeDir) {
        Response res = null;

        // Make sure we won't die of an exception later
        if (!homeDir.isDirectory())
            res = new Response(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.");

        if (res == null) {
            // Remove URL arguments
            uri = uri.trim().replace(File.separatorChar, '/');
            if (uri.indexOf('?') >= 0)
                uri = uri.substring(0, uri.indexOf('?'));

            // Prohibit getting out of current directory
            if (uri.startsWith("src/main") || uri.endsWith("src/main") || uri.contains("../"))
                res = new Response(Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: Won't serve ../ for security reasons.");
        }

        File f = new File(homeDir, uri);
        if (res == null && !f.exists())
            res = new Response(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found.");

        // List the directory, if necessary
        if (res == null && f.isDirectory()) {
            // Browsers get confused without '/' after the
            // directory, send a redirect.
            if (!uri.endsWith("/")) {
                uri += "/";
                res = new Response(Response.Status.REDIRECT, NanoHTTPD.MIME_HTML, "<html><body>Redirected: <a href=\"" + uri + "\">" + uri
                        + "</a></body></html>");
                res.addHeader("Location", uri);
            }

            if (res == null) {
                // First try index.html and index.htm
                if (new File(f, "index.html").exists())
                    f = new File(homeDir, uri + "/index.html");
                else if (new File(f, "index.htm").exists())
                    f = new File(homeDir, uri + "/index.htm");
                    // No index file, list the directory if it is readable
                else if (f.canRead()) {
                    String[] files = f.list();
                    String msg = "<html><body><h1>Directory " + uri + "</h1><br/>";

                    if (uri.length() > 1) {
                        String u = uri.substring(0, uri.length() - 1);
                        int slash = u.lastIndexOf('/');
                        if (slash >= 0 && slash < u.length())
                            msg += "<b><a href=\"" + uri.substring(0, slash + 1) + "\">..</a></b><br/>";
                    }

                    if (files != null) {
                        for (int i = 0; i < files.length; ++i) {
                            File curFile = new File(f, files[i]);
                            boolean dir = curFile.isDirectory();
                            if (dir) {
                                msg += "<b>";
                                files[i] += "/";
                            }

                            msg += "<a href=\"" + encodeUri(uri + files[i]) + "\">" + files[i] + "</a>";

                            // Show file size
                            if (curFile.isFile()) {
                                long len = curFile.length();
                                msg += " &nbsp;<font size=2>(";
                                if (len < 1024)
                                    msg += len + " bytes";
                                else if (len < 1024 * 1024)
                                    msg += len / 1024 + "." + (len % 1024 / 10 % 100) + " KB";
                                else
                                    msg += len / (1024 * 1024) + "." + len % (1024 * 1024) / 10 % 100 + " MB";

                                msg += ")</font>";
                            }
                            msg += "<br/>";
                            if (dir)
                                msg += "</b>";
                        }
                    }
                    msg += "</body></html>";
                    res = new Response(msg);
                } else {
                    res = new Response(Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: No directory listing.");
                }
            }
        }

        try {
            if (res == null) {
                // Get MIME type from file name extension, if possible
                String mime = null;
                int dot = f.getCanonicalPath().lastIndexOf('.');
                if (dot >= 0)
                    mime = MIME_TYPES.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
                if (mime == null)
                    mime = NanoHTTPD.MIME_DEFAULT_BINARY;

                // Calculate etag
                String etag = Integer.toHexString((f.getAbsolutePath() + f.lastModified() + "" + f.length()).hashCode());

                // Support (simple) skipping:
                long startFrom = 0;
                long endAt = -1;
                String range = header.get("range");
                if (range != null) {
                    if (range.startsWith("bytes=")) {
                        range = range.substring("bytes=".length());
                        int minus = range.indexOf('-');
                        try {
                            if (minus > 0) {
                                startFrom = Long.parseLong(range.substring(0, minus));
                                endAt = Long.parseLong(range.substring(minus + 1));
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }

                // Change return code and add Content-Range header when skipping is requested
                long fileLen = f.length();
                if (range != null && startFrom >= 0) {
                    if (startFrom >= fileLen) {
                        res = new Response(Response.Status.RANGE_NOT_SATISFIABLE, NanoHTTPD.MIME_PLAINTEXT, "");
                        res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                        res.addHeader("ETag", etag);
                    } else {
                        if (endAt < 0)
                            endAt = fileLen - 1;
                        long newLen = endAt - startFrom + 1;
                        if (newLen < 0)
                            newLen = 0;

                        final long dataLen = newLen;
                        FileInputStream fis = new FileInputStream(f) {
                            @Override
                            public int available() throws IOException {
                                return (int) dataLen;
                            }
                        };
                        fis.skip(startFrom);

                        res = new Response(Response.Status.PARTIAL_CONTENT, mime, fis);
                        res.addHeader("Content-Length", "" + dataLen);
                        res.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                        res.addHeader("ETag", etag);
                    }
                } else {
                    if (etag.equals(header.get("if-none-match")))
                        res = new Response(Response.Status.NOT_MODIFIED, mime, "");
                    else {
                        res = new Response(Response.Status.OK, mime, new FileInputStream(f));
                        res.addHeader("Content-Length", "" + fileLen);
                        res.addHeader("ETag", etag);
                    }
                }
            }
        } catch (IOException ioe) {
            res = new Response(Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
        }

        res.addHeader("Accept-Ranges", "bytes"); // Announce that the file server accepts partial content requestes
        return res;
    }
    
    

    @Override
    public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files, String remote_ip, long pid) {
    	    	
    	Response res = null;
		JSONArray json = new JSONArray();
		
		//String[] host = header.get("host").split(":");	
		String agent = header.get("user-agent");		
		
		//Identifico que el header no viene de la propia maestra para saber si es externo.
		if (!app.get_IP_SERVER().equals(remote_ip)){
			Log.v("","MENUSERVER REMOTE " + parms.toString() );
		} else {
			Log.v("","MENUSERVER LOCAL " + parms.toString() );
		}
		
		if (method==Method.POST){	
		
				try {					
										
					String params = parms.toString();
					Parser p = new Parser(params, remote_ip, pid, agent);
					
					int action = p.getAction();
					//Pedido en loop de 
					if ( 
							action!= 11004 &&
							action!= 15000 &&
							action!= 10008
							){
						
						Log.v("","");
						
					}
					
					/** Ajustes por metodos en archivos subidos. **/
					
					String real_image = null;								
					String real_video = null;		
					
					String temp_image_file = null;
					String temp_video_file = null;						
					
					boolean hasImage = false;
					boolean hasVideo= false;
					
					switch (action){					
					
						case Constans.UPDATE_MENU_ITEM:
						case Constans.SEND_COMMAND_RESTORE_DATABASE_AND_MEDIA:
						case Constans.INSERT_MENU_ITEM:		
						case Constans.SEND_COMMAND_IMPORT_EXCEL_FILE:
						case Constans.SEND_COMMAND_IMPORT_GDOCS_FILE:
						case Constans.SEND_COMMAND_RESTORE_DATABASE:
							
							Iterator<String> iparams = parms.keySet().iterator();	
											
							while (iparams.hasNext()) {
								
								String value = iparams.next();						
								
								if ( value.contains("item_image") || value.contains("edit_item_image")){
									
									real_image = parms.get(value);
																	
									if (real_image.equals("")){
										
										hasImage = false;
										
									} else {									
										
										if(!real_image.contains(".zip")){											
											
											if ( 
													!real_image.contains(".jpg") 	&& 
													!real_image.contains(".png") 	&& 
													!real_image.contains(".xlsx") 	&& 
													!real_image.contains(".xls")  	&&
													!real_image.contains(".db")
												) {
												
												real_image += ".jpg";
											}													
										}
										hasImage = true;										
									}	
									
								} else if (value.contains("item_video") || value.contains("edit_item_video")){
									
									real_video = parms.get(value);								
									
									if (real_video.equals("")){
										hasVideo = false;
									} else {
										hasVideo = true;
										if ( !real_video.contains(".mp4")) {
											real_video += ".mp4";
										}
									}							
								}								
							}					
										
							Iterator<String> ifiles = files.keySet().iterator();
							
						    while (ifiles.hasNext()) {
						    	
						    	String value = ifiles.next();
						    	
								if (value.contains("item_image") || value.contains("edit_item_image")){
									
									temp_image_file = files.get(value);
									
								} else if (value.contains("item_video")){
									
									temp_video_file = files.get(value);
									
								}					          
						    }					  
						    
						    if (hasImage){				
								
								File uploaded_image_file = new File(temp_image_file);
								
								String real_image_path = uploadDirectory + "/" + real_image;
								File real_image_file = new File(real_image_path);					
								
						        boolean ss = uploaded_image_file.renameTo(real_image_file);
						        if (!ss) {
						            System.err.println("Error Renombrendo Temporal a imagen subida.");
						        } 
						        
						        if (real_image.endsWith(".zip")){
						        	
						        	API.setBackpupFile(real_image_file);
						        	
						        } else if (real_image.endsWith(".xlsx") || (real_image.endsWith(".xls"))) {
						        	
						        	API.setImportFile(real_image_file);
						        	
						        } else if (real_image.endsWith(".db")) {
						        	
						        	API.setDatabaseFile(real_image_file);
						        	
						        } else {
						        	
						        	API.setImageFile(real_image_file);
						        	
						        }
									
							} 
							
							if (hasVideo){
								
								File uploaded_video_file = new File(temp_video_file);
								
								String real_video_path = uploadDirectory + "/" + real_video;
								File real_video_file = new File(real_video_path);					
								
						        boolean ss = uploaded_video_file.renameTo(real_video_file);
						        if (!ss) {
						            System.err.println("Error Renombrendo Temporal a video subido.");
						        } 
						        
						        API.setVideoFile(real_video_file);
								
							}
						    		
							break;
					
					}		
									
					json = API.parseParams(parms.toString(), p);		
					
					
				} catch (JSONException e) {
					Log.v("error",""+e);
				} catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            res = new Response(Status.OK, MIME_JSON, json.toString());
				
				return res;
					
		}
    	
        System.out.println(method + " '" + uri + "' ");

        Iterator<String> e = header.keySet().iterator();
        while (e.hasNext()) {
            String value = e.next();
            System.out.println("  HDR: '" + value + "' = '" + header.get(value) + "'");
        }
        e = parms.keySet().iterator();
        while (e.hasNext()) {
            String value = e.next();
            System.out.println("  PRM: '" + value + "' = '" + parms.get(value) + "'");
        }
        e = files.keySet().iterator();
        while (e.hasNext()) {
            String value = e.next();
            System.out.println("  UPLOADED: '" + value + "' = '" + files.get(value) + "'");
        }   

        return serveFile(uri, header, getRootDir());
    }    
    
    public void startHTTPD(){
    	try {
    		ServerRunner.executeInstance(server);
    	} catch(Exception e){
    		Log.v("","MENUSERVER REMOTE " + e.toString() );
    	}
    	
    }
    

    private static class FileUploadedManagerFactory implements TempFileManagerFactory {
    	
        @Override
        public TempFileManager create() {
            return new FileUploadedManager();
        }
        
    }
    
    private static class FileUploadedManager implements TempFileManager {
    	
        private final String tmpdir;
        private final List<TempFile> tempFiles;

        private FileUploadedManager() {
            //tmpdir = System.getProperty("java.io.tmpdir");
        	tmpdir = uploadDirectory;
            tempFiles = new ArrayList<TempFile>();
        }

        @Override
        public TempFile createTempFile() throws Exception {
            DefaultTempFile tempFile = new DefaultTempFile(tmpdir);
            tempFiles.add(tempFile);
            System.out.println("Created tempFile: " + tempFile.getName());
            return tempFile;
        }

        @Override
        public void clear() {
            if (!tempFiles.isEmpty()) {
                System.out.println("Cleaning up:");
            }
            for (TempFile file : tempFiles) {
                try {
                    System.out.println("   "+file.getName());
                    boolean has_extension = file.getName().contains("\\.");                 
                    if(!has_extension)
                    	file.delete();
                } catch (Exception ignored) {}
            }
            tempFiles.clear();
        }
    }
    
    
}
