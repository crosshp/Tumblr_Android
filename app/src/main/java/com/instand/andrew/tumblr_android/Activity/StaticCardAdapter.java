package com.instand.andrew.tumblr_android.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instand.andrew.tumblr_android.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 23.07.2015.
 */
public class StaticCardAdapter extends RecyclerView.Adapter<StaticCardAdapter.PersonViewHolder> {
    private View view = null;

    List<StaticCard> data;

    public List<StaticCard> getData() {
        return data;
    }

    public void addData(ArrayList<StaticCard> arrayList) {
        for (StaticCard dishCard : arrayList) {
            data.add(dishCard);
        }
    }

    StaticCardAdapter(List<StaticCard> data) {
        this.data = data;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.static_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        view = v;
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        StaticCard objectItem = data.get(i);
        personViewHolder.key.setText(objectItem.getKey());
        personViewHolder.value.setText(objectItem.getValue());
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView key;
        TextView value;

        PersonViewHolder(final View itemView) {
            super(itemView);
            key = (TextView) itemView.findViewById(R.id.keyTextView);
            value = (TextView) itemView.findViewById(R.id.valueTextView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}



