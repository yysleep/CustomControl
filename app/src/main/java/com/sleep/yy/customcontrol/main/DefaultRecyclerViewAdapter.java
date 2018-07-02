package com.sleep.yy.customcontrol.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sleep.yy.customcontrol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YySleep on 2018/3/14.
 *
 * @author YySleep
 */

public class DefaultRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "DefaultRecyclerViewAdapter";
    private final static int VIEW_TYPE_01 = 1;

    public List<MainModel> list;
    private OnItemClickListener mItemClickListener;

    public DefaultRecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            default:
                holder = new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_main, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (list == null || list.size() == 0) {
            return;
        }
        switch (holder.getItemViewType()) {
            default:
                MainViewHolder viewHolder = (MainViewHolder) holder;
                viewHolder.mTvTitle.setText(list.get(position).title);
                if (mItemClickListener != null) {
                    viewHolder.mV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onItemClick(holder.getAdapterPosition());
                        }
                    });
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_01;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvTitle;
        public View mV;

        public MainViewHolder(View itemView) {
            super(itemView);
            mV = itemView.findViewById(R.id.item_main_v);
            mTvTitle = itemView.findViewById(R.id.item_main_tv);;
        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
