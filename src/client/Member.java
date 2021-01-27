package client;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Member extends HBox {
    private ImageView imageView;
    private Label name = new Label();
    private Circle redCircle = new Circle(5,Paint.valueOf("red"));
    private boolean isMiss = false;//是否有未接信息
    private String path;
    private List<String> chatRecord = new ArrayList();

    Member(String name) {
        this.setPrefHeight(50);
        this.name.setFont(new Font(20));
        this.name.setPadding(new Insets(10,10,0,10));
        /*
        生成头像
         */
        if (name.equals("聊天大厅")){
            path = "all.png";
        }else {
            path = (int)(Math.random()*5)+1+".png";
        }
        imageView = new ImageView(new Image("file:" + new File("src/picture/"+path),45,45,true,true));
        redCircle.setVisible(false);
        this.name.setText(name);
        System.out.println(this.name.getText());
        this.getChildren().addAll(this.imageView,this.name,this.redCircle);
        this.setStyle("-fx-background-color: WHITE");
        this.setOnMouseClicked(event -> {/*
            for (Member member:ClientThread.members){
                if (!member.equals(this)){
                    member.setPressed(false);
                    member.setStyle("-fx-background-color: WHITE");
                }
            }
            this.setPressed(true);
            this.setStyle("-fx-background-color: gray");*/
            redCircle.setVisible(false);
            System.out.println("click member " + name);
            ChatController.member = this;
            ChatController.chatUpdate.setValue(ChatController.chatUpdate.getValue()+1);
        });
        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: #acacac");
        });
        this.setOnMouseExited(event -> {
            if (!this.isPressed()){
                this.setStyle("-fx-background-color: WHITE");
            }
        });
    }

    Member(String name, Image image) {
        this.name.setText(name);
        imageView = new ImageView(image);
    }

    public ImageView getImageView() {
        return imageView;
    }
    public Label getName() {
        return name;
    }
    public boolean getMiss() { return isMiss; }
    public List<String> getChatRecord() {
        return chatRecord;
    }

    public void setName(Label name) {
        this.name = name;
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    public void setMiss(boolean miss) {
        isMiss = miss;
    }

    public Circle getRedCircle(){
        return this.redCircle;
    }
    public String getPath(){
        return path;
    }
}
