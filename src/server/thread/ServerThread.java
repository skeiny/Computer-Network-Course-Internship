package server.thread;

import server.data.User;
import server.data.UserQueue;
import server.mysql.Dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread extends Thread {
    private static UserQueue users = UserQueue.getUsers();
    private Socket socket;
    private boolean flag = true;
    private Dao dao = new Dao();

    public ServerThread(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        User user;
        String data;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter os = new PrintWriter(socket.getOutputStream())) {
            data = reader.readLine();
            String[] dates = data.split(",");
            System.out.println(dates[0] + "," + dates[1] + "," + dates[2]);
            try {
                if ("login".equals(dates[0])) {
                    /*
                    如果已经登录了，就不能登录了
                     */
                    boolean isLogin = false;
                    for (User each:users.getQueue()){
                        if (each.getUserName().equals(dates[1])){
                            os.println("loginFail");
                            os.flush();
                            socket.close();
                            isLogin = true;
                            break;
                        }
                    }
                    //从数据库核对账号密码
                    //成功 -> 创建用户对象返回成功,更新所有用户界面
                    if (dao.get(dates[1], dates[2]) && !isLogin) {
                        user = new User(socket, dates[1]);
                        user.send("loginSuccess"); //发送登录成功的消息
                        /*
                        新用户登录进来了，做两件事
                        1给所有已经在线的用户发送该用户上线的消息
                        2给新上线的用户发送所有已经在线的用户
                         */
                        String message2NewUser = users.sendOnLine(dates[1]);
                        user.send(message2NewUser);

                        //加入队列
                        users.addUser(user);
                        addListener(user);//监听
                    }else {
                        //失败返回错误信息
                        os.println("fail");
                        os.flush();
                    }
                }
                else if ("registered".equals(dates[0])) {
                    if (!dates[1].equals("all") && dao.add(dates[1], dates[2])){
                        os.println("success");
                        os.flush();
                        System.out.println("registered" + ":success");
                    } else {
                        os.println("fail");
                        os.flush();
                        System.out.println("registered" + ":fail");
                    }
                    Thread.sleep(500);
                    socket.close();
                }

            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void addListener(User user) {
        try {
            while (flag) {

                String data = user.receive();

                /*
                下线,切断socket,通知所有用户这个人离线了
                */
                if (data.contains("offline")){
                    socket.close();
                    users.getQueue().remove(user);//先移出去
                    users.sendOffLine(user);//给所有用户发送该用户的离线信息
                    flag = false;
                    break;
                }

                /*
                可能的情况有
                ①离线结束 收到E
                ②发送信息 S
                    ①大厅 S,ALL
                    ②私聊 S,
                 */
                if(data.contains("chat")){
                    String[] dates = data.split(",");//chat,receiver,message (sender = user.name)
                    int type = 0;
                    if (dates[1].equals("all")){
                        type = 1;
                    }
                    users.send(type, user.getUserName(), dates[1], dates[2]);
                }

                /*if ("E".equals(dates[0])) {//结束判断
                    users.sendOffLine(user);

                } else if ("S".equals(dates[0])) {
                    if (!"ALL".equals(dates[1])) {
                        users.send(0, user.getUserName(), dates[1], dates[2]);
                    } else {
                        users.send(1, user.getUserName(), dates[1], dates[2]);
                    }
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
