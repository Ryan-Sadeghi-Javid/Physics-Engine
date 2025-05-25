package ICS4UProject;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.net.MalformedURLException;

/**
 * This class shows the instruction scene
 */
public class Instructions {

    private Button back;
    private Scene scene;
    private StartUp startUp;
    private VBox vBox;
    private BorderPane borderPane;

    private Group root;

    private Main main;

    /**
     * Create a new instruction pane
     * @param m the main class so that the go back button works
     */
    public Instructions(Main m){
        main = m;
        back = new Button("Back");
        vBox = new VBox(10);
        ImageView picture1 = new ImageView();
        ImageView picture2 = new ImageView();
        ImageView picture3 = new ImageView();
        Label sentence = new Label("Move left and right with the A and D keys.\n" +
                "                   Press W to jump");
        sentence.setFont(new Font("Arial", 24));
        try {
            picture1.setImage(new Image((new File("Sprites/game-description.png")).toURI().toURL().toString(), false));
            picture2.setImage(new Image((new File("Sprites/enemy-descriptions.png")).toURI().toURL().toString(), false));
            picture3.setImage(new Image((new File("Sprites/jumping-description.png")).toURI().toURL().toString(), false));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        vBox.getChildren().addAll(sentence, picture3, picture1, picture2, back);
        vBox.setAlignment(Pos.CENTER);
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
        borderPane.setCenter(vBox);
        scene = new Scene(borderPane);
        back.setOnAction(e -> {
            main.setStartUp();
        });
    }

    /**
     * Return the instruction scene
     * @return the instruction scene
     */
    public Scene getScene() {
        return scene;
    }
}
