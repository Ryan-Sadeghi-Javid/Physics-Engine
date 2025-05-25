package ICS4UProject;

/**
 * This class represents a 2-D vector
 */
public class Vector {

    private double x;
    private double y;

    /**
     * Construct a vector
     * @param x x component
     * @param y y component
     */
    public Vector(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * Default args constructor
     * x and y components are set to 0
     */
    public Vector(){
        this.x=0;
        this.y=0;
    }

    /**
     * Copy constructor
     * @param v a vector
     */
    public Vector(Vector v){
        this.x=v.x;
        this.y=v.y;
    }

    /**
     * sets the vector to another vector
     * @param v vector
     */
    public void set(Vector v){
        x=v.x;
        y=v.y;
    }

    /**
     * x component of vector
     * @return x component of vector
     */
    public double getX() {
        return x;
    }

    /**
     * y component of vector
     * @return y component of vector
     */
    public double getY() {
        return y;
    }

    /**
     * sets x component of vector
     * @param x new x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * sets y component of vector
     * @param y new y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Overrided toString() method
     * @return String
     */
    @Override
    public String toString() {
        return "<"+x+","+y+">";
    }

    /**
     * Adds two vectors together
     * @param o vector
     * @return vector sum of two vectors
     */
    public Vector add(Vector o){
        return new Vector(x+o.x,y+o.y);
    }

    /**
     * Calculates the dot product between two vectors
     * @param o vector
     * @return dot product of both vectors
     */
    public double dotProduct(Vector o){
        return x*o.x+y*o.y;
    }

    /**
     * Return the distance of the vector
     * @return Distance of the vector
     */
    public double length(){
        return Math.sqrt(x*x+y*y);
    }

    /**
     * Multiples both x and y components by coeffecient
     * @param coe multiplication coeffecient
     * @return multiplied vector
     */
    public Vector multiply(double coe){
        return new Vector(x*coe,y*coe);
    }

    /**
     * Checks whether one object equals to another one
     * @param o An object
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        return x==((Vector)o).x && y==((Vector)o).y;
    }

    /**
     * returns vector
     * @return Vector
     */
    public Vector getCurrentValue() {
        return this;
    }

    /**
     * Calculates and returns angle of vector
     * @return angle of vector
     */
    public double getAngle(){
        return Math.toDegrees(Math.atan2(y,x));
    }

}
