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

public class LoginThread extends Thread {
    private static UserQueue users = UserQueue.getUsers();
    private Socket socket;
    private boolean flag = true;
    private Dao dao = new Dao();

    public LoginThread(Socket socket) { this.socket = socket; }

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
                    //从数据库核对账号密码
                    //成功 -> 创建用户对象返回成功,更新所有用户界面
                    if (dao.get(dates[1], dates[2])) {
                        user = new User(socket, dates[1]);
                        if (users.getQueue().size() > 0) {//已经有人上线了
                            user.send(users.sendOnLine(dates[1]));
                        } else user.send("success," + "\n"); //当其是第一个上线，随便改
                        users.addUser(user);
                        addListener(user);//监听
                    } else {
                        //失败返回错误信息
                        os.println("fail");
                        os.flush();
                    }
                } else if ("registered".equals(dates[0])) {
                    if (dao.add(dates[1], dates[2]))
                        os.println("success");
                    else os.println("fail");
                    socket.close();
                }

            } catch (SQLException e) {
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
                可能的情况有
                ①离线结束 收到E
                ②发送信息 S
                    ①大厅 S,ALL
                    ②私聊 S,
                 */
                String[] dates = data.split(",");
                if ("E".equals(dates[0])) {//结束判断
                    users.sendOffLine(user);
                    flag = false;
                } else if ("S".equals(dates[0])) {
                    if (!"ALL".equals(dates[1])) {
                        users.send(0, user.getUserName(), dates[1], dates[2]);
                    } else {
                        users.send(1, user.getUserName(), dates[1], dates[2]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
