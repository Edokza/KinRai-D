package com.tni.pattarapong.kinrai_d;

import org.json.JSONException;
import org.json.JSONObject;

public class jsonManager {
    private String content = "";
    private String root = "\"not set\":";
    private JSONObject json;

    public jsonManager setRoot(String name) {
        root = "\"" + name + "\":";
        return this;
    }

    public jsonManager addString(String name, String text) {
        if (content != "") {
            content += ", ";
        }
        content += "\"" + name + "\":" + "\"" + text + "\"";
        //System.out.println(content);
        return this;
    }

    public String getjson() {
        return "{" + root + "{" + content + "}" + "}";
    }

    public void parse(String jsonstring) throws JSONException {
        json = new JSONObject(jsonstring);
    }

    public JSONObject getJsonObject(){
        return json;
    }
}
