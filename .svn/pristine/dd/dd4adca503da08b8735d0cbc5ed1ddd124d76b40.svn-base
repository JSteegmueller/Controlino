package de.jsteeg.controlino.common.controlinoMain;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ControlinoClient {
    final int port = 404;
    private String mIpAdress;
    private ControlinoParser mParser;
    private Socket mTCPSocket;
    private InputStream mInStream;
    private OutputStream mOutStream;
    private ArrayList<Byte> mBuffer;


    public ControlinoClient(String ipAdress) {
        mIpAdress = ipAdress;
        mTCPSocket = new Socket();
        mBuffer = new ArrayList<>();
    }

    //false: connection error, true: connected
    public boolean initAndConnectToServer() {
        try {
            mTCPSocket.connect(new InetSocketAddress(mIpAdress, port), 1000);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Byte> requestPinStates() {
        int bufferIndex = 0;
        try {
            if (mTCPSocket.isConnected()) {
                mOutStream = mTCPSocket.getOutputStream();
                mInStream = mTCPSocket.getInputStream();

                //send request to Arduino
                mOutStream.write(ControlinoMessageBuilder.getPinStatusRequest());
                mOutStream.flush();

                //wait setSoTimeout(secs) for Answer and read
                mTCPSocket.setSoTimeout(1000);
                for (;;) {
                    mBuffer.add((byte) mInStream.read());
                    //check for end of inputStream
                    if (mBuffer.get(bufferIndex) == -1) {
                        mBuffer.remove(bufferIndex);   // remove -1 from the buffer
                        mInStream.close();
                        break;
                    } else bufferIndex++;
                }
                mTCPSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBuffer;
    }

    public ArrayList<Byte> requestArduinoType() {
        int bufferIndex = 0;
        try {
            if (mTCPSocket.isConnected()) {
                mOutStream = mTCPSocket.getOutputStream();
                mInStream = mTCPSocket.getInputStream();

                //send request to Arduino
                mOutStream.write(ControlinoMessageBuilder.getArduinoTypeRequest());
                mOutStream.flush();

                //wait setSoTimeout(secs) for Answer and read
                mTCPSocket.setSoTimeout(1000);
                for (;;) {
                    mBuffer.add((byte) mInStream.read());
                    //check for end of inputStream
                    if (mBuffer.get(bufferIndex) == -1) {
                        mBuffer.remove(bufferIndex);   // remove -1 from the buffer
                        mInStream.close();
                        break;
                    } else bufferIndex++;
                }
                mTCPSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBuffer;
    }
}
