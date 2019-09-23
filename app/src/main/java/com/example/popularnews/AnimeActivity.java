package com.example.popularnews;

import android.os.Bundle;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.popularnews.R;
import android.view.View;
import android.widget.TextView;

public class AnimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);
      /*  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String fio  = getIntent().getExtras().getString("fio");
        String category = getIntent().getExtras().getString("category");
        String region = getIntent().getExtras().getString("region") ;
        String taklif = getIntent().getExtras().getString("taklif");

        String created_time = getIntent().getExtras().getString("created_time") ;
        String dislike_count = getIntent().getExtras().getString("dislike_count") ;
        String like_count = getIntent().getExtras().getString("like_count") ;

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_fio = findViewById(R.id.fio_name);
        TextView tv_category = findViewById(R.id.category_n);
        TextView tv_region = findViewById(R.id.region_n) ;
        TextView tv_created_time = findViewById(R.id.time);
        TextView tv_dislike  = findViewById(R.id.dislike) ;
        TextView tv_taklif  = findViewById(R.id.tv_taklif) ;
        TextView tv_like_count  = findViewById(R.id.like) ;
        /*ImageView img = findViewById(R.id.aa_thumbnail);*/

        // setting values to each view

        tv_fio.setText(fio);
        tv_category.setText(category);
        tv_taklif.setText(taklif);
        tv_created_time.setText(created_time);
        tv_dislike.setText(dislike_count);
        tv_like_count.setText(like_count);
        tv_region.setText(region);

        collapsingToolbarLayout.setTitle(fio);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        /*Glide.with(this).load(image_url).apply(requestOptions).into(img);*/




    }

}
