package ICS4UProject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * This class shows the start up scene
 */
public class StartUp  {
    private HBox buttonBox;
    private BorderPane borderPane;
    private Instructions instructionsScene;
    private Button start, instructions, settings;
    private Image startIcon, instructionsIcon, settingsIcon;
    private Scene scene;
    private Main main;

    /**
     * To create a start up scene
     * @param m the main class
     */
    public StartUp(Main m){
        main = m;
        instructionsScene = new Instructions(main);
        InputStream stream = null;
        try {
            stream = new FileInputStream("Sprites/Startup-background.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image backgroundImage = new Image(stream);
        ImageView background = new ImageView();
        background.setImage(backgroundImage);
        background.setX(0);
        background.setY(0);
        background.fitWidthProperty().bind(main.getStage().widthProperty());
        background.setPreserveRatio(true);
        try {
            settingsIcon = new Image((new File("Sprites/Settings-icon.png")).toURI().toURL().toString(), false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView settingsView = new ImageView(settingsIcon);
        settingsView.setFitHeight(120);
        settingsView.setPreserveRatio(true);
        try {
            startIcon = new Image((new File("Sprites/play-button.png")).toURI().toURL().toString(), false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView startView = new ImageView(startIcon);
        startView.setFitHeight(120);
        startView.setPreserveRatio(true);
        try {
            instructionsIcon = new Image((new File("Sprites/instructions-icon.png")).toURI().toURL().toString(), false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView instructionsView = new ImageView(instructionsIcon);
        instructionsView.setFitHeight(120);
        instructionsView.setPreserveRatio(true);
        buttonBox = new HBox(100);
        start = new Button("Start");
        instructions = new Button("Instructions");
        settings = new Button("Settings");
        start.setGraphic(startView);
        instructions.setGraphic(instructionsView);
        settings.setGraphic(settingsView);
        Font font = Font.font("Verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 40);
        start.setFont(font);
        start.setMinWidth(400);
        instructions.setFont(font);
        instructions.setMinWidth(400);
        settings.setFont(font);
        buttonBox.getChildren().addAll(start, instructions);
        buttonBox.setAlignment(Pos.CENTER);
        //menu
        Menu file = new Menu("File");
        MenuBar mb = new MenuBar(file);
        MenuItem about = new MenuItem("About");
        about.setOnAction(e->{
            Alert info = new Alert(Alert.AlertType.INFORMATION,Main.ABOUT,ButtonType.OK);
            info.initOwner(main.getStage());
            info.showAndWait();
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->{
            System.exit(0);
        });
        file.getItems().addAll(about,exit);
        borderPane = new BorderPane();
        borderPane.setTop(mb);
        borderPane.setCenter(buttonBox);

        StackPane sp = new StackPane(background,borderPane);
        scene = new Scene(sp);
        start.setOnAction(e -> {
            main.setLevelSelection();
        });
        instructions.setOnAction(e -> {
            main.getStage().setScene(instructionsScene.getScene());
        });
        settings.setOnAction(e -> {

        });
    }

    /**
     * Return the start up scene
     * @return the start up scene
     */

    public Scene getScene() {
        return scene;
    }

}