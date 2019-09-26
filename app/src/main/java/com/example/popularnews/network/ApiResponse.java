package com.example.popularnews.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiResponse {
    private static Retrofit retrofit = null;
    private static ApiClient apiClient = null;

    public static ApiClient getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jamoatchilik.uz/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if (apiClient == null) {
            apiClient = retrofit.create(ApiClient.class);
        }

        return apiClient;
    }
}
