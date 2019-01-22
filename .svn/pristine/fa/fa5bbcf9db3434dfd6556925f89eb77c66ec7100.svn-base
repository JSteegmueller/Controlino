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
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.help.activities.HelpActivity;
import de.jsteeg.controlino.main.adapters.PinRecyclerViewAdapter;
import de.jsteeg.controlino.main.tasks.CheckForBrokenConnectionRptTask;
import de.jsteeg.controlino.main.tasks.ConnectToAliveServerTask;
import de.jsteeg.controlino.main.tasks.ConnectToControlinoTask;
import de.jsteeg.controlino.main.tasks.GetArduinoTypeTask;
import de.jsteeg.controlino.main.tasks.GetPinStateRptTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mSharedPrefsEdit;

    //Controlino
    private Arduino myArduino;
    private ControlinoClient mCClient;
    private ControlinoClient mAliveCheckClient;

    //Threads
    private GetPinStateRptTask pinStateTask;
    private CheckForBrokenConnectionRptTask chckBrokenConnectionTask;
    private Handler mBrokenPipeTaskHandler;
    private Handler mPinTaskHandler;
    private boolean pinTaskIsRunning = false;

    //Layout
    private android.support.v7.widget.Toolbar appBarLayMain;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayoutManager mPinRecyclerViewLManager;
    private PinRecyclerViewAdapter mPinRecyclerViewAdapter;
    private NavigationView navViewMain;
    private DrawerLayout drawLayout;
    private RecyclerView pinRecView;
    private AppCompatButton btnSetIp;
    private AppCompatButton btnGetArduinoType;
    private AppCompatButton btnStart;
    private AppCompatButton btnConnect;
    private EditText etIpServer;
    private TextView tvArduinoIp;
    private TextView tvArduinoType;
    private TextView tvDeviceIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myArduino = new Arduino("0.0.0.0");
        CommonVars.mArduino = myArduino;

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefsEdit = mSharedPrefs.edit();

        /*init view objects*/
        //TextViews
        tvArduinoType = findViewById(R.id.textViewArduinoType);
        tvDeviceIp = findViewById(R.id.textViewLocalIp);
        tvArduinoIp = findViewById(R.id.textViewArduinoAddress);

        //Buttons
        btnSetIp = findViewById(R.id.buttonIp);
        btnGetArduinoType = findViewById(R.id.buttonArduinoType);
        btnStart = findViewById(R.id.buttonStart);
        btnConnect = findViewById(R.id.buttonConnect);

        //EditText
        etIpServer = findViewById(R.id.editTextIp);

        //Layout
        appBarLayMain = findViewById(R.id.appBarLayoutMain);
        navViewMain = findViewById(R.id.navigationViewMain);
        drawLayout = findViewById(R.id.mainDrawer);
        pinRecView = findViewById(R.id.recyclerViewPins);

        checkForDataInSharedPrefs();
        setUpOnClickListener();

        //check for WiFi-Connection
        Utils.checkWifiAvaiableAndWriteInGlobalVars(this);
        if (CommonVars.mWifiConnected) {
            tvDeviceIp.setText(CommonVars.mLocalIpAddr);
        } else {
            tvDeviceIp.setText(R.string.wifi_error);
        }

        //setup views
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

    //Buttons
    private void setUpOnClickListener() {

        //set IP-address
        btnSetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myArduino.setIpAdress(etIpServer.getText().toString());
                tvArduinoIp.setText(myArduino.getIPAdress());
                mSharedPrefsEdit.putString("IP", myArduino.getIPAdress());
                mSharedPrefsEdit.apply();
            }
        });

        //request type of Arduino
        btnGetArduinoType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new GetArduinoTypeTask(myArduino, new MainHandler(), mCClient));
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
                    pinStateTask = new GetPinStateRptTask(myArduino, mPinTaskHandler, new MainHandler(), mCClient);
                    mPinTaskHandler.post(pinStateTask);
                } else {
                    pinTaskIsRunning = false;
                    btnStart.setText(R.string.start);
                    pinStateTask.end();
                }
            }
        });

        //connect to ControlinoServer
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCClient = new ControlinoClient(myArduino.getIPAdress(), 404);
                mAliveCheckClient = new ControlinoClient(myArduino.getIPAdress(), 405);

                //thread for checking --> is the Arduino alive ?
                Thread aliveCheckConnectThread = new Thread(new ConnectToAliveServerTask(mAliveCheckClient));

                //thread for main communication (pins, arduino-type, etc)
                Thread connectMainClientThread = new Thread(new ConnectToControlinoTask(mCClient, new MainHandler()));

                connectMainClientThread.start();
                aliveCheckConnectThread.start();
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
        mPinRecyclerViewAdapter = new PinRecyclerViewAdapter(myArduino);
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
        //show about
        if (item.getTitle().equals("Hilfe")) {
            Intent startHelp = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(startHelp);
        }
        return super.onOptionsItemSelected(item);
    }

    //Handler for main-loop, used by threads to communicate with the main-thread
    public class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                //ControlinoClient is requesting Arduino-Type from ControlinoServer
                case 0:
                    Toast.makeText(getApplicationContext(), R.string.connectingType, Toast.LENGTH_SHORT).show();
                    break;

                //ControlinoType-request answer received
                case 1:
                    Toast.makeText(getApplicationContext(), myArduino.getType(), Toast.LENGTH_SHORT).show();
                    tvArduinoType.setText(myArduino.getType());
                    break;

                //Connection timeout
                case 2:
                    Toast.makeText(getApplicationContext(), R.string.connectionTimeout, Toast.LENGTH_SHORT).show();
                    break;

                //Connection to Arduino successful
                case 3:
                    Toast.makeText(getApplicationContext(), R.string.connected, Toast.LENGTH_SHORT).show();

                    btnConnect.setVisibility(AppCompatButton.GONE);
                    btnStart.setVisibility(AppCompatButton.VISIBLE);
                    btnGetArduinoType.setVisibility(AppCompatButton.VISIBLE);

                    //Check for broken pipe
                    HandlerThread mHandlerThread = new HandlerThread("CheckForConnection");
                    mHandlerThread.start();
                    mBrokenPipeTaskHandler = new Handler(mHandlerThread.getLooper());
                    chckBrokenConnectionTask =   new CheckForBrokenConnectionRptTask(mBrokenPipeTaskHandler, new MainHandler(), mAliveCheckClient);
                    mBrokenPipeTaskHandler.post(chckBrokenConnectionTask);
                    break;

                //Parsing failed
                case 4:
                    Toast.makeText(getApplicationContext(), R.string.parsingFailed, Toast.LENGTH_LONG).show();
                    break;

                 //Disconnected from Arduino
                case 5:
                    Toast.makeText(getApplicationContext(), R.string.disconnected, Toast.LENGTH_SHORT).show();
                    btnConnect.setVisibility(AppCompatButton.VISIBLE);
                    btnStart.setVisibility(AppCompatButton.GONE);
                    btnGetArduinoType.setVisibility(AppCompatButton.GONE);
                    pinTaskIsRunning = false;
                    chckBrokenConnectionTask.end();
                    btnStart.setText(R.string.start);
                    mCClient.closeConnection();
                    mAliveCheckClient.closeConnection();
                case 6:
                    mPinRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

}
