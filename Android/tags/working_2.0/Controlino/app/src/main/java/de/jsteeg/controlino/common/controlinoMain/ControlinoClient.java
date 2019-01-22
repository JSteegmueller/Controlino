package de.jsteeg.controlino.common.controlinoMain;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ControlinoClient {
    private int mPort;
    private String mIpAdress;
    private Socket mTCPSocket;
    private InputStream mInStream;
    private OutputStream mOutStream;
    private ArrayList<Byte> mBufferList;
    byte mBuffer;



    public ControlinoClient(String ipAdress, int port) {
        mIpAdress = ipAdress;
        mTCPSocket = new Socket();
        mPort = port;
    }

    //false: connection error, true: connected
    public boolean initAndConnectToServer() {
        try {
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

    public ArrayList<Byte> sendMessage(int messageID){
        try {
            if (mTCPSocket.isConnected()) {
                //wait setSoTimeout(secs) for Answer
                mTCPSocket.setSoTimeout(1000);
                mOutStream = mTCPSocket.getOutputStream();
                mInStream = mTCPSocket.getInputStream();

                //send request to Arduino
                switch (messageID){
                    case 0:
                        mOutStream.write(ControlinoByteMessageBuilder.getArduinoTypeRequest());
                        Log.v("ControlinoMessage", "ArduinoTypeSend");
                        break;
                    case 2:
                        mOutStream.write(ControlinoByteMessageBuilder.getPinStatusRequest());
                        Log.v("ControlinoMessage", "PinStatusSend");
                        break;
                    case 4:
                        mOutStream.write(ControlinoByteMessageBuilder.getAliveRequest());
                        Log.v("ControlinoMessage", "AliveSend");
                        break;
                }

                mOutStream.flush();

                //read data
                mBufferList = new ArrayList<>();
                mBuffer = (byte) mInStream.read();
                while (mBuffer != -1){
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
