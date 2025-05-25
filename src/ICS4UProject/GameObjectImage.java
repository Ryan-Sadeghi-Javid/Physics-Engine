package ICS4UProject;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * This class represent s game object in the game with image added
 */
public class GameObjectImage extends GameObject implements KineticsWithSize{

    private ImageView image;
    private double sizeX, sizeY;
    private boolean isUpdate = true;

    /**
     * To construct a game object
     * @param x the x coordinate of the object
     * @param y the y coordinate of the object
     * @param sizeX the width
     * @param sizeY the height
     * @param image the image of the object
     */
    public GameObjectImage(double x, double y, double sizeX, double sizeY, Image image) {
        super(x,y);
        this.image = new ImageView(image);
        this.image.setX(x);
        this.image.setY(y);
        this.image.setFitWidth(sizeX);
        this.image.setFitHeight(sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * To construct a game object
     * @param v the position vector
     * @param sizeX the width
     * @param sizeY the height
     * @param image the image of the object
     */
    public GameObjectImage(Vector v, double sizeX, double sizeY, Image image){
        this(v.getX(),v.getY(),sizeX,sizeY, image);
    }

    @Override
    public boolean isCollide(Object o){
        return image.getBoundsInParent().intersects(((Node)o).getBoundsInParent());
    }

    @Override
    public void update(long elapsedTime) {
        if(isUpdate) {
            updateRelativePosition();
            image.setX(getRelativePosition().getX());
            image.setY(getRelativePosition().getY());
            updatePosition(elapsedTime);
        }
    }

    /**
     * get the image view object
     * @return the image view object
     */

    public ImageView getImage(){
        return image;
    }


    @Override
    public double getSizeX() {
        return sizeX;
    }

    @Override
    public double getSizeY() {
        return sizeY;
    }

    @Override
    public void setSizeX(double x) {
        sizeX = x;
        image.setFitWidth(x);
    }

    @Override
    public void setSizeY(double y) {
        sizeY = y;
        image.setFitHeight(y);
    }

    @Override
    public void close(){
        super.close();
        image.setImage(null);
        isUpdate = false;
    }
}