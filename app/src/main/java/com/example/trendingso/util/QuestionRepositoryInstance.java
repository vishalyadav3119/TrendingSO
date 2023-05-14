package com.example.trendingso.util;

import android.app.Application;

import com.example.trendingso.QuestionRepository;
import com.example.trendingso.data.QuestionsDatabase;

public class QuestionRepositoryInstance {
    private QuestionRepositoryInstance(){};
    private static QuestionRepository questionRepository;
    public static QuestionRepository getInstance(Application application){
        if(questionRepository == null){
            questionRepository = new QuestionRepository(application);
        }
        return questionRepository;
    }
}
