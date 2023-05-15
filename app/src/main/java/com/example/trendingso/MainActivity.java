package com.example.trendingso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.trendingso.data.Question;
import com.example.trendingso.databinding.ActivityMainBinding;
import com.example.trendingso.viewmodels.OnDataSetListener;
import com.example.trendingso.viewmodels.QuestionViewModelFactory;
import com.example.trendingso.viewmodels.QuestionsViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        setupRecyclerView();
        questionsViewModel = new ViewModelProvider(this, new QuestionViewModelFactory(RetroFitInstance.getInstance()))
                .get(QuestionsViewModel.class);
        questionsViewModel.callAPI(() -> {
            questionsViewModel.questionLiveData.observe(MainActivity.this, questions -> {
                questionsAdapter.submitList(questions);
                if(questions != null){
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            });
        });
        binding.searchBar.setOnEditorActionListener((TextView.OnEditorActionListener) (textView, i, keyEvent) -> {
            Log.e(TAG, "onCreate: +called");
            Log.e(TAG, "onCreate: keyevenis "+keyEvent );
            if(i == EditorInfo.IME_ACTION_GO){
                hideKeyboard(this);
                String text = textView.getText().toString();
                binding.searchBar.getText().clear();
                binding.progressBar.setVisibility(View.VISIBLE);
                questionsViewModel.searchQuestions(text, () -> {
                    questionsViewModel.searchLiveData.observe(MainActivity.this, questions -> {
                        questionsAdapter.submitList(questions);
                        if(questions != null){
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                });
                return true;
            }
            return true;
        });
    }

    private void setupRecyclerView() {
        questionsAdapter = new QuestionsAdapter(new QuestionsAdapter.ItemClickCallback() {
            @Override
            public void onClick(Question question) {
                Intent myIntent = new Intent(MainActivity.this, WebViewActivity.class);
                myIntent.putExtra("url", question.getLink());
                startActivity(myIntent);
            }
        });
        binding.recyclerView.setAdapter(questionsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}