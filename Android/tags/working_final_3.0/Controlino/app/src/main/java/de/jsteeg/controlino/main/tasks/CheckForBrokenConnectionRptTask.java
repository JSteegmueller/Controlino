package de.jsteeg.controlino.main.tasks;

import android.os.Message;

import java.util.ArrayList;

import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.common.controlinoMain.ControlinoMessage;
import de.jsteeg.controlino.common.controlinoMain.ControlinoParser;
import de.jsteeg.controlino.main.MainHandler;


//Checks if Arduino is still alive
public class CheckForBrokenConnectionRptTask implements Runnable {

    android.os.Handler mHandler;
    MainHandler mMainHandler;
    ControlinoClient mCClient;
    boolean isRunning = true;
    int missedAliveMessagesInARow = 0;


    public CheckForBrokenConnectionRptTask(android.os.Handler handler, MainHandler mainHandler, ControlinoClient cClient) {
        mHandler = handler;
        mMainHandler = mainHandler;
        mCClient = cClient;
    }

    @Override
    public void run() {
        if (isRunning) {
            ControlinoMessage answerMessage;
            ArrayList<Byte> answerBuffer;

            //send request to Arduino and parse answer
            answerBuffer = mCClient.sendMessage(4);
            ControlinoParser myParser = new ControlinoParser(answerBuffer);
            answerMessage = myParser.parseBuffer();

            //interrupt the app if connection to Arduino is broken
            if (!answerMessage.isConnected) {
                missedAliveMessagesInARow++;
                if (missedAliveMessagesInARow > 2) {
                    Message brokenPipe = new Message();
                    brokenPipe.arg1 = 5;
                    mMainHandler.sendMessage(brokenPipe);
                }
            } else missedAliveMessagesInARow = 0;

            //restart the task automatically every x seconds
            mHandler.postDelayed(this, 1000);
        }
    }

    //end task
    public void end() {
        isRunning = false;
    }
}
