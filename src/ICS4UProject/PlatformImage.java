package ICS4UProject;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

/**
 * This represents a platform with image in the game
 */

public class PlatformImage extends Platform{

    /**
     * To construct a platform
     * @param x the x coordinate of the platform
     * @param y the y coordinate of the platform
     * @param sizeX the width
     * @param sizeY the height
     * @param image the image of the platform
     */
    public PlatformImage(double x, double y, double sizeX, double sizeY, Image image) {
        super(x, y, sizeX, sizeY);
        setElasticity(new double[]{1,1,1,1});
        getRectangle().setFill(new ImagePattern(image));
    }

}
