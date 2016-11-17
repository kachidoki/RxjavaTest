package com.kachidoki.rxjavatest.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mayiwei on 16/11/17.
 */
public class ApiResult {
    public int showapi_res_code;
    public String showapi_res_error;
    public @SerializedName("showapi_res_body") ApiBody showapi_res_body;
}
