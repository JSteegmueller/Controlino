package de.jsteeg.controlino.main;

import android.os.Handler;
import android.os.Message;

import de.jsteeg.controlino.main.activities.MainActivity;
import de.jsteeg.controlino.main.fragments.PinStatusFragment;
import de.jsteeg.controlino.main.fragments.PinManagerFragment;

//Handler for main-loop, used by threads to communicate with the main-thread
public class MainHandler extends Handler {
    PinStatusFragment mPinStatusFragment;
    MainActivity mMainActivity;
    PinManagerFragment mPinManagerFragment;
    HandlerFor mHandlerFor;

    public MainHandler(PinStatusFragment pinStatusFragment) {
        mPinStatusFragment = pinStatusFragment;
        mHandlerFor = HandlerFor.MAIN_FRAGMENT;

    }

    public MainHandler(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        mHandlerFor = null;
    }

    public MainHandler(PinManagerFragment pinManagerFragment) {
        mPinManagerFragment = pinManagerFragment;
        mHandlerFor = HandlerFor.PIN_MANAGER_FRAGMENT;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mHandlerFor == HandlerFor.MAIN_FRAGMENT) {
            switch (msg.arg1) {
                //ControlinoClient is requesting Arduino-Type from ControlinoServer
                case 0:
                    mPinStatusFragment.controlinoRequestSend();
                    break;

                //ControlinoType-request answer received
                case 1:
                    mPinStatusFragment.controlinoTypeAnswerReceived();
                    break;

                //Parsing failed
                case 4:
                    mPinStatusFragment.controlinoParsinfFailed();
                    break;

                //pins received
                case 6:
                    mPinStatusFragment.controlinoPinsReceived();
                    break;
            }
        } else if (mHandlerFor == HandlerFor.PIN_MANAGER_FRAGMENT) {
            switch (msg.arg1) {
            }
        } else {
            switch (msg.arg1) {
                case 0:
                    break;

                case 2:
                    mMainActivity.controlinoTimeout();
                    break;

                case 3:
                    mMainActivity.controlinoConnected();
                    break;

                case 5:
                    mMainActivity.controlinoDisconnected();
            }
        }
    }

    public enum HandlerFor {
        MAIN_FRAGMENT, PIN_MANAGER_FRAGMENT
    }
}
