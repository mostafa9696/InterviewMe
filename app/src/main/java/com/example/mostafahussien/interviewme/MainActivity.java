package com.example.mostafahussien.interviewme;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mostafahussien.interviewme.Activity.AndroidActivity;
import com.example.mostafahussien.interviewme.Activity.FavoriteActivity;
import com.example.mostafahussien.interviewme.Activity.QuestioneActivity;
import com.example.mostafahussien.interviewme.Database.AppDatabase;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.Model.Topic;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton androidBtn,javaBtn;
    ActionBarDrawerToggle toggle;
    Animation animation;
    List<Topic>topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase.getInstance(this);
        topics=new ArrayList<>();
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        androidBtn=(ImageButton)findViewById(R.id.android_btn);
        javaBtn=(ImageButton)findViewById(R.id.java_btn);
        startButtonAnim(R.anim.button_down_anim,1);
        startButtonAnim(R.anim.button_up_anim,2);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
         toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        androidBtn.setOnClickListener(this);
        javaBtn.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // testData();
    }
    /*
    @SuppressLint("StaticFieldLeak")
    public void testData(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                topics=topicDAO.getAllTopic();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                //for(Topic t : topics)
                  //  Log.d("jj7", String.valueOf(t.getId())+" "+t.getName()+" "+t.getCategory());
                super.onPostExecute(aVoid);
            }
        }.execute();
    }*/
    public void startButtonAnim(int id,int flag){
        animation= AnimationUtils.loadAnimation(this,id);
        if(flag==1) {
            androidBtn.setAnimation(animation);
        } else {
            javaBtn.setAnimation(animation);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.edit_profile)
            Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_LONG).show();
        else if(id==R.id.drawer_item_settings)
            Toast.makeText(getApplicationContext(),"setting",Toast.LENGTH_LONG).show();
        else if(id==R.id.favorite_item){
            Intent intent=new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
            return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.android_btn){
            Intent intent=new Intent(this, AndroidActivity.class);
            startActivity(intent);
        } else if(id==R.id.java_btn){
            Toast.makeText(getApplicationContext(),"java",Toast.LENGTH_LONG).show();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
}
