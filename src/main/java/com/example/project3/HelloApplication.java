package com.example.project3;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Flow;

public class HelloApplication extends Application {
    int pin = 0;
    int pinY = 0;
    String name;
    private Stage primaryStage = new Stage();
    ArrayList<String> yourAnswers = new ArrayList<>();
    int ind = 0;
    @Override
    public void start(Stage stage) throws Exception{
        Image image = new Image(new FileInputStream("src/kahoot-logo-png-4-Transparent-Images.png"));
        ImageView imageView = new ImageView(image);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView);
        stackPane.getChildren().add(Name());

        primaryStage.setScene(new Scene(stackPane,800,600));
        primaryStage.show();
    }
    public BorderPane Name(){
        BorderPane borderPane = new BorderPane();
        Label nam = new Label("Name");
        TextField na = new TextField();

        BorderPane n = new BorderPane();
        n.setTop(new StackPane(nam));
        n.setCenter(na);
        n.setMaxWidth(200);
        n.setMaxHeight(60);

        Button ent = new Button("Enter");
        ent.setOnAction(e->{
            name = na.getText();
            if(name.length() == 0){
                na.setText("Enter your name");
            }
            if(name.length() != 0){
                try {
                    On();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    primaryStage.setScene(new Scene(PIN(), 800, 600));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                primaryStage.show();
            }
        });
        borderPane.setCenter(n);
        borderPane.setBottom(new StackPane(ent));
        return borderPane;
    }
    public StackPane PIN() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/kahoot-logo-png-4-Transparent-Images.png"));
        ImageView imageView = new ImageView(image);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView);
        BorderPane borderPane = new BorderPane();
        Label pi = new Label("PIN");
        TextField textField = new TextField();

        BorderPane p = new BorderPane();
        p.setCenter(pi);
        p.setBottom(textField);
        p.setMaxHeight(60);
        p.setMaxWidth(200);

        Button ent = new Button("Enter");
        ent.setOnAction(e -> {
            pinY = Integer.parseInt(textField.getText());
            if(pin != pinY){
                textField.setText("Error");
            }
                if(pin == pinY){
                    primaryStage.setScene(new Scene(Buttons(), 420,420));
                }
        });
        borderPane.setCenter(p);
        borderPane.setBottom(new StackPane(ent));
        stackPane.getChildren().addAll(borderPane);
        return stackPane;
    }
    public Socket On() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8000);
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        OutputStream out = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(out, true);
        pw.println(name);

        String line = reader.readLine();
        System.out.println(line);

        pin = Integer.parseInt(line);
        return socket;
    }
    public FlowPane Buttons(){
        FlowPane flowPane = new FlowPane();

        RadioButton rbtR = new RadioButton();
        RadioButton rbtB = new RadioButton();
        RadioButton rbtO = new RadioButton();
        RadioButton rbtG = new RadioButton();

        ToggleGroup group = new ToggleGroup();

        rbtR.setStyle("-fx-background-color: red");
        rbtR.setPrefSize(200, 200);
        rbtR.setToggleGroup(group);
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
        rbtB.setToggleGroup(group);
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
        rbtO.setToggleGroup(group);
        rbtO.setTextFill(Color.WHITE);
        StackPane O = new StackPane();
        O.getChildren().addAll(rbtO);
        O.setPadding(new Insets(5,5,5,5));
        Circle circle = new Circle(20);
        circle.setFill(Color.WHITE);
        O.getChildren().addAll(circle);


        rbtG.setStyle("-fx-background-color: green");
        rbtG.setPrefSize(200, 200);
        rbtG.setToggleGroup(group);
        rbtG.setTextFill(Color.WHITE);
        StackPane G = new StackPane();
        G.getChildren().add(rbtG);
        G.setPadding(new Insets(5,5,5,5));
        Rectangle rec2 = new Rectangle(40, 40);
        rec2.setFill(Color.WHITE);
        G.getChildren().addAll(rec2);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle();
                yourAnswers.add(chk.getText());
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(R,B);

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(O,G);
        flowPane.getChildren().addAll(hBox,hBox1);

        return flowPane;
    }
    public static void main(String[] args) {
        launch();
    }
}