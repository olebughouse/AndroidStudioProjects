package com.example.gisela.sendmobilestatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.TextView;
import android.net.NetworkInfo;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Runnable {

    public static TextView mTextMessage;
    private static Server server;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    getMobileStatus();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    //startService1();
                    startConnection();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected void getMobileStatus() {
        Connectivity con = new Connectivity();
        String connectionType = con.getConnectivityType(this);
        int signalStrength = con.getSignalStrength();
        mTextMessage.setText("Is connected to: " + connectionType + "\n Signalstaerke: " + signalStrength);
    }

    public void startService1() {
        //System.out.println("Das ist noch kein Service");
        //mTextMessage.setText("Das ist noch kein Service");
        // use this to start and trigger a service
        Intent i = new Intent(getBaseContext(), Send_Mobile_Status_Service.class);
        // potentially add data to the intent
        //i.putExtra("KEY1", "Value to be used by the service");
        startService(i);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String recievedString = bundle.getString(Send_Mobile_Status_Service.textMsgFromService);
                mTextMessage.setText(recievedString);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                Send_Mobile_Status_Service.textMsgFromActivity));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    protected void startConnection() {
        try {

            // starts a new Thread in which the server runs
                //Thread serverThread = new Thread(new MainActivity());
                //serverThread.start();
            //WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
            //String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            //mTextMessage.setText("Server Adress: "+ip);
            server = new Server(60166);
            //mTextMessage.setText("Server Adress: " + server.getLocalIpAddress());
            server.startServer();


            //setting up a client, connect to server and send a message to the server
            //Client client = new Client("localhost", 60164);
            //client.sendMsgToServer("Hello Server, my friend!!");

            //get and print the recieved message from the server
            //System.out.println(server.getServerMsg());
                //mTextMessage.setText(server.getServerMsg());

            //repeat message send and print, in order to see if the server still listen
            //client.sendMsgToServer("second Msg");
            //System.out.println(server.getServerMsg());

            // print serveradress
            //System.out.println(server.getServerAdress());

            //shut down the client and server and server Thread
            //client.closeServerConnection();
            //server.closeServer();
                //serverThread.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            //mTextMessage.setText("Server Thread started");
            server = new Server(60166);
            server.startServer();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
