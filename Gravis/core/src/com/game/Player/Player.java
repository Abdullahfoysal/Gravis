package com.game.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.gameClass;

public class Player extends Sprite {
	
	public  World world;
	public Body b2body;
	private TextureRegion playerStand;
	
	public Player(World world,PlayScreen screen) {
		super(screen.getAtlas().findRegion("jumping"));
		this.world=world;
		definePlayer();
		playerStand=new TextureRegion(getTexture(),0,0,200,250);
		setBounds(0,0,280/gameClass.PPM,312/gameClass.PPM);
		setRegion(playerStand);
		
	}
	
	public void definePlayer() {
		BodyDef bdef=new BodyDef();
		bdef.position.set(500/gameClass.PPM,600/gameClass.PPM);
		bdef.type=BodyDef.BodyType.DynamicBody;
		b2body=world.createBody(bdef);
		
		FixtureDef fdef=new FixtureDef();
		 
		
		CircleShape shape=new CircleShape();
		shape.setRadius(20f/gameClass.PPM);
		fdef.shape=shape;
		fdef.friction=0.4f;
		 fdef.restitution=0.4f;
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
