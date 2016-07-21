package com.fanfan.newsmy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SlpashActivity extends AppCompatActivity {
    private Animation mAnimation;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        mImageView= (ImageView) findViewById(R.id.iv_image);
        mAnimation= AnimationUtils.loadAnimation(this,R.anim.alpha);
        mImageView.startAnimation(mAnimation);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SlpashActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
