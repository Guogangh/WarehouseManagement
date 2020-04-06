package com.jetusesoft.warehousemanagement.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static boolean checkConnect(String host, String port) {

        return host.equals("asd") && port.equals("123");
    }

    public static boolean checkLogin(String uname, String upwd, okhttp3.Callback callback) {
        String address = "https://www.baidu.com";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

        return uname.equals("asd") && upwd.equals("123");
    }


}
