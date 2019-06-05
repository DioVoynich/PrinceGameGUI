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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
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
    private Scene sceneStart, scenePlay, sceneOption, sceneCredit, previous;
    private createAudio songs;
    private makeImage picture;
    private Rectangle2D primaryScreenBounds;
    private MediaPlayer musicPlay;
    private MediaView mView;
    private Slider volumeSlider;
    private double currentVolume;
    private int songChooser;
    private GameManager gameControl;
    private Group princeAddon;
    private Group lastResult;
    private Random rand;
    private Decision[] options;
    private HashMap<String, Integer> route;

    // This method will accept a random object as a parameter.
    private void playSong() {
        // int rVal = rand.nextInt(songs.getSize() - songChooser) + songChooser;
        int rVal = rand.nextInt(19) + 3;
        String path = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\Music\\"
                + songs.getSong(rVal) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        musicPlay = new MediaPlayer(media);
        mView.setMediaPlayer(musicPlay);
        musicPlay.setVolume(currentVolume);
        musicPlay.play();

        // Recursively playing the music.
        musicPlay.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playSong();
            }
        });
    }

    // This is the main part of the program. It will scan files from
    // prince game manager files.
    private Scene createPlayingScene() {
        if (previous == sceneStart) {
            playSong();
        }

        Image image = new Image(new File("E:\\All Computer Science Materials\\" +
                "Java 240 Project\\PrinceFX\\image\\" + picture.getImage(6) + ".png").toURI().toString());
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
        Button options = new Button("Options");

        // This will add an option button.
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // I know this is annoying, but you have to make sure handling the exception case.
                try {
                    musicPlay.stop();
                    gameControl.newGame();
                    sceneStart = createStartScene();
                    musicPlay.setVolume(currentVolume);
                    stageOne.setScene(sceneStart);
                } catch (Exception e) {
                    System.out.println("Something is wrong");
                }
            }
        });

        // This will lead to the option page.
        options.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                previous = scenePlay;
                sceneOption = createOptionScene();
                musicPlay.setVolume(currentVolume);
                stageOne.setScene(sceneOption);
            }
        });

        // Button for first page.
        HBox layoutPlaying = new HBox(10);
        layoutPlaying.getChildren().addAll(labels, options, goBack);
        layoutPlaying.setTranslateX(70);
        layoutPlaying.setTranslateY(750);


        // Depending how many options left.
        buildGame();

        // Store them buttons and images together.
        Group playGroup = new Group(imageView, layoutPlaying, princeAddon);
        return new Scene(playGroup, 200, 200);
    }

    // Playing Game recursively.
    private void buildGame() {
        if (!gameControl.isGameOver()) {
            options = gameControl.currentEvent.getDecs();
        } else {
            options = null;
        }
        princeAddon = printStatus(gameControl.currentEvent);
    }

    // This is just for my own convenience.
    private Group createAllUnits() {
        // Name and Age
        Label name = new Label("Prince Harry");
        Label age = new Label("Age: " + gameControl.getStats().get("AGE"));
        VBox identity = new VBox(10);
        identity.getChildren().addAll(name, age);
        identity.setStyle("-fx-font: 30 arial;");
        identity.setTranslateX(100);
        identity.setTranslateY(120);

        // Year
        Label year = new Label("Year: " + gameControl.getStats().get("YEAR") + " AD");
        year.setStyle("-fx-font: 24 arial;");
        year.setTranslateX(1240);
        year.setTranslateY(120);

        // Personal Status
        Label wealth = new Label("Wealth: " + gameControl.getStats().get("WLTH"));
        Label army = new Label("Army: " + gameControl.getStats().get("ARMY"));
        Label health = new Label("Health: " + gameControl.getStats().get("HLTH"));
        VBox personalStatus = new VBox(80);
        personalStatus.getChildren().addAll(wealth, army, health);
        personalStatus.setStyle("-fx-font: 36 arial;");
        personalStatus.setTranslateX(70);
        personalStatus.setTranslateY(330);

        // Other Forces: clergy, nobility, commoners
        HBox allForces = new HBox(70);
        // Clergy status
        Label clergy = new Label("Clergy: ");
        clergy.setStyle("-fx-font: 36 arial;");
        Label clergyLoyalty = new Label("Loyalty: " + gameControl.getStats().get("CLG_LOY"));
        clergyLoyalty.setStyle("-fx-font: 24 arial;");
        Label clergyInfluence = new Label("Influence: " + gameControl.getStats().get("CLG_INF"));
        clergyInfluence.setStyle("-fx-font: 24 arial;");
        VBox clergyGroup = new VBox();
        clergyGroup.getChildren().addAll(clergy,  clergyInfluence, clergyLoyalty);

        // Nobility status
        Label nobility = new Label("Nobility: ");
        nobility.setStyle("-fx-font: 40 arial;");
        Label nobilityLoyalty = new Label("Loyalty: " + gameControl.getStats().get("NOB_LOY"));
        nobilityLoyalty.setStyle("-fx-font: 24 arial;");
        Label nobilityInfluence = new Label("Influence: " + gameControl.getStats().get("NOB_INF"));
        nobilityInfluence.setStyle("-fx-font: 24 arial;");
        VBox nobilityGroup = new VBox();
        nobilityGroup.getChildren().addAll(nobility, nobilityInfluence, nobilityLoyalty);

        // commoners status
        Label commoners = new Label("Commoners: ");
        commoners.setStyle("-fx-font: 40 arial;");
        Label commonersLoyalty = new Label("Loyalty: " + gameControl.getStats().get("COM_LOY"));
        commonersLoyalty.setStyle("-fx-font: 24 arial;");
        Label commonersInfluence = new Label("Influence: " + gameControl.getStats().get("COM_INF"));
        commonersInfluence.setStyle("-fx-font: 24 arial;");
        VBox commonersGroup = new VBox();
        commonersGroup.getChildren().addAll(commoners, commonersInfluence, commonersLoyalty);

        // Final grouping up process.
        allForces.getChildren().addAll(clergyGroup, nobilityGroup, commonersGroup);
        allForces.setTranslateX(450);
        allForces.setTranslateY(110);
        return new Group(identity, year, personalStatus, allForces);
    }

    // This will printout all the game status.
    private Group printStatus(Event event) {
        Group allUnits = createAllUnits();
        // Printing the quest text
        Group story = new Group();
        VBox allOptions = new VBox(10);
        allOptions.setStyle("-fx-font: 20 arial;");
        allOptions.setTranslateX(420);
        allOptions.setTranslateY(510);

        // Storing all the indexes
        route = new HashMap<String, Integer>();

        if(event != null) {
            story.getChildren().add(printParagraph(event.getTxt()));
            for (int i = 0; i < 4; i++) {
                if (event.getDecs()[i] == null) {
                    break;
                } else {
                    String text = ((i + 1) + "." + event.getDecs()[i].getTxt());
                    if (text.length() > 70) {
                        text = text.substring(0, 70) + "-\n" + text.substring(70);
                    }
                    route.put(text, i);

                    Button temp = new Button(text);
                    allOptions.getChildren().add(temp);
                    temp.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Effect[] update = options[route.get(temp.getText().toString())].getEffs();
                            // for loop applying stats updates
                            for (int j = 0; j < update.length; j++) {
                                if (update[j] == null) { //rough handling of null exceptions (change later)
                                    break;
                                } else {
                                    String faction = update[j].getTarget();
                                    int statChange = update[j].getValue();
                                    gameControl.updateValue(faction, statChange);
                                }
                            }
                            if (gameControl.currentEvent.increasesYear()) {
                                gameControl.increaseYear();
                            }
                            gameControl.nextEvent(route.get(temp.getText().toString()));
                            previous = null;
                            scenePlay = createPlayingScene();
                            musicPlay.setVolume(currentVolume);
                            stageOne.setScene(scenePlay);
                        }
                    });
                }
            }
        }

        lastResult = new Group(allUnits, story, allOptions);
        return lastResult;
    }

    // This will print out the current quest.
    private Label printParagraph(String text) {
        String[] para = text.split(" ");
        String quest = "";
        int txtLength = 50;
        for (int i = 0; i < para.length; i++) {
            if(para[i].length() > txtLength) {
                quest += "\n";
                txtLength = 50;
            }
            quest += para[i] + " ";
            txtLength = txtLength - para[i].length();
        }
        Label questLabel = new Label(quest);
        questLabel.setStyle("-fx-font: 24 arial;");
        questLabel.setTranslateX(420);
        questLabel.setTranslateY(300);
        return questLabel;
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

        if (previous == sceneStart) {
            musicPlay = new MediaPlayer(media);
            mView.setMediaPlayer(musicPlay);
            musicPlay.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlay.play();
        }

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

        Button goBack = null;
        if (previous == sceneStart) {
            goBack = new Button("Go Back to Main");
            goBack.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    musicPlay.stop();
                    sceneStart = createStartScene();
                    musicPlay.setVolume(currentVolume);
                    stageOne.setScene(sceneStart);
                }
            });
        } else {
            goBack = new Button("Go Back to Play");
            goBack.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    previous = null;
                    scenePlay= createPlayingScene();
                    musicPlay.setVolume(currentVolume);
                    stageOne.setScene(scenePlay);
                }
            });
        }

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
        Button loading = new Button("Load Your Life");
        Button options = new Button("Options");
        Button credits = new Button("Credits");
        Button quitting = new Button("Quit");

        // This will lead to the playing scene.
        loading.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating the scene inside a event handler.
                previous = sceneStart;
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
                previous = sceneStart;
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
        layoutNewGame.getChildren().addAll(loading, options, credits, quitting, label1);

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
        // There are 22 songs. Begins from index of 0 to 21.
        // 0 - 8: Normal, 9 - 16 war crusade songs, 17 - 21 Dark era songs.
        songs = new createAudio();
        rand = new Random();

        // Calling GameManager
        gameControl = new GameManager();

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
        stageOne.setTitle("Prince Game ver 6.1.5.beta");
        stageOne.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
