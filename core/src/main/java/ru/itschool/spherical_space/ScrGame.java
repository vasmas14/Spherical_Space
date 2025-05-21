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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    Texture textureAtlas;
    TextureRegion imgrock1;
    Texture textureAtlas2;
    TextureRegion imgrock2;
    Texture textureAtlas3;
    TextureRegion imgalien;
    TextureRegion imgrock3;

    DynamicBodyCircle[] rocks = new DynamicBodyCircle[3];
    DynamicBodyCircle[] aliens = new DynamicBodyCircle[2];
    DynamicBodyBox[] boxes = new DynamicBodyBox[3];
    public Body bodyTouched;
    StaticBody floor;
    StaticBody floor2;
    StaticBody wall1;
    StaticBody wall2;


    Texture img;
    Button bBack;
    Button bNext;

    public ScrGame(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        img = new Texture("bg2.jpg");
        bBack = new Button(font, "X", 1375, 900);
        bNext = new Button(font, ">Next level", 700, 450);
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        Box2D.init();
        world = new World(new Vector2(0, -15f), true);
        renderer = new Box2DDebugRenderer();
        touch = new Vector3();
        count = aliens.length;
        Gdx.input.setInputProcessor(new MyInputProcessor());
        textureAtlas = new Texture("rock3.png");
        textureAtlas2 = new Texture("rock4.jpg");
        textureAtlas3 = new Texture("alien1.png");
        imgrock1 = new TextureRegion(textureAtlas, 0, 0, 2048, 2048);
        imgrock2 = new TextureRegion(textureAtlas2, 0, 0, 100, 100);
        imgrock3 = new TextureRegion(textureAtlas2, 0, 0, 600, 300);
        imgalien = new TextureRegion(textureAtlas3, 0, 0, 160, 160);
        for (int i = 0; i<rocks.length; i++){
            rocks[i] = new DynamicBodyCircle(world, 100+i*50, 120, 12f, "rock" + i);
        }
        aliens[0] = new DynamicBodyCircle(world, 820, 200, 8f, "alien0");
        aliens[1] = new DynamicBodyCircle(world, 760, 200, 8f, "alien1");
        boxes[0] = new DynamicBodyBox(world, 790, 150, 200f, 40f, "boardSafe");
        boxes[1] = new DynamicBodyBox(world, 860, 250, 20f, 100f, "board");
        boxes[2] = new DynamicBodyBox(world, 720, 250, 20f, 100f, "board");
        floor = new StaticBody(world, SCR_WIDTH/2, 40, 1800f, 100f);
        floor2 = new StaticBody(world, 0, 60, 500f, 100f);
        wall1 = new StaticBody(world, 20, SCR_HEIGHT/2, 50f, 950f);
        wall2 = new StaticBody(world, SCR_WIDTH-20, SCR_HEIGHT/2, 50f, 950f);

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
            if (bNext.hit(touch.x, touch.y)){
                main.setScreen(main.level2);
            }
        }
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(img, 0 , 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "LEVEL 1", 400, 800);
        font.draw(batch, "Alien count: " + count, 700, 800);
        bBack.f.draw(batch, bBack.text, bBack.x, bBack.y);
        if (count==0){
            font.draw(batch, "LEVEL CLEARED!", 700, 500);
            bNext.f.draw(batch, bNext.text, bNext.x, bNext.y);
        }
        batch.end();
        renderer.render(world, camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i=0; i<rocks.length; i++){
            batch.draw(imgrock1, rocks[i].getX(), rocks[i].getY(), rocks[i].getW()/2, rocks[i].getH()/2,
                rocks[i].getW(), rocks[i].getH(), 1, 1, rocks[i].getR());
        }
        for (int i=0; i<boxes.length; i++){
            batch.draw(imgrock2, boxes[i].getX(), boxes[i].getY(), boxes[i].getW()/2, boxes[i].getH()/2,
                boxes[i].getW(), boxes[i].getH(), 1, 1, boxes[i].getR());
        }
        for (int i=0; i<aliens.length; i++){
            if (aliens[i]!=null){
                batch.draw(imgalien, aliens[i].getX(), aliens[i].getY(), aliens[i].getW()/2, aliens[i].getH()/2,
                    aliens[i].getW(), aliens[i].getH(), 1, 1, aliens[i].getR());
            }
        }
        batch.draw(imgrock3, floor2.getX(), floor2.getY(), floor2.getW()/2, floor2.getH()/2,
            floor2.getW(), floor2.getH(), 1, 1, floor2.getR());
        batch.draw(imgrock3, floor.getX(), floor.getY(), floor.getW()/2, floor.getH()/2,
            floor.getW(), floor.getH(), 1, 1, floor.getR());
        batch.draw(imgrock3, wall1.getX(), wall1.getY(), wall1.getW()/2, wall1.getH()/2,
            wall1.getW(), wall1.getH(), 1, 1, wall1.getR());
        batch.draw(imgrock3, wall2.getX(), wall2.getY(), wall2.getW()/2, wall2.getH()/2,
            wall2.getW(), wall2.getH(), 1, 1, wall2.getR());
        batch.end();
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
        textureAtlas.dispose();
        batch.dispose();
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
            if (main.scrGame==main.getScreen()){
                touchDown.set(screenX, screenY, 0);
                camera.unproject(touchDown);
                bodyTouched = null;
                for (int i=0; i<rocks.length; i++){
                    if(rocks[i].hit(touchDown) && !rocks[i].isTouched){
                        bodyTouched = rocks[i].body;
                        rocks[i].isTouched=true;
                    }
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
