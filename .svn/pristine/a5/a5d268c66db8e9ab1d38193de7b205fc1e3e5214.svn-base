package de.jsteeg.controlino.common.controlinoMain;

import de.jsteeg.controlino.common.arduino.model.ArduinoBase;
import de.jsteeg.controlino.common.arduino.model.ArduinoVirtual;

public class ArduinoDataFiller {
    static ArduinoBase mArduino;
    static PinDataContainer mPinData;

    static void setDataset(ArduinoVirtual arduino, PinDataContainer pinData) {
        mArduino = arduino;
        mPinData = pinData;
    }

    static void updateArduinoData() {
        for (int i = 1; i <= mArduino.countPins(); i++) {
            mArduino.setPin(mPinData.getArduinoPin(i), i);
        }
    }


}
