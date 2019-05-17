package net.dermetfan.blackpoint2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.dermetfan.blackpoint2.Blackpoint2;

public class Main {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title=Blackpoint2.TITLE+" V"+Blackpoint2.VERSION;
		cfg.vSyncEnabled=true;
		cfg.width=1270;
		cfg.height=768;
		new LwjglApplication(new Blackpoint2(), cfg);
	}
}
