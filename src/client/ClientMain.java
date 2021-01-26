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

public class ClientMain extends Application {

    public static Stage stage;
    public static Socket socket = null;
    public static PrintWriter writer = null;
    public static BufferedReader reader = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("登录");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.setOnCloseRequest(event -> {
            if (writer!=null){
                writer.println("offline,"+ClientThread.myName);
                writer.flush();
            }
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
