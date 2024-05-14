package com.zuel.onlineCode.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AiUtil {
    public static String getAccessToken() throws IOException {
        String apiKey = "wLiyU3zmhq8xXeTUfy9RiMPl";
        String secretKey = "VqVblK1TyTHAfQvf2FwhqpcCE02faunA\n";
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), ""))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseBody);
            Object accessToken = jsonObject.get("access_token");
            return accessToken.toString();
        }
    }

    public static String ask(String code) throws IOException {
        String accessToken = getAccessToken();
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/llama_3_70b?access_token=" + accessToken;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String content = "你是我的代码评测老师，你会检查我提交的代码中的错误并指正，请你以老师的口吻,用最精炼的语言指出我代码中的错误，但是你不会给我修改的后的代码，以下是我提交的代码：\n" + code+"\n请您用中文回答！！";
        HashMap<String, String> map = new HashMap<>();
        map.put("role", "user");
        map.put("content", content);
        ArrayList<Map> maps = new ArrayList<>();
        maps.add(map);
        HashMap<String, List> stringListHashMap = new HashMap<>();
        stringListHashMap.put("messages", maps);
        String json = JSON.toJSONString(stringListHashMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String string = response.body().string();
            JSONObject jsonObject = JSON.parseObject(string);
            return jsonObject.get("result").toString();
        }
    }
}
