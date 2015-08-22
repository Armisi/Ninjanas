import java.io.*;
import java.net.*;
import java.util.Random;

class UDPClient {
	public static void main(String args[]) throws Exception {
		
		   // Now we create a thread that will listen for incoming socket connections
        new Thread (new Runnable(){

            @Override
            public void run() {
            	while(true){
            		DatagramSocket clientSocket = null;
					try {
						clientSocket = new DatagramSocket();
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		InetAddress IPAddress = null;
					try {
						IPAddress = InetAddress.getByName("192.168.1.71");
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		byte[] sendData = new byte[1024];
            		byte[] receiveData = new byte[1024];
            		String sentence = "asdasd"; //inFromUser.readLine();
            		sendData = sentence.getBytes();
            		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            		try {
						clientSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		clientSocket.close();
            		try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		}
           
           
            }
        }).start(); // And, start the thread running
		while(true){
			Random rand = new Random();

			int  n = rand.nextInt(50) + 1;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("192.168.1.71");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = ""+n; //inFromUser.readLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
		}
	}
}
