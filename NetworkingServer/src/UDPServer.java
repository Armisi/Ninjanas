import java.io.*;
import java.net.*;

class UDPServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[64];
		byte[] sendData = new byte[64];
		boolean gotPort = false;
		int port = 0;
		boolean listening = true;
		
		while (listening) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			//if(!sentence.equals("asdasd")){
				System.out.println("RECEIVED: " + sentence);
			//}
			InetAddress IPAddress = receivePacket.getAddress();
			   new MultiServerThread(receivePacket.getPort(), IPAddress).start();
//			 port = receivePacket.getPort();
//			String capitalizedSentence = sentence.toUpperCase();
//			sendData = capitalizedSentence.getBytes();
//			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
//			serverSocket.send(sendPacket);
		}
	}
}
