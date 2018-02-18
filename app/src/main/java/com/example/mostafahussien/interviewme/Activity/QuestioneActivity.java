package com.example.mostafahussien.interviewme.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mostafahussien.interviewme.Adapter.QuestionAdapter;
import com.example.mostafahussien.interviewme.Adapter.QuestionFavAdapter;
import com.example.mostafahussien.interviewme.Adapter.TopicAdapter;
import com.example.mostafahussien.interviewme.BackgroundTask.InsertService;
import com.example.mostafahussien.interviewme.BackgroundTask.RetriveFavoriteService;
import com.example.mostafahussien.interviewme.BackgroundTask.RetriveService;
import com.example.mostafahussien.interviewme.Interface.OnSelectFavorite;
import com.example.mostafahussien.interviewme.Listener.OnTopicPressListener;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.Model.QuestionAnswer;
import com.example.mostafahussien.interviewme.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuestioneActivity extends AppCompatActivity {
    String topicName,category;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    FloatingActionButton fab;
    ImageView imageView;
    TypedArray images;
    int topic_position,topic_id;
    ArrayList<QuestionAnswer>questionAnswers;
    QuestionAdapter questionAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            questionAnswers=new ArrayList<>();
            questionAnswers=intent.getParcelableArrayListExtra("question_data");
            questionAdapter=new QuestionAdapter(getApplicationContext(), questionAnswers, new OnSelectFavorite() {
               @Override
               public void onSelect(int position, String type) {
                   if(type.equals("like")){     // insert question with its topic to fav database
                       favAction("insert_fav",questionAnswers.get(position));
                   } else {         // remove question from database
                       favAction("remove_fav",questionAnswers.get(position));
                   }
               }
           });
           recyclerView.setAdapter(questionAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questione);
        topicName = getIntent().getExtras().getString("topic_name");
        category = getIntent().getExtras().getString("cat_name");
        topic_id = getIntent().getExtras().getInt("topic_id");
        topic_position = getIntent().getExtras().getInt("selected_position");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_question);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbar.setExpandedTitleMarginTop(6);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        imageView=(ImageView)findViewById(R.id.back_image);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setBackImage();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
            getQuestionAnswerData();
    }
    public void favAction(String action,QuestionAnswer questionAnswer){
        Intent favIntent=new Intent(this,InsertService.class);
        favIntent.putExtra("topic_name",topicName);
        favIntent.putExtra("question",questionAnswer);
        favIntent.putExtra("insert_type",action);
        startService(favIntent);
    }
    public void getQuestionAnswerData(){
        Intent intent=new Intent(this, RetriveService.class);
        intent.putExtra("retrieve_type","QuestionAnswer");
        intent.putExtra("topic_id",topic_id);
        intent.putExtra("topic_name",topicName);
        startService(intent);
    }


    public void setBackImage(){
        images=getResources().obtainTypedArray(R.array.android_drawable);
        imageView.setImageResource(images.getResourceId(topic_position,R.drawable.noimage));
    }
    @Override
    protected void onResume() {
        super.onResume();
            registerReceiver(receiver, new IntentFilter("retrieve_question_result"));

    }
    @Override
    protected void onPause() {
        super.onPause();
            unregisterReceiver(receiver);

    }
}
