package com.example.mostafahussien.interviewme.BackgroundTask;


import android.app.IntentService;
import android.content.Intent;

import android.support.annotation.Nullable;
import android.util.Log;


import com.example.mostafahussien.interviewme.Database.DatabaseAccess;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;
import com.example.mostafahussien.interviewme.Model.QuestionAnswer;
import com.example.mostafahussien.interviewme.Model.Topic;
import com.example.mostafahussien.interviewme.utils.FavUtils;


import java.util.ArrayList;
import java.util.List;

public class RetriveService extends IntentService{
    ArrayList<Topic>topicArrayList;
    ArrayList<QuestionAnswer> questionAnswers;
    DatabaseAccess databaseAccess ;
    String type,topic_name;
    int topic_id;
    FavUtils favUtils;
    public RetriveService() {
        super("retreive_data");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        type=intent.getExtras().getString("retrieve_type");
        topic_id=intent.getExtras().getInt("topic_id");
        databaseAccess=DatabaseAccess.getInstance(this);
        databaseAccess.openDatabase();
        if(type.equals("Topic")) {
            topicArrayList = new ArrayList<>();
            topicArrayList = databaseAccess.getTopics();
            sendTopicResult();
        } else {
            questionAnswers=new ArrayList<>();
            questionAnswers=databaseAccess.getQuestionAnswer(topic_id);
            favUtils=new FavUtils(this);
            topic_name=intent.getExtras().getString("topic_name");
            checkForFav();
            sendQuestionResult();
        }
        databaseAccess.closeDatabase();
    }
    public void checkForFav(){
        List<FavoriteQuestion>favs=favUtils.getFavs(topic_name);

            boolean fav;
            for(int j=0 ; j<questionAnswers.size() ; j++){
                fav=false;
                for(int i=0 ; i<favs.size() ; i++){
                    if(j==0)
                        Log.d("hh", favs.get(i).getQuestion());
                if(questionAnswers.get(j).getQuestion().equals(favs.get(i).getQuestion())){
                    questionAnswers.get(j).setFav(true);
                    fav=true;
                    break;
                }
            }
                if(!fav){
                    questionAnswers.get(j).setFav(false);
                }
        }
    }
    public void sendTopicResult(){
        Intent intent=new Intent("retrieve_topic_result");
        intent.putParcelableArrayListExtra("topic_data", topicArrayList);
        sendBroadcast(intent);
    }
    public void sendQuestionResult(){
        Intent intent=new Intent("retrieve_question_result");
        intent.putParcelableArrayListExtra("question_data", questionAnswers);
        sendBroadcast(intent);
    }
}
