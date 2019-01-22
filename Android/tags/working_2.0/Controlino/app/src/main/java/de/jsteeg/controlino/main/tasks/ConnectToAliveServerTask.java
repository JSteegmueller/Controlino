package de.jsteeg.controlino.main.tasks;

import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;

public class ConnectToAliveServerTask implements Runnable {

    private ControlinoClient mCClient;

    public ConnectToAliveServerTask(ControlinoClient cClient) {
        mCClient = cClient;
    }

    @Override
    public void run() {
        mCClient.initAndConnectToServer();
    }
}
