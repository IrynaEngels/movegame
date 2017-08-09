package com.example.kapusta.move;

import android.graphics.PointF;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class RunActivity extends AppCompatActivity implements ParentInterface {

    ArrayList<Figure> figures = new ArrayList<>();
    FrameLayout frameLayout;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        handler = new Handler();
        frameLayout = (FrameLayout) findViewById(R.id.container);

        Figure figure = new Figure(this);
        figure.setmView(findViewById(R.id.imgFirst));
        figure.direction = Figure.DIRECTION.RIGHT;
        figures.add(figure);

        figure = new Figure(this);
        figure.setmView(findViewById(R.id.imgSecond));
        figure.setDirection(Figure.DIRECTION.LEFT);
        figures.add(figure);

    }

    @Override
    public PointF size() {
        return new PointF(frameLayout.getWidth(), frameLayout.getHeight());
    }

    @Override
    public List<Figure> children() {
        return figures;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 300);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < figures.size(); i++) {
                Figure figure = figures.get(i);
                figure.move();
            }

            handler.postDelayed(runnable, 300);
        }
    };
}
