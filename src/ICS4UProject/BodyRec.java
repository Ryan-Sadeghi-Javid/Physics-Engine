package ICS4UProject;
/**
 * This is a class represent rectangular a body with javafx rectangle added
 */
public class BodyRec extends GameObjectRec implements Body{

    private Vector gravity = new Vector();
    private Drag drag = new Drag(this,0);
    private Vector friction  = new Vector();
    private Vector normalForce = new Vector();

    /**
     * Initialize the body
     * @param x the x coordinate of the object
     * @param y the y coordinate of the object
     * @param sizeX the width of the object
     * @param sizeY the height of the object
     */
    public BodyRec(double x, double y, double sizeX, double sizeY) {
        super(x, y, sizeX, sizeY);
        getForceList().add(gravity);
        getForceList().add(drag);
        getForceList().add(friction);
        getForceList().add(normalForce);
    }

    /**
     * Initialize the body
     * @param v the position of the object
     * @param sizeX the width of the object
     * @param sizeY the height of the object
     */
    public BodyRec(Vector v, double sizeX, double sizeY) {
        this(v.getX(),v.getY(),sizeX,sizeY);
    }

    /**
     * To get the gravity of the object
     * @return the gravity of the object
     */
    @Override
    public Vector getGravity() {
        return gravity;
    }

    /**
     * To get the current drag force
     * @return the current drag force
     */
    @Override
    public Vector getDrag() {
        return drag.getCurrentValue();
    }

    /**
     * get the drag coefficient
     * @return the drag coefficient of air drag
     */
    @Override
    public double getDragCoe() {
        return drag.getDragCoe();
    }

    /**
     * get the default normal force of the object
     * @return the default normal force of the object
     */
    @Override
    public Vector getNormalForce() {
        return normalForce;
    }

    /**
     * get the default friction force
     * @return the default friction force
     */
    @Override
    public Vector getFriction() {
        return friction;
    }

    /**
     * To set the gravity
     * @param v the gravitational force
     */
    @Override
    public void setGravity(Vector v) {
        gravity.set(v);
    }

    /**
     * To set the drag coefficient
     * @param dragCoe the drag coefficient
     */
    @Override
    public void setDragCoe(double dragCoe) {
        drag.setDragCoe(dragCoe);
    }

    /**
     * To set the normal force
     * @param v the normal force
     */
    @Override
    public void setNormalForce(Vector v) {
        normalForce.set(v);
    }

    /**
     * To set the friction
     * @param v the friction force
     */
    @Override
    public void setFriction(Vector v) {
        friction.set(v);
    }
}
