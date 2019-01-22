package de.jsteeg.controlino.common.controlinoMain;

import java.util.ArrayList;

public class ControlinoParser {
    ArrayList<Byte> mBuffer;

    String cmStart = "CMS";
    String cmEnde = "CME";

    private final int MESSAGE_ARDUINO_TYPE_ANSWER = 1;

    public ControlinoParser(ArrayList<Byte> buffer) {
        mBuffer = buffer;
    }

    //parse incoming buffer to ControlinoMessage
    public ControlinoMessage parseBuffer() {
        ControlinoMessage rcvMessage = new ControlinoMessage();
        rcvMessage.type = 404;
        int messageType;
        boolean isControlinoMessage = true;

        //index of buffer
        int index = 0;

        //Enthält der buffer Daten ?
        if (mBuffer.size() < 1) return rcvMessage;

        //compare with "CMStart" to check if buffer is an ControlinoMessage
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
                    rcvMessage.type = 1;
                    rcvMessage.arduinoType = mBuffer.get(index);
                    break;
                default:
                    rcvMessage.type = 404;
            }
        }
        return rcvMessage;
    }
}
