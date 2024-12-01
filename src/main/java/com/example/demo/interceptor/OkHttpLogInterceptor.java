package com.example.demo.interceptor;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okio.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OkHttpLogInterceptor implements Interceptor {
    private static Logger LOGGER = LoggerFactory.getLogger(OkHttpLogInterceptor.class);
    public static String TAG = "OkHttpLogInterceptor";
    
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LOGGER.info(TAG + "\n");
        LOGGER.info(TAG + "----------Start----------------");
        LOGGER.info(TAG +  "| "+request.toString());
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                LOGGER.info(TAG + "| RequestParams:{"+sb.toString()+"}");
            } else if (request.body() instanceof MultipartBody) {
                // 上传文件类型的请求不打印body
            } else {
                LOGGER.info(TAG + "| RequestBody:"+bodyToString(request));
            }
        }
        LOGGER.info(TAG + "| Response:" + content);
        LOGGER.info(TAG + "----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
    private static String bodyToString(final Request request){
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "error";
        }
    }
    
}
