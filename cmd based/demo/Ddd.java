import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.ArrayList;
public interface Ddd extends Remote
{
	public boolean uploadd(ArrayList<Integer> x,String name)throws RemoteException;
	public ArrayList<Integer> downloadd(String x)throws RemoteException;
	public boolean check(String x)throws RemoteException;
	public String[] listing(String x)throws RemoteException;



}