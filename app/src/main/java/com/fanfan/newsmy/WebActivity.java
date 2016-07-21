package com.fanfan.newsmy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {
    private Button mbtn_return;
    private TextView mtitle;
    private WebView mwv_matter;
    String title;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mbtn_return = (Button) findViewById(R.id.btn_return);
        mtitle = (TextView) findViewById(R.id.title);
        mwv_matter = (WebView) findViewById(R.id.wv_matter);
        Bundle bundle = getIntent().getExtras();
        WebSettings settings = mwv_matter.getSettings();
        //缩放
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放
        title = bundle.getString("title");
        url = bundle.getString("url");
//            url = "http://m.baidu.com";
        mtitle.setText(title);
        mwv_matter.loadUrl(url);

        mwv_matter.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mbtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(WebActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
