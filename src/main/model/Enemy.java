package model;

import javafx.geometry.Rectangle2D;

public class Enemy implements GraphicModel {

    public Enemy(){

    }


    @Override
    public void update(double time) {

    }

    @Override
    public Rectangle2D getBoundary() {
        return null;
    }

    @Override
    public boolean intersects(GraphicModel s) {
        return false;
    }
}
