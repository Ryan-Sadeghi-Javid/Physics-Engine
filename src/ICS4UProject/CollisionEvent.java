package ICS4UProject;

/**
 * This class contains all the data after a collision
 */
public class CollisionEvent {
    private boolean[] collisionPosition; // Boolean array that detects which direction the collision in coming from
    private double[] depth; // Double array that indicates how far a KineticWithSize object is inside a platforms' collider(s)

    /**
     * Sets the values of the collision position boolean array
     * @param collisionPosition the collision boolean array as [top, bottom,left,right]
     */
    public void setCollisionPosition(boolean[] collisionPosition) {
        this.collisionPosition = collisionPosition;
    }

    /**
     * Sets the values of the depth double array
     * @param depth the collision depth array as [top, bottom,left,right]
     */
    public void setDepth(double[] depth) {
        this.depth = depth;
    }

    /**
     * returns the collision position boolean array
     * @return collisionPosition
     */
    public boolean[] getCollisionPosition() {
        return collisionPosition;
    }
    /**
     * returns the depth double array
     * @return depth
     */
    public double[] getDepth() {
        return depth;
    }
}