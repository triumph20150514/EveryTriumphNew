package com.warm.pic.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: Trimph
 * data: 2017/4/17.
 * description:
 */

public class UpdateMode implements Parcelable {


    /**
     * name : PicSelectLibrary
     * version : 3
     * changelog : 。。。。。。。。。。。
     * updated_at : 1491795988
     * versionShort : 3.0
     * build : 3
     * installUrl : http://download.fir.im/v2/app/install/58eafa8a959d69121e000386?download_token=b84d4965a0445209e6e9d488d9bb58c3&source=update
     * install_url : http://download.fir.im/v2/app/install/58eafa8a959d69121e000386?download_token=b84d4965a0445209e6e9d488d9bb58c3&source=update
     * direct_install_url : http://download.fir.im/v2/app/install/58eafa8a959d69121e000386?download_token=b84d4965a0445209e6e9d488d9bb58c3&source=update
     * update_url : http://fir.im/9lvc
     * binary : {"fsize":1898892}
     */

    private String name;
    private String version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    /**
     * fsize : 1898892
     */

    private BinaryBean binary;

    @Override
    public String toString() {
        return "UpdateMode{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", changelog='" + changelog + '\'' +
                ", updated_at=" + updated_at +
                ", versionShort='" + versionShort + '\'' +
                ", build='" + build + '\'' +
                ", installUrl='" + installUrl + '\'' +
                ", install_url='" + install_url + '\'' +
                ", direct_install_url='" + direct_install_url + '\'' +
                ", update_url='" + update_url + '\'' +
                ", binary=" + binary +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }


    public static class BinaryBean implements Parcelable {
        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.version);
        dest.writeString(this.changelog);
        dest.writeInt(this.updated_at);
        dest.writeString(this.versionShort);
        dest.writeString(this.build);
        dest.writeString(this.installUrl);
        dest.writeString(this.install_url);
        dest.writeString(this.direct_install_url);
        dest.writeString(this.update_url);
        dest.writeParcelable(this.binary, flags);
    }

    public UpdateMode() {
    }

    protected UpdateMode(Parcel in) {
        this.name = in.readString();
        this.version = in.readString();
        this.changelog = in.readString();
        this.updated_at = in.readInt();
        this.versionShort = in.readString();
        this.build = in.readString();
        this.installUrl = in.readString();
        this.install_url = in.readString();
        this.direct_install_url = in.readString();
        this.update_url = in.readString();
        this.binary = in.readParcelable(BinaryBean.class.getClassLoader());
    }

    public static final Creator<UpdateMode> CREATOR = new Creator<UpdateMode>() {
        @Override
        public UpdateMode createFromParcel(Parcel source) {
            return new UpdateMode(source);
        }

        @Override
        public UpdateMode[] newArray(int size) {
            return new UpdateMode[size];
        }
    };
}
