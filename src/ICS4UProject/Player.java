package ICS4UProject;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * represents the main player object
 */

public class Player extends CollisionBodyImage {

    private final Vector HORIZONTAL_FORCE = new Vector();
    private final ArrayList<PlatformImage> PLATFORM_IMAGE_LIST = new ArrayList<>();
    private final KeyInput k;
    private GameObjectImage flag;
    public ArrayList<PlatformImage> getPlatformImageList() {
        return PLATFORM_IMAGE_LIST;
    }
    private Image[] playerStates = new Image[5];
    private boolean isUpdate = true;
    private boolean isCrouching = false;
    private boolean isPowerUp = false;
    private boolean isInvisible = false;
    private int score = 0;
    private Game game;

    /**
     * To construct a player
     * @param x the x coordinate of the player
     * @param y the y coordinate of the player
     * @param sizeX the width
     * @param sizeY the height
     * @param image the image of the platform
     * @param k the key detection
     */

    public Player(double x, double y, double sizeX, double sizeY, Image image, KeyInput k,Game game) {
        super(x, y, sizeX, sizeY, image);
        this.game = game;
        getForceList().add(HORIZONTAL_FORCE);
        setElasticity(new double[]{.5,1,1,1});
        playerStates = new Image[]{image,image,image,image,image};
        this.k = k;
    }

    /**
     * Checks if the player is on the ground
     * Useful to avoid the player from "flying" and jumping while in the air
     * @return if the player is touching the ground
     */
    private boolean touchingGround() {
        for(PlatformImage i : PLATFORM_IMAGE_LIST) {
            if(i.collideWith(this).getCollisionPosition()[0]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player has jumped on the enemy
     * @param enemy current enemy
     * @return if the player is jumping on the enemy
     */
    public boolean jumpOnEnemy(Enemy enemy) {
        return this.collideWith(enemy).getCollisionPosition()[1];
    }

    public boolean contactLavaBall(Lavaball lb) {
        return (this.collideWith(lb).getCollisionPosition()[0] || this.collideWith(lb).getCollisionPosition()[1] || this.collideWith(lb).getCollisionPosition()[2] || this.collideWith(lb).getCollisionPosition()[3]);
    }

    /**
     * Checks if the player has run into enemy
     * @param enemy current enemy
     * @return if the player is jumping on the enemy
     */
    public boolean runIntoEnemy(Enemy enemy) {
        return (this.collideWith(enemy).getCollisionPosition()[2] || this.collideWith(enemy).getCollisionPosition()[3]) && !isInvisible;
    }

    public boolean runIntoEnemyLeft(Enemy enemy) {
        return this.collideWith(enemy).getCollisionPosition()[2]  && !isInvisible;
    }

    public boolean runIntoEnemyRight(Enemy enemy) {
        return this.collideWith(enemy).getCollisionPosition()[3] && !isInvisible;
    }

    /**
     * Checks if the player has run into mushroom, and powers the player up
     * @param mushroom current enemy
     */
    public void consumeMushroom(Mushroom mushroom) {
        if(!isPowerUp&&(this.collideWith(mushroom).getCollisionPosition()[0] || this.collideWith(mushroom).getCollisionPosition()[1] || this.collideWith(mushroom).getCollisionPosition()[2] || this.collideWith(mushroom).getCollisionPosition()[3])) {
            isPowerUp = true;
            setSizeY(getSizeY()*2.0);
            System.out.println("change the sprite to big mario"); // animation stuff
        }
    }

    /**
     * exert a forces relative to the respective key press for player movement
     * w - up
     * a - left
     * d - right
     */
    private void keyMovement() {
        if(k.isaPressed()&&k.isdPressed()){
            HORIZONTAL_FORCE.set(new Vector());
        }else if(k.isaPressed()) {
            if(touchingGround())
                HORIZONTAL_FORCE.set(new Vector(-2000, 0));
            else
                HORIZONTAL_FORCE.set(new Vector(-600, 0));
        }else if(k.isdPressed()){
            if(touchingGround())
                HORIZONTAL_FORCE.set(new Vector(2000, 0));
            else
                HORIZONTAL_FORCE.set(new Vector(600, 0));
        } else if(k.isaPressed()&&k.isShiftPressed()) {
            if(touchingGround())
                HORIZONTAL_FORCE.set(new Vector(-2150, 0));
            else
                HORIZONTAL_FORCE.set(new Vector(-850, 0));
        } else if(k.isdPressed()&&k.isShiftPressed()) {
            if(touchingGround())
                HORIZONTAL_FORCE.set(new Vector(2150, 0));
            else
                HORIZONTAL_FORCE.set(new Vector(850, 0));
        } else {
            HORIZONTAL_FORCE.set(new Vector());
        }
        if(k.iswPressed() && touchingGround()){
            this.setAppliedForce(new Vector(0,-11000),150);
        }
    }

    /**
     * Sets the player image depending on current movement, to simulate animation
     */
    private void playerStateChange() {
        if(getVelocity().getY() < 0)
            getImage().setImage(playerStates[0]);
        else if(getVelocity().getX() > 0)
            getImage().setImage(playerStates[1]);
        else if(getVelocity().getX() < 0)
            getImage().setImage(playerStates[2]);
        else
            getImage().setImage(playerStates[3]);
    }

    /**
     * includes a horizontal velocity cap to avoid player acceleration beyond a certain point
     * @param elapsedTime the time between two update is called
     */
    @Override
    public void update(long elapsedTime) {
        if(isUpdate){
            super.update(elapsedTime);
            keyMovement();
            playerStateChange();
            if(!k.isShiftPressed()) {
                if(getVelocity().getX() > 500) {
                    setVelocity(new Vector(500, getVelocity().getY()));
                }
                if(getVelocity().getX() < -500) {
                    setVelocity(new Vector(-500,getVelocity().getY()));
                }
            } else {
                if(getVelocity().getX() > 700) {
                    setVelocity(new Vector(700, getVelocity().getY()));
                }
                if(getVelocity().getX() < -700) {
                    setVelocity(new Vector(-700,getVelocity().getY()));
                }
            }
            if(flag!=null&&this.isCollide(flag.getImage())){
                gameEnd(true);
            }

            if(isPowerUp && k.issPressed() && !isCrouching && touchingGround()) {
                setSizeY(50);
                setPosition(new Vector(getPosition().getX(), getPosition().getY()+50));
                isCrouching = true;
            } else if(isPowerUp && !k.issPressed() && isCrouching && touchingGround()) {
                setPosition(new Vector(getPosition().getX(), getPosition().getY()-50));
                setSizeY(100);
                isCrouching = false;
            }

            if(getPosition().getY()>800){
                gameEnd(false);
            }
        }
    }

    /**
     * Closes the player object by stopping it from updating
     */
    @Override
    public void close() {
        super.close();
        isUpdate = false;
    }

    /**
     * This is used to set the display image for different states of the player
     * @param playerStates a 5-element Image Array
     */
    public void setPlayerStates(Image[] playerStates) {
        this.playerStates = playerStates;
    }

    /**
     * Check if the player is at power up state
     * @return if the player is at power up state
     */
    public boolean isPowerUp() {
        return isPowerUp;
    }

    /**
     * Set the player's power-up state
     * @param isPowerUp player's power-up state
     */

    public void setIsPowerUp(boolean isPowerUp) {
        this.isPowerUp = isPowerUp;
        //if it is set to false, the player will decrease the size and invisible for 2s
        if(!isPowerUp){
            if(!isCrouching)
                setSizeY(getSizeY()/2.0);
            setIsInvisible(2000);
        }
    }

    /**
     * Set player to invisible for duration millisecond (It won't react from the enemy)
     * @param duration time that the player is invisible in millisecond
     */
    public void setIsInvisible(long duration) {
        isInvisible = true;
        (new Thread(()->{
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isInvisible = false;
        })).start();
    }

    /**
     * Check if the player is invisible
     * @return whether the player is invisible or not
     */
    public boolean isInvisible(){
        return isInvisible;
    }

    /**
     * To end the game
     * @param isWin the ending state
     */
    public void gameEnd(boolean isWin){
        game.gameEnd(isWin);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Set end point
     * @param flag the end point
     */
    public void setFlag(GameObjectImage flag) {
        this.flag = flag;
    }
}