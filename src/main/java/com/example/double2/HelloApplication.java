package com.example.double2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HelloApplication extends Application {
    Stage primaryStage = new Stage();
    File selectedFile;
    ArrayList<String> question = new ArrayList<>();
    ArrayList<Integer> shuffle = new ArrayList<>();
    int pin = 0;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage.setScene(new Scene(getBackground(), 800, 600));
        primaryStage.show();

    }
    private StackPane getBackground() throws FileNotFoundException {
        music();
        StackPane stackPane = new StackPane();
        Image image = new Image(new FileInputStream("src/resources2/img/background.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);
        Button chFile = new Button("Choose a file");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src"));
        chFile.setOnAction(e -> {
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            Quiz quiz = new Quiz();
            question.addAll(quiz.getQuestions(selectedFile.toString()));

            shuffle = new ArrayList<>();
            for(int i = 0; i < question.size(); i++){
                shuffle.add(i);
            }
            boolean shuffleOrder = false;
            if(shuffleOrder == true){
                Collections.shuffle(shuffle);
            }
            try {
                primaryStage.setScene(new Scene(Pin(), 800, 600));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        stackPane.getChildren().addAll(imageView, chFile);
        return stackPane;
    }
    public static void music(){
        Media media = new Media(new File("src/resources2/kahoot_music.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public BorderPane Pin() throws IOException {
        pin = (int)(Math.random() * 899999) + 100000;
        BorderPane borderPane = new BorderPane();
        Image image = new Image(new FileInputStream("src/Ka.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(700);
        imageView.setFitHeight(500);
        borderPane.setCenter(imageView);

        Label lbl = new Label("Game PIN: " + pin);
        lbl.setFont(new Font("Times New Roman",44));
        borderPane.setTop(lbl);
        Button button = new Button("Next");
        /*button.setOnAction(e-> {
            try {
                I();
                primaryStage.setScene(new Scene(start(), 800, 600));
                primaryStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });*/
        borderPane.setRight(new StackPane(button));
        return borderPane;
    }


    public static void main(String[] args) {
        launch();
    }
}