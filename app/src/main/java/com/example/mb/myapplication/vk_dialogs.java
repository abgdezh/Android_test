package com.example.mb.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class vk_dialogs extends AppCompatActivity {

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_vk_dialogs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk_dialogs);
        setContentView(R.layout.content_vk_dialogs);

        WebView mWebView = findViewById(R.id.vk_WebView);
        mWebView.setWebViewClient(new MyWebViewClient());
        // включаем поддержку JavaScript

        mWebView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки

        mWebView.loadUrl("https://vk.com/im");
    }
}
