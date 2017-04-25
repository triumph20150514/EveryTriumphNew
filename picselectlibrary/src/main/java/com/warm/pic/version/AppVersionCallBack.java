package com.warm.pic.version;

/**
 * author: Trimph
 * data: 2017/4/19.
 * description:
 */

public interface AppVersionCallBack<T> {

    void success(T t);

    void failed(String message);

}
