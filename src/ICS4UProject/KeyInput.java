package ICS4UProject;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


/**
 * This class is used to deal with key input
 *
 */
public class KeyInput {

    // Boolean values indicating whether a button is pressed or not
    private boolean wPressed, aPressed, sPressed, dPressed, spaceBarPressed, shiftPressed;

    /**
     * Sets boolean fields to true when their respective buttons are pressed
     * @param scene the scene in which the key listener is attached to
     */
    public KeyInput(Scene scene) {
        scene.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.W)) {
                wPressed = true;
            }

            if(e.getCode().equals(KeyCode.A)) {
                aPressed = true;
            }
            if(e.getCode().equals(KeyCode.S)) {
                sPressed = true;
            }
            if(e.getCode().equals(KeyCode.D)) {
                dPressed = true;
            }
            if(e.getCode().equals(KeyCode.SPACE)) {
                spaceBarPressed = true;
            }
            if(e.getCode().equals(KeyCode.SHIFT)) {
                shiftPressed = true;
            }
        });

        scene.setOnKeyReleased(e ->{
            if(e.getCode().equals(KeyCode.W)) {
                wPressed = false;
            }
            if(e.getCode().equals(KeyCode.A)) {
                aPressed = false;
            }
            if(e.getCode().equals(KeyCode.S)) {
                sPressed = false;
            }
            if(e.getCode().equals(KeyCode.D)) {
                dPressed = false;
            }
            if(e.getCode().equals(KeyCode.SPACE)) {
                spaceBarPressed = false;
            }
            if(e.getCode().equals(KeyCode.SHIFT)) {
                shiftPressed = false;
            }

        });

    }

    /**
     * check if W key is pressed
     * @return wPressed
     */
    public boolean iswPressed() {
        return wPressed;
    }

    /**
     * check if A key is pressed
     * @return aPressed
     */
    public boolean isaPressed(){
        return aPressed;
    }

    /**
     * check if S key is pressed
     * @return sPressed
     */
    public boolean issPressed(){
        return sPressed;
    }

    /**
     * check if D key is pressed
     * @return dPressed
     */
    public boolean isdPressed() {
        return dPressed;
    }

    /**
     * check if space bar is pressed
     * @return spaceBarPressed
     */
    public boolean isSpaceBarPressed() {
        return spaceBarPressed;
    }

    /**
     * check if shift is pressed
     * @return shiftPressed
     */

    public boolean isShiftPressed() {
        return shiftPressed;
    }
}