package de.jsteeg.controlino.main.tasks;


import android.os.Message;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.model.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.common.controlinoMain.ControlinoMessage;
import de.jsteeg.controlino.common.controlinoMain.ControlinoParser;
import de.jsteeg.controlino.main.activities.MainActivity;

public class GetArduinoTypeTask implements Runnable {

    private Arduino mArduino;
    private ControlinoClient mControlinoClient;
    private MainActivity.MainHandler mHandler;
    private ControlinoParser mParser;

    public GetArduinoTypeTask(Arduino arduino, MainActivity.MainHandler handler, ControlinoClient cClient) {
        mArduino = arduino;
        mHandler = handler;
        mControlinoClient = cClient;
    }

    @Override
    public void run() {

        //notify MainActivity connecting to Arduino
        Message sendingRequest = new Message();
        sendingRequest.arg1 = 0;
        mHandler.sendMessage(sendingRequest);

        ArrayList<Byte> replyBuffer;

        //network, request to Arduino
        replyBuffer = mControlinoClient.sendMessage(0);

        //parse the data of received message into an ControlinoMessage-Object
        mParser = new ControlinoParser(replyBuffer);
        ControlinoMessage theReply = mParser.parseBuffer();

        //parsing success?
        if (theReply.type != 404) {
            mArduino.setTypeOnId(theReply.arduinoType);

            //notify MainActivity data received
            Message msgReady = new Message();
            msgReady.arg1 = 1;
            mHandler.sendMessage(msgReady);
        } else {
            //notify MainActivity parsing failed
            Message msgParseError = new Message();
            msgParseError.arg1 = 4;
            mHandler.sendMessage(msgParseError);
        }
    }
}
