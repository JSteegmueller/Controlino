package de.jsteeg.controlino.common.arduino.model;

import de.jsteeg.controlino.common.arduino.ArduinoPin;

public class Arduino extends ArduinoBase {

    final public int ARDUINO_MEGA = 1;
    final public int ARDUINO_MICRO = 2;
    final public int ARDUINO_PRO_MINI = 3;
    final public int ARDUINO_UNKNOWN = 404;

    public Arduino(String ipAddress) {
        super(ipAddress);
        mPins = new ArduinoPin[71];
        for (int i = 1; i < 71; i++) {
            mPins[i] = new ArduinoPin(i, 0, ArduinoPin.ArduinoPinState.OUTPUT);
        }
    }

    public void setTypeOnId(int id) {
        switch (id) {
            case ARDUINO_MEGA:
                setType("Arduino Mega");
                break;
            case ARDUINO_MICRO:
                setType("Arduino Micro");
                break;
            case ARDUINO_PRO_MINI:
                setType("Arduino Pro Mini");
                break;
            case ARDUINO_UNKNOWN:
                setType("Unbestimmt");
            default:
                setType("Error");
        }
    }
}
