package com.warm.pic.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: Trimph
 * data: 2017/3/31.
 * description:
 */

public class Image implements Parcelable {
    public String name;
    public String path;
    public String time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Image(String name, String path, String time) {
        this.name = name;
        this.path = path;
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeString(this.time);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.time = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
