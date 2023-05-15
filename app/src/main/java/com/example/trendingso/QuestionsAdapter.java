package com.example.trendingso;

import android.content.ClipData;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendingso.data.Question;
import com.example.trendingso.databinding.ItemAdBinding;
import com.example.trendingso.databinding.ItemQuestionBinding;
import com.example.trendingso.viewmodels.QuestionsViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AsyncListDiffer<Question> mDiffer;
    private ItemClickCallback itemClickCallback;
    private static final int CONTENT_TYPE = 0;
    public static final int AD_TYPE  = 1;
    public QuestionsAdapter(ItemClickCallback itemClickCallback) {
        mDiffer = new AsyncListDiffer<>(this, diffCallback);
        this.itemClickCallback = itemClickCallback;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder{
        public ItemQuestionBinding binding;
        public QuestionsViewHolder(@NonNull ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public class AdsViewHolder extends RecyclerView.ViewHolder{
        View view;
        public AdsViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == CONTENT_TYPE){
            ItemQuestionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_question,parent,false);
            return new QuestionsViewHolder(binding);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad,parent,false);
            AdView adView = v.findViewById(R.id.adview);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            return  new AdsViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == CONTENT_TYPE){
            QuestionsViewHolder qvh = (QuestionsViewHolder) holder;
            ItemQuestionBinding binding = qvh.binding;
            Question question = mDiffer.getCurrentList().get(position);
            binding.setQuestion(question);
            binding.executePendingBindings();
            binding.cardView.setOnClickListener(view -> {
                itemClickCallback.onClick(question);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }
    private DiffUtil.ItemCallback<Question> diffCallback = new DiffUtil.ItemCallback<Question>() {
        @Override
        public boolean areItemsTheSame(@NonNull Question oldItem, @NonNull Question newItem) {
            return oldItem.getQuestionId() == newItem.getQuestionId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Question oldItem, @NonNull Question newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };
    public void submitList(List<Question> data) {
        mDiffer.submitList(data);
    }
    public interface ItemClickCallback{
        void onClick(Question question);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 6 == 0)
            return AD_TYPE;
        return CONTENT_TYPE;
    }
}