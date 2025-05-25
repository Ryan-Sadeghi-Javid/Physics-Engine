package ICS4UProject;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * This class represents a platform with repeating pattern
 */
public class PlatformImagePattern extends PlatformImage{

    private Image image;
    private double xSpacing, ySpacing;

    /**
     * To construct a platform
     *
     * @param x     the x coordinate of the platform
     * @param y     the y coordinate of the platform
     * @param sizeX the width
     * @param sizeY the height
     * @param xSpacing the x spacing for pattern
     * @param ySpacing the y spacing for pattern
     * @param image the image of the platform
     */
    public PlatformImagePattern(double x, double y, double sizeX, double sizeY, double xSpacing,double ySpacing, Image image) {
        super(x, y, sizeX, sizeY, image);
        this.image = image;
        this.xSpacing = xSpacing;
        this.ySpacing = ySpacing;
        getRectangle().setFill(new ImagePattern(image,0,0,xSpacing,ySpacing,false));
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        getRectangle().setFill(new ImagePattern(image, getRelativePosition().getX(), getRelativePosition().getY(),xSpacing,ySpacing,false));
    }
}
