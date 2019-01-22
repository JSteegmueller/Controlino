package de.jsteeg.controlino.help.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import de.jsteeg.controlino.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        android.support.v7.widget.Toolbar helpToolbar = findViewById(R.id.appBarLayoutMain);
        WebView myWebView = findViewById(R.id.webViewHelp);

        setSupportActionBar(helpToolbar);
        getSupportActionBar().setTitle("Über die App");
        helpToolbar.setTitleTextColor(getResources().getColor(R.color.colorFont));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.loadUrl("file:///android_res/drawable/tenor.gif");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
