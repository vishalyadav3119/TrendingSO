package com.example.trendingso.util;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;

import com.example.trendingso.data.QuestionsDatabase;

public class DatabaseInstance {
    private static QuestionsDatabase questionsDatabase;

    private DatabaseInstance() {}

    public static QuestionsDatabase getInstance(Application application){
        if(questionsDatabase == null){
            questionsDatabase = Room.databaseBuilder
                    (application,
                    QuestionsDatabase.class,
               "questions_database")
                    .build();
        }
        return questionsDatabase;
    }
}