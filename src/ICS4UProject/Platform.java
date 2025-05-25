package ICS4UProject;

import java.util.ArrayList;

/**
 * This represents a platform in the game
 */
public class Platform extends CollisionRec {
    private final static double coefficientOfZerothTerm = 100, coefficientOfFirstTerm = 50, exponent= 1.7;
    private ArrayList<Body> bodyArrayList = new ArrayList<>();
    private ArrayList<Vector> normalForceList = new ArrayList<>();
    private boolean isUpdate = true;

    /**
     * To construct a platform
     * @param x the x coordinate of the platform
     * @param y the y coordinate of the platform
     * @param sizeX the width
     * @param sizeY the height
     */
    public Platform(double x, double y, double sizeX, double sizeY) {
        super(x, y, sizeX, sizeY);
        setElasticity(new double[]{1,1,1,1});
    }

    /**
     * add objects to the platform so that the platform can exert forces on them
     * @param k the body objects
     */

    public void addKinetic(Body k) {
        bodyArrayList.add(k);
        Vector normalForce = new Vector();
        normalForceList.add(normalForce);
        k.getForceList().add(normalForce);
    }

    /**
     * exert forces on each body object
     */
    public void collide(){
        //traverse the body list
        for(int i = 0; i< bodyArrayList.size(); i++){
            //get the information of collision
            CollisionEvent e = collideWith(bodyArrayList.get(i));
            //find the side of collision with lowest overlapping distance
            double min = Double.POSITIVE_INFINITY;
            int minIndex = -1;
            for(int j=0;j<4;j++) {
                if(e.getDepth()[j] < min && e.getDepth()[j]>0) {
                    min=e.getDepth()[j];
                    minIndex=j;
                }
            }
            //exert normal force and friction on that side
            //both force are using the zeroth order term (overlapping distance) and second order term(the relative velocity) so that the system will reach a equilibrium
            //https://math.libretexts.org/Bookshelves/Calculus/Book%3A_Calculus_(OpenStax)/17%3A_Second-Order_Differential_Equations/17.3%3A_Applications_of_Second-Order_Differential_Equations
            switch (minIndex){
                case 0: normalForceList.get(i).set(
                        new Vector((Math.abs(bodyArrayList.get(i).getVelocity().getX())<10 ? -getFrictionCoe()* bodyArrayList.get(i).getVelocity().getX()*10:
                                (bodyArrayList.get(i).getVelocity().getX()>0 ? -1:1)*getFrictionCoe()* bodyArrayList.get(i).getGravity().length())* bodyArrayList.get(i).getFrictionCoe(),
                                (-Math.pow(e.getDepth()[minIndex],exponent)*coefficientOfZerothTerm
                                        -getElasticity()[0]* bodyArrayList.get(i).getElasticity()[1]* bodyArrayList.get(i).getVelocity().getY()*coefficientOfFirstTerm)* bodyArrayList.get(i).getMass()));
                    break;
                case 1: normalForceList.get(i).set(
                        new Vector(0,
                                (Math.pow(e.getDepth()[minIndex],exponent)*coefficientOfZerothTerm
                                        -getElasticity()[1]* bodyArrayList.get(i).getElasticity()[0]* bodyArrayList.get(i).getVelocity().getY()*coefficientOfFirstTerm)* bodyArrayList.get(i).getMass()));
                    break;
                case 2: normalForceList.get(i).set(
                        new Vector((-Math.pow(e.getDepth()[minIndex],exponent)*coefficientOfZerothTerm
                                -getElasticity()[2]* bodyArrayList.get(i).getElasticity()[3]* bodyArrayList.get(i).getVelocity().getX()*coefficientOfFirstTerm)* bodyArrayList.get(i).getMass(),
                                0));
                    break;
                case 3: normalForceList.get(i).set(
                        new Vector((Math.pow(e.getDepth()[minIndex],exponent)*coefficientOfZerothTerm
                                -getElasticity()[3]* bodyArrayList.get(i).getElasticity()[2]* bodyArrayList.get(i).getVelocity().getX()*coefficientOfFirstTerm)* bodyArrayList.get(i).getMass(),
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