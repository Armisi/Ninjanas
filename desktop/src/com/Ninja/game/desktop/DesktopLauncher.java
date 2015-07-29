package com.Ninja.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Ninja.game.MyGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	      config.title = "Drop";
	      config.width = 1600;
	      config.height = 900;
	      new LwjglApplication(new MyGame(), config);
	}
}

