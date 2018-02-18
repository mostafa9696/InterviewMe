package com.example.mostafahussien.interviewme.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;

import java.util.List;


@Dao
public interface FavoriteDAO {
    @Query("select * from quest_favorite where topic_name=:topic_name")
    List<FavoriteQuestion> getAllFavsByName(String topic_name);

    @Query("select * from quest_favorite")
    List<FavoriteQuestion> getAllFav();

    @Query("select * from quest_favorite where question=:favQuesName")
    FavoriteQuestion getFavByName(String favQuesName);

    @Insert
    void inserFav(FavoriteQuestion... FavoriteQuestions);           // ... means that it can take more than one topic

    @Update
    void updateFav(FavoriteQuestion... FavoriteQuestions);

    @Delete
    void deleteFav(FavoriteQuestion... FavoriteQuestions);

    @Query("SELECT * FROM quest_favorite WHERE fav_id=:id")
    FavoriteQuestion getFav(int id);

    @Query("DELETE FROM quest_favorite")
    public void deleteAll();

    @Query("SELECT fav_id FROM quest_favorite WHERE topic_name=:topic_name AND answer=:answer")
    public int getFavID(String topic_name,String answer);
}
