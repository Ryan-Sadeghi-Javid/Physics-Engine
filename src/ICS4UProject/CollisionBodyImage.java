package ICS4UProject;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * represents a body with image that can return collision data with other objects
 */
public class CollisionBodyImage extends BodyImage {
    private final double COLLIDER_WIDTH = 3;
    // The amount subtracted from the edges of the colliders
    private final double WALL_MARGIN = 3;
    private boolean isUpdate = true;
    //Array containing the 4 colliders around a platform(0 is top, 1 is bottom, 2 is left, 3 is right)
    private Rectangle[] colliders = new Rectangle[4];

    /**
     * Constructs a CollisionBodyImage object
     * @param x position in x-axis
     * @param y position in y-axis
     * @param sizeX the width of the object
     * @param sizeY the height of the object
     * @param image the image of the object
     */
    public CollisionBodyImage(double x, double y, double sizeX, double sizeY, Image image) {
        super(x, y, sizeX, sizeY, image);
        colliders[0] = new Rectangle(x+WALL_MARGIN, y-COLLIDER_WIDTH, sizeX-WALL_MARGIN*2, COLLIDER_WIDTH);
        colliders[1] = new Rectangle(x+WALL_MARGIN, y+sizeY, sizeX-WALL_MARGIN*2, COLLIDER_WIDTH);
        colliders[2] = new Rectangle(x-COLLIDER_WIDTH, y+WALL_MARGIN, COLLIDER_WIDTH, sizeY-WALL_MARGIN*2);
        colliders[3] = new Rectangle(x+sizeX, y+WALL_MARGIN, COLLIDER_WIDTH, sizeY-WALL_MARGIN*2);
    }

    /**
     * Creates a collision event, checks which collider a "KineticWithSize" object collides with,
     * sets that in a boolean[] array, checks how deep the object is in the collider(s) , and sets that
     * in the double[] array
     * @param o an game object to collide with
     * @return CollisionEvent object
     */
    public CollisionEvent collideWith(KineticsWithSize o) {
        CollisionEvent e = new CollisionEvent();
        if(isUpdate){
            e.setCollisionPosition(new boolean[]{o.isCollide(colliders[0]),
                    o.isCollide(colliders[1]),
                    o.isCollide(colliders[2]),
                    o.isCollide(colliders[3])});
            e.setDepth(new double[]{
                    o.isCollide(colliders[0]) ? ((o.getPosition().getY() + o.getSizeY()) - (getPosition().getY() - COLLIDER_WIDTH)) : 0,
                    o.isCollide(colliders[1]) ? (getPosition().getY() + this.getSizeY() + COLLIDER_WIDTH - o.getPosition().getY()) : 0,
                    o.isCollide(colliders[2]) ? (o.getPosition().getX() + o.getSizeX() - (getPosition().getX() - COLLIDER_WIDTH)) : 0,
                    o.isCollide(colliders[3]) ? (getPosition().getX() + getSizeX() + COLLIDER_WIDTH - o.getPosition().getX()) : 0});
        }
        return e;
    }

    /**
     * Updates the collider positions
     * @param elapsedTime the time between two update is called
     */
    @Override
    public void update(long elapsedTime){
        if(isUpdate) {
            updateRelativePosition();

            colliders[0].setX(getPosition().getX()+WALL_MARGIN-getCameraPosition().getX());
            colliders[0].setY((getPosition().getY()-COLLIDER_WIDTH)-getCameraPosition().getY());
            colliders[1].setX(getPosition().getX()+WALL_MARGIN-getCameraPosition().getX());
            colliders[1].setY((getPosition().getY()+getSizeY())-getCameraPosition().getY());
            colliders[2].setX(getPosition().getX()-COLLIDER_WIDTH-getCameraPosition().getX());
            colliders[2].setY(getPosition().getY()+WALL_MARGIN-getCameraPosition().getY());
            colliders[3].setX(getPosition().getX()+getSizeX()-getCameraPosition().getX());
            colliders[3].setY(getPosition().getY()+WALL_MARGIN-getCameraPosition().getY());
            getImage().setX(getRelativePosition().getX());
            getImage().setY(getRelativePosition().getY());
            updatePosition(elapsedTime);
        }
    }

    public boolean runIntoLeft(CollisionBodyImage i) {
        return this.collideWith(i).getCollisionPosition()[2];
    }

    public boolean runIntoRight(CollisionBodyImage i) {
        return this.collideWith(i).getCollisionPosition()[3];
    }

    /**
     * Closes the CollisionBodyImage object by setting all of its colliders to null and stopping it from updating
     */
    @Override
    public void close() {
        super.close();
        isUpdate = false;
        colliders = null;
    }

    @Override
    public void setSizeX(double x) {
        super.setSizeX(x);
        //also change the size of the collider
        colliders[0].setWidth(getSizeX()-WALL_MARGIN*2);
        colliders[0].setHeight(COLLIDER_WIDTH);
        colliders[1].setWidth(getSizeX()-WALL_MARGIN*2);
        colliders[1].setHeight(COLLIDER_WIDTH);
    }

    @Override
    public void setSizeY(double y) {
        super.setSizeY(y);
        //also change the size of the collider
        colliders[2].setWidth(COLLIDER_WIDTH);
        colliders[2].setHeight(getSizeY()-WALL_MARGIN*2);
        colliders[3].setWidth(COLLIDER_WIDTH);
        colliders[3].setHeight(getSizeY()-WALL_MARGIN*2);
    }

    public Rectangle[] getColliders() {
        return colliders;
    }
}