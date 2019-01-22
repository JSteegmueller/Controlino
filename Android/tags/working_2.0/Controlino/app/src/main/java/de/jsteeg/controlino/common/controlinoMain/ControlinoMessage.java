package de.jsteeg.controlino.common.controlinoMain;

import de.jsteeg.controlino.common.arduino.ArduinoPin;

public class ControlinoMessage {
    public int type;
    public int arduinoType;
    public boolean isConnected;
    public ArduinoPin mPins[];

    public void initPinArray(int numPins){
        mPins = new ArduinoPin[numPins+1];
        for (int i = 1 ; i < mPins.length ; i++ ){
            mPins[i] = new ArduinoPin();
        }
    }
}
