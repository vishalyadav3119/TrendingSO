package com.example.trendingso.viewmodels;

import android.support.v4.os.IResultReceiver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.trendingso.QuestionRepository;
import com.example.trendingso.classes.Question;
import com.example.trendingso.util.Resource;

import java.util.List;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

public class QuestionsViewModel extends ViewModel {
    private static final String TAG = "QuestionsViewModel";
    private QuestionRepository repository;
    public LiveData<Resource<List<Question>>> liveData;

    public QuestionsViewModel(QuestionRepository repository) {
        this.repository = repository;
    }
    public void getQuestions(ReturnCallback returnCallback){
        repository.getQuestions(flowable -> {
            LiveData<Resource<List<Question>>> liveData = LiveDataReactiveStreams.fromPublisher(flowable);
            returnCallback.onComplete(liveData);
        });
    }
    public interface ReturnCallback{
        void onComplete(LiveData<Resource<List<Question>>> liveData);
    }
}