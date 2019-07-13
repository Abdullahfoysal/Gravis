package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.*;

public class Play implements Screen{
	
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private Sprite s_ground,s_box;
	private Texture t_box;
	private SpriteBatch batch;
	private Body b_ground,b_box,b_border,b_border2,b_joint;
	private final float pxm=32;
	private Array<Body> tmpBodies = new Array<Body>();

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update();

		
		batch.setProjectionMatrix(camera.combined);
//		
//		  batch.begin(); 
//		  batch.draw(t_box,b_box.getPosition().x*32-t_box.getWidth()/2,b_box.getPosition().y*32-t_box.getHeight()/2 ); 
//		  batch.end();
		 
		batch.begin();
		world.getBodies(tmpBodies);
		for(Body body:tmpBodies)		
			if(body.getUserData()!=null && body.getUserData() instanceof Sprite) {
			Sprite sprite=(Sprite) body.getUserData();
			sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y-sprite.getHeight()/2);
			sprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees);
			sprite.draw(batch);
			}
		batch.end();
		
		debugRenderer.render(world, camera.combined);
		 
	}
	public void update() {
		world.step(1f/60f, 8, 3);
		inputUpdate();
	}
	public void inputUpdate() {
		int horizontalForce=0;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce-=1;
			b_border.setLinearVelocity(horizontalForce*5,b_border.getLinearVelocity().y);	
			b_border.applyTorque(30, true);
			//b_box.applyTorque(30, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce+=1;
			b_border.setLinearVelocity(horizontalForce*5,b_border.getLinearVelocity().y);
			b_border.applyTorque(30, true);

//			b_ground.applyForceToCenter(1, 15,false);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			b_box.applyForceToCenter(0,30,false);
//			b_box.applyAngularImpulse(100,true);
//			b_box.applyTorque(30, true);
			
					
		}
		//b_ground.setLinearVelocity(horizontalForce*5,b_ground.getLinearVelocity().y);
//		b_ground.applyForceToCenter(15,3.5f,false);
		
	}

	@Override
	public void show() {
		 world=new World(new Vector2(0,-9.81f),true);
		 debugRenderer = new Box2DDebugRenderer();
		 
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
		
		b_border=createBorder( new float[] {10,10,15,0,30,0});	
		b_border2=createBorder(new float[] {30,0,45,0,55,15});
	    b_ground=createArea();
		b_box=creatPlayer();

		
		//DistansceJoint between other box and box1
		DistanceJointDef distanceJointDef=new DistanceJointDef();
		distanceJointDef.bodyA= b_border;
		distanceJointDef.bodyB=b_border2;
		distanceJointDef.length=1;
		distanceJointDef.localAnchorA.set(30,0);
		distanceJointDef.localAnchorB.set(30,0);
		
		
		world.createJoint(distanceJointDef);
		
		
		
	ChainShape groundShape2 = new ChainShape();
	groundShape2.createChain(new Vector2[] { new Vector2(-20, 1), new Vector2(20, 1) });
BodyDef bodyDef=new BodyDef();
	 bodyDef.type=BodyType.StaticBody;
	 bodyDef.position.set(0,-3);
	 FixtureDef fixtureDef=new FixtureDef();
	 fixtureDef.shape=groundShape2;
	 
	world.createBody(bodyDef).createFixture(fixtureDef);
	 
	 groundShape2.dispose();
	 
	 CircleShape circle=new CircleShape();
	 circle.setRadius(2f);
	 BodyDef bodyDef2=new BodyDef();
	 bodyDef2.type=BodyType.DynamicBody;
	 
	 FixtureDef fixtureDef2=new FixtureDef();
	 fixtureDef2.shape=circle;
	 bodyDef2.position.set(-2f,10);
	 world.createBody(bodyDef2).createFixture(fixtureDef2);
	 circle.dispose();
	
	

	
		
		
		
	}
	
	public Body creatPlayer() {
		/// sprite adding with box2d
		Body tempBody;
		Sprite tempSprite;
		Texture tempTexture;
		
		BodyDef tempBodyDef=new BodyDef();
		tempBodyDef.type=BodyType.DynamicBody;
		tempBodyDef.position.set(0,-5);
		
		FixtureDef tempFixtureDef=new FixtureDef();
		tempFixtureDef.friction=0.25f;
		tempFixtureDef.restitution=0.75f;
		///sprite creating
		tempTexture=new Texture("img/transparent.png");
		tempSprite = new Sprite(tempTexture);
		tempSprite.setSize(10,10);// width,height in meter which will compress in
		tempSprite.setOrigin(tempSprite.getWidth() / 2, tempSprite.getHeight() / 2);
			 
				/*
				 * PolygonShape box=new PolygonShape(); box.setAsBox(5,5); fixtureDef.shape=box;box.dispose();
				 */
			CircleShape tempCircle=new CircleShape();
			tempCircle.setRadius(5f);
			tempFixtureDef.shape=tempCircle;
			tempBodyDef.position.set(-2f,0f);
			
			tempBody=world.createBody(tempBodyDef);
			tempBody.createFixture(tempFixtureDef);
			tempBody.setUserData(tempSprite);
			tempCircle.dispose();
			
			return tempBody;
		
	}
	public Body createArea() {
		
		Body bodyTemp;
		 //Ground
		 PolygonShape groundShape =new PolygonShape();
		 groundShape.set(new float[] {-30,-1,30,-1,1,7,-30,-1});
		 //groundShape.setAsBox(10, 20);
		 BodyDef bodyDef=new BodyDef();
		 bodyDef.type=BodyType.DynamicBody;
		 bodyDef.position.set(0,15);//pixel to meter
		 
		 FixtureDef fixtureDef=new FixtureDef();
		 fixtureDef.shape=groundShape;
		 fixtureDef.friction=0.5f;
		 fixtureDef.restitution=1f;
		 
		 
		 bodyTemp=world.createBody(bodyDef);
		 bodyTemp.createFixture(fixtureDef);
		
		groundShape.dispose();
		
		return bodyTemp;
		
	}
	public Body createBorder(float[] vertices) {
		Body bodyTemp;
	
		 //Ground
		 PolygonShape tempPoly =new PolygonShape();
		 tempPoly.set(vertices);
		// tempPoly.set(new float[] {10,10,15,0,30,0} );
	
//		 tempPoly.set(new float[] {-12,8,-12,4,-6,4,-6,8,-7,8,-7,5,-11,5,-11,8,-12,8});

		 //groundShape.setAsBox(10, 20);
		 BodyDef tempBody=new BodyDef();
		 tempBody.type=BodyType.DynamicBody;
		 tempBody.position.set(0,1);//pixel to meter
		 
		 FixtureDef tempFixtureDef=new FixtureDef();
		 tempFixtureDef.shape=tempPoly;
		 tempFixtureDef.friction=1f;
		 tempFixtureDef.restitution=1f;
		 tempFixtureDef.density=0.5f;
		 
		 
		 bodyTemp=world.createBody(tempBody);
		 bodyTemp.createFixture(tempFixtureDef);
		
		 tempPoly.dispose();
		
		
		
		return bodyTemp;
	}

	


	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 20;
		camera.viewportHeight = height / 20;		
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
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();
		
	}
	

}
