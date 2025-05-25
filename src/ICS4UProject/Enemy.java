package ICS4UProject;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * represents the simple enemy object
 * better known as the "goomba"
 */

public class Enemy extends CollisionBodyImage {

    private final static double coefficientOfZerothTerm = 100, coefficientOfFirstTerm = 50, exponent= 1.7;
    private ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<PlatformImage> platformImageList = new ArrayList<>();
    private boolean isClose = false;
    private  ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<CollisionBodyImage> allCollision = new ArrayList<>();

    /**
     * To construct an enemy
     * @param x the x coordinate of the enemy
     * @param y the y coordinate of the enemy
     * @param sizeX the width
     * @param sizeY the height
     * @param image the image of the enemy
     */
    public Enemy(double x, double y, double sizeX, double sizeY, Image image) {
        super(x, y, sizeX, sizeY, image);
        setElasticity(new double[]{0,1,0,0});
    }

    /**
     * To get the current platformImage list
     * @return the current platformImage list
     */
    public ArrayList<PlatformImage> getPlatformImageList() {
        return platformImageList;
    }

    public void addAllCollisions(CollisionBodyImage cb) {
        allCollision.add(cb);
    }

    public ArrayList<CollisionBodyImage> getAllCollision() {
        return allCollision;
    }


    /**
     * To add to the list of players
     * @param player the player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean runIntoEnemy(Enemy enemy) {
        return (this.collideWith(enemy).getCollisionPosition()[2] || this.collideWith(enemy).getCollisionPosition()[3]);
    }

    /**
     * exert forces in the opposite direction of impact to make the enemy go back and forth
     * closes the game if player touches the enemy, and it is not powered up
     * closes the enemy if it is jumped on
     * gets rid of players power up if ran into with a power up on
     */
    public void collide() {

        for (CollisionBodyImage i : allCollision) {
            if (i != this&&!i.isClose()) {
                if(runIntoLeft(i)) {
                    setVelocity(new Vector(100,0));
                }
                else if(runIntoRight(i)) {
                    setVelocity(new Vector(-100,0));
                }

            }
        }
        //check if the enemy collide
        for (PlatformImage i : platformImageList) {
            if (i.collideWith(this).getCollisionPosition()[2]) {
                setVelocity(new Vector(-200,0));
            } else if (i.collideWith(this).getCollisionPosition()[3]) {
                setVelocity(new Vector(200,0));
            }
        }
        for (Player j : players) {
            if (j.jumpOnEnemy(this)&& !j.isInvisible()) {
                AudioClip music = null;
                try {
                    music = new AudioClip((new File("Sounds/Goomba.mp3")).toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                music.setCycleCount(1);
                music.play();
                j.addScore(100);
                j.setAppliedForce(new Vector(0,-11000),150);
                j.setAppliedForce(new Vector(0,-11000),150);
                this.close();
            }
            else if (j.runIntoEnemyLeft(this) || j.runIntoEnemyRight(this)) {
                if(j.isPowerUp())
                    j.setIsPowerUp(false);
                else
                    j.gameEnd(false);
            }
        }


    }

    /**
     * updates collision position of the enemy
     * @param elapsedTime the time between two update is called
     */
    @Override
    public void update(long elapsedTime) {
        if(isClose)
            System.out.println("closed");
        if(!isClose){
            super.update(elapsedTime);
            collide();
        }
    }

    @Override
    public void close() {
        isClose = true;
        super.close();

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}