package com.veemed.veedoc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;

import com.veemed.veedoc.R;

public class SplashScreenActivity extends BaseActivity {
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    TextView textView, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        textView = findViewById(R.id.tvPoint);
        textView2 = findViewById(R.id.textView2);
        findViewById(R.id.flSplash).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                mScaleDetector.onTouchEvent(motionEvent);
               if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    textView.setText(motionEvent.getX()+", "+motionEvent.getY());
                    if(motionEvent.getPointerCount() == 2)
                        textView.append("\n"+motionEvent.getX(1)+", "+motionEvent.getY(1));
                }
                return true;
            }
        });
        Thread splash = new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(1000);
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            textView2.setText(mScaleFactor+", ");
            // invalidate();
            return true;
        }
    }
}
