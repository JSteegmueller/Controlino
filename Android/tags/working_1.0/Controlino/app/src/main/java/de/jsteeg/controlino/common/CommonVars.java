package de.jsteeg.controlino.common;

import de.jsteeg.controlino.common.arduino.model.ArduinoBase;

/**
 * Created by jsteegmuller on 07.02.18.
 */

public class CommonVars {
    static public ArduinoBase mArduino;
    static public boolean mWifiConnected = false;
    static public String mLocalIpAddr = "0.0.0.0";
}
