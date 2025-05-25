package ICS4UProject;

/**
 * This body interface represents a game object with dimension and basic forces
 */
public interface Body extends KineticsWithSize{
    /**
     * get the gravity of the object
     * @return the gravity of the object
     */
    public Vector getGravity();

    /**
     * get the drag of the object
     * @return the drag
     */
    public Vector getDrag();

    /**
     * get the drag coefficient of the drag
     * @return the drag coefficient of the object
     */
    public double getDragCoe();

    /**
     * get the default normal force of the object
     * @return the default normal force of the object
     */
    public Vector getNormalForce();

    /**
     * get the default friction of the object
     * @return the default friction of the object
     */
    public Vector getFriction();

    /**
     * set the gravity (a constant force) to the vector in the parameter
     * @param v the gravity vector
     */
    public void setGravity(Vector v);

    /**
     * set the drag coefficient so that the drag is calculated by |D| = C*|v|^2 and opposite to the direct of motion
     * @param dragCoe the drag coefficient
     */
    public void setDragCoe(double dragCoe);

    /**
     * set the normal force to v
     * @param v the default normal force of the object
     */
    public void setNormalForce(Vector v);

    /**
     * set the friction to v
     * @param v the default friction force of the object
     */
    public void setFriction(Vector v);
}
