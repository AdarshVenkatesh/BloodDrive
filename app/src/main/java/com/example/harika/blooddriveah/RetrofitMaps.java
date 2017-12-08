package com.example.harika.blooddriveah;

/**
 * Created by adars on 12/4/2017.
 */

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import com.example.harika.blooddriveah.POJO.Example;
public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDN7RJFmImYAca96elyZlE5s_fhX-MMuhk")
    Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}
