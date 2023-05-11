package com.example.trendingso;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendingso.data.Question;
import com.example.trendingso.databinding.ItemQuestionBinding;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private AsyncListDiffer<Question> mDiffer;
    public QuestionsAdapter() {
        mDiffer = new AsyncListDiffer<>(this, diffCallback);
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder{
        public ItemQuestionBinding binding;
        public QuestionsViewHolder(@NonNull ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public QuestionsAdapter.QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionsViewHolder(ItemQuestionBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.QuestionsViewHolder holder, int position) {
        holder.binding.questionTitle.setText(mDiffer.getCurrentList().get(position).getTitle());
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
    public Question getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }
}
