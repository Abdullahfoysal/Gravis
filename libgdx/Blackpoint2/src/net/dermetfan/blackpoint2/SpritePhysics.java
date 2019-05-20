package net.dermetfan.blackpoint2;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import net.dermetfan.blackpoint2.screens.Splash;

public class SpritePhysics implements Screen {
	
	
	private final float TIMESTEP=1/60f;
	private final int VELOCITYITERATIONS=8,POSITIONITERATIONS=3;
	private Vector2 movement=new Vector2();
	private float speed=5.0f;
	
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	  private  Sprite sprite;
	  private  Texture img;
	   private World world;
	   private  Body body;
	   private Body bodyEdgeScreen;
	   
	   float torque=0.1f;
	   boolean drawSprite=true;
	   final float TO_METER=100f;

	   
	   
	@Override
	public void show() {


		world=new World(new Vector2(0,-9.81f),true);
		debugRenderer=new Box2DDebugRenderer();

		camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		//input type check to InputController
		Gdx.input.setInputProcessor(new InputController() {
			@Override
			public boolean keyDown(int keycode) {
					switch(keycode) {
					case Keys.ESCAPE:
					 ( (Game) Gdx.app.getApplicationListener()).setScreen((Screen) new Splash());
					 break;
					case Keys.W:
						movement.y=+speed;
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
						movement.x=-speed;
						break;
					case Keys.RIGHT:
						movement.x=speed;
						break;
					case Keys.UP:
						movement.y=speed;
						break;
					case Keys.DOWN://fixture defination

						movement.y=-speed;
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
					movement.x=0;
					break;
				case Keys.RIGHT:
					movement.x=0;
					break;
				case Keys.UP:
					movement.y=0;
					break;
				case Keys.DOWN:
					movement.y=0;
					break;
				}
				
				
				return true;
			
			}
		});
        
        
		
		batch=new SpriteBatch();
		img=new Texture("img/transparent.png");
		sprite =new Sprite(img);
		sprite.setPosition(sprite.getWidth()/2,sprite.getHeight()/2);
		
	
		sprite.setScale(0.5f);

		
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyDef.BodyType.DynamicBody;
		
     	bodyDef.position.set((sprite.getX()+sprite.getWidth()/2)/TO_METER,(sprite.getY()+sprite.getHeight()/2)/TO_METER);
		
		body=world.createBody(bodyDef);

		//define polygon shape with the same as my sprite
		PolygonShape shape=new PolygonShape();
		shape.setAsBox(sprite.getWidth()/2/TO_METER,sprite.getHeight()/2/TO_METER);
//		shape.setAsBox(0.5f,1f);
	
	
	
		
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.shape=shape;
		fixtureDef.density=0.1f;
//		fixtureDef.friction=9f;
		fixtureDef.restitution=0.5f;
		
		body.createFixture(fixtureDef);
		shape.dispose();
		
		///end of sprite of physics first sprite
		
		
		//start second one
		BodyDef bodyDef2=new BodyDef();
		bodyDef2.type=BodyDef.BodyType.StaticBody;
		
		float w=Gdx.graphics.getWidth()/TO_METER;
		float h=Gdx.graphics.getHeight()/TO_METER-50/TO_METER;///Above 50 pixel
		bodyDef2.position.set(0,0);
		
//		FixtureDef fixtureDef2=new FixtureDef();
//		EdgeShape edgeShape=new EdgeShape();
//		
//		edgeShape.set(-w/2, h/2,w/2-1f,h/2);
//		fixtureDef2.shape=edgeShape;
//		
//		bodyEdgeScreen=world.createBody(bodyDef2);
//		bodyEdgeScreen.createFixture(fixtureDef2);
//		edgeShape.dispose();
//		
		
		
		//ground
		BodyDef bodyDef3=new BodyDef();
		bodyDef3.type=BodyType.StaticBody;
		bodyDef3.position.set(0,0);
		
		//ground Shape
		ChainShape groundShape=new ChainShape();
		groundShape.createChain(new Vector2[] {new Vector2(-30,0.5f),new Vector2(30,0.5f)});
		FixtureDef fixtureDef3=new FixtureDef();
		fixtureDef3.shape=groundShape;
		fixtureDef3.friction=1f;
		fixtureDef3.restitution=0.75f;
		
		world.createBody(bodyDef3).createFixture(fixtureDef3);
		groundShape.dispose();
		
	
				
		
		
	}

	@Override
	public void render(float delta) {
		  
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
//        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		//camera.position.set(body.getPosition().x,body.getPosition().y,0);
		camera.update();
	    body.applyTorque(torque, true);
		body.applyForceToCenter(movement, true);
		
        // Now update the spritee position accordingly to it's now updated 
       // Physics body
		sprite.setPosition((body.getPosition().x * TO_METER) - sprite.
                getWidth()/2 ,
        (body.getPosition().y * TO_METER) -sprite.getHeight()/2 );
        
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        
        // You know the rest...
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
         batch.setProjectionMatrix(camera.combined);
        debugMatrix=batch.getProjectionMatrix().cpy().scale(TO_METER,TO_METER,0);


        
        batch.begin();
        
        if(drawSprite)
           batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),
           sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());
        
         //sprite.draw(batch);
        batch.end();
        
       debugRenderer.render(world,debugMatrix);
		
	}
	

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth=width/10;
		camera.viewportHeight=height/10;
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		img.dispose();
		world.dispose();
		debugRenderer.dispose();
	}
   
	
}
