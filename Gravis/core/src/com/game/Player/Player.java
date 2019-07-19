package com.game.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	private float stateTimer;
	private boolean runningRight;
	
	public Player(World world,PlayScreen screen) {
		super(screen.getAtlas().findRegion("jumping"));
		this.world=world;
		
		currentState=State.JUMPING;
		previousState=State.JUMPING;
		stateTimer=0f;
		runningRight=true;
		
		Array<TextureRegion> frames=new Array<TextureRegion>();
		for(int i=0;i<4;i++)
			frames.add(new TextureRegion(getTexture(),i*200,0,200,260));//getTexture(),i*281,300,281,300)
		
		playerJump=new Animation(0.1f,frames);
		frames.clear();
//		for(int i=0;i<=4;i++)
//			frames.add(new TextureRegion(getTexture(),i*200,3*300,200,300));
//		playerRun=new Animation(0.1f,frames);
//		frames.clear();
//		
		definePlayer();
		playerStand=new TextureRegion(getTexture(),0,0,200,250);//player width and height
		setBounds(0,0,280/gameClass.PPM/2,312/gameClass.PPM/2);///scale divide by 2
		setRegion(playerStand);
		
	}
	
	public void update(float dt) {
		setPosition(0.21f+b2body.getPosition().x-getWidth()/2,0.35f+b2body.getPosition().y-getHeight()/2);//set to body
		setRegion(getFrame(dt));
	}
	
	private TextureRegion getFrame(float dt) {
		
		currentState=getState();
		
		TextureRegion region;
		switch(currentState) {
		case JUMPING:
			region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
			break;
		case RUNNING:
			region = (TextureRegion) playerRun.getKeyFrame(stateTimer);
			break;
		case FALLING:
		case STANDING:
			default:
				region=playerStand;
				break;
			
		
		}
		
//		if((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX() ) {
//			region.flip(true, false);
//			runningRight=false;
//		}  
//		else if((b2body.getLinearVelocity().x>0 || runningRight ) && region.isFlipX() ) {
//			region.flip(true, false);
//			runningRight=true;
//		}
		
		stateTimer=currentState==previousState ? stateTimer +dt:0;
		previousState=currentState;
		
		return region  ;
	}

	private State getState() {
		
//		if(b2body.getLinearVelocity().y>0 || b2body.getLinearVelocity().y<0 && previousState == State.JUMPING)
//			return State.JUMPING;
//		else if(b2body.getLinearVelocity().y<0)
//			return State.FALLING  ;
//		else if(b2body.getLinearVelocity().x!=0)
//			return State.RUNNING;
//		else return State.STANDING;
		return State.JUMPING;
		
		
	}

	public void definePlayer() {
		BodyDef bdef=new BodyDef();
		bdef.position.set(500/gameClass.PPM,600/gameClass.PPM);
		bdef.type=BodyDef.BodyType.DynamicBody;
		b2body=world.createBody(bdef);
		
		FixtureDef fdef=new FixtureDef();
		 
		
		CircleShape shape=new CircleShape();
		shape.setRadius(19f/gameClass.PPM);
		fdef.shape=shape;
		fdef.friction=1f;
		 fdef.restitution=0f;
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
