package com.Ninja.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class Shuriken extends Circle{
	
	public float x;
	public float y;
	
	private Texture shuTxt;
	
	public Shuriken(Texture shuTxt, float x, float y, float radius){
		this.x = x + radius;
		this.y = y + radius;
		this.shuTxt = shuTxt;
		this.radius = radius;
	}
	
	public Texture getTexture(){
		return shuTxt;
	}

	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getRadius(){
		return radius;
	}
	
	
	
}
