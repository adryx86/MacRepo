package com.example.alarmns;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class ListenToPhoneState extends PhoneStateListener {

    boolean callEnded=false;
    
    public void onCallStateChanged(int state, String incomingNumber) {

    	System.out.println("Inizio: " + state);
    	
        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
        	System.out.println("State changed: " + state+" Idle");
        	

            if(callEnded)
            {
                //you will be here at **STEP 4**
                //you should stop service again over here
            }
              else
              {
                //you will be here at **STEP 1**
             //stop your service over here,
                //i.e. stopService (new Intent(`your_context`,`CallService.class`));
                //NOTE: `your_context` with appropriate context and `CallService.class` with appropriate class name of your service which you want to stop.

              }

            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
        	System.out.println("State changed: " + state+" Offhook");
                //you will be here at **STEP 3**
             // you will be here when you cut call
            callEnded=true;
            break;
        case TelephonyManager.CALL_STATE_RINGING:
        	System.out.println("State changed: " + state+" Ringing");	
                //you will be here at **STEP 2**

            break;


        default:
            break;
        }
    }

}
