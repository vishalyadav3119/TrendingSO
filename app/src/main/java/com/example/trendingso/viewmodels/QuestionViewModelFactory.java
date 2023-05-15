package com.example.trendingso.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.trendingso.QuestionsAPI;

public class QuestionViewModelFactory implements ViewModelProvider.Factory {
    private QuestionsAPI questionsAPI;

    public QuestionViewModelFactory(QuestionsAPI questionsAPI) {
        this.questionsAPI = questionsAPI;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new QuestionsViewModel(questionsAPI);
    }
}
