package ICS4UProject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This represents a rectangular interactive object with javafx rectangular so that objects can collide with each other
 */

public class InteractiveObjectRec extends CollisionBodyRec {
    private final static double coefficientOfZerothTerm = 100, coefficientOfFirstTerm = 50, exponent = 1.7;
    private ArrayList<Body> kineticList = new ArrayList<>();
    private ArrayList<Vector> normalForceList = new ArrayList<>();
    private boolean isUpdate = true;

    /**
     * To construct an object
     * @param x the x coordinate of the object
     * @param y the y coordinate of the object
     * @param sizeX the width
     * @param sizeY the height
     */
    public InteractiveObjectRec(double x, double y, double sizeX, double sizeY) {
        super(x, y, sizeX, sizeY);
    }

    /**
     * add other objects to the object so that this object can exert forces on them
     * @param k the body objects
     */

    public void addKinetic(Body k) {
        kineticList.add(k);
        Vector normalForce = new Vector();
        normalForceList.add(normalForce);
        k.getForceList().add(normalForce);
    }

    /**
     * exert forces on each body object
     */
    public void collide() {
        //traverse the body list
        for (int i = 0; i < kineticList.size(); i++) {
            //get the information of collision
            CollisionEvent e = collideWith(kineticList.get(i));
            //find the side of collision with lowest overlapping distance
            double min = Double.POSITIVE_INFINITY;
            int minIndex = -1;
            for (int j = 0; j < 4; j++) {
                if (e.getDepth()[j] < min && e.getDepth()[j] > 0) {
                    min = e.getDepth()[j];
                    minIndex = j;
                }
            }
            //exert normal force and friction on that side
            //both force are using the zeroth order term (overlapping distance) and second order term(the relative velocity) so that the system will reach a equilibrium
            //https://math.libretexts.org/Bookshelves/Calculus/Book%3A_Calculus_(OpenStax)/17%3A_Second-Order_Differential_Equations/17.3%3A_Applications_of_Second-Order_Differential_Equations
            switch (minIndex) {
                case 0:
                    normalForceList.get(i).set(
                            new Vector(0,
                                    (-Math.pow(e.getDepth()[minIndex], exponent) * coefficientOfZerothTerm
                                            - getElasticity()[0] * kineticList.get(i).getElasticity()[1] * (kineticList.get(i).getVelocity().getY() - getVelocity().getY()) * coefficientOfFirstTerm) * kineticList.get(i).getMass()));
                    break;
                case 1:
                    normalForceList.get(i).set(
                            new Vector(0,
                                    (Math.pow(e.getDepth()[minIndex], exponent) * coefficientOfZerothTerm
                                            - getElasticity()[1] * kineticList.get(i).getElasticity()[0] * (kineticList.get(i).getVelocity().getY() - getVelocity().getY()) * coefficientOfFirstTerm) * kineticList.get(i).getMass()));
                    break;
                case 2:
                    normalForceList.get(i).set(
                            new Vector((-Math.pow(e.getDepth()[minIndex], exponent) * coefficientOfZerothTerm
                                    - getElasticity()[2] * kineticList.get(i).getElasticity()[3] * (kineticList.get(i).getVelocity().getX() - getVelocity().getX()) * coefficientOfFirstTerm) * kineticList.get(i).getMass(),
                                    0));
                    break;
                case 3:
                    normalForceList.get(i).set(
                            new Vector((Math.pow(e.getDepth()[minIndex], exponent) * coefficientOfZerothTerm
                                    - getElasticity()[3] * kineticList.get(i).getElasticity()[2] * (kineticList.get(i).getVelocity().getX() - getVelocity().getX()) * coefficientOfFirstTerm) * kineticList.get(i).getMass(),
                                    0));
                    break;
                default:
                    normalForceList.get(i).set(new Vector());
                    break;
            }
        }
    }

    @Override
    public void update(long elapsedTime) {
        if(isUpdate){
            super.update(elapsedTime);
            collide();
        }
    }

    @Override
    public void close() {
        super.close();
        isUpdate = false;
    }
}
