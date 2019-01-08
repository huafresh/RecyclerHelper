package com.hua.recyclerhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hua.recyclerhelper_core.adapter.BaseMultiTypeRvAdapter;
import com.hua.recyclerhelper_core.adapter.BaseSingleRvAdapter;
import com.hua.recyclerhelper_core.adapter.HeaderFooterWrapper;
import com.hua.recyclerhelper_core.adapter.BaseViewHolder;
import com.hua.recyclerhelper_core.decoration.GridDecoration;
import com.hua.recyclerhelper_core.decoration.LinearDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                        RecyclerView.VERTICAL, false));

                BaseSingleRvAdapter<String> adapter = new BaseSingleRvAdapter<String>(MainActivity.this,
                        testDataList(), R.layout.recycler_item) {
                    @Override
                    protected void convert(BaseViewHolder holder, String data, int position) {
                        holder.setText(R.id.text, (String) data);
                    }
                };

                adapter.setOnItemClickListener(new BaseMultiTypeRvAdapter.OnItemClickListener<String>() {
                    @Override
                    public void onClick(View view, String data, int position) {
                        Log.e("@@@hua", "click = " + data);
                    }
                });
                recyclerView.addItemDecoration(new LinearDecoration().drawLastLine(false));

                HeaderFooterWrapper wrapAdapter = new HeaderFooterWrapper(adapter);
                TextView textView = new TextView(MainActivity.this);
                textView.setText("i am header");
                TextView textView2 = new TextView(MainActivity.this);
                textView2.setText("i am footer");
                wrapAdapter.addHeadView(textView);
                wrapAdapter.addFootView(textView2);

                recyclerView.setAdapter(wrapAdapter);

            }
        });
        findViewById(R.id.grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3,
                        RecyclerView.HORIZONTAL, false));
                BaseSingleRvAdapter<String> adapter = new BaseSingleRvAdapter<String>(MainActivity.this,
                        testDataList(), R.layout.recycler_item2) {
                    @Override
                    protected void convert(BaseViewHolder holder, String data, int position) {
                        holder.setText(R.id.text, (String) data);
                    }
                };
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseMultiTypeRvAdapter.OnItemClickListener<String>() {
                    @Override
                    public void onClick(View view, String data, int position) {
                        Log.e("@@@hua", "click = " + data);
                    }
                });
                recyclerView.addItemDecoration(new GridDecoration().drawLastLine(false));

            }
        });
    }

    private List<String> testDataList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }

        return list;
    }
}
