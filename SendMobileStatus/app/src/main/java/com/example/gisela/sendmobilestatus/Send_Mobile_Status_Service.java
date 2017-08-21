package com.example.gisela.sendmobilestatus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Service class
 * @author Oliver Backhaus (C)2017
 *
 */

public class Send_Mobile_Status_Service extends Service {

    public static final String textMsgFromService = "empty Msg";
    public static final String textMsgFromActivity = "empty Msg 2";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Intent intent = new Intent(textMsgFromService);
        intent.putExtra(textMsgFromService, textMsgFromService);
        sendBroadcast(intent);
        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        //MainActivity.mTextMessage.setText("Das ist ein Service");
        //stopSelf();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
