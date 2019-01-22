package de.jsteeg.controlino.common.arduino;

public class ArduinoPinDigital extends ArduinoPin {
    private double mAnalogValue;
    private DigitalValue mDigitalDigitalValue;

    public ArduinoPinDigital() {
        mPinIO = ArduinoPinIO.UNKNOWN;
        mAnalogValue = 0;
        mDigitalDigitalValue = DigitalValue.UNDEFINED;
        mPinNr = 0;
    }

    public ArduinoPinDigital(int pinNr, ArduinoPinIO io, double value) {
        mPinIO = io;
        mPinNr = pinNr;
        mAnalogValue = value;
    }

    public void setAnalogValue(double val) {
        mAnalogValue = val;
    }
    public double getAnalogValue() {
        return mAnalogValue;
    }

    public void setDigitalValue(DigitalValue val){ mDigitalDigitalValue = val; }
    public DigitalValue getDigitalValue(){return mDigitalDigitalValue; }

    public enum DigitalValue {
        HIGH, LOW, UNDEFINED
    }

}
