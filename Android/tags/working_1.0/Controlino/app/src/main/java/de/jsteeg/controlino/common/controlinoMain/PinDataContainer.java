package de.jsteeg.controlino.common.controlinoMain;

import de.jsteeg.controlino.common.arduino.ArduinoPin;

public class PinDataContainer {
    private ArduinoPin webArduinoPins[];

    public PinDataContainer(int numPins) {
        for (int i = 1; i <= numPins; i++) {
            webArduinoPins[i] = new ArduinoPin();
        }
    }

    public void setPinsFromWebData(int pinNr, double value, ArduinoPin.ArduinoPinState state) {
        webArduinoPins[pinNr].setPinState(state);
        webArduinoPins[pinNr].setValue(value);
    }

    public ArduinoPin getArduinoPin(int pinNr) {
        return webArduinoPins[pinNr];
    }

}
