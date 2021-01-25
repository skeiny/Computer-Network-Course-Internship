package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Author: Sky
 * @Date: 2021/1/24 23:02
 */
public class ServerThread extends Thread{

    private Socket socket;
    private BufferedReader br;
    private PrintStream ps;

    public ServerThread(Socket socket,BufferedReader br,PrintStream ps) throws IOException {
        this.socket = socket;
        this.br = br;
        this.ps = ps;
    }

    @Override
    public void run(){
        try {
            ps.println("1");
            ps.flush();
            /*Thread.sleep(500);
            ps.println("online,sky,sjz,zhl,end");
            ps.flush();*/

            br.close();
            ps.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
