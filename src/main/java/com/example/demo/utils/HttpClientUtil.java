package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.interceptor.OkHttpLogInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpClientUtil {
    
    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new OkHttpLogInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(50, 10, TimeUnit.MINUTES))
            .build();
    
    public static String doGet(String urlWithParams, Map<String, String> headers) {
        String res = null;
        Request.Builder builder = new Request.Builder().url(urlWithParams);
        Response response = null;
        try {
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> en : headers.entrySet()) {
                    builder.header(en.getKey(), en.getValue());
                }
            }
            response = client.newCall(builder.build()).execute();
            res = response.body().string();
            LOGGER.error("response code={} gsid={}", response.code(), response.header("gsid"));
            HttpServletResponse currResponse = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            currResponse.addHeader("gsid", response.header("gsid"));
        } catch (IOException e) {
            LOGGER.error("HttpClientUtil get urlWithParams error", e);
        }
        return res;
    }
    
    public static String get(String urlWithParams) {
        return doGet(urlWithParams, null);
    }
    public static <T> ResponseEntity<T> getResponseEntity(String urlWithParams, Map<String, String> headers, Class<T> responseClazz) {
        String res = null;
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Request.Builder builder = new Request.Builder().url(urlWithParams);
        Response response = null;
        try {
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> en : headers.entrySet()) {
                    builder.header(en.getKey(), en.getValue());
                }
            }
            response = client.newCall(builder.build()).execute();
            res = response.body().string();
            responseEntity = new ResponseEntity(JSONObject.parseObject(res).toJavaObject(responseClazz), HttpStatus.valueOf(response.code()));
            HttpServletResponse currResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            currResponse.addHeader("gsid", response.header("gsid"));
        } catch (IOException e) {
            LOGGER.error("HttpClientUtil get urlWithParams error", e);
        }
        return responseEntity;
    }
    public static <T> T getEntity(String urlWithParams, Class<T> responseClazz) {
        return getEntity(urlWithParams, null, responseClazz);
    }
    
    public static <T> T getEntity(String urlWithParams, Map<String, String> headers, Class<T> responseClazz) {
        String res = doGet(urlWithParams, headers);
        if (StringUtils.isBlank(res)) {
            return null;
        }
    
        return JSONObject.parseObject(res).toJavaObject(responseClazz);
    }
    
    public static String postWithParam(String url, Map<String, Object> params) {
        String res = null;
        Response response = null;
        try {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    bodyBuilder.add(entry.getKey(), entry.getValue().toString());
                }
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(bodyBuilder.build())
                    .build();
            response = client.newCall(request).execute();
            res = response.body().string();
            LOGGER.error("postWithParam response code={} gsid={}", response.code(), response.header("gsid"));
            HttpServletResponse currResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            currResponse.addHeader("gsid", response.header("gsid"));
        } catch (IOException e) {
            LOGGER.error("HttpClientUtil postWithParam error", e);
        }
        return res;
    }
    
    public static <T> ResponseEntity<T> postWithParam(String url, Map<String, Object> params, Map<String, String> headers, Class<T> responseClazz) {
        String res = null;
        Response response = null;
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    bodyBuilder.add(entry.getKey(), entry.getValue().toString());
                }
            }
           
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .post(bodyBuilder.build());
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> en : headers.entrySet()) {
                    requestBuilder.header(en.getKey(), en.getValue());
                }
            }
            Request request = requestBuilder.build();
            response = client.newCall(request).execute();
            res = response.body().string();
            responseEntity = new ResponseEntity(JSONObject.parseObject(res).toJavaObject(responseClazz), HttpStatus.valueOf(response.code()));
            HttpServletResponse currResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            currResponse.addHeader("gsid", response.header("gsid"));
        } catch (IOException e) {
            LOGGER.error("HttpClientUtil postWithParam error", e);
        }
        return responseEntity;
    }
    
    public static <T> T postWithParam(String url, Map<String, Object> params, Class<T> responseClazz) {
        String res = postWithParam(url, params);
        if (StringUtils.isBlank(res)) {
            return null;
        }
        return JSONObject.parseObject(res).toJavaObject(responseClazz);
    }
    
    public static String postWithJson(String url, JSONObject jsonParams) {
        String res = null;
        Response response = null;
        try {
            RequestBody body = RequestBody.create(JSON, jsonParams.toJSONString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
            res = response.body().string();
            HttpServletResponse currResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            currResponse.addHeader("gsid", response.header("gsid"));
        } catch (IOException e) {
            LOGGER.error("HttpClientUtil postWithJson error", e);
        }
        return res;
    }
    
    public static <T> ResponseEntity<T> postWithJson(String url, JSONObject params, Map<String, String> headers, Class<T> responseClazz) {
        String res = null;
        Response response = null;
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            RequestBody body = RequestBody.create(JSON, params.toJSONString());
            
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .post(body);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> en : headers.entrySet()) {
                    requestBuilder.header(en.getKey(), en.getValue());
                }
            }
            Request request = requestBuilder.build();
            response = client.newCall(request).execute();
            res = response.body().string();
            responseEntity = new ResponseEntity(JSONObject.parseObject(res).toJavaObject(responseClazz), HttpStatus.valueOf(response.code()));
            HttpServletResponse currResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            currResponse.addHeader("gsid", response.header("gsid"));
        } catch (IOException e) {
            LOGGER.error("HttpClientUtil postWithParam error", e);
        }
        return responseEntity;
    }
    
    public static <T> T postWithJson(String url, JSONObject jsonParams, Class<T> responseClazz) {
        String res = postWithJson(url, jsonParams);
        if (StringUtils.isBlank(res)) {
            return null;
        }
        return JSONObject.parseObject(res).toJavaObject(responseClazz);
    }
}
