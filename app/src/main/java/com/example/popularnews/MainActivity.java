package com.example.popularnews;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.popularnews.model.Example;
import com.example.popularnews.network.ApiClient;
import com.example.popularnews.network.ApiResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Example> examples = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private TextView topHeadline;
    private RelativeLayout errorLayout;
    private ImageView errorImage, search, xabar, added, talkif, btn_like, btn_dislike, options;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView txtError;
    ProgressBar progressBar;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 1;
    private static final int PAGE_START = 1;
    private static final int TOTAL_PAGES = 50;

    private ApiClient apiClient = ApiResponse.getClient();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        adapter = new Adapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 2;
                LoadJson();
                LoadJsonNext();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
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

        progressBar = findViewById(R.id.main_progress);
        options = findViewById(R.id.options);
        search = findViewById(R.id.search);
        btn_like = findViewById(R.id.btn_like);
        xabar = findViewById(R.id.xabar);
        talkif = findViewById(R.id.taklif1);
        added = findViewById(R.id.added);
        btn_dislike = findViewById(R.id.btn_dislike);
        errorLayout = findViewById(R.id.errorLayout1);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);
        progressBar = findViewById(R.id.main_progress);
        LoadJson();
        swipeRefreshLayout.setOnRefreshListener(this::doRefresh);

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

    private void doRefresh() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.getMovies().clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        LoadJson();
        LoadJsonNext();

    }

    public void LoadJson() {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Example>> result = apiClient.getResult();
        result.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, retrofit2.Response<List<Example>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    if (!examples.isEmpty()) {
                        examples.clear();
                    }
                    examples = response.body();
                    adapter.addAll(examples);

                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                    recyclerView.setAdapter(adapter);
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {

                showErrorView(t);
            }
        });
    }

    private void LoadJsonNext() {
        Call<List<Example>> call = apiClient.getPage(2);
        call.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, retrofit2.Response<List<Example>> response) {
                isLoading = false;
                recyclerView.setAdapter(adapter);
                adapter.addAll(examples);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                t.printStackTrace();
                adapter.showRetry(true, fetchErrorMessage(t));
            }
        });

    }

    private void initListener() {
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject jsonObject = null;
                Intent intent = new Intent(MainActivity.this, AnimeActivity.class);

                // jsonObject null bo'lgani uchun quyidagi items ham bo'sh bo'ladi.
                Example items = new Gson().fromJson(jsonObject.toString(), Example.class);
                intent.putExtra("fio", items.getFio());
                intent.putExtra("taklif", items.getTaklif());
                intent.putExtra("taklif", items.getTaklif());
                intent.putExtra("created_time", items.getCreatedTime());
                intent.putExtra("dislike_count", items.getDislikeCount());
                intent.putExtra("like_count", items.getLikeCount());

                // TODO: 2019-09-24 where is startActivity(intent) method?
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
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadJson(); // TODO: 2019-09-24
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        initListener();
        LoadJson();
        LoadJsonNext();
    }


    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}

