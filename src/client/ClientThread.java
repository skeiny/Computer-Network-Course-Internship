package client;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @Author: Sky
 * @Date: 2021/1/25 14:16
 */
public class ClientThread extends Thread{
    public static String myName;
    //在线成员
    public static final ArrayList<Member> members = new ArrayList<>();

    //连接
    public static Socket socket;
    public static BufferedReader reader;
    public static PrintWriter writer;

    public ClientThread(Socket socket,BufferedReader reader,PrintWriter writer,String myName){
        ClientThread.socket = socket;
        ClientThread.reader = reader;
        ClientThread.writer = writer;
        ClientThread.myName = myName;
    }
/*
    *//*
    改变
     *//*
    public ClientThread(Socket socket,BufferedReader reader,PrintWriter writer,Member member){
        ClientThread.socket = socket;
        ClientThread.reader = reader;
        ClientThread.writer = writer;
        ClientThread.member = member;
    }*/

    @Override
    public void run() {
        /*
        一直接受消息并根据对应的消息改变界面
         */
        try {
            String receiveMessage;
            while (true){
                receiveMessage = reader.readLine();//有消息过来了

                if(receiveMessage.contains("currentOnline")){
                    //初始化+1
                    String[] userNames = receiveMessage.split(",");
                    if(userNames.length>1){
                        System.out.println("首次登录加载所有user");
                        for (int i = 1;i<userNames.length;i++){
                            members.add(new Member(userNames[i]));
                        }
                    }
                    ChatController.update.setValue(ChatController.update.getValue() + 1);
                }else if (receiveMessage.contains("newUserOnline")){
                    //用户上线功能+2
                    String name = receiveMessage.split(",")[1];//从中截取name
                    members.add(0,new Member(name));
                    ChatController.update.setValue(ChatController.update.getValue() + 2);
                }else if(receiveMessage.contains("offline")){
                    //下线-2
                    String name = receiveMessage.split(",")[1];//从中截取name
                    for(int i=0;i<members.size();i++){
                        if(members.get(i).getName().getText().equals(name)){
                            members.remove(i);
                            break;
                        }
                    }
                    ChatController.update.setValue(ChatController.update.getValue() - 2);
                }else if (receiveMessage.contains("chat")){
                    //收到消息更新界面-1
                    String name = receiveMessage.split(",")[1];//从中截取name
                    String data = receiveMessage.split(",")[2];//从中截取data
                    for (Member member : members) {
                        if (member.getName().getText().equals(name)) {
                            member.setStyle("-fx-background-color: green");
                            member.getChatRecord().add(name+ "," + data);
                            members.remove(member);
                            members.add(0,member);
                            break;
                        }
                    }
                    ChatController.update.setValue(ChatController.update.getValue() - 1);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 提供发送消息的接口
     * @param content
     */
    public static void send2Server(String content){
        try {
            writer.println(content);
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
