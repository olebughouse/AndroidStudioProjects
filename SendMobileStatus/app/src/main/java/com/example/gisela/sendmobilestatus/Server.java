package com.example.gisela.sendmobilestatus;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

import static com.example.gisela.sendmobilestatus.MainActivity.*;

/**
 * Created by gisela on 03.08.2017.
 */

public class Server {

    private int serverPort;
    private String serverAdress;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out_Msg;
    private BufferedReader in_Msg;

    public Server(int serverPort) throws IOException {
        this.serverPort = serverPort;

        //implement new ServerSocket, initialize it with the Port argument
        ServerSocket serverSocket = new ServerSocket(serverPort);
        this.serverSocket = serverSocket;

        //get server IP from the local machine
       // this.serverAdress = Inet4Address.getLocalHost().getHostAddress();
    }


    public void startServer() throws IOException {

        //  listen to a client connection until closeServer() Method is invoked
        while(true) {
            //MainActivity.mTextMessage.setText("Server wartet auf Client");
            Socket clientSocket = this.serverSocket.accept();
            MainActivity.mTextMessage.setText("Client verbunden");
            this.clientSocket = clientSocket;
            // implements reader and writer connection to the connected client socket
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            this.out_Msg = out;
            this.in_Msg = in;
        }
    }

    public void closeServer() throws IOException {
        serverSocket.close();
    }

    //----------------------------------------------
    //
    //		getter and setter methods
    //
    //---------------------------------------------

    public String getServerMsg() throws IOException {
        return this.in_Msg.readLine();
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public String getServerAdress() {
        return this.serverAdress;
    }

    public Socket getClientSocket() {
        return this.clientSocket;
    }

    public PrintWriter getOutMsg() {
        return this.out_Msg;
    }

    public BufferedReader getInMsg() {
        return this.in_Msg;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getLocalIpAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }
}
