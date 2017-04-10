package com.warm.pic.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author: Trimph
 * data: 2017/3/31.
 * description: 文件夹
 */

public class Floder implements Parcelable {

    public String name;
    public String path;
    public Image firstImage;

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

    public Image getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(Image firstImage) {
        this.firstImage = firstImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeParcelable(this.firstImage, flags);
    }

    public Floder() {
    }

    protected Floder(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.firstImage = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Creator<Floder> CREATOR = new Creator<Floder>() {
        @Override
        public Floder createFromParcel(Parcel source) {
            return new Floder(source);
        }

        @Override
        public Floder[] newArray(int size) {
            return new Floder[size];
        }
    };
}
