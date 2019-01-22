package de.jsteeg.controlino.main.tasks;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.ArduinoPin;
import de.jsteeg.controlino.common.arduino.model.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.common.controlinoMain.ControlinoMessage;
import de.jsteeg.controlino.common.controlinoMain.ControlinoParser;
import de.jsteeg.controlino.main.activities.MainActivity;


//get the value of all pins of the connected Arduino
public class GetPinStateRptTask implements Runnable {

    MainActivity.MainHandler mHandlerMain;
    private Arduino mArduino;
    private ControlinoClient mControlinoClient;
    private Handler mHandler;
    private ControlinoParser mParser;
    private boolean running = true;

    public GetPinStateRptTask(Arduino arduino, Handler handlerSelf, MainActivity.MainHandler handlerMain, ControlinoClient cClient) {
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
        for (int pinNumber = 1 ; pinNumber < recieved.mPins.length  ; pinNumber++ ) {
            mArduino.setPin(recieved.mPins[pinNumber], pinNumber);
        }

        //notify main-activity ready
        Message ready = new Message();
        ready.arg1 = 6;
        mHandlerMain.sendMessage(ready);

        if (running) mHandler.postDelayed(this, 2000);
    }

    public void end() {
        running = false;
    }
}
