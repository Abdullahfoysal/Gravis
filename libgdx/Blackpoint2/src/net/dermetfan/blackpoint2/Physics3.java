package net.dermetfan.blackpoint2;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Physics3 extends ApplicationAdapter {
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
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("img/transparent.png");

        // Create two identical sprites slightly offset from each other vertically
        sprite = new Sprite(img);
        sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2 +200);
        //sprite.setScale(0.5f);
        sprite.setSize(300,300);
        sprite2 = new Sprite(img);
        sprite2.setPosition(-sprite.getWidth()/2 + 20,-sprite.getHeight()/2 + 400);
       //sprite2.setScale(0.5f);
        sprite2.setSize(300,300);

        world = new World(new Vector2(0, -1f),true);

        // Sprite1's Physics body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);


        body = world.createBody(bodyDef);

        // Sprite2's physics body
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set((sprite2.getX() + sprite2.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite2.getY() + sprite2.getHeight()/2) / PIXELS_TO_METERS);

        body2 = world.createBody(bodyDef2);

        // Both bodies have identical shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
                /2 / PIXELS_TO_METERS);

        // Sprite1
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.5f;


        // Sprite2
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = shape;
        fixtureDef2.density = 0.1f;
        fixtureDef2.restitution = 0.5f;

        body.createFixture(fixtureDef);
        body2.createFixture(fixtureDef2);

        shape.dispose();

        // Now the physics body of the bottom edge of the screen
        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS;

        bodyDef3.position.set(0,0);
        FixtureDef fixtureDef3 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);

        fixtureDef3.shape = edgeShape;


        bodyEdgeScreen = world.createBody(bodyDef3);
        bodyEdgeScreen.createFixture(fixtureDef3);
        edgeShape.dispose();
        
      //ground
      		BodyDef bodyDef4=new BodyDef();
      		bodyDef4.type=BodyType.StaticBody;
      		bodyDef4.position.set(0,0);
      		
      		//ground Shape
      		ChainShape groundShape=new ChainShape();
      		groundShape.createChain(new Vector2[] {new Vector2(-30,0.5f),new Vector2(30,0.5f)});
      		FixtureDef fixtureDef4=new FixtureDef();
      		fixtureDef3.shape=groundShape;
      		fixtureDef3.friction=1f;
      		fixtureDef3.restitution=0.75f;
      		
      		world.createBody(bodyDef4).createFixture(fixtureDef3);
      		groundShape.dispose();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
                  getHeight());

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
//                // Check to see if the collision is between the second sprite and the bottom of the screen
//                // If so apply a random amount of upward force to both objects... just because
//                if((contact.getFixtureA().getBody() == bodyEdgeScreen &&
//                        contact.getFixtureB().getBody() == body2)
//                        ||
//                        (contact.getFixtureA().getBody() == body2 &&
//                                contact.getFixtureB().getBody() == bodyEdgeScreen)) {
//
//                    body.applyForceToCenter(0,MathUtils.random(20,50),true);
//                    body2.applyForceToCenter(0, MathUtils.random(20,50), true);
//                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    @Override
    public void render() {
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PIXELS_TO_METERS) -sprite.getHeight()/2 );


        sprite.setRotation((float)Math.toDegrees(body2.getAngle()));
        sprite2.setPosition((body2.getPosition().x * PIXELS_TO_METERS) - sprite2.
                        getWidth()/2 ,
                (body2.getPosition().y * PIXELS_TO_METERS) -sprite2.getHeight()/2 );
        sprite2.setRotation((float)Math.toDegrees(body.getAngle()));


        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                        getScaleY(),sprite.getRotation());
        batch.draw(sprite2, sprite2.getX(), sprite2.getY(),sprite2.getOriginX(),
                sprite2.getOriginY(),
                sprite2.getWidth(),sprite2.getHeight(),sprite2.getScaleX(),sprite2.
                        getScaleY(),sprite2.getRotation());
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        world.dispose();
    }
}