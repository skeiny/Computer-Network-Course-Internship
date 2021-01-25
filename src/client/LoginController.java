package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.MD5Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/1/25 14:07
 */
public class LoginController implements Initializable {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button registered;


    /**
     * 自动初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void login(){
        if (userName.getText().equals("")||password.getText().equals("")){
            System.out.println("请输入账号密码！");
            return;
        }
        try {
            /*
            初始化连接，建立输入输出流
             */
            Socket socket = new Socket("localhost",8010);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*
            发送登录信息 格式为 login,username,password
             */
            writer.println("login,"+userName.getText()+","+ MD5Utils.md5(password.getText()));
            writer.flush();
            /*
            等待返回的结果
             */
            String loginResult = reader.readLine();
            if(loginResult.equals("fail")){
                System.out.println("登录失败！");
                writer.println("end");
                /*
                关闭连接
                 */
                writer.flush();
                writer.close();
                reader.close();
                socket.close();
            }
            else {
                System.out.println("登录成功！");
                /*
                接下来加载聊天界面
                 */
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void registered(){
        if (userName.getText().equals("")||password.getText().equals("")){
            System.out.println("请输入账号密码！");
            return;
        }
        try {
            /*
            初始化连接，建立输入输出流
             */
            Socket socket = new Socket("localhost",8010);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*
            发送登录信息 格式为 login,username,password
             */
            writer.println("registered,"+userName.getText()+","+ MD5Utils.md5(password.getText()));
            writer.flush();
            /*
            等待返回的结果
             */
            String loginResult = reader.readLine();
            if(loginResult.equals("fail")){
                System.out.println("注册失败！");
                writer.println("end");//通知服务器与线程关闭连接，服务器也可以在返回fail后直接关闭
            }
            else {
                System.out.println("注册成功！请登录···");
            }
            /*
            失败与否都得关闭连接
            */
            writer.flush();
            writer.close();
            reader.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
