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

    public synchronized String sendOnLine(String name) {
        StringBuilder sb = new StringBuilder("success,online");
        for (User u : queue) {
            sb.append(",").append(u.getUserName());
            u.send("L:" + name);
        }
        return sb.toString() + "\n";
    }

    public synchronized void sendOffLine(User user) {
        Iterator<User> iterator = queue.iterator();
        while (iterator.hasNext()) {
            User u = iterator.next();
            if (!user.equals(u)) {
                u.send("E:" + user.getUserName());
            } else {
                iterator.remove();
            }
        }
    }

    public synchronized void send(int type, String sName, String rName, String data) {
        if (type == 0) {//单独
            for (User u : queue) {
                //通知UI界面更新
                if (rName.equals(u.getUserName())) {
                    u.send("send," + sName + "," + data);
                    break;
                }
            }
        } else if (type == 1) {//群发
            for (User u : queue) {
                //通知UI界面更新
                u.send("send," + sName + "," + data);
            }
        }
    }
}
