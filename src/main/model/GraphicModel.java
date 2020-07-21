package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface GraphicModel {

    public void update(double time);

    //public void render(GraphicsContext gc);

    public Rectangle2D getBoundary();

    public boolean intersects(GraphicModel s);
}
