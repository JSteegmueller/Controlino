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
        mPinState = ArduinoPinState.INPUT;
    }

    public ArduinoPinState getPinState() {
        return mPinState;
    }

    public double getValue() {
        return mValue;
    }

    public int getPinNr() {
        return mPinNr;
    }

    public void setValue(double mValue) {
        this.mValue = mValue;
    }

    public void setPinState(ArduinoPinState mPinState) {
        this.mPinState = mPinState;
    }

    public enum ArduinoPinState {
        OUTPUT, INPUT
    }

}
