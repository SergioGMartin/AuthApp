package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import java.security.MessageDigest;

public class HachageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hachage);
        Button btnGetKey=findViewById(R.id.btnGetCle);

        btnGetKey.setOnClickListener((evt)->{
            PackageInfo infos;
            try{
                infos=getPackageManager().getPackageInfo("com.example.authapp", PackageManager.GET_SIGNATURES);
                for (Signature signature:infos.signatures) {
                    MessageDigest messageDigest;
                    messageDigest=MessageDigest.getInstance("SHA");
                    messageDigest.update(signature.toByteArray());
                    String key=new String(Base64.encode(messageDigest.digest(),0));
                    Log.e("Key_HACH",key);
                }
            }catch (Exception e){
                Log.e("Exception",e.getMessage());
            }
        });
    }
}