package de.jsteeg.controlino.common.controlinoMain;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.ArduinoPinDigital;

public class ControlinoClient {
    byte mBuffer;
    boolean sw = false;
    private int mPort;
    private String mIpAdress;
    private Socket mTCPSocket;
    private InputStream mInStream;
    private OutputStream mOutStream;
    private ArrayList<Byte> mBufferList;
    private ControlinoByteMessageBuilder cmb;
    private ArduinoPinDigital mArduinoPin;


    public ControlinoClient(int port) {
        mIpAdress = "127.0.0.1";
        mPort = port;
        cmb = new ControlinoByteMessageBuilder();
    }

    public void setIpAdress(String ip) {
        mIpAdress = ip;
    }

    //false: connection error, true: connected
    public boolean initAndConnectToServer() {
        try {
            mTCPSocket = new Socket();
            mBufferList = new ArrayList<>();
            mTCPSocket.connect(new InetSocketAddress(mIpAdress, mPort), 2000);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeConnection() {
        try {
            mTCPSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedPin(ArduinoPinDigital pin){
        mArduinoPin = pin;
    }

    public ArrayList<Byte> sendMessage(int messageID) {
        try {
            if (mTCPSocket.isConnected()) {
                mBufferList.clear();
                mBuffer = 0;
                //wait setSoTimeout(secs) for Answer
                mTCPSocket.setSoTimeout(1500);
                mOutStream = mTCPSocket.getOutputStream();
                mInStream = mTCPSocket.getInputStream();

                //send request to Arduino
                switch (messageID) {
                    case 0:
                        mOutStream.write(cmb.getArduinoTypeRequest());
                        Log.v("ControlinoMessage", "ArduinoTypeSend");
                        break;
                    case 2:
                        mOutStream.write(cmb.getPinStatusRequest());
                        Log.v("ControlinoMessage", "PinStatusSend");
                        break;
                    case 4:
                        mOutStream.write(cmb.getAliveRequest());
                        Log.v("ControlinoMessage", "AliveSend");
                        break;
                    case 6:
                        mOutStream.write(cmb.getSetPinRequest(mArduinoPin));
                }

                mOutStream.flush();

                //read data
                mBuffer = (byte) mInStream.read();
                while (mBuffer != -1) {
                    mBufferList.add(mBuffer);
                    mBuffer = (byte) mInStream.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBufferList;
    }
}
