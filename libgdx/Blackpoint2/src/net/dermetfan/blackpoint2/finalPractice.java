package net.dermetfan.blackpoint2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class finalPractice implements Screen {
	
	private static final float TO_METER = 500f;
	private Box2DDebugRenderer debugRenderer;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	private final float TIMESTEP = 1 / 60f;
	SpriteBatch batch;
    Sprite sprite,sprite2;
    Texture img;
    World world;
    Body body,body2;
    Body bodyEdgeScreen;

    Matrix4 debugMatrix;
    OrthographicCamera camera;

    final float PIXELS_TO_METERS = 100f;
    
    @Override
	public void render(float delta) {
    	
    	world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
//      world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		//camera.position.set(body.getPosition().x,body.getPosition().y,0);
		camera.update();
	   // body.applyTorque(torque, true);
		//body.applyForceToCenter(movement, true);
		
      // Now update the spritee position accordingly to it's now updated 
     // Physics body
		sprite.setPosition((body.getPosition().x * TO_METER) - sprite.
              getWidth()/2 ,
      (body.getPosition().y * TO_METER) -sprite.getHeight()/2 );
      
      sprite.setRotation((float)Math.toDegrees(body.getAngle()));
      
      // You know the rest...
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      // batch.setProjectionMatrix(camera.combined);
     // debugMatrix=batch.getProjectionMatrix().cpy().scale(TO_METER,TO_METER,0);


      
      batch.begin();
      
      //if(drawSprite)
         batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),
         sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());
      
       //sprite.draw(batch);
      batch.end();
      
     debugRenderer.render(world,camera.combined);
		
	}
  

	@Override
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		
	

		camera = new OrthographicCamera();
		//Ground
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.StaticBody;
		bodyDef.position.set(0,0);
		
		ChainShape groundShape =new ChainShape();
		groundShape.createChain(new Vector2[] {new Vector2(-30,-1),new Vector2(30,-1)});
		FixtureDef fixtureDef=new FixtureDef();
		
		//property
		fixtureDef.shape=groundShape;
		fixtureDef.friction=0.5f;
		fixtureDef.restitution=0.0f;
		
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();
		
		
		
		batch=new SpriteBatch();
		img=new Texture("img/transparent.png");
		sprite =new Sprite(img);
		sprite.setPosition(sprite.getWidth()/2,sprite.getHeight()/2);
		
	
		sprite.setScale(0.5f);

		
		//BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyDef.BodyType.DynamicBody;
		
     	bodyDef.position.set((+sprite.getX()+sprite.getWidth()/2)/TO_METER,(sprite.getY()+sprite.getHeight()/2)/TO_METER);
		
		body=world.createBody(bodyDef);

		//define polygon shape with the same as my sprite
		PolygonShape shape=new PolygonShape();
		shape.setAsBox(sprite.getWidth()/2/TO_METER,sprite.getHeight()/2/TO_METER);
//		shape.setAsBox(0.5f,1f);
	
	
	
		
		//FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.shape=shape;
		fixtureDef.density=0.1f;
//		fixtureDef.friction=9f;
		fixtureDef.restitution=0.5f;
		
		body.createFixture(fixtureDef);
		shape.dispose();
		
		///end of sprite of physics first sprite
		
	}

	

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 20;
		camera.viewportHeight = height / 20;
		
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
		world.dispose();
		debugRenderer.dispose();
		img.dispose();
		
	}

}
