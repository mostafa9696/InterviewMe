package com.example.mostafahussien.interviewme.BackgroundTask;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.mostafahussien.interviewme.DAO.FavoriteDAO;
import com.example.mostafahussien.interviewme.Database.AppDatabase;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.Model.QuestionAnswer;

public class InsertService extends IntentService{
    FavoriteDAO favoriteDAO;
    String insert_type,topic_name;
    QuestionAnswer questionAnswer;
    FavoriteQuestion favoriteQuestion;
    public InsertService() {
        super("InitData");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        favoriteDAO= AppDatabase.getInstance(this).getFavoriteDAO();
        insert_type=intent.getExtras().getString("insert_type");
        topic_name=intent.getExtras().getString("topic_name");
        questionAnswer=intent.getExtras().getParcelable("question");
        favoriteQuestion=new FavoriteQuestion(questionAnswer.getQuestion(),questionAnswer.getAnswer(),topic_name);
        if(insert_type.equals("insert_fav")){
            favoriteDAO.inserFav(favoriteQuestion);
        } else if(insert_type.equals("remove_fav")) {
            int fav_id=favoriteDAO.getFavID(topic_name,questionAnswer.getAnswer());
            favoriteQuestion.setFavorite_id(fav_id);
            favoriteDAO.deleteFav(favoriteQuestion);
        }
    }

}
