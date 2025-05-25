package ICS4UProject;

import javafx.scene.image.Image;

public class MovingPlatformImage extends PlatformImage{

    boolean isUpdate = true;

    /**
     * To construct a moving platform
     *
     * @param x     the x coordinate of the platform
     * @param y     the y coordinate of the platform
     * @param sizeX the width
     * @param sizeY the height
     * @param image the image of the platform
     */
    public MovingPlatformImage(double x, double y, double sizeX, double sizeY, Image image) {
        super(x, y, sizeX, sizeY, image);
        setVelocity(new Vector(0, 150));
    }

    public void update(long elapsedTime) {
        if(isUpdate){
            super.update(elapsedTime);
            if (getPosition().getY() > 800) {
                setPosition(new Vector(getPosition().getX(), 0));
            }
        }
    }

    public void close() {
        super.close();
        isUpdate = false;
    }
}
