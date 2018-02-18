package com.example.mostafahussien.interviewme.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.mostafahussien.interviewme.Adapter.QuestionFavAdapter;
import com.example.mostafahussien.interviewme.BackgroundTask.RetriveFavoriteService;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.R;
import com.example.mostafahussien.interviewme.utils.FavUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<FavoriteQuestion>  favoriteQuestions;
    QuestionFavAdapter questionFavAdapter;
    LinearLayout linearLayout;
    private BroadcastReceiver favReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            favoriteQuestions=new ArrayList<>();
            favoriteQuestions=intent.getParcelableArrayListExtra("question_fav_data");
            if(favoriteQuestions.size()>0) {
                //  sorted by topic_name
                favoriteQuestions = FavUtils.sortFavs(favoriteQuestions);
                questionFavAdapter = new QuestionFavAdapter(getApplicationContext(), favoriteQuestions);
                recyclerView.setAdapter(questionFavAdapter);
            } else {
                 Snackbar.make(linearLayout, "No inserted favorite to show !", Snackbar.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_questionFav);
        linearLayout=(LinearLayout)findViewById(R.id.linear);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getQuestionAnswerFavorite();
    }
    public void getQuestionAnswerFavorite(){
        Intent intent=new Intent(this, RetriveFavoriteService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(favReceiver, new IntentFilter("retrive_question_favs"));

    }
    @Override
    protected void onPause() {
        super.onPause();
            unregisterReceiver(favReceiver);
    }
}
