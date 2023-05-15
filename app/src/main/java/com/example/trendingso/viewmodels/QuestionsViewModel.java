package com.example.trendingso.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trendingso.QuestionsAPI;
import com.example.trendingso.data.QuestionsResponse;
import com.example.trendingso.data.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsViewModel extends ViewModel {
    private static final String TAG = "QuestionsViewModel";
    private MutableLiveData<List<Question>> mutableLiveData;
    public LiveData<List<Question>> questionLiveData;
    public LiveData<List<Question>> searchLiveData;
    QuestionsAPI questionsAPI;
    private final String ORDER = "desc";
    private final String SORT = "hot";
    private final String SEARCH_SORT = "activity";
    private final String SITE = "stackoverflow";
    private final String FILTER = "!-NPfkDD6rjlaOThHZ8L7x1y6YZW8FbktT";
    private final String KEY = "GkQFPf46lyw8PfbEMXVvyw((";
    public QuestionsViewModel(QuestionsAPI questionsAPI) {
        mutableLiveData = new MutableLiveData<>();
        this.questionsAPI = questionsAPI;
    }

    public void callAPI(OnDataSetListener listener) {
        new Thread(()->{
            questionsAPI.getQuestions(ORDER,SORT ,SITE,FILTER,KEY).enqueue(new Callback<QuestionsResponse>() {
                @Override
                public void onResponse(Call<QuestionsResponse> call, Response<QuestionsResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        List<Question> questions = response.body().getQuestions();
                        mutableLiveData.setValue(questions);
                        questionLiveData = mutableLiveData;
                        listener.onComplete();
                    }else{
                        Log.e(TAG, "onResponse: API Response Error: "+ response.getClass() );
                    }
                }

                @Override
                public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: StackOverFlow Questions API Error: "+t.getMessage());
                }
            });
        }).start();
    }
    public void searchQuestions(String intitle,OnDataSetListener listener){
        new Thread(() -> {
            questionsAPI.searchQuestions(intitle,ORDER,SEARCH_SORT,SITE,FILTER,KEY).enqueue(new Callback<QuestionsResponse>() {
                @Override
                public void onResponse(Call<QuestionsResponse> call, Response<QuestionsResponse> response) {
                    Log.e(TAG, "onResponse: response is : "+response );
                    if(response.isSuccessful() && response.body() != null){
                        List<Question> questions = response.body().getQuestions();
                        mutableLiveData.setValue(questions);
                        searchLiveData = mutableLiveData;
                        listener.onComplete();
                    }
                }

                @Override
                public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: StackOverFlow Search API Error: "+t.getMessage());
                }
            });
        }).start();
    }
}