package com.example.mostafahussien.interviewme.Database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.mostafahussien.interviewme.DAO.FavoriteDAO;
import com.example.mostafahussien.interviewme.Model.FavoriteQuestion;

@Database(entities = { FavoriteQuestion.class},version = 7)                    // version of our database (which will be incremented every time we change something in the database schema).
public abstract class AppDatabase extends RoomDatabase{
    private static final String DB_NAME = "appDatabase.db";
    private static volatile AppDatabase instance;
    private static Context con;
    public static synchronized AppDatabase getInstance(Context context){       // singletone pattern
        con=context;
        if(instance==null) {
            instance = create(context);
        }
        return instance;
    }
    static final Migration MIGRATION_3_4 = new Migration(3, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //database.execSQL("DROP TABLE topic");
            //database.execSQL("DROP TABLE favorite");
            database.execSQL("CREATE TABLE IF NOT EXISTS quest_favorite ( fav_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                    " question TEXT ,\n" +
                    " answer TEXT ,\n" +
                    " topic_name TEXT )" );
        }
    };

    private static AppDatabase create(Context context){
        return Room.databaseBuilder(context,AppDatabase.class,DB_NAME)
                .addMigrations(MIGRATION_3_4)
                .build();
    }
    public abstract FavoriteDAO getFavoriteDAO();
    //public abstract QuestionDAO getQuestionDAO();
}
