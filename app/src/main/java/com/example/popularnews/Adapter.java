package com.example.popularnews;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Callback;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String errorMsg;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;

   /* private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int FOOTER = 2;*/

    private PaginationAdapterCallback mCallback;
    private boolean isLoaderVisible = false;

    private List<Example> exampleList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public Adapter(Context context) {
       // this.mCallback = (PaginationAdapterCallback) context;
        this.context = context;
        this.exampleList = new ArrayList<>();
    }

  /*  public Adapter(Callback<List<Example>> listCallback) {
        this.mCallback = (PaginationAdapterCallback) context;

    }*/


    public List<Example> getMovies() {
        return exampleList;
    }


    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
                viewHolder = new MovieVH(viewItem) {
                    @Override
                    protected void clear() {

                    }
                };
                break;

            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;

            case HERO:
                View viewFooter = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
                viewHolder = new FooterVH(viewFooter) {
                    @Override
                    protected void clear() {

                    }
                };
                break;
            // View viewFaking = inflater.inflate(R.layout.item_loading, parent, false);
        }

        RecyclerView.ViewHolder finalViewHolder = viewHolder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AnimeActivity.class);
                i.putExtra("id", exampleList.get(finalViewHolder.getAdapterPosition()).getId());
                i.putExtra("taklif", exampleList.get(finalViewHolder.getAdapterPosition()).getTaklif());
                i.putExtra("category", exampleList.get(finalViewHolder.getAdapterPosition()).getCategory().getName());
                i.putExtra("fio", exampleList.get(finalViewHolder.getAdapterPosition()).getFio());
                i.putExtra("region", exampleList.get(finalViewHolder.getAdapterPosition()).getRegion().getName());
                i.putExtra("created_time", exampleList.get(finalViewHolder.getAdapterPosition()).getCreatedTime());
                i.putExtra("like_count", exampleList.get(finalViewHolder.getAdapterPosition()).getLikeCount());
                i.putExtra("dislike_count", exampleList.get(finalViewHolder.getAdapterPosition()).getDislikeCount());
                /*i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage_url());*/
                context.startActivity(i);
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holders, int position) {
        final RecyclerView.ViewHolder holder = holders;
        Example model = exampleList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH heroVh = (MovieVH) holders;
                heroVh.fio.setText(model.getFio());
                heroVh.taklif.setText(model.getTaklif());
                heroVh.created_time.setText(model.getCreatedTime());
                heroVh.category.setText(model.getCategory().getName());
                heroVh.region.setText(model.getRegion().getName());
                heroVh.dislike_count.setText(model.getDislikeCount());
                heroVh.like_count.setText(model.getLikeCount());
                break;

            case HERO:
                final FooterVH movieVH = (FooterVH) holder;

                movieVH.fio.setText(model.getFio());
                movieVH.taklif.setText(model.getTaklif());
                movieVH.created_time.setText(model.getCreatedTime());
                movieVH.category.setText(model.getCategory().getName());
                movieVH.region.setText(model.getRegion().getName());
                movieVH.dislike_count.setText(model.getDislikeCount());
                movieVH.like_count.setText(model.getLikeCount());
                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }




      /*
        Helpers -pagination
   _________________________________________________________________________________________________
    */


    @Override
    public int getItemCount() {
        return exampleList == null ? 0 : exampleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM;
        } else {
            return (position == exampleList.size() - 1 && isLoadingAdded) ? LOADING : HERO;
        }
    }

    public void addAll(List<Example> exampleList) {
        for (Example result : exampleList) {
            add(result);
        }
    }

    public void add(Example r) {
        exampleList.add(r);
        notifyItemInserted(exampleList.size() - 1);
    }

    public void remove(Example r) {
        int position = exampleList.indexOf(r);
        if (position > -1) {
            exampleList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public boolean isEmpty() {
        return getItemCount() == 0;
    }


   public void addLoadingFooter() {
       isLoadingAdded = true;
       int position = exampleList.size() - 1;
   }


    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = exampleList.size();
        Example result = getItem(position);

        if (result != null) {
            exampleList.remove(position);
            notifyItemRemoved(position);
        }
    }
    public Example getItem(int position) {
        return exampleList.get(position -1);
    }

    public void addLoading() {
        //   isLoaderVisible = true;
        exampleList.add(new Example());
        notifyItemInserted(exampleList.size() - 1);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(exampleList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

  /*  public void removeLoading() {
        //      isLoaderVisible = false;
        int position = exampleList.size() - 1;
        //       PostItem item = getItem(position);
        //       if (item != null)
        {
            //     exampleList.remove(position);
            notifyItemRemoved(position);
        }
    }*/

          /*
        Helpers -pagination
   _________________________________________________________________________________________________
    */


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public static abstract class MovieVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView category, fio, region, created_time, like_count, dislike_count;
        TextView taklif;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public MovieVH(View itemView) {
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
            //  progressBar = itemView.findViewById(R.id.prog);

            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        protected abstract void clear();
    }

    public static abstract class FooterVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView category, fio, region, created_time, like_count, dislike_count;
        TextView taklif;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public FooterVH(View itemView) {
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
            //  progressBar = itemView.findViewById(R.id.prog);

            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        protected abstract void clear();
    }
    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);
            mErrorLayout.setOnClickListener(this);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.loadmore_errorlayout:

                    /*showRetry(false, null);*/
                    mCallback.retryPageLoad1();

                    break;
            }
        }
    }

}
