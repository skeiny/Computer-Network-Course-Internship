package client;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public static SimpleObjectProperty<Integer> update = new SimpleObjectProperty<>(0);
    @FXML
    private VBox members;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Control init");
        update.addListener(((observable, oldValue, newValue) -> {
//            Platform.runLater(this::update);
            update(oldValue, newValue);
        }));

        System.out.println("Control init end");
    }

    private void update(Integer oldValue, Integer newValue) {
        if (newValue == oldValue + 1) {
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
            }
        } else if (newValue == oldValue + 2) {
            members.getChildren().clear();
            //前提是已经将新上线用户加入ClientThread.members
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
            }
        } else if (newValue == oldValue - 2) {
            members.getChildren().clear();
            //前提是已经将下线用户移除ClientThread.members
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
            }
        } else if (newValue == oldValue - 1) {
            for (Member member : ClientThread.members) {
                if (member.getMiss()) //判断是否有未接信息
                    ;//有的话出现小红点或者变色
            }
        }
        /*
        System.out.println("update is called");
        if (ChatController.InOrOff == 1) {
            System.out.println("上线:" + update.getValue().getName().getText());
            members.getChildren().add(update.getValue());
        } else if (ChatController.InOrOff == 0) {
            System.out.println("下线:" + update.getValue().getName().getText());
            for (Node :
            ) {

            }
        }*/
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
