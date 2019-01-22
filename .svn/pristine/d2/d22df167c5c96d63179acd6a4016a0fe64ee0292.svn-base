package de.jsteeg.controlino.common.arduino;

public class ArduinoPin {

    private int mPinNr;
    private double mValue;
    private ArduinoPinState mPinState;

    public ArduinoPin(int pinNr, double value, ArduinoPinState pinState) {
        mPinNr = pinNr;
        mValue = value;
        mPinState = pinState;
    }

    public ArduinoPin() {
        mPinNr = 0;
        mValue = 0;
        mPinState = ArduinoPinState.UNKNOWN;
    }

    public ArduinoPinState getPinState() {
        return mPinState;
    }

    public void setPinState(ArduinoPinState mPinState) {
        this.mPinState = mPinState;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double mValue) {
        this.mValue = mValue;
    }

    public void setPinNr(int mPinNr) {
        this.mPinNr = mPinNr;
    }

    public int getPinNr() {
        return mPinNr;
    }

    public enum ArduinoPinState {
        OUTPUT, INPUT, UNKNOWN
    }

}
