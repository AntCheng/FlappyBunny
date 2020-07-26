package model;

import java.awt.geom.Rectangle2D;

public interface GraphicModel {

    //update this graphical mode; as time changes
    public void update(double time);

    //public void update(double time, List<Floor> floorList);

    //public void render(GraphicsContext gc);

    //EFFECTS: return a rectangle shape of this object
    public Rectangle2D getBoundary();

    //EFFECT: to determind if this object intersect with other graphical object
    public boolean intersects(GraphicModel g);
}
