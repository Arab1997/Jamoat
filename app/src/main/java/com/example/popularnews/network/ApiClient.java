package com.example.popularnews.network;

import com.example.popularnews.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {
   // https://jamoatchilik.uz/api/list.php/?page=2

    @GET("list.php/")
    Call<List<Example>> getPage(@Query("page") int pageIndex);

}