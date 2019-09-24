package com.example.popularnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularnews.model.Example;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;

    private ListItemClickListener itemClickListener;
    private List<Example> exampleList;

    Adapter(ListItemClickListener listener) {
        itemClickListener = listener;
        exampleList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MovieVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Example model = exampleList.get(position);
        MovieVH heroVh = (MovieVH) holder;
        heroVh.fio.setText(model.getFio());
        heroVh.taklif.setText(model.getTaklif());
        heroVh.created_time.setText(model.getCreatedTime());
        heroVh.category.setText(model.getCategory().getName());
        heroVh.region.setText(model.getRegion().getName());
        heroVh.dislike_count.setText(model.getDislikeCount());
        heroVh.like_count.setText(model.getLikeCount());
        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(exampleList.get(position)));
    }

    @Override
    public int getItemCount() {
        return exampleList == null ? 0 : exampleList.size();
    }

    void addItems(List<Example> newItems) {
        exampleList.addAll(newItems);
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onItemClick(Example example);
    }

    private class MovieVH extends RecyclerView.ViewHolder {
        TextView category, fio, region, created_time, like_count, dislike_count;
        TextView taklif;

        MovieVH(View itemView) {
            super(itemView);
            View view = null;
            fio = itemView.findViewById(R.id.anime_name);
            taklif = itemView.findViewById(R.id.tv_taklif);
            region = itemView.findViewById(R.id.region_n);
            category = itemView.findViewById(R.id.category_n);
            created_time = itemView.findViewById(R.id.time);
            like_count = itemView.findViewById(R.id.like);
            dislike_count = itemView.findViewById(R.id.dislike);
        }
    }

    /*
    private class FooterVH extends RecyclerView.ViewHolder {
        TextView category, fio, region, created_time, like_count, dislike_count;
        TextView taklif;

        FooterVH(View itemView) {
            super(itemView);
            View view = null;
            fio = itemView.findViewById(R.id.anime_name);
            taklif = itemView.findViewById(R.id.tv_taklif);
            region = itemView.findViewById(R.id.region_n);
            category = itemView.findViewById(R.id.category_n);
            created_time = itemView.findViewById(R.id.time);
            like_count = itemView.findViewById(R.id.like);
            dislike_count = itemView.findViewById(R.id.dislike);
        }
    }

    private class LoadingVH extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private LinearLayout mErrorLayout;

        LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
     */
}
