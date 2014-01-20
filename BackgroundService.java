package com.example.alarmns;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class BackgroundService extends Service {
    
	private NotificationManager mNM;
    
	Bundle b;
    Intent notificationIntent;
    
    private final IBinder mBinder = new LocalBinder();
    private String newtext;
    private MediaPlayer mp = null;
    
    boolean attiva	=	false;
    
    int contatore = 0;
    
    CountDownTimer waitTimer;
    
    static int fromNotification;
    String Operatore;
    
    public class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }
 
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
 
        newtext = "Alarmns Attivo";
        fromNotification = 123;
        
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        int events = PhoneStateListener.LISTEN_SIGNAL_STRENGTHS;
        Operatore = telephonyManager.getNetworkOperatorName();
        telephonyManager.listen(phoneStateListener, events);
        
        if	(Operatore	==	null)	{
        	Operatore = "Operatore NON rilevato";
        }
        
        Notification notification = new Notification(R.drawable.ic_ballonfeed, newtext,System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(BackgroundService.this, 0, new Intent(BackgroundService.this,MainActivity.class), 0);
        notification.setLatestEventInfo(BackgroundService.this,"Controllo " + Operatore, newtext, contentIntent);
        mNM.notify(R.string.local_service_started, notification);
        notificationIntent = new Intent(this, MainActivity.class);
        showNotification();  
        
        inizioTimer();
    }
 
    public void inizioTimer() {
	    //@SuppressWarnings("unused")
		//CountDownTimer waitTimer;
	    waitTimer = new CountDownTimer(60000, 3000) {
	
		    public void onTick(long millisUntilFinished) {
		           //System.out.println("RICHIAMO LA CLASSE");
			    	   
		           Thread thread = new Thread(new Runnable()      
			           {
			             public void run()
			           {
			             startSignalListener();
			            }
		          });
		          thread.start(); 
		    }
		
			public void onFinish() {
		    	contatore = 0;
		    	inizioTimer();
		    	
		    }
		}.start();
		

    }
 
 
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    
    public void onDestroy() {
        
        System.out.println("ON DESTROY");
        mNM.cancel(R.string.local_service_started);
        waitTimer.cancel();
        stopSelf();
    }
    
    @SuppressWarnings("deprecation")
	private void showNotification() {
        CharSequence text = getText(R.string.local_service_started);
         
        if	(Operatore	==	null)	{
        	Operatore = "Operatore NON rilevato";
        }
        
        Notification notification = new Notification(R.drawable.ic_ballonfeed, text, System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, "Controllo " + Operatore, newtext, contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;     
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
        mNM.notify(R.string.local_service_started, notification);
        
        //MediaController md = new MediaController();
        
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @SuppressWarnings("deprecation")
	public void startSignalListener() {
        
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        int events = PhoneStateListener.LISTEN_SIGNAL_STRENGTHS;
        telephonyManager.listen(phoneStateListener, events);
      	        
        //System.out.println("START SIGNAL1 " + telephonyManager.getDataState());
        //System.out.println("START SIGNAL2 " + telephonyManager.getSimState());
        //System.out.println("START SIGNAL3 " + telephonyManager.getSimOperator());
        //System.out.println("START SIGNAL4 " + telephonyManager.getLine1Number());
        //System.out.println("START SIGNAL4 " + telephonyManager.getCallState());	
        //System.out.println("START SIGNAL4 " + telephonyManager.getDataActivity());	
        System.out.println("START OPERATOR NAME " + telephonyManager.getNetworkOperatorName());
        //System.out.println("START NETWORK TYPE  " + telephonyManager.getNetworkType());         
        //System.out.println("START MAILALTPHA    " + telephonyManager.getVoiceMailAlphaTag());
        //System.out.println("START MAILNUMBER    " + telephonyManager.getVoiceMailNumber());
        //System.out.println("START SERIALNUMBER  " + telephonyManager.getSimSerialNumber());
         
        String statoCell = telephonyManager.getNetworkOperatorName();
        
        System.out.println("OPERATOR NAME " + "-" + statoCell + "-");
        
        if	(statoCell.isEmpty())	{
      
        	contatore++;
        	
        	System.out.println("NO SEGNALE " + contatore);
        	
        	if	(contatore == 5)		{
        		gestioneNoSignal();
        		contatore = 0;
        	}
        }
        else {
        	
        	if	(attiva)	{	
        		@SuppressWarnings("static-access")
				NotificationManager nm = ( NotificationManager ) getSystemService( this.NOTIFICATION_SERVICE );
        		Notification notification = new Notification();
        		notification.ledARGB = 0; 									//Spengo il LED
        		notification.flags = Notification.FLAG_SHOW_LIGHTS;
        		notification.ledOnMS  = 0; 									//Spengo il LED
        		notification.ledOffMS  = 0;									//Spengo il LED
        		nm.notify(1, notification);
        		mp = MediaPlayer.create(getApplicationContext(),R.raw.voce001);  
        		mp.stop();
        	}
        	attiva = false;
        	System.out.println("SEGNALE OK" + contatore);
        }
        
    }

    public void stopListening() {
        
		System.out.println("STOP SIGNAL");
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        System.out.println("1111DetectCalls ");    
    }


	public PhoneStateListener phoneStateListener = new PhoneStateListener() {
	
	//Segnale da 0 a 31
		@SuppressWarnings("deprecation")
		@Override
		public void onSignalStrengthChanged(int asu) {
	    		System.out.println("DetectCalls " + "onSignalStrengthChanged " + asu);
	            super.onSignalStrengthChanged(asu);
		}
	
	};
    
	PhoneStateListener listener = new PhoneStateListener() { 
        @Override
        public void onDataConnectionStateChanged(int state) {
            super.onDataConnectionStateChanged(state);
            
            System.out.println("MA CHE OOO : " + state);
            
            switch (state) {
                case TelephonyManager.DATA_DISCONNECTED:
                	System.out.println("DISCONESSO");
                	break;
                case TelephonyManager.DATA_CONNECTED:
                	System.out.println("CONNESSO");
                    break;
            }
        }
    };
    
    
    @SuppressWarnings("deprecation")
	public void gestioneNoSignal()	{
    
    	@SuppressWarnings("static-access")
		NotificationManager nm = ( NotificationManager ) getSystemService(this.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.ledARGB = Color.GRAY; 
        notification.flags = Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;  /*FLAG_SHOW_LIGHTS;*/
        notification.ledOnMS = 100; //Durata LED acceso
        notification.ledOffMS = 50; //Durata LED spento
        nm.notify(1, notification);
         attiva	=	true;
        
        mp = MediaPlayer.create(getApplicationContext(), R.raw.voce001);  
        mp.start();
                
        mp.setOnCompletionListener(new OnCompletionListener() {
        	public void onCompletion(MediaPlayer mp) {
                    mp.release();

                    }; 
           });
    }
    
    
}
