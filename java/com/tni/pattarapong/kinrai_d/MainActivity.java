package com.tni.pattarapong.kinrai_d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,
                Toast.LENGTH_SHORT).show();
    }

    public void launchSearchPage(View view) {
        displayToast(getString(R.string.search_order_message));
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void launchinsertPage(View view) {
        displayToast(getString(R.string.insert_order_message));
        Intent intent = new Intent(MainActivity.this, InsertActivity.class);
        /*
        String data = jsondata.setRoot("krapow").addString("meat", "pork").get();
        connect.addData("test", data);
        */
        startActivity(intent);
    }

    public void sentKeyValue(View view) {
        
    }
}