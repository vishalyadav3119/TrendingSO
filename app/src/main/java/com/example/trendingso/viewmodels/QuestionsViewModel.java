package com.example.trendingso.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trendingso.QuestionsAPI;
import com.example.trendingso.data.JsonResponse;
import com.example.trendingso.data.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsViewModel extends ViewModel {
    private static final String TAG = "QuestionsViewModel";
    private MutableLiveData<List<Question>> mutableLiveData;
    public LiveData<List<Question>> liveData;
    QuestionsAPI questionsAPI;
    OnDataSetListener listener;
    private final String ORDER = "desc";
    private final String SORT = "hot";
    private final String SITE = "stackoverflow";
    private final String FILTER = "!-NPfkDD6rjlaOThHZ8L7x1y6YZW8FbktT";
    private final String KEY = "GkQFPf46lyw8PfbEMXVvyw((";
    public QuestionsViewModel(QuestionsAPI questionsAPI, OnDataSetListener listener) {
        mutableLiveData = new MutableLiveData<>();
        this.questionsAPI = questionsAPI;
        this.listener = listener;
        callAPI(questionsAPI);
    }

    private void callAPI(QuestionsAPI questionsAPI) {
        new Thread(()->{
            questionsAPI.getQuestions(ORDER,SORT ,SITE,FILTER,KEY).enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        List<Question> questions = response.body().getQuestions();
                        mutableLiveData.setValue(questions);
                        liveData = mutableLiveData;
                        listener.onComplete();
                    }else{
                        Log.e(TAG, "onResponse: API Response Error: "+ response.getClass() );
                    }
                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: StackOverFlow API Error: "+t.getMessage());
                }
            });
        }).start();
    }
}