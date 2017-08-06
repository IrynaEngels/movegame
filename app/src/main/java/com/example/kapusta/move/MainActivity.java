package com.example.kapusta.move;


import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView circle;
    ImageView rectangle;
    ConstraintLayout mConstraintLayout;
    final Handler handler = new Handler();
    HashMap<Integer, RunRunnable> hashObjectMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConstraintLayout = (ConstraintLayout) findViewById(R.id.container);

        circle = (ImageView) findViewById(R.id.circle);
        rectangle = (ImageView) findViewById(R.id.rectan);

        circle.setOnClickListener(this);
        rectangle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        moving(v);
    }

    public void moving(final View v){
        if (hashObjectMap.get(v.getId())==null) {
            RunRunnable mRun = new RunRunnable(v);
            handler.post(mRun);
            hashObjectMap.put(v.getId(), mRun);
        } else {
            RunRunnable mRun = hashObjectMap.get(v.getId());
            handler.removeCallbacks(mRun);
            hashObjectMap.remove(v.getId());
            mRun = null;
        }
    }

    class RunRunnable implements Runnable {
        View v;
        int width = mConstraintLayout.getWidth();
        int height = mConstraintLayout.getHeight();
        float viewX = 0;
        float viewY = 0;
        int side = 1;

        int x1 = (int)(Math.random()*10);
        int y1 = (int)(Math.random()*10);
        int x2 = (int)(Math.random()*10);
        int y2 = (int)(Math.random()*10);
        int x3 = (int)(Math.random()*10);
        int y3 = (int)(Math.random()*10);
        int x4 = (int)(Math.random()*10);
        int y4 = (int)(Math.random()*10);

        ScaleAnimation scaleAnimation;

        public View getmView() {
            return v;
        }

        public void setmView(View mView) {
            this.v = mView;
        }

        private RunRunnable() {
        }

        public RunRunnable(View mView) {
            this.v = mView;
            viewX = v.getX();
            viewY = v.getY();
        }

        public boolean collide(float initX, float initY, float initWidth, float initHeight){
            for (Map.Entry<Integer, RunRunnable> entry : hashObjectMap.entrySet()){
                float comparedX = entry.getValue().getmView().getX();
                float comparedY = entry.getValue().getmView().getY();
                float width = entry.getValue().getmView().getWidth();
                float height = entry.getValue().getmView().getHeight();

                if(comparedX+width>=initX&&comparedX+width<=initX+initWidth&&
                        comparedY+height>=initY&& comparedY+height<=initY+initHeight||

                        comparedX<=initX+initWidth&&comparedX>=initX&&
                                comparedY<=initY+initHeight&&comparedY>=initY||
                        comparedX+width>=initX&&comparedX+width<=initX+initWidth&&
                                comparedY<=initY+initHeight&&comparedY>=initY||
                        comparedX<=initX+initWidth&&comparedX>=initX&&
                                comparedY+height>=initY&& comparedY+height<=initY+initHeight){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void run() {
            for (Map.Entry<Integer, RunRunnable> entry : hashObjectMap.entrySet()) {
                Integer key = entry.getKey();
                if (key.equals(v.getId())) continue;
                RunRunnable value = entry.getValue();
            }
            if(collide(v.getX(), v.getY(), v.getWidth(), v.getHeight())){
                scaleAnimation = new ScaleAnimation(1,2,1,2);
                scaleAnimation.setDuration(10000);
                v.setAnimation(scaleAnimation);
            }

            if (v.getX() >= width - v.getWidth()) {
                side = 2;
            }
            if (v.getY() >= height - v.getHeight()) {
                side = 3;
            }
            if (v.getX() <= 0) {
                side = 4;
            }
            if (v.getY() <= 0) {
                side = 1;
            }

            if (side == 1) {
                v.setX(viewX+=x1);
                v.setY(viewY+=y1);
            }
            else if (side == 2){
                v.setX(viewX-=x2);
                v.setY(viewY+=y2);
            }
            else if (side == 3){
                v.setX(viewX-=x3);
                v.setY(viewY-=y3);
            }
            else if (side == 4){
                v.setX(viewX+=x4);
                v.setY(viewY-=y4);
            }
            handler.postDelayed(this, 10);
        }
    }
}

