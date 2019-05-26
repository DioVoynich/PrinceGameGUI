package sample;

import java.io.*;
import java.util.*;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Insets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.scene.control.Slider;
import javafx.scene.control.Button;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.Group;

// Try not to run this program with battery in save mode, it may cause temporary freezing.
// Sometimes, start -> load may cause freezing for a short period, but everything is still
// functional. Consider it as a memory overuse in any games. -> This issue is been resolved.
// I didn't type the name of the song file correctly.

public class Main extends Application {
    private Stage stageOne;
    private Scene sceneStart, scenePlay, sceneOption, sceneCredit;
    private createAudio songs;
    private makeImage picture;
    private Rectangle2D primaryScreenBounds;
    private MediaPlayer musicPlay;
    private MediaView mView;
    private Slider volumeSlider;
    private double currentVolume;
    private int songChooser;

    // This method will accept a random object as a parameter.
    private void playSong(Random rand) {
        // int rVal = rand.nextInt(songs.getSize() - songChooser) + songChooser;
        int rVal = rand.nextInt(17) + 3;
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(rVal) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        musicPlay = new MediaPlayer(media);
        mView.setMediaPlayer(musicPlay);
        musicPlay.play();

        // Recursively playing the music.
        musicPlay.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playSong(rand);
            }
        });
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
        Button goBack = new Button("Go Back to Main");
        // Button options = new Button("Options");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                musicPlay.stop();
                sceneStart = createStartScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(sceneStart);
            }
        });

        // Button for first page.
        HBox layoutPlaying = new HBox();
        layoutPlaying.getChildren().addAll(labels, goBack);
        layoutPlaying.setTranslateX(1300);
        layoutPlaying.setTranslateY(770);

        // Store them buttons and images together.
        Group playGroup = new Group(imageView, layoutPlaying);
        return new Scene(playGroup, 200, 200);
    }

    // This will load the credit scene.
    private Scene createCreditScene() {
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(2) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        musicPlay = new MediaPlayer(media);
        mView.setMediaPlayer(musicPlay);
        musicPlay.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlay.play();

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
        Button goBack = new Button("Go Back to Main");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                musicPlay.stop();
                sceneStart = createStartScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(sceneStart);
            }
        });

        // Button for first page.
        HBox layoutCredit = new HBox();
        layoutCredit.getChildren().addAll(labelCredits, goBack);
        layoutCredit.setTranslateX(1350);
        layoutCredit.setTranslateY(750);

        // Store them buttons and images together.
        Group creditGroup = new Group(imageView, layoutCredit);
        return new Scene(creditGroup, 200, 200);
    }

    // This method will create option scene. It is accessible from main and playing.
    private Scene createOptionScene() {
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(1) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        musicPlay = new MediaPlayer(media);
        mView.setMediaPlayer(musicPlay);
        musicPlay.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlay.play();

        // Volume Control
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                currentVolume = volumeSlider.getValue() / 100;
                musicPlay.setVolume(currentVolume);
            }
        });

        /* // This is only for the test purpose. It can be substituted to the existing volume slider.
        volumeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                musicPlay.setVolume(volumeSlider.getValue() / 100);
            }
        }); */

        HBox temp = new HBox(15);
        temp.getChildren().addAll(volumeSlider);
        temp.setTranslateX(850);
        temp.setTranslateY(410);
        volumeSlider.setMinWidth(400);

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

        Button goBack = new Button("Go Back to Main");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                musicPlay.stop();
                sceneStart = createStartScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(sceneStart);
            }
        });

        // Button to the main page.
        HBox layoutOp = new HBox();
        layoutOp.getChildren().add(goBack);

        // Button coordinate.
        layoutOp.setTranslateX(1400);
        layoutOp.setTranslateY(750);

        Group gOption = new Group(imageView, layoutOp, temp);
        return new Scene(gOption, 200, 200);
    }

    // This method will create the main scene of the game.
    private Scene createStartScene() {
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(0) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        musicPlay = new MediaPlayer(media);
        mView.setMediaPlayer(musicPlay);
        musicPlay.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlay.play();

        // This is for the initialization.
        if (currentVolume < 0) {
            currentVolume = musicPlay.getVolume() * 100;
            volumeSlider.setValue(currentVolume); // 1.0 = max 0.0 = min
        }

        //Creating an image from the image file.
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

        Label label1 = new Label("© 2019 Prince Game, CS 240");
        label1.setStyle("-fx-font: 15 arial;");
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
                musicPlay.stop();
                scenePlay = createPlayingScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(scenePlay);
            }
        });

        // This will lead to the option page.
        options.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                musicPlay.stop();
                sceneOption = createOptionScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(sceneOption);
            }
        });

        // Creating the credit scene.
        credits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                musicPlay.stop();
                sceneCredit = createCreditScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(sceneCredit);
            }
        });

        // This will quit the game.
        quitting.setOnAction(e -> stageOne.close());

        // Button for first page.
        VBox layoutNewGame = new VBox(20);
        layoutNewGame.getChildren().addAll(start, loading, options, credits, quitting, label1);

        // Moving to the right coordinate.
        layoutNewGame.setTranslateX(1300);
        layoutNewGame.setTranslateY(520);

        // Store them buttons and images together.
        Group gOne = new Group(imageView, layoutNewGame);
        return new Scene(gOne, 200, 200);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stageOne = primaryStage;
        volumeSlider = new Slider();
        mView = new MediaView();
        // default value for the music volume.
        currentVolume = -1;
        // default song range.
        songChooser = 5;
        // There are 21 songs. Begins from index of 0 to 20.
        // 0 - 7: Normal, 8 - 15 war crusade songs, 16 - 20 Dark era songs.
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
        stageOne.setTitle("Prince Game ver 3.0.2.b");
        stageOne.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
