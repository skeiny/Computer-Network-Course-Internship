package client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Member extends HBox {
    private ImageView imageView;
    private Label name;
    private boolean isMiss = false;//是否有未接信息
    /*
    格式：1(0),记录
    1表示对方发送，0表示自己发送
     */
    private List<String> chatRecord = new ArrayList();

    Member(String name) {
        this.name.setText(name);
        System.out.println(this.name.getText());
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
