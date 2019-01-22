package de.jsteeg.controlino.main.tasks;

import android.os.Handler;
import android.os.Message;

import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;

public class ConnectToControlinoTask implements Runnable {

    private ControlinoClient mCClient;
    private Handler mHandler;

    public ConnectToControlinoTask(ControlinoClient cClient, Handler handler) {
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
