package de.jsteeg.controlino.common.arduino;

public abstract class ArduinoPin {

    int mPinNr;
    ArduinoPinIO mPinIO;

    public int getPinNr() {
        return mPinNr;
    }

    public void setPinNr(int pinNr) {
        mPinNr = pinNr;
    }

    public ArduinoPinIO getPinIO() {
        return mPinIO;
    }

    public void setPinIO(ArduinoPinIO io) {
        mPinIO = io;
    }

    public enum ArduinoPinIO {
        OUTPUT, INPUT, UNKNOWN
    }

}
