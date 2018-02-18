package com.example.mostafahussien.interviewme.BackgroundTask;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.utils.FavUtils;

import java.util.ArrayList;
import java.util.List;


public class RetriveFavoriteService extends IntentService {
    FavUtils favUtils;
    ArrayList<FavoriteQuestion>favoriteQuestionList;
    public RetriveFavoriteService() {
        super("retrive_favorite");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        favUtils=new FavUtils(this);
        favoriteQuestionList= (ArrayList<FavoriteQuestion>) favUtils.getAllFavs();
        sendResultToBroadcast();
    }
    public void sendResultToBroadcast(){
        Intent intent=new Intent("retrive_question_favs");
        intent.putParcelableArrayListExtra("question_fav_data",favoriteQuestionList);
        sendBroadcast(intent);
    }
}
