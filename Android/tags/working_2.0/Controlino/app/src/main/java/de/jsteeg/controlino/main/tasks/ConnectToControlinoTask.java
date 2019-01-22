package de.jsteeg.controlino.main.tasks;

import android.os.Message;

import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.main.activities.MainActivity;

public class ConnectToControlinoTask implements Runnable {

    private ControlinoClient mCClient;
    private MainActivity.MainHandler mHandler;

    public ConnectToControlinoTask(ControlinoClient cClient, MainActivity.MainHandler handler) {
        mCClient = cClient;
        mHandler = handler;
    }

    @Override
    public void run() {
        if (mCClient.initAndConnectToServer()) {
            Message connectSuccess = new Message();
            connectSuccess.arg1 = 3;
            mHandler.sendMessage(connectSuccess);
        } else {
            Message connectError = new Message();
            connectError.arg1 = 2;
            mHandler.sendMessage(connectError);
        }
    }
}
