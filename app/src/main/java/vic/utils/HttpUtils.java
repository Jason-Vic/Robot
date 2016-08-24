package vic.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import vic.bean.ChatMessage;
import vic.bean.Result;

/**
 * Created by Vic on 2016/8/3.
 */
public class HttpUtils {
    private static final String url = "http://www.tuling123.com/openapi/api";
    private static final String apiKey = "26376d2ebefc4be08e3784d8d8875d18";

    public static ChatMessage sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage();
        String jsonRes = doGet(message);
        Gson gson = new Gson();
        Result result = null;
        result = gson.fromJson(jsonRes, Result.class);
        chatMessage.setMsg(result.getText());
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    public static String doGet(String msg) {
        String result = null;

        String url = setParams(msg);

        InputStream inputStream = null;
        ByteArrayOutputStream arrayOutputStream = null;

        URL urlStr = null;
        try {
            urlStr = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlStr.openConnection();
            urlConnection.setReadTimeout(5 * 1000);
            urlConnection.setConnectTimeout(5 * 1000);
            urlConnection.setRequestMethod("GET");
            inputStream = urlConnection.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            arrayOutputStream = new ByteArrayOutputStream();

            while ((len = inputStream.read(buf)) != -1) {
                arrayOutputStream.write(buf, 0, len);
            }
            arrayOutputStream.flush();
            result = new String(arrayOutputStream.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (arrayOutputStream != null) {
                try {
                    arrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }

    private static String setParams(String msg) {
        String Url = null;
        try {
            Url = url + "?key=" + apiKey + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Url;
    }
}
