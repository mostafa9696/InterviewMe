package com.example.mostafahussien.interviewme.utils;


import android.content.Context;

import com.example.mostafahussien.interviewme.DAO.FavoriteDAO;
import com.example.mostafahussien.interviewme.Database.AppDatabase;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FavUtils {
    Context context;
    FavoriteDAO favoriteDAO;
    public FavUtils(Context context) {
        this.context = context;
        favoriteDAO= AppDatabase.getInstance(context).getFavoriteDAO();
    }
    public void addToFav(FavoriteQuestion favoriteQuestion){
        favoriteDAO.inserFav(favoriteQuestion);
    }
    public void removeFav(FavoriteQuestion favoriteQuestion){
        favoriteDAO.deleteFav(favoriteQuestion);
    }
    public List<FavoriteQuestion> getFavs(String topic_name){
        List<FavoriteQuestion>favoriteQuestions=new ArrayList<>();
        favoriteQuestions=favoriteDAO.getAllFavsByName(topic_name);
        return favoriteQuestions;
    }
    public List<FavoriteQuestion> getAllFavs(){
        List<FavoriteQuestion>favoriteQuestions=new ArrayList<>();
        favoriteQuestions=favoriteDAO.getAllFav();
        return favoriteQuestions;
    }
    public static ArrayList<FavoriteQuestion>sortFavs(ArrayList<FavoriteQuestion> favoriteQuestions){
        HashSet<String> set=new HashSet<String>();
        ArrayList<FavoriteQuestion> results=new ArrayList<>();
        for(FavoriteQuestion F:favoriteQuestions) {
            set.add(F.getTopic_name());
        }
        for (String s : set) {
            for(FavoriteQuestion F:favoriteQuestions){
                if(F.getTopic_name().equals(s))
                    results.add(F);
            }
        }
        return results;
    }
}
