package com.example.trendingso.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.trendingso.QuestionRepository;
import com.example.trendingso.data.QuestionsAPI;

public class QuestionViewModelFactory implements ViewModelProvider.Factory {
    private QuestionRepository questionRepository;

    public QuestionViewModelFactory(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new QuestionsViewModel(questionRepository);
    }
}
