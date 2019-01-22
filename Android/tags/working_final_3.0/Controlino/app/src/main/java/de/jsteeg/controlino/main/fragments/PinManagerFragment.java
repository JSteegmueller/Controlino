package de.jsteeg.controlino.main.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.arduino.ArduinoPinDigital;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.main.MainHandler;
import de.jsteeg.controlino.main.adapters.SelectPinSpinnerAdapter;
import de.jsteeg.controlino.main.tasks.SingleControlinoRequestTask;

public class PinManagerFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    //Controlino
    private Arduino mArduino;

    //Views
    private AppCompatButton btnSetPins;
    private AppCompatSpinner spSelectPin;
    private PinManagerFragment fm;
    private ControlinoClient mCClient;
    private SwitchCompat swOnOff;

    public PinManagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pin_manager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fm = this;
        //init Views
        btnSetPins = getView().findViewById(R.id.btnSetPins);
        spSelectPin = getView().findViewById(R.id.spPinSelect);
        spSelectPin.setAdapter(new SelectPinSpinnerAdapter(this.getContext(), R.layout.sp_select_pin_item, mArduino));
        spSelectPin.setOnItemSelectedListener(this);
        swOnOff = getView().findViewById(R.id.swOnOff);

        setUpOnClickListeners();
    }

    private void setUpOnClickListeners() {
        btnSetPins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check switch whether pin on or off
                if (swOnOff.isChecked()) mArduino.getSelectedPin().setDigitalValue(ArduinoPinDigital.DigitalValue.HIGH);
                else mArduino.getSelectedPin().setDigitalValue(ArduinoPinDigital.DigitalValue.LOW);

                //start thread which sets pin
                Thread myThread = new Thread(new SingleControlinoRequestTask(mArduino, new MainHandler(fm), mCClient, 6));
                myThread.start();
            }
        });
    }

    public void setObjects(Arduino arduino, ControlinoClient cclient) {
        mArduino = arduino;
        mCClient = cclient;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mArduino.setSelectedPin(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
