package com.joseortale.ortalesoft.bbchars.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.joseortale.ortalesoft.bbchars.R;
import com.joseortale.ortalesoft.bbchars.model.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    private List<Character> data;
    private LayoutInflater inflater;
    private ClickListener<Character> clickListener;

    public CharactersAdapter(Context context, List<Character> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public interface ClickListener<T> {
        void onItemClick(T data);
    }

    public void setOnItemClickListener(ClickListener<Character> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Character character = data.get(position);
        holder.tvName.setText(character.getName());
        holder.cardView.setOnClickListener(v -> clickListener.onItemClick(character));
        Picasso.get().load(character.getImg()).resize(50, 50).centerCrop().into(holder.ivChar);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(List<Character> characters) {
        data = characters;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {;
        TextView tvName;
        ConstraintLayout cardView;
        ImageView ivChar;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            cardView = itemView.findViewById(R.id.cons_view);
            ivChar = itemView.findViewById(R.id.iv_char);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
