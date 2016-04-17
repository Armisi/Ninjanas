package com.Ninja.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.Ninja.game.*;

public class MyGame implements ApplicationListener {
	private Texture NinjaImage;
	private Texture BackgroundImage;
	private Texture ShurikenImage;
	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	float w;
	float h;
	float shuCool = 0;
	private float ScWidth = 1920;
	private float ScHeight = 1080;
	private float Speed = 500; // main nindz45s speed
	private float ShuSpeed = 1000;
	private float kampas;
	private float ShuRadius = 16;
	private float NinjaRadius = 64;
	private int NinjaCount = 4;
	private int main = 2;
	private String ip;
	private int score = 0;

	private List<Ninja> NinjaList;

	@Override

	public void create() {

		// pirma paveiksliukas Arnai
		batch = new SpriteBatch();
		font = new BitmapFont();
		NinjaImage = new Texture(Gdx.files.internal("ninja.png"));
		BackgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		ShurikenImage = new Texture(Gdx.files.internal("shi.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ScWidth, ScHeight);

		NinjaList = new ArrayList<Ninja>();

		for (int i = 0; i < NinjaCount; i++) {
			NinjaList.add(new Ninja(NinjaImage, (float) Math.random() * ScWidth, (float) Math.random() * ScHeight,
					NinjaRadius));
		}

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		Connection();
		
		
	}

	public void draw() {
		float MouseX = Gdx.input.getX() * ScWidth / w;
		float MouseY = ScHeight - Gdx.input.getY() * ScHeight / h;
		Gdx.gl.glClearColor(100, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(BackgroundImage, 0, 0);

		

		// piesia shurikenus
		for (int u = 0; u < NinjaList.size(); u++) {
			for (int i = 0; i < NinjaList.get(u).getShurikens().size(); i++) {
				batch.draw(ShurikenImage,
						NinjaList.get(u).getShurikens().get(i).getX() - NinjaList.get(u).getShurikens().get(i).radius,
						NinjaList.get(u).getShurikens().get(i).getY() - NinjaList.get(u).getShurikens().get(i).radius);
				NinjaList.get(u).getShurikens().get(i).x += Math.cos(NinjaList.get(u).getShurikens().get(i).kampas)
						* Gdx.graphics.getDeltaTime() * ShuSpeed;
				NinjaList.get(u).getShurikens().get(i).y += Math.sin(NinjaList.get(u).getShurikens().get(i).kampas)
						* Gdx.graphics.getDeltaTime() * ShuSpeed;
				for (int j = 0; j < NinjaList.size(); j++) {
					if (j != u) {
						if (atstumas(NinjaList.get(j).x, NinjaList.get(u).getShurikens().get(i).x, NinjaList.get(j).y,
								NinjaList.get(u).getShurikens().get(i).y) < NinjaRadius + ShuRadius - 5) {
							NinjaList.get(j).x = (float) Math.random() * ScWidth;
							NinjaList.get(j).y = (float) Math.random() * ScHeight;
						}
					}
				}
			}
		}

		for (int i = 0; i < NinjaList.size(); i++) {
			batch.draw(NinjaImage, NinjaList.get(i).x - NinjaList.get(i).getRadius(),
					NinjaList.get(i).y - NinjaList.get(i).getRadius());
		}
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		font.draw(batch, "Kampas: " + Math.toDegrees(kampas), 0, 700);

		String Mou = "Mouse Koord in window: " + MouseX + "   " + MouseY;
		font.draw(batch, Mou, 0, 600);

		String tekstas = "Ninjas koord: " + NinjaList.get(main).x + ";  " + NinjaList.get(main).y;
		font.draw(batch, tekstas, 0, 100);
		font.draw(batch, "" + NinjaList.get(main).getShurikens().size(), 0, 200);
		String rad = "Ninja Radius: " + NinjaList.get(main).getRadius();
		font.draw(batch, rad, 0, 300);
		batch.end();
	}

	@Override
	public void render() {

		draw();

		if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S)) {
			NinjaList.get(main).x -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(main).y -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W)) {
			NinjaList.get(main).x -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(main).y += Speed * 0.707 * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)) {
			NinjaList.get(main).x += Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(main).y -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.W)) {
			NinjaList.get(main).x += Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(main).y += Speed * 0.707 * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			NinjaList.get(main).x -= Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			NinjaList.get(main).x += Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.W)) {
			NinjaList.get(main).y += Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			NinjaList.get(main).y -= Speed * Gdx.graphics.getDeltaTime();
		}

		for (int j = 0; j < NinjaList.size(); j++) {
			for (int i = j + 1; i < NinjaList.size(); i++) {
				if (atstumas(NinjaList.get(j).x, NinjaList.get(i).x, NinjaList.get(j).y,
						NinjaList.get(i).y) < NinjaRadius * 2 - 3) {
					double CollisionKampas = Math.atan2(NinjaList.get(j).y - NinjaList.get(i).y,
							NinjaList.get(j).x - NinjaList.get(i).x);
					NinjaList.get(j).x += Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(j).y += Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(i).x -= Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(i).y -= Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					if (atstumas(NinjaList.get(j).x, NinjaList.get(i).x, NinjaList.get(j).y,
							NinjaList.get(i).y) < NinjaRadius * 2 - 2) {
						NinjaList.get(j).x += Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
						NinjaList.get(j).y += Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
						NinjaList.get(i).x -= Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
						NinjaList.get(i).y -= Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					}
				}
			}
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (TimeUtils.nanoTime() / 2 - shuCool > 500000000) {
				kampas = (float) Math.atan2(ScHeight - Gdx.input.getY() * ScHeight / h - NinjaList.get(main).y,
						Gdx.input.getX() * ScWidth / w - NinjaList.get(main).x);
				NinjaList.get(main).addShuriken(ShurikenImage, NinjaList.get(main).x - ShuRadius,
						NinjaList.get(main).y - ShuRadius, ShuRadius, kampas);
				shuCool = TimeUtils.nanoTime() / 2;
			}
		}
		// borders
		for (int i = 0; i < NinjaList.size(); i++) {
			if (NinjaList.get(i).x - NinjaList.get(i).getRadius() < 0) {
				NinjaList.get(i).x = NinjaList.get(i).getRadius();
				NinjaList.get(i).setTime(0);
			}
			if (NinjaList.get(i).y - NinjaList.get(i).getRadius() < 0) {
				NinjaList.get(i).y = NinjaList.get(i).getRadius();
				NinjaList.get(i).setTime(0);
			}
			if (NinjaList.get(i).x + NinjaList.get(i).getRadius() > ScWidth) {
				NinjaList.get(i).x = ScWidth - NinjaList.get(i).getRadius();
				NinjaList.get(i).setTime(0);
			}
			if (NinjaList.get(i).y + NinjaList.get(i).getRadius() > ScHeight) {
				NinjaList.get(i).y = ScHeight - NinjaList.get(i).getRadius();
				NinjaList.get(i).setTime(0);
			}
		}
		
		//score
		
		AI();
		
	}
	
	public void AI(){
		Random randomGenerator = new Random();
		for(int i = 0; i < NinjaList.size(); i++){
			if(i != main){
			NinjaList.get(i).x += Math.cos(NinjaList.get(i).getAngle()) * Gdx.graphics.getDeltaTime() * Speed;
			NinjaList.get(i).y += Math.sin(NinjaList.get(i).getAngle()) * Gdx.graphics.getDeltaTime() * Speed;
			}
		}
		
		
		for (int i = 0; i < NinjaList.size(); i++) {
			if(i != main){
			if (NinjaList.get(i).getTime() < TimeUtils.nanoTime() / 1000) {
				
				NinjaList.get(i).setTime(randomGenerator.nextInt(5000000) + TimeUtils.nanoTime() / 1000);
				NinjaList.get(i).setAngle(randomGenerator.nextInt(360));
				System.out.println(TimeUtils.nanoTime());
			}
			}
		}
	}

	public float atstumas(float x, float x2, float y, float y2) {
		return (float) Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

		batch.dispose();
	}

	private void Connection() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
				try {
					ip = inFromUser.readLine();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

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
								IPAddress = InetAddress.getByName(ip);
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("FROM SE");

							}
							byte[] sendData = new byte[64];
							byte[] receiveData = new byte[64];
							String sentence = NinjaList.get(main).x + ":" + NinjaList.get(main).y + ":"; // inFromUser.readLine();
							sendData = sentence.getBytes();
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
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

				while (true) {
					Random rand = new Random();
					DatagramSocket clientSocket = null;
					try {
						clientSocket = new DatagramSocket(15232);
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("FROM SE");

					}
					InetAddress IPAddress = null;
					try {
						IPAddress = InetAddress.getByName(ip);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("FROM SE");
					}
					byte[] sendData = new byte[64];
					byte[] receiveData = new byte[64];
					String sentence = NinjaList.get(main).x + ":" + NinjaList.get(main).y + ":"; // inFromUser.readLine();
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
					String modifiedSentence = new String(receivePacket.getData());
					System.out.println("FROM SERVER:" + modifiedSentence);
					clientSocket.close();
				}
			}
		}).start(); // And, start the thread running
		// Now we create a thread that will listen for incoming socket
		// connections

	}
}
