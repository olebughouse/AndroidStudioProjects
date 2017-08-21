package myapplication.tutorialspoint7.example.com.exampleserver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import java.util.HashMap;

/**
 * Check mobile device connection type
 * @author Oliver Backhaus (C) 2017
 *
 */
public class Connectivity extends PhoneStateListener {
    protected HashMap<Integer,String> mobileTypeToString;
    protected int mSignalStrength = 123;

    /**
     *    Connectivity class creates a hashmap with different NETWORK_TYPE enums
     *    The class has different getter methods for identify the network status and signal quality
     */
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
     * Returns the connection type enum as a String message
     * @param context
     * @return String ConnectionType
     */
    protected String getConnectivityType(Context context) {
        NetworkInfo netInfo = Connectivity.getNetworkInfo(context);
        int type = netInfo.getType();
        int subType = netInfo.getSubtype();

        if(netInfo != null) {
            if (type == ConnectivityManager.TYPE_WIFI)
                return "WIFI";
            else
                return mobileTypeToString.get(subType);
        } else
            return "not able to identify a network";
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
     * @return NetworkInfo
     */
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return Boolean
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return Boolean
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return Boolean
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }


}
