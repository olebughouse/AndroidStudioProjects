package myapplication.tutorialspoint7.example.com.exampleserver;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    EditText editTextAddress, editTextPort, editTextMsg;
    Button buttonConnect, buttonDisconnect, buttonSend, buttonSendMobile, buttonDisplayMobile;
    TextView textViewState, textViewRx;

    ClientHandler clientHandler;
    ClientThread clientThread;
    HashMap<String, Integer> connectionStatusServer;
    int defaultButtonColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        editTextMsg = (EditText) findViewById(R.id.msgtosend);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonDisconnect = (Button) findViewById(R.id.disconnect);
        buttonDisplayMobile = (Button) findViewById(R.id.displayMobile);
        buttonSend = (Button)findViewById(R.id.send);
        buttonSendMobile = (Button)findViewById(R.id.sendMobile);
        // old output of server connection status
        //textViewState = (TextView)findViewById(R.id.state);
        textViewRx = (TextView)findViewById(R.id.received);

        buttonDisconnect.setEnabled(false);
        buttonDisplayMobile.setEnabled(false);
        buttonDisplayMobile.setEnabled(true);
        buttonSend.setEnabled(false);
        buttonSendMobile.setEnabled(false);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonDisconnect.setOnClickListener(buttonDisConnectOnClickListener);
        buttonDisplayMobile.setOnClickListener(buttonDisplayMobileOnClickListener);
        buttonSend.setOnClickListener(buttonSendOnClickListener);
        buttonSendMobile.setOnClickListener(buttonSendMobileOnClickListener);

        clientHandler = new ClientHandler(this);

        defaultButtonColor = buttonConnect.getTextColors().getDefaultColor();

        connectionStatusServer = new <String, Integer> HashMap();
        connectionStatusServer.put("disconnected", Color.parseColor("red"));
        connectionStatusServer.put("connecting...", Color.parseColor("yellow"));
        connectionStatusServer.put("connected", Color.parseColor("green"));
        connectionStatusServer.put("default", defaultButtonColor);
    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    clientThread = new ClientThread(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),
                            clientHandler);
                    clientThread.start();

                    buttonConnect.setEnabled(false);
                    buttonDisconnect.setEnabled(true);
                    buttonSend.setEnabled(true);
                    buttonSendMobile.setEnabled(true);
                }
            };

    View.OnClickListener buttonDisConnectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(clientThread != null){
                clientThread.setRunning(false);
            }

            buttonConnect.setEnabled(true);
            buttonDisconnect.setEnabled(false);
            buttonSend.setEnabled(false);
            buttonSendMobile.setEnabled(false);
            buttonDisconnect.setTextColor(connectionStatusServer.get("disconnected"));
            buttonConnect.setTextColor(connectionStatusServer.get("default"));
        }
    };

    View.OnClickListener buttonDisplayMobileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msgToSend = getMobileStatus();
            updateRxMsg("Is connected to: " +msgToSend);
        }
    };

    View.OnClickListener buttonSendOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(clientThread != null){
                String msgToSend = editTextMsg.getText().toString();
                clientThread.txMsg(msgToSend);
            }
        }
    };

    View.OnClickListener buttonSendMobileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(clientThread != null){
                String msgToSend = getMobileStatus();
                clientThread.txMsg("Is connected to: " +msgToSend);
            }
        }
    };

    private void updateState(String state){
        if(state != null)
            buttonConnect.setTextColor(connectionStatusServer.get(state));
        buttonDisconnect.setTextColor(connectionStatusServer.get("default"));

        // old output of server connection status
        // textViewState.setText(state);
    }

    private void updateRxMsg(String rxmsg){
        textViewRx.append(rxmsg + "\n");
    }

    private void clientEnd(){
        clientThread = null;
        // old output of server connection status
        // textViewState.setText("clientEnd()");
        buttonConnect.setEnabled(true);
        buttonDisconnect.setEnabled(false);
        buttonSend.setEnabled(false);
        buttonSendMobile.setEnabled(false);
    }

    public static class ClientHandler extends Handler {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private MainActivity parent;

        public ClientHandler(MainActivity parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateState((String)msg.obj);
                    break;
                case UPDATE_MSG:
                    parent.updateRxMsg((String)msg.obj);
                    break;
                case UPDATE_END:
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }

    }

    protected String getMobileStatus() {
        Connectivity con = new Connectivity();
        String connectionType = con.getConnectivityType(this);
        return connectionType;
    }
}
