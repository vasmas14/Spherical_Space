package ru.itschool.spherical_space;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Main extends Game {
    public static final float SCR_WIDTH = 900;
    public static final float SCR_HEIGHT = 1300;
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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("poetsen_one.fnt"));

        scrMenu = new ScrMenu(this);
        scrGame = new ScrGame(this);
        scrSettings = new ScrSettings(this);
        scrLeaderboard = new ScrLeaderboard(this);
        scrAbout = new ScrAbout(this);
        setScreen(scrMenu);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
