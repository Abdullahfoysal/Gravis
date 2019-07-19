package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class gameClass extends Game {
	
	public static final float PPM=100;
	public static final int v_width=1028;
	public static final int v_height=700;
	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch=new SpriteBatch();
		setScreen(new PlayScreen(this));
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
