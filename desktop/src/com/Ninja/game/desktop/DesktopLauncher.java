package com.Ninja.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Ninja.game.MyGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	      config.title = "Drop";
	      config.width = 1920;
	      config.height = 1080;
	      new LwjglApplication(new MyGame(), config);
	}
}

