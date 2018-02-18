package com.example.mostafahussien.interviewme.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mostafahussien.interviewme.Adapter.TopicAdapter;
import com.example.mostafahussien.interviewme.BackgroundTask.InsertService;
import com.example.mostafahussien.interviewme.BackgroundTask.RetriveService;
import com.example.mostafahussien.interviewme.Listener.OnTopicPressListener;
import com.example.mostafahussien.interviewme.Model.QuestionAnswer;
import com.example.mostafahussien.interviewme.Model.Topic;
import com.example.mostafahussien.interviewme.R;

import java.util.ArrayList;
import java.util.List;

public class AndroidActivity extends AppCompatActivity {            // momken tkon brdo de JavaActivity ht5tlf bs fe data ely hagbga mn database
    RecyclerView recyclerView;
    ArrayList<Topic> topics;
    TopicAdapter topicAdapter;
    LinearLayoutManager layoutManager;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            topics=new ArrayList<>();
            topics=intent.getParcelableArrayListExtra("topic_data");
            topicAdapter=new TopicAdapter(getApplicationContext(), topics, new OnTopicPressListener() {
                @Override
                public void onSelect(int pos, ImageView imageView) {
                    Intent quesIntent=new Intent(getApplicationContext(), QuestioneActivity.class);
                    quesIntent.putExtra("topic_name",topics.get(pos).getName());
                    quesIntent.putExtra("cat_name",topics.get(pos).getCategory());
                    quesIntent.putExtra("selected_position",pos);
                    quesIntent.putExtra("topic_id",topics.get(pos).getId());
                    quesIntent.putExtra("activity_type","normal_type");
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startAnim(imageView,quesIntent);
                    }
                    else{
                        startActivity(quesIntent);
                    }
                }
            });
            recyclerView.setAdapter(topicAdapter);
        }
    };
    public void startAnim(ImageView imageView,Intent quesIntent){
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView,"image_transition");
        startActivity(quesIntent, optionsCompat.toBundle());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_android);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        getTopicData();
    }
    public void getTopicData(){
        Intent intent=new Intent(this, RetriveService.class);
        intent.putExtra("retrieve_type","Topic");
        startService(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter("retrieve_topic_result"));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

}
