package com.law.test;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.aliheader.AliFooter;
import com.liaoinstan.springview.aliheader.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

public class RvFragment extends Fragment {
    private List<String> mDatas = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SpringView springView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 20; i++) {
            mDatas.add(i / 3 == 0 ? "We are in RecyclerView" : (i / 3 == 1 ? "SpringView支持RecyclerView\n\n这是一个仿阿里旅行的header\n\nlogo可以图片可自行替换" : ""));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        recyclerView = view.findViewById(R.id.recycle);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewAdapter = new RecyclerViewAdapter(mDatas);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        springView = view.findViewById(R.id.springview);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        springView.setHeader(new AliHeader(getContext(), R.drawable.ali, true));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new AliFooter(getContext(), false));
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SampleViewHolder> {
        private List<String> results;

        public RecyclerViewAdapter(List<String> results) {
            this.results = results;
        }

        @Override
        public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new SampleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SampleViewHolder holder, final int position) {
            holder.text_item.setText(results.get(position));
            if ((position + 1) / 2 % 2 == 1) {
                holder.text_item.setBackgroundColor(Color.parseColor("#e3f1fc"));
                holder.text_item.setTextColor(Color.parseColor("#9dd2fc"));
            } else {
                holder.text_item.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.text_item.setTextColor(Color.parseColor("#cccccc"));
            }
        }

        @Override
        public int getItemCount() {
            return results.size();
        }

        public class SampleViewHolder extends RecyclerView.ViewHolder {
            public TextView text_item;

            public SampleViewHolder(View view) {
                super(view);
                text_item = view.findViewById(R.id.item_text);
            }
        }
    }
}
