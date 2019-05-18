package sample;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.AudioClip;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.Group;

public class Main extends Application {
    private Stage stageOne;
    private Scene sceneStart, scenePlay, sceneOption, sceneCredit;
    private createAudio songs;
    private makeImage picture;
    private Rectangle2D primaryScreenBounds;
    private AudioClip currentPlay;

    private void playSong(Random rand) {
        int rVal = rand.nextInt(13) + 3;
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(rVal) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        currentPlay = new AudioClip(media.getSource());
        currentPlay.setCycleCount(MediaPlayer.INDEFINITE);
        currentPlay.play();
    }

    // This is the main part of the program. It will scan files from
    // prince game manager files.
    private Scene createPlayingScene() {
        Random rand = new Random();
        playSong(rand);

        Image image = new Image(new File("E:\\All Computer Science Materials\\" +
                "Java 240 Project\\PrinceFX\\image\\" + picture.getImage(1) + ".png").toURI().toString());
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(0);
        imageView.setY(0);
        //setting the fit height and width of the image view
        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        // All the buttons.
        Label labels = new Label();
        Button goBack = new Button("Save and Go Back to Main");
        Button options = new Button("Options");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPlay.stop();
                sceneStart = createStartScene();
                stageOne.setScene(sceneStart);
            }
        });

        // Button for first page.
        HBox layoutPlaying = new HBox(15);
        layoutPlaying.getChildren().addAll(labels, goBack, options);

        // Store them buttons and images together.
        Group playGroup = new Group(imageView, layoutPlaying);

        return new Scene(playGroup, 200, 200);
    }

    // This will load the credit scene.
    private Scene createCreditScene() {
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(2) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        currentPlay = new AudioClip(media.getSource());
        currentPlay.setCycleCount(MediaPlayer.INDEFINITE);
        currentPlay.play();

        //Creating an image
        Image image = new Image(new File("E:\\All Computer Science Materials\\" +
                "Java 240 Project\\PrinceFX\\image\\" + picture.getImage(3) + ".png").toURI().toString());
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(0);
        imageView.setY(0);
        //setting the fit height and width of the image view
        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        Label labelCredits = new Label();
        Button goBack = new Button("Save and Go Back to Main");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPlay.stop();
                sceneStart = createStartScene();
                stageOne.setScene(sceneStart);
            }
        });

        // Button for first page.
        HBox layoutCredit = new HBox(15);
        layoutCredit.getChildren().addAll(labelCredits, goBack);

        // Store them buttons and images together.
        Group creditGroup = new Group(imageView, layoutCredit);
        return new Scene(creditGroup, 200, 200);
    }

    // This method will create option scene. It is accessible from main and playing.
    private Scene createOptionScene() {
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(1) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        currentPlay = new AudioClip(media.getSource());
        currentPlay.setCycleCount(MediaPlayer.INDEFINITE);
        currentPlay.play();

        Image image = new Image(new File("E:\\All Computer Science Materials\\" +
                "Java 240 Project\\PrinceFX\\image\\" + picture.getImage(2) + ".png").toURI().toString());
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(0);
        imageView.setY(0);
        //setting the fit height and width of the image view
        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        Label labelOption = new Label();
        Button goBack = new Button("Go Back to Main");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPlay.stop();
                sceneStart = createStartScene();
                stageOne.setScene(sceneStart);
            }
        });

        // Button for first page.
        HBox layoutOp= new HBox(15);
        layoutOp.getChildren().addAll(labelOption, goBack);
        Group gOption = new Group(imageView, layoutOp);
        return new Scene(gOption, 200, 200);
    }

    // This method will create the main scene of the game.
    private Scene createStartScene() {
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(0) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        currentPlay = new AudioClip(media.getSource());
        currentPlay.setCycleCount(MediaPlayer.INDEFINITE);
        currentPlay.play();

        //Creating an image
        Image image = new Image(new File("E:\\All Computer Science Materials\\" +
                "Java 240 Project\\PrinceFX\\image\\" + picture.getImage(0) + ".png").toURI().toString());
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(0);
        imageView.setY(0);
        //setting the fit height and width of the image view
        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        Label label1 = new Label();
        Button start = new Button("New Game");
        Button loading = new Button("Load Game");
        Button options = new Button("Options");
        Button credits = new Button("Credits");
        Button quitting = new Button("Quit");
        // This will lead to the playing scene.
        loading.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                currentPlay.stop();
                scenePlay = createPlayingScene();
                stageOne.setScene(scenePlay);
            }
        });

        // This will lead to the option page.
        options.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                currentPlay.stop();
                sceneOption = createOptionScene();
                stageOne.setScene(sceneOption);
            }
        });

        // Creating the credit scene.
        credits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                currentPlay.stop();
                sceneCredit = createCreditScene();
                stageOne.setScene(sceneCredit);
            }
        });

        // This will quit the game.
        quitting.setOnAction(e -> stageOne.close());

        // Button for first page.
        VBox layoutNewGame = new VBox(15);
        layoutNewGame.getChildren().addAll(label1, start, loading, options, credits, quitting);

        // Store them buttons and images together.
        Group gOne = new Group(imageView, layoutNewGame);
        return new Scene(gOne, 200, 200);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stageOne = primaryStage;

        // There are 17 songs. Begins from index of 0 to 16.
        // 0 - 7: Normal, 8 - 11 crusade songs, 12 - 16 dark era songs.
        songs = new createAudio();

        // There are only 2 images.
        picture = new makeImage();

        // This part is only making the window.
        stageOne.setMaximized(true);
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stageOne.setX(primaryScreenBounds.getMinX());
        stageOne.setY(primaryScreenBounds.getMinY());
        stageOne.setWidth(primaryScreenBounds.getWidth());
        stageOne.setHeight(primaryScreenBounds.getHeight());

        sceneStart = createStartScene();

        stageOne.setScene(sceneStart);
        stageOne.setTitle("Prince Game ver 2.0.4.c");
        stageOne.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
