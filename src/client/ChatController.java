package client;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public static SimpleObjectProperty<Integer> update = new SimpleObjectProperty<>(0);

    public static SimpleObjectProperty<Integer> chatUpdate = new SimpleObjectProperty<>(0);
    public static Member member = ClientThread.members.get(0);

    public static Button send2;

    private Label chatTitle;
    @FXML
    private VBox members;

    @FXML
    private VBox chat;

    @FXML
    private TextArea sendBox;

    @FXML
    private Button send;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chat.getChildren().clear();
        chatTitle = new Label("聊天大厅");
        chatTitle.setPadding(new Insets(5,0,5,250));
        chatTitle.setFont(new Font(20));
        chat.getChildren().add(chatTitle);
        update.addListener(((observable, oldValue, newValue) -> {
            Platform.runLater(()-> updateMember(oldValue,newValue));
        }));
        chatUpdate.addListener(((observable, oldValue, newValue) -> {
            Platform.runLater(()-> updateChat());
        }));
        send2 = send;
        sendBox.setFont(new Font(20));
    }

    private void updateChat(){
        chat.getChildren().clear();
        chatTitle = new Label(member.getName().getText().equals("聊天大厅")?"聊天大厅":"与"+member.getName().getText()+"的聊天");
        chatTitle.setFont(new Font(20));
        chatTitle.setPadding(new Insets(5,0,5,250));
        chat.getChildren().add(chatTitle);
        for (String each:member.getChatRecord()){
            String[] nameAndMessage = each.split(",");
            chat.getChildren().add(new Message(nameAndMessage[0],nameAndMessage[1]));
        }
    }

    private void updateMember(Integer oldValue, Integer newValue) {
        if (newValue == oldValue + 1) {
            System.out.println("首次获取用户信息");
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
            }
        } else if (newValue == oldValue + 2) {
            System.out.println("有新用户上线啦");
            members.getChildren().clear();
            //前提是已经将新上线用户加入ClientThread.members
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
            }
        } else if (newValue == oldValue - 2) {
            System.out.println("有用户下线啦");
            members.getChildren().clear();
            //前提是已经将下线用户移除ClientThread.members
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
            }
        } else if (newValue == oldValue - 1) {
            System.out.println("我收到消息啦");
            Platform.runLater(()-> updateChat());
            members.getChildren().clear();
            for (Member member : ClientThread.members) {
                members.getChildren().add(member);
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

    @FXML
    private void send(){
        if (sendBox.getText().equals(""))return;
        String rName = member.getName().getText();
        if (rName.equals("聊天大厅")){
            rName = "all";
        }
        ClientThread.send2Server("chat,"+ rName + "," + sendBox.getText());
        member.getChatRecord().add(ClientThread.myName +","+sendBox.getText());
        Platform.runLater(()-> updateChat());
        sendBox.setText("");
    }
}
