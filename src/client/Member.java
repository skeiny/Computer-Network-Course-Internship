package client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Member extends HBox {
    private ImageView imageView = new ImageView();
    private Label name = new Label();
    private Circle message = new Circle();
    private boolean isMiss = false;//是否有未接信息

    private List<String> chatRecord = new ArrayList();

    Member(String name) {
        this.name.setText(name);
        System.out.println(this.name.getText());
        this.getChildren().addAll(this.imageView,this.name);
        this.setStyle("-fx-background-color: red");
        this.setOnMouseClicked(event -> {
            this.setStyle("-fx-background-color: red");
            System.out.println("click member " + name);
            ChatController.member = this;
            ChatController.chatUpdate.setValue(ChatController.chatUpdate.getValue()+1);
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
}
