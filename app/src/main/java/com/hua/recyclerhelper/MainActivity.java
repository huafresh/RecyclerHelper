package com.hua.recyclerhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hua.recyclerhelper_core.adapter2.BaseRvAdapter;
import com.hua.recyclerhelper_core.adapter2.BaseViewHolder;
import com.hua.recyclerhelper_core.adapter2.CommRvAdapter;
import com.hua.recyclerhelper_core.adapter2.listeners.OnItemClickListener;
import com.hua.recyclerhelper_core.decoration.LinearDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BaseRvAdapter<Item> adapter;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.addItemDecoration(new LinearDecoration().drawLastLine(false));
//
//                TextView textView = new TextView(MainActivity.this);
//                textView.setText("i am header");
//                TextView textView2 = new TextView(MainActivity.this);
//                textView2.setText("i am footer");
//
//                TextView textView3 = new TextView(MainActivity.this);
//                textView3.setText("i am second header");
//
//                recyclerView.setAdapter(CommRvAdapter.withModel(MainActivity.this, String.class)
//                        .single(R.layout.recycler_item)
//                        .setDataList(testDataList())
//                        .setBinder(new IBindHolder<BaseViewHolder, String>() {
//                            @Override
//                            public void bind(BaseViewHolder holder, String data) {
//                                holder.setText(R.id.text, data);
//                            }
//                        })
//                        .setOnItemLongClickListener(new OnItemLongClickListener<String>() {
//                            @Override
//                            public void onLongClick(BaseViewHolder holder, String data) {
//                                Log.e("@@@hua", "onlongclick = " + data);
//                            }
//                        })
//                        .setOnItemClickListener(new OnItemClickListener<String>() {
//                            @Override
//                            public void onClick(BaseViewHolder holder, String data) {
//                                Log.e("@@@hua", "onclick = " + data);
//                            }
//                        })
//                        .addHeader(textView)
//                        .addFooterView(textView2));
            }
        });
        findViewById(R.id.grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.addItemDecoration(new LinearDecoration().drawLastLine(false));

                TextView textView = new TextView(MainActivity.this);
                textView.setText("i am header");
                TextView textView2 = new TextView(MainActivity.this);
                textView2.setText("i am footer");

                TextView textView3 = new TextView(MainActivity.this);
                textView3.setText("i am second header");

                adapter = CommRvAdapter.newAdapter(MainActivity.this, Item.class)
                        .addRvItemType(new ItemType1())
                        .addRvItemType(new ItemType2())
                        .addHeaderView(textView)
                        .addHeaderView(textView3)
                        .addFooterView(textView2)
                        .setDataList(testList2())
                        .setOnItemClickListener(new OnItemClickListener<Item>() {
                            @Override
                            public void onClick(BaseViewHolder holder, Item data) {
                                Log.e("@@@hua", "item click = " + data);
                            }
                        });
                recyclerView.setAdapter(adapter);

                MyItemAnimator animator = new MyItemAnimator();
                animator.setAddDuration(10000);
                animator.setRemoveDuration(3000);
                recyclerView.setItemAnimator(animator);

            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i++;
                adapter.append(new Item(0, "add" + i));
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(0);
            }
        });
    }

    private List<Item> testList2() {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                list.add(new Item(0, i + ""));
            } else {
                list.add(new Item(1, i + ""));
            }
        }

        return list;
    }

    private List<String> testDataList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }

        return list;
    }
}
