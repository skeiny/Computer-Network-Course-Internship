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

    //在线成员
    public static final ArrayList<Member> members = new ArrayList<>();

    //连接
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static Member member;//改变

    public ClientThread(Socket socket,BufferedReader reader,PrintWriter writer){
        ClientThread.socket = socket;
        ClientThread.reader = reader;
        ClientThread.writer = writer;
    }

    /*
    改变
     */
    public ClientThread(Socket socket,BufferedReader reader,PrintWriter writer,Member member){
        ClientThread.socket = socket;
        ClientThread.reader = reader;
        ClientThread.writer = writer;
        ClientThread.member = member;
    }

    @Override
    public void run() {
        /*
        一直接受消息并根据对应的消息改变界面
         */
        try {
            String receiveMessage;
            while (true){
                receiveMessage = reader.readLine();//有消息过来了

                if(receiveMessage.contains("首次初始化")){
                    //初始化+1
                    String[] userNames = receiveMessage.split(",");
                    for (String name : userNames) {
                        members.add(new Member(name));
                    }
                    ChatController.update.setValue(ChatController.update.getValue() + 1);
                }else if (receiveMessage.contains("用户上线")){
                    //用户上线功能+2
                    String name = receiveMessage;//从中截取name
                    members.add(new Member(name));
                    ChatController.update.setValue(ChatController.update.getValue() + 2);
                }else if(receiveMessage.contains("用户下线")){
                    //下线-2
                    String name = receiveMessage;//从中截取name
                    for (Member member : members) {
                        if (member.getName().equals(name)) {
                            members.remove(member);
                            break;
                        }
                    }
                    ChatController.update.setValue(ChatController.update.getValue() - 2);
                }else if (receiveMessage.contains("收到消息")){
                    //收到消息更新界面-1
                    String name = receiveMessage;//从中截取name
                    String data = receiveMessage;//从中截取data
                    for (Member member : members) {
                        if (member.getName().equals(name)) {
                            member.setMiss(true);
                            break;
                        }
                    }
                    member.getChatRecord().add("1," + data);
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
