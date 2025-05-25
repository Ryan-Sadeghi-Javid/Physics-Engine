package ICS4UProject;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * It represents a lavaball
 */
public class Lavaball extends CollisionBodyImage {


    private final ArrayList<Player> players = new ArrayList<>();

    private double initY;

    /**
     * Constructs a LavaBall object
     *
     * @param x   initial position in x-axis
     * @param y   initial position in y-axis
     * @param sizeX the width of the object
     * @param sizeY the height of the object
     * @param image the image of the object
     */
    public Lavaball(double x, double y, double sizeX, double sizeY, Image image) {
        super(x, y, sizeX, sizeY, image);
        initY = y;
    }

    /**
     * Add player to detect collision
     * @param player the player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }



    public void update(long elapsedTime) {
        super.update(elapsedTime);
        if(getPosition().getY()>initY&&getAppliedForce().equals(new Vector())){
            setVelocity(new Vector());
            setAppliedForce(new Vector(0,-8000),200);
        }
        for (Player p:players) {
            if(p.isCollide(getImage())&&!p.isInvisible()){
                if(p.isPowerUp())
                    p.setIsPowerUp(false);
                else
                    p.gameEnd(false);
            }
        }
    }


}
