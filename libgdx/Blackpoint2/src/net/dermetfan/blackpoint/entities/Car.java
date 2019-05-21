package net.dermetfan.blackpoint.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class Car extends InputAdapter {
	
	private Body chassis,leftWheel,rightWheel;
	private WheelJoint leftAxis,rightAxis;
	
	public Car(World world,FixtureDef chassisFixtureDef,FixtureDef wheelFixtureDef,float x,float y,float width,float height) {
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.DynamicBody;
		bodyDef.position.set(-10,8);
		
		//chassis
		PolygonShape chassisShape=new PolygonShape();
		chassisShape.set(new float[] {-width/2,-height/2,width/2,-height/2,width/2*0.75f,height/2,-width/2*0.8f,height/2*0.8f});
		
		chassisFixtureDef.shape=chassisShape;
		
		chassis=world.createBody(bodyDef);
		chassis.createFixture(chassisFixtureDef);
		
		//left wheel
		CircleShape wheelShape=new CircleShape();
		wheelShape.setRadius(height/2.5f);
		
		wheelFixtureDef.shape=wheelShape;
		
		leftWheel=world.createBody(bodyDef);
		leftWheel.createFixture(wheelFixtureDef);
		
		//right wheel
		rightWheel=world.createBody(bodyDef);
		rightWheel.createFixture(wheelFixtureDef);
		
		//left axis
		WheelJointDef axisDef=new WheelJointDef();
		axisDef.bodyA=chassis;
		axisDef.bodyB=leftWheel;
		axisDef.localAnchorA.set(-width/2*.75f+wheelShape.getRadius(),-height/2);
		axisDef.frequencyHz=chassisFixtureDef.density;
		axisDef.localAxisA.set(Vector2.Y);
		leftAxis=(WheelJoint) world.createJoint(axisDef);
		
		//right axis
		axisDef.bodyB=rightWheel;
		axisDef.localAnchorA.x*=-2.3f;
		
		rightAxis=(WheelJoint) world.createJoint(axisDef);
		
		
			
		
	}
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			break;
		case Keys.S:
			break;
		
		}
		return true;
		
	}
	@Override
	public boolean keyUp(int keycode) {
		
		return true;
	}

}
