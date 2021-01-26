package server.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserQueue {
    List<User> queue = new ArrayList();

    static UserQueue users;

    public static UserQueue getUsers() {
        if (users == null){
            synchronized (UserQueue.class){
                if (users == null){
                    users = new UserQueue();
                }
            }
        }
        return users;
    }

    public List<User> getQueue() {
        return queue;
    }

    public synchronized void addUser(User user) {
            queue.add(user);
    }

    public synchronized void removeUser(User user) { queue.remove(user); }

    public synchronized User getUser(String userName) {
        for (User user : queue) {
            if (userName.equals(user.getUserName())) {
                return user;
            }
        }
        return null;
    }

    /*
    给所有已经在线的用户更新在线信息
     */
    public synchronized String sendOnLine(String newUserName) {
        if (queue.size()<=0) return "currentOnline,";
        StringBuilder currentOnline = new StringBuilder("currentOnline");
        for (User u : queue) {
            u.send("newUserOnline," + newUserName);//给已经上线的用户传递新上线用户的信息
            currentOnline.append(",").append(u.getUserName());//构造已经上线的用户的串
        }
        return currentOnline.toString();
    }

    public synchronized void sendOffLine(User user) {
        Iterator<User> iterator = queue.iterator();
        while (iterator.hasNext()) {
            User u = iterator.next();
            u.send("offline," + user.getUserName());
        }
    }

    public synchronized void send(int type, String sName, String rName, String data) {
        if (type == 0) {//单独
            for (User u : queue) {
                //通知UI界面更新
                if (rName.equals(u.getUserName())) {
                    /*
                    sName--信息来源
                     */
                    u.send("chat," + sName + "," + data);
                    System.out.println("服务器给"+u.getUserName()+"发送了:"+"chat," + sName + "," + data);
                    break;
                }
            }
        } else if (type == 1) {//群发
            for (User u : queue) {
                //通知UI界面更新
                if (!u.getUserName().equals(sName)){
                    u.send("allChat," + sName + "," + data);
                }
            }
        }
    }
}
