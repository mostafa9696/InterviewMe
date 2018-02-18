package com.example.mostafahussien.interviewme.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.mostafahussien.interviewme.Model.QuestionAnswer;
import com.example.mostafahussien.interviewme.Model.Topic;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseAccess instance;
    private static Context con;
    private DatabaseAccess(Context context) {
        this.sqLiteOpenHelper = new DatabaseOpenHelper(context);
    }
    public static DatabaseAccess getInstance(Context context) {
        con=context;
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    public void openDatabase() {
        this.sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }
    public void closeDatabase() {
        if (sqLiteDatabase != null) {
            this.sqLiteDatabase.close();
        }
    }
    public ArrayList<Topic> getTopics(){
        ArrayList<Topic> topics = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Topic", null);
        cursor.moveToFirst();
        int topic_id;
        String topic_name,topic_cat;
        while (!cursor.isAfterLast()) {
            topic_id=cursor.getInt( cursor.getColumnIndex("topic_id") );
            topic_name=cursor.getString( cursor.getColumnIndex("topic_name") );
            topic_cat=cursor.getString( cursor.getColumnIndex("topic_cat") );
            topics.add(new Topic(topic_id,topic_name,topic_cat));
            cursor.moveToNext();
        }
        cursor.close();
        return topics;
    }
    public ArrayList<QuestionAnswer>getQuestionAnswer(int topicID){
        String[] args = new String[]{String.valueOf(topicID)};
        ArrayList<QuestionAnswer> questionAnswers = new ArrayList<>();
        String MY_QUERY = "SELECT * FROM AnswerQuestion WHERE topic_id=?";
        Cursor cursor = sqLiteDatabase.rawQuery(MY_QUERY, args);
        int topic_id,question_id;
        String question,answer;
        if(cursor!=null && cursor.getCount()>0 ) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                    question_id = cursor.getInt(cursor.getColumnIndex("answer_id"));
                    topic_id = cursor.getInt(cursor.getColumnIndex("topic_id"));
                    question = cursor.getString(cursor.getColumnIndex("question"));
                    answer = cursor.getString(cursor.getColumnIndex("answer"));
                    questionAnswers.add(new QuestionAnswer(question_id, question, answer, topic_id,true));
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.d("ee22", " "+topicID+" "+questionAnswers.size());
        return questionAnswers;
    }
    public int getNameID(String topicName) {
        String[] args = new String[]{topicName};
        String[] col = new String[]{"topic_id"};
        Cursor cursor = sqLiteDatabase.query("Topic",col,"topic_name=?",args,null,null,null);
        if(cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return -1;
    }
}
