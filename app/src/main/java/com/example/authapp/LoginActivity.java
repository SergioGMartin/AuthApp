package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText psw;
    private Button btnConnect;
    private Button btnCreate;
    private FirebaseAuth auth;
    private final Handler handler = new Handler();
    private SignInButton btnGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN=1;
    private Button btnLogPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createView();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("154365267953-0qsfptnf6mq7hnevpdvo5adg62l7nr4i.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
        btnGoogle.setOnClickListener((evt)->{
            signIn();
        });
        btnLogPhone.setOnClickListener((evt)->{
            startActivity(new Intent(LoginActivity.this,LoginPhoneActivity.class));
        });
    }
    //autentificacion google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG:InfoAccount", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("ExceptionSignInGoogle", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignSuccess", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                            String nameUser=user.getDisplayName();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("login",nameUser);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignFaliure", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user){

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        updateUI(user);
    }
    //fin de autentificacion google

    private void createView(){
        login=findViewById(R.id.login);
        psw=findViewById(R.id.password);
        btnConnect=findViewById(R.id.btnConnectPhone);
        btnCreate=findViewById(R.id.btnCreate);
        auth=FirebaseAuth.getInstance();
        btnGoogle=findViewById(R.id.btnGoogle);
        btnLogPhone=findViewById(R.id.btnLogPhone);

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