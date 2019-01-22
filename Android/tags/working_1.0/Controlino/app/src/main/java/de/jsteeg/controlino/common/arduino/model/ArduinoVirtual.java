package de.jsteeg.controlino.common.arduino.model;

import de.jsteeg.controlino.common.arduino.ArduinoPin;

import static de.jsteeg.controlino.common.arduino.ArduinoPin.ArduinoPinState.INPUT;

/**
 * Created by jsteegmuller on 07.02.18.
 */

public class ArduinoVirtual extends ArduinoBase {

    public ArduinoVirtual() {
        super("0.0.0.0");
        ArduinoPin.ArduinoPinState startState = INPUT;
        mType = "Arduino Virtual";
        mPins = new ArduinoPin[21];
        for (int i = 1; i < 21; i++) {
            mPins[i] = new ArduinoPin(i, 0, startState);
        }
    }

    public boolean isVirtuak() {
        return true;
    }
}
