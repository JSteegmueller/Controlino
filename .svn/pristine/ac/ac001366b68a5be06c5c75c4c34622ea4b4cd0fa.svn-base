package de.jsteeg.controlino.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.jsteeg.controlino.R;
import de.jsteeg.controlino.common.arduino.Arduino;
import de.jsteeg.controlino.common.controlinoMain.ControlinoClient;
import de.jsteeg.controlino.help.activities.HelpActivity;
import de.jsteeg.controlino.main.MainHandler;
import de.jsteeg.controlino.main.fragments.PinStatusFragment;
import de.jsteeg.controlino.main.fragments.PinManagerFragment;
import de.jsteeg.controlino.main.tasks.CheckForBrokenConnectionRptTask;
import de.jsteeg.controlino.main.tasks.ConnectToAliveServerTask;
import de.jsteeg.controlino.main.tasks.ConnectToControlinoTask;
import de.jsteeg.controlino.start.activities.StartActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Fragments
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private PinStatusFragment mPinStatusFragment;
    private PinManagerFragment mPinManagerFragment;

    //Layout
    private android.support.v7.widget.Toolbar appBarLayMain;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navViewMain;
    private DrawerLayout drawLayout;

    //Controlino
    private ControlinoClient mCClient;
    private ControlinoClient mAliveClient;
    private Arduino mArduino;

    //Threads
    private CheckForBrokenConnectionRptTask chckBrokenConnectionTask;
    private Handler mBrokenPipeTaskHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Controlino setup
        mCClient = new ControlinoClient(404);
        mAliveClient = new ControlinoClient(405);
        mArduino = new Arduino("0.0.0.0");

        //Receive Objects
        Intent in = getIntent();
        mArduino.setIpAdress(in.getStringExtra("ip"));

        //update clients with new ip
        mCClient.setIpAdress(mArduino.getIPAdress());
        mAliveClient.setIpAdress(mArduino.getIPAdress());

        //thread for checking --> is the Arduino alive ?
        Thread aliveCheckConnectThread = new Thread(new ConnectToAliveServerTask(mAliveClient));

        //thread for main communication (pins, arduino-type, etc)
        Thread connectMainClientThread = new Thread(new ConnectToControlinoTask(mCClient, new MainHandler(this)));

        connectMainClientThread.start();
        aliveCheckConnectThread.start();


        mPinStatusFragment = new PinStatusFragment();
        mPinManagerFragment = new PinManagerFragment();
        mFragmentManager = getSupportFragmentManager();

        appBarLayMain = findViewById(R.id.appBarLayoutMain);
        navViewMain = findViewById(R.id.navigationViewMain);
        drawLayout = findViewById(R.id.mainDrawer);

        setUpMainToolbar();
        setUpMainDrawer();

        mPinStatusFragment.setObjects(mArduino, mCClient, mAliveClient);
        mPinManagerFragment.setObjects(mArduino, mCClient);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentContainer, mPinStatusFragment);
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (item.getTitle().equals("Start")) {
            mFragmentTransaction.replace(R.id.fragmentContainer, mPinStatusFragment);
        } else if (item.getTitle().equals("Pins einstellen")) {
            mFragmentTransaction.replace(R.id.fragmentContainer, mPinManagerFragment);
        }
        mFragmentTransaction.commit();
        item.setChecked(true);
        drawLayout.closeDrawer(GravityCompat.START, true);

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

    @Override
    protected void onPause() {
        super.onPause();
        if (chckBrokenConnectionTask != null) chckBrokenConnectionTask.end();
    }

    private void setUpMainToolbar() {
        setSupportActionBar(appBarLayMain);
        appBarLayMain.setTitle(R.string.app_name);
        appBarLayMain.setTitleTextColor(getResources().getColor(R.color.colorFont));
    }

    private void setUpMainDrawer() {
        navViewMain.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawLayout, appBarLayMain, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        drawLayout.addDrawerListener(mDrawerToggle);
    }

    public void controlinoConnected() {
        Toast.makeText(getApplicationContext(), R.string.connected, Toast.LENGTH_SHORT).show();

        //Check for broken connection
        HandlerThread mHandlerThread = new HandlerThread("CheckForConnection");
        mHandlerThread.start();
        mBrokenPipeTaskHandler = new Handler(mHandlerThread.getLooper());
        chckBrokenConnectionTask = new CheckForBrokenConnectionRptTask(mBrokenPipeTaskHandler, new MainHandler(this), mAliveClient);
        mBrokenPipeTaskHandler.post(chckBrokenConnectionTask);
    }

    public void controlinoDisconnected() {
        Toast.makeText(getApplicationContext(), R.string.disconnected, Toast.LENGTH_SHORT).show();

        chckBrokenConnectionTask.end();

        //close all clients
        mAliveClient.closeConnection();
        mCClient.closeConnection();
        Intent start = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(start);
    }

    public void controlinoTimeout() {
        Toast.makeText(getApplicationContext(), R.string.connectionTimeout, Toast.LENGTH_SHORT).show();
        Intent start = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(start);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCClient.closeConnection();
        mAliveClient.closeConnection();
    }
}
