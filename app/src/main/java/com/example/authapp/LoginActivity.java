package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText psw;
    private Button btnConnect;
    private Button btnCreate;
    private FirebaseAuth auth;
    private final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createView();
        btnCreate.setOnClickListener((evt)-> {
            if(!login.getText().toString().equals("") && !psw.getText().toString().equals("")){
                createUser(login.getText().toString(),psw.getText().toString());
            } else {
                Toast.makeText(LoginActivity.this,"Login and password is requierd",Toast.LENGTH_LONG).show();
            }
        });
        btnConnect.setOnClickListener((evt)->{
            connect(login.getText().toString(),psw.getText().toString());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    close();
                }
            }, 600);
        });
    }

    private void createView(){
        login=findViewById(R.id.login);
        psw=findViewById(R.id.password);
        btnConnect=findViewById(R.id.btnConnectPhone);
        btnCreate=findViewById(R.id.btnCreate);
        auth=FirebaseAuth.getInstance();

    }

    private void createUser(String email, String pass){

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"User Email: "+email+" Is Created",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,"User Email: "+email+" Not Created",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void connect(String email, String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("login",email);
                startActivity(intent);
            }
        });

    }

    private void close(){
        this.finish();
    }
}