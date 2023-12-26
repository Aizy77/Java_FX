package com.example.double2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Application {
    int indn = 0;
    int pin = 0;
    int sec = 30;
    DataOutputStream to;
    DataInputStream from;
    ServerSocket server;
    Stage primaryStage;
    String nickname;
    File selectedFile;
    ArrayList<String> question = new ArrayList();
    ArrayList<Integer> shuffle = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> Answers = new ArrayList<>();
    ArrayList<String> CAnswers = new ArrayList<>();
    ArrayList<String> Cli = new ArrayList<>();
    int propusk = 0;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setScene(new Scene(getBackground(), 800, 600));
        primaryStage.show();
        new Thread(() -> {
            try {
                server = new ServerSocket(8000);
                while (true) {
                    try {
                        Socket socket = server.accept();
                        new Thread(() -> {
                            try {
                                to = new DataOutputStream(socket.getOutputStream());
                                from = new DataInputStream(socket.getInputStream());
                                while (true) {
                                    to.writeUTF(String.valueOf(pin));
                                    to.writeUTF(String.valueOf(question.size()));
                                    nickname = from.readUTF();
                                    names.add(nickname);
                                    to.writeUTF(CAnswers.toString());
                                    System.out.println(propusk);
                                    if(propusk > 0) {
                                        String ans = from.readUTF();
                                        Cli.add(ans);
                                        System.out.println(Cli);
                                    }
                                }

                            } catch (IOException e) {
                                System.out.println("N");
                            }
                        }).start();
                    } catch (IOException e) {
                        System.out.println("N");
                    }
                }
            } catch (IOException e) {
                System.out.println("N");
            }
        }).start();
    }
    public BorderPane PIN() throws IOException {
        primaryStage.setTitle("PIN");
        BorderPane borderPane = new BorderPane();
        pin = (int) (Math.random() * 899999) + 100000;
        Label pinc = new Label("Game PIN: " + pin);
        pinc.setMinWidth(800);
        pinc.setStyle("-fx-background-color: white");
        pinc.setFont(new Font("Times New Roman", 44));

        borderPane.setStyle("-fx-background-color: #9933ff");
        Label lbl = new Label("Kahoot!");
        lbl.setFont(new Font("Chiller", 84));
        lbl.setTextFill(Color.WHITE);
        Button next = new Button("Next");
        next.setOnAction(e -> {
            propusk++;
            try {
                primaryStage.setScene(new Scene(Nickname(), 800, 600));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        borderPane.setCenter(lbl);
        borderPane.setRight(new StackPane(next));
        borderPane.setTop(new StackPane(pinc));
        return borderPane;
    }
    public BorderPane Nickname() throws IOException {
        primaryStage.setTitle("Start");
        BorderPane borderPane = new BorderPane();
        String t = names.toString().replaceAll("\\[", "");

        Label nicks = new Label("Players\n\n" + t.replaceAll("]", ""));
        nicks.setWrapText(true);
        nicks.setTextFill(Color.WHITE);
        nicks.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 32));

        Button start = new Button("Start");
        start.setMinHeight(25);
        start.setMinWidth(70);
        start.setOnAction(e -> {
            propusk++;
            try {
                primaryStage.setScene(new Scene(getQuestion(0), 800, 600));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Label players = new Label("    " + names.size() + "\nPlayers");
        players.setTextFill(Color.WHITE);
        players.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));

        Label ka = new Label("Kahoot");
        ka.setTextFill(Color.WHITE);
        ka.setFont(Font.font("New Times Roman", FontWeight.BOLD, FontPosture.ITALIC, 56));

        borderPane.setStyle("-fx-background-color: #b036b0");
        borderPane.setLeft(new StackPane(players));
        borderPane.setTop(new StackPane(nicks));
        borderPane.setRight(new StackPane(start));
        borderPane.setCenter(ka);
        return borderPane;
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
            for (int i = 0; i < question.size(); i++) {
                shuffle.add(i);
            }
            boolean shuffleOrder = false;
            if (shuffleOrder == true) {
                Collections.shuffle(shuffle);
            }
            try {
                primaryStage.setScene(new Scene(PIN(), 800, 600));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stackPane.getChildren().addAll(imageView, chFile);

        return stackPane;
    }
    public static void music() {
        Media media = new Media(new File("src/resources2/kahoot_music.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    int cind = 0;
    public synchronized BorderPane getQuestion(int ind) throws IOException {
        sec = 30;
        Timeline timeline = new Timeline();
        Label lblt = new Label();
        StackPane stackPane = new StackPane();
        timeline.setCycleCount(Timeline.INDEFINITE);
        BorderPane borderPane = new BorderPane();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {
            @Override
            public void handle(Event event) {
                sec--;
                lblt.setText(String.valueOf(sec));
                if(sec <= 0){
                    timeline.stop();
                    if(ind == question.size() - 1){
                        Button finish = new Button("finish");
                        finish.setOnAction(e -> {
                            primaryStage.setTitle("Result");
                            try {
                                primaryStage.setScene(new Scene(finish(), 800, 600));
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            primaryStage.show();
                        });
                        borderPane.setRight(new StackPane(finish));
                    }else {
                        try {
                            primaryStage.setScene(new Scene(getQuestion(ind + 1), 800, 600));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }));
        timeline.playFromStart();
        lblt.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 32));
        stackPane.getChildren().addAll(lblt);

        Label lbl = new Label(ind + 1 + ") " + question.get(shuffle.get(ind)));
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 22));
        lbl.setWrapText(true);

        TextField textField = new TextField();
        borderPane.setTop(new StackPane(lbl));

        Image image = new Image(new FileInputStream("src/resources2/img/logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(300);

        Image image1 = new Image(new FileInputStream("src/resources2/img/fillin.png"));
        ImageView fill = new ImageView(image1);
        fill.setFitHeight(200);
        fill.setFitWidth(300);

        textField.setMinWidth(400);
        ArrayList<String> ans = new ArrayList();
        Quiz quiz = new Quiz();
        ans.addAll(quiz.getVariant(selectedFile.toString()));

        Answers.addAll(quiz.getAnswers());
        if (question.get(shuffle.get(ind)).indexOf("{true, false}") > 0) {
            Button rbtR = new Button();
            rbtR.setStyle("-fx-background-color: red");
            rbtR.setPrefSize(390, 80);
            rbtR.setTextFill(Color.WHITE);
            GridPane R = new GridPane();
            R.getChildren().addAll(rbtR);
            R.setPadding(new Insets(5,5,5,5));

            Button rbtG = new Button();
            rbtG.setStyle("-fx-background-color: green");
            rbtG.setPrefSize(390, 80);
            rbtG.setTextFill(Color.WHITE);
            GridPane G = new GridPane();
            G.getChildren().add(rbtG);
            G.setPadding(new Insets(5, 5, 5, 5));

            rbtG.setText(ans.get(indn++));
            if(ans.get(cind++).equals(Answers.get(ind))){
                CAnswers.add("Green");
            }
            rbtR.setText(ans.get(indn++));
            if(ans.get(cind++).equals(Answers.get(ind))){
                CAnswers.add("Red");
            }

            borderPane.setCenter(fill);
            FlowPane flowPane = new FlowPane();
            flowPane.getChildren().addAll(G, R);
            borderPane.setBottom(flowPane);
        } else {
            borderPane.setCenter(imageView);
            FlowPane flowPane = new FlowPane();
            Button rbtR = new Button();
            Button rbtB = new Button();
            Button rbtO = new Button();
            Button rbtG = new Button();

            rbtR.setText(ans.get(indn++));
            if(ans.get(cind++).equals(Answers.get(ind))){
                CAnswers.add("Red");
            }
            rbtB.setText(ans.get(indn++));
            if(ans.get(cind++).equals(Answers.get(ind))){
                CAnswers.add("Blue");
            }
            rbtO.setText(ans.get(indn++));
            if(ans.get(cind++).equals(Answers.get(ind))){
                CAnswers.add("Orange");
            }
            rbtG.setText(ans.get(indn++));
            if(ans.get(cind++).equals(Answers.get(ind))){
                CAnswers.add("Green");
            }

            rbtR.setStyle("-fx-background-color: red");
            rbtR.setPrefSize(390, 80);
            rbtR.setTextFill(Color.WHITE);
            GridPane R = new GridPane();
            R.getChildren().addAll(rbtR);
            R.setPadding(new Insets(5, 5, 5, 5));

            rbtB.setStyle("-fx-background-color: blue");
            rbtB.setPrefSize(390, 80);
            rbtB.setTextFill(Color.WHITE);
            GridPane B = new GridPane();
            B.getChildren().addAll(rbtB);
            B.setPadding(new Insets(5, 5, 5, 5));

            rbtO.setStyle("-fx-background-color: orange");
            rbtO.setPrefSize(390, 80);
            rbtO.setTextFill(Color.WHITE);
            GridPane O = new GridPane();
            O.getChildren().addAll(rbtO);
            O.setPadding(new Insets(5, 5, 5, 5));

            rbtG.setStyle("-fx-background-color: green");
            rbtG.setPrefSize(390, 80);
            rbtG.setTextFill(Color.WHITE);
            GridPane G = new GridPane();
            G.getChildren().add(rbtG);
            G.setPadding(new Insets(5, 5, 5, 5));

            flowPane.getChildren().addAll(R, B, O, G);

            borderPane.setBottom(flowPane);
        }
        borderPane.setLeft(new StackPane(stackPane));
        return borderPane;
    }
    public BorderPane finish() throws FileNotFoundException {
        BorderPane borderPane = new BorderPane();
        ArrayList<String> sort = new ArrayList<>();
        int na = 0;
        int i = 0;
        while(true){
            if(Cli.get(i).indexOf(names.get(na)) >= 0){
                sort.add(Cli.get(i));
                if(sort.size() % CAnswers.size() == 0){
                    na++;
                    i = 0;
                }
            }
            i++;
            if(Cli.size() == sort.size())break;
        }
        na = 0;
        double correct = 0;
        String n1 = "";
        String n2 = "";
        String n3 = "";
        String n = "";

        int first = 0;
        int second = 0;
        int third = 0;
        for(int j = 0; j < sort.size(); j++){
            if(sort.get(j).indexOf(CAnswers.get(na)) > 0){
                n = sort.get(j).substring(0, sort.get(j).indexOf(CAnswers.get(na)) - 2);
                correct += (Math.random() * 2) * 500;
                na++;
            }else {
                na++;
            }
            if(na == CAnswers.size()) {
                na = 0;
                if(second >= first && second >= third) {
                    first = second;
                    n1 = n2;
                }
                if(third >= first && third >= second){
                    first = third;
                    n1 = n3;
                }
                if(third >= second){
                    second = third;
                    n2 = n3;
                }
                if(correct >= first) {
                    third = second;
                    n3 = n2;
                    second = first;
                    n2 = n1;
                    first = (int) correct;
                    n1 = n;
                }
            }
        }
        Label lbl1 = new Label(n1 + "\n" + first);
        lbl1.setWrapText(true);
        lbl1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 36));
        lbl1.setTextFill(Color.WHITE);

        Label lbl2 = new Label(n2 + "\n" + second);
        lbl2.setWrapText(true);
        lbl2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 36));
        lbl2.setTextFill(Color.WHITE);

        Label lbl3 = new Label("\n\n" + n3 + "\n" + third);
        lbl3.setWrapText(true);
        lbl3.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 36));
        lbl3.setTextFill(Color.WHITE);

        borderPane.setTop(new StackPane(lbl1));
        borderPane.setLeft(new StackPane(lbl2));
        borderPane.setRight(new StackPane(lbl3));

        Image image = new Image(new FileInputStream("src//resources2//img//result.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);

        borderPane.setStyle("-fx-background-color: #9933ff");

        borderPane.setCenter(imageView);
        return borderPane;
    }
    public static void main(String[] args) throws IOException {
        launch();
    }
}
