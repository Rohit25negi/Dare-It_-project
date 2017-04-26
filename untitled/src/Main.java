
import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.rmi.Naming;



public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
         Pane root = FXMLLoader.load(getClass().getResource("First.fxml"));
        primaryStage.setTitle("Dare It");
        primaryStage.setScene(new Scene(root, 340, 375));
        primaryStage.show();
        stg=primaryStage;
    }
    static Stage stg;
    public static void main(String[] args)throws Exception {


        launch(args);
    }
    public void passive()throws Exception
    {
        stg.close();
        Desktop.getDesktop().open(new File("rmi.bat"));
        Thread.sleep(4000);
        Test.main(new String[]{""});


    }

    public void active()throws Exception
    {

        try{String x=JOptionPane.showInputDialog(null,"enter the host name");
        InetAddress ip=InetAddress.getByName(x);

            System.out.println(ip.getHostAddress());
            stg.close();
       Naming.lookup("rmi://" + ip.getHostAddress() + ":5000/untitled");

            Main2.main(new String[]{ip.getHostAddress()});

        }
        catch(Exception e)
        {
       e.printStackTrace();
        }
    }

}
