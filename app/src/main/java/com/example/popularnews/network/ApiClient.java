package com.example.popularnews.network;


import com.example.popularnews.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.provider.ContactsContract.CommonDataKinds.StructuredName.PREFIX;

public interface ApiClient {
   // http://jamoatchilik.uz/api/list.php/?page=2

    @GET("list.php")
    Call<List<Example>> getResult();


    //   @Query("page") int pageIndex
  /*  @GET("page=3")
    Call<List<Example>> getPage();*/


    @GET("list.php/")
    Call<List<Example>> getPage(
            @Query("page") int pageIndex

    );

}