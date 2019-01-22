package de.jsteeg.controlino.common.controlinoMain;

import android.util.Log;

import java.util.ArrayList;

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

        int messageType;
        boolean isControlinoMessage = true;

        //index of mBuffer
        int index = 0;

        //does the mBuffer contain data?
        if (mBuffer.size() < 1) {
            Log.v("ControlinoMessage", "empty");
            return rcvControlinoMessage;
        }

        //compare with "CMStart" to check if mBuffer is an ControlinoMessage
        for (int i = 0; i < cmStart.length(); i++) {
            if (cmStart.charAt(i) != mBuffer.get(i)) {
                isControlinoMessage = false;
                break;
            }
            index++;
        }

        if (isControlinoMessage) {
            //get message type
            messageType = mBuffer.get(index);
            index++;

            //perform parsing depending on type
            switch (messageType) {
                case MESSAGE_ARDUINO_TYPE_ANSWER:
                    Log.v("ControlinoMessage", "ArduinoType");

                    rcvControlinoMessage.type = 1;
                    rcvControlinoMessage.arduinoType = mBuffer.get(index);
                    break;
                case MESSAGE_ARDUINO_PIN_ANSWER:
                    Log.v("ControlinoMessage", "PinStatus");

                    int pinNr = 1;

                    //amount of pins send by Arduino
                    rcvControlinoMessage.initPinArray(mBuffer.get(index));

                    index++;
                    for(;;) {
                        //check for end of message
                        if (cmEnde.charAt(0) == mBuffer.get(index)) {
                            if (cmEnde.charAt(1) == mBuffer.get(index + 1)) {
                                if (cmEnde.charAt(2) == mBuffer.get(index + 2)) {
                                    return rcvControlinoMessage;
                                }
                            }
                        } else {
                            rcvControlinoMessage.mPins[pinNr].setValue(mBuffer.get(index));
                            rcvControlinoMessage.mPins[pinNr].setPinNr(pinNr);
                            index++;
                            pinNr++;
                        }
                    }
                case MESSAGE_ARDUINO_ALIVE_ANSWER:
                    Log.v("ControlinoMessage", "ALIVE");

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
