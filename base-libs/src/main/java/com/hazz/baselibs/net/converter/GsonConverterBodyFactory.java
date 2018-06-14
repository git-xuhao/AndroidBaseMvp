package com.hazz.baselibs.net.converter;

import com.hazz.baselibs.net.BaseHttpResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * GsonConverterBodyFactory
 * 自定义 Gson 转换器
 */
public class GsonConverterBodyFactory extends Converter.Factory {

    private final Gson gson;

    public static GsonConverterBodyFactory create() {
        GsonBuilder builder = new GsonBuilder().
                registerTypeAdapter(BaseHttpResult.class, new GsonResponseDeserializer());
        return create(builder);
    }

    public static GsonConverterBodyFactory create(GsonBuilder builder) {
        return new GsonConverterBodyFactory(builder.create());
    }

    public static GsonConverterBodyFactory create(Gson gson) {
        return new GsonConverterBodyFactory(gson);
    }

    private GsonConverterBodyFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("Gson == null");
        }

        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }
}