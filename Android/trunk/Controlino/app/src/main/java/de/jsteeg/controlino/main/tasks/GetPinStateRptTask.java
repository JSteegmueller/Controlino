package de.jsteeg.controlino.main.tasks;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.common.controlinoMain.ControlinoMessage;
import de.jsteeg.controlino.common.controlinoMain.ControlinoParser;
import de.jsteeg.controlino.main.MainHandler;


//get the value of all pins of the connected Arduino
public class GetPinStateRptTask implements Runnable {

    MainHandler mHandlerMain;
    private Arduino mArduino;
    private ControlinoClient mControlinoClient;
    private Handler mHandler;
    private ControlinoParser mParser;
    private boolean running = true;

    public GetPinStateRptTask(Arduino arduino, Handler handlerSelf, MainHandler handlerMain, ControlinoClient cClient) {
        mArduino = arduino;
        mHandler = handlerSelf;
        mHandlerMain = handlerMain;
        mControlinoClient = cClient;
    }

    @Override
    public void run() {
        ArrayList<Byte> replyBuffer;
        replyBuffer = mControlinoClient.sendMessage(2);
        mParser = new ControlinoParser(replyBuffer);
        ControlinoMessage recieved = mParser.parseBuffer();

        //Write received data into the arduino-object
        if (recieved.type != 404) {
            for (int pinNumber = 0; pinNumber < recieved.mPinsAnalog.length; pinNumber++) {
                mArduino.setDigitalPin(recieved.mPinsAnalog[pinNumber], pinNumber);
            }
        }
        //notify main-activity ready
        Message ready = new Message();
        ready.arg1 = 6;
        mHandlerMain.sendMessage(ready);

        if (running) mHandler.postDelayed(this, 1500);
    }

    public void end() {
        running = false;
    }
}
