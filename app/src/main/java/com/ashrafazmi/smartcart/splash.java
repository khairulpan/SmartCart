package com.ashrafazmi.smartcart;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ashrafazmi on 2017Jan.
 */
public class splash extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        FloatingActionButton buttonpromo = (FloatingActionButton) findViewById(R.id. buttonpromo);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(7000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(splash.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

        //button to promo
        buttonpromo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(splash.this, "Direct To Setting", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_MAIN);
                PackageManager managerclock = getPackageManager();
                i = managerclock.getLaunchIntentForPackage("com.android.settings");
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
