package com.example.trendingso.utils;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

import java.util.List;
import static android.widget.LinearLayout.LayoutParams;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

public class DataBindingAdapters {
    private static final String TAG = "DataBindingAdapters";
    @BindingAdapter("loadTags")
    public static void loadTags(LinearLayout linearLayout, List<String> tags){
        int size = Math.min(2, tags.size());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,2,0,2);
        if(linearLayout.getChildCount() < 2){
            for(int i=0;i<size;i++){
                String tag = tags.get(i);
                Chip chip = new Chip(linearLayout.getContext());
                chip.setLayoutParams(layoutParams);
                chip.setText(tag);
                linearLayout.addView(chip);
            }
        }
    }
}