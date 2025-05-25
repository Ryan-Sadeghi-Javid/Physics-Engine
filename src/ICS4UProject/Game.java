package ICS4UProject;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class can initialize the game
 */
public class Game extends AnimationTimer {

    private long lastUpdatedTime = 0;
    // coefficient that determines the amount of force from gravity
    private boolean isUpdate = true;
    private GameObjectImage flag;

    private ArrayList<GameObjectImage> decorations = new ArrayList<>();
    private final double gravityCoefficient = 2000;
    private static final double cameraOffset = 100;
    private final Camera camera = new Camera();
    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<Enemy> enemyList = new ArrayList<>();
    private ArrayList<EnemyShell> enemyShellList = new ArrayList<>();
    private ArrayList<PlatformImage> platformImageList = new ArrayList<>();
    private ArrayList<Mushroom> mushroomList = new ArrayList<>();
    private ArrayList<Lavaball> lavaball = new ArrayList<>();
    private Main main;
    private Text score;
    Group root;

    /**
     * Scans the "Initializer.txt" file contained within the project folder
     * Inside the file is a list of all the objects that will be in the scene, with their parameter values
     * This simplifies the process of creating Kinetic objects and PlatformImage objects
     *
     * @param address The address of the "Initializer.txt" file inside the project folder
     * @param group A group - should be empty when passed into parameter
     * @throws FileNotFoundException
     * @throws MalformedURLException
     */
    public Game(String address, Group group, KeyInput k,Main m) throws FileNotFoundException, MalformedURLException {
        root = group;
        main = m;
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40);
        score = new Text("Score:000");
        score.setFont(font);
        score.setStroke(Color.WHITE);
        score.setX(10);
        score.setY(70);
        File textFile = new File(address);
        Scanner input = new Scanner(textFile);


        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.startsWith("1")) {
                Image image;
                String[] values = line.split(" ");
                image = new Image((new File(values[5])).toURI().toURL().toString(), false);
                Player p = new Player(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image, k,this);
                p.setGravity(new Vector(0, gravityCoefficient));
                p.setFrictionCoe(0.4);
                p.setDragCoe(0.001);
//                p.setElasticity(new double[]{0, 0, 0, 0});
                playerList.add(p);
            } else if (line.startsWith("2")) {
                Image image;
                String[] values = line.split(" ");
                image = new Image((new File(values[5])).toURI().toURL().toString(), false);
                Enemy e = new Enemy(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image);
                e.setGravity(new Vector(0, gravityCoefficient));
                e.setVelocity(new Vector(((Math.random()<.5)? -1:1)*100, 0));
                enemyList.add(e);
            } else if (line.startsWith("3")) {
                Image image;
                Image shellImage;
                String[] values = line.split(" ");
                image = new Image((new File(values[5])).toURI().toURL().toString(), false);
                shellImage = new Image((new File(values[6])).toURI().toURL().toString(), false);
                EnemyShell e = new EnemyShell(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image);
                e.setShellImage(shellImage);
                e.setGravity(new Vector(0, gravityCoefficient));
                e.setVelocity(new Vector(((Math.random()<.5)? -1:1)*100, 0));
                enemyShellList.add(e);
            }else if(line.startsWith("4")) {
                Image image;
                String[] values = line.split(" ");
                image = new Image(new File(values[5]).toURI().toURL().toString(), false);
                PlatformImage platform = new PlatformImage(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image);
                platform.setFrictionCoe(1);
                platformImageList.add(platform);
            } else if(line.startsWith("5")) {
                Image pImage;
                Image mImage;
                String[] values = line.split(" ");
                pImage = new Image(new File(values[5]).toURI().toURL().toString(), false);
                mImage = new Image(new File(values[8]).toURI().toURL().toString(), false);
                PlatformImage trigger = new PlatformImage(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), pImage);
                Mushroom mushroom = new Mushroom(trigger, Double.parseDouble(values[6]),
                        Double.parseDouble(values[7]), mImage);
                trigger.setFrictionCoe(1);
                mushroom.setGravity(new Vector(0,2000));
                mushroom.setMovingVelocity(new Vector(300,0));
                trigger.setFrictionCoe(1);
                platformImageList.add(trigger);
                mushroomList.add(mushroom);
            } else if(line.startsWith("6")) {
                Image flagImage;
                String[] values = line.split(" ");
                flagImage = new Image(new File(values[5]).toURI().toURL().toString(), false);
                flag = new GameObjectImage(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), flagImage);
                camera.add(flag);
            } else if(line.startsWith("background")) {
                String[] values = line.split(" ");
                main.setSceneColor(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
            }else if (line.startsWith("7")){
                Image image;
                String[] values = line.split(" ");
                image = new Image(new File(values[5]).toURI().toURL().toString(), false);
                PlatformImagePattern platform = new PlatformImagePattern(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]),50,50, image);
                platform.setFrictionCoe(1);
                platformImageList.add(platform);
            } else if(line.startsWith("8")) {
                Image image;
                String[] values = line.split(" ");
                image = new Image(new File(values[5]).toURI().toURL().toString(), false);
                GameObjectImage decoration = new GameObjectImage(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image);
                decorations.add(decoration);
            } else if (line.startsWith("9")) {
                Image image;
                String[] values = line.split(" ");
                image = new Image(new File(values[5]).toURI().toURL().toString(), false);
                MovingPlatformImage mpi = new MovingPlatformImage(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image);
                mpi.setFrictionCoe(1);
                platformImageList.add(mpi);
            } else if (line.startsWith("l")){
                Image image;
                String[] values = line.split(" ");
                image = new Image(new File(values[5]).toURI().toURL().toString(), false);
                Lavaball l = new Lavaball(Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), image);
                l.setGravity(new Vector(0, gravityCoefficient));
                lavaball.add(l);
            }
        }

        for (PlatformImage platform : platformImageList) {
            for (Enemy enemy : enemyList) {
                platform.addKinetic(enemy);
            }
            for (EnemyShell enemyShell : enemyShellList) {
                platform.addKinetic(enemyShell);
            }
            for (Player player : playerList) {
                platform.addKinetic(player);
                player.setFlag(flag);

            }
            for (Mushroom mushroom: mushroomList) {
                platform.addKinetic(mushroom);
            }
        }

        for (Enemy enemy : enemyList) {
            camera.add(enemy);
        }
        for (EnemyShell enemyShell : enemyShellList) {
            camera.add(enemyShell);
        }
        for (Player player : playerList) {
            camera.add(player);
        }
        for (PlatformImage platform : platformImageList) {
            camera.add(platform);
        }
        for (Mushroom mushroom: mushroomList) {
            camera.add(mushroom);
        }
        for (GameObjectImage decoration: decorations) {
            camera.add(decoration);
        }
        for(Lavaball l : lavaball){
            camera.add(l);
        }

        for (PlatformImage platform : platformImageList) {
            for (Enemy enemy : enemyList) {
                enemy.getPlatformImageList().add(platform);
            }
        }

        for (PlatformImage platform : platformImageList) {
            for (EnemyShell enemy : enemyShellList) {
                enemy.getPlatformImageList().add(platform);
            }
        }

        for (PlatformImage platform : platformImageList) {
            for (Player player : playerList) {
                player.getPlatformImageList().add(platform);
            }
        }

        for (PlatformImage platform: platformImageList) {
            for (Mushroom mushroom: mushroomList) {
                mushroom.getPlatformImageList().add(platform);
            }
        }
        for (Lavaball l:lavaball) {
            for (Player player : playerList) {
                l.addPlayer(player);
            }
        }

        if(flag!=null)
            root.getChildren().add(flag.getImage());
        for (Lavaball l: lavaball) {
            root.getChildren().add(l.getImage());
        }
        for (GameObjectImage g: decorations) {
            root.getChildren().add(g.getImage());
        }
        for (Enemy enemy : enemyList) {
            root.getChildren().add(enemy.getImage());
        }
        for (EnemyShell enemyShell : enemyShellList) {
            root.getChildren().add(enemyShell.getImage());
        }
        for (Player player : playerList) {
            root.getChildren().add(player.getImage());
        }

        for (PlatformImage platform : platformImageList) {
            root.getChildren().add(platform.getRectangle());
        }
        for (Mushroom mushroom: mushroomList) {
            root.getChildren().add(mushroom.getImage());
        }

        root.getChildren().add(score);

        for (Enemy enemy: enemyList) {
            for (Player p: playerList) {
                enemy.addPlayer(p);
            }
        }

        for (EnemyShell enemyShell: enemyShellList) {
            for (Player p: playerList) {
                enemyShell.addPlayer(p);
            }
        }


        for (Mushroom mushroom: mushroomList) {
            for (Player p: playerList) {
                mushroom.addPlayer(p);
            }

        }

        for (Enemy enemy: enemyList) {
            for(EnemyShell es : enemyShellList) {
                enemy.addAllCollisions(es);
            }
            enemy.addAllCollisions(enemy);
        }

        for (EnemyShell enemyShell: enemyShellList) {
            for(Enemy e : enemyList) {
                enemyShell.addAllCollisions(e);
            }
            enemyShell.addAllCollisions(enemyShell);
        }
        start();
    }

    /**
     * Updates all objects inside the scene
     * @param timestamp the timestamp when the method is called
     */
    @Override
    public void handle(long timestamp) {
        score.setText("Score:"+ (new DecimalFormat("000")).format(playerList.get(0).getScore()));
        if (lastUpdatedTime > 0) {
            long elapsedTime = timestamp - lastUpdatedTime;
            double elapsedSeconds = elapsedTime / 1_000_000_000.0;
            System.out.println("fps:"+1/elapsedSeconds);
            camera.setCameraPosition(new Vector(playerList.get(0).getPosition().getX()-cameraOffset, 0));
            for (Enemy enemy : enemyList) {
                enemy.update(elapsedTime);
            }
            for (EnemyShell enemyShell : enemyShellList) {
                enemyShell.update(elapsedTime);
            }
            for (Player player : playerList) {
                player.update(elapsedTime);
            }
            for (PlatformImage platform : platformImageList) {
                platform.update(elapsedTime);
            }
            for (Mushroom mushroom: mushroomList) {
                mushroom.update(elapsedTime);
            }
            for (GameObjectImage decoration: decorations) {
                decoration.update(elapsedTime);
            }
            for (Lavaball l: lavaball) {
                l.update(elapsedTime);
            }
            if(flag!=null)
                flag.update(elapsedTime);
        }
        lastUpdatedTime = timestamp;
    }

    public void endGame() {
        for (Enemy enemy : enemyList) {
            enemy.close();
        }
        for (EnemyShell enemyShell : enemyShellList) {
            enemyShell.close();
        }
        for (Player player : playerList) {
            player.close();
        }
        for (PlatformImage platform : platformImageList) {
            platform.close();
        }
        for (Mushroom mushroom: mushroomList) {
            mushroom.close();
        }
    }

    public void gameEnd(boolean isWin){
        endGame();
        stop();
        root.getChildren().clear();
        playerList=new ArrayList<>();
        enemyList=new ArrayList<>();
        enemyShellList=new ArrayList<>();
        platformImageList=new ArrayList<>();
        mushroomList=new ArrayList<>();
        main.gameEnd(isWin);
    }
}