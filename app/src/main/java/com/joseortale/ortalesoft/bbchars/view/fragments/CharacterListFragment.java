package com.joseortale.ortalesoft.bbchars.view.fragments;

import android.content.ClipData;
import android.content.Context;
import android.database.Observable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joseortale.ortalesoft.bbchars.R;
import com.joseortale.ortalesoft.bbchars.databinding.FragmentListCharacterBinding;
import com.joseortale.ortalesoft.bbchars.helpers.Resource;
import com.joseortale.ortalesoft.bbchars.helpers.ViewHelper;
import com.joseortale.ortalesoft.bbchars.model.Character;
import com.joseortale.ortalesoft.bbchars.view.activities.MainActivity;
import com.joseortale.ortalesoft.bbchars.view.adapters.CharactersAdapter;
import com.joseortale.ortalesoft.bbchars.viewmodel.CharactersViewModel;

import java.util.ArrayList;
import java.util.List;

public class CharacterListFragment extends Fragment {
    private Context context;

    private List<Character> characterArrayList = new ArrayList<>();
    private List<Integer> season = new ArrayList<>();
    private RecyclerView.Adapter charactersAdapter;
    private RecyclerView rvCharacters;
    private ArrayAdapter<String> adapter;
    private CharactersViewModel charactersViewModel;

    private FragmentListCharacterBinding binding;

    public static CharacterListFragment newInstance() {
        CharacterListFragment fragment = new CharacterListFragment();

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_character, container, false);

        onDataLoading();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);
        charactersViewModel.init(context);
        charactersViewModel.getCharactersRepository().observe(getViewLifecycleOwner(), characterResponse -> {
            if (characterResponse.status == Resource.Status.SUCCESS) {
                assert characterResponse.data != null;
                onDataFinishedLoading();
                characterArrayList = characterResponse.data;
                refreshRecyclerView();
                setupViewEvents();
            } else {
                onDataFinishedLoading();
                Toast.makeText(context, context.getResources().getString(R.string.error_occ), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupViewEvents() {
        setupCharFilter();
        setupSeasonFilter();
    }

    private void filterByName(String name) {
        List<Character> filteredList = ViewHelper.filterCharByName(name, characterArrayList);

        ((CharactersAdapter) charactersAdapter).filterList(filteredList);
    }

    private void setupCharFilter() {
        binding.etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterByName(s.toString());
            }
        });
    }

    private List<String> fillSeason() {
        List<String> seasons = new ArrayList<>();
        seasons.add("Select a season");
        seasons.add("1");
        seasons.add("2");
        seasons.add("3");
        seasons.add("4");
        seasons.add("5");

        return seasons;
    }

    private void setupSeasonFilter() {
        List<String> seasons = fillSeason();

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, seasons);
        binding.spSeasonApp.setAdapter(adapter);

        binding.spSeasonApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    List<Character> filteredList = ViewHelper.filterCharBySeason(position, characterArrayList);

                    ((CharactersAdapter) charactersAdapter).filterList(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onDataLoading() {
        binding.rlMain.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    private void onDataFinishedLoading() {
        binding.rlMain.setVisibility(View.VISIBLE);
        binding.progressCircular.setVisibility(View.GONE);
    }

    private void refreshRecyclerView() {
        charactersAdapter = new CharactersAdapter(context, characterArrayList);

        ((CharactersAdapter) charactersAdapter).setOnItemClickListener(data -> {
            ((MainActivity) context).setFragment(CharacterDetailFragment.newInstance(data));
        });

        binding.rvCharacters.setLayoutManager(new LinearLayoutManager(context));
        binding.rvCharacters.setAdapter(charactersAdapter);
        binding.rvCharacters.setItemAnimator(new DefaultItemAnimator());
        binding.rvCharacters.setNestedScrollingEnabled(true);
        charactersAdapter.notifyDataSetChanged();
    }
}
