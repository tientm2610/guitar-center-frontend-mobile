package com.example.guitar_center.services;

import com.example.guitar_center.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductAPIServices {
    @GET("products")
    Call<List<Product>> getAllProducts();

    @POST("products")
    Call<List<Product>>  insertProduct(@Body Product product);
    @PUT("products")
    Call<List<Product>>  updateProduct(@Body Product product);
    @DELETE("products/{id}")
    Call<Void>  deleteProduct(@Path("id") String id);
}
