package de.jsteeg.controlino.main.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.arduino.ArduinoPin;

public class SelectPinSpinnerAdapter extends ArrayAdapter<ArduinoPin> implements SpinnerAdapter {

    private Arduino mArduino;

    public SelectPinSpinnerAdapter(Context context, int resource, Arduino arduino) {
        super(context, resource, arduino.getPins());
        mArduino = arduino;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = li.inflate(R.layout.sp_select_pin_item, parent, false);
        }
        TextView pinNr = convertView.findViewById(R.id.tvSelectPin);
        pinNr.setText(String.valueOf(mArduino.getDigitalPin(position).getPinNr()));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = li.inflate(R.layout.sp_select_pin_item, parent, false);
        }
        TextView pinNr = convertView.findViewById(R.id.tvSelectPin);
        pinNr.setText(String.valueOf(mArduino.getDigitalPin(position).getPinNr()));

        return convertView;
    }
}
