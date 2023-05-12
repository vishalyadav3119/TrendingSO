package com.example.trendingso.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.trendingso.QuestionsAPI;

public class QuestionViewModelFactory implements ViewModelProvider.Factory {
    private QuestionsAPI questionsAPI;
    private OnDataSetListener listener;

    public QuestionViewModelFactory(QuestionsAPI questionsAPI,OnDataSetListener listener) {
        this.questionsAPI = questionsAPI;
        this.listener = listener;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new QuestionsViewModel(questionsAPI,listener);
    }
}
