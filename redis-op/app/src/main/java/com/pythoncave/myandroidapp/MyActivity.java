package com.pythoncave.myandroidapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import redis.clients.jedis.Jedis;

import static android.view.View.OnClickListener;

public class MyActivity extends Activity {
    private Button btn;
    private static final String TAG = "redis";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i(TAG,"请求结果:" + val);
        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            // TODO: http request.
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value","1111111");
            msg.setData(data);
            handler.sendMessage(msg);

            Jedis jedis = new Jedis("192.168.1.110");
            jedis.set("foo", "barrrrr");
            String value = jedis.get("foo");
            Log.d(TAG,value);
            System.out.println(value);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btn = (Button)findViewById(R.id.button);
        //匿名内部类 实现点击事件方法
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(runnable).start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
