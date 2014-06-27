package com.tngnts.wayfarer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		

		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally {
					Intent openMainMenu = new Intent("android.intent.action.MENU");
					startActivity(openMainMenu);
					finish();
				}
			}
		};
		timer.start();

	}

    
}