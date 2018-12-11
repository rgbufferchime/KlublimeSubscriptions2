package com.bufferchime.klublimesubscriptions;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText email,password,name;
    CheckBox checkBox;
    ImageButton signup;
    Button signin;
    RelativeLayout activity_sign_up;

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Set Portrait
        setContentView(R.layout.activity_sign_up);

        //View
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        name = (EditText)findViewById(R.id.name);
        checkBox = (CheckBox)findViewById(R.id.checkbox);
        signup =(ImageButton)findViewById(R.id.signup);
        signin = (Button) findViewById(R.id.signin);

        signup.setOnClickListener(this);
        signin.setOnClickListener(this);

        //Init Firebase
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }





    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signin){
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            finish();
        }

        else if(view.getId() == R.id.signup){
            showProgressDialog();
            if (!validateForm()) {
                return;
            }
            signUpUser(email.getText().toString(),password.getText().toString());
        }

    }


    private void signUpUser(String emailid, String passwordid) {
        auth.createUserWithEmailAndPassword(emailid,passwordid)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            hideProgressDialog();
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Error",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                        else{
                            hideProgressDialog();
                            writingtodatabase();
                            startActivity(new Intent(SignUpActivity.this,GamePlayActivity.class));
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Successfully registered",
                                    Toast.LENGTH_SHORT);

                            toast.show();


                        }
                    }
                });
    }


    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Required");
            result = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Required");
            result = false;
        } else {
            password.setError(null);
        }

        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError("Required");
            result = false;
        } else {
            name.setError(null);
        }

        return result;
    }


    // [START basic_write]
    private void writeNewUser(String name,String emailid,String userid) {
        User user = new User(name,emailid,userid);

        mDatabase.child("users").child(name).setValue(user);
    }
    // [END basic_write]


    //writingtodatabase
    private void writingtodatabase(){
        // Write new user
        writeNewUser(name.getText().toString(),email.getText().toString(),auth.getUid());

    }

    //writingtodatabase

}