package net.dermetfan.blackpoint2;

import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import net.dermetfan.blackpoint2.screens.Splash;

public class Play implements Screen {
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private float down=0.0f;
	
	private final float TIMESTEP=1/60f;
	private final int VELOCITYITERATIONS=8,POSITIONITERATIONS=3;
	
	private float speed=250f;
	private Vector2 movement=new Vector2();
	private Vector2 movement2=new Vector2();
	private Body box1,box2;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		box1.applyForceToCenter(movement, true);
		box2.applyForceToCenter(movement2, true);

		camera.position.set(box1.getPosition().x,box1.getPosition().y,0);
		camera.update();
		
		debugRenderer.render(world, camera.combined);
	}
	@Override
	public void show() {
		world=new World(new Vector2(0,-9.81f),true);
		debugRenderer=new Box2DDebugRenderer();
		
		camera=new OrthographicCamera();
		
		//input type check to InputController
		Gdx.input.setInputProcessor(new InputController() {
			@Override
			public boolean keyDown(int keycode) {
					switch(keycode) {
					case Keys.ESCAPE:
					 ( (Game) Gdx.app.getApplicationListener()).setScreen((Screen) new Splash());
					 break;
					case Keys.W:
						movement.y=speed;
						break;
					case Keys.S:
						movement.y=-speed;
						
						break;
					case Keys.A:
						movement.x=-speed;
						break;
					case Keys.D:
						movement.x=speed;
						break;
					case Keys.LEFT:
						movement2.x=-speed;
						break;
					case Keys.RIGHT:
						movement2.x=speed;
						break;
					case Keys.UP:
						movement2.y=speed;
						break;
					case Keys.DOWN:
						movement2.y=-speed;
						break;
			}
				return true;
			}
			
			public boolean keyUp(int keycode) {
				
				switch(keycode) {
				case Keys.W:
				case Keys.S:
					movement.y=0;
					break;
				case Keys.A:
				case Keys.D:
					movement.x=0;
					break;
				case Keys.LEFT:
					movement2.x=0;
					break;
				case Keys.RIGHT:
					movement2.x=0;
					break;
				case Keys.UP:
					movement2.y=0;
					break;
				case Keys.DOWN:
					movement2.y=0;
					break;
				}
				
				
				return true;
			
			}
			
		});
		
		//BALL
		//body defination
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.DynamicBody;
		bodyDef.position.set(1,1);//unit metre
		
		
		//ball shape
		CircleShape ballshape=new CircleShape();
		ballshape.setRadius(.50f);
		ballshape.setPosition(new Vector2(-1,0));
//		System.out.println(ballshape.getType());
//		System.out.println(ballshape.getPosition());
		//multi thread work on ballshape
		
		//fixture defination->physical property
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.shape=ballshape;
		fixtureDef.density=2.5f;//kg m-2
		fixtureDef.friction=0.9f;
		fixtureDef.restitution=0.75f;
		
		
		world.createBody(bodyDef).createFixture(fixtureDef);
		
		ballshape.setRadius(.50f);
		ballshape.setPosition(new Vector2(-1.5f,1.5f));
		world.createBody(bodyDef).createFixture(fixtureDef);
		
		ballshape.dispose();
		
		
		
	    //Box
		
		bodyDef.type=BodyType.DynamicBody;
		bodyDef.position.set(0.9f,10);
		
		//box shape
		PolygonShape boxShape=new PolygonShape();
		boxShape.setAsBox(0.5f,1);//weight/2,height/2
		
		//fixture def
		fixtureDef.shape=boxShape;
		fixtureDef.friction=0.75f;
		fixtureDef.restitution=0.1f;
		fixtureDef.density=5;
		
		box1=world.createBody(bodyDef);
		box1.createFixture(fixtureDef);
		
 
		box2=world.createBody(bodyDef);
		box2.createFixture(fixtureDef);
		
		boxShape.dispose();
		
		box1.applyAngularImpulse(20,true);
		box2.applyAngularImpulse(20,true);
		
		
		//groundShape
				//body Defination
		
		  bodyDef.type=BodyType.StaticBody; 
		  bodyDef.position.set(0,0);
		 
				//group shape
				ChainShape groundShape=new ChainShape();
				groundShape.createChain(new Vector2[] {new Vector2(-30,-1),new Vector2(30,-1)});
				//fixture defination
				fixtureDef.shape=groundShape;
				fixtureDef.friction=0.5f;
				fixtureDef.restitution=0;
				
				world.createBody(bodyDef).createFixture(fixtureDef);
				groundShape.dispose();
				///another shape soil plain
				
				ChainShape groundShape2=new ChainShape();
				groundShape2.createChain(new Vector2[] {new Vector2(-2,-3),new Vector2(2,-0.6f)});
				//box1.createFixture(fixtureDef);
				world.createBody(bodyDef).createFixture(fixtureDef);
				groundShape2.dispose();
			
		
	
	}


	

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth=width/20;
		camera.viewportHeight=height/20;
//		camera.invProjectionView.rotate(new Vector3(1,10,0), 45);
		camera.update();
		

	}

	private Vector3 Vector3(int i, int j, int k) {
		
		return null;
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		
	}

}
