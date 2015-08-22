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
	private int NinjaCount = 4;
	
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
		
		//piesia shurikenus
		for(int u = 0; u < NinjaList.size(); u++){
			for (int i = 0; i < NinjaList.get(u).getShurikens().size(); i++) {
				batch.draw(ShurikenImage, NinjaList.get(u).getShurikens().get(i).getX() - NinjaList.get(u).getShurikens().get(i).radius,
						NinjaList.get(u).getShurikens().get(i).getY() - NinjaList.get(u).getShurikens().get(i).radius);
				NinjaList.get(u).getShurikens().get(i).x += Math.cos(NinjaList.get(u).getShurikens().get(i).kampas) 
						* Gdx.graphics.getDeltaTime() * ShuSpeed;
				NinjaList.get(u).getShurikens().get(i).y += Math.sin(NinjaList.get(u).getShurikens().get(i).kampas) 
						* Gdx.graphics.getDeltaTime() * ShuSpeed;
				for (int j = 1; j < NinjaList.size(); j++) {
					if (atstumas(NinjaList.get(j).x, NinjaList.get(u).getShurikens().get(i).x, NinjaList.get(j).y,
							NinjaList.get(u).getShurikens().get(i).y) < NinjaRadius + ShuRadius - 5) {
						NinjaList.get(j).x = (float) Math.random() * ScWidth;
						NinjaList.get(j).y = (float) Math.random() * ScHeight;
					}
				}
			}
		}
		
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		font.draw(batch, "Kampas: " + Math.toDegrees(kampas), 0, 700);

		String Mou = "Mouse Koord in window: " + MouseX + "   " + MouseY;
		font.draw(batch, Mou, 0, 600);

		String tekstas = "Ninjas koord: " + NinjaList.get(0).x + ";  " + NinjaList.get(0).y;
		font.draw(batch, tekstas, 0, 100);
		font.draw(batch, "" + NinjaList.get(0).getShurikens().size(), 0, 200);
		String rad = "Ninja Radius: " + NinjaList.get(0).getRadius();
		font.draw(batch, rad, 0, 300);
		batch.end();
	}
	
	@Override
	public void render() {

		draw();
		
		if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S)){
			NinjaList.get(0).x -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(0).y -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}else if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W)){
			NinjaList.get(0).x -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(0).y += Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}else if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)){
			NinjaList.get(0).x += Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(0).y -= Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}else if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.W)){
			NinjaList.get(0).x += Speed * 0.707 * Gdx.graphics.getDeltaTime();
			NinjaList.get(0).y += Speed * 0.707 * Gdx.graphics.getDeltaTime();
		}
		else if (Gdx.input.isKeyPressed(Keys.A)) {
			NinjaList.get(0).x -= Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			NinjaList.get(0).x += Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.W)) {
			NinjaList.get(0).y += Speed * Gdx.graphics.getDeltaTime();
		} else if (Gdx.input.isKeyPressed(Keys.S)){
			NinjaList.get(0).y -= Speed * Gdx.graphics.getDeltaTime();
		}
		
		for (int j = 0; j < NinjaList.size(); j++){
			for (int i = j + 1; i < NinjaList.size(); i++) {
				if (atstumas(NinjaList.get(j).x, NinjaList.get(i).x, NinjaList.get(j).y, NinjaList.get(i).y) < NinjaRadius * 2 - 5) {
					double CollisionKampas = Math.atan2(NinjaList.get(j).y - NinjaList.get(i).y, NinjaList.get(j).x - NinjaList.get(i).x);
					NinjaList.get(j).x += Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(j).y += Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(i).x -= Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					NinjaList.get(i).y -= Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					if (atstumas(NinjaList.get(j).x, NinjaList.get(i).x, NinjaList.get(j).y, NinjaList.get(i).y) < NinjaRadius * 2 - 2) {
						NinjaList.get(j).x += Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
						NinjaList.get(j).y += Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
						NinjaList.get(i).x -= Math.cos(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
						NinjaList.get(i).y -= Math.sin(CollisionKampas) * Gdx.graphics.getDeltaTime() * Speed / 2;
					}
				}
			}
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {	
			if (TimeUtils.nanoTime()/2- shuCool > 500000000) {
				kampas = (float) Math.atan2(ScHeight - Gdx.input.getY() * ScHeight / h - NinjaList.get(0).y,
						Gdx.input.getX() * ScWidth / w - NinjaList.get(0).x);
				NinjaList.get(0).addShuriken(ShurikenImage, NinjaList.get(0).x - ShuRadius, NinjaList.get(0).y - ShuRadius, ShuRadius, kampas);
				shuCool = TimeUtils.nanoTime()/2;
			}		
		}
		//borders
		for(int i = 0; i < NinjaList.size(); i++){	
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
