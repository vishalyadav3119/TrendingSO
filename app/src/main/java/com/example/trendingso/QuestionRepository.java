package com.example.trendingso;

import android.app.Application;

import com.example.trendingso.classes.JsonResponse;
import com.example.trendingso.classes.Question;
import com.example.trendingso.data.QuestionsAPI;
import com.example.trendingso.data.QuestionsDatabase;
import com.example.trendingso.util.DatabaseInstance;
import com.example.trendingso.util.Resource;
import com.example.trendingso.util.RetroFitInstance;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableAll;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import kotlinx.coroutines.flow.Flow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionRepository{
    private final String ORDER = "desc";
    private final String SORT = "hot";
    private final String SITE = "stackoverflow";
    private final String FILTER = "!-NPfkDD6rjlaOThHZ8L7x1y6YZW8FbktT";
    private final String KEY = "GkQFPf46lyw8PfbEMXVvyw((";
    QuestionsAPI questionsAPI;
    QuestionsDatabase questionsDatabase;
    public QuestionRepository(Application application){
        questionsAPI = RetroFitInstance.getInstance();
        questionsDatabase = DatabaseInstance.getInstance(application);
    }
    public void getQuestions(ReturnCallback callback){
        if(shouldFetch()){
            new ApiCallThread(flowable -> {
                new SaveInDBThread(flowable, () -> {
                    new LoadFromDbThread((flow) -> {
                        callback.onComplete(flow);
                    });
                });
            });
        }
    }
    protected void saveCallResult(List<Question> questions) {
        if(questions != null){
            questionsDatabase.questionDao().insertAndDeleteInTransaction(questions);
        }
    }

    protected boolean shouldFetch() {
        return true;
    }

    protected Flowable<Resource<List<Question>>> loadFromDb() {
        return questionsDatabase.questionDao().getAllQuestions()
                .map(Resource::success);
    }

    protected void createCall(CreateCallCallback callback) {
        questionsAPI.getQuestions(ORDER,SORT,SITE,FILTER,KEY).enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    callback.onComplete(null,response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                callback.onComplete(t,null);
            }
        });
    }
    class ApiCallThread implements Runnable{
        ApiCallCallback callback;
        public ApiCallThread(ApiCallCallback callback){
            this.callback = callback;
        }

        @Override
        public void run() {
            createCall((th, jsonResponse) -> {
                Flowable<Resource<List<Question>>> flowable;
                if(th == null) flowable = Flowable.just(jsonResponse.getQuestions()).map(Resource::success);
                else flowable = Flowable.just(jsonResponse.getQuestions()).map(questions -> Resource.failure(questions,th));
                callback.onComplete(flowable);
            });
        }
    }
    interface ApiCallCallback{
        void onComplete(Flowable<Resource<List<Question>>> flowable);
    }
    class SaveInDBThread implements Runnable{
        SaveInDbCallback saveInDbCallback;
        Flowable<Resource<List<Question>>> flowable;

        public SaveInDBThread(Flowable<Resource<List<Question>>> flowable,SaveInDbCallback saveInDbCallback) {
            this.saveInDbCallback = saveInDbCallback;
            this.flowable = flowable;
        }

        @Override
        public void run() {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = flowable.subscribe((resource)->{
                saveCallResult(resource.data);
                saveInDbCallback.onComplete();
            });
            compositeDisposable.add(disposable);
        }
    }
    interface SaveInDbCallback{
        void onComplete();
    }
    class LoadFromDbThread implements Runnable{
        LoadFromDbCallback callback;

        public LoadFromDbThread(LoadFromDbCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.onComplete(loadFromDb());
        }
    }
    interface LoadFromDbCallback{
        void onComplete(Flowable<Resource<List<Question>>> flowable);
    }
    interface CreateCallCallback{
        void onComplete(Throwable th,JsonResponse jsonResponse);
    }
    public interface  ReturnCallback{
        void onComplete(Flowable<Resource<List<Question>>> flowable);
    }
}