package client;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public static SimpleObjectProperty<Integer> update = new SimpleObjectProperty<>(0);
    @FXML
    private VBox members;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Control init");
        update.addListener(((observable, oldValue, newValue) -> {
            Platform.runLater(this::update);
        }));
        System.out.println("Control init end");
    }

    private void update(){
        /*System.out.println("update is called");
        for (int i=0;i<UpdateThread.allUsersName.size();i++){
            System.out.println(111);
            Member member = new Member(UpdateThread.allUsersName.get(i));
            member.setOnMousePressed(event -> {
                System.out.println(member.getLabelName());
            });
            members.getChildren().add(member);
        }
        System.out.println("update call end");*/
    }
}
