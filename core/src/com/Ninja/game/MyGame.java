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
	@Override

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		NinjaImage = new Texture(Gdx.files.internal("ninja.png"));
		BackgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		ShurikenImage = new Texture(Gdx.files.internal("shi.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ScWidth, ScHeight);

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
		
		for (int i = 0; i < Ninja.getShurikens().size(); i++) {
			batch.draw(ShurikenImage, Ninja.getShurikens().get(i).getX() - Ninja.getShurikens().get(i).radius,
					Ninja.getShurikens().get(i).getY() - Ninja.getShurikens().get(i).radius);
			Ninja.getShurikens().get(i).x += Math.cos(Ninja.getShurikens().get(i).kampas) 
					* Gdx.graphics.getDeltaTime() * ShuSpeed;
			Ninja.getShurikens().get(i).y += Math.sin(Ninja.getShurikens().get(i).kampas) 
					* Gdx.graphics.getDeltaTime() * ShuSpeed;
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

		
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {	
			if (TimeUtils.nanoTime()/2- shuCool > 500000000) {
				kampas = (float) Math.atan2(ScHeight - Gdx.input.getY() * ScHeight / h - Ninja.y,
						Gdx.input.getX() * ScWidth / w - Ninja.x);
				Ninja.addShuriken(ShurikenImage, Ninja.x - ShuRadius, Ninja.y - ShuRadius, ShuRadius, kampas);
				shuCool = TimeUtils.nanoTime()/2;
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

	public void ShootShu(double kampas, int i) {
	
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
