package de.jsteeg.controlino.main.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.main.MainHandler;
import de.jsteeg.controlino.main.adapters.PinRecyclerViewAdapter;
import de.jsteeg.controlino.main.tasks.GetPinStateRptTask;
import de.jsteeg.controlino.main.tasks.SingleControlinoRequestTask;


public class PinStatusFragment extends Fragment {

    PinStatusFragment mf = this;

    //Layout
    private LinearLayoutManager mPinRecyclerViewLManager;
    private PinRecyclerViewAdapter mPinRecyclerViewAdapter;
    private RecyclerView pinRecView;
    private AppCompatButton btnGetArduinoType;
    private AppCompatButton btnStart;
    private TextView tvArduinoIp;
    private TextView tvArduinoType;

    //Controlino
    private Arduino mArduino;
    private ControlinoClient mCClient;
    private ControlinoClient mAliveClient;

    //Threads
    private GetPinStateRptTask pinStateTask;
    private Handler mPinTaskHandler;
    private boolean pinTaskIsRunning = false;

    public PinStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        /*init view objects*/
        //TextViews
        tvArduinoType = getView().findViewById(R.id.textViewArduinoType);
        tvArduinoIp = getView().findViewById(R.id.textViewArduinoAddress);

        //Buttons
        btnGetArduinoType = getView().findViewById(R.id.buttonArduinoType);
        btnStart = getView().findViewById(R.id.buttonStart);

        //Layout
        pinRecView = getView().findViewById(R.id.recyclerViewPins);

        setUpOnClickListener();

        //setup views
        setUpPinRecyclerView();
        tvArduinoIp.setText(mArduino.getIPAdress());

        super.onActivityCreated(savedInstanceState);
    }

    private void setUpPinRecyclerView() {
        mPinRecyclerViewLManager = new LinearLayoutManager(getActivity());
        pinRecView.setLayoutManager(mPinRecyclerViewLManager);
        mPinRecyclerViewAdapter = new PinRecyclerViewAdapter(mArduino);
        pinRecView.setAdapter(mPinRecyclerViewAdapter);
        mPinRecyclerViewAdapter.notifyDataSetChanged();
    }

    //Buttons
    private void setUpOnClickListener() {

        //request type of Arduino
        btnGetArduinoType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new SingleControlinoRequestTask(mArduino, new MainHandler(mf), mCClient, 0));
                thread.start();
            }
        });

        //start or end a thread that auto-restarts every 2 seconds --> requests pin-data from Arduino
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pinTaskIsRunning) {
                    pinTaskIsRunning = true;
                    btnStart.setText(R.string.end);

                    //create new thread for gathering pin-data from Arduino
                    HandlerThread mHandlerThread = new HandlerThread("PinThread");
                    mHandlerThread.start();
                    mPinTaskHandler = new Handler(mHandlerThread.getLooper());
                    pinStateTask = new GetPinStateRptTask(mArduino, mPinTaskHandler, new MainHandler(mf), mCClient);
                    mPinTaskHandler.post(pinStateTask);
                } else {
                    //stop thread for gathering pin-data
                    pinTaskIsRunning = false;
                    btnStart.setText(R.string.start);
                    pinStateTask.end();
                }
            }
        });
    }

    //set objects this class needs from parent-activity
    public void setObjects(Arduino arduino,
                           ControlinoClient cclient,
                           ControlinoClient aliveClient) {
        mArduino = arduino;
        mCClient = cclient;
        mAliveClient = aliveClient;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pinStateTask != null) pinStateTask.end();
    }

    @Override
    public void onResume() {
        super.onResume();
        tvArduinoType.setText(mArduino.getType());
    }

    public void controlinoRequestSend() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.sendingRequest, Toast.LENGTH_SHORT).show();
    }

    public void controlinoTypeAnswerReceived() {
        Toast.makeText(getActivity().getApplicationContext(), mArduino.getType(), Toast.LENGTH_SHORT).show();
        tvArduinoType.setText(mArduino.getType());
    }

    public void controlinoParsinfFailed() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.parsingFailed, Toast.LENGTH_LONG).show();
    }

    public void controlinoPinsReceived() {
        mPinRecyclerViewAdapter.notifyDataSetChanged();
    }

}
