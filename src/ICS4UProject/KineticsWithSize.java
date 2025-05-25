package ICS4UProject;

/**
 * This interface represents a rectangular object
 */
public interface KineticsWithSize extends Kinetic{
    /**
     * get the width
     * @return the width
     */
    public double getSizeX();

    /**
     * get the height
     * @return the height
     */
    public double getSizeY();

    /**
     * set the width
     * @param x the width
     */
    public void setSizeX(double x);

    /**
     * set the height
     * @param y the height
     */
    public void setSizeY(double y);
}
