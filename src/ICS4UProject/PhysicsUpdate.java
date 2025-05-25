package ICS4UProject;

/**
 * This interface enable all gameObjects be called by the animation time to update
 */
public interface PhysicsUpdate {
    /**
     * update the data of object
     * @param elapsedTime the time between two update is called
     */
    public void update(long elapsedTime);

}
