package de.jsteeg.controlino.common.controlinoMain;

import de.jsteeg.controlino.common.arduino.ArduinoPinDigital;

public class ControlinoMessage {
    public int type;
    public int arduinoType;
    public boolean isConnected;
    public ArduinoPinDigital mPinsAnalog[];
    public boolean changedPinSuccess;

    public ControlinoMessage() {
    }

    public void initPinArray(int numPinsAnalog, int numPinsDigital) {
        mPinsAnalog = new ArduinoPinDigital[numPinsAnalog];
        for (int i = 0; i < mPinsAnalog.length; i++) {
            mPinsAnalog[i] = new ArduinoPinDigital();
        }
    }
}
