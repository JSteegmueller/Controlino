package de.jsteeg.controlino.main.tasks;


import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.common.controlinoMain.ControlinoMessage;
import de.jsteeg.controlino.common.controlinoMain.ControlinoParser;

public class SingleControlinoRequestTask implements Runnable {

    private Arduino mArduino;
    private ControlinoClient mControlinoClient;
    private Handler mHandler;
    private ControlinoParser mParser;
    private int mMessageType;

    public SingleControlinoRequestTask(Arduino arduino,
                                       Handler handler,
                                       ControlinoClient cClient,
                                       int messageType) {
        mArduino = arduino;
        mHandler = handler;
        mControlinoClient = cClient;
        mMessageType = messageType;

    }

    @Override
    public void run() {

        //notify MainActivity connecting to Arduino
        Message sendingRequest = new Message();
        sendingRequest.arg1 = 0;
        mHandler.sendMessage(sendingRequest);

        ArrayList<Byte> replyBuffer;

        //network, request to Arduino
        mControlinoClient.setSelectedPin(mArduino.getSelectedPin());
        replyBuffer = mControlinoClient.sendMessage(mMessageType);

        //parse the data of received message into an ControlinoMessage-Object
        mParser = new ControlinoParser(replyBuffer);
        ControlinoMessage theReply = mParser.parseBuffer();

        switch (theReply.type) {
            case 1:
                mArduino.setTypeOnId(theReply.arduinoType);

                //notify MainActivity data received
                Message msgReady = new Message();
                msgReady.arg1 = 1;
                mHandler.sendMessage(msgReady);
                break;

            case 7:
                Message msgSuccess = new Message();
                //if (theReply.changedPinSuccess) msgSuccess.arg1 =

                break;

            case 404:
                //notify MainActivity parsing failed
                Message msgParseError = new Message();
                msgParseError.arg1 = 4;
                mHandler.sendMessage(msgParseError);
                break;
        }
    }
}
