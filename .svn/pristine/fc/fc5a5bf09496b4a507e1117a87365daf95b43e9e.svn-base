package de.jsteeg.controlino.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.arduino.ArduinoPin;
import de.jsteeg.controlino.common.arduino.ArduinoPinDigital;

/**
 * Created by jsteegmuller on 07.02.18.
 */

public class PinRecyclerViewAdapter extends RecyclerView.Adapter<PinRecyclerViewAdapter.ViewHolder> {

    private Arduino mArduino;

    public PinRecyclerViewAdapter(Arduino arduino) {
        mArduino = arduino;
    }

    @Override
    public void onBindViewHolder(PinRecyclerViewAdapter.ViewHolder holder, int position) {
        ArduinoPinDigital pin = mArduino.getDigitalPin(position);
        Double value = pin.getAnalogValue();
        int pinNr = pin.getPinNr();
        ArduinoPin.ArduinoPinIO state = pin.getPinIO();
        holder.mTextViewValue.setText(String.valueOf(value));
        holder.mTextViewStatus.setText(state.toString());
        holder.mTextViewPin.setText(String.valueOf(pinNr));
    }

    @Override
    public int getItemCount() {
        return mArduino.countPins();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pin_recyclerview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewPin;
        public TextView mTextViewStatus;
        public TextView mTextViewValue;

        public ViewHolder(View v) {
            super(v);
            mTextViewPin = v.findViewById(R.id.rv_pinText);
            mTextViewStatus = v.findViewById(R.id.rv_status);
            mTextViewValue = v.findViewById(R.id.rv_pinWert);
        }
    }
}
