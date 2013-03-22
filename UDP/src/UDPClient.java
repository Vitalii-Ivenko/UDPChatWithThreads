import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


	/**
	 * Класс клиентской части чата на UDP<br>
	 * при подключении ожидает ввода имени от пользователя которое пересылается серверу,<br>
	 * после чего начинается общение.<br>
	 * В отдельной нити идет постоянное прослушивание на предмет входящих сообщений<p>
	 * <strong>Содержит методы:</strong> {@link  UDPClient#run()}<p>
	 * <strong>Содержит конструктор:</strong> {@link UDPClient#UDPClient(int)}
	 * @author I-vengo
	 * <br>
	 * <br>
	 * @see  {@link  AbstractUdpClass}, {@link  UDPClient}
	 * <br>
	 * <br>
	 * <strong>
	 * <p>
	 * All implemented interfaces:
	 * </strong>
	 *      <p>                         {@link Runnable} 
	 */

public class UDPClient extends AbstractUdpClass implements Runnable {
	
	/**
	 * При инициализации нового обьекта класса UDPClient создаем новый сокет
	 * @param port создает сокет привязанный к конкретному порту
	 * @param inetAddrServer вводится ip-адрес сервера 
	 * @throws SocketException
	 */
	
	public UDPClient(int port,String inetAddrServer){
		this.port=port;
		try {
			
			socketAd=InetAddress.getByName(inetAddrServer);
			soc=new DatagramSocket();
			socketOpened=true;
			
		} catch (UnknownHostException e) {
			
			System.err.println("There's no such server");
			
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
		System.out.println("Created new client.....\nsending message to server   "+socketAd+port);
	}
	
	protected void recieveMessage(){
			
		while(socketOpened){
			
			
			byte[]buf=new byte[1000];
			inPack=new DatagramPacket(buf,buf.length);
			try {
				soc.receive(inPack);
				
			} catch (IOException e) {
				System.err.println("Client offline");
				return;
			}
			
			mes=new String(buf,0,inPack.getLength());
			checkAndPrint(mes);
		}
	}
		
	/**
	 * Поток, отвечающий за постоянное прослушивание входящих сообщений
	 * 
	 */
	
	public  void run(){
		
		recieveMessage();
		
	}
		
}
