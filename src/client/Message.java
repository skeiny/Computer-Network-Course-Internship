package client;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @Author: Sky
 * @Date: 2021/1/26 19:02
 */
public class Message extends HBox {
    public Label message = new Label();
    public Label name = new Label();

    public Message(String name,String message){
        this.name.setText(name+":");
        this.message.setText(message);
        this.getChildren().addAll(this.name,this.message);
    }
}
