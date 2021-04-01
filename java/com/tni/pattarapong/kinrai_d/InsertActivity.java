package com.tni.pattarapong.kinrai_d;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InsertActivity extends AppCompatActivity {
    private final serverconnect connect = new serverconnect();
    private final jsonManager jsondata = new jsonManager();

    private String meatType = "not set";
    private String foodType = "not set";
    private String spicyLevel = "not set";
    private String foodName = "not set";
    private String phoneNum = "not set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
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


    public void insert_onclick(View view) {
        EditText foodTextInput = findViewById(R.id.foodname);
        EditText phoneTextInput = findViewById(R.id.phonenum);
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;

        if(!foodTextInput.getText().toString().toLowerCase().equals("")){
            foodName = foodTextInput.getText().toString().toLowerCase();
        }
        if(!phoneTextInput.getText().toString().toLowerCase().equals("")){
            phoneNum = phoneTextInput.getText().toString().toLowerCase();
        }

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        date = dateFormat.format(calendar.getTime());
        System.out.print("Date: ");
        System.out.println(date);

        jsondata.setRoot(date)
                .addString("food name", foodName)
                .addString("phone number", phoneNum)
                .addString("meat", meatType)
                .addString("food type", foodType)
                .addString("spicy level", spicyLevel);

        connect.addData("test", jsondata.getjson());
        displayToast("Inserted");

        finish();
    }
}