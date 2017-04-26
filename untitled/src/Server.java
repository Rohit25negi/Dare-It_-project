

/**
 * Created by Champ on 2/28/2016.
 */
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.ArrayList;
interface Cest extends Remote
{
    ArrayList<String[]> list()throws RemoteException;
    boolean delete(String f)throws RemoteException;
    boolean back()throws RemoteException;
    void forward(String to)throws RemoteException;
    File[] findRoots()throws RemoteException;
    void goToRoot()throws RemoteException;
    byte[] download(String filename)throws RemoteException;
    boolean upload(byte[] data,String name)throws RemoteException;
}
public class Server extends UnicastRemoteObject implements Cest
{	String current="";
    public boolean upload(byte[] data,String name)throws RemoteException
    {
        try
        {
            File f=new File(current+name);
            FileOutputStream fout=new FileOutputStream(f);
            fout.write(data);
            fout.close();
            return true;
        }catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    public byte[] download(String filename)throws RemoteException
    {
        File f=new File(current+filename);
         if(!f.exists())return null;
        try
        {
            byte P[]=new byte[(int)f.length()];

            FileInputStream fin=new FileInputStream(f);
              fin.read(P);

            fin.close();
            return P;

        }catch (FileNotFoundException e)
        {
            System.out.println(e);
            return null;
        }catch (IOException e)
        { System.out.println(e);
            return null;

        }
    }
    public boolean back()throws RemoteException
    {
        current=new File(current).getParent()==null?"":(new File(current).getParent());
        return true;
    }
    public void forward(String to)
    {
        current+=to+"/";

    }
    public  File[] findRoots()throws RemoteException
    {
        return File.listRoots();
    }
    public void goToRoot()throws  RemoteException
    {
        current="";
    }
    public ArrayList<String[]> list()throws RemoteException
    {	    System.out.println(current);
        ArrayList<String[]> A=new ArrayList();
        if(!current.isEmpty())
        {File f=new File(current);

        A.add(new String[]{f.getAbsolutePath()});

        for(String x:f.list())
        {
            A.add(new String[]{new File(f+"/"+x).isFile()?"FILE":"DIRECTORY",x});
        }}
        else
        {   A.add(new String[]{"ROOT"});
            for(File x:findRoots())A.add(new String[]{"DIRECTORY",(x+"").substring(0,2)});

        }
        return A;
    }
    public boolean delete(String f)
    {
        while(!new File(current+f).delete());
        return true;
    }
    Server() throws RemoteException
    {
        super();
    }
}
class Test
{
    public static void main(String args[])
    {
       try {
           Cest c = new Server();
            System.out.println("hello");
           Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":5000/untitled", c);
       }catch(Exception e)
       {
           e.printStackTrace();
       }
    }
}