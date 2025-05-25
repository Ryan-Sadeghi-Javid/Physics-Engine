package ICS4UProject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a camera so that the relative position of objects attached to this class can be moved
 */
public class Camera implements CameraView{

    private ArrayList<CameraView> arr;

    /**
     * create a camera so that all objects can move along the camera
     */

    public Camera(){
        arr = new ArrayList<>();
    }

    /**
     * create a camera so that all objects can move along the camera
     * @param arr add all array list into the camera
     */
    public Camera(ArrayList<? extends CameraView> arr){
        this.arr = (ArrayList<CameraView>) arr;
    }

    /**
     * create a camera so that all objects can move along the camera
     * @param arr add all array list into the camera
     * @param <E> a CameraView object
     */
    public <E extends CameraView> Camera(E ... arr){
        this.arr = (ArrayList<CameraView>) Arrays.asList(arr);
    }

    /**
     * add CameraView object to the Camera so that it will move these object as the camera position is changed
     * @param i the index of the object in the list which you want to add
     * @param e a CameraView object
     */

    public  void add(int i,CameraView e){
        arr.add(i,e);
    }

    /**
     * add CameraView object to the Camera so that it will move these object as the camera position is changed
     * @param e a CameraView object
     */
    public  void add(CameraView e){
        arr.add(e);
    }

    /**
     * Get the CameraView at index i
     * @param i index of the object
     */
    public void get(int i){
        arr.get(i);
    }

    /**
     * set the KineticObject at index i to e
     * @param i index
     * @param e CameraView object
     */
    public  void set(int i,CameraView e){
        arr.set(i,e);
    }

    @Override
    public void setCameraPosition(Vector v) {
        for(CameraView i: arr){
            i.setCameraPosition(v);
        }
    }

    @Override
    public void addCameraPosition(Vector v) {
        for(CameraView i: arr){
            i.addCameraPosition(v);
        }
    }

    @Override
    public Vector getCameraPosition() {
        if(arr.get(0)==null){
            return null;
        }else{
            return arr.get(0).getCameraPosition();
        }
    }
}
