package ICS4UProject;

import java.util.ArrayList;


/**
 * This class represent all the game object in the game
 * It has all the data, including the position, velocity, acceleration and all forces applied on the object
 * Each time the update method is call, it will calculate the relative position to the screen
 */
public abstract class GameObject implements PhysicsUpdate, Kinetic, CameraView{


    private double[] elasticity ={0,0,0,0};
    private double frictionCoe;
    private Vector ObjectPosition;
    private Vector CameraPosition;
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private ArrayList<Vector> forceList;
    private Vector appliedForce;
    private double mass = 1;
    private boolean isUpdate = true;


    /**
     * To construct a game object
     * @param x the x coordinate of the object
     * @param y the y coordinate of the object
     */
    public GameObject(double x, double y){

        ObjectPosition = new Vector(x,y);
        velocity = new Vector();
        acceleration = new Vector();
        appliedForce = new Vector();
        forceList = new ArrayList<>();
        forceList.add(appliedForce);
        CameraPosition = new Vector();
    }

    /**
     * It initializes the object at (0,0)
     */

    public GameObject(){
        this(0,0);
    }

    /**
     * It construct an object at position v
     * @param v initial position
     */
    public GameObject(Vector v){
        this(v.getX(),v.getY());
    }


    /**
     * update the ABSOLUTE position of the object
     * @param elapsedTime the time between two update is called
     */
    public void updatePosition(long elapsedTime){
        double elapsedSeconds = elapsedTime / 1_000_000_000.0; //calculate time in second
        Vector netF = new Vector(); //calculate the net force by adding all force in the list together
        for(Vector v:forceList){
            netF = netF.add(v.getCurrentValue());
        }
        //using newton's second law:F=ma
        acceleration = netF.multiply(1/mass);
        //velocity change = acceleration * time
        velocity = velocity.add(acceleration.multiply(elapsedSeconds));
        //displacement = velocity * time
        ObjectPosition = ObjectPosition.add(velocity.multiply(elapsedSeconds));
    }
    /**
     * update the RELATIVE position of the object
     */
    public void updateRelativePosition(){
        position = ObjectPosition.add(CameraPosition.multiply(-1));
    }

    /**
     * update the data of object
     * @param elapsedTime the time between two update is called
     */
    @Override
    public void update(long elapsedTime) {
        if(isUpdate){
            updateRelativePosition();
            updatePosition(elapsedTime);
        }
    }


    @Override
    public Vector getPosition() {
        return ObjectPosition;
    }

    /**
     * get relative position of the object (the position on the screen)
     * @return relative position of the object (the position on the screen)
     */
    public Vector getRelativePosition(){
        return position;
    }

    @Override
    public void setPosition(Vector v) {
        ObjectPosition = v;
    }

    @Override
    public void addDisplacement(Vector v){
        ObjectPosition = ObjectPosition.add(v);
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector v) {
        velocity = v;
    }

    @Override
    public void addVelocity(Vector v) {
        acceleration = acceleration.add(v);
    }

    @Override
    public Vector getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Vector v) {
        acceleration.set(v);
    }

    @Override
    public void addAcceleration(Vector v) {
        addAppliedForce(v);
    }

    /**
     * set the applied force
     * @param v the applied force
     */
    public void setAppliedForce(Vector v) {
        appliedForce.set(v);
    }

    /**
     * add a applied force v to the object
     * @param v the force
     */
    public void addAppliedForce(Vector v){
        appliedForce.set(appliedForce.add(v));
    }

    /**
     * set a applied force v to the object over duration time
     * @param v the force
     * @param duration the length of time you want the force be applied
     */
    public void setAppliedForce(Vector v, int duration){
        appliedForce.set(v);
        Thread t = new Thread(() ->{
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    appliedForce.set(new Vector());
                });
        t.start();
    }

    /**
     * get the applied force
     * @return the applied force
     */
    public Vector getAppliedForce() {
        return appliedForce;
    }

    @Override
    public ArrayList<Vector> getForceList() {
        return forceList;
    }

    @Override
    public void setCameraPosition(Vector v){
        CameraPosition.set(v);
    }
    @Override
    public void addCameraPosition(Vector v){
        CameraPosition.set(CameraPosition.add(v));
    }
    @Override
    public Vector getCameraPosition(){
        return CameraPosition;
    }
    @Override
    public void setMass(double m){
        mass = m;
    }
    @Override
    public double getMass(){
        return mass;
    }

    /**
     * destroy the game object
     */
    public void close() {
        forceList = null;
        velocity = new Vector();
        position = new Vector();
        isUpdate =false;
    }

    @Override
    public double getFrictionCoe() {
        return frictionCoe;
    }

    @Override
    public void setFrictionCoe(double frictionCoe) {
        this.frictionCoe = frictionCoe;
    }

    @Override
    public double[] getElasticity() {
        return elasticity;
    }

    @Override
    public void setElasticity(double[] elasticity) {
        this.elasticity = elasticity;
    }

    public boolean isClose(){
        return !isUpdate;
    }

}
