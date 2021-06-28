package com.joseortale.ortalesoft.bbchars.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.joseortale.ortalesoft.bbchars.data.restapi.CharactersRepository;
import com.joseortale.ortalesoft.bbchars.helpers.Resource;
import com.joseortale.ortalesoft.bbchars.model.Character;

import java.util.List;

public class CharactersViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Character>>> mutableLiveData;
    private CharactersRepository charactersRepository;

    public void init(Context context) {
        if (mutableLiveData != null) {
            return;
        }

        charactersRepository = CharactersRepository.getInstance(context);
        mutableLiveData = charactersRepository.getCharacters();
    }

    public LiveData<Resource<List<Character>>> getCharactersRepository() {
        return mutableLiveData;
    }
}
