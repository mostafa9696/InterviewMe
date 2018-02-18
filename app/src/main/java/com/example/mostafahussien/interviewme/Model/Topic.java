package com.example.mostafahussien.interviewme.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable {
    private int id;
    private String name;
    private String category;
    private byte[] image;
    public Topic() {
    }
    public Topic(String name, String category) {
        this.name = name;
        this.category = category;
    }
    @Ignore
    public Topic(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
    protected Topic(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        image = in.createByteArray();
    }
    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeByteArray(image);
    }
}
