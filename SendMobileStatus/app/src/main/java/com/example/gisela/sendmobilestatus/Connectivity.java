package com.example.gisela.sendmobilestatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import java.util.HashMap;

/**
 * Check mobile device connection type
 * @author Oliver Backhaus (C)2017
 *
 */
public class Connectivity extends PhoneStateListener {
    protected HashMap<Integer,String> mobileTypeToString;
    protected int mSignalStrength = 123;

    Connectivity() {
        mobileTypeToString = new <Integer, String>HashMap();
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_1xRTT, "1xRTT");                       // ~ 50-100 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_CDMA, "CDMA");                         // ~ 14-64 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_EDGE, "EDGE");                         // ~ 50-100 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_EVDO_0, "EVDO 0");                     // ~ 400-1000 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_EVDO_A, "EVDO A");                     // ~ 600-1400 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_GPRS, "GPRS");                         // ~ 100 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_HSDPA, "HSDPA");                       // ~ 2-14 Mbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_HSDPA, "HSPA");                        // ~ 700-1700 kbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_HSUPA, "HSUPA");                       // ~ 1-23 Mbps
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_UMTS, "UMTS");                         // ~ 400-7000 kbps
        /*
        * Above API level 7, make sure to set android:targetSdkVersion
        * to appropriate level to use these
        */
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_EHRPD, "EHRPD");                       // ~ 1-2 Mbps           // API level 11
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_EVDO_B, "EVDO B");                     // ~ 5 Mbps             // API level 9
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_HSPAP, "HSPAP");                       // ~ 10-20 Mbps         // API level 13
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_IDEN, "IDEN");                         // ~25 kbps             // API level 8
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_LTE, "LTE");                           // ~ 10+ Mbps           // API level 11
        mobileTypeToString.put(TelephonyManager.NETWORK_TYPE_UNKNOWN, "Network Type unknown");      // Unknown


    }

    /**
     * Returns the connection type as a String mgs
     * @param context
     * @return String ConnectionType
     */
    protected String getConnectivityType(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        int type = info.getType();
        int subType = info.getSubtype();

        if(type == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        else
            return mobileTypeToString.get(subType);
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        this.mSignalStrength = signalStrength.getGsmSignalStrength();
    }


    public int getSignalStrength() {
        return this.mSignalStrength;
    }


    /**
     * Get the network info
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }



    /**
     * Returns the connection type as a String mgs
     * @param context
     * @return String ConnectionType
     */
    public static String getConnetionType(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        int type = info.getType();
        int subType = info.getSubtype();



        if (type == ConnectivityManager.TYPE_WIFI) {
            return "WIFI";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "1xRTT ()"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "CDMA"; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "EDGE"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "EVDO 0"; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "EVDO A"; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "GPRS"; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "HSDPA"; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "HSPA"; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "HSUPA"; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "UMTS"; // ~ 400-7000 kbps
            /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return "EHRPD"; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return "EVDO B"; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return "HSPAP"; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return "IDEN"; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return "LTE"; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return "unknown connection type";
            }
        } else {
            return "unknown connection type";
        }
    }
}
