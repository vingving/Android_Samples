/**
 * system info sample
 * 2017-06-01 K.OHWADA 
 */
 
package jp.ohwada.android.systeminfosample1; 


import android.app.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    
     	// debug
    	private final static String TAG_SUB = "MainActivity";   
		private TextView mTextView1;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
            	        mTextView1 = (TextView) findViewById( R.id.TextView_1 );
            	            
        	        Button btnSystem = (Button) findViewById( R.id.Button_system );
				btnSystem.setOnClickListener( new View.OnClickListener() {
	 		@Override
			public void onClick( View v ) {
				procButtonSystem();
			}
		});	
		
    } // onCreate
    
    
       	/**
	 * procButtonSystem
	 */	
    private void procButtonSystem() {
    SystemInfo   system  = new SystemInfo( this );
    String msg = system.getSystemInfo();
    	mTextView1.setText( msg );
} // 	procButtonDevice
   
   
} // class MainActivity
