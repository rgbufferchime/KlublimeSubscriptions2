package com.bufferchime.klublimesubscriptions;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    ImageButton signin;
    Button signup;
    TextView forgotpass;
    private FirebaseAuth auth;
    RelativeLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Set Portrait
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        checkBox = (CheckBox)findViewById(R.id.checkbox);
        signin =(ImageButton)findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        forgotpass = (TextView) findViewById(R.id.forgot);

        FirebaseApp.initializeApp(this);
        signup.setOnClickListener(this);
        forgotpass.setOnClickListener(this);
        signin.setOnClickListener(this);

        //Init Firebase Auth
        auth = FirebaseAuth.getInstance();

        //Check already session , if ok-> DashBoard
        if(auth.getCurrentUser() != null)
            startActivity(new Intent(LoginActivity.this,GamePlayActivity.class));


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.forgot)
        {
            startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            finish();
        }
        else if(view.getId() == R.id.signup)
        {
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            finish();
        }
        else if(view.getId() == R.id.signin)
        {
            loginUser(email.getText().toString(),password.getText().toString());
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            if(password.length() < 6)
                            {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Password length must be over 6",
                                        Toast.LENGTH_SHORT);

                                toast.show();
                            }
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this,GamePlayActivity.class));
                        }
                    }
                });
    }



}