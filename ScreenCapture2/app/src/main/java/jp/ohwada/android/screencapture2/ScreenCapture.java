/**
 * Screen Capture Sample
 * 2019-02-01 K.OHWADA
 */
package jp.ohwada.android.screencapture2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceView;


/**
 * class ScreenCapture
 * original : https://github.com/googlesamples/android-ScreenCapture
 */
public class ScreenCapture {

    // debug
	 private final static boolean D = true;
     private final static String TAG = "ScreenCapture";
     private final static String TAG_SUB = "ScreenCapture";


/**
 * Virtual Display
 */
    private static final String VIRTUAL_DISPLAY_NAME = "ScreenCapture";
    private static final VirtualDisplay.Callback VIRTUAL_DISPLAY_CALLBACK = null;
    private static final Handler VIRTUAL_DISPLAY_HANDLER = null;

    private VirtualDisplay mVirtualDisplay;


/**
 * Activity
 */
    private Activity mActivity;


/**
 * MediaProjectionManager
 */
    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mMediaProjection;


/**
 * Display Metrics
 */
    private int mDisplayWidth;
    private int mDisplayHeight;
    private int mDisplayDensity;


/**
 * Result of  Permission Request
 */
    private int mResultCode = 0;
    private Intent mResultData;
 

/**
 * constractor
 */
public ScreenCapture(Activity activity) {
    mActivity = activity;
    mMediaProjectionManager 
        = getMediaProjectionManager(activity);

        DisplayMetrics  metrics = DisplayUtil.getDisplayMetrics(activity);
        mDisplayWidth = metrics.widthPixels;
        mDisplayHeight = metrics.heightPixels;
        mDisplayDensity = metrics.densityDpi;
}


/**
 * MediaProjectionManager
 */
private MediaProjectionManager getMediaProjectionManager(Activity activity) {
            log_d("getMediaProjectionManager");
    MediaProjectionManager manager = (MediaProjectionManager)
                activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        return manager;
}


/**
 * requestPermissionIfNotGranted
 */
public void requestPermissionIfNotGranted(int requestCode) {
        if( ! hasPermission() ) {
                // request　Permission, if not granted
                requestPermission(requestCode);
        }
}


/**
 * requestPermission
 */
public void requestPermission(int requestCode) {
            log_d("requestPermission");
            Intent intent = createScreenCaptureIntent();
            mActivity.startActivityForResult( 
                intent,
                requestCode);
}


/**
 * createScreenCaptureIntent
 */
private Intent createScreenCaptureIntent() {
            log_d("createScreenCaptureIntent");
            Intent intent = mMediaProjectionManager.createScreenCaptureIntent();
            return intent;
}


/*
 * setResultData
 */
public boolean setResultData(int resultCode, Intent data) {
            log_d("setResultData");
        if (resultCode != Activity.RESULT_OK) {
                log_d("User cancelled");
                return false;
        }
        log_d("Starting screen capture");
        mResultCode = resultCode;
        mResultData = data;
        return true;
}


/**
 * hasPermission
 */ 
public boolean hasPermission() {
    boolean ret = (mResultCode != 0 && mResultData != null)? true: false;
    return ret;
}


/**
 * setUpMediaProjection
 */
public boolean setUpMediaProjection() {
        log_d("setUpMediaProjection");
     if (mMediaProjection != null) {
            // setup alreadry 
            return false;
    }
    if (mResultData == null ) {
            return false;
    }
        mMediaProjection = mMediaProjectionManager.getMediaProjection(mResultCode, mResultData);
            return true;
}


/**
 * tearDownMediaProjection
 */
public void tearDownMediaProjection() {
        log_d(" tearDownMediaProjection");
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }


/**
 * startScreenCapture
 */ 
public boolean startScreenCapture(int requestCode, SurfaceView surfaceView) {

        log_d("startScreenCapture");
    if( !hasPermission() ){
        // request Permission, if not granted
        requestPermission(requestCode);
        return false;
    }

    setUpMediaProjection();
    setUpVirtualDisplay(surfaceView);
    return true;
}


/**
 * setUpVirtualDisplay
 */ 
public boolean setUpVirtualDisplay(SurfaceView surfaceView) {

    log_d("setUpVirtualDisplay");
        if (mVirtualDisplay != null) {
            // setup alreadry 
            return false;
        }

        int width = surfaceView.getWidth();
        int height = surfaceView.getHeight();
        Surface surface = surfaceView.getHolder().getSurface();
        if (surface == null) {
            return false;
        }

        mVirtualDisplay 
            = creteVirtualDisplay(surface, width, height, mDisplayDensity);

            return true;
}


/**
 * setUpVirtualDisplay
 */ 
public boolean setUpVirtualDisplay(ImageReader imageReader) {

    log_d("setUpVirtualDisplay");
        if (mVirtualDisplay != null) {
            // setup alreadry 
            return false;
        }

    Surface surface = imageReader.getSurface();
    mVirtualDisplay 
            = creteVirtualDisplay(surface, mDisplayWidth, mDisplayHeight, mDisplayDensity);

    return true;
}


/**
 * creteVirtualDisplay
 */ 
public VirtualDisplay creteVirtualDisplay(Surface surface, int width, int height, int density) {

        String msg = "createVirtualDisplay: width= " + width + ", height= " + height + ", Density= " + density;
        log_d(msg);

        VirtualDisplay virtualDisplay
            =  mMediaProjection.createVirtualDisplay(
                VIRTUAL_DISPLAY_NAME,
                width, 
                height, 
                density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface, 
                VIRTUAL_DISPLAY_CALLBACK, VIRTUAL_DISPLAY_HANDLER );

            return virtualDisplay ;
}


/**
 * stopScreenCapture
 */ 
public void stopScreenCapture() {
        log_d("stopScreenCapture" );
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }

}


/**
 * isCaptureRunning
 */ 
public boolean isCaptureRunning() {
    log_d("isCaptureRunning" );
    boolean ret = (mVirtualDisplay != null)? true: false;
    return ret;
}


/**
 * write into logcat
 */ 
private void log_d(String msg ) {
	    if (D) Log.d( TAG, TAG_SUB + " " + msg );
} 

}
