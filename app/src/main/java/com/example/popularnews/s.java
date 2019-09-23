/*
package com.example.popularnews;

package com.example.popularnews;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.popularnews.model.Example;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Example> exampleList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public Adapter(List<Example> exampleList, Context context){
        this.exampleList = exampleList;
        this.context = context;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        */
/*return new MyViewHolder(view, onItemClickListener);*//*



        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AnimeActivity.class);
                i.putExtra("id",exampleList.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("taklif",exampleList.get(viewHolder.getAdapterPosition()).getTaklif());
                i.putExtra("category",exampleList.get(viewHolder.getAdapterPosition()).getCategory().getName());
                i.putExtra("fio",exampleList.get(viewHolder.getAdapterPosition()).getFio());
                i.putExtra("region",exampleList.get(viewHolder.getAdapterPosition()).getRegion().getName());
                i.putExtra("created_time",exampleList.get(viewHolder.getAdapterPosition()).getCreatedTime());
                i.putExtra("like_count",exampleList.get(viewHolder.getAdapterPosition()).getLikeCount());
                i.putExtra("dislike_count",exampleList.get(viewHolder.getAdapterPosition()).getDislikeCount());
                */
/*i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage_url());*//*

                context.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position){
        final MyViewHolder holder = holders;
        Example model = exampleList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getId())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                });
                */
/*.transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);*//*



        holder.fio.setText(model.getFio());
        holder.taklif.setText(model.getTaklif());
        holder.created_time.setText(model.getCreatedTime());
        holder.category.setText(model.getCategory().getName());
        holder.region.setText(model.getRegion().getName());
        holder.dislike_count.setText(model.getDislikeCount());
        holder.like_count.setText(model.getLikeCount());

    }

    @Override
    public int getItemCount(){
        return exampleList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView category, fio, region, created_time, like_count, dislike_count;
        TextView taklif;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(View itemView){
            super(itemView);
            View view = null;
            itemView.setOnClickListener(this);
            fio = itemView.findViewById(R.id.anime_name);
            taklif = itemView.findViewById(R.id.tv_taklif);
            region = itemView.findViewById(R.id.region_n);
            category = itemView.findViewById(R.id.category_n);
            created_time = itemView.findViewById(R.id.time);
            like_count = itemView.findViewById(R.id.like);
            dislike_count = itemView.findViewById(R.id.dislike);


            this.onItemClickListener = onItemClickListener;



        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());


        }
    }
}
*/
