package com.tni.pattarapong.kinrai_d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

public class SearchActivity extends AppCompatActivity {

    private final serverconnect connect = new serverconnect();
    private final jsonManager jsondata = new jsonManager();
    private final LinkedList<String> matchmenu = new LinkedList<>();
    public static final String ExtraMessage = "app.kinrai-d.matchmenu";

    private String meatType = "not set";
    private String foodType = "not set";
    private String spicyLevel = "not set";
    private String foodName = "not set";
    private String phoneNum = "not set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,
                Toast.LENGTH_SHORT).show();
    }

    public void select_meat_type_onclick(View view) {
        String label = ((RadioButton)view).getText().toString().toLowerCase();

        meatType = label;

        displayToast("You had select " + label);
    }

    public void select_food_type_onclick(View view){
        String label = ((RadioButton)view).getText().toString().toLowerCase();

        foodType = label;

        displayToast("You had select " + label);
    }

    public void select_spicy_level_onclick(View view){
        String label = ((RadioButton)view).getText().toString().toLowerCase();

        spicyLevel = label;

        displayToast("You had select " + label);
    }

    public void search_onclick(View view) throws InterruptedException, JSONException {
        String data = connect.getData("test");
        JSONObject matchjson = new JSONObject();


        matchmenu.clear();
        jsondata.parse(data);

        Iterator<String> iter = jsondata.getJsonObject().keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject content = jsondata.getJsonObject().getJSONObject(key);

                if(is_match(content)){
                    matchjson.put(key, content);
                    matchmenu.addLast(key);
                }

            } catch (JSONException e) {
                // Something went wrong!
            }
        }

        launchshowPage(matchjson.toString());
    }

    private boolean is_match(JSONObject content) throws JSONException {

        if(foodType.equals(content.getString("food type"))
                && meatType.equals(content.getString("meat"))
                && spicyLevel.equals(content.getString("spicy level"))){

            return true;
        }
        return false;
    }

    private void launchshowPage(String matchmenu) {
        displayToast(getString(R.string.search_order_message));
        Intent intent = new Intent(SearchActivity.this, show_menu.class);
        intent.putExtra(ExtraMessage, matchmenu);
        startActivity(intent);
    }

}