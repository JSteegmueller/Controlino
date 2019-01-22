package de.jsteeg.controlino.start.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.CommonVars;
import de.jsteeg.controlino.common.Utils;
import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.main.activities.MainActivity;

public class StartActivity extends AppCompatActivity {

    //Layout
    private TextView tvDeviceIp;
    private EditText etIpServer;
    private AppCompatButton btnSetIp;
    private AppCompatButton btnConnect;

    //Controlino
    private Arduino mArduino;

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mSharedPrefsEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /*init view objects*/
        //TextViews
        tvDeviceIp = findViewById(R.id.textViewLocalIp);

        //buttons
        btnSetIp = findViewById(R.id.buttonIp);
        btnConnect = findViewById(R.id.buttonConnect);

        //EditText
        etIpServer = findViewById(R.id.editTextIp);

        //Controlino setup
        mArduino = new Arduino("127.0.0.1");

        //check for saved ip
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefsEdit = mSharedPrefs.edit();
        checkForDataInSharedPrefs();

        //check for WiFi-Connection
        Utils.checkWifiAvaiableAndWriteInGlobalVars(this);


        if (CommonVars.mWifiConnected) {
            tvDeviceIp.setText(CommonVars.mLocalIpAddr);
        } else {
            tvDeviceIp.setText(R.string.wifi_error);
        }

        setUpOnClickListeners();
    }

    private void checkForDataInSharedPrefs() {
        // saved ip available ?
        if (mSharedPrefs.contains("IP")) {
            String ip = mSharedPrefs.getString("IP", null);
            if (ip != null) {
                mArduino.setIpAdress(ip);
                etIpServer.setText(mArduino.getIPAdress());
            }
        }
    }

    private void setUpOnClickListeners() {
        //set IP-address
        btnSetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArduino.setIpAdress(etIpServer.getText().toString());

                //write new ip into shared prefs.
                mSharedPrefsEdit.putString("IP", mArduino.getIPAdress());
                mSharedPrefsEdit.apply();
            }
        });

        //start main activity
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                startMain.putExtra("ip", mArduino.getIPAdress());
                startActivity(startMain);
            }
        });
    }

}
