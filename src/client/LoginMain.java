package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginMain extends Application {

    public static Stage stage;
    public static Socket socket = null;
    public static PrintWriter writer = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("登录");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.setOnCloseRequest(event -> {
            writer.println("offline");
            writer.flush();
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
