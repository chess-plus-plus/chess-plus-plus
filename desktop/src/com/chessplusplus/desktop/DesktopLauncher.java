package com.chessplusplus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chessplusplus.ChessPlusPlus;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.width = ChessPlusPlus.WIDTH;
		config.height = ChessPlusPlus.HEIGHT;
		new LwjglApplication(new ChessPlusPlus(new FirebaseDesktopInterface()), config);

	}
}
