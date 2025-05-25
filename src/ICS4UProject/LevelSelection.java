package ICS4UProject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * This classed is used for level selection
 */
public class LevelSelection {
    private ArrayList<Button> levels = new ArrayList<>(); // all the levels are button so they are stored in an arraylist
    private Scene scene; // the scene the level selection
    private ScrollPane scrollPane; //the scroll pane for the level selection
    private HBox hBox; //use a hbox to store all the buttons
    private VBox vBox; //use a vbox to contain the title, the hbox and the return button
    private Button back;
    private ArrayList<String> levelNames = new ArrayList<>(); // the arraylist that stores all the path of the levels
    private ArrayList<Boolean> isLock = new ArrayList<>(); //the arraylist that stores whether this level is locked or not
    private Main main;
    private String fileAddress;
    private BorderPane borderPane;

    /**
     * To initialize the level selection pane
     * @param address the path of the level selection
     * @param m the main class in order to change scene
     */
    public LevelSelection(String address, Main m) {
        fileAddress = address;
        main = m;
        Scanner loader = null;
        try {
            loader = new Scanner(new File(address));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileDoesNotFound();
        }
        //scan the data from the file to ram
        while (loader.hasNextLine()) {
            String line = loader.nextLine();
            String[] info = line.split(" ");
            levelNames.add(info[0]);
            isLock.add(info[1].equals("true"));
        }
        loader.close();
        //initialize the buttons
        for (int i = 0; i < levelNames.size(); i++) {
            int finalI = i;
            String pictureAddress = "";
            if (isLock.get(finalI)) {
                pictureAddress = levelNames.get(finalI) + "\\lock.jpg";
            } else {
                pictureAddress = levelNames.get(finalI) + "\\unlock.jpg";
            }
            File image = new File(pictureAddress);
            ImageView imageView = null;
            try {
                imageView = new ImageView(new Image(image.toURI().toURL().toString(), false));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            imageView.fitHeightProperty().bind(main.getStage().heightProperty().multiply(.7));
            imageView.setPreserveRatio(true);
            Button levelButton = new Button();
            levelButton.setGraphic(imageView);
            levelButton.setOnAction(e -> {
//                System.out.println(levels.indexOf(e.getSource()));
                if (isLock.get(finalI)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "This level has not been unlocked yet", ButtonType.OK);
                    alert.initOwner(main.getStage());
                    alert.showAndWait();
                } else {
                    main.initLevel(levelNames.get(finalI) + "\\initializer.txt",levels.indexOf(e.getSource()));
                }
            });
            levels.add(levelButton);
        }
        //construct the scene
        hBox = new HBox(40);
        vBox = new VBox(10);
        Label label = new Label("Level Selection");

        label.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 40));
        hBox.setPadding(new Insets(10, 40, 10, 40));
        hBox.getChildren().addAll(levels);
        scrollPane = new ScrollPane(hBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        Button back = new Button("Back");
        back.setOnAction(e -> {
            main.setStartUp();
        });
        vBox.getChildren().addAll(label, scrollPane, back);
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

    }

    /**
     * helper method update the buttons
     */
    private void update() {
        for (int i = 0; i < levels.size(); i++) {
            int finalI = i;
            String pictureAddress = "";
            if (isLock.get(finalI)) {
                pictureAddress = levelNames.get(finalI) + "\\lock.jpg";
            } else {
                pictureAddress = levelNames.get(finalI) + "\\unlock.jpg";
            }
            File image = new File(pictureAddress);
            ImageView imageView = null;
            try {
                imageView = new ImageView(new Image(image.toURI().toURL().toString(), false));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            imageView.fitHeightProperty().bind(main.getStage().heightProperty().multiply(.7));
            imageView.setPreserveRatio(true);
            levels.get(i).setGraphic(imageView);
            levels.get(i).setOnAction(e -> {
                if (isLock.get(finalI)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This level has not been unlocked yet", ButtonType.OK);
                    alert.initOwner(main.getStage());
                    alert.showAndWait();
                } else {
                    main.initLevel(levelNames.get(finalI) + "\\initializer.txt",levels.indexOf(e.getSource()));
                }
            });
        }


    }

    /**
     * unlock a level
     * @param index the index of the level
     */
    public void unlock(int index) {
        if(index<levels.size()){
            isLock.set(index,false);
            update();
            updateFile();
        }
    }

    /**
     * Helper method to update the file
     */
    private void updateFile() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(fileAddress));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileDoesNotFound();
        }
        StringJoiner joiner = new StringJoiner("\n");
        for(int i=0;i<levelNames.size();i++){
            joiner.add(levelNames.get(i)+" "+isLock.get(i));
        }
        pw.print(joiner);
        pw.close();
    }

    /**
     * get the scene for level selection
     * @return the scene for level selection
     */
    public Scene getScene() {
        return scene;
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
