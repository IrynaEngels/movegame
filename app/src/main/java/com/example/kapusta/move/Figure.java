package com.example.kapusta.move;

import android.graphics.PointF;
import android.util.Log;
import android.view.View;

/**
 * Created by user08 on 09.08.17.
 */

public class Figure {
    public enum DIRECTION {UP, DOWN, LEFT, RIGHT}
    DIRECTION direction = DIRECTION.RIGHT;

    private PointF coordinates = new PointF();
    private ParentInterface parent;
    private View mView;

    public View getmView() {
        return mView;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public void setmView(View mView) {
        this.mView = mView;
        coordinates = new PointF(mView.getX(), mView.getY());
    }

    public Figure(ParentInterface parent) {
        this.parent = parent;
    }

    public void move(){
        PointF coordinates = new PointF(this.coordinates.x, this.coordinates.y);

        switch (direction) {
            case UP:
                coordinates.set(coordinates.x - speed(), coordinates.y - speed());
                break;
            case DOWN:
                coordinates.set(coordinates.x - speed(), coordinates.y + speed());
                break;
            case LEFT:
                coordinates.set(coordinates.x + speed(), coordinates.y - speed());
                break;
            case RIGHT:
                coordinates.set(coordinates.x + speed(), coordinates.y + speed());
                break;
        }

        Log.d("TAG", "isFrame: " + )
        if (!isFrame(coordinates)) {
            switch (direction) {
                case UP:
                    direction = DIRECTION.DOWN;
                    break;
                case DOWN:
                    direction = DIRECTION.UP;
                    break;
                case LEFT:
                    direction = DIRECTION.RIGHT;
                    break;
                case RIGHT:
                    direction = DIRECTION.LEFT;
                    break;
            }
        } else {
            this.coordinates = coordinates;
            getmView().setX(coordinates.x);
            getmView().setY(coordinates.y);
        }

    }

    public PointF getCoordinates() {
        return coordinates;
    }

    private int speed(){
        return 5;
    }

    public boolean isFrame(PointF nextPoint){
        return nextPoint.x>=0 && nextPoint.y>=0 && nextPoint.x<=parent.size().x && nextPoint.y<=parent.size().y;
    }
}
