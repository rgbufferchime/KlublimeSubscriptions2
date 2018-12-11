package com.bufferchime.klublimesubscriptions;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class Launch extends AppCompatActivity implements View.OnClickListener {

  //  LottieAnimationView lottieAnimationView;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Set Portrait
       // lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        button = (Button) findViewById(R.id.launch);
       // lottieAnimationView.playAnimation();
        button.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.launch)
        {
            startActivity(new Intent(Launch.this, WelcomeActivity.class));
        }
    }


}
