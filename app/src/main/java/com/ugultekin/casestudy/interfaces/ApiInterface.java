package com.ugultekin.casestudy.interfaces;

import android.text.Editable;

import com.ugultekin.casestudy.model.Card;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

//    @Headers({
//            "x-rapidapi-host: omgvamp-hearthstone-v1.p.rapidapi.com",
//            "x-rapidapi-key: 52edad2338mshfc697a816268a35p116dd6jsneaabe8b17b72"
//    })
//    @GET("search/gnomeferatu")
//    Call<List<Card>> getCards();


//
//    @Headers({
//            "x-rapidapi-host: omgvamp-hearthstone-v1.p.rapidapi.com",
//            "x-rapidapi-key: 52edad2338mshfc697a816268a35p116dd6jsneaabe8b17b72"
//    })
//    @GET("search")
//    Call<List<Card>> getCards();


//    @Headers({
//            "x-rapidapi-host: omgvamp-hearthstone-v1.p.rapidapi.com",
//            "x-rapidapi-key: 52edad2338mshfc697a816268a35p116dd6jsneaabe8b17b72"
//    })
//    @GET("search")
//    Call<List<Card>> getCards();


    @Headers({
            "x-rapidapi-host: omgvamp-hearthstone-v1.p.rapidapi.com",
            "x-rapidapi-key: 52edad2338mshfc697a816268a35p116dd6jsneaabe8b17b72"
    })
    @GET("search/{name}")
    Call<List<Card>> getCards(@Path("name") String name);



    @Headers({
            "x-rapidapi-host: omgvamp-hearthstone-v1.p.rapidapi.com",
            "x-rapidapi-key: 52edad2338mshfc697a816268a35p116dd6jsneaabe8b17b72"
    })
    @GET("classes/{class}")
    Call<List<Card>> getCardClass(@Path("class") String class_name);



//    @Headers({
//            "x-rapidapi-host: omgvamp-hearthstone-v1.p.rapidapi.com",
//            "x-rapidapi-key: 52edad2338mshfc697a816268a35p116dd6jsneaabe8b17b72"
//    })
//    @GET("classes/Druid")
//    Call<List<Card>> getCardss();


//    @Headers({
//            "Accept: application/vnd.github.v3.full+json",
//            "User-Agent: Retrofit-Sample-App"
//    })
//    @GET("users/{username}")
//    Call<Card> getCards(@Path("username") String username);
}