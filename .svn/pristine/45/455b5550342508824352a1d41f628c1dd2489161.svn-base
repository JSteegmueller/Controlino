package de.jsteeg.controlino.main.tasks;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import de.jsteeg.controlino.common.arduino.model.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.common.controlinoMain.ControlinoParser;
import de.jsteeg.controlino.main.activities.MainActivity;

public class GetPinStateRptTask implements Runnable {

    private Arduino mArduino;
    private ControlinoClient mControlinoClient;
    private Handler mHandler;
    MainActivity.MainHandler mHandlerMain;
    private ControlinoParser mParser;
    private boolean running = true;

    public GetPinStateRptTask(Arduino arduino, Handler handlerSelf, MainActivity.MainHandler handlerMain) {
        mArduino = arduino;
        mHandler = handlerSelf;
        mHandlerMain = handlerMain;
    }

    @Override
    public void run() {
        mControlinoClient = new ControlinoClient(mArduino.getIPAdress());

        //when connection is succesfull
        if (mControlinoClient.initAndConnectToServer()) {
            ArrayList<Byte> replyBuffer;
            replyBuffer = mControlinoClient.requestPinStates();
            mParser = new ControlinoParser(replyBuffer);
        } else {
            //notify MainActivity connection timeout
            Message msgTimeout = new Message();
            msgTimeout.arg1 = 2;
            mHandlerMain.sendMessage(msgTimeout);
        }
        if (running) mHandler.postDelayed(this, 1000);
    }

    public void end(){
        running = false;
    }
}
