package com.example.trendingso.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.trendingso.classes.Question;

@Database(entities = {Question.class},version = 1,exportSchema = false)
public abstract class QuestionsDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
}
