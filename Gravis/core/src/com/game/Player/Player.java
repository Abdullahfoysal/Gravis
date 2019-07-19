package com.game.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.gameClass;

public class Player extends Sprite {
	public enum State{	FALLING,JUMPING,STANDING,RUNNING};
	public State currentState;
	public State previousState;
	public  World world;
	public Body b2body;
	private TextureRegion playerStand;
	private Animation playerRun;
	private Animation playerJump;

	private boolean runningRight;
	
	
	private Texture texture;
	
	public Player(World world,PlayScreen screen) {
		
		this.world=world;
		
		definePlayer();
		
		
		
	}
	
	
	public void update(float dt,Sprite ss) {
		//setPosition(b2body.getPosition().x,b2body.getPosition().y-getHeight()/2);//set to body
		//ss.setPosition(b2body.getPosition().x, b2body.getPosition().y);
		
	}
	
	
	
	
	

	public void definePlayer() {
		BodyDef bdef=new BodyDef();
		bdef.position.set(500/gameClass.PPM,600/gameClass.PPM);
		bdef.type=BodyDef.BodyType.DynamicBody;
		b2body=world.createBody(bdef);
		
		FixtureDef fdef=new FixtureDef();
		 
		
	//	CircleShape shape=new CircleShape();
	 //   shape.setRadius(20f/gameClass.PPM);
	    PolygonShape shape=new PolygonShape();
		shape.setAsBox(0.2f,0.2f);
		fdef.shape=shape;
		fdef.friction=1f;
		 fdef.restitution=0.35f;
		 fdef.density=0.75f;
		b2body.createFixture(fdef);
				
	}
	
	public Body creatPlayer(String spriteAddress) {
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
		tempTexture=new Texture(spriteAddress);
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
	

}
