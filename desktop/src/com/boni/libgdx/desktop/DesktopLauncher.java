package com.boni.libgdx.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.boni.libgdx.StarfishCollectorBeta;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game starfishCollectorAlpha = new StarfishCollectorBeta();
		LwjglApplication launcher = new LwjglApplication(starfishCollectorAlpha, "Starfish Collector", 800, 600);
	}
}
