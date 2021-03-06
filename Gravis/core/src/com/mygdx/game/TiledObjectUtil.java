package com.mygdx.game;

import java.awt.Rectangle;
import com.badlogic.gdx.math.*;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class TiledObjectUtil {

	private World world;
	private TiledMap map;
	private PolygonShape shape;
	private com.badlogic.gdx.math.Rectangle rect;
	
	public TiledObjectUtil(World world,TiledMap map) {
			this.world=world;
			this.map=map;

			BodyDef bdef=new BodyDef();
			PolygonShape shape=new PolygonShape();
			FixtureDef fdef=new FixtureDef();
			Body body;
            for(MapObject object:map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
				
				 rect=((RectangleMapObject) object).getRectangle();
				bdef.type=BodyDef.BodyType.StaticBody;
				bdef.position.set((rect.getX()+rect.getWidth()/2)/gameClass.PPM,(rect.getY()+rect.getHeight()/2)/gameClass.PPM);
				body=world.createBody(bdef);
				
				shape.setAsBox(rect.getWidth()/2/gameClass.PPM, rect.getHeight()/2/gameClass.PPM);
				
				fdef.shape=shape;
				body.createFixture(fdef);
				
			}			 
		
		
	}
	
	public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object : objects){

            Shape shape;
            if(object instanceof PolygonMapObject){
                shape = createPolyline((PolygonMapObject) object);
            }else {
                continue;
            }
            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            FixtureDef fdef=new FixtureDef();
            fdef=Fixture(fdef,object.getName());
            fdef.shape=shape;
            fdef.density=0.30f;
            fdef.friction=0.45f;
            fdef.restitution=0.25f;
            body.createFixture(fdef);
            
            shape.dispose();
        }
    }

    private static ChainShape createPolyline(PolygonMapObject polyline){

        float [] vertices = polyline.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];
        for(int i=0; i< worldVertices.length; i++){
           worldVertices[i] = new Vector2(vertices[i*2]/ gameClass.PPM, vertices[i*2 + 1]/gameClass.PPM);

            //System.out.println(worldVertices[i]);
        }

       ChainShape chainShape = new ChainShape();
       chainShape.createChain(worldVertices);

        return chainShape;
    }
    
    public static FixtureDef Fixture(FixtureDef ff,String string) {
    	if(string=="spring")System.out.println("hi");
    	if(string=="spike")System.out.println("hi2");
    	
	
    	
    	return ff;
    	
    	
    }
}
