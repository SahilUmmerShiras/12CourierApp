package com.example.film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiinterface {
    @GET("movie/{category}")
    Call<Feed> tmbd(@Path("category") String category, @Query("api_key") String api_key);

    @GET("search/movie")
    Call<Feed> QUERY(@Query("api_key") String Apikey,@Query("query")String query);

    @GET("movie/{id}")
    Call<Results> related(@Path("id") String id, @Query("api_key") String api_key);

    @GET("movie/{id}/similar")
    Call<Feed>  similar(@Path("id") String id, @Query("api_key") String api_key);
}
