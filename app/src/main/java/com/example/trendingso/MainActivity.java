package com.example.trendingso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.trendingso.classes.Question;
import com.example.trendingso.databinding.ActivityMainBinding;
import com.example.trendingso.util.DatabaseInstance;
import com.example.trendingso.util.QuestionRepositoryInstance;
import com.example.trendingso.util.Resource;
import com.example.trendingso.viewmodels.QuestionViewModelFactory;
import com.example.trendingso.viewmodels.QuestionsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private QuestionsViewModel questionsViewModel;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private QuestionsAdapter questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupRecyclerView();
        questionsViewModel = new ViewModelProvider(
                this,
                new QuestionViewModelFactory(QuestionRepositoryInstance.getInstance(getApplication())))
                        .get(QuestionsViewModel.class);


        questionsViewModel.getQuestions(new QuestionsViewModel.ReturnCallback() {
            @Override
            public void onComplete(LiveData<Resource<List<Question>>> liveData) {
                liveData.observe(MainActivity.this, new Observer<Resource<List<Question>>>() {
                    @Override
                    public void onChanged(Resource<List<Question>> listResource) {
                        questionsAdapter.submitList(listResource.data);
                        if(listResource.state == Resource.State.SUCCESS){
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void setupRecyclerView() {
        questionsAdapter = new QuestionsAdapter();
        binding.recyclerView.setAdapter(questionsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}