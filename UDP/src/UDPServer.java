import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;


/**
 * ����� ��������� ����� ���� �� UDP<br>
 * ��� ����������� ������� ��������� ��������� �� ������������<br>
 * � ��������� ���� ���� ���������� ������������� �� ������� �������� ���������<p>
 * <strong>�������� ������:</strong> {@link  UDPServer#run()}<p>
 * <strong>�������� �����������:</strong> {@link UDPServer#UDPServer(int)
 * 
 * 
 * 
 * @author I-vengo
 * <br>
 * <br>
 * @see  {@link  AbstractUdpClass}, {@link  UDPClient}
 * <br>
 * <br>
 * 
 * <strong>
 * All implemented interfaces:
 * </strong>
 * 
 *      <p>                         {@link Runnable} 
 */
public class UDPServer extends AbstractUdpClass implements Runnable {
	
	/**
	 * ��� ������������� ������ ������� ������ UDPServer ������� ����� �����
	 * @param port ������� ����� ����������� � ����������� �����
	 * @throws SocketException
	 */
	public UDPServer(int port) throws SocketException{
		this.port=port;
		soc=new DatagramSocket(port);
		socketOpened=true;
		System.out.println("Server started at "+ soc.getInetAddress()+"  "+soc.getLocalPort()+" port");
	}
		
	protected  void recieveMessage(){
			
				
			while(socketOpened){
						
			byte[] buffer= new byte[1000];
			inPack=new DatagramPacket(buffer,buffer.length);
			try {
				soc.receive(inPack);
			} catch (IOException e) {
				System.err.println("Server offline");
				return;
			}
					
			mes=new String(buffer,0,inPack.getLength());
			
			
			if(((socketAd==null)&&(clientPort==null))){
				
				socketAd=inPack.getAddress();
				clientPort=inPack.getPort();
				System.out.println("\nThere is a new connection "+inPack.getAddress());
				checkAndPrint(mes);
				System.out.print("["+clientUserName+"]: \n");
				
				
			}else checkAndPrint(mes);
				}
			}
	
	/**
	 * �����, ���������� �� ���������� ������������� �������� ���������
	 * 
	 */
    public void run(){
    	   	
    	recieveMessage();
				
	}

}
