package server;

import server.data.UserQueue;
import server.thread.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    static boolean END = false;
    private static UserQueue users = UserQueue.getUsers();

    public static void main(String[] args) {
        try {
            System.out.println("服务器已启动···等待连接");
            ServerSocket serverSocket = new ServerSocket(8010);
            Socket socket;

            while (!ServerMain.END) {
                if ((socket = serverSocket.accept()) != null)
                    new ServerThread(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
