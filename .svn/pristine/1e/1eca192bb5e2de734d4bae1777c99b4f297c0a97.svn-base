package de.jsteeg.controlino.common.controlinoMain;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.ArduinoPinDigital;

public class ControlinoByteMessageBuilder {

    //Controlino message types
    private final int MESSAGE_REQUEST_ARDUINO_TYPE = 0;
    private final int MESSAGE_REQUEST_PIN_STATUS = 2;
    private final int MESSAGE_ALIVE_REQUEST = 4;
    private final int MESSAGE_SET_PIN = 6;

    private ArrayList<Byte> request;

    public byte[] getArduinoTypeRequest() {
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_REQUEST_ARDUINO_TYPE);
        addControlinoEndeMessage(request);

        return tobyte(request);
    }

    public byte[] getPinStatusRequest() {
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_REQUEST_PIN_STATUS);
        addControlinoEndeMessage(request);

        return tobyte(request);
    }

    public byte[] getAliveRequest() {
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_ALIVE_REQUEST);
        addControlinoEndeMessage(request);

        return tobyte(request);
    }

    public byte[] getSetPinRequest(ArduinoPinDigital pin) {
        initArray();
        addControlinoStartMessage(request);
        request.add((byte) MESSAGE_SET_PIN);
        request.add((byte) pin.getPinNr());
        if (pin.getDigitalValue() == ArduinoPinDigital.DigitalValue.HIGH) request.add((byte) 1);
        else request.add((byte) 0);
        addControlinoEndeMessage(request);
        return tobyte(request);
    }

    private void initArray() {
        request = new ArrayList<>();
        request.clear();
    }

    //add start of every Controlino message
    private void addControlinoStartMessage(ArrayList<Byte> s) {
        String startMessage = "CMS";
        for (int i = 0; i < startMessage.length(); i++) {
            s.add((byte) startMessage.charAt(i));
        }
    }

    //add end of every Controlino message
    private void addControlinoEndeMessage(ArrayList<Byte> s) {
        String endMessage = "CME";
        for (int i = 0; i < endMessage.length(); i++) {
            s.add((byte) endMessage.charAt(i));
        }
    }

    //ArrayList<Byte> to byte[]
    private byte[] tobyte(ArrayList<Byte> byteList) {
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
