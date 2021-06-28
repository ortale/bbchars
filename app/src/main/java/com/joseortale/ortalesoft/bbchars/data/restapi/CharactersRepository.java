package com.joseortale.ortalesoft.bbchars.data.restapi;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.joseortale.ortalesoft.bbchars.R;
import com.joseortale.ortalesoft.bbchars.helpers.Resource;
import com.joseortale.ortalesoft.bbchars.model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersRepository {
    private static CharactersRepository instance;

    private final ApiInterface client;

    private Context context;
    private MutableLiveData<Resource<List<Character>>> charactersData;
    private MutableLiveData<Resource<Character>> characterData;

    private CharactersRepository(Context context) {
        client = RetrofitClient.getClient();
        this.context = context;
    }

    public static CharactersRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CharactersRepository(context);
        }

        return instance;
    }

    public MutableLiveData<Resource<List<Character>>> getCharacters() {
        charactersData = new MutableLiveData<>();

        client.getCharacters().enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                if (response.body() != null) {
                    charactersData.setValue(Resource.success(response.body()));
                }

                else {
                    if (response.code() == 401) {
                        charactersData.setValue(Resource.error(context.getResources().getString(R.string.not_auth), null, 401));
                    }

                    else {
                        charactersData.setValue(Resource.error(context.getResources().getString(R.string.error_occ), null, response.code()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                charactersData.setValue(Resource.error(context.getResources().getString(R.string.error_occ), null, null));
            }
        });

        return charactersData;
    }
}
