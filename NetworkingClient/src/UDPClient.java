import java.io.*;
import java.net.*;
import java.util.Random;

class UDPClient {
	
	private static int port = 0;
	private static boolean gotPort = false;
	public static void main(String args[]) {
		
	
		boolean signal = true;
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FROM SE");

		}
		
		Random rand = new Random();

		int n = rand.nextInt(50) + 1;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		InetAddress IPAddress = null;
		try {
			IPAddress = InetAddress.getByName("192.168.1.71");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FROM SE");
		}
		byte[] sendData = new byte[64];
		byte[] receiveData = new byte[64];
		String sentence = "" + n; // inFromUser.readLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("FROM SE");

		}
	
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			clientSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FROM SE");

		}
		
		if(!gotPort){
			port = receivePacket.getPort();
			gotPort = true;
		}
		
		System.out.println("Eina");
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		
		while (true) {

			n = rand.nextInt(50) + 1;
			try {
				IPAddress = InetAddress.getByName("192.168.1.71");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("FROM SE");
			}
			byte[] sendData1 = new byte[64];
			byte[] receiveData1 = new byte[64];
			String sentence11 = "" + n; // inFromUser.readLine();
			sendData1 = sentence11.getBytes();
			DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPAddress, port);
			try {
				clientSocket.send(sendPacket1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("FROM SE");

			}
		
			DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
			try {
				clientSocket.receive(receivePacket1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("FROM SE");

			}
			
			System.out.println("Eina");
			String modifiedSentence1 = new String(receivePacket1.getData());
			System.out.println("FROM SERVER:" + modifiedSentence1);
			//clientSocket.close();

			if (signal) {
				signal = false;
				// Now we create a thread that will listen for incoming socket
				// connections
				new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							DatagramSocket clientSocket = null;
							try {
								clientSocket = new DatagramSocket();
							} catch (SocketException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("FROM SE");

							}
							InetAddress IPAddress = null;
							try {
								IPAddress = InetAddress.getByName("192.168.1.71");
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("FROM SE");

							}
							byte[] sendData = new byte[64];
							byte[] receiveData = new byte[64];
							String sentence = "asdasd"; // inFromUser.readLine();
							sendData = sentence.getBytes();
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							try {
								clientSocket.send(sendPacket);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("FROM SE");

							}
							clientSocket.close();
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("FROM SE");

							}
						}

					}
				}).start(); // And, start the thread running

			}
		}
	}
}
