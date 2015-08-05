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

import java.util.ArrayList;
import java.util.List;

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
	private Ninja Ninja;
	private float ScWidth = 1920;
	private float ScHeight = 1080;
	private float Speed = 500; // main nindz45s speed
	private float ShuSpeed = 1000;
	private float kampas;
	private float ShuRadius = 16;
	private float NinjaRadius = 64;
	private int NinjaCount = 3;
	
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
		
		for(int i = 0; i < NinjaCount; i++){
			NinjaList.add(new Ninja(NinjaImage, (float) Math.random()*ScWidth, (float) Math.random()*ScHeight, NinjaRadius));
		}

		Ninja = new Ninja(NinjaImage, 64, 64, NinjaRadius);

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
	}

	public void draw() {
		float MouseX = Gdx.input.getX() * ScWidth / w;
		float MouseY = ScHeight - Gdx.input.getY() * ScHeight / h;
		Gdx.gl.glClearColor(100, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(BackgroundImage, 0, 0);
		
		for(int i = 0; i < NinjaList.size(); i++){
			batch.draw(NinjaImage, NinjaList.get(i).x - NinjaList.get(i).getRadius(), NinjaList.get(i).y - NinjaList.get(i).getRadius());
		}
		
		for (int i = 0; i < Ninja.getShurikens().size(); i++) {
			batch.draw(ShurikenImage, Ninja.getShurikens().get(i).getX() - Ninja.getShurikens().get(i).radius,
					Ninja.getShurikens().get(i).getY() - Ninja.getShurikens().get(i).radius);
			Ninja.getShurikens().get(i).x += Math.cos(Ninja.getShurikens().get(i).kampas) 
					* Gdx.graphics.getDeltaTime() * ShuSpeed;
			Ninja.getShurikens().get(i).y += Math.sin(Ninja.getShurikens().get(i).kampas) 
					* Gdx.graphics.getDeltaTime() * ShuSpeed;
			for (int j = 0; j < NinjaList.size(); j++) {
				if (atstumas(NinjaList.get(j).x, Ninja.getShurikens().get(i).x, NinjaList.get(j).y,
						Ninja.getShurikens().get(i).y) < NinjaRadius + ShuRadius - 5) {
					NinjaList.get(j).x = (float) Math.random() * ScWidth;
					NinjaList.get(j).y = (float) Math.random() * ScHeight;
				}
			}
		}
		
		batch.draw(NinjaImage, Ninja.x - Ninja.getRadius(), Ninja.y - Ninja.getRadius());
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		font.draw(batch, "Kampas: " + Math.toDegrees(kampas), 0, 700);

		String Mou = "Mouse Koord in window: " + MouseX + "   " + MouseY;
		font.draw(batch, Mou, 0, 600);

		String tekstas = "Ninjas koord: " + Ninja.x + ";  " + Ninja.y;
		font.draw(batch, tekstas, 0, 100);
		font.draw(batch, "" + Ninja.getShurikens().size(), 0, 200);
		String rad = "Ninja Radius: " + Ninja.getRadius();
		font.draw(batch, rad, 0, 300);
		batch.end();
	}

	@Override
	public void render() {

		draw();
		
		if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S)){
			Ninja.x -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
			Ninja.y -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}else if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W)){
			Ninja.x -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
			Ninja.y += Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}else if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)){
			Ninja.x += Speed * 0.707 * Gdx.graphics.getDeltaTime();
			Ninja.y -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}else if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.W)){
			Ninja.x += Speed * 0.707 * Gdx.graphics.getDeltaTime();
			Ninja.y += Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}
		else if (Gdx.input.isKeyPressed(Keys.A)) {
			Ninja.x -= Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			Ninja.x += Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.W)) {
			Ninja.y += Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.S)){
			Ninja.y -= Speed * Gdx.graphics.getDeltaTime();
		}

		for (int i = 0; i < NinjaList.size(); i++) {
			if (atstumas(Ninja.x, NinjaList.get(i).x, Ninja.y, NinjaList.get(i).y) < NinjaRadius * 2 - 5) {
				double CollisionKampas = Math.atan2(Ninja.y - NinjaList.get(i).y, Ninja.x - NinjaList.get(i).x);
				Ninja.x += Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
				Ninja.y += Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
				NinjaList.get(i).x -= Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
				NinjaList.get(i).y -= Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
				if (atstumas(Ninja.x, NinjaList.get(i).x, Ninja.y, NinjaList.get(i).y) < NinjaRadius * 2 - 2) {
					Ninja.x += Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					Ninja.y += Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(i).x -= Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(i).y -= Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
				}
			}
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {	
			if (TimeUtils.nanoTime()/2- shuCool > 500000000) {
				kampas = (float) Math.atan2(ScHeight - Gdx.input.getY() * ScHeight / h - Ninja.y,
						Gdx.input.getX() * ScWidth / w - Ninja.x);
				Ninja.addShuriken(ShurikenImage, Ninja.x - ShuRadius, Ninja.y - ShuRadius, ShuRadius, kampas);
				shuCool = TimeUtils.nanoTime()/2;
			}		
		}
		
		for(int i = 0;i < NinjaList.size(); i++){	
			if(NinjaList.get(i).x - NinjaList.get(i).getRadius() < 0){
				NinjaList.get(i).x = NinjaList.get(i).getRadius();
			}
			if(NinjaList.get(i).y - NinjaList.get(i).getRadius() < 0){
				NinjaList.get(i).y = NinjaList.get(i).getRadius();
			}
			if(NinjaList.get(i).x + NinjaList.get(i).getRadius() > ScWidth){
				NinjaList.get(i).x = ScWidth - NinjaList.get(i).getRadius();
			}
			if(NinjaList.get(i).y + NinjaList.get(i).getRadius() > ScHeight){
				NinjaList.get(i).y = ScHeight - NinjaList.get(i).getRadius();
			}
		}
		
		// borders
		if(Ninja.x - Ninja.getRadius() < 0){
			Ninja.x = Ninja.getRadius();
		}
		if(Ninja.y - Ninja.getRadius() < 0){
			Ninja.y = Ninja.getRadius();
		}
		if(Ninja.x + Ninja.getRadius() > ScWidth){
			Ninja.x = ScWidth - Ninja.getRadius();
		}
		if(Ninja.y + Ninja.getRadius() > ScHeight){
			Ninja.y = ScHeight - Ninja.getRadius();
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
}
