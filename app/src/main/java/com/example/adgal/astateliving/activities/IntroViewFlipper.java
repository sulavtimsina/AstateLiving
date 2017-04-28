package com.example.adgal.astateliving.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.example.adgal.astateliving.R;

public class IntroViewFlipper extends AppCompatActivity {

    //Reference: http://www.truiton.com/2013/07/android-viewflipper-with-swipe-by-motionevent-class/
    ViewFlipper viewFlipper;
    Button btnEnterApp;
    private float initialX, finalX;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_view_flipper);
        initComponents();
        showIntroIfFirstRun();
    }

    private void showIntroIfFirstRun() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }
    }

    private void initComponents(){
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        btnEnterApp = (Button) findViewById(R.id.btnEnterApp);

        btnEnterApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent loginIntent = new Intent(IntroViewFlipper.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                Log.d("down",initialX+"");
                break;
            case MotionEvent.ACTION_UP:
                 finalX = touchevent.getX();
                Log.d("up",finalX+"");
                float deltaX = finalX - initialX;
                if (deltaX > 0) {
                    viewFlipper.setInAnimation(this, R.anim.in_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_right);
                    viewFlipper.showPrevious();

                } else {
//                    if (viewFlipper.getDisplayedChild() == 0)
//                        break;
                    viewFlipper.setInAnimation(this, R.anim.in_right);
                    viewFlipper.setOutAnimation(this, R.anim.out_left);

                    viewFlipper.showNext();
                }
                break;
        }
        return super.onTouchEvent(touchevent);
    }

}
