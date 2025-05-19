package ru.itschool.spherical_space;

import static ru.itschool.spherical_space.Main.SCR_HEIGHT;
import static ru.itschool.spherical_space.Main.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScrGame implements Screen {
    //public static final float WWIDTH = 16, WHEIGHT = 9;
    private int count;
    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;
    private World world;
    private Box2DDebugRenderer renderer;

    DynamicBodyCircle[] rocks = new DynamicBodyCircle[3];
    DynamicBodyCircle[] aliens = new DynamicBodyCircle[2];
    DynamicBodyBox[] boxes = new DynamicBodyBox[3];
    public Body bodyTouched;


    Texture img;
    Button bBack;

    public ScrGame(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        img = new Texture("bg2.jpg");
        bBack = new Button(font, "X", 1375, 900);
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        Box2D.init();
        world = new World(new Vector2(0, -15f), true);
        renderer = new Box2DDebugRenderer();
        touch = new Vector3();
        count = aliens.length;
        Gdx.input.setInputProcessor(new MyInputProcessor());
        for (int i = 0; i<rocks.length; i++){
            rocks[i] = new DynamicBodyCircle(world, 100+i*50, 120, 12f, "rock" + i);
        }
        aliens[0] = new DynamicBodyCircle(world, 820, 200, 8f, "alien0");
        aliens[1] = new DynamicBodyCircle(world, 760, 200, 8f, "alien1");
        boxes[0] = new DynamicBodyBox(world, 790, 150, 200f, 40f, "boardSafe");
        boxes[1] = new DynamicBodyBox(world, 860, 250, 20f, 100f, "board");
        boxes[2] = new DynamicBodyBox(world, 720, 250, 20f, 100f, "board");
        StaticBody floor = new StaticBody(world, SCR_WIDTH/2, 40, 1800f, 100f);
        StaticBody floor2 = new StaticBody(world, 0, 60, 500f, 100f);
        StaticBody wall1 = new StaticBody(world, 20, SCR_HEIGHT/2, 50f, 950f);
        StaticBody wall2 = new StaticBody(world, SCR_WIDTH-20, SCR_HEIGHT/2, 50f, 950f);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                Body bodyA = fixtureA.getBody();
                Body bodyB = fixtureB.getBody();

                if (bodyA.getType() == BodyDef.BodyType.DynamicBody
                    && bodyB.getType() == BodyDef.BodyType.DynamicBody){
                    Object userDataA = bodyA.getUserData();
                    Object userDataB = bodyB.getUserData();
                    for (int i=0; i<aliens.length; i++){
                        if ((userDataA.equals("alien"+ i) && userDataA.equals("board")) || (userDataB.equals("alien" + i) && userDataA.equals("board"))){
                            aliens[i].isDead = true;
                        }
                        for (int j=0; j<rocks.length; j++){
                            if ((userDataA.equals("alien"+ i) && userDataA.equals("rock"+j)) || (userDataB.equals("alien" + i) && userDataA.equals("rock"+j))){
                                aliens[i].isDead = true;
                            }
                        }
                    }
                }
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
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (bBack.hit(touch.x, touch.y)){
                main.setScreen(main.scrMenu);
            }
        }
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(img, 0 , 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "LEVEL 1", 400, 800);
        font.draw(batch, "Alien count: " + count, 700, 800);
        bBack.f.draw(batch, bBack.text, bBack.x, bBack.y);
        batch.end();
        renderer.render(world, camera.combined);
        batch.setProjectionMatrix(camera.combined);
        world.step(1/60f, 6, 2);
        for (int i=0; i<aliens.length; i++){
            if (aliens[i] != null && aliens[i].isDead){
                world.destroyBody(aliens[i].body);
                aliens[i]=null;
                count--;
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }

    class MyInputProcessor implements InputProcessor{
        Vector3 touchDown = new Vector3();
        Vector3 touchUp = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            touchDown.set(screenX, screenY, 0);
            camera.unproject(touchDown);
            bodyTouched = null;
            for (int i=0; i<rocks.length; i++){
                if(rocks[i].hit(touchDown)){
                    bodyTouched = rocks[i].body;
                }
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (bodyTouched != null) {
                touchUp.set(screenX, screenY, 0);
                camera.unproject(touchUp);
                Vector3 swipe = new Vector3(touchUp).sub(touchDown);
                float swipeLength = swipe.len();
                bodyTouched.applyLinearImpulse(new Vector2(0-swipe.x*100, 0-swipe.y*100), bodyTouched.getPosition(), true);
            }
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}
