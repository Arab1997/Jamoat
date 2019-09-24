package com.example.popularnews;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.popularnews.model.Example;
import com.example.popularnews.network.ApiClient;
import com.example.popularnews.network.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adapter.ListItemClickListener {

    // 1ta pageda nechta object borligi
    private static final int OBJECTS_IN_PAGE = 20;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private Adapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;

    private ApiClient apiClient = ApiResponse.getClient();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        adapter = new Adapter(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                currentPage++;
                getData();
            }

            @Override
            public int getTotalPageCount() {
                return OBJECTS_IN_PAGE;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        getData();

        ImageView options = findViewById(R.id.options);
        ImageView search = findViewById(R.id.search);
        ImageView xabar = findViewById(R.id.xabar);
        ImageView talkif = findViewById(R.id.taklif1);
        ImageView added = findViewById(R.id.added);

        xabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        talkif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE); /* SearchView */
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Ismlar ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    // TODO: 2019-09-24
                    // apida poisk realizatsiya qilinmagan hali
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: 2019-09-24
                // apida poisk realizatsiya qilinmagan hali
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public void onItemClick(Example example) {
        Intent intent = new Intent(this, AnimeActivity.class);
        intent.putExtra("id", example.getId());
        intent.putExtra("taklif", example.getTaklif());
        intent.putExtra("category", example.getCategory().getName());
        intent.putExtra("fio", example.getFio());
        intent.putExtra("region", example.getRegion().getName());
        intent.putExtra("created_time", example.getCreatedTime());
        intent.putExtra("like_count", example.getLikeCount());
        intent.putExtra("dislike_count", example.getDislikeCount());
        startActivity(intent);
    }

    private void getData() {
        isLoading = true;
        Call<List<Example>> call = apiClient.getPage(currentPage);
        call.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(@NonNull Call<List<Example>> call, @NonNull Response<List<Example>> response) {
                log("onResponse: current page: " + currentPage);
                adapter.addItems(response.body());
                isLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<List<Example>> call, @NonNull Throwable t) {
                Log.e("MainActivity", "current page: " + currentPage, t);
                isLoading = false;
                isLastPage = true;
            }
        });
    }

    private void log(String message) {
        Log.d("MainActivity", message);
    }
}

