package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public interface GraphicModel {

    public void update(double time);

    //public void update(double time, List<Floor> floorList);

    //public void render(GraphicsContext gc);

    public Rectangle2D getBoundary();

    public boolean intersects(GraphicModel g);
}
