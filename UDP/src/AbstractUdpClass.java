import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * 
 *����������� ����� ���� �� ������ UDP-��������� �������� � ���� ������ � ���� ����������� ��� ������ ����������<br> � ������� ������� protected ��� ��������������� � ����������   {@link  UDPServer}, {@link  UDPClient}
 * <p>
 * <strong>�������� ������:</strong> {@link  AbstractUdpClass#typeMessage()}<p>
 * 
 * @author I-vengo
 * 
 * @see {@link  UDPServer},  {@link  UDPClient}
 * 
 */

public abstract class AbstractUdpClass {
	
	protected DatagramSocket soc;
	
	protected Boolean socketOpened=false  ;
	
	protected InetAddress socketAd=null;
	
	protected Integer port;
	
	protected String mes=null;
	
	protected String userName=null;
	
	protected Integer clientPort=null;
	
	protected String clientUserName=null;
	
	protected DatagramPacket inPack=null;
	
	protected String send;
	
	protected void renameUser(String s){
    	
    	if((s.matches("rename:[a-zA-Z][\\w]*"))&&(userName!=null)){
			String[]m=s.split(":");
			userName=m[1];
			send=new String("username:"+userName);			
		}
    	
    	   	
    }
	
	protected void checkAndPrint(String s){
		
		if(s.matches("username:[a-zA-Z][\\w]*")){
			String[]m=s.split(":");
			clientUserName=m[1];
			
		}else			
		System.out.println("\r"+"["+clientUserName+"]"+":  "+"\n"+s);
		}
	
	protected void authorize(Scanner s){
		System.out.println("Enter user name:");
		userName=s.nextLine().trim();
		DatagramPacket pac=new DatagramPacket(("username:"+userName).getBytes(),("username:"+userName).getBytes().length,socketAd,port);
		try {
			soc.send(pac);
			System.out.println();
					
		} catch (IOException e) {
			System.err.println("Server is Unavailable");//������ � ������ ��� ��� ������� ���� while � ��������� ������������ ���� �� �����������
		}
	}
	
	
	/**
	 * ����� ��������������� ��� ����� ��������� ��������� � ����������<br>
	 * ������������ ���������� ������ ���� ��� ������������ ������� ������������ �������,<br>
	 * ����� ���� ���������� �������, ������� ����������� ������ � ���������� ����� "bye"<br>
	 * ��� �� ���� ����������� �������� ���� ��� ������������, ����� � ���������� rename:[���_������������]
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void typeMessage() throws IOException, InterruptedException{
		
		Scanner in=new Scanner(System.in);
		authorize(in);
		
		 while(socketOpened){
			 
			if((socketAd!=null)&&(clientPort!=null)){
			
			send=in.nextLine();
			
			if(send.trim().equalsIgnoreCase("bye")){
				soc.close();
				in.close();
				socketOpened=false;
				return;
			}
			
			renameUser(send);			
			DatagramPacket pack=new DatagramPacket(send.getBytes(),send.getBytes().length,socketAd,clientPort);
			soc.send(pack);

					
			}else{
				System.out.println("Waiting for a new connection!!");
				Thread.sleep(5000);
			}
		}
	
		
	}
	
		
	protected abstract void recieveMessage();
	
	
}
