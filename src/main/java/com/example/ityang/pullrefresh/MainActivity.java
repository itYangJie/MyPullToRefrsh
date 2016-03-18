package com.example.ityang.pullrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private PullRefreshListView listView = null;
    private List<String> list = null;
    private MyAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        listView = (PullRefreshListView)findViewById(R.id.refresh_list);

        list = new ArrayList<String>();
        for(int i=1;i<=20;i++){
            list.add(new String("这是第"+i+"个数据"));
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        listView.setRefreshingListener(new PullRefreshListView.OnRefreshingListener() {
            @Override
            public void onRefreshing() {
                //模拟向服务器请求数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0,"这是上拉刷新出来的数据");
                        adapter.notifyDataSetChanged();
                        listView.changeStateToStart();
                    }
                },2000);
            }

            @Override
            public void onLoadingMore() {
                //模拟向服务器请求数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add( "这是下拉刷新出来的数据");
                        adapter.notifyDataSetChanged();
                        listView.changeStateToStart();
                    }
                }, 1000);
            }
        });
    }


    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tx;
            if(convertView==null) {
                tx = new TextView(MainActivity.this);
            }else {
                tx = (TextView)convertView;
            }
            tx.setTextSize(30);
            tx.setText(list.get(position));
            return tx;
        }
    }
}
