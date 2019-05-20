package net.dermetfan.blackpoint2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.dermetfan.blackpoint2.screens.Splash;

public class Blackpoint2 extends Game {
	
	public static final String TITLE="BLACKPOINT GAME",VERSION="0.000.001.ReallyEarly";
	@Override
	public void create () {
		
	    setScreen(new finalPractice());
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
	
	
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize(int width,int height) {
		super.resize(width, height);
	}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}
	
}
