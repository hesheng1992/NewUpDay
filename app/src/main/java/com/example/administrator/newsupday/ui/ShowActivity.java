package com.example.administrator.newsupday.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.db.NewsDbManager;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;

import java.io.Serializable;

public class ShowActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FloatingActionButton button;
    
    private Content data;

//    private DBManager dbManager;
    private NewsDbManager manager;

    private WebView webView;
    private WebViewClient client=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
    };
    private WebChromeClient chromeClient=new WebChromeClient(){

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            progressBar.setProgress(newProgress);
            if (progressBar.getProgress()==100){
                progressBar.setVisibility(View.GONE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        webView= (WebView) findViewById(R.id.webview);
        button= (FloatingActionButton) findViewById(R.id.floatbutton);


        Bundle bundle = getIntent().getExtras();
//        data = (ContentlistBean) bundle.getSerializable("news");
        data = (Content) bundle.getSerializable("news");

        Log.e("TAG","data===="+data.toString());
//        ContentlistBean news = (ContentlistBean) bundle.getSerializable("news");
//        String url = getIntent().getStringExtra("url");
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        webView.loadUrl(data.getLink());

//        manager=NewsDbManager.getDbManager(this);
        manager=new NewsDbManager(this);


        //悬浮按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager!=null){
                    boolean b = manager.addData(data);
                    if (b){
                        Toast.makeText(ShowActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ShowActivity.this, "收藏失败，已经存在", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        });

    }
}
