package com.fanfan.newsmy;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    int[] pics={R.drawable.bd,R.drawable.small,R.drawable.welcome,R.drawable.wy};
    private ImageView icon1,icon2,icon3,icon4;
    ImageView[] icons={icon1,icon2,icon3,icon4};
    private ArrayList<View> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mList=new ArrayList<>();
        mPager= (ViewPager) findViewById(R.id.vp_pager);
        icons[0]= (ImageView) findViewById(R.id.icon1);
        icons[1]= (ImageView) findViewById(R.id.icon2);
        icons[2]= (ImageView) findViewById(R.id.icon3);
        icons[3]= (ImageView) findViewById(R.id.icon4);
        icons[0].setImageResource(R.drawable.adware_style_selected);
        for (int i = 0; i <pics.length ; i++) {
            ImageView iv=new ImageView(this);
            iv.setImageResource(pics[i]);
            mList.add(iv);
        }
        mPager.setAdapter(new MyAdapter(mList));
        mPager.addOnPageChangeListener(this);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 设置pager在滑动到最后一页时跳转
        if (position==pics.length-1){
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(MainActivity.this,SlpashActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }
        for (int i = 0; i <icons.length ; i++) {
            icons[i].setImageResource(R.drawable.adware_style_default);
        }
        icons[position].setImageResource(R.drawable.adware_style_selected);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyAdapter extends PagerAdapter {
        private ArrayList<View>  mList;
        public  MyAdapter(ArrayList<View>  mlist){
            mList=mlist;
        }
        @Override
        public int getCount() {
            if (mList!=null){
                return mList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        // 初始化position 展现到界面上来
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position),0);
            return mList.get(position);
        }
        //当不可见时销毁position
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }

}
