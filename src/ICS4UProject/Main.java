//Main
//We used "Java in Two Semester" as GUI components reference
//https://link.springer.com/book/10.1007/978-3-319-99420-8

package ICS4UProject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Optional;

/**
 * This class contains the main method that actually run the game
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    double scaleFactor = 1;
    Stage stage;
    StartUp startUp;
    Scene scene;
    LevelSelection levelSelection;
    int gameLevel = 0;
    final static String ABOUT = "Authors:Efe, Kristopher, Ryan\nGithub:https://github.com/KristopherZ/ICS4UFinalProject";

    @Override
    public void start(Stage PrimaryStage) {
        stage = PrimaryStage;
        stage.setTitle("Mario");
        AudioClip music = null;
        try {
            music = new AudioClip((new File("Sounds/SuperMarioBrosThemeSong.mp3")).toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        music.setCycleCount(AudioClip.INDEFINITE);
        music.play();
        startUp = new StartUp(this);
        levelSelection = new LevelSelection("LevelSelection.txt",this);
        try {
            stage.getIcons().add(new Image((new File("Sprites/icon.png").toURI().toURL().toString()),false));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        stage.setHeight(720);
        stage.setWidth(1280);

        stage.setScene(startUp.getScene());
        stage.show();
    }

    public void initLevel(String address, int level){
        gameLevel=level;
        Menu menu1 = new Menu("File");
        MenuItem exit = new MenuItem("Back to menu");

        menu1.getItems().add(exit);
        MenuBar mb = new MenuBar(menu1);
        mb.prefWidthProperty().bind(stage.widthProperty());
        Group root = new Group();
        Group group = new Group();
        Scale scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        scale.setX(scaleFactor);
        scale.setY(scaleFactor);

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            scaleFactor = newVal.doubleValue()/720;
            scale.setX(scaleFactor);
            scale.setY(scaleFactor);
        });

        group.getTransforms().add(scale);
        root.getChildren().add(group);
        root.getChildren().add(mb);
        scene = new Scene(root);
        KeyInput k = new KeyInput(scene);
        try {
            Game game = new Game(address,group,k,this);
            game.start();
            stage.setScene(scene);
            exit.setOnAction((e)->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Progress will not be saved",ButtonType.OK,ButtonType.CANCEL);
                alert.initOwner(stage);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK)
                    game.gameEnd(false);

            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileDoesNotFound();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }


    public void gameEnd(boolean isWin) {
        if(isWin)
            levelSelection.unlock(gameLevel+1);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, isWin? "You win!":"You lose!",ButtonType.OK);
        alert.initOwner(stage);
        alert.show();
        System.gc();
        setLevelSelection();
    }

    public void setSceneColor(int a, int b, int c) {
        scene.setFill(Color.rgb(a, b, c));
    }

    public Stage getStage(){
        return stage;
    }

    public void setStartUp(){
        stage.setScene(startUp.getScene());
    }

    public void setLevelSelection() {
        stage.setScene(levelSelection.getScene());
    }

    /**
     * Helper method for fileDoesNotFoundException
     */
    private void fileDoesNotFound(){
        Alert alert = new Alert(Alert.AlertType.ERROR,"Essential file CANNOT be found",ButtonType.OK);
        alert.showAndWait();
        System.exit(-1);
    }
}