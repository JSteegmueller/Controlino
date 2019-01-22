package de.jsteeg.controlino.main.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.CommonVars;
import de.jsteeg.controlino.common.Utils;
import de.jsteeg.controlino.common.arduino.model.Arduino;
import de.jsteeg.controlino.common.arduino.model.ArduinoVirtual;
import de.jsteeg.controlino.help.activities.HelpActivity;
import de.jsteeg.controlino.main.adapters.PinRecyclerViewAdapter;
import de.jsteeg.controlino.main.tasks.GetArduinoTypeTask;
import de.jsteeg.controlino.main.tasks.GetPinStateRptTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayoutManager mPinRecyclerViewLManager;
    private PinRecyclerViewAdapter mPinRecyclerViewAdapter;
    private Arduino myArduino;

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mSharedPrefsEdit;

    private GetPinStateRptTask  pinStateTask;
    private Handler             mPinTaskHandler;
    private boolean             isRunning = false;

    //Views
    private AppCompatButton btnSetIp;
    private AppCompatButton btnGetArduinoType;
    private AppCompatButton btnStart;
    private EditText etIpServer;
    private TextView tvArduinoIp;
    private TextView tvArduinoType;
    private TextView tvDeviceIp;

    android.support.v7.widget.Toolbar appBarLayMain;
    NavigationView navViewMain;
    DrawerLayout drawLayout;
    RecyclerView pinRecView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myArduino = new Arduino("0.0.0.0");
        CommonVars.mArduino = myArduino;

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefsEdit = mSharedPrefs.edit();

        /*init view objects
        TextViews*/
        tvArduinoType   = findViewById(R.id.textViewArduinoType);
        tvDeviceIp      = findViewById(R.id.textViewLocalIp);
        tvArduinoIp     = findViewById(R.id.textViewArduinoAddress);

        //Buttons
        btnSetIp            = findViewById(R.id.buttonIp);
        btnGetArduinoType   = findViewById(R.id.buttonArduinoType);
        btnStart            = findViewById(R.id.buttonStart);

        //EditText
        etIpServer = findViewById(R.id.editTextIp);

        //Layout
        appBarLayMain   = findViewById(R.id.appBarLayoutMain);
        navViewMain     = findViewById(R.id.navigationViewMain);
        drawLayout      = findViewById(R.id.mainDrawer);
        pinRecView      = findViewById(R.id.recyclerViewPins);

        checkForDataInSharedPrefs();
        setUpOnClickListener();

        Utils.checkWifiAvaiableAndWriteInGlobalVars(this);
        if (CommonVars.mWifiConnected) {
            tvDeviceIp.setText(CommonVars.mLocalIpAddr);
        } else {
            tvDeviceIp.setText(R.string.wifi_error);
        }

        setUpMainToolbar();
        setUpMainDrawer();
        setUpPinRecyclerView();
    }

    private void checkForDataInSharedPrefs() {
        // saved ip available ?
        if (mSharedPrefs.contains("IP")) {
            String ip = mSharedPrefs.getString("IP", null);
            if (ip != null) {
                myArduino.setIpAdress(ip);
                tvArduinoIp.setText(myArduino.getIPAdress());
            }
        }
    }

    private void setUpOnClickListener() {
        btnSetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myArduino.setIpAdress(etIpServer.getText().toString());
                tvArduinoIp.setText(myArduino.getIPAdress());
                mSharedPrefsEdit.putString("IP", myArduino.getIPAdress());
                mSharedPrefsEdit.apply();
            }
        });

        btnGetArduinoType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new GetArduinoTypeTask(myArduino, new MainHandler()));
                thread.start();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning){
                    isRunning = true;
                    btnStart.setText(R.string.end);
                    HandlerThread mHandlerThread = new HandlerThread("PinThread");
                    mHandlerThread.start();
                    mPinTaskHandler = new Handler(mHandlerThread.getLooper());
                    pinStateTask = new GetPinStateRptTask(myArduino, mPinTaskHandler, new MainHandler());
                    mPinTaskHandler.post(pinStateTask);
                }
                else {
                    isRunning = false;
                    btnStart.setText(R.string.start);
                    pinStateTask.end();
                }
            }
        });
    }

    private void setUpMainToolbar() {
        setSupportActionBar(appBarLayMain);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void setUpMainDrawer() {
        navViewMain.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawLayout, appBarLayMain, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        drawLayout.addDrawerListener(mDrawerToggle);
    }

    private void setUpPinRecyclerView() {
        mPinRecyclerViewLManager = new LinearLayoutManager(this);
        pinRecView.setLayoutManager(mPinRecyclerViewLManager);
        mPinRecyclerViewAdapter = new PinRecyclerViewAdapter(new ArduinoVirtual());
        pinRecView.setAdapter(mPinRecyclerViewAdapter);
        mPinRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Hilfe")) {
            Intent startHelp = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(startHelp);
        }
        return super.onOptionsItemSelected(item);
    }

    //Handler for main-loop
    public class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                //Controlino is connecting
                case 0:
                    Toast.makeText(getApplicationContext(), R.string.connectingType, Toast.LENGTH_SHORT).show();
                    break;

                //ControlinoType-request answer
                case 1:
                    Toast.makeText(getApplicationContext(), myArduino.getType(), Toast.LENGTH_SHORT).show();
                    tvArduinoType.setText(myArduino.getType());
                    break;

                //Connection timeout
                case 2:
                    Toast.makeText(getApplicationContext(), R.string.connectionTimeout, Toast.LENGTH_SHORT).show();
                    break;

                //Parsing failed
                case 4:
                    Toast.makeText(getApplicationContext(), R.string.parsingFailed, Toast.LENGTH_LONG).show();
            }
        }
    }

}
