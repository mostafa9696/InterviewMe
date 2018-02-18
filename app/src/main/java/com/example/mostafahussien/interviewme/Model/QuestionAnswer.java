package com.example.mostafahussien.interviewme.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class QuestionAnswer implements Parcelable{

    private int id;
    private String question;
    private String answer;
    private int topicID;
    private boolean isExpandable;       // always = true in this app
    private boolean isFav;
    public QuestionAnswer() {
    }
    public QuestionAnswer(int id, String question, String answer, int topicID, boolean isExpandable) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.topicID = topicID;
        this.isExpandable = isExpandable;
    }
    public QuestionAnswer(int topicID,String question, String answer, boolean isExpandable) {       // for list of favs
        this.topicID = topicID;
        this.question = question;
        this.answer = answer;
        this.isExpandable = isExpandable;
        this.isFav=true;
    }


    public static final Creator<QuestionAnswer> CREATOR = new Creator<QuestionAnswer>() {
        @Override
        public QuestionAnswer createFromParcel(Parcel in) {
            return new QuestionAnswer(in);
        }

        @Override
        public QuestionAnswer[] newArray(int size) {
            return new QuestionAnswer[size];
        }
    };


    public boolean isExpandable() {
        return isExpandable;
    }

    public boolean isFav() {
        return isFav;
    }

    public static Creator<QuestionAnswer> getCREATOR() {
        return CREATOR;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    protected QuestionAnswer(Parcel in) {
        id = in.readInt();
        question = in.readString();
        answer = in.readString();
        topicID = in.readInt();
        isExpandable = in.readByte() != 0;     //myBoolean == true if byte != 0
        isFav = in.readByte() != 0;     //myBoolean == true if byte != 0
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getTopicID() {
        return topicID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(question);
        parcel.writeString(answer);
        parcel.writeInt(topicID);
        parcel.writeByte((byte) (isExpandable ? 1 : 0));
        parcel.writeByte((byte) (isFav ? 1 : 0));
    }
}
