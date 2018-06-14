package com.hazz.baselibs.net.converter;

import com.hazz.baselibs.net.BaseHttpResult;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * 这里解决因服务器 API 接口缺少规范化，需实现 Gson 的 JsonDeserializer 接口来完成自定义解析
 * 这里有几种情况
 * 1、例如当返回结果 {"code":0,"info":"\u5bc6\u7801\u9519\u8bef","result":-5}
 * result应为JsonObject或JsonArray，但返回的是-5，该类型为基本数据类型，会导致Gson对泛型的解析错误并抛出异常，导致 Retrofit2 返回到 onFailure 方法
 * 2、例如当返回结果 {"code":1,"info":"\u767b\u5f55\u6210\u529f","result":{"userid":"00001"}}
 * 此时为规范数据，直接返回原数据
 */
public class GsonResponseDeserializer implements JsonDeserializer<BaseHttpResult> {

    public GsonResponseDeserializer(){

    }

    @Override
    public BaseHttpResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new Gson().fromJson(json, typeOfT);
    }
}