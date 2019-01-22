package de.jsteeg.controlino.common.arduino;

public class Arduino {

    final private int ARDUINO_MEGA = 1;
    final private int ARDUINO_MICRO = 2;
    final private int ARDUINO_PRO_MINI = 3;
    final private int ARDUINO_UNKNOWN = 404;

    private String mType;
    private String mIpAdress;
    private ArduinoPinDigital[] mPinsAnalog;
    private ArduinoPinDigital selectedPin;


    public Arduino(String ipAddress) {
        mIpAdress = ipAddress;
        mPinsAnalog = new ArduinoPinDigital[70];
        for (int i = 0; i < 70; i++) {
            mPinsAnalog[i] = new ArduinoPinDigital();
            mPinsAnalog[i].setPinNr(i);
        }
        selectedPin = mPinsAnalog[13];
        setTypeOnId(404);
    }

    //set selected pin for switching value on arduino
    public void setSelectedPin(int pNr){
        selectedPin = mPinsAnalog[pNr];
    }

    public ArduinoPinDigital getSelectedPin() {
        return selectedPin;
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
                break;
            default:
                setType("Error");
        }
    }

    public void setDigitalPin(ArduinoPinDigital pin, int pinNr) {
        mPinsAnalog[pinNr] = pin;
    }

    public ArduinoPinDigital getDigitalPin(int pinNr) {
        return mPinsAnalog[pinNr];
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int countPins() {
        return mPinsAnalog.length;
    }

    public String getIPAdress() {
        return mIpAdress;
    }

    public void setIpAdress(String ip) {
        mIpAdress = ip;
    }

    public ArduinoPinDigital[] getPins() {
        return mPinsAnalog;
    }

}
