package de.jsteeg.controlino.common.controlinoMain;

import java.util.ArrayList;

public class ControlinoByteMessageBuilder {

    //Controlino message types
    private final static int MESSAGE_REQUEST_ARDUINO_TYPE = 0;
    private final static int MESSAGE_REQUEST_PIN_STATUS = 2;
    private final static int MESSAGE_ALIVE_REQUEST = 4;

    private static ArrayList<Byte> request;

    public static byte[] getArduinoTypeRequest() {
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_REQUEST_ARDUINO_TYPE);
        addControlinoEndeMessage(request);

        return tobyte(request);
    }

    public static byte[] getPinStatusRequest() {
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_REQUEST_PIN_STATUS);
        addControlinoEndeMessage(request);

        return tobyte(request);
    }

    public static byte[] getAliveRequest(){
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_ALIVE_REQUEST);
        addControlinoEndeMessage(request);

        return tobyte(request);
    }

    private static void initArray() {
        request = new ArrayList<>();
        request.clear();
    }

    //add start of every Controlino message
    private static void addControlinoStartMessage(ArrayList<Byte> s) {
        String startMessage = "CMS";
        for (int i = 0; i < startMessage.length(); i++) {
            s.add((byte) startMessage.charAt(i));
        }
    }

    //add end of every Controlino message
    private static void addControlinoEndeMessage(ArrayList<Byte> s) {
        String endMessage = "CME";
        for (int i = 0; i < endMessage.length(); i++) {
            s.add((byte) endMessage.charAt(i));
        }
    }

    //ArrayList<Byte> to byte[]
    private static byte[] tobyte(ArrayList<Byte> byteList) {
        int sizeOfRequest = request.size();

        //ArrayList<Byte> to Byte[]
        Byte[] requestByteArray = byteList.toArray(new Byte[sizeOfRequest]);

        //make byte[] with size of the request
        byte[] requestNativeByteArray = new byte[sizeOfRequest];

        int aktArrayIndex = 0;
        for (Byte b : requestByteArray) {
            requestNativeByteArray[aktArrayIndex] = b;
            aktArrayIndex++;
        }
        return requestNativeByteArray;
    }
}
