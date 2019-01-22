package de.jsteeg.controlino.common.arduino.model;

import de.jsteeg.controlino.common.arduino.ArduinoPin;

/**
 * Created by jsteegmuller on 07.02.18.
 * Basisklasse für Arduinos, verschiedene Typen (Arduino, ArduinoVirtual) werden Abgeleitet!
 */

abstract public class ArduinoBase {
    protected String mType;
    protected String mIpAdress;
    protected ArduinoPin[] mPins;

    public ArduinoBase(String ipAdress) {
        mIpAdress = ipAdress;
    }

    public void setPin(ArduinoPin pin, int pinNr) {
        mPins[pinNr] = pin;
    }

    public ArduinoPin getPinData(int pinNr) {
        return mPins[pinNr];
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int countPins() {
        return mPins.length - 1;   /* Pins beginnen bei 1, Array bei 0 */
    }

    public String getIPAdress() {
        return mIpAdress;
    }

    public void setIpAdress(String ip) {
        mIpAdress = ip;
    }

}
