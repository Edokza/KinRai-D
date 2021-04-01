package com.tni.pattarapong.kinrai_d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

public class show_menu extends AppCompatActivity {

    private final LinkedList<JSONObject> matchmenu = new LinkedList<>();

    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        get_intent_data();

        for (int i = 0; i < get_menu_size() ; i++) {
            mWordList.addLast(get_food_name(i)+"\nTel: "+get_phone_number(i));
        }
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mWordList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void get_intent_data(){
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra(SearchActivity.ExtraMessage);
        try {
            JSONObject json = new JSONObject(jsonString);
            Iterator<String> iter = json.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                JSONObject content = json.getJSONObject(key);
                matchmenu.addLast(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String get_food_name(int index){

        if(index > matchmenu.size()-1 || index < 0){
            return "";
        }

        try {
            return matchmenu.get(index).getString("food name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String get_phone_number(int index){

        if(index > matchmenu.size()-1 || index < 0){
            return "";
        }

        try {
            return matchmenu.get(index).getString("phone number");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int get_menu_size(){
        return matchmenu.size();
    }
}