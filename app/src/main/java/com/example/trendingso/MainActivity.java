package com.example.trendingso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.trendingso.data.JsonResponse;
import com.example.trendingso.data.Question;
import com.example.trendingso.databinding.ActivityMainBinding;
import com.example.trendingso.viewmodels.OnDataSetListener;
import com.example.trendingso.viewmodels.QuestionViewModelFactory;
import com.example.trendingso.viewmodels.QuestionsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private QuestionsViewModel questionsViewModel;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private QuestionsAdapter questionsAdapter;
    private final String ORDER = "desc";
    private final String SORT = "hot";
    private final String SITE = "stackoverflow";
    private final String FILTER = "!-NPfkDD6rjlaOThHZ8L7x1y6YZW8FbktT";
    private final String KEY = "GkQFPf46lyw8PfbEMXVvyw((";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setupRecyclerView();
        questionsViewModel = new ViewModelProvider(this,new QuestionViewModelFactory(RetroFitInstance.getInstance(), new OnDataSetListener() {
            @Override
            public void onComplete() {
                questionsViewModel.liveData.observe(MainActivity.this, questions -> {
                    questionsAdapter.submitList(questions);
                });
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        })).get(QuestionsViewModel.class);
//        new Thread(this::callAPIAndUpdateUI).start();
    }
    private void setupRecyclerView(){
        questionsAdapter = new QuestionsAdapter();
        binding.recyclerView.setAdapter(questionsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void callAPIAndUpdateUI() {
        RetroFitInstance.getInstance().getQuestions(ORDER, SORT, SITE, FILTER, KEY)
                .enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            runOnUiThread(() -> {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                questionsAdapter.submitList(response.body().getQuestions());
                            });
                        } else {
                            Log.e(TAG, "onResponse: response not successful for api");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: Failed API get request: " + t.getMessage());
                    }
                });
    }
}