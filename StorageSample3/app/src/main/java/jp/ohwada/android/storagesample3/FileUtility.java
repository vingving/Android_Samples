 /**
 * write Storage for Android 4.4
 * 2017-06-01 K.OHWADA 
 */
 
 package jp.ohwada.android.storagesample3;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * File Utility
 * copy Assets to ExternalStoragePublicDirectory
 */
public class FileUtility {
	// dubug
	private final static boolean D = Constant.DEBUG; 
	private final static String TAG_SUB = "MyFileUtility";
	
	// return code
	public final static int C_FAIL =0;
	public final static int C_EXISTS =1;
		public final static int C_SUCCESS =2;
			
	// all files in asset		 
	private final static String ASSET_PATH = "";
	
	// copy stream
	private final static int EOF = -1;
	private final static int BUFFER_SIZE = 1024 * 4;

	// junk in assets	
	private final static String[] IGNORE_FILES 
	 	= new String[] { "images", "sounds", "webkit" };
		 
	private AssetManager mAssetManager;

	private String mBaseDir = "";
	
	private String mAppDir = "";
		
	/**
     * === constractor ===
	 * @param Context context  
	 */	    
	 public FileUtility( Context context  ) {
		mAssetManager = context.getAssets();
		
		File  dir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
		if ( dir != null ) {	
		mBaseDir = dir.getPath();
		log_d( "BaseDir "  + mBaseDir );
		}
		mAppDir = mBaseDir;
	} 
	




	/**
     * mkdirs
	 * @param String dir 
	  * @return  int
	 */	
	public int mkdirs( String dir ) {
		mAppDir = mBaseDir + File.separator + dir ;	
					log_d("AppDir " + mAppDir);
		
		File file = new File( mAppDir );
		// nothng to do if exists
		if ( file.exists() ) {
			log_d("exists " + mAppDir);
			return C_EXISTS;
		}

		boolean ret = file.mkdirs();
		if (ret) {
			log_d("mkdir " + mAppDir);
			return C_SUCCESS ;
		}
			log_d("mkdir NG" + mAppDir );		
		return C_FAIL ;

} // mkdirs

	    
	/**
	 * copyFiles
	 * @param String ext
	 * @return boolean
	 */  
	public boolean copyFiles( String ext ) {
		
	boolean is_error = false;
		
		List<String> list = getAssetList( ASSET_PATH, ext );
		
		// no result
		if  ( (list == null) ||( list.size() == 0 ) ) {
			return false;
		}
	
		// copy files
		for ( String name: list ) {
			
			log_d( "copy " + name );

				String path_dst =  getPath( name );
												
			 int ret = copyAssetsToStorage( name, path_dst );
			 if(ret == C_FAIL) {
			 	is_error = true;
			 }
	
		} // for

		return ! is_error ;

	} //copyFiles
	

	/**
	 * get AssetsList
	 */  
	private List<String> getAssetList( String path, String ext ) {
		
	List<String> list =	new ArrayList<String>();
	String[] files = null;
	
		try {
			files = mAssetManager.list( path );
		} catch (IOException e) {
			if (D) e.printStackTrace();
		}

		// nothing if no files
		if ( files == null ) return list;
					 
		int length = files.length;
		// nothing if no files
		if ( length == 0 ) return list;

		// all files
		for ( int i=0; i < length; i ++ ) {
						
			String name = files[i];
			log_d( "assets " + name );
			
			// skip if ignore
		 if ( checkIgnore( name ) ) continue;
			
			// skip if munmach ext
			String name_ext = parseExt( name );
			if ( ( name_ext != null )&&( ext != null )&&( ext.length() > 0)&&(! name_ext.equals(ext) )) continue;

	list.add(name);
	
	} // for
	
	return list;
} // getAssetList


	/**
	 * checkIgnore
	 */  
	private boolean checkIgnore( String name ) {

		if ( name == null ) return false;
		
	for ( int i=0; i < IGNORE_FILES.length; i ++ ) {
		
			// check ignore
			if ( name.equals( IGNORE_FILES[ i ] ) ) return true;

		} // for

		return false;
} // checkIgnore
	
	
	/**
	 * copyAssetsToStorage
	 */  	
	private int copyAssetsToStorage( String name_src, String path_dst ) {
		
			File file_dst = new File( path_dst );
			if ( file_dst.exists() ) {
				log_d( "exitsts " + path_dst );
					return C_EXISTS;
	}		
	
		InputStream is = getAssetsInputStream( name_src );
		OutputStream os = getOutputStream( file_dst );

if ( is == null ) return C_FAIL;		
if ( os == null ) return C_FAIL;

boolean ret = copyStream( is,  os );

			closeInputStreamStream( is );
			closeOutputStreamStream( os );
			
				if ( ret ) {
				log_d( "copy OK " + path_dst );
							return C_SUCCESS ;
			} 
				log_d( "copy NG " + path_dst );
		return C_FAIL ;

	} // copyAssetsToStorage
	
	
	/**
	 * getAssetsInputStream
	 */ 	
	private InputStream getAssetsInputStream( String fileName ) {
		
		InputStream is = null;
		try {
			is = mAssetManager.open(fileName);
		} catch (IOException e) {
			if (D) e.printStackTrace();
		} 
		
	return is;
} //getAssetsInputStream


	/**
	 * getOutputStream
	 */ 
	private OutputStream getOutputStream( File file ) {	
	
		OutputStream os = null;

		try {
			os = new FileOutputStream(file); 
					} catch (IOException e) {
			if (D) e.printStackTrace();
		} 
		
		return os;

} // getOutputStream


	/**
	 * copyStream
	 */
private boolean copyStream( InputStream is, OutputStream os ) {
			
		byte[] buffer = new byte[BUFFER_SIZE];
		int n = 0;	
			boolean is_error = false;
			
		try {
			// copy input to output		
			while (EOF != (n = is.read(buffer))) {
				os.write(buffer, 0, n);
			}

		} catch (IOException e) {
			is_error = true;
			if (D) e.printStackTrace();
	}

	return ! is_error;
}	// copyStream


	/**
	 * closeInputStreamStream
	 */
private boolean closeInputStreamStream( InputStream is ) {
					boolean is_error = false;
				try {
					is.close();
				} catch (IOException e) {
					is_error = true;
					if (D) e.printStackTrace();
				} // try

	return ! is_error;
} //closeInputStreamStream


	/**
	 * closeOutputStreamStream
	 */
private boolean closeOutputStreamStream( OutputStream os ) {
					boolean is_error = false;
				try {
					os.close();
				} catch (IOException e) {
					is_error = true;
					if (D) e.printStackTrace();
				} // try

	return ! is_error;
} // closeOutputStreamStream


	/**
	 * parseExt
	 */
private String parseExt( String fileName ) {
	String ext = "";
    if (fileName != null) {        
    int point = fileName.lastIndexOf(".");
    if (point != -1) {
        ext = fileName.substring(point + 1);
    } // if point
    } // if fileName
    
    return ext;
} // parseExt


	/**
	 * getBitmap
	 * @param String path
	 * @return Bitmap
	 */	
	public Bitmap getBitmap( String name ) {
		
				Bitmap bitmap = null;
		try {
		bitmap = BitmapFactory.decodeFile( getPath(name) );
				} catch (Exception e) {
			if (D) e.printStackTrace();
		}
		
		if ( bitmap != null ) {
			bitmap.setDensity( DisplayMetrics.DENSITY_MEDIUM );
		}
		
		return bitmap;
	} // getBitmap


	/**
	 * getFile
	 */		
		private File getFile( String name ) {
		File file = new File(getPath(name));
		return file;
		
	} // getFile

	/**
	 * getPath
	 */			
	private String getPath( String name ) { 
		 String path = mAppDir  + File.separator + name ;
		 return path;
		 
	} // getPath
	

 	/**
	 * write into logcat
	 */ 
	private void log_d( String msg ) {
	    if (Constant.DEBUG) Log.d( Constant.TAG, TAG_SUB + " " + msg );
	} // log_d

} // class FileUtility