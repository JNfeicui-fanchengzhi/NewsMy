package com.fanfan.newsmy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    public static final String TAG = "TAG";
    private ImageButton mbtnlift;
    private ImageButton mbtnright;
    private SlidingMenu mSlidingMenu;

    private Bitmap defaultBitmap = null;
    private BitmapUtils loadImage;

    ListView mView;
    List<Map<String, String>> list = new ArrayList<>();
    Map<String, String> paramMap = new HashMap<>();
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        mView = (ListView) findViewById(R.id.lv_view);
        defaultBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        loadImage = new BitmapUtils(this, listener);
        mView.setAdapter(ba);
        mView.setOnItemClickListener(this);
        getData();

    }

    private void initView() {
        mbtnlift= (ImageButton) findViewById(R.id.ib_lift);
        mbtnright= (ImageButton) findViewById(R.id.ib_right);
        mbtnlift.setOnClickListener(this);
        mbtnright.setOnClickListener(this);
        //侧拉菜单初始化
        mSlidingMenu=new SlidingMenu(this);
        //侧拉菜单的触摸响应范围
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置侧拉菜单的偏移量
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_width);
        //布置侧拉菜单界面
        mSlidingMenu.setMenu(R.layout.list_lift_item);
        mSlidingMenu.setSecondaryMenu(R.layout.list_right_item);
    }

    private BaseAdapter ba=new BaseAdapter() {
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        if (convertView==null){
            convertView= LayoutInflater.from(HomeActivity.this).inflate(R.layout.list_item,null);
            holder.mimage = (ImageView) convertView.findViewById(R.id.image);
            holder.mtitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mitem = (TextView) convertView.findViewById(R.id.tv_text);
            holder.mtime = (TextView) convertView.findViewById(R.id.tv_data);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        Map<String,String> map= (Map<String, String>) getItem(position);
        String url = map.get("icon");
        holder.mimage.setTag(url);
        Bitmap bitmap = loadImage.geBitmap(url);
        if (bitmap != null) {
            holder.mimage.setImageBitmap(bitmap);
        } else {
            holder.mimage.setImageBitmap(defaultBitmap);
        }
        holder.mtitle.setText(map.get("title"));
        holder.mitem.setText(map.get("summary"));
        holder.mtime.setText(map.get("stamp"));
        return convertView;
    }
};
    private BitmapUtils.ImageLoadListener listener = new BitmapUtils.ImageLoadListener() {

        @Override
        public void imageLoadOk(Bitmap bitmap, String url) {
            ImageView iv = (ImageView) mView.findViewWithTag(url);
            if (iv != null)
                iv.setImageBitmap(bitmap);
        }
    };

    private List<Map<String, String>> getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams rp = new RequestParams();
        url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
        client.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject jsonObject;
                String str = null;
                jsonObject = response;
                try {
                    str = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                list = gson.fromJson(str, new TypeToken<List<Map<String, String>>>(){}.getType());
                ba.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, String> map = list.get(position);
        String title = map.get("title");
        String link = map.get("link");

        Bundle bundle = new Bundle();
        bundle.putString("url", link);
        bundle.putString("title", title);

        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, WebActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_lift:
                mSlidingMenu.toggle();
                break;
            case R.id.ib_right:
                mSlidingMenu.toggle();
                mSlidingMenu.showSecondaryMenu();
                break;
        }
    }

    class Holder{
        private ImageView mimage;
        private TextView mitem;
        private TextView mtitle;
        private TextView mtime;
    }
}
