import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class MultiServerThread  extends Thread {
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	int port;
	InetAddress ipAddress;
	
	public MultiServerThread(int port, InetAddress ip){
	    super("MultiServerThread");
	    this.port = port;
	    this.ipAddress = ip;
	    //   dSocket = new DatagramSocket(4445);
	}

	public void run() {
		
		
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FROM SE");

		}


		
		byte[] sendData = new byte[64];
		byte[] receiveData = new byte[64];
		String sentence = "Is serverio threado"; // inFromUser.readLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		while(true){
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		String capitalizedSentence = sentence.toUpperCase();
		sendData = capitalizedSentence.getBytes();
		DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length, ipAddress, port);
		try {
			serverSocket.send(sendPacket1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FROM CLIENT:" + capitalizedSentence);
		//serverSocket.close();

		}
	}
}
