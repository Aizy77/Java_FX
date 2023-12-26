package com.example.project3;

import com.example.double2.Quiz;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import static javafx.scene.text.FontWeight.*;

public class IT extends Application {
    int pin = 0;
    private Stage primaryStage;
    File selectedFile;
    Scene scene;
    String p = "";
    int ind = 0;
    int all = 0;
    int indn = 0;
    ArrayList<String> names = new ArrayList<>();
    ArrayList <String> question = new ArrayList();
    ArrayList<Integer> shuffle = new ArrayList<>();
    ArrayList answers = new ArrayList();
    ArrayList yourAnswers = new ArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = new Stage();

        pin = (int)(Math.random() * 899999) + 100000;
        if(p.length() == 0)p += pin;

        System.out.println(pin);

        Scanner in = new Scanner(System.in);
        stage.setTitle("Quiz");
        boolean shuffleOrder = false;
        shuffle = new ArrayList<>();
        for(int i = 0; i < question.size(); i++){
            shuffle.add(i);
        }
        if(shuffleOrder == true){
            Collections.shuffle(shuffle);
        }

        scene = new Scene(getBackground(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }public ServerSocket I() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket socket = serverSocket.accept();
        System.out.println("Connected");
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));

        String name = in.readLine();
        names.add(name);
        System.out.println(name);


        OutputStream out = socket.getOutputStream();
        PrintWriter pr = new PrintWriter(out, true);

        pr.println(p);
/*
        String line = "";

        while (!line.equals("Over"))
        {
            try
            {
                line = in.readUTF();
                System.out.println(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }*/

        //serverSocket.close();
        //in.close();
        //serverSocket.close();


        return serverSocket;
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
        button.setOnAction(e-> {
            try {
                I();
                primaryStage.setScene(new Scene(start(), 800, 600));
                primaryStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        borderPane.setRight(new StackPane(button));
        return borderPane;
    }
    public BorderPane start(){
        BorderPane borderPane = new BorderPane();

        Button start = new Button("Start");
        start.setMinWidth(80);
        start.setMinHeight(40);
        start.setOnAction(e -> {
            try {
                primaryStage.setScene(new Scene(getQuestion(0), 800, 600));
                primaryStage.show();    
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        Label lbl = new Label("Players:");
        lbl.setFont(new Font("Chiller",32));
        lbl.setTextFill(Color.WHITE);

        Label kahoot = new Label("Kahoot!");
        kahoot.setFont(new Font("Chiller", 64));
        kahoot.setTextFill(Color.WHITE);

        Circle circle = new Circle(40);
        circle.setFill(Color.MEDIUMPURPLE);
        StackPane stackPane = new StackPane();
        Label players = new Label("   " + names.size() + "\nPlayers");
        players.setTextFill(Color.WHITE);
        players.setFont(new Font("Chiller", 22));
        stackPane.getChildren().addAll(circle, players);

        String t = names.toString().replaceAll("\\[", "");
        Label name = new Label(t.replaceAll("]",""));
        name.setTextFill(Color.WHITE);
        name.setFont(new Font("Chiller", 32));

        BorderPane pl = new BorderPane();
        pl.setTop(new StackPane(lbl));
        pl.setCenter(name);

        borderPane.setTop(new StackPane(pl));
        borderPane.setRight(new StackPane(start));
        borderPane.setLeft(stackPane);
        borderPane.setCenter(kahoot);
        borderPane.setStyle("-fx-background-color: purple");
        return borderPane;
    }
    public BorderPane getQuestion(int ind) throws FileNotFoundException {
        Label lbl = new Label(ind + 1 + ") " + question.get(shuffle.get(ind)));
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        TextField textField = new TextField();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new StackPane(lbl));


        Button br = new Button("Next");

        br.setMinHeight(50.0);
        br.setMinWidth(100.0);

        if (ind == question.size() - 1)
            br.setText("Skip");
        br.setOnAction(exc -> {
            try {
                primaryStage.setScene(new Scene(finish(),800,600));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        if(ind < question.size() - 1) {
            br.setOnAction(exc -> {
                try {
                    primaryStage.setScene(new Scene(getQuestion(ind + 1), 800, 600));
                    if(question.get(shuffle.get(ind)).indexOf("______") > 0){
                        yourAnswers.add(shuffle.get(ind), textField.getText());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

        Image image = new Image(new FileInputStream("src/resources2/img/logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(300);

        Image image1 = new Image(new FileInputStream("src/resources2/img/fillin.png"));
        ImageView fill = new ImageView(image1);
        fill.setFitHeight(200);
        fill.setFitWidth(300);

        textField.setMinWidth(400);

        if(question.get(shuffle.get(ind)).indexOf("______") > 0){
            GridPane t = new GridPane();
            t.setPadding(new Insets(0,0,140,200));
            t.getChildren().add(textField);
            borderPane.setCenter(fill);
            borderPane.setBottom(t);
        }else{
            borderPane.setCenter(imageView);
            FlowPane flowPane = new FlowPane();
            RadioButton rbtR = new RadioButton();
            RadioButton rbtB = new RadioButton();
            RadioButton rbtO = new RadioButton();
            RadioButton rbtG = new RadioButton();

            ArrayList<String> ans = new ArrayList();
            Quiz quiz = new Quiz();
            ans.addAll(quiz.getVariant(selectedFile.toString()));
            if(indn == ans.size()){
                indn -= 4;
            }
            if(ind == 0)indn = 0;
            rbtR.setText(ans.get(indn++));
            rbtB.setText(ans.get(indn++));
            rbtO.setText(ans.get(indn++));
            rbtG.setText(ans.get(indn++));

            ToggleGroup group = new ToggleGroup();

            rbtR.setStyle("-fx-background-color: red");
            rbtR.setPrefSize(390, 80);
            rbtR.setToggleGroup(group);
            rbtR.setTextFill(Color.WHITE);
            GridPane R = new GridPane();
            R.getChildren().addAll(rbtR);
            R.setPadding(new Insets(5, 5,5,5));

            rbtB.setStyle("-fx-background-color: blue");
            rbtB.setPrefSize(390, 80);
            rbtB.setToggleGroup(group);
            rbtB.setTextFill(Color.WHITE);
            GridPane B = new GridPane();
            B.getChildren().addAll(rbtB);
            B.setPadding(new Insets(5,5,5,5));

            rbtO.setStyle("-fx-background-color: orange");
            rbtO.setPrefSize(390, 80);
            rbtO.setToggleGroup(group);
            rbtO.setTextFill(Color.WHITE);
            GridPane O = new GridPane();
            O.getChildren().addAll(rbtO);
            O.setPadding(new Insets(5,5,5,5));

            rbtG.setStyle("-fx-background-color: green");
            rbtG.setPrefSize(390, 80);
            rbtG.setToggleGroup(group);
            rbtG.setTextFill(Color.WHITE);
            GridPane G = new GridPane();
            G.getChildren().add(rbtG);
            G.setPadding(new Insets(5,5,5,5));

            group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                    RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle();
                    yourAnswers.add(shuffle.get(ind) , chk.getText());
                }
            });

            flowPane.getChildren().addAll(R, B, O, G);

            borderPane.setBottom(flowPane);
        }
        borderPane.setRight(br);
        return borderPane;
    }
    public BorderPane finish() throws FileNotFoundException {
        int correct = 0;
        Quiz quiz = new Quiz();
        answers.addAll(quiz.getAnswers(selectedFile.toString()));
        for(int i = 0; i < question.size(); i++) {
            if (yourAnswers.get(shuffle.get(i)).toString().equals(answers.get(shuffle.get(i)))){
                correct++;
            }
        }
        BorderPane borderPane = new BorderPane();
        Image image = new Image(new FileInputStream("src/resources2/img/result.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
        all = question.size();
        Double p = ((double)correct / question.size()) * 100.0;
        Label lbl = new Label("Your Result:\n");

        lbl.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.ITALIC, 32 ));

        Label lbl2 = new Label("\n" +  p + "%\n" + "\n Number of correct answers: " + correct + "/"
                + question.size());

        lbl2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC,22));

        Button SA = new Button("Show answers");
        Button CT = new Button("Close Test");
        SA.setTextFill(Color.WHITE);
        CT.setTextFill(Color.WHITE);

        SA.setStyle("-fx-background-color: blue");
        CT.setStyle("-fx-background-color: red");

        SA.setMinHeight(60);
        SA.setMinWidth(400);

        CT.setMinHeight(60);
        CT.setMinWidth(400);

        BorderPane bp = new BorderPane();
        bp.setTop(new StackPane(lbl));
        bp.setBottom(new StackPane(lbl2));

        BorderPane bp2 = new BorderPane();
        bp2.setTop(new StackPane(SA));
        bp2.setBottom(new StackPane(CT));

        borderPane.setTop(new StackPane(bp));
        borderPane.setCenter(new StackPane(bp2));
        borderPane.setBottom(new StackPane(imageView));

        return borderPane;
    }
}
