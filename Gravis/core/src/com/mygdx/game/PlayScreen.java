package com.mygdx.game;

import java.util.Vector;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Player.Player;

import Scenes.Hud;

public class PlayScreen implements Screen {
	

	private gameClass game;
	private Hud hud;
	private TextureAtlas atlas;
	
	private final static float pxm=32;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private World world;
	private Body body=null,b_player1;
	private Box2DDebugRenderer debugRenderer;
	
	private Player player;
	private TiledObjectUtil tiled;
	private Vector2 gavityDirection=new Vector2(0,-9.8f);
	
	
	public PlayScreen(gameClass game) {
		this.game=game;
		hud=new Hud(game.batch);
		atlas=new TextureAtlas("Sprite/sprites.pack");
		
		
		 world=new World(gavityDirection,true);
		gamecam=new OrthographicCamera();//Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10
		//gamecam.setToOrtho(false, 1000, 700);
		//gamecam.position.x+=500;
		//gamecam.position.y+=400;
		gamePort= new FitViewport(1000/gameClass.PPM,700/gameClass.PPM,gamecam);///screen height && width
		
		maploader=new TmxMapLoader();
		map=maploader.load("map/mapdraw.tmx");
		
		renderer=new OrthogonalTiledMapRenderer(map,1/gameClass.PPM);
		gamecam.position.set(gamePort.getWorldHeight()/2,gamePort.getWorldHeight()/2,0);
		
		debugRenderer = new Box2DDebugRenderer();
	
		//gamecam.position.set(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10,0);
		
		
		
		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("level1").getObjects());
		
		tiled=new TiledObjectUtil(world,map);
		
		//player creation world with physics
		player=new Player(world,this);
		
		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
		
	}
	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor (0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();//map renderer
		debugRenderer.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		
	}
	
	
	

	@Override
	public void show() {
		
		// TODO Auto-generated method stub
		
	}
	public void handleInput(float dt) {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			player.b2body.applyLinearImpulse(new Vector2(0,0.25f), player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=2)
			player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-2)
			player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
		
		
		///rotating camera and gravity
//		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
//		{
//			gamecam.rotate(player.b2body.getPosition().x,0,0,.002f*dt);
//			gavityDirection.x-=0.1f*dt;
//			gavityDirection.y+=0.1f*dt;
//			
//			
//		}
//		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
//		{
//			gamecam.rotate(player.b2body.getPosition().x,0,0,-.002f*dt);
//			gavityDirection.x+=0.1f*dt;
//			gavityDirection.y+=0.1f*dt;
//			
//			
	//	}
		

		
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			gamecam.zoom+=0.01f;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			gamecam.zoom-=0.01f;
////		if(Gdx.input.isTouched())
////			gamecam.position.x+=100*dt;
//			
//		
	}
	public void update(float dt) {
		world.step(1f/60f, 8, 2);
		handleInput(dt);
		player.update(dt);
		
		
		gamecam.position.x=player.b2body.getPosition().x;
		//gamecam.rotate(player.b2body.getPosition().x,0,0,.002f*dt);
	//	gamecam.rotate(axis, angle);
		//update gravity direction
		world.setGravity(gavityDirection);
		
		
		gamecam.update();
		renderer.setView(gamecam);
		
		
	}

	
	
	public void definePlayer() {
		Body b2body;
		BodyDef bdef=new BodyDef();
		bdef.position.set(500/gameClass.PPM,500/gameClass.PPM);
		bdef.type=BodyDef.BodyType.DynamicBody;
		b2body=world.createBody(bdef);
		
		FixtureDef fdef=new FixtureDef();
		 
		 
		CircleShape shape=new CircleShape();
		shape.setRadius(500f/gameClass.PPM);
		fdef.shape=shape;
		fdef.friction=1f;
		 fdef.restitution=0f;
		 fdef.density=0.75f;
		b2body.createFixture(fdef);
		
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
	map.dispose();
	renderer.dispose();
	world.dispose();
	debugRenderer.dispose();
		
	}

}
