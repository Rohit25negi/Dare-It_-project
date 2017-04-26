
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Main2 extends Application implements Initializable{

    @FXML
    public TableView<RemoteItem>table;
    public Label directory;
    public void initialize(URL location, ResourceBundle resources)
    {
        TableColumn<RemoteItem,String> type=new TableColumn("Type");
        type.setCellValueFactory(new PropertyValueFactory("type"));
        TableColumn<RemoteItem,String> name=new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        name.setMinWidth(300);

        try {
            //table.setItems(root());
            log();

        }catch (Exception e)
        {
            System.out.println(e);
        }

        table.getColumns().addAll(type, name);
    }

static    Stage stg;
    @Override

    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("main_activity.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 550, 575));
        primaryStage.show();

    }


    public void update()throws Exception
    {
        RemoteItem r=table.getSelectionModel().getSelectedItem();


        if(r.type.equals("FILE"))
        {

            byte[]X;

            X=c.download(r.name);

            FileOutputStream fout=new FileOutputStream(r.name);
            fout.write(X);
            fout.close();
            Desktop d=Desktop.getDesktop();
            d.open(new File(r.name));

            new OnYo().oncreate(r.name);

        }

    }



    public void yes(String name)
    {
        try
        {  File f=new File(name);

            byte P[];

            FileInputStream fin=new FileInputStream(f);
           P=new byte[(int)f.length()];
            fin.read(P);
            fin.close();

            System.out.println(c.upload(P,name));
            while(!f.delete());
        }catch(FileNotFoundException e)
        {

        }
        catch(IOException e)
        {

        }

    }

    static Cest c;
    public static void main(String[] args)throws Exception {


  c= (Cest)Naming.lookup("rmi://"+args[0]+":5000/untitled");
        stg=new Stage();
        new Main2().start(stg);
    }
    public void delete()throws Exception
    {
        RemoteItem r=table.getSelectionModel().getSelectedItem();
        if(r.type.equals("FILE"))
            System.out.println(c.delete(r.name));
        log();
    }
    public void upload()
    {
        FileChooser ff=new FileChooser();
        ff.setTitle("Select");
        ff.setInitialDirectory(new File("./"));
        File f=ff.showOpenDialog(stg);

        if(f!=null)
        {
            try
            {
                FileInputStream fin=new FileInputStream(f);
		byte p[]=new byte[(int)f.length()];
		fin.read(p);



                fin.close();
                String name=f.toString();
                System.out.println(c.upload(p,name.substring(name.lastIndexOf("\\"))));
            }catch(FileNotFoundException e)
            {

            }
            catch(IOException e)
            {

            }
        }

    }
    public void goToRoot()throws Exception
    {
       c.goToRoot();
        log();
    }
    public void download()throws RemoteException
    {   RemoteItem r=table.getSelectionModel().getSelectedItem();


        if(r.type.equals("FILE"))
        {
            FileChooser ff=new FileChooser();
            ff.setTitle("SAVE");
            ff.getExtensionFilters().add(new FileChooser.ExtensionFilter("lolo", "*" + r.name.substring(r.name.lastIndexOf("."))));
            ff.setInitialDirectory(new File("./"));

            File f=ff.showSaveDialog(stg);


          byte  P[]= c.download(r.name);

            try
            {
                FileOutputStream fout=new FileOutputStream(f);
                fout.write(P);

                fout.close();
                System.out.println(P.length);

            }catch(Exception e)
            {
                System.out.println(e);
            }

        }
    }
    public void forward()throws  Exception
    {
        RemoteItem r=table.getSelectionModel().getSelectedItem();
        if(r.type.equals("DIRECTORY"))
        {c.forward(r.name);
            log();}

    }
    public void back()throws Exception
    {
        c.back();
        log();


    }

    void log()throws Exception
    {
        ObservableList<RemoteItem>list= FXCollections.observableArrayList();
        ArrayList<String[]> list2=c.list();
        directory.setText(list2.get(0)[0]);
        for(int i=1;i<list2.size();i++)
        {
            list.add(new RemoteItem(list2.get(i)[0], list2.get(i)[1] ));}

        table.getItems().clear();
        table.setItems(list);


    }
}
