package de.jsteeg.controlino.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class Utils {

    //writes network status (WiFi or no Network) in CommonVars
    @SuppressWarnings("deprecation")
    public static void checkWifiAvaiableAndWriteInGlobalVars(Activity mActivity) {
        //get network state
        NetworkInfo myNetworkInfo;
        ConnectivityManager myConnectivityManager;

        myConnectivityManager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        myNetworkInfo = myConnectivityManager.getActiveNetworkInfo();

        //WiFi available ?
        if (myNetworkInfo != null) {
            if (myNetworkInfo.getTypeName().equals("WIFI")) {
                WifiManager wifiManager = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                CommonVars.mLocalIpAddr = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                CommonVars.mWifiConnected = true;
            } else {
                CommonVars.mWifiConnected = false;
            }
        } else {
            CommonVars.mWifiConnected = false;
        }
    }
}
