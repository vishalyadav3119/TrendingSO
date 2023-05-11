package com.example.trendingso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.trendingso.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupRecyclerView();
        new Thread(() ->
                RetroFitInstance.getInstance().getQuestions(ORDER, SORT, SITE,FILTER,KEY)
                .enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            runOnUiThread(() -> {
                                questionsAdapter.submitList(response.body().questions);
                            });
                        } else {
                            Log.e(TAG, "onResponse: response not successful for api" );
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: Failed API get request: "+t.getMessage());
                    }
                })).start();
    }
    private void setupRecyclerView(){
        questionsAdapter = new QuestionsAdapter();
        binding.recyclerView.setAdapter(questionsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}