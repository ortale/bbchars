package com.joseortale.ortalesoft.bbchars.data.restapi;

import com.joseortale.ortalesoft.bbchars.model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("characters")
    Call<List<Character>> getCharacters();
}
