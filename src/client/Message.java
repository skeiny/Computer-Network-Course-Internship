package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.File;

/**
 * @Author: Sky
 * @Date: 2021/1/26 19:02
 */
public class Message extends HBox {
    public Label message = new Label();
    public Label name = new Label();

    public Message(String name,String message){
        this.setPrefHeight(50);
        this.message.setFont(new Font(20));
        this.name.setFont(new Font(20));
        this.message.setPadding(new Insets(10,0,0,0));
        this.name.setPadding(new Insets(10,0,0,0));
        ImageView imageView = null;
        if (name.equals(ClientThread.myName)){
            imageView = new ImageView(new Image("file:" + new File("src/picture/1.png"),45,45,true,true));
        }

        for (int i=0;i<ClientThread.members.size();i++){
            if (ClientThread.members.get(i).getName().getText().equals(name)){
                imageView = new ImageView(new Image("file:" + new File("src/picture/"+ClientThread.members.get(i).getPath()),45,45,true,true));
            }
        }

        this.message.setText(message);
        if (name.equals(ClientThread.myName)){
            this.setAlignment(Pos.CENTER_RIGHT);
            //this.message.setPadding(new Insets(10,0,0,350));
            this.name.setText(":"+name);
            this.getChildren().addAll(this.message,this.name,imageView);
        }else {
            this.name.setText(name+":");
            this.getChildren().addAll(imageView,this.name,this.message);
        }
    }
}
