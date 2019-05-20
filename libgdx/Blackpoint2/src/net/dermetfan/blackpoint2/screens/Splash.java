package net.dermetfan.blackpoint2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import net.dermetfan.blackpoint2.Physics3;
import net.dermetfan.blackpoint2.Play;
import net.dermetfan.blackpoint2.SpritePhysics;
import net.dermetfan.blackpoint2.tween.SpriteAccessor;

public class Splash implements Screen {
	
	private Sprite splash;
	private SpriteBatch batch;
	private TweenManager tweenManager;

	@Override
	public void show() {
		batch=new SpriteBatch();
		
		tweenManager=new TweenManager();//using for Annimating Tween engine
		Tween.registerAccessor(Sprite.class,new SpriteAccessor());//tell the splash we have sprite accessor
		
		Texture splashTexture=new Texture("img/splash.jpg");
		splash=new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		Tween.set(splash,SpriteAccessor.ALPHA).target(0).start(tweenManager);//set the sprite splash to accessor,transparent,to tweenManager
//		Tween.to(splash, SpriteAccessor.ALPHA,2).target(1).start(tweenManager);//(fade in )setting to another value..transparent nai  with tweenManager by start
//		Tween.to(splash, SpriteAccessor.ALPHA,2).target(0).delay(3).start(tweenManager);//fade out to transparent
		//above same thing is below with repeat
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,0.01f).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen( new Play());
				
				
		 	}
			}).start(tweenManager);
		}
		
	
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);//delta time between privious frame to current frame
		
		batch.begin();
		splash.draw(batch);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {

		
	}

	@Override
	public void pause() {
	
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
		
	}

}
