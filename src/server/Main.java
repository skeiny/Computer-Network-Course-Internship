package server;

import server.data.UserQueue;
import server.thread.LoginThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static boolean END = false;
    private static UserQueue users = UserQueue.getUsers();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8010);
            Socket socket;

            while (!Main.END) {
                if ((socket = serverSocket.accept()) != null)
                    new LoginThread(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
