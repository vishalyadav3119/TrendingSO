package com.example.trendingso.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.trendingso.classes.Question;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public abstract class QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertQuestions(List<Question> questions);

    @Query("DELETE FROM questions")
    public abstract void deleteAllQuestions();

    @Query("SELECT * FROM questions")
    public abstract Flowable<List<Question>> getAllQuestions();

    @Transaction
    public void insertAndDeleteInTransaction(List<Question> questions) {
        deleteAllQuestions();
        insertQuestions(questions);
    }
}
