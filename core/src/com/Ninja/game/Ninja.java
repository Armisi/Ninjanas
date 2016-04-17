package com.Ninja.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

// Ninzes klase
public class Ninja extends Circle{
	private Texture NinjaImage; // nindzes paveiksliukas
	private List<Shuriken> shuList;
	private float time = 0;
	private float angle = 0;
	
	public Ninja(Texture NinjaT, float x, float y, float radius){
		this.x = x + radius;
		this.y = y + radius;
		this.NinjaImage = NinjaT;
		this.radius = radius;
		shuList = new ArrayList<Shuriken>();
		
	}
	
	public void setAngle(float angle){
		this.angle = angle;
	}
	
	public void setTime(float time){
		this.time = time;
		
	}
	
	public float getAngle(){
		return angle;
	}
	
	public float getTime(){
		return time;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public Texture getNinjaImage(){
		return NinjaImage;
	}
	
	public void addShuriken(Texture Txt, float x, float y, float radius, float kampas){
		Shuriken shi =  new Shuriken(Txt, x, y, radius/2, kampas);
		//shuList.clear();
		shuList.add(shi);
	}
	
	public List<Shuriken> getShurikens(){
		return shuList;
	}
}
