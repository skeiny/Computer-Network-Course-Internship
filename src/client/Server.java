package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Sky
 * @Date: 2021/1/23 17:10
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        Socket socket;
        while ((socket=serverSocket.accept())!=null){
            System.out.println("来了一个");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream ps = new PrintStream(socket.getOutputStream());
                ServerThread st = new ServerThread(socket,br,ps);
                st.start();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
