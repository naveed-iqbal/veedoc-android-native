package com.veemed.veedoc.utils;

import android.content.Context;
import android.util.Base64;

import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.webservices.UserAPIResponse;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String bearerToken = "";
    public static int refreshDelay = 3000;

    // keep deferred conversations by sessionId int
    public static HashMap<Integer, Conversation> deferredConversations = new HashMap<>();

    public static UserAPIResponse user;
    public static final DateFormat SERVER_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.S");
    public static final DateFormat APP_TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static void initBearerToken(Context context) {
        bearerToken = "Bearer "
                + AppPreferencesManager.getInstance(context).findStringPrferenceValue(AppPreferencesManager.STRING_ENUM_KEY.ACCESS_TOKEN, "");
    }

    public static String BASE_URL = "https://dev-aws.veemed.com";

    public static boolean isPossibleEmail(String email){
        // regex credit to https://howtodoinjava.com/regex/java-regex-validate-email-address/
        String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);
        // return if the email entered is valid according to regex
        return m.matches();
    }

    public static String encodeText(String text) {
        try {
            return new String(Base64.encode(text.getBytes(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeText(String text){
        try {
            return new String(Base64.decode(text, Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deepCopyStatesAndNumbersMap(LinkedHashMap<String, String> originalMap, LinkedHashMap<String, String> copyToMap){
        for (Map.Entry<String, String> entry : originalMap.entrySet()){
            copyToMap.put(entry.getKey(), entry.getValue());
        }
    }

}
