package com.kachidoki.rxjavatest.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mayiwei on 16/11/17.
 */
public class ApiBody {
    public int ret_code;
    public @SerializedName("pagebean") PageBean pagebean;
}
