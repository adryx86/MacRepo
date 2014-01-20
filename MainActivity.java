package com.example.alarmns;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	 
    public static boolean isService = false; 
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        
        Button startserviceButton = (Button) findViewById(R.id.button);
        startserviceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,BackgroundService.class));
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                isService = true;
            }
        });
    	
    
        Button stopserviceButton = (Button) findViewById(R.id.buttonStop);
        stopserviceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	stopService(new Intent(MainActivity.this,
                          BackgroundService.class));
            	
            	BackgroundService.fromNotification = 0;
            	finishActivity(0);
            	finish();
            }
        });
        
        /*startService(new Intent(MainActivity.this,BackgroundService.class));
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        isService = true;*/

    }
 
    @Override
    public void onStart(){
            super.onStart();
            
            if(BackgroundService.fromNotification == 123)	{
            	stopService(new Intent(MainActivity.this,BackgroundService.class));
            	BackgroundService.fromNotification = 0;
            	finish();
            }
    }        
    
    /*
    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MainActivity.this,
                BackgroundService.class));
        
        System.out.println("ON RESUME " + isService);
        
        if(isService)
        {
            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setText("Service Resumed");
            isService = false;
            finish();
        }
     
    }*/
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
	





