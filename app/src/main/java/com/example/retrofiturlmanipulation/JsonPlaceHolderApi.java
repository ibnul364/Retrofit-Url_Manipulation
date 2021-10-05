package com.example.retrofiturlmanipulation;

import com.example.retrofiturlmanipulation.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments();
}
