package com.joseortale.ortalesoft.bbchars.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joseortale.ortalesoft.bbchars.R;
import com.joseortale.ortalesoft.bbchars.databinding.FragmentDetailCharacterBinding;
import com.joseortale.ortalesoft.bbchars.databinding.FragmentListCharacterBinding;
import com.joseortale.ortalesoft.bbchars.helpers.AppCons;
import com.joseortale.ortalesoft.bbchars.helpers.Resource;
import com.joseortale.ortalesoft.bbchars.model.Character;
import com.joseortale.ortalesoft.bbchars.view.activities.MainActivity;
import com.joseortale.ortalesoft.bbchars.view.adapters.CharactersAdapter;
import com.joseortale.ortalesoft.bbchars.viewmodel.CharactersViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterDetailFragment extends Fragment {
    private Context context;

    private List<Character> characterArrayList = new ArrayList<>();

    private FragmentDetailCharacterBinding binding;

    private Character character;

    public static CharacterDetailFragment newInstance(Character character) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCons.KEY_CHARACTER, character);

        fragment.setArguments(bundle);

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            character = (Character) bundle.getSerializable(AppCons.KEY_CHARACTER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_character, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupView();
    }

    private void setupView() {
        if (character != null) {
            binding.tvName.setText(character.getName());
            binding.tvStatus.setText(character.getStatus());
            binding.tvNickname.setText(character.getNickname());

            int defaultMargin = (int) context.getResources().getDimension(R.dimen.default_padding);

            Picasso.get().load(character.getImg()).resize(250, 250).centerCrop().into(binding.ivImage);

            if (character.getOccupation().isEmpty()) {
                binding.lineOccupation.setVisibility(View.GONE);
            }

            else {
                binding.lineOccupation.setVisibility(View.VISIBLE);

                binding.lineOccupation.removeAllViews();

                LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvLayoutParams.bottomMargin = defaultMargin;
                tvLayoutParams.topMargin = defaultMargin;
                tvLayoutParams.leftMargin = defaultMargin;
                tvLayoutParams.rightMargin = defaultMargin;

                for (String occupation : character.getOccupation()) {
                    TextView tvOccupation = new TextView(context);
                    tvOccupation.setText(occupation);
                    tvOccupation.setTextColor(Color.BLACK);
                    tvOccupation.setLayoutParams(tvLayoutParams);

                    binding.lineOccupation.addView(tvOccupation);
                }
            }

            if (character.getAppearance().isEmpty()) {
                binding.lineSeasonAppearance.setVisibility(View.GONE);
            }

            else {
                binding.lineSeasonAppearance.setVisibility(View.VISIBLE);

                binding.lineSeasonAppearance.removeAllViews();

                LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvLayoutParams.bottomMargin = defaultMargin;
                tvLayoutParams.topMargin = defaultMargin;
                tvLayoutParams.leftMargin = defaultMargin;
                tvLayoutParams.rightMargin = defaultMargin;

                for (Integer appearance : character.getAppearance()) {
                    TextView tvAppearance = new TextView(context);
                    tvAppearance.setText("Season " + appearance);
                    tvAppearance.setTextColor(Color.BLACK);
                    tvAppearance.setLayoutParams(tvLayoutParams);

                    binding.lineSeasonAppearance.addView(tvAppearance);
                }
            }
        }
    }
}
