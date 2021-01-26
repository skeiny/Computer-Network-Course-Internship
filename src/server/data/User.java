package server.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class User {
    private Socket socket;
    private String userName;
    public BufferedReader bf;
    public PrintWriter ps;

    public User(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        try {
            this.bf = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ps = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User(Socket socket, String userName, BufferedReader bf, PrintWriter ps) {
        this.socket = socket;
        this.userName = userName;
        this.bf = bf;
        this.ps = ps;
    }

    public User(Socket socket) {
        this.socket = socket;
        try {
            this.bf = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ps = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void destroy() {
        try {
            bf.close();
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String data) {
        ps.println(data);
        ps.flush();
        System.out.println("服务器给"+userName+"发送了:"+data);
    }

    public String receive() throws IOException {
//        return bf.readLine();
        String s = bf.readLine();
        System.out.println("服务器接收到"+s);
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(socket, user.socket) &&
                Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, userName);
    }
}
