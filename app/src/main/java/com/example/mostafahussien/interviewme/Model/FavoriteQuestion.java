package com.example.mostafahussien.interviewme.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "quest_favorite")
public class FavoriteQuestion implements Parcelable{
    @PrimaryKey(autoGenerate = true)            // auto generate id and increment it
    @ColumnInfo(name = "fav_id")
    int favorite_id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "answer")
    private String answer;
    @ColumnInfo(name = "topic_name")
    private String topic_name;
    @Ignore
    public FavoriteQuestion() {
    }

    public FavoriteQuestion(String question, String answer, String topic_name) {
        this.question = question;
        this.answer = answer;
        this.topic_name = topic_name;
    }

    protected FavoriteQuestion(Parcel in) {
        favorite_id = in.readInt();
        question = in.readString();
        answer = in.readString();
        topic_name = in.readString();
    }

    public static final Creator<FavoriteQuestion> CREATOR = new Creator<FavoriteQuestion>() {
        @Override
        public FavoriteQuestion createFromParcel(Parcel in) {
            return new FavoriteQuestion(in);
        }

        @Override
        public FavoriteQuestion[] newArray(int size) {
            return new FavoriteQuestion[size];
        }
    };

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public int getFavorite_id() {
        return favorite_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(favorite_id);
        parcel.writeString(question);
        parcel.writeString(answer);
        parcel.writeString(topic_name);
    }
}
