package com.boni.libgdx.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.boni.libgdx.StarfishCollectorAlpha;
import com.boni.libgdx.libgdx;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game starfishCollectorAlpha = new StarfishCollectorAlpha();
		LwjglApplication launcher = new LwjglApplication(starfishCollectorAlpha, "Starfish Collector", 800, 600);
	}
}
