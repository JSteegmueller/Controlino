package de.jsteeg.controlino.common.controlinoMain;

import android.util.Log;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.ArduinoPin;

public class ControlinoParser {
    private final int MESSAGE_ARDUINO_TYPE_ANSWER = 1;
    private final int MESSAGE_ARDUINO_PIN_ANSWER = 3;
    private final int MESSAGE_ARDUINO_ALIVE_ANSWER = 5;

    //contains the received data
    private ArrayList<Byte> mBuffer;

    private String cmStart = "CMS";
    private String cmEnde = "CME";

    public ControlinoParser(ArrayList<Byte> buffer) {
        mBuffer = buffer;
    }

    //parse incoming mBuffer to ControlinoMessage
    public ControlinoMessage parseBuffer() {
        ControlinoMessage rcvControlinoMessage = new ControlinoMessage();

        //default type ( if parsing fails )
        rcvControlinoMessage.type = 404;

        boolean isControlinoMessage = true;

        //index of mBuffer
        int index = 0;

        //does the mBuffer contain data?
        if (mBuffer.size() < 1) {
            Log.v("ControlinoMessage", "empty");
            return rcvControlinoMessage;
        }

        //compare with "CMS" to check if mBuffer is an ControlinoMessage
        for (int i = 0; i < cmStart.length(); i++) {
            if (cmStart.charAt(i) != mBuffer.get(i)) {
                isControlinoMessage = false;
                break;
            }
            index++;
        }

        if (isControlinoMessage) {
            //get message type
            rcvControlinoMessage.type = mBuffer.get(index);
            index++;

            //perform parsing depending on type
            switch (rcvControlinoMessage.type) {
                case MESSAGE_ARDUINO_TYPE_ANSWER:
                    Log.v("ControlinoMessage", "AnswArduinoType");

                    rcvControlinoMessage.type = 1;
                    rcvControlinoMessage.arduinoType = mBuffer.get(index);
                    break;
                case MESSAGE_ARDUINO_PIN_ANSWER:
                    Log.v("ControlinoMessage", "AnswPinStatus");

                    int pinNr = 0;

                    if (mBuffer.size() < mBuffer.get(index)) {
                        rcvControlinoMessage.type = 404;
                        return rcvControlinoMessage;
                    }

                    //amount of pins send by Arduino
                    int amountOfPins = mBuffer.get(index);
                    rcvControlinoMessage.initPinArray(amountOfPins, 0);

                    index++;
                    for (; ; ) {
                        //check for max pins
                        if (pinNr >= amountOfPins) {
                            return rcvControlinoMessage;
                        }
                        //check for end of message
                        if (cmEnde.charAt(0) == mBuffer.get(index)) {
                            if (cmEnde.charAt(1) == mBuffer.get(index + 1)) {
                                if (cmEnde.charAt(2) == mBuffer.get(index + 2)) {
                                    return rcvControlinoMessage;
                                }
                            }
                        } else {
                            //read pin io
                            int isOutput;
                            isOutput = (int) mBuffer.get(index);

                            if (isOutput == 1)
                                rcvControlinoMessage.mPinsAnalog[pinNr].setPinIO(ArduinoPin.ArduinoPinIO.OUTPUT);
                            else
                                rcvControlinoMessage.mPinsAnalog[pinNr].setPinIO(ArduinoPin.ArduinoPinIO.INPUT);
                            index++;

                            //read pin value
                            //convert java signed byte into unsigned byte, write into int
                            int val = ((int) mBuffer.get(index)) & 0xFF;
                            rcvControlinoMessage.mPinsAnalog[pinNr].setAnalogValue(val);
                            rcvControlinoMessage.mPinsAnalog[pinNr].setPinNr(pinNr);
                            index++;

                            pinNr++;
                        }
                    }
                case MESSAGE_ARDUINO_ALIVE_ANSWER:
                    Log.v("ControlinoMessage", "AnswAlive");

                    rcvControlinoMessage.type = 5;
                    rcvControlinoMessage.isConnected = true;
                    break;
                default:
                    Log.v("ControlinoMessage", "404");

                    rcvControlinoMessage.type = 404;
            }
        }
        return rcvControlinoMessage;
    }
}
