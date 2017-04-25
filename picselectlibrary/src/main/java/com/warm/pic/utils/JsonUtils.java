package com.warm.pic.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * author: Trimph
 * data: 2017/4/18.
 * description:
 */

public class JsonUtils {

    /**
     * @param json
     * @return
     */
    public static <T> T JsonToClass(String json, Type t) {
        return new Gson().fromJson(json, t);
    }
}
