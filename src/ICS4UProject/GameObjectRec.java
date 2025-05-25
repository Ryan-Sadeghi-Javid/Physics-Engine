package ICS4UProject;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
/**
 * This class represent s game object in the game with javafx rectangle added
 */
public class GameObjectRec extends GameObject implements KineticsWithSize{

    private Rectangle rec;
    private double sizeX, sizeY;
    private boolean isUpdate = true;

    /**
     * To construct a game object
     * @param x the x coordinate of the object
     * @param y the y coordinate of the object
     * @param sizeX the width
     * @param sizeY the height
     */
    public GameObjectRec(double x, double y, double sizeX, double sizeY) {
        super(x,y);
        rec = new Rectangle(x,y,sizeX,sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * To construct a game object
     * @param v the position vector
     * @param sizeX the width
     * @param sizeY the height
     */
    public GameObjectRec(Vector v, double sizeX, double sizeY){
        this(v.getX(),v.getY(),sizeX,sizeY);
    }

    @Override
    public boolean isCollide(Object o){
        return rec.getBoundsInParent().intersects(((Node)o).getBoundsInParent());
    }

    @Override
    public void update(long elapsedTime) {
        if(isUpdate) {
            updateRelativePosition();
            rec.setX(getRelativePosition().getX());
            rec.setY(getRelativePosition().getY());
            updatePosition(elapsedTime);
        }
    }

    /**
     * get the Javafx rectangle object
     * @return Javafx rectangle
     */
    public Rectangle getRectangle(){
        return rec;
    }

    @Override
    public double getSizeX() {
        return sizeX;
    }

    @Override
    public double getSizeY(){
        return sizeY;
    }

    public void setSizeX(double x){
        rec.setWidth(x);
        sizeX = x;
    }

    public void setSizeY(double y){
        rec.setHeight(y);
        sizeY = y;
    }

    @Override
    public void close(){
        super.close();
        rec = null;
        isUpdate=false;
    }

}