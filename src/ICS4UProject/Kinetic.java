package ICS4UProject;

import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Kinetic interface
 * Implemented in various other classes in order to add kinetic properties to objects
 * Getter and setter methods for position, displacement, velocity, acceleration and mass
 */
public interface Kinetic {
    /**
     * get the position of the object
     * @return the position as a vector
     */
    public Vector getPosition();

    /**
     * set the position of the object
     * @param v the position
     */
    public void setPosition(Vector v);

    /**
     * add a displacement to the position
     * @param v the displacement
     */
    public void addDisplacement(Vector v);

    /**
     * get the velocity of the object
     * @return the velocity
     */
    public Vector getVelocity();

    /**
     * set the velocity to given vector
     * @param v the velocity
     */
    public void setVelocity(Vector v);

    /**
     * add velocity by the vector
     * @param v the change of velocity
     */
    public void addVelocity(Vector v);

    /**
     * get the acceleration
     * @return the acceleration vector
     */
    public Vector getAcceleration();

    /**
     * set the acceleration to the given vector
     * @param v the acceleration vector
     */
    public void setAcceleration(Vector v);

    /**
     * add the acceleration by the given vector
     * @param v the change of acceleration
     */
    public void addAcceleration(Vector v);

    /**
     * set the mass
     * @param m mass
     */
    public void setMass(double m);

    /**
     * get the mass
     * @return the mass
     */
    public double getMass();

    /**
     * get the friction coefficient
     * @return the friction coefficient
     */
    public double getFrictionCoe();

    /**
     * set the friction coefficient
     * @param frictionCoefficient the friction coefficient
     */
    public void setFrictionCoe(double frictionCoefficient);

    /**
     * Checks for collision between two objects
     * @param o another javafx node to collide with
     * @return whether they overlap each other
     */
    public boolean isCollide(Object o);

    /**
     * get the ist of applied forces
     * @return List of applied forces of the object
     */
    public ArrayList<Vector> getForceList();

    /**
     * get the elasticity coefficient array - a 4-element array arranged in this order [top, bottom, left, right]
     * @return the elasticity coefficient array - a 4-element array arranged in this order [top, bottom, left, right]
     */
    public double[] getElasticity();

    /**
     * set the elasticity for 4 sides
     * @param elasticity the elasticity coefficient array - a 4-element array arranged in this order [top, bottom, left, right]
     */
    public void setElasticity(double[] elasticity);
}