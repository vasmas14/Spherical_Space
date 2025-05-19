package ru.itschool.spherical_space;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


public class Main extends Game {
    public static final float SCR_WIDTH = 1400;
    public static final float SCR_HEIGHT = 900;
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public Vector3 touch;
    public BitmapFont font;

    ScrMenu scrMenu;
    ScrGame scrGame;
    ScrSettings scrSettings;
    ScrLeaderboard scrLeaderboard;
    ScrAbout scrAbout;
    @Override
    public void create() {
        batch = new SpriteBatch();
        touch = new Vector3();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        font = new BitmapFont(Gdx.files.internal("poetsen_one2.fnt"));
        scrMenu = new ScrMenu(this);
        scrGame = new ScrGame(this);
        scrSettings = new ScrSettings(this);
        scrAbout = new ScrAbout(this);
        setScreen(scrMenu);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
