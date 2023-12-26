package com.example.double2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application{
    int pin = 0;
    int pinY = 0;
    Stage primaryStage;
    DataOutputStream to;
    DataInputStream from;
    Socket socket;
    String name;
    {
        try {
            socket = new Socket("127.0.0.1", 8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = new Stage();
        primaryStage.setScene(new Scene(PIN(), 800,600));
        primaryStage.show();
    }
    int san = 0;
    public StackPane PIN() throws IOException {
        primaryStage.setTitle("Enter PIN");
        pin = Integer.parseInt(From());
        san = Integer.parseInt(From());
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #9933ff");
        TextField ch = new TextField();
        ch.setMaxWidth(150);
        Button enter = new Button("Enter");
        enter.setMinWidth(150);
        enter.setStyle("-fx-background-color: #4d4d4d");
        enter.setTextFill(Color.WHITE);
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(ch, enter);
        vBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(vBox);
        enter.setOnAction(e -> {
            pinY = Integer.parseInt(ch.getText());
            if(pin != pinY){
                ch.setText("Incorrect");
            }else{
                primaryStage.setScene(new Scene(name(), 800, 600));
            }
        });
        return stackPane;
    }
    public void To(String too) throws IOException{
        to = new DataOutputStream(socket.getOutputStream());
        to.writeUTF(too);
    }
    public String From() throws IOException{
        from = new DataInputStream(socket.getInputStream());
        String s = from.readUTF();
        return s;
    }
    public StackPane name(){
        primaryStage.setTitle("Enter name");
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #9933ff");
        TextField ch = new TextField();
        ch.setMaxWidth(150);
        Button enter = new Button("Enter");
        enter.setMinWidth(150);
        enter.setStyle("-fx-background-color: #4d4d4d");
        enter.setTextFill(Color.WHITE);
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(ch, enter);
        vBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(vBox);
        enter.setOnAction(e -> {
            if(ch.getLength() == 0){
                ch.setText("Enter name");
            }else {
                try {
                    name = ch.getText();
                    To(name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                vBox.setVisible(false);
                Label lbl = new Label("Waiting...");
                lbl.setFont(new Font("Times New Roman", 44));
                lbl.setTextFill(Color.WHITE);
                stackPane.getChildren().addAll(lbl);
                primaryStage.setScene(new Scene(btn(), 420, 420));
            }
        });
        return stackPane;
    }
    int sec = 30;
    public StackPane btn(){
        StackPane stackPane2 = new StackPane();
        sec = 30;
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {
            @Override
            public void handle(Event event) {
                sec--;
                if(sec <= 0){
                    timeline.stop();

                    try {
                        To(name + ": 1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    primaryStage.setScene(new Scene(btn(), 420, 420));
                }
            }
        }));
        timeline.playFromStart();

        primaryStage.setTitle("Quiz");
        FlowPane flowPane = new FlowPane();

        Label lbl = new Label("Waiting...");
        lbl.setFont(new Font("Times New Roman", 44));
        lbl.setTextFill(Color.WHITE);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(lbl);

        Button rbtR = new Button();
        Button rbtB = new Button();
        Button rbtO = new Button();
        Button rbtG = new Button();

        rbtR.setStyle("-fx-background-color: red");
        rbtR.setPrefSize(200, 200);
        rbtR.setTextFill(Color.WHITE);
        StackPane R = new StackPane();
        R.getChildren().addAll(rbtR);
        R.setPadding(new Insets(5, 5,5,5));
        Polygon triangle = new Polygon();
        triangle.setFill(Color.WHITE);
        triangle.getPoints().setAll(new Double[]{
                67.5, 5.0,
                40.0, 40.0,
                95.0, 40.0
        });
        R.getChildren().addAll(triangle);

        rbtB.setStyle("-fx-background-color: blue");
        rbtB.setPrefSize(200, 200);
        rbtB.setTextFill(Color.WHITE);
        StackPane B = new StackPane();
        B.getChildren().addAll(rbtB);
        B.setPadding(new Insets(5,5,5,5));
        Rectangle rec = new Rectangle(40,40);
        rec.setFill(Color.WHITE);
        rec.setRotate(45);
        B.getChildren().addAll(rec);

        rbtO.setStyle("-fx-background-color: orange");
        rbtO.setPrefSize(200, 200);
        rbtO.setTextFill(Color.WHITE);
        StackPane O = new StackPane();
        O.getChildren().addAll(rbtO);
        O.setPadding(new Insets(5,5,5,5));
        Circle circle = new Circle(20);
        circle.setFill(Color.WHITE);
        O.getChildren().addAll(circle);

        rbtG.setStyle("-fx-background-color: green");
        rbtG.setPrefSize(200, 200);
        rbtG.setTextFill(Color.WHITE);
        StackPane G = new StackPane();
        G.getChildren().add(rbtG);
        G.setPadding(new Insets(5,5,5,5));
        Rectangle rec2 = new Rectangle(40, 40);
        rec2.setFill(Color.WHITE);
        G.getChildren().addAll(rec2);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(R,B);

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(O,G);

        flowPane.getChildren().addAll(hBox,hBox1);
        rbtR.setOnAction(e -> {
            hBox.setVisible(false);
            hBox1.setVisible(false);
            stackPane2.getChildren().add(stackPane);
            try {
                To(name + ": Red");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        rbtG.setOnAction(e -> {
            hBox.setVisible(false);
            hBox1.setVisible(false);
            stackPane2.getChildren().addAll(stackPane);
            try {
                To(name + ": Green");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        rbtB.setOnAction(e ->{
            hBox.setVisible(false);
            hBox1.setVisible(false);
            stackPane2.getChildren().add(stackPane);
            try {
                To(name + ": Blue");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        rbtO.setOnAction(e -> {
            hBox.setVisible(false);
            hBox1.setVisible(false);
            stackPane2.getChildren().add(stackPane);
            try {
                To(name + ": Orange");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        flowPane.setStyle("-fx-background-color: #9933ff");
        stackPane2.getChildren().addAll(flowPane);
        return stackPane2;
    }
    public static void main(String[] args) throws IOException {
        launch();
    }
}
